<%@ tag description="Modal Box" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<%@ attribute name="id" required="true" type="java.lang.String" %>
<%@ attribute name="style" required="false" type="java.lang.String" %>
<%@ attribute name="text" required="false" type="java.lang.Boolean" %>
<%@ attribute name="close" required="false" type="java.lang.Boolean" %>
<%@ attribute name="wide" required="false" type="java.lang.Boolean" %>
<%@ attribute name="alignleft" required="false" type="java.lang.Boolean" %>
<%-- confirm modal dialog --%>	
<div class="modal ${not empty wide ? 'modal-wide' : ''}" id="${id}" style="${style}">
	<div style="text-align: center; ${not empty alignleft ? 'text-align: left;' : ''}">
		<div>
			<jsp:doBody/>
			<c:if test="${not empty text}">
				<div class="modal-text">
				</div>
			</c:if>
			<c:if test="${not empty close}">
				<div style="margin-top: 5px;">
					<a class="close" href="#" onclick=""><span><spring:message code="button.close"/></span></a>
				</div>
			</c:if>
		</div>
	</div>
</div>
