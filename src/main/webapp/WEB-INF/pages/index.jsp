<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<t:layout>
	<div class="left leftfixed">		
		
    	<div class="setsheader">
			<%-- <a href="<t:context/>/index.html" style="color: #888888; text-decoration: none;">PHOTOSETS</a> --%>
			<div id="searchbox">
				<div class="left">
					<input type="text" name="search" id="search" value="" onchange="jQuery('#search-progress').show(); jQuery.get('albums.html?s='+jQuery(this).val(), function(data){jQuery('#search-progress').hide(); jQuery('#albums').html(data); jQuery('#search').focus();jQuery('#search').select(); jQuery.post('addsearch.html', {phrase: jQuery('#search').val()}); });"/>&#160;
					<a href="#" onclick="jQuery('#search').trigger('onchange');"><spring:message code="button.search"/></a>
					|
					<a href="#" onclick="jQuery('#search').val(''); jQuery('#search').trigger('onchange');"><spring:message code="button.showAll"/></a>
					|
					<a href="#" onclick="jQuery('#searches').load('searches.html', function(){ jQuery('#searches').toggle('fast'); });">&#187;</a>
				</div>
				<div class="right">
					<img src="images/ajax-loader6.gif" id="search-progress" style="display: none;"/>
				</div><div class="clear"></div>
			</div>
		</div>
		<div id="searches" style="display:none;">
		</div>

		<%-- albums list --%>
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
					<span id="main-progress" style="display: none;"><img src="<t:context/>/images/ajax-loader5.gif"/></span>
					<security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER">	
						<span class="username"><security:authentication property="principal.username" /></span>
						<a href="<t:context/>/logout.html" ><spring:message code="button.logout"/></a>
					</security:authorize>
					<security:authorize ifNotGranted="ROLE_ADMIN, ROLE_USER">
						<a href="#" class="modalInput" onclick="openModal(this); return false;" rel="#loginf"><spring:message code="button.login"/></a>
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
				<a href="#" onclick="openModal(this); return false;" id="scan-trigger" class="modalInput modalInputClick" rel="#yesnoadmin" rev="loadingIcon('#scan-trigger'); jQuery.get('<t:context/>/scan.html', function(data){openOverlay('#scaned', data); jQuery('#scan-trigger').text('<spring:message code="button.scan"/>');})"><spring:message code="button.scan"/></a>
				<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputHref" rel="#yesnoadmin" rev="<t:context/>/load.html">load</a>
				<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputHref" rel="#yesnoadmin" rev="<t:context/>/makepublic.html">make public</a>
				<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputHref" rel="#yesnoadmin" rev="<t:context/>/unload.html">unload</a>
				<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputHref" rel="#yesnoadmin" rev="<t:context/>/cleardb.html">cleardb</a>
				<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputClick" rel="#yesnoadmin" rev="jQuery.get('<t:context/>/testalbum.html', function(data){albums();covers();})">test album</a>
				<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputClick" rel="#yesnoadmin" rev="jQuery.get('<t:context/>/makephotos.html?count=10', function(data){albums();covers();})">make 10 photos</a>
				<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputClick" rel="#yesnoadmin" rev="jQuery.get('<t:context/>/makephotos.html', function(data){albums();covers();})">make photos</a>
				<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputClick" rel="#yesnoadmin" rev="jQuery.get('<t:context/>/shuffle.html', function(data){if(data == '0') {albums();covers();}})">shuffle</a>				
			</div>
			<t:modalyesno id="yesnoadmin">
				<spring:message code="text.areYouSure"/>
			</t:modalyesno>
			<t:modal id="scaned" text="true">
			</t:modal>
			
		</security:authorize>
	
		<%--
		<div id="menu">
			<%@include file="/WEB-INF/templates/menu.jsp" %>
		</div>
		--%>
		
		<div id="mini">
			<div id="photos">
				<%@ include file="/WEB-INF/pages/photos.jsp" %>
			</div>			
		</div>	
		
		<div id="footer-right">
			Inspired by <a href="http://minishowcase.net">minishowcase</a>. Powered by <a href="http://www.nickstakenburg.com/projects/lightview/">Lightview</a>&nbsp;|&nbsp;<a href="http://marioosh.net">marioosh.net</a>&nbsp;|&nbsp;<a href="<t:server-https/><t:context/>">https</a>
			<div class="left"></div>
			<div class="right"></div>
			<div class="clear"></div>
			
		</div>
		
		<%-- exif --%>
		<div id="exif-container">
			<div id="exif"></div>
		</div>
	</div>
	<div class="clear"></div>
</t:layout>
