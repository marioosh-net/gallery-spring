<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<div class="left">
	<a href="<t:context/>/index.html"><spring:message code="menu.home"/></a>
	<security:authorize ifAnyGranted="ROLE_USER">
		<a href="accounts.html"><spring:message code="menu.accounts"/></a>
		<a href="operations.html"><spring:message code="menu.operations"/></a>
	</security:authorize>
	<security:authorize ifAnyGranted="ROLE_ADMIN">
		<a href="texts.html"><spring:message code="menu.texts"/></a>
	</security:authorize>
</div>

<div class="right">
	<security:authorize ifAnyGranted="ROLE_ADMIN">	
		<span class="username"><security:authentication property="principal.username" /></span>
		<a href="<t:context/>/logout.html" ><spring:message code="button.logout"/></a>
	</security:authorize>
	<security:authorize ifAnyGranted="ROLE_USER">	
		<a class="username" href="<t:context/>/user/profile.html"><span class="username"><security:authentication property="principal.username" /></span></a>
		<a href="<t:context/>/logout.html" ><spring:message code="button.logout"/></a>
	</security:authorize>	
</div>

<div class="clear"></div>
