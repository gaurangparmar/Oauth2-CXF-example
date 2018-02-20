<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>User Registration Form</title>
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
<H1>User Registration Form</H1>
<br/>
     <form action="oauth2/registerUser" enctype="multipart/form-data" method="POST">
       <div class="padded">  
       <table>    
        <tr>
            <td><big><big><big>User Name:</big></big></big></td>
            <td>
              <input type="text" name="username" size="50" value="gaurang"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>        
        <tr>
            <td><big><big><big>Password:</big></big></big></td>
            <td>
              <input type="text" size="50" name="password" value="secret"/>
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
                <input type="submit" value="    Register User    "/>
            </td>
        </tr>
        </table>
  </form>
</body>
</html>