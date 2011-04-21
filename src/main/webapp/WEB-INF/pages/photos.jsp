<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<security:authorize ifAnyGranted="ROLE_ADMIN">
<%-- album info --%>
<c:if test="${album != null}">
	<div id="album">
		<div class="left thumb">
			<img src="<t:context/>/p.html?type=2&amp;id=${album.id}"/>
		</div>
		<div class="left ainfo">
			<div class="album-name">${album.name}</div>
			<div class="album-date">${album.modDate}</div>
			<div>
				<security:authorize ifAnyGranted="ROLE_ADMIN">
					<img class="icon" src="images/key.png"/><a href="<t:context/>/visibility.html?id=${album.id}&v=PUBLIC"><spring:message code="button.publicAllPhotos"/></a>
					<img class="icon" src="images/key.png"/><a href="<t:context/>/visibility.html?id=${album.id}&v=USER"><spring:message code="button.privateAllPhotos"/></a>
					<a href="#" class="modalInput modalInputHref" rel="#delalb" rev="deletealbum.html?id=${album.id}"><img class="icon" src="images/list_remove_btn.gif"/><spring:message code="button.delete"/></a>
					<form:form modelAttribute="album" cssClass="sform" id="al" action="index.html">
						<form:hidden path="id"/>
						<form:hidden path="hash"/>
						<form:hidden path="path"/>
						<form:input path="name"/><br/>
						<form:select path="visibility">
							<form:options itemLabel="name"/>
						</form:select><br/>
						<img class="icon" src="images/save.png"/><a href="#" onclick="jQuery('#al').submit();"><spring:message code="button.save"/></a>
						<input type="submit" class="hiddensubmit"/>
					</form:form>
					<t:modalyesno id="delalb">
						<spring:message code="text.confirmDeleteAlbum" />
					</t:modalyesno>
				</security:authorize>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</c:if>
</security:authorize>

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

		
<!-- album name -->
<div id="menu">
<div class="left album-title">
	<c:if test="${album != null}"><a href="#">${album.name}</a></c:if>
</div>
<div class="right">
</div>
<div class="clear"></div>
</div>

<c:if test="${ppagesCount > 1}">
	<div class="pages">
		<c:forEach items="${ppages}" var="p" varStatus="i">
			<%--<c:if test="${i.index == 0}"><spring:message code="label.pages"/>&#160;</c:if>--%>
			<span><c:if test="${p[0] != ppage}"><a href="#" onclick="loading('#photos'); jQuery('#photos').load('<t:context/>/photos.html?a=${param.a}&amp;pp=${p[0]}');">[${p[0]}]</a></c:if><c:if test="${p[0] == ppage}"><span style="color: #fff;">[${p[0]}]</span></c:if></span>
		</c:forEach>
	</div>
</c:if>

<div class="photos">
	<div class="thumbs">
		<%--
		<div class="thumb_box">
			<c:if test="${ppage == 1}">
			<a href="#" class="inactive">
			</c:if>
			<c:if test="${ppage != 1}">
			<a href="<t:context/>/index.html?a=${aid}&amp;p=${apage}&amp;pp=${ppage == 1 ? ppage : ppage - 1}">
			</c:if>
				<div class="thumb prevp ${ppage == 1 ? 'inactive' : ''}" onmouseover="jQuery(this).addClass('over');" onmouseout="jQuery(this).removeClass('over');">
					<br>P<br>R<br>E<br>V<br>
				</div>
			</a>
		</div>
		--%>				
	
		<%-- before --%>
		<c:forEach items="${before}" var="p" varStatus="i">
			<a href="<t:context/>/p.html?type=0&amp;id=${p['id']}" class="lightview" rel="gallery[mygallery]" title="<a href='<t:context/>/p.html?type=0&amp;id=${p["id"]}' target='_blank'>${p['name']}</a>"></a>
		</c:forEach>
		<%-- before END--%>
		<c:forEach items="${photos}" var="p" varStatus="i">
			<c:if test="${i.index == 0}">
			</c:if>
		
			<div id="th${i.index}" class="thumb_box" style="<security:authorize ifAllGranted='ROLE_ADMIN'>height: auto;</security:authorize>">
				<a href="<t:context/>/p.html?type=0&amp;id=${p['id']}" class="lightview" rel="gallery[mygallery]" title="<a href='<t:context/>/p.html?type=0&amp;id=${p["id"]}' target='_blank'>${p['name']}</a>">
					<div id="t${i.index}" class="thumb" style="background-image: url(images/ajax-loader5.gif); background-position: 50% 50%; "></div>
					<div id="u${i.index}" class="thumb" style="float: none; display: none; background-image: url(<t:context/>/p.html?type=1&amp;id=${p['id']}); "></div>
					<img style="display: none;" src="<t:context/>/p.html?type=1&amp;id=${p['id']}" onload="jQuery('#t${i.index}').hide(); jQuery('#u${i.index}').show();">
				</a>
				<a href="<t:context/>/p.html?type=3&amp;id=${p['id']}" title="full size" target="_blank"><img class="full" src="images/n3.png" alt="full size"></a>
				
				<security:authorize ifAllGranted="ROLE_ADMIN">
					<div class="thumb_options">
						<div>
							<img src="images/key.png">&nbsp;
							<a id="v${i.index}" href="#" onclick="jQuery.get('changevisibility.html?id=${p['id']}',function(data){ jQuery('#v${i.index}').text(data); });" >${p['visibility'] == 0 ? 'public' : (p['visibility'] == 1 ? 'private' : (p['visibility'] == 2 ? 'admin' : ' '))}</a>
						</div>				
						<div>
							<img src="images/list_remove_btn.gif">&nbsp;
							<a href="#" class="modalInput modalInputClick" rev="jQuery.get('deletephoto.html?id=${p['id']}',function(data){if(data == '0'){ jQuery('#th${i.index}').remove(); }});" rel="#yesnophoto" ><spring:message code="button.delete"/></a>											
							<%-- <a href="#" class="modalInput modalInputHref" rev="deletephoto2.html?id=${p['id']}" rel="#yesnophoto" ><spring:message code="button.delete"/></a> --%>
						</div>						
					</div>
				</security:authorize>
			</div>
		
		</c:forEach>
		<%-- after --%>
		<c:forEach items="${after}" var="p" varStatus="i">
			<a href="<t:context/>/p.html?type=0&amp;id=${p['id']}" class="lightview" rel="gallery[mygallery]" title="<a href='<t:context/>/p.html?type=0&amp;id=${p["id"]}' target='_blank'>${p['name']}</a>"></a>		
		</c:forEach>
		<%-- after END --%>
				
		<t:modalyesno id="yesnophoto">
			<spring:message code="text.confirmDeletePhoto" />
		</t:modalyesno>
		
		<%--
		<div class="thumb_box">
			<c:if test="${ppage == ppagesCount}">
			<a href="#" class="inactive">
			</c:if>
			<c:if test="${ppage != ppagesCount}">
			<a href="<t:context/>/index.html?a=${aid}&amp;p=${apage}&amp;pp=${ppage == ppagesCount ? ppage : ppage + 1}">
			</c:if>
				<div class="thumb nextp ${ppage == ppagesCount or ppagesCount == 0 ? 'inactive' : ''}" onmouseover="jQuery(this).addClass('over');" onmouseout="jQuery(this).removeClass('over');">
					<br>N<br>E<br>X<br>T<br>
				</div>
			</a>
		</div>
		--%>
		
	</div>
	
	<%-- reszta fotek z albumu do slimboxa --%>
	
	<%-- 		
	<script type="text/javascript">
	slimboxstart();
	</script>
	--%>
	
	<div style="clear: both;"></div>
</div>

<c:if test="${ppagesCount > 1}">
	<div class="pages">
		<c:forEach items="${ppages}" var="p" varStatus="i">
			<%--<c:if test="${i.index == 0}"><spring:message code="label.pages"/>&#160;</c:if>--%>
			<span><c:if test="${p[0] != ppage}"><a href="#" onclick="loading('#photos'); jQuery('#photos').load('<t:context/>/photos.html?a=${param.a}&amp;pp=${p[0]}');">[${p[0]}]</a></c:if><c:if test="${p[0] == ppage}"><span style="color: #fff;">[${p[0]}]</span></c:if></span>
		</c:forEach>
	</div>
</c:if>

<script>
ready1();
</script>