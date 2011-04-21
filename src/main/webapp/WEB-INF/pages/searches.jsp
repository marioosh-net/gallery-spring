<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<c:if test="${empty searches}">
<spring:message code="text.noResults"/>
</c:if>
<c:forEach items="${searches}" var="search" varStatus="i">
	<div class="search"><a href="#" onclick="jQuery('#search').val(jQuery(this).text()); jQuery('#search').trigger('onchange');">${search.phrase}</a><span class="search-counter">(${search.counter})</span></div>
</c:forEach>
<div class="clear"></div>