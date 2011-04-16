<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<security:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
	<a href="/logout.html"><spring:message code="button.logout"/></a>
</security:authorize>
<security:authorize ifNotGranted="ROLE_USER,ROLE_ADMIN">
	<form:form cssClass="sform" id="jf" name="f" action="j_spring_security_check" method="post">
		<div class="msg">
			<c:if test="${!empty param.loginfail}"><span class="errors"><spring:message code="text.loginError"/></span></c:if>
		</div>
		<div class="fcell left">
			<div class="clabel"><spring:message code="label.login"/></div>
			<div class="cdata"><input type="text" id="username" name="j_username" value="" size="5"/></div>
		</div>
		<div class="fcell left">
			<div class="clabel"><spring:message code="label.password"/></div>
			<div class="cdata"><input type="password" name="j_password" value="" size="6" /></div>
		</div>
		
		<div class="fcell left">
			<div class="cdata" style="float:left;"><input type="checkbox" name="_spring_security_remember_me" style="width: 20px;"/></div>
			<div class="clabel" style="float:left;"><spring:message code="label.rememberMe"/></div>
			<div class="cmsgs"></div>
		</div>				
	
		<div class="buttons">	
			<a href="javascript:;" class="bu" onclick="jQuery('#jf').submit();"><span><spring:message code="button.login"/></span></a>
			<input type="submit" class="hiddensubmit"/>
		</div>
		
	</form:form>
</security:authorize>
