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

        <title></title>
        <script src="spicearraybuffer.min.js"></script> 
        <script src="enums.js"></script> 
        <script src="atKeynames.js"></script> 
        <script src="utils.min.js"></script> 
        <script src="png.min.js"></script> 
        <script src="lz.min.js"></script> 
        <script src="quic.min.js"></script> 
        <script src="bitmap.min.js"></script> 
        <script src="spicedataview.min.js"></script> 
        <script src="spicetype.min.js"></script> 
        <script src="spicemsg.min.js"></script> 
        <script src="wire.min.js"></script> 
        <script src="spiceconn.min.js"></script> 
        <script src="display.min.js"></script> 
        <script src="main.min.js"></script> 
        <script src="inputs.min.js"></script> 
        <script src="simulatecursor.min.js"></script>
        <script src="cursor.min.js"></script> 
        <script src="thirdparty/jsbn.js"></script>
        <script src="thirdparty/rsa.js"></script>
        <script src="thirdparty/prng4.js"></script>
        <script src="thirdparty/rng.js"></script>
        <script src="thirdparty/sha1.js"></script>
        <script src="ticket.min.js"></script>
        <link rel="stylesheet" type="text/css" href="spice.css" />
        <script>
            var host = null, port = null;
            var sc;

            function spice_error(e)
            {
                disconnect();
            }
  
            function connect()
            {
                var host, port, password, scheme = "ws://", uri;

                host = document.getElementById("host").value; 
                port = document.getElementById("port").value; 
                password = document.getElementById("password").value;


                if ((!host) || (!port)) {
                    console.log("must set host and port");
                    return;
                }

                if (sc) {
                    sc.stop();
                }

                uri = scheme + host + ":" + port;

                document.getElementById('connectButton').innerHTML = "Stop";
                document.getElementById('connectButton').onclick = disconnect;

                try
                {
                    sc = new SpiceMainConn({uri: uri, screen_id: "spice-screen", dump_id: "debug-div", 
                                message_id: "message-div", password: password, onerror: spice_error });
                }
                catch (e)
                {
                    alert(e.toString());
                    disconnect();
                }

            }

            function disconnect()
            {
                console.log(">> disconnect");
                if (sc) {
                    sc.stop();
                }
                document.getElementById('connectButton').innerHTML = "Start";
                document.getElementById('connectButton').onclick = connect;
                console.log("<< disconnect");
            }
        </script>

    </head>

    <body style="overflow: hidden;">

        <div id="login" style="display: none;">
            <span class="logo">SPICE</span>
            <label for="host">Host:</label> <input type='text' id='host' readonly="readonly" value='<%=host%>'> <!-- localhost -->
            <label for="port">Port:</label> <input type='text' id='port' readonly="readonly" value='<%=port%>'>
            <label for="password">Password:</label> <input type='password' id='password' readonly="readonly" value='<%=pwd%>'>
            <button id="connectButton" onclick="connect();">Start</button>
        </div>

        <div id="spice-area">
            <div id="spice-screen" class="spice-screen"></div>
        </div>

        <div id="message-div" class="spice-message" style="display: none;"></div>

        <div id="debug-div" style="display: none;">
        <!-- If DUMPXXX is turned on, dumped images will go here -->
        </div>
		<script type="text/javascript">
		connect();
		</script>
    </body>
</html>
