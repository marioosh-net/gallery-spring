<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<t:layout>
	<div class="left leftfixed">
		<div id="mini">
			<c:if test="${param.a != null}">
				<div id="photos">
					<%@ include file="/WEB-INF/pages/photos.jsp" %>
				</div>
			</c:if>
			<c:if test="${param.a == null}">
				<div id="covers">
					<%@ include file="/WEB-INF/pages/covers.jsp" %>
				</div>
			</c:if>
		</div>
	</div>
	<div class="left rightfixed">
		<a href="#" onclick="$.get('testalbum.html', function(data){albums();covers();})">test album</a>
		<a href="#" onclick="$.get('testphoto.html', function(data){albums();covers();})">test photo</a>
		<div id="albums">
			<%@ include file="/WEB-INF/pages/albums.jsp" %>
			<!-- <div style="padding-left: 10px; padding-top: 10px;"><img src="images/ajax.gif"/>&#160;Loading...</div> -->
		</div>
	</div>
	<div class="clear"></div>
</t:layout>