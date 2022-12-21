<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내장 객체 - Response</title>
</head>
<body>
<% 
String id = request.getParameter("user_id"); /* 1 */
String pwd = request.getParameter("user_pwd"); 

if (id.equalsIgnoreCase("must") && pwd.equalsIgnoreCase("1234")) { /* 2 */
    response.sendRedirect("ResponseWelcome.jsp"); /* 3 */
} else {
    request.getRequestDispatcher("ResponseMain.jsp?loginErr=1") /* 4 */
        .forward(request, response);              
}
%>
</body>
</html>