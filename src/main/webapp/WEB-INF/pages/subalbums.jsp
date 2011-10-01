<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<c:forEach items="${subalbums}" var="a" varStatus="i">
	<a href="#" title="${a.name}" onclick="subalbums(${a.id},'#subalbums_${a.id}'); jQuery('.oneset').removeClass('selected'); jQuery('.oneset_${a.id}').addClass('selected'); jQuery('#exif').html(''); loading('#photos'); jQuery('#photos').load('<c:url value="/app/photos/${a.id}/1"/>');"><div class="oneset oneset_${a.id} ${a.id == param.a ? 'selected' : ''}" style="${a.id == aid ? 'background-color: #AAA;' : ''}">${a.shortName}<span class="counts">(${ac[i.index][0]})</span><security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER"><span class="counts pub"><c:if test="${ac[i.index][1] > 0}">(${ac[i.index][1]})</span></c:if></security:authorize></div></a>
	<span class="subalbums subalbums2" id="subalbums_${a.id}"/>
</c:forEach>
