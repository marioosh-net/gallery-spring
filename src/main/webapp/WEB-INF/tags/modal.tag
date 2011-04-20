<%@ tag description="Body Wrapper" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<%@ attribute name="id" required="true" type="java.lang.String" %>
<%@ attribute name="style" required="false" type="java.lang.String" %>
<%-- confirm modal dialog --%>	
<div class="modal" id="${id}" style="${style}">
	<div style="text-align: center;">
		<div>
			<jsp:doBody/>
		</div>
	</div>
</div>
