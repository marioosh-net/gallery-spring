<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<c:forEach items="${albums}" var="album">
	<div>
		<a href="#">${album.name}</a>&#160;<a href="#" onclick="$.get('deletealbum.html?id=${album.id}',function(){albums();})">X</a>
	</div>
</c:forEach>
