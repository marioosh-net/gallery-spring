<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<!-- prev -->
<div class="right">
<c:if test="${apage == 1}">
<a href="#" class="inactive">&#171; prev</a>
</c:if>
<c:if test="${apage != 1}">
<a href="<t:context/>/index.html?a=${aid}&amp;pp=${ppage}&amp;p=${apage == 1 ? apage : apage - 1}">&#171; prev</a>
</c:if>

<!-- next -->
<c:if test="${apage == apagesCount}">
<a href="#" class="inactive">next &#187;</a>
</c:if>
<c:if test="${apage != apagesCount}">
<a href="<t:context/>/index.html?a=${aid}&amp;pp=${ppage}&amp;p=${apage == apagesCount ? apage : apage + 1}">next &#187;</a>
</c:if>
</div>
<div class="clear"></div>

<c:forEach items="${albums}" var="a">
	<div>
		<a href="<t:context/>/index.html?a=${a.id}&amp;p=${apage}">${a.name}</a>&#160;<a href="deletealbum.html?id=${a.id}">X</a>
	</div>
</c:forEach>
