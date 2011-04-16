<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<% String servername = request.getServerName(); if(servername.equals("localhost")) { %>
<div style="position: absolute; right: 0px; margin: 5px; z-index: 1000; top: 0px; text-align: left;">
	<a href="#" class="debug_button" onclick="jQuery('#d2').toggle();jQuery('#d1').toggle();jQuery('#debug').toggle();"><span id="d1">DEBUG</span><span id="d2" style="display: none;">DEBUG</span></a>&#160;
	<div id="debug" style="display: none; margin-top: 5px; padding: 5px; background-color: #fafafa; border: 1px solid silver; z-index: 1000;">
		<form:form>		
			<a href="#" class="debug_button" onclick="jQuery('.debugel').toggle();">CONTENT</a>
			<div class="clear"></div>

			<div><b>&lt;security:authentication&gt;.principal:</b><security:authentication property="principal"/></div>
			<div>param[]:</div>
			<div>
			<c:forEach items="${param}" var="p">
				<div>${p}</div>
			</c:forEach>
			</div>
			<div>
			<a href="<t:context/>/user/testaccount.html">create test account</a>
			</div>
		</form:form>
	</div>
</div>
<% } %>