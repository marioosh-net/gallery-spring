<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<div class="left">
	<a href="<t:context/>/index.html"><spring:message code="menu.home"/></a>
	<c:if test="${album != null}">&#160;&#8250;&#160;<a href="">${album.name}</a></c:if>
</div>

<div class="right">
	<security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER">	
		<span class="username"><security:authentication property="principal.username" /></span>
		<a href="<t:context/>/logout.html" ><spring:message code="button.logout"/></a>
	</security:authorize>
	<security:authorize ifNotGranted="ROLE_ADMIN, ROLE_USER">
		
	</security:authorize>
</div>

<div class="clear"></div>
