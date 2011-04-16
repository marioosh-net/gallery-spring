<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<div class="covers">
	<div class="thumbs">
		<c:forEach items="${albums}" var="a" varStatus="i">
			<c:if test="${i.index == 0}">
				<div class="thumb_box">
					<a href="<t:context/>/index.html?p=">
						<div class="thumb prevp" onmouseover="jQuery(this).addClass('over');" onmouseout="jQuery(this).removeClass('over');">
							<br>P<br>R<br>E<br>V<br>
						</div>
					</a>
				</div>				
			</c:if>
			<div class="thumb_box">
				<a href="<t:context/>/index.html?a=${a.id}" class="thumb_link">
						<div id="t${i.index}" class="thumb" style="background-image: url(http://tomcat.marioosh.net/gpg/images/ajax-loader2.gif); display: none; background-position: 50% 50%; "></div>
						<div id="u${i.index}" class="thumb thumb_box_cover" style="float: none; background-image: url(<t:context/>/p.html/cover/${a.id}); "></div>
						<img style="display: none;" src="<t:context/>/p.html/cover/${a.id}" onload="jQuery('#t${i.index}').hide(); jQuery('#u${i.index}').show();">
						<div class="album_cover_info" style="display: block;">
							<div>
								${a.name}
							</div>
							<div><span style="color: silver;">${a.modDate}</span>
							</div>
						</div>				
				</a>
			</div>
		</c:forEach>
		<div class="thumb_box">
			<a href="<t:context/>/index.html?p=">
				<div class="thumb nextp" onmouseover="jQuery(this).addClass('over');" onmouseout="jQuery(this).removeClass('over');">
					<br>N<br>E<br>X<br>T<br>
				</div></a>
			</a>
		</div>		
		
	</div>
						
	<div style="clear: both;"></div>

	<div class="photos_pages"><span id="j_idt68:0:j_idt69" name="j_idt68:0:j_idt69">1</span>&nbsp;
		<a id="j_idt68:1:j_idt69" href="#" onclick="mojarra.ab(this,event,'action','@form','@form',{'onevent':onevent1});return false">2</a>&nbsp;
		<a id="j_idt68:2:j_idt69" href="#" onclick="mojarra.ab(this,event,'action','@form','@form',{'onevent':onevent1});return false">3</a>&nbsp;
		<span id="j_idt68:3:j_idt69" name="j_idt68:3:j_idt69">...</span>&nbsp;
		<a id="j_idt68:4:j_idt69" href="#" onclick="mojarra.ab(this,event,'action','@form','@form',{'onevent':onevent1});return false">17</a>&nbsp;
		
		
	</div>
	<div style="clear: both;"></div>
</div>
