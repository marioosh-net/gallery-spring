<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<a href="<t:context/>/index.html?a=${aid}&amp;pp=${ppage}&amp;p=${apage - 1}">prev</a>
<c:forEach items="${albums}" var="a">
	<div>
		<a href="<t:context/>/index.html?a=${a.id}">${a.name}</a>&#160;<a href="deletealbum.html?id=${a.id}">X</a>
	</div>
</c:forEach>
<a href="<t:context/>/index.html?a=${aid}&amp;pp=${ppage}&amp;p=${apage + 1}">next</a>
