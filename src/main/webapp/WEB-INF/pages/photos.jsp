<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<c:forEach items="${ppages}" var="p" varStatus="i">
	<c:if test="${i.index == 0}">Pages:&#160;</c:if>
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
	
		<c:forEach items="${photos}" var="p" varStatus="i">
			<c:if test="${i.index == 0}">
			</c:if>
		
			<div class="thumb_box" style="float: left; /* */">
				<a href="<t:context/>/p.html?type=0&amp;id=${p}" rel="lightbox-gal">
					<div id="t${i.index}" class="thumb" style="background-image: url(images/ajax-loader4.gif); background-position: 50% 50%; "></div>
					<div id="u${i.index}" class="thumb" style="float: none; display: none; background-image: url(<t:context/>/p.html?type=1&amp;id=${p}); "></div>
					<img style="display: none;" src="<t:context/>/p.html?type=1&amp;id=${p}" onload="jQuery('#t${i.index}').hide(); jQuery('#u${i.index}').show();">
				</a>
				<security:authorize ifAllGranted="ROLE_ADMIN">
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
				</security:authorize>
			</div>
		
		</c:forEach>
		<div class="thumb_box">
			<c:if test="${ppage == ppagesCount}">
			<a href="#" class="inactive">
			</c:if>
			<c:if test="${ppage != ppagesCount}">
			<a href="<t:context/>/index.html?a=${aid}&amp;p=${apage}&amp;pp=${ppage == ppagesCount ? ppage : ppage + 1}">
			</c:if>
				<div class="thumb nextp ${ppage == ppagesCount ? 'inactive' : ''}" onmouseover="jQuery(this).addClass('over');" onmouseout="jQuery(this).removeClass('over');">
					<br>N<br>E<br>X<br>T<br>
				</div>
			</a>
		</div>
		
	</div>
	
	<!-- reszta fotek z albumu do slimboxa -->
			
	<script type="text/javascript">
	slimboxstart();
	</script>
	
	<!-- 					
	<div style="clear: both;"></div>

	<div class="photos_pages"><span id="j_idt39:0:j_idt40" name="j_idt39:0:j_idt40">1</span>&nbsp;
		<a id="j_idt39:1:j_idt40" href="#" onclick="mojarra.ab(this,event,'action','@form','@form',{'onevent':onevent1});return false">2</a>&nbsp;
		<a id="j_idt39:2:j_idt40" href="#" onclick="mojarra.ab(this,event,'action','@form','@form',{'onevent':onevent1});return false">3</a>&nbsp;
		<span id="j_idt39:3:j_idt40" name="j_idt39:3:j_idt40">...</span>&nbsp;
		<a id="j_idt39:4:j_idt40" href="#" onclick="mojarra.ab(this,event,'action','@form','@form',{'onevent':onevent1});return false">15</a>&nbsp;
	</div>
	<div style="clear: both;"></div>
	-->
	 
	<div style="clear: both;"></div>
</div>
