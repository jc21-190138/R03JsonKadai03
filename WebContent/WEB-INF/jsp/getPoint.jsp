<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    //int POINT = (int)request.getAttribute("POINT");
	String POINT = (String)request.getAttribute("POINT");
%>
{"POINT":3580}
{"POINT":<%= POINT %>}
