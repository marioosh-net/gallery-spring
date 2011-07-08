<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<t:layout>

	<%-- LEFT: albums --%>
	<div class="left leftfixed">		
		
    	<div class="setsheader">
			<%-- <a href="<c:url value="/app/home"/>" style="color: #888888; text-decoration: none;">PHOTOSETS</a> --%>
			<div id="searchbox">
				<div class="left">
					<input type="text" name="search" id="search" value="" onchange="jQuery('#search-progress').show(); jQuery.get('<c:url value="/app/albums/1/"/>'+jQuery(this).val(), function(data){jQuery('#search-progress').hide(); jQuery('#albums').html(data); jQuery('#search').focus();jQuery('#search').select(); jQuery.post('<c:url value="/app/addsearch.html"/>', {phrase: jQuery('#search').val()}); });"/>&#160;
					<a href="#" onclick="jQuery('#search').trigger('onchange');"><spring:message code="button.search"/></a>
					|
					<a href="#" onclick="jQuery('#search').val(''); jQuery('#search').trigger('onchange');"><spring:message code="button.showAll"/></a>
					|
					<a href="#" onclick="jQuery('#searches').load('<c:url value="/app/searches"/>', function(){ jQuery('#searches').toggle('fast'); });"><spring:message code="button.expandSearches"/></a>
				</div>
				<div class="right">
					<img src="<c:url value="/images/ajax-loader6.gif"/>" id="search-progress" style="display: none;"/>
				</div><div class="clear"></div>
			</div>
			<div id="searches" style="display:none;">
			</div>
			
		</div>

		<%-- albums list --%>
		<div id="albums">
			<%@ include file="/WEB-INF/pages/albums.jsp" %>
			<%-- <div style="padding-left: 10px; padding-top: 10px;"><img src="<c:url value="/images/ajax.gif"/>"/>&#160;Loading...</div> --%>
		</div>		

	</div>
	
	
	<%-- RIGHT: covers / photos / etc --%>
	<div class="left rightfixed">
		<div id="header">
			<%-- logo --%>
			<div class="left" style="padding-top: 10px;">
				<a href="<c:url value="/app/home"/>" class="logoref" href=""><img src="<c:url value="/images/logo.png"/>"/></a>
			</div>
			
			<%-- user / logout --%>
			<div class="right" style="margin-top: 10px;">
				<span id="main-progress" style="display: none;"><img src="<c:url value="/images/ajax-loader5.gif"/>"/></span>
				<security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER">	
					<span class="username"><security:authentication property="principal.username" /></span>
					<a href="<c:url value="/app/logout"/>" ><spring:message code="button.logout"/></a>
				</security:authorize>
				<security:authorize ifNotGranted="ROLE_ADMIN, ROLE_USER">
					<%--<a href="#" class="modalInput" onclick="openModal(this); return false;" rel="#loginf"><spring:message code="button.login"/></a>--%>
					<a href="<c:url value="/app/login"/>"><spring:message code="button.login"/></a>
				</security:authorize>
				&#160;<a href="?lang=pl">PL</a>|<a href="?lang=en">EN</a>					
			</div>
			<div class="clear"></div>
			
			<%-- login form --%>	
			<t:modal id="loginf">
				<%@include file="/WEB-INF/templates/login.jsp" %>
			</t:modal>
			<c:if test="${!empty loginfail or !empty login}">
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
				<a href="#" title="<spring:message code='text.description.scan'/>" onclick="openModal(this); return false;" id="scan-trigger" class="modalInput modalInputClick" rel="#yesnoadmin" rev="loadingIcon('#scan-trigger'); openOverlay('#logmodal', null); jQuery.get('<c:url value="/app/scan.html?refresh=1"/>', function(data){/*openOverlay('#scaned', data);*/ jQuery('#scan-trigger').text('<spring:message code="button.scan"/>');})"><spring:message code="button.scan"/></a>
				<a href="<c:url value='/app/messages' />"><spring:message code="button.messages"/></a>				
				<t:modalyesno id="yesnoadmin">
					<spring:message code="text.areYouSure"/>
				</t:modalyesno>
				<t:modal id="scaned" text="true">
				</t:modal>
				
				<%-- testowe --%>
				<c:if test="${servername == 'localhost'}">
					<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputHref" rel="#yesnoadmin" rev="<c:url value="/app/load.html"/>">load</a>
					<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputHref" rel="#yesnoadmin" rev="<c:url value="/app/makepublic.html"/>">make public</a>
					<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputHref" rel="#yesnoadmin" rev="<c:url value="/app/unload.html"/>">unload</a>
				
					<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputHref" rel="#yesnotest" rev="<c:url value="/app/cleardb.html"/>">cleardb</a>
					<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputClick" rel="#yesnotest" rev="jQuery.get('<c:url value="/app/testalbum.html"/>', function(data){albums();covers();})">test album</a>
					<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputClick" rel="#yesnotest" rev="jQuery.get('<c:url value="/app/makephotos.html?count=10"/>', function(data){albums();covers();})">make 10 photos</a>
					<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputClick" rel="#yesnotest" rev="jQuery.get('<c:url value="/app/makephotos.html"/>', function(data){albums();covers();})">make photos</a>
					<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputClick" rel="#yesnotest" rev="jQuery.get('<c:url value="/app/shuffle.html"/>', function(data){if(data == '0') {albums();covers();}})">shuffle</a>
					<t:modalyesno id="yesnotest">
						<spring:message code="text.areYouSure"/>
					</t:modalyesno>	
				</c:if>
								
			</div>
		</security:authorize>
	
		<%-- thumbs --%>
		<div id="mini">
			<div id="photos">
				<%@ include file="/WEB-INF/pages/photos.jsp" %>
			</div>			
		</div>	

		<%-- footer --%>		
		<div id="footer-right">
			Inspired by <a href="http://minishowcase.net">minishowcase</a>. Powered by <a href="http://www.nickstakenburg.com/projects/lightview/">Lightview</a>&nbsp;|&nbsp;<a href="http://marioosh.net">marioosh.net</a><%-- &nbsp;|&nbsp;<a href="<t:server-https/><c:url value='/'/>">https</a>--%>
			<br/><spring:message code="text.photos_license"/>
			<%--<a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/3.0/"><img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-nc-nd/3.0/88x31.png" /></a><br />Page content is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/3.0/"><img class="middle" alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-nc-nd/3.0/80x15.png" /></a>--%>
			  
			<div class="left"></div>
			<div class="right"></div>
			<div class="clear"></div>
		</div>
		
		<%-- exif --%>
		<div id="exif-container">
			<div id="exif"></div>
		</div>
		
		<%-- admin log --%>
		<t:modal id="logmodal" close="true" wide="true" alignleft="true">
			<div id="logdiv" style="height: 200px; overflow: auto;">
				<pre id="log"></pre>
			</div>		
		</t:modal>
	</div>
	<div class="clear"></div>
</t:layout>
