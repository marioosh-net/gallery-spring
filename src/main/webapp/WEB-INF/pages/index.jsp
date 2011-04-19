<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<t:layout>
	<div class="left leftfixed">
		<div id="mini">
			<c:if test="${param.a != null and param.a != 0}">
				<div id="photos">
					<%@ include file="/WEB-INF/pages/photos.jsp" %>
				</div>
			</c:if>
			<c:if test="${param.a == null or param.a == 0}">
				<div id="covers">
					<%@ include file="/WEB-INF/pages/covers.jsp" %>
				</div>
			</c:if>
		</div>
	</div>
	<div class="left rightfixed">
		<!-- login panel -->
		<%@include file="/WEB-INF/templates/login.jsp" %>
		
		<!-- album info -->
		<c:if test="${album != null}">
			<div id="album" class="rightbox">
				<div class="left">
					<img src="<t:context/>/p.html?type=2&amp;id=${album.id}"/>
				</div>
				<div class="left ainfo">
					<div class="album-name">${album.name}</div>
					<div class="album-date">${album.modDate}</div>
					<div>
						<security:authorize ifAnyGranted="ROLE_ADMIN">
							<a href="#" class="modalInput modalInputHref" rel="#yesno" rev="deletealbum.html?id=${album.id}"><img class="icon" src="images/list_remove_btn.gif"/><spring:message code="button.delete"/></a>
							<form:form modelAttribute="album" cssClass="sform" id="al">
								<form:hidden path="id"/>
								<form:hidden path="hash"/>
								<form:hidden path="path"/>
								<form:input path="name"/><br/>
								<form:select path="visibility">
									<form:options itemLabel="name"/>
								</form:select><br/>
								<a href="#" onclick="jQuery('#al').submit();">Save</a>
								<input type="submit" class="hiddensubmit"/>
							</form:form>
						</security:authorize>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</c:if>

		<!-- admin global functions -->
		<security:authorize ifAnyGranted="ROLE_ADMIN">
			<div id="main-funcs" class="rightbox">
				<a href="#" class="modalInput modalInputHref" rel="#yesnoadmin" rev="<t:context/>/load.html">load</a>
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
		
		<!-- albums list -->
		<div id="albums" class="rightbox">
			<%@ include file="/WEB-INF/pages/albums.jsp" %>
			<!-- <div style="padding-left: 10px; padding-top: 10px;"><img src="images/ajax.gif"/>&#160;Loading...</div> -->
		</div>
		
	</div>
	<div class="clear"></div>
</t:layout>