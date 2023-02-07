<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OuterPage</title>
</head>
<body>
    <h2>외부 파일 2</h2>
    <%
    String newVar1 = "백제 온조왕";
    %>
    <ul>
        <li>page 속성 영역 : <%= pageContext.getAttribute("pAttr") %></li>    
        <li>request 속성 영역 : <%= request.getAttribute("rAttr") %></li>    
    </ul>
</body>
</html>