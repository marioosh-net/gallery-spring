<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>

<c:forEach items="${albums}" var="a" varStatus="i">
	<%-- <a href="<t:context/>/index.html?a=${a.id}&amp;p=${apage}"><div class="oneset" style="${a.id == aid ? 'background-color: #AAA;' : ''}">${a.shortName}<span class="counts">(${ac[i.index]})</span></div></a> --%>
	<a href="#" title="${a.name}" onclick="jQuery('.oneset').removeClass('selected'); jQuery('.oneset_${a.id}').addClass('selected'); loading('#photos'); jQuery('#photos').load('<t:context/>/photos/${a.id}/1');"><div class="oneset oneset_${a.id} ${a.id == param.a ? 'selected' : ''}" style="${a.id == aid ? 'background-color: #AAA;' : ''}">${a.shortName}<span class="counts">(${ac[i.index]})</span></div></a>
	<%-- <security:authorize ifAnyGranted="ROLE_ADMIN">&#160;<a href="#" onclick="openModal(this)" class="modalInput modalInputHref" rel="#yesno" rev="deletealbum.html?id=${a.id}"><img src="images/list_remove_btn.gif"/></a></security:authorize> --%>
</c:forEach>
<%-- prev --%> 
<div class="left" style="margin-top: 3px;">
<%--
<c:if test="${apage == 1}">
<a href="#" class="inactive">&#171; prev</a>
</c:if>
--%>
<c:if test="${apage != 1}">
	<a href="#" onclick="
		loadingIcon(this); 
		jQuery('#albums').load('<t:context/>/albums/${apage == 1 ? apage : apage - 1}/${search}');
		if(jQuery('#mode').val() == 'covers') {
			jQuery('#photos').load('<t:context/>/covers/${apage == 1 ? apage : apage - 1}/${search}');
		}
	"><spring:message code="button.prevAlbum"/></a>
</c:if>
</div>

<%-- next --%> 
<div class="right" style="margin-top: 3px;">
<%--
<c:if test="${apage == apagesCount}">
<a href="#" class="inactive">next &#187;</a>
</c:if>
--%>
<c:if test="${apage != apagesCount}">
	<a href="#" onclick="
		loadingIcon(this); 
		jQuery('#albums').load('<t:context/>/albums/${apage == apagesCount ? apage : apage + 1}/${search}');
		if(jQuery('#mode').val() == 'covers') { 
			jQuery('#photos').load('<t:context/>/covers/${apage == apagesCount ? apage : apage + 1}/${search}');
		}
		"><spring:message code="button.nextAlbum"/></a>
	</c:if>
</div>
<div class="clear"></div>

	<t:modalyesno id="yesno">
		<spring:message code="text.confirmDeleteAlbum" />
	</t:modalyesno>
