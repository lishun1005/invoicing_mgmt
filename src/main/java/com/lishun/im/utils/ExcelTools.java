package com.lishun.im.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ExcelTools {
	public static Logger logger=LoggerFactory.getLogger(ExcelTools.class);
	/**
	* Description: 获取导出excel工作表格对象 
	* @param list 数据集合
	* @param headerList 标题 [map.put("titele", "标题");]
	* @param excelName 导出excel名称
	* @param childListName 子列表名称：不存在就置为空
	* @returnHSSFWorkbook
	* @author lishun 
	* @date 2016年6月14日 下午2:54:52
	 */
	public static <T> HSSFWorkbook exportListExcel(List<T> list,Map<String, Object> headerMap,
			String excelName,String childListName) throws Exception {
		List<Map<String, Object>> dataList=toListMap(list);
		HSSFWorkbook wb = new HSSFWorkbook();/* 创建excel文档对象 */
		HSSFSheet sheet = wb.createSheet(excelName);/* 创建excel一个工作表格（sheet）：*/
		HSSFRow row = sheet.createRow((int) 0);/* 创建excel的行(首行标题)*/
		HSSFCellStyle style = wb.createCellStyle();/* excel的格式*/
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		if(headerMap!=null&&headerMap.size()>0){/* 设置标题*/
			Set<String> keyHeader = headerMap.keySet();
			int j = 0;
			for (String keys : keyHeader) {
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(String.valueOf(headerMap.get(keys)));
				cell.setCellStyle(style);
				sheet.setColumnWidth(j, 7500);/* 设置每列的宽度 */
				headerMap.put(keys, j);
				j++;
			}
		}
		if (StringUtils.isNoneBlank(childListName)) {/* 存在子列表 */
			return (HSSFWorkbook) exportChildListExcel(wb, dataList, headerMap,
					excelName,childListName);
		}
		if(dataList!=null){
			Integer rowIndex = 1;/* 计算行数 */
			Integer cellIndex=0;/* 列数 */
			String cellVal="";
			for (Map<String, Object> mapTemp : dataList) {/* 设置内容 */
				row = sheet.createRow(rowIndex);
				Set<String> keyVal = headerMap.keySet();
				for (String key : keyVal) {
					cellIndex=Integer.valueOf(String.valueOf(headerMap.get(key)));
					if(mapTemp.get(key)!=null){
						cellVal=String.valueOf(mapTemp.get(key));
					}else{
						cellVal="";
						logger.info("无法匹配:"+key+",集合元素不包含有该属性");
					}
					row.createCell(cellIndex).setCellValue(cellVal);
				}
				rowIndex++;
			}
		}
		return wb;
	}
	/**
	* Description:导出含有子列表的excel 
	* @param workbook  
	* @param list 数据集合
	* @param headerList 标题 [map.put("titele", "标题");]
	* @param excelName 导出excel名称
	* @param childListName 子列表名称
	* @return Workbook
	* @author lishun 
	* @date 2016年6月14日 下午3:00:44
	 */
	private static Workbook exportChildListExcel(Workbook workbook,
			List<Map<String, Object>> list, Map<String, Object> headerMap
			,String excelName,String childListName) throws Exception {

		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(0);
		Integer rowIndex = 1; /* 计算行数 */
		List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
		Set<String> childKey=null;
		for (Map<String, Object> map : list) {
			childList = (List) map.get(childListName);
			if(childList!=null){
				childList=toListMap(childList);
				for (Map<String, Object> mapChild : childList) {
					childKey=mapChild.keySet();
					row = sheet.createRow(rowIndex);
					Integer cellIndex=0;/* 列数 */
					String cellVal="";
					for (String key : headerMap.keySet()) {
						cellIndex=Integer.valueOf(String.valueOf(headerMap.get(key)));
						if(map.get(key)!=null){
							cellVal=String.valueOf(map.get(key));
						}else{
							if(mapChild.get(key)!=null){
								cellVal=String.valueOf(mapChild.get(key));
							}else{
								cellVal="";
								logger.info("无法匹配:"+key+",集合元素不包含有该属性");
							}
						}
						row.createCell(cellIndex).setCellValue(cellVal);
					}
					rowIndex++;
				}
				if (rowIndex > 1) {/* 若存在多个数据，就合并成一行 */
					if(childKey!=null){
						for (String key : headerMap.keySet()) {
							if(!childKey.contains(key)){/* 对不存在子列表的key值进行合并 */
								int cellIndex=Integer.valueOf(String.valueOf(headerMap.get(key)));
								sheet /* firstRow:从第几行开始; lastRow:到第几行结束; firstCol:从第几列开始;lastCol:到第几列结束 */
									.addMergedRegion(new CellRangeAddress(rowIndex- childList.size(), 
															 			   rowIndex - 1, cellIndex,cellIndex));
							}
						}
					}
				}
			}else{
				return exportListExcel(list, headerMap, excelName, null);/* 不存在子列表 */
			}
		}
		return workbook;
	}
	/**
	* Description: 把list转换成 List<Map<String, Object>>
	* @param listObj 
	* @return List
	* @author lishun 
	* @date 2016年6月14日 下午2:55:40
	 */
	public static <T> List<Map<String, Object>> toListMap(List<T> listObj)
			throws Exception {
		if (listObj.size() > 0) {
			if (listObj.get(0) instanceof Map) {
				return (List<Map<String, Object>>) listObj;
			}
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (T t : listObj) {
				Map<String, Object> temp = new HashMap<String, Object>();
				Class clazz = t.getClass();
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					PropertyDescriptor pd = new PropertyDescriptor(
							field.getName(), clazz);
					Object val = pd.getReadMethod().invoke(t);
					if(val==null){
						val="";
					}
					temp.put(field.getName(), val);
				}
				listMap.add(temp);
			}
			return listMap;
		}
		return null;
	}
}
