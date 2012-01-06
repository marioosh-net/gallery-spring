<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<input type="hidden" id="mode" value="covers"/>

<div class="covers">
	<div class="thumbs">
		<c:forEach items="${albums}" var="a" varStatus="i">
			
			<div class="thumb_box" title="${a.name}">
				<a href="#" onclick="jQuery('.tooltip').remove(); jQuery('#photos').load('<c:url value="/app/photos/${a.id}/1"/>');" class="thumb_link">
					<div id="t${i.index}" class="thumb" style="background-image: url(<c:url value="/images/ajax-loader7.gif"/>); background-position: 50% 50%; "></div>
					<div id="u${i.index}" class="thumb thumb_box_cover" style="display: none; float: none; background-image: url(<c:url value="/app/a/${a.id}/cover"/>); "></div>
					<img style="display: none;" src="<c:url value="/app/a/${a.id}/cover"/>" onload="jQuery('#t${i.index}').hide(); jQuery('#u${i.index}').show();">
					<%--
					<div class="album_cover_info" style="display: block;">
						<div>
							${a.name}
						</div>
						<div><span style="color: silver;">${a.modDate}</span>
						</div>
					</div>
					--%>				
				</a>
			</div>
		</c:forEach>
		
	</div>

	<div style="clear: both;"></div>
	<%-- tooltips --%>
	<script type="text/javascript">jQuery('.thumb_box').tooltip({ position: 'bottom right', offset: [-144, -124], effect: 'slide'});</script>

</div>
