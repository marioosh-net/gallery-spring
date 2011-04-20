<%@ tag description="Body Wrapper" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<%@ attribute name="id" required="true" type="java.lang.String" %>
<!-- confirm modal dialog -->	
<div class="modal" id="${id}">
	<div style="text-align: center;">
		<div>
			<jsp:doBody/>
		</div>
		<div class="buttons" style="margin: 0px auto; width: 130px; margin-top: 5px;">
		<a class="close" href="#"><span><spring:message code="button.no"/></span></a>	
		<a class="close yes" href="#" onclick=""><span><spring:message code="button.yes"/></span></a>
		</div>
	</div>
</div>
