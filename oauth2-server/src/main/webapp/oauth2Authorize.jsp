<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*,javax.servlet.http.HttpServletRequest,org.apache.cxf.rs.security.oauth2.common.OAuthAuthorizationData,org.apache.cxf.rs.security.oauth2.common.Permission" %>

<%
    OAuthAuthorizationData data = (OAuthAuthorizationData)request.getAttribute("oauthauthorizationdata");
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="cache-control" content="no-store" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
	<meta http-equiv="pragma" content="no-cache" />
    <title>Third Party Authorization Form</title>
    <STYLE TYPE="text/css">
	<!--
	  input,button {font-family:verdana, arial, helvetica, sans-serif;font-size:20px;line-height:40px;} 
	-->
</STYLE>
<title align="center">Third Party Authorization Form</title>
</head>
<body>
Welcom, <%= data.getEndUserName()%> to Third Party Authorization Form
<table align="center">
       <tr align="center">
                <td>
                    <form action="<%= data.getReplyTo() %>" method="POST">
                    	<input type="hidden" name="response_type" value="code"/>
                        <input type="hidden" name="client_id"
                               value="<%= data.getClientId() %>"/>
                        <input type="hidden" name="state"
                               value="<%= data.getState() %>"/>
                        <input type="hidden" name="scope"
                               value="<%= data.getProposedScope() %>"/>
                        <input type="hidden" name="redirect_uri"
                               value="<%= data.getRedirectUri() %>"/>              
                        <input type="hidden"
                               name="<%= org.apache.cxf.rs.security.oauth2.utils.OAuthConstants
                                   .SESSION_AUTHENTICITY_TOKEN %>"
                               value="<%= data.getAuthenticityToken() %>"/>
                        <p><big><big><big><%= data.getApplicationName() %><br/>(<%= data.getApplicationDescription() %>)</big></big></big>
                        <br/>
                        <br/></p>
                        <big><big>requests the following permissions:<big/></big>
                        <p/>
                        <table> 
                            <%
                               for (Permission perm : data.getPermissions()) {
                            %>
                               <tr>
                                <td>
                                  <input type="checkbox" 
                                    <%
                                      if (perm.isDefault()) {
                                    %>
                                    disabled="disabled"
                                    <%
                                      }
                                    %> 
                                    checked="checked"
                                    name="<%= perm.getPermission()%>_status" 
                                    value="allow"
                                  ><big><big><%= perm.getDescription() %></big></big></input>
                                    <%
                                      if (perm.isDefault()) {
                                    %>
                                    <input type="hidden" name="<%= perm.getPermission()%>_status" value="allow" />
                                    <%
                                      }
                                    %>
                                </td>
                               </tr>
                            <%   
                               }
                            %> 
                        </table>    
                        <br/></p>
                        <button name="<%= org.apache.cxf.rs.security.oauth2.utils.OAuthConstants
                            .AUTHORIZATION_DECISION_KEY %>"
                                type="submit"
                                value="<%= org.apache.cxf.rs.security.oauth2.utils.OAuthConstants
                                    .AUTHORIZATION_DECISION_ALLOW %>">
                            OK
                        </button>
                        <button name="<%= org.apache.cxf.rs.security.oauth2.utils.OAuthConstants
                            .AUTHORIZATION_DECISION_KEY %>"
                                type="submit"
                                value="<%= org.apache.cxf.rs.security.oauth2.utils.OAuthConstants
                                    .AUTHORIZATION_DECISION_DENY %>">
                            No,thanks
                        </button>
                        
                    </form>
                </td>
            </tr>
        </table>
    
</body>
</html>