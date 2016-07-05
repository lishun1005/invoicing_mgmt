package com.lishun.im.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lishun.im.bean.SysPermissions;
import com.lishun.im.bean.SysRole;
import com.lishun.im.bean.SysUser;
import com.lishun.im.dto.SysPermissionDto;
import com.lishun.im.dto.SysRoleDto;
import com.lishun.im.page.Page;
import com.lishun.im.service.SysUserManageService;
import com.lishun.im.shiro.utils.PasswordHelper;

@Controller
public class SysUserManageController {
	@Autowired
	private SysUserManageService sysUserManageService;

	@Autowired
	@Qualifier("passwordHelper")
	private PasswordHelper passwordHelper;

	@RequiresPermissions("systemManager:sysuserManager:list")
	@RequestMapping(value = "sysuser/sysUserList", method = RequestMethod.GET)
	public String sysUserList(Integer rows, Integer pageNo, String keyword,
			Model model) {
		if (null == rows) {
			rows = 10;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		Map<String, Object> map = sysUserManageService.querySysUserByPage(rows,
				pageNo, keyword);
		@SuppressWarnings("unchecked")
		List<SysUser> list = (List<SysUser>) map.get("list");
		Long total = (Long) map.get("total");
		Page<SysUser> sysUserPage = new Page<SysUser>();
		sysUserPage.setResult(list);
		sysUserPage.setTotalItems(total);
		sysUserPage.setPageNo(pageNo);
		sysUserPage.setPageSize(rows);
		model.addAttribute("sysUserPage", sysUserPage);
		// model.addAttribute("sysUserPage", sysUserPage);
		return "/sysuser/sysUserList";
	}

	@RequiresPermissions("systemManager:sysuserManager:add")
	@RequestMapping(value = "sysuser/sysUserAdd", method = RequestMethod.POST)
	public String sysUserAdd(String username, String email, String password,
			String locked,String realname) {
		SysUser user = new SysUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setRealname(realname);
		if ("true".equalsIgnoreCase(locked)) {
			user.setLocked(true);
		} else {
			user.setLocked(false);
		}
		passwordHelper.encryptPassword(user);// 加密密码
		sysUserManageService.createUser(user);
		return "redirect:/sysuser/sysUserList";
	}

	@RequiresPermissions("systemManager:sysuserManager:delete")
	@RequestMapping(value = "sysuser/sysUserDelete", method = RequestMethod.GET)
	public String sysUserDelete(String sysUserId) {

		sysUserManageService.deleteUser(sysUserId);
		return "redirect:/sysuser/sysUserList";
	}

	@RequiresPermissions("systemManager:sysuserManager:edit")
	@RequestMapping(value = "sysuser/sysUserUpdate", method = RequestMethod.POST)
	public String sysUserUpdate(String sysUserId, String email, String locked,String realname) {
		System.out.println(sysUserId + "," + email + "," + locked);
		SysUser userDto = new SysUser();
		userDto.setId(sysUserId);
		userDto.setEmail(email);
		userDto.setRealname(realname);
		if ("true".equalsIgnoreCase(locked)) {
			userDto.setLocked(true);
		} else {
			userDto.setLocked(false);
		}

		sysUserManageService.updateUser(userDto);

		return "redirect:/sysuser/sysUserList";
	}

	@RequiresPermissions("systemManager:sysroleManager:list")
	@RequestMapping(value = "sysuser/getMyRole", method = RequestMethod.GET)
	@ResponseBody
	public Object getMyRole(String username) {
		Set<String> userRoleSet = sysUserManageService.findRoles(username);
		@SuppressWarnings("unchecked")
		List<SysRole> allRoleList = (List<SysRole>) sysUserManageService
				.querySysRoleListMap(1000, 1, null).get("list");
		List<SysRoleDto> roleDtoList = new ArrayList<SysRoleDto>();
		for(SysRole role : allRoleList){
			SysRoleDto roleDto = new SysRoleDto();
			if(userRoleSet.contains(role.getRole())){
				roleDto.setIsbind(true);
			}else{
				roleDto.setIsbind(false);
			}
			roleDto.setSysRole(role);
			roleDtoList.add(roleDto);
		}	

		return roleDtoList;// roleDtoList;
	}

	@RequiresPermissions("systemManager:sysroleManager:edit")
	@RequestMapping(value = "sysuser/editMyRole", method = RequestMethod.POST)
	public String editMyRole(String username, String[] role) {

		Set<String> userRoleIdSet = sysUserManageService.findRoleIds(username);
		SysUser user = sysUserManageService.findByUserName(username);
		List<String> removeList = new ArrayList<String>();
		List<String> addList = new ArrayList<String>();
		if (userRoleIdSet != null) {
			for (String oldRole : userRoleIdSet) {
				if (!ArrayUtils.contains(role, oldRole)) {
					// 新的角色主不包含旧的角色
					// 删除该角色
					removeList.add(oldRole);
				}
			}
		}
		if (role != null) {
			for (String newRole : role) {
				if (!userRoleIdSet.contains(newRole)) {
					// 旧角色不包含新角色
					// 新增该角色
					addList.add(newRole);
				}
			}
		}
		if (removeList.size() > 0)
			sysUserManageService.uncorrelationRoles(user.getId(),
					removeList.toArray(new String[removeList.size()]));
		if (addList.size() > 0) {
			sysUserManageService.correlationRoles(user.getId(),
					addList.toArray(new String[addList.size()]));
		}
		return "redirect:/sysuser/sysUserList";
	}

	@RequiresPermissions("systemManager:sysroleManager:list")
	@RequestMapping(value = "sysuser/sysRoleList", method = RequestMethod.GET)
	public String sysRoleList(Integer rows, Integer pageNo, String keyword,
			Model model) {
		if (null == rows) {
			rows = 10;
		}
		if (null == pageNo) {
			pageNo = 1;
		}

		Map<String, Object> map = sysUserManageService.querySysRoleListMap(
				rows, pageNo, keyword);

		@SuppressWarnings("unchecked")
		List<SysRole> list = (List<SysRole>) map.get("list");
		Long total = (Long) map.get("total");
		Page<SysRole> sysUserPage = new Page<SysRole>();
		sysUserPage.setResult(list);
		sysUserPage.setTotalItems(total);
		sysUserPage.setPageNo(pageNo);
		sysUserPage.setPageSize(rows);
		model.addAttribute("sysRolePage", sysUserPage);

		// Page<SysRole> sysRolePage =
		// sysUserManageService.querySysRoleByPage(rows, pageNo, keyword, null);
		// model.addAttribute("sysRolePage", sysRolePage);
		return "/sysuser/sysRoleList";
	}

	@RequiresPermissions("systemManager:sysroleManager:add")
	@RequestMapping(value = "sysuser/sysRoleAdd", method = RequestMethod.POST)
	public String sysRoleAdd(String role, String description, String available) {

		SysRole sysRole = new SysRole();
		sysRole.setRole(role);
		sysRole.setDescription(description);
		if ("true".equalsIgnoreCase(available)) {
			sysRole.setAvailable(true);
		} else {
			sysRole.setAvailable(false);
		}
		sysUserManageService.createRole(sysRole);
		return "redirect:/sysuser/sysRoleList";
	}

	@RequiresPermissions("systemManager:sysroleManager:edit")
	@RequestMapping(value = "sysuser/sysRoleUpdate", method = RequestMethod.POST)
	public String sysRoleUpdate(String id, String role, String description,
			String available) {

		SysRole sysRole = new SysRole();
		sysRole.setId(id);
		sysRole.setRole(role);
		sysRole.setDescription(description);
		if ("true".equalsIgnoreCase(available)) {
			sysRole.setAvailable(true);
		} else {
			sysRole.setAvailable(false);
		}
		sysUserManageService.updateRole(sysRole);

		return "redirect:/sysuser/sysRoleList";
	}

	@RequiresPermissions("systemManager:sysroleManager:delete")
	@RequestMapping(value = "sysuser/sysRoleDelete", method = RequestMethod.GET)
	public String sysRoleDelete(String sysRoleId) {
		sysUserManageService.deleteRole(sysRoleId);
		return "redirect:/sysuser/sysRoleList";
	}

	@RequiresPermissions("systemManager:sysroleManager:list")
	@RequestMapping(value = "sysuser/getMyPermission", method = RequestMethod.GET)
	@ResponseBody
	public Object getMyPermission(String sysRoleId) {
		Set<String> userPermissionSet = sysUserManageService.findPermissionIdsByRoleId(sysRoleId);
		
		List<SysPermissions>  allPermissionList = (List<SysPermissions>) sysUserManageService
				.querySysPermissionListMap(1000, 1, null).get("list");
		List<SysPermissionDto> sysPermissionDtoList = new ArrayList<SysPermissionDto>();
		for(SysPermissions permission : allPermissionList){
			SysPermissionDto sysPermissionDto = new SysPermissionDto();
			if(userPermissionSet.contains(permission.getId())){
				sysPermissionDto.setIsbind(true);
			}else{
				sysPermissionDto.setIsbind(false);
			}
			sysPermissionDto.setSysPermission(permission);
			sysPermissionDtoList.add(sysPermissionDto);
		}		
		
		return sysPermissionDtoList;
	}

	@RequestMapping(value = "sysuser/editMyPermission", method = RequestMethod.POST)
	public String editMyPermission(String sysRoleId, String... permission) {
		Set<String> userPermissionSet = sysUserManageService
				.findPermissionIdsByRoleId(sysRoleId);
		List<String> removeList = new ArrayList<String>();
		List<String> addList = new ArrayList<String>();
		if (userPermissionSet != null) {
			for (String oldPermission : userPermissionSet) {
				if (!ArrayUtils.contains(permission, oldPermission)) {
					removeList.add(oldPermission);
				}
			}
		}
		if (permission != null) {
			for (String newPermission : permission) {
				if (!userPermissionSet.contains(newPermission)) {
					addList.add(newPermission);
				}
			}
		}
		if (removeList.size() > 0)
			sysUserManageService.uncorrelationPermissions(sysRoleId,
					removeList.toArray(new String[removeList.size()]));
		if (addList.size() > 0){
			sysUserManageService.correlationPermissions(sysRoleId,
					addList.toArray(new String[addList.size()]));
		}
		return "redirect:/sysuser/sysRoleList";
	}

	@RequiresPermissions("systemManager:sysPermission:list")
	@RequestMapping(value = "sysuser/sysPermissionList", method = RequestMethod.GET)
	public String sysPermissionList(Integer rows, Integer pageNo,
			String keyword, Model model) {
		if (null == rows) {
			rows = 10;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		Map<String, Object> map = sysUserManageService
				.querySysPermissionListMap(rows, pageNo, keyword);
		@SuppressWarnings("unchecked")
		List<SysPermissions> list = (List<SysPermissions>) map
				.get("list");
		Long total = (Long) map.get("total");
		Page<SysPermissions> sysUserPage = new Page<SysPermissions>();

		sysUserPage.setResult(list);
		sysUserPage.setTotalItems(total);
		sysUserPage.setPageNo(pageNo);
		sysUserPage.setPageSize(rows);
		model.addAttribute("sysPermissionPage", sysUserPage);

		// Page<SysPermission> sysPermissionPage =
		// sysUserManageService.querySysPermissionByPage(rows, pageNo, keyword,
		// null);
		// model.addAttribute("sysPermissionPage", sysPermissionPage);
		return "/sysuser/sysPermissionList";
	}

	@RequiresPermissions("systemManager:sysPermission:delete")
	@RequestMapping(value = "sysuser/sysPermissionDelete", method = RequestMethod.GET)
	public String sysPermissionDelete(String sysPermissionId) {
		sysUserManageService.deletePermission(sysPermissionId);
		return "redirect:/sysuser/sysPermissionList";
	}

	@RequiresPermissions("systemManager:sysPermission:edit")
	@RequestMapping(value = "sysuser/sysPermissionUpdate", method = RequestMethod.POST)
	public String sysPermissionUpdate(String id, String permission,
			String description, String available) {

		SysPermissions sysPermission = new SysPermissions();
		sysPermission.setId(id);
		sysPermission.setPermission(permission);
		sysPermission.setDescription(description);
		if ("true".equalsIgnoreCase(available))
			sysPermission.setAvailable(true);
		else
			sysPermission.setAvailable(false);
		sysUserManageService.updatePermission(sysPermission);

		return "redirect:/sysuser/sysPermissionList";
	}

	@RequiresPermissions("systemManager:sysPermission:add")
	@RequestMapping(value = "sysuser/sysPermissionAdd", method = RequestMethod.POST)
	public String sysPermissionAdd(String permission, String description,
			String available) {

		SysPermissions sysPermission = new SysPermissions();
		sysPermission.setPermission(permission);
		sysPermission.setDescription(description);
		if ("true".equalsIgnoreCase(available)) {
			sysPermission.setAvailable(true);
		} else {
			sysPermission.setAvailable(false);
		}
		sysUserManageService.createPermission(sysPermission);

		return "redirect:/sysuser/sysPermissionList";
	}
	@RequestMapping(value = "user/change_password", method = RequestMethod.GET)
	public String changePasswd(String newPassword,RedirectAttributes redirectAttributes){
		Subject subject = SecurityUtils.getSubject();
		String username = subject.getPrincipal().toString();
		SysUser user =  sysUserManageService.findByUserName(username);
		if(user != null){
			user.setPassword(newPassword);
			sysUserManageService.changePassword(user);
			redirectAttributes.addFlashAttribute("msg", "修改成功");
		}else{
			redirectAttributes.addFlashAttribute("msg", "修改失败");
		}
		
		return "redirect:/index";
	}
}
