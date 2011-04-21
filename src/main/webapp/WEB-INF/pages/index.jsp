<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<t:layout>
	<div class="left leftfixed">		
		<%-- albums list --%>
		<a href="<t:context/>/index.html" style="color: #888888; text-decoration: none;"><div class="setsheader">PHOTOSETS</div></a>
		<div id="albums">
			<%@ include file="/WEB-INF/pages/albums.jsp" %>
			<%-- <div style="padding-left: 10px; padding-top: 10px;"><img src="images/ajax.gif"/>&#160;Loading...</div> --%>
		</div>		
	</div>
	
	
	<div class="left rightfixed" style="/*position: fixed; margin-left: 310px;*/">
		<div id="header">
				<div class="left" style="padding-top: 10px;">
					<a href="<t:context/>/index.html" class="logoref" href=""><img src="<t:context/>/images/logo.png"/></a>
				</div>
				<div class="right" style="margin-top: 10px;">
					<security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER">	
						<span class="username"><security:authentication property="principal.username" /></span>
						<a href="<t:context/>/logout.html" ><spring:message code="button.logout"/></a>
					</security:authorize>
					<security:authorize ifNotGranted="ROLE_ADMIN, ROLE_USER">
						<a href="#" class="modalInput" rel="#loginf"><spring:message code="button.login"/></a>
					</security:authorize>
					&#160;<a href="?lang=pl">PL</a>|<a href="?lang=en">EN</a>					
				</div>
				<div class="clear"></div>	
				<t:modal id="loginf">
					<%@include file="/WEB-INF/templates/login.jsp" %>
				</t:modal>
				<c:if test="${!empty param.loginfail}">
					<script type="text/javascript">
					jQuery(window).load(function () {
						openOverlay('#loginf');
					});
					</script>
				</c:if>	
		</div>
	
		<%-- admin global functions --%>
		<security:authorize ifAnyGranted="ROLE_ADMIN">
			<div id="main-funcs">
				<a href="#" class="modalInput modalInputHref" rel="#yesnoadmin" rev="<t:context/>/load.html">load</a>
				<a href="#" class="modalInput modalInputHref" rel="#yesnoadmin" rev="<t:context/>/makepublic.html">make public</a>
				<a href="#" class="modalInput modalInputHref" rel="#yesnoadmin" rev="<t:context/>/unload.html">unload</a>
				<a href="#" class="modalInput modalInputHref" rel="#yesnoadmin" rev="<t:context/>/cleardb.html">cleardb</a>
				<a href="#" class="modalInput modalInputClick" rel="#yesnoadmin" rev="jQuery.get('<t:context/>/testalbum.html', function(data){albums();covers();})">test album</a>
				<a href="#" class="modalInput modalInputClick" rel="#yesnoadmin" rev="jQuery.get('<t:context/>/makephotos.html?count=10', function(data){albums();covers();})">make 10 photos</a>
				<a href="#" class="modalInput modalInputClick" rel="#yesnoadmin" rev="jQuery.get('<t:context/>/makephotos.html', function(data){albums();covers();})">make photos</a>
				<a href="#" class="modalInput modalInputClick" rel="#yesnoadmin" rev="jQuery.get('<t:context/>/shuffle.html', function(data){if(data == '0') {albums();covers();}})">shuffle</a>				
			</div>
			<t:modalyesno id="yesnoadmin">
				<spring:message code="text.areYouSure"/>
			</t:modalyesno>
		</security:authorize>
	
		<%--
		<div id="menu">
			<%@include file="/WEB-INF/templates/menu.jsp" %>
		</div>
		--%>
		
		<div id="mini">
			<div id="photos">
				<%@ include file="/WEB-INF/pages/covers.jsp" %>
			</div>			
		</div>	
		
		<div id="footer-right">
			Inspired by <a href="http://minishowcase.net">minishowcase</a>. Powered by <a href="http://www.nickstakenburg.com/projects/lightview/">Lightview</a>&nbsp;|&nbsp;<a href="http://marioosh.net">marioosh.net</a>
			<div class="left"></div>
			<div class="right"></div>
			<div class="clear"></div>
			
		</div>
	</div>
	<div class="clear"></div>
</t:layout>