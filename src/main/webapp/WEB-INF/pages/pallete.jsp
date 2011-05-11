<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<t:layout>
	<div class="left"><img title="resized" src="<t:context/>/p/${id}/resized"/></div>
	<div class="left"><img title="thumbnail" src="<t:context/>/p/${id}/thumb"/></div>
	<div class="left"><img title="original" src="<t:context/>/p/${id}/original"/></div>	
</t:layout> 
