<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Client Application Registration Form</title>
    <STYLE TYPE="text/css">
	<!--
	  input {font-family:verdana, arial, helvetica, sans-serif;font-size:20px;line-height:40px;}
	  H1 { text-align: center}
	  div.padded {  
         padding-left: 5em;  
      }   
	-->
</STYLE>
</head>
<body>
<H1>Client Application Registration Form</H1>
<br/>
     <form action="oauth2/registerClientApp" enctype="multipart/form-data" method="POST">
       <div class="padded">  
       <table>    
        <tr>
            <td><big><big><big>Application Name:</big></big></big></td>
            <td>
              <input type="text" name="appName" size="50" value="ClientApp"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td><big><big><big>Application Description:</big></big></big></td>
            <td>
              <input type="text" size="50" name="appDescription" 
                     value="Client App description"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td><big><big><big>Application URI:</big></big></big></td>
            <td>
              <input type="text" size="50" name="appURI" value="http://localhost:8080/client-app-server-1.0-SNAPSHOT/view-quote"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td><big><big><big>Application Redirect URI:</big></big></big></td>
            <td>
              <input type="text" size="50" name="appRedirectURI" value="http://localhost:8080/client-app-server-1.0-SNAPSHOT/view-quote"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>        
        <tr>
            <td>
              &nbsp;
            </td>
        </tr>
        </table>
        </div>
        <table align="center">
        <tr>
            <td colspan="2">
                <input type="submit" value="    Register Your Application    "/>
            </td>
        </tr>
        </table>
  </form>
 
  
</body>
</html>