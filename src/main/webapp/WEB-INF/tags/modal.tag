<%@ tag description="Modal Box" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<%@ attribute name="id" required="true" type="java.lang.String" %>
<%@ attribute name="style" required="false" type="java.lang.String" %>
<%@ attribute name="text" required="false" type="java.lang.Boolean" %>
<%-- confirm modal dialog --%>	
<div class="modal" id="${id}" style="${style}">
	<div style="text-align: center;">
		<div>
			<jsp:doBody/>
			<c:if test="${not empty text}">
				<div class="modal-text">
				</div>
			</c:if>
		</div>
	</div>
</div>
