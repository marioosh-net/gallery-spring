<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<c:forEach items="${albums}" var="album">
	<div>
		<a href="<t:context/>/index.html?a=${album.id}">${album.name}</a>&#160;<a href="deletealbum.html?id=${album.id}">X</a>
	</div>
</c:forEach>
