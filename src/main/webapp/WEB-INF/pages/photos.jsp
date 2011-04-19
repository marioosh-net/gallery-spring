<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<c:forEach items="${ppages}" var="p" varStatus="i">
	<c:if test="${i.index == 0}"><spring:message code="label.pages"/>&#160;</c:if>
	<span>
		<c:if test="${p[0] != ppage}"><a href="index.html?a=${aid}&amp;p=${apage}&amp;pp=${p[0]}">${p[0]}</a></c:if>
		<c:if test="${p[0] == ppage}">${p[0]}</c:if>
		&#160;
	</span>
</c:forEach>

<div class="photos">
	<div class="thumbs">
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
	
		<!-- before -->
		<c:forEach items="${before}" var="p" varStatus="i">
			<a href="<t:context/>/p.html?type=0&amp;id=${p['id']}" class="lightview" rel="gallery[mygallery]" title="<a href='<t:context/>/p.html?type=0&amp;id=${p["id"]}' target='_blank'>${p['name']}</a>"></a>
		</c:forEach>
		<!-- before END-->
		<c:forEach items="${photos}" var="p" varStatus="i">
			<c:if test="${i.index == 0}">
			</c:if>
		
			<div id="th${i.index}" class="thumb_box" style="float: left; /* */">
				<a href="<t:context/>/p.html?type=0&amp;id=${p['id']}" class="lightview" rel="gallery[mygallery]" title="<a href='<t:context/>/p.html?type=0&amp;id=${p["id"]}' target='_blank'>${p['name']}</a>">
					<div id="t${i.index}" class="thumb" style="background-image: url(images/ajax-loader4.gif); background-position: 50% 50%; "></div>
					<div id="u${i.index}" class="thumb" style="float: none; display: none; background-image: url(<t:context/>/p.html?type=1&amp;id=${p['id']}); "></div>
					<img style="display: none;" src="<t:context/>/p.html?type=1&amp;id=${p['id']}" onload="jQuery('#t${i.index}').hide(); jQuery('#u${i.index}').show();">
				</a>
				<security:authorize ifAllGranted="ROLE_ADMIN">
					<div class="thumb_options">
						<div>
							<img src="images/key.png">&nbsp;
							<a id="v${i.index}" href="#" onclick="jQuery.get('changevisibility.html?id=${p['id']}',function(data){ jQuery('#v${i.index}').text(data); });" >${p['visibility'] == 0 ? 'public' : (p['visibility'] == 1 ? 'private' : (p['visibility'] == 2 ? 'admin' : ' '))}</a>
						</div>				
						<div>
							<img src="images/list_remove_btn.gif">&nbsp;
							<a href="#" class="modalInput modalInputClick" rev="jQuery.get('deletephoto.html?id=${p['id']}',function(data){if(data == '0'){ jQuery('#th${i.index}').remove(); }});" rel="#yesnophoto" ><spring:message code="button.delete"/></a>											
							<!-- <a href="#" class="modalInput modalInputHref" rev="deletephoto2.html?id=${p['id']}" rel="#yesnophoto" ><spring:message code="button.delete"/></a> -->
						</div>						
					</div>
				</security:authorize>
			</div>
		
		</c:forEach>
		<!-- after -->
		<c:forEach items="${after}" var="p" varStatus="i">
			<a href="<t:context/>/p.html?type=0&amp;id=${p['id']}" class="lightview" rel="gallery[mygallery]" title="<a href='<t:context/>/p.html?type=0&amp;id=${p["id"]}' target='_blank'>${p['name']}</a>"></a>		
		</c:forEach>
		<!-- after END -->
				
		<t:modalyesno id="yesnophoto">
			<spring:message code="text.confirmDeletePhoto" />
		</t:modalyesno>
		
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
		
	</div>
	
	<!-- reszta fotek z albumu do slimboxa -->
	
	<!-- 		
	<script type="text/javascript">
	slimboxstart();
	</script>
	-->
	
	<div style="clear: both;"></div>
</div>
