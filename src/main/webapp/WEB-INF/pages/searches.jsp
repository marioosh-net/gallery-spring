<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<c:forEach items="${searches}" var="search" varStatus="i">
	<span class="search-item search-item_${search.id}">
		<a href="index.html?q=${search.phrase}">${search.phrase}</a>&#160;<span class="search-counter">(${search.counter})</span>
		<security:authorize access="hasRole('ROLE_ADMIN')">
			<span class="func-item">
				<a class="del" href="javascript:;" onclick="deleteSearch(${search.id})"><img src="images/del.png"/></a>
			</span>
		</security:authorize>
	</span>
</c:forEach>
<div style="clear: both;"></div>