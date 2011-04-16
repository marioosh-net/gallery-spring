<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<t:layout>
	<div class="left leftfixed">
		&#160;
	</div>
	<div class="left rightfixed">
		<a href="#" onclick="$.get('testalbum.html', function(data){albums();})">test album</a>
		<div id="albums">
			<div style="padding-left: 10px; padding-top: 10px;"><img src="images/ajax.gif"/>&#160;Loading...</div>
		</div>
	</div>
	<div class="clear"></div>
</t:layout>