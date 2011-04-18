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
		<!-- <%@include file="/WEB-INF/templates/login.jsp" %> -->
		<a href="#" class="modalInput" rev="" rel="#loginf" ><spring:message code="button.login"/></a>
		<t:modal id="loginf">
			<%@include file="/WEB-INF/templates/login.jsp" %>
			<!-- <a class="close" href="#"><span><spring:message code="button.close"/></span></a> -->
		</t:modal>
		<c:if test="${!empty param.loginfail}">
			<script type="text/javascript">
			jQuery(window).load(function () {
				openOverlay('#loginf');
			});
			</script>
		</c:if>	
	</security:authorize>
</div>

<div class="clear"></div>
