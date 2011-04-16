<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

               		<!-- links -->
                   	<div class="link-items-header" >Links by date <span class="main-count">(${count})</span><!-- <span style="float: right; font-size: 12px;">Open links in new window&#160;<input type="checkbox"/></span> --></div>
                      	<c:forEach items="${links}" var="link" varStatus="i">
                      		<div class="link-item">
                      			<div class="clicks count_${link.id}">${link.clicks}</div>
                      			<div class="link-data">
                      				<div>
									<div class="link-item-title">
									<a href="#" onclick="openLink(${link.id},'${link.address}');">${link.name != '' ? link.name : link.address}</a>
									</div>                   				
									<a href="search.html?q=${link.hostName}" class="link-item-source">${link.hostName}</a>
									<a href="${link.address}" class="link-item-teaser">— ${link.description}<span class="timestamp">${link.ldate }</span></a>										
                      				</div>
                      			</div>
                      			<div style="clear: both;"></div>
                      		</div>
						</c:forEach>
                     </div>