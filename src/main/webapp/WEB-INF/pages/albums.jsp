<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<div class="frame-albums">
<c:forEach items="${albums}" var="a" varStatus="i">
	<a href="#" title="${a.name}" onclick="subalbums(${a.id},'#subalbums_${a.id}'); jQuery('.oneset').removeClass('selected'); jQuery('.oneset_${a.id}').addClass('selected'); jQuery('#exif').html(''); loading('#photos'); jQuery('#photos').load('<c:url value="/app/photos/${a.id}/1"/>');"><div class="oneset oneset_${a.id} ${a.id == param.a ? 'selected' : ''}" style="${a.id == aid ? 'background-color: #AAA;' : ''}">${a.shortName}<span class="counts">(${ac[i.index]})</span></div></a>
	<span class="subalbums" id="subalbums_${a.id}"/>
</c:forEach>
</div>
<%-- prev --%> 
<div class="left" style="margin-top: 3px;">
<%--
<c:if test="${apage == 1}">
<a href="#" class="inactive">&#171; prev</a>
</c:if>
--%>
<c:if test="${apage != 1}">
	<a href="#" onclick="
		loadingIcon(this); 
		jQuery('#albums').load('<c:url value="/app/albums/${apage == 1 ? apage : apage - 1}/${search}"/>');
		if(jQuery('#mode').val() == 'covers') {
			jQuery('#photos').load('<c:url value="/app/covers/${apage == 1 ? apage : apage - 1}/${search}"/>');
		}
	"><spring:message code="button.prevAlbum"/></a>
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
	<a href="#" onclick="
		loadingIcon(this); 
		jQuery('#albums').load('<c:url value="/app/albums/${apage == apagesCount ? apage : apage + 1}/${search}"/>');
		if(jQuery('#mode').val() == 'covers') { 
			jQuery('#photos').load('<c:url value="/app/covers/${apage == apagesCount ? apage : apage + 1}/${search}"/>');
		}
		"><spring:message code="button.nextAlbum"/></a>
	</c:if>
</div>
<div class="clear"></div>