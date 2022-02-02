<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String code = (String)request.getAttribute("code");
    int id = (int)request.getAttribute("id");
	String name = (String)request.getAttribute("name");
	int point = (int)request.getAttribute("point");
%>

[
{"ID":1,"OptName":"トッピング無料券","POINT":500},
{"ID":5,"OptName":"チャーハン無料券","POINT":1500},
{"CODE":"<%=code%>","ID":<%= id %>,"OptName":"<%= name %>","POINT":<%= point %>}
]
