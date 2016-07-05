<!--
   Copyright (C) 2012 by Jeremy P. White <jwhite@codeweavers.com>

   This file is part of spice-html5.

   spice-html5 is free software: you can redistribute it and/or modify
   it under the terms of the GNU Lesser General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   spice-html5 is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License
   along with spice-html5.  If not, see <http://www.gnu.org/licenses/>.

   --------------------------------------------------
    Spice Javascript client template.
    Refer to main.js for more detailed information
   --------------------------------------------------

-->

<%@page import="com.cloud.servlet.SpiceHtml5Param"%>
<%@page import="com.cloud.servlet.VerifyUtilBean"%>
<%
String host = "";
String port = "";
String pwd = "";

VerifyUtilBean bean = new VerifyUtilBean();
SpiceHtml5Param parm = bean.verifyQuery(request.getQueryString());

if (parm == null) {
// 	response.setStatus(400);
	response.setContentType("text/html");
	response.getWriter().print("Access Forbidden!");
	return;
}
host = parm.getHost();
port = parm.getPort();
pwd = parm.getPwd();

%>

<!doctype html>
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>通过客户端查看控制台</title>
			
        <script>
        function JsExecCmd(value) {
        	var  cmd = new ActiveXObject("WScript.Shell");
        	cmd.run("cmd.exe /k "+value);
        	cmd = null;
        }
        function run() {
        	var host = <%=host%>
    		var port = <%=port%>
    		var pwd = <%=pwd%>
    		var command = "cd C:/Program Files/VirtViewer/bin && remote-viewer.exe spice://"+host+":"+port;
        	JsExecCmd(command);
        }
        </script>

    </head>

    <body>
<%--     <% out.print("C:/Program Files/VirtViewer/bin/remote-viewer.exe spice://"+host+":"+port); %> --%>
               请在remote-viewer里面使用如下字符串查看控制台:
    <% out.print("spice://"+host+":"+port); %>
    <br>
                如果您没有安装remote-viewer,请下载安装<br>
     <a href="http://virt-manager.org/download/sources/virt-viewer/virt-viewer-x64-0.5.7.msi">64位机器请点击安装</a>
     <br>           
     <a href="http://virt-manager.org/download/sources/virt-viewer/virt-viewer-x86-0.5.7.msi">32位机器请点击安装</a>           
    </body>
</html>
