<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<c:forEach items="${toplinks}" var="link" varStatus="i">
	<div class="link-item link-item_${link.id}">
		<div class="clicks count_${link.id}">
			${link.clicks}
		</div>
		<div class="link-data" style="width: 200px;">
			<div>
				<div class="link-item-title">
					<a href="javascript:;" onclick="openLink(${link.id},'${link.address}');" class="name">${link.name != '' ? link.name : link.address}</a>
				</div>
				<a href="index.html?site=1&amp;q=${link.hostName}" class="link-item-source">${link.hostName}</a>
				<span class="timestamp">${link.dateModFormatted}</span>
			</div>
			<security:authorize access="hasRole('ROLE_ADMIN')">
			<div class="admin-funcs">
				<span class="func-item">
					<a class="del" href="javascript:;" onclick="deleteLink(${link.id})"><img src="images/del.png"/><span class="button-text">delete</span></a>
				</span>
				<span class="func-item">	
					<a class="edit" href="javascript:;" onclick="edit(${link.id})"><img src="images/ed.png"/><span class="button-text">edit</span></a>
				</span>
				<span class="func-item">	
					<a class="refresh" href="javascript:;" onclick="refresh(${link.id})"><img src="images/reload.png"/><span class="button-text">refresh</span></a>
				</span>                      					
			</div>			
			</security:authorize>
		</div>
		<div style="clear: both;">
		</div>
	</div>
</c:forEach>