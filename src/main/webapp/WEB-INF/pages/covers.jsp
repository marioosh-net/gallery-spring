<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<!--
<c:forEach items="${apages}" var="p" varStatus="i">
	<c:if test="${i.index == 0}"><spring:message code="label.pages"/>&#160;</c:if>
	<span>
		<c:if test="${p[0] != apage}"><a href="index.html?a=${aid}&amp;pp=${ppage}&amp;p=${p[0]}">${p[0]}</a></c:if>
		<c:if test="${p[0] == apage}">${p[0]}</c:if>
		&#160;
	</span>
</c:forEach>
-->

<div class="covers">
	<div class="thumbs">
		<c:forEach items="${albums}" var="a" varStatus="i">
			<!--
			<c:if test="${i.index == 0}">
				<div class="thumb_box">
					<c:if test="${apage == 1}">
					<a href="#" class="inactive">
					</c:if>
					<c:if test="${apage != 1}">
					<a href="<t:context/>/index.html?a=${aid}&amp;pp=${ppage}&amp;p=${apage == 1 ? apage : apage - 1}">
					</c:if>				
						<div class="thumb prevp ${apage == 1 ? 'inactive' : ''}" onmouseover="jQuery(this).addClass('over');" onmouseout="jQuery(this).removeClass('over');">
							<br>P<br>R<br>E<br>V<br>
						</div>
					</a>
				</div>				
			</c:if>
			-->
			
			<c:if test="${(i.index%7 == 0 and i.index != 0)}">
				<div class="clear"></div>
			</c:if>
			
			<div class="thumb_box">
				<a href="<t:context/>/index.html?a=${a.id}&amp;p=${apage}" class="thumb_link">
						<div id="t${i.index}" class="thumb" style="background-image: url(images/ajax-loader4.gif); background-position: 50% 50%; "></div>
						<div id="u${i.index}" class="thumb thumb_box_cover" style="display: none; float: none; background-image: url(<t:context/>/p.html?type=2&amp;id=${a.id}); "></div>
						<img style="display: none;" src="<t:context/>/p.html?type=2&amp;id=${a.id}" onload="jQuery('#t${i.index}').hide(); jQuery('#u${i.index}').show();">
						<!--
						<div class="album_cover_info" style="display: block;">
							<div>
								${a.name}
							</div>
							<div><span style="color: silver;">${a.modDate}</span>
							</div>
						</div>
						-->				
				</a>
			</div>
		</c:forEach>
		<!--
		<div class="thumb_box">
			<c:if test="${apage == apagesCount}">
			<a href="#" class="inactive">
			</c:if>
			<c:if test="${apage != apagesCount}">
			<a href="<t:context/>/index.html?a=${aid}&amp;pp=${ppage}&amp;p=${apage == apagesCount ? apage : apage + 1}">
			</c:if>
				<div class="thumb nextp ${apage == apagesCount or apagesCount == 0 ? 'inactive' : ''}" onmouseover="jQuery(this).addClass('over');" onmouseout="jQuery(this).removeClass('over');">
					<br>N<br>E<br>X<br>T<br>
				</div></a>
			</a>
		</div>
		-->		
		
	</div>

	<div style="clear: both;"></div>
</div>
