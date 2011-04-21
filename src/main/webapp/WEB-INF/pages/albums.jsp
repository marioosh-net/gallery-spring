<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<c:forEach items="${albums}" var="a" varStatus="i">
	<%-- <a href="<t:context/>/index.html?a=${a.id}&amp;p=${apage}"><div class="oneset" style="${a.id == aid ? 'background-color: #AAA;' : ''}">${a.shortName}<span class="counts">(${ac[i.index]})</span></div></a> --%>
	<a href="#" onclick="loading('#photos'); jQuery('#photos').load('<t:context/>/photos.html?a=${a.id}');"><div class="oneset" style="${a.id == aid ? 'background-color: #AAA;' : ''}">${a.shortName}<span class="counts">(${ac[i.index]})</span></div></a>
	<%-- <security:authorize ifAnyGranted="ROLE_ADMIN">&#160;<a href="#" class="modalInput modalInputHref" rel="#yesno" rev="deletealbum.html?id=${a.id}"><img src="images/list_remove_btn.gif"/></a></security:authorize> --%>
</c:forEach>
<%-- prev --%> 
<div class="left" style="margin-top: 3px;">
<%--
<c:if test="${apage == 1}">
<a href="#" class="inactive">&#171; prev</a>
</c:if>
--%>
<c:if test="${apage != 1}">
<a href="#" onclick="loadingIcon(this); jQuery('#albums').load('albums.html?p=${apage == 1 ? apage : apage - 1}&amp;s=${param.s}');">&#171; prev</a>
</c:if>
</div>

<%-- next --%> 
<div class="right" style="margin-top: 3px;">
<%--
<c:if test="${apage == apagesCount}">
<a href="#" class="inactive">next &#187;</a>
</c:if>
--%>
<c:if test="${apage != apagesCount}">
<a href="#" onclick="loadingIcon(this); jQuery('#albums').load('albums.html?p=${apage == apagesCount ? apage : apage + 1}&amp;s=${param.s}');">next &#187;</a>
</c:if>
</div>
<div class="clear"></div>

	<t:modalyesno id="yesno">
		<spring:message code="text.confirmDeleteAlbum" />
	</t:modalyesno>
