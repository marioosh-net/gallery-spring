<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<input type="hidden" id="mode" value="photos"/>
<input type="hidden" id="album_id" value="${album != null ? album.id : 0}"/>
<!-- admin tools -->
<security:authorize ifAnyGranted="ROLE_ADMIN">
	<%-- album info --%>
	<c:if test="${album != null}">
		<div id="album">
			<div class="left thumb">
				<img src="<c:url value="/app/a/${album.id}/cover"/>"/>
			</div>
			<div class="left ainfo">
				<div class="album-name">${album.name}</div>
				<div class="album-path">${settings.rootPath}${utilService.separatorChar}${album.path}</div>
				<div class="album-date">${album.modDate}</div>
				<div>
					<security:authorize ifAnyGranted="ROLE_ADMIN">
						<div class="album-buttons">
						<img class="icon middle" width="16" height="16" src="<c:url value="/images/key2.png"/>"/><a href="#" onclick="openModal(this); return false;" class="modalInput modalInputClick" rel="#delalb" rev="loadingMain(); jQuery.get('<c:url value="/app/visibility.html?id=${album.id}&v=PUBLIC"/>', function(data){if(data == '0') { photos('${aid}','${ppage}'); } })"><spring:message code="button.publicAllPhotos"/></a>
						<img class="icon middle" width="16" height="16" src="<c:url value="/images/key2.png"/>"/><a href="#" onclick="openModal(this); return false;" class="modalInput modalInputClick" rel="#delalb" rev="loadingMain(); jQuery.get('<c:url value="/app/visibility.html?id=${album.id}&v=USER"/>', function(data){if(data == '0') { photos('${aid}','${ppage}'); } })"><spring:message code="button.privateAllPhotos"/></a>
						<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputHref" rel="#delalb" rev="/app/deletealbum.html?id=${album.id}"><img class="icon middle" height="16" width="16" src="<c:url value="/images/delete.png"/>"/><spring:message code="button.delete"/></a><br/>
						<a href="#" onclick="openModal(this); return false;" id="scan-trigger-alb" class="modalInput modalInputClick" rel="#yesnoalbum" rev="loadingIcon('#scan-trigger-alb'); jQuery.get('<c:url value="/app/scan.html?refresh=1"/>', { path: '${album.path}'}, function(data){openOverlay('#scaned', data); jQuery('#scan-trigger-alb').text('<spring:message code="button.scan"/>');})"><spring:message code="button.scan"/></a>
						<a href="#" onclick="openModal(this); return false;" id="reload-trigger" class="modalInput modalInputClick" rel="#yesnoalbum" rev="loadingIcon('#reload-trigger'); jQuery.get('<c:url value="/app/reload.html"/>', { id: '${album.id}'}, function(data){openOverlay('#scaned', data); jQuery('#reload-trigger').text('<spring:message code="button.reload"/>');})"><spring:message code="button.reload"/></a>
						<a href="#" onclick="openModal(this); return false;" id="refresh-trigger" class="modalInput modalInputClick" rel="#yesnoalbum" rev="loadingIcon('#refresh-trigger'); jQuery.get('<c:url value="/app/refresh.html"/>', { id: '${album.id}'}, function(data){if(data == '0') {openOverlay('#scaned', 'OK');} else {openOverlay('#scaned', 'Error');} jQuery('#refresh-trigger').text('<spring:message code="button.refresh"/>');})"><img class="middle" src="<c:url value="/images/refresh.png"/>" height="16" width="16"/><spring:message code="button.refresh"/></a>
						</div>
						<form:form modelAttribute="album" cssClass="sform" id="al" action="${context}/app/home">
							<form:hidden path="id"/>
							<form:hidden path="hash"/>
							<form:hidden path="path"/>
							<form:input path="name"/><br/>
							<form:select path="visibility">
								<form:options itemLabel="name"/>
							</form:select><br/>
							<img class="icon middle" width="16" height="16" src="<c:url value="/images/save.png"/>"/><%--<a href="#" onclick="jQuery('#al').submit();"><spring:message code="button.save"/></a>--%>
							<a href="#" onclick="jQuery.post('<c:url value="/app/savealbum.html"/>', jQuery('#al').serialize(), function(data){ if(data == '0') {openOverlay('#saved');} else {openOverlay('#error');} });" ><spring:message code="button.save"/></a>
							<input type="submit" class="hiddensubmit"/>
						</form:form>
						<t:modalyesno id="delalb">
							<spring:message code="text.confirmDeleteAlbum" />
						</t:modalyesno>
						<t:modalyesno id="yesnoalbum">
							<spring:message code="text.areYouSure"/>
						</t:modalyesno>
						
						<t:modal id="saved">
							<spring:message code="text.albumSaved" />
						</t:modal>
						<t:modal id="error">
							<spring:message code="text.error" />
						</t:modal>						
					</security:authorize>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</c:if>
</security:authorize>
		
<!-- album name -->
<div id="photos-header">
	<div class="left album-title">
		<c:if test="${album != null}"><a href="#" onclick="loadingIcon(this); jQuery('#photos').load('<c:url value="/app/covers"/>'); jQuery('#albums').load('<c:url value="/app/albums"/>');"><spring:message code="button.albums"/></a>&#160;&#187;&#160;<%--<a href="#" onclick="loadingIcon(this); jQuery('#photos').load('<c:url value="/app/photos/${aid}/1"/>');">${album.name}</a>--%><a href="<c:url value="/app/home/${aid}/${ppage}"/>" >${album.name}</a></c:if>
	</div>
	<div class="right">
	</div>
	<div class="clear"></div>
</div>

<c:if test="${ppagesCount > 1}">
	<div class="pages">
		<c:forEach items="${ppages}" var="p" varStatus="i">
			<%--<c:if test="${i.index == 0}"><spring:message code="label.pages"/>&#160;</c:if>--%>
			<div><c:if test="${p[0] != ppage}"><a href="#" onclick="loadingIcon(this); jQuery('#photos').load('<c:url value="/app/photos/${aid}/${p[0]}"/>'); return false;">[${p[0]}]</a></c:if><c:if test="${p[0] == ppage}"><span style="color: #fff;">[${p[0]}]</span></c:if></div>
		</c:forEach>
	</div>
	<div class="clear"></div>
</c:if>

<div class="photos">
	<div class="thumbs">
	
		<%-- before --%>
		<c:forEach items="${before}" var="p" varStatus="i">
			<a href="<c:url value="/app/p/${p['id']}/resized"/>" class="lightview" rel="gallery[mygallery]" title="<a href='<c:url value="/app/p/${p['id']}/original"/>' target='_blank'>${p['name']}</a>"></a>
		</c:forEach>
		
		<%-- thumbs --%>
		<c:if test="${empty photos}"><div class="no-results margin3">[<spring:message code="text.noPhotos"/>]</div></c:if>
		<c:forEach items="${photos}" var="p" varStatus="i">
			<c:if test="${i.index == 0}"></c:if>
			<div id="th${i.index}" class="thumb_box" style="<security:authorize ifAllGranted='ROLE_ADMIN'>height: auto; ${p['visibility'] == 0 ? 'background-color: #63773c;' : ''}</security:authorize>">
				<a id="x${p['id']}" href='<c:url value="/app/p/${p['id']}/resized"/>' class="lightview" rel="gallery[mygallery]" title="<a href='<c:url value="/app/p/${p['id']}/original"/>' target='_blank'>${p['name']}</a>">
					<div id="t${i.index}" class="thumb" style="background-image: url(<c:url value='/images/ajax-loader5.gif'/>); background-position: 50% 50%; "></div>
					<div id="u${p['id']}" class="thumb ttip" onmouseover="exif(${p['id']});" style="float: none; display: none; background-image: url(<c:url value='/app/p/${p["id"]}/thumb'/>); "></div>
					<img style="display: none;" src="<c:url value="/app/p/${p['id']}/thumb"/>" onload="jQuery('#t${i.index}').hide(); jQuery('#u${p['id']}').show();">
				</a>
				<a href="<c:url value="/app/p/${p['id']}/resized"/>" title="<spring:message code="button.openInNewWindow"/>" target="_blank"><img class="full" src="<c:url value="/images/n3.png"/>" alt="full size"></a>

				<%-- admin funcs --%>				
				<security:authorize ifAllGranted="ROLE_ADMIN">
					<div class="thumb_options">
						<div>
							<img class="middle" width="16" height="16" src="<c:url value="/images/key2.png"/>">&#160;<a id="v${i.index}" href="#" style="${p['visibility'] == 0 ? 'color: #9fee00; font-weight: bold;' : ''}" onclick="
							jQuery.get('<c:url value="/app/changevisibility.html?id=${p['id']}"/>',function(data){ 
								jQuery('#v${i.index}').text(data); 
								if(data == 'public') {
									jQuery('#th${i.index}').css('background-color','#63773c');
									jQuery('#v${i.index}').attr('style','color: #9fee00; font-weight: bold;');
								} else {
									jQuery('#th${i.index}').css('background-color','#000');
									jQuery('#v${i.index}').attr('style','');
								}
							}); return false;" >${p['visibility'] == 0 ? 'public' : (p['visibility'] == 1 ? 'private' : (p['visibility'] == 2 ? 'admin' : ' '))}</a>
						</div>				
						<div>
							<img class="middle" height="16" width="16" src="<c:url value="/images/delete.png"/>">&#160;<a href="#" onclick="openModal(this); return false;" class="modalInput modalInputClick" rev="jQuery.get('<c:url value="/app/deletephoto.html?id=${p['id']}"/>',function(data){if(data == '0'){ jQuery('#th${i.index}').remove(); }});" rel="#yesnophoto" ><spring:message code="button.delete"/></a><br/>											
							<img class="middle" src="<c:url value="/images/rotatel.png"/>" height="16" width="16"/>&#160;<a href="#"  onclick="jQuery.get('<c:url value="/app/rotate.html?id=${p['id']}"/>&amp;left=1',function(data){if(data == '0'){ jQuery('#u${p['id']}').css('background-image', 'url(<c:url value="/app/p/${p['id']}/thumb."/>'+(new Date()).getTime()+')'); jQuery('#x${p['id']}').attr('href', '<c:url value="/app/p/${p['id']}/resized."/>'+(new Date()).getTime()); }}); return false;" ><spring:message code="button.rotateLeft"/></a><br/>
							<img class="middle" src="<c:url value="/images/rotater.png"/>" height="16" width="16"/>&#160;<a href="#"  onclick="jQuery.get('<c:url value="/app/rotate.html?id=${p['id']}"/>&amp;left=0',function(data){if(data == '0'){ jQuery('#u${p['id']}').css('background-image', 'url(<c:url value="/app/p/${p['id']}/thumb."/>'+(new Date()).getTime()+')'); jQuery('#x${p['id']}').attr('href', '<c:url value="/app/p/${p['id']}/resized."/>'+(new Date()).getTime()); /*hrefDate('#x${p['id']}');*/ }}); return false;" ><spring:message code="button.rotateRight"/></a><br/>
							<img class="middle" src="<c:url value="/images/palette.png"/>" height="16" width="16"/>&#160;<a target="_blank" href="<c:url value="/app/palette.html?id=${p['id']}"/>"><spring:message code="button.palette"/></a><br/>
							<%-- <img class="middle" src="<c:url value="/images/picnik.png"/>" height="16" width="16"/>&#160;<a target="_blank" href="http://www.picnik.com/service/?_apikey=224466f6d30d0e0887e24bfb017c971d&_export=<t:server/><c:url value='/app/picnik/${p['id']}'/>&_export_method=POST&_export_field=file&_export_title=save&_import=<t:server/><c:url value='/app/p2/${p['hash']}'/>${hash2}&_redirect=<t:server/><c:url value='/app/p2/${p['hash']}${hash2}'/>"><spring:message code="button.picnik"/>-push</a> --%>
							<img class="middle" src="<c:url value="/images/picnik.png"/>" height="16" width="16"/>&#160;<a target="_blank" href="http://www.picnik.com/service/?_apikey=224466f6d30d0e0887e24bfb017c971d&_export=<t:server/><c:url value='/app/picnik2/${p["id"]}'/>&_export_agent=browser&_export_field=file&_export_title=save&_import=<t:server/><c:url value='/app/p2/${p["hash"]}${hash2}'/>"><spring:message code="button.picnik"/></a>
							<%--<br/><a href="<c:url value="/app/exif2/${p['id']}/1"/>" target="_blank">EXIF</a>--%>
							<br/><img class="middle" src="<c:url value="/images/lupka.png"/>" height="16" width="16"/>&#160;<a href="<c:url value="/app/exif3/${p['id']}"/>" target="_blank">EXIF [exiftool]</a>
							<br/><img class="middle" src="<c:url value="/images/refresh.png"/>" height="16" width="16"/>&#160;<a href="#" id="ref_${p['id']}" onclick="openModal(this); return false;" class="modalInput modalInputClick" rev="loadingIcon('#ref_${p['id']}'); jQuery.get('<c:url value="/app/refreshone.html?id=${p['id']}"/>',function(data){ jQuery('#ref_${p['id']}').text('<spring:message code="button.refreshOne"/>'); if(data == '0'){  jQuery('#u${p['id']}').css('background-image', 'url(<c:url value="/app/p/${p['id']}/thumb."/></img>'+(new Date()).getTime()+')'); jQuery('#x${p['id']}').attr('href', '<c:url value="/app/p/${p['id']}/resized."/>'+(new Date()).getTime());   }}); return false;" rel="#yesnorefresh" ><spring:message code="button.refreshOne"/></a>
							<%-- <a href="#" onclick="openModal(this); return false;" class="modalInput modalInputHref" rev="<c:url value="/app/deletephoto2.html?id=${p['id']}"/>" rel="#yesnophoto" ><spring:message code="button.delete"/></a> --%>
						</div>						
					</div>
				</security:authorize>
			</div>
		</c:forEach>
		
		<%-- after --%>
		<c:forEach items="${after}" var="p" varStatus="i">
			<a href='<c:url value="/app/p/${p['id']}/resized"/>' class="lightview" rel="gallery[mygallery]" title="<a href='<c:url value="/app/p/${p['id']}/original"/>' target='_blank'>${p['name']}</a>"></a>		
		</c:forEach>
		
		<security:authorize ifAllGranted="ROLE_ADMIN">
			<t:modalyesno id="yesnophoto">
				<spring:message code="text.confirmDeletePhoto" />
			</t:modalyesno>
			<t:modalyesno id="yesnorefresh">
				<spring:message code="text.confirmRefreshPhoto" />
			</t:modalyesno>
		</security:authorize>
		
		<%--
		<div class="thumb_box">
			<c:if test="${ppage == ppagesCount}">
			<a href="#" class="inactive">
			</c:if>
			<c:if test="${ppage != ppagesCount}">
			<a href="<c:url value="/app/home?a=${aid}&amp;p=${apage}&amp;pp=${ppage == ppagesCount ? ppage : ppage + 1}"/>">
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
			<div><c:if test="${p[0] != ppage}"><a href="#" onclick="loadingIcon(this); jQuery('#photos').load('<c:url value="/app/photos/${aid}/${p[0]}"/>'); return false;">[${p[0]}]</a></c:if><c:if test="${p[0] == ppage}"><span style="color: #fff;">[${p[0]}]</span></c:if></div>
		</c:forEach>
	</div>
	<div class="clear"></div>
</c:if>
<%--
<script type="text/javascript">
	/* jQuery('.ttip[title]').tooltip({
		onBeforeShow: function() {
			var trigger = this.getTrigger();
			var id = this.getTrigger().attr('id').substring(1);
			loading('#exif');
			jQuery.get('exif/'+id+'/0', function(data){
				jQuery('#exif').html(data);
				trigger.attr('title',data);
			});
		}
	});
	*/
</script>
--%>
