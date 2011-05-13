<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<t:layout>
	<div id="header">
		<%-- logo --%>
		<div class="left" style="padding-top: 10px;">
			<a href="<c:url value="/index.html"/>" class="logoref" href=""><img src="<c:url value="/r/images/logo.png"/>"/></a>
		</div>
	</div>
	<div class="error-box">
		<div class="error-message">
			ERROR ${message}
		</div>
		<div>
			<a href="<c:url value="/index.html"/>"><spring:message code="page.home"/></a>
		</div>
	</div>
</t:layout> 
