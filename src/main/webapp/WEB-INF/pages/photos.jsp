<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<div class="photos">
	<div class="thumbs">
		<c:forEach items="${photos}" var="p" varStatus="i">
			<c:if test="${i.index == 0}">
				<div class="thumb_box">
					<a href="<t:context/>/index.html?pp=">
						<div class="thumb prevp" onmouseover="jQuery(this).addClass('over');" onmouseout="jQuery(this).removeClass('over');">
							<br>P<br>R<br>E<br>V<br>
						</div>
					</a>
				</div>				
			</c:if>
		
			<div class="thumb_box" style="float: left; /* */">
				<a href="<t:context/>/p.html/photo/${p}" rel="lightbox-gal">
					<div id="t${i.index}" class="thumb" style="background-image: url(http://tomcat.marioosh.net/gpg/images/ajax-loader2.gif); display: none; background-position: 50% 50%; "></div>
					<div id="u${i.index}" class="thumb" style="float: none; background-image: url(<t:context/>/p.html/thumb/${p}); "></div>
					<img style="display: none;" src="<t:context/>/p.html/thumb/${p}" onload="jQuery('#t${i.index}').hide(); jQuery('#u${i.index}').show();">
				</a>
					<div class="thumb_options">
						<div>
							<img src="/gpg/images/key.png">&nbsp;
							<a id="photos2:0:vis2" href="#" onclick="mojarra.ab(this,event,'action','@form','photos',{'onevent':onevent1});return false" class="">admin</a>
						</div>				
						<div>
							<img src="/gpg/images/list_remove_btn.gif">&nbsp;
							<a id="photos2:0:j_idt197" href="#" onclick="mojarra.ab(this,event,'action','@form','photos content',{'onevent':onevent1});return false">Delete</a>											
						</div>						
					</div>
			</div>
		
		</c:forEach>
		<div class="thumb_box">
			<a href="<t:context/>/index.html?pp=">
				<div class="thumb nextp" onmouseover="jQuery(this).addClass('over');" onmouseout="jQuery(this).removeClass('over');">
					<br>N<br>E<br>X<br>T<br>
				</div>
			</a>
		</div>
		
	</div>
	
	<!-- reszta fotek z albumu do slimboxa -->
			
	<script type="text/javascript">
	slimboxstart();
	</script>
						
	<div style="clear: both;"></div>

	<div class="photos_pages"><span id="j_idt39:0:j_idt40" name="j_idt39:0:j_idt40">1</span>&nbsp;
		<a id="j_idt39:1:j_idt40" href="#" onclick="mojarra.ab(this,event,'action','@form','@form',{'onevent':onevent1});return false">2</a>&nbsp;
		<a id="j_idt39:2:j_idt40" href="#" onclick="mojarra.ab(this,event,'action','@form','@form',{'onevent':onevent1});return false">3</a>&nbsp;
		<span id="j_idt39:3:j_idt40" name="j_idt39:3:j_idt40">...</span>&nbsp;
		<a id="j_idt39:4:j_idt40" href="#" onclick="mojarra.ab(this,event,'action','@form','@form',{'onevent':onevent1});return false">15</a>&nbsp;
	</div>
	<div style="clear: both;"></div>
	
	<div style="clear: both;"></div>
</div>
