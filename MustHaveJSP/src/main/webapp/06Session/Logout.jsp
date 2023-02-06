<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 방법1 : 회원인증정보 속성 삭제
session.removeAttribute("UserId");
session.removeAttribute("UserName");

// 방법2 : 모든 속성 한꺼번에 삭제  --> 세션 정보를 유지할 필요가 없어 부담이 줄어든다. 이 메서드 사용!
session.invalidate();

// 속성 삭제 후 페이지 이동
response.sendRedirect("LoginForm.jsp");
%>