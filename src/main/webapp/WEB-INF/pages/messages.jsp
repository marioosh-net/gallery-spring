<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<t:layout>
	<div id="header">
		<%-- logo --%>
		<div class="left" style="padding-top: 10px;">
			<a href="<c:url value="/app/home"/>" class="logoref" href=""><img src="<c:url value="/images/logo.png"/>"/></a>
		</div>
	</div>
	
	<form id="fpl" action="${context}/app/save-messages" method="post">
		<div><span style="color: #fff;"><spring:message code="text.messages_pl"/></span>&#160;
		<a href="#" onclick="openModal(this); return false;" id="scan-trigger-alb" class="modalInput modalInputClick" rel="#yesnotexts" rev="jQuery('#fpl').submit();"><spring:message code="button.save"/></a></div>
		<div>
			<textarea name="text" rows="15" cols="80">${messagespl}</textarea>
			<input type="hidden" value="pl" name="savedlang">
		</div>
	</form>
	<span>${plpath}</span>
	
	<div style="height: 20px;"></div>
	
	<form id="fen" action="${context}/app/save-messages" method="post">
		<div><span style="color: #fff;"><spring:message code="text.messages_en"/></span>&#160;
		<a href="#" onclick="openModal(this); return false;" id="scan-trigger-alb" class="modalInput modalInputClick" rel="#yesnotexts" rev="jQuery('#fen').submit();"><spring:message code="button.save"/></a></div>
		<div>
			<textarea name="text" rows="15" cols="80">${messagesen}</textarea>
			<input type="hidden" value="en" name="savedlang">
		</div>		
	</form>
	<span>${enpath}</span>
	
	<t:modalyesno id="yesnotexts">
		<spring:message code="text.areYouSure"/>
	</t:modalyesno>
	
</t:layout> 
