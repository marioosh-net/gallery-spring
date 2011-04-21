<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<div class="left">
	<a href="<t:context/>/index.html"><spring:message code="menu.home"/></a>
	<c:if test="${album != null}">&#160;&#8250;&#160;<a href="#">${album.name}</a></c:if>
</div>

<div class="right">
</div>

<div class="clear"></div>
