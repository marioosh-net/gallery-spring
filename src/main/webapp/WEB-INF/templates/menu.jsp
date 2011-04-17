<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<div class="left">
	<a href="<t:context/>/index.html"><spring:message code="menu.home"/></a>
</div>

<div class="right">
	<security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER">	
		<span class="username"><security:authentication property="principal.username" /></span>
		<a href="<t:context/>/logout.html" ><spring:message code="button.logout"/></a>
	</security:authorize>
	<security:authorize ifNotGranted="ROLE_ADMIN, ROLE_USER">
		<%@include file="/WEB-INF/templates/login.jsp" %>
	</security:authorize>
</div>

<div class="clear"></div>
