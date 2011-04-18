<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<t:layout>
	<div class="left leftfixed">
		<div id="mini">
			<c:if test="${param.a != null and param.a != 0}">
				<div id="photos">
					<%@ include file="/WEB-INF/pages/photos.jsp" %>
				</div>
			</c:if>
			<c:if test="${param.a == null or param.a == 0}">
				<div id="covers">
					<%@ include file="/WEB-INF/pages/covers.jsp" %>
				</div>
			</c:if>
		</div>
	</div>
	<div class="left rightfixed">
		<%@include file="/WEB-INF/templates/login.jsp" %>
		
		<security:authorize ifAnyGranted="ROLE_ADMIN">
			<div id="main-funcs" class="rightbox">
				<a href="<t:context/>/load.html">load</a>
				<a href="<t:context/>/unload.html">unload</a>
				<a href="<t:context/>/cleardb.html">cleardb</a>
				<a href="#" onclick="jQuery.get('<t:context/>/testalbum.html', function(data){albums();covers();})">test album</a>
				<a href="#" onclick="jQuery.get('<t:context/>/makephotos.html?count=10', function(data){albums();covers();})">make 10 photos</a>
				<a href="#" onclick="jQuery.get('<t:context/>/makephotos.html', function(data){albums();covers();})">make photos</a>
				<a href="#" onclick="jQuery.get('<t:context/>/shuffle.html', function(data){if(data == '0') {albums();covers();}})">shuffle</a>				
			</div>
		</security:authorize>
		
		<c:if test="${album != null}">
		<div id="album" class="rightbox">
			<div class="left">
				<img src="<t:context/>/p.html?type=2&amp;id=${album.id}"/>
			</div>
			<div class="left ainfo">
				<div class="album-name">${album.name}</div>
				<div class="album-date">${album.modDate}</div>
				<div>
					<security:authorize ifAnyGranted="ROLE_ADMIN">
						<a href="#" class="modalInput modalInputHref" rel="#yesno" rev="deletealbum.html?id=${album.id}"><img class="icon" src="images/list_remove_btn.gif"/><spring:message code="button.delete"/></a>
					</security:authorize>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		</c:if>
		
		<div id="albums" class="rightbox">
			<%@ include file="/WEB-INF/pages/albums.jsp" %>
			<!-- <div style="padding-left: 10px; padding-top: 10px;"><img src="images/ajax.gif"/>&#160;Loading...</div> -->
		</div>
	</div>
	<div class="clear"></div>
</t:layout>