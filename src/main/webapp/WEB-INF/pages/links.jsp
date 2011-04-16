<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta name="description" content="Linkownia - przechowalnia linków" />
	<meta name="author" content="marioosh.net">
	<meta name="Keywords" lang="pl" content="morele, linkownia, baza linków, odnośniki, adresy, www, marioosh">
	<meta name="Keywords" lang="en" content="apricots, linkownia, links database, references, addresses, www, marioosh">
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/main2.css" media="screen">
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/main.js"></script>
	<title>Linkownia - przechowalnia linków</title>
</head>

<body id="body" onload="jQuery('#qtext').focus();"> 

 	<c:if test="${header['host'] == 'localhost:8081' or header['host'] == 'localhost:8080'}">
	 	<div id="debug">
	 		<a class="debugbutton" href="#" onclick="jQuery('#debug-container').toggle();">DEBUG</a>
	 		<div id="debug-container">
		 		<c:forEach items="${param}" var="par">
		 			<div>${par }</div>
		 		</c:forEach>
		 		<div style="font-weight: bold; display: none;">DEBUG:</div>
		 		<div id="debug-content">
		 		</div>
	 		</div>
	 	</div>
 	</c:if>

	<div id="wrapper">
		<div id="header">
			<div class="fixedwidth">
				<div class="left leftfixed">
				<a href="index.html"><img src="images/logo.png" alt="" /></a>
				</div>
				
				<div class="left rightfixed">
					<div id="search">
					<form method="post" action="search.html">
						<input id="qtext" type="text" name="q" value="${param['site'] != 1 ? q : ''}"/>
						<input type="button" class="button" onclick="submit()" />
					</form>
					</div>
 				</div>
 				
 				<div class="clear"></div>
			</div>		
		</div>
	
		<div id="main">
			<div id="menu">
				<div class="fixedwidth">
					<div class="left">
		       			<security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER, ROLE_XXX">
		       			<div class="menu-item first-item">
							<a href="#" onclick="hidepanels('new', function() {jQuery('#address').focus();});"><img width="51" height="20" src="images/add.png"/></a>
						</div>
						</security:authorize>
						
						<security:authorize ifNotGranted="ROLE_ADMIN, ROLE_USER, ROLE_XXX">
						<div class="menu-item">
							<a href="#" onclick="hidepanels('login', function() {jQuery('#username').focus();});"><img width="51" height="20" src="images/loginbutton.png"/></a>
						</div>
						</security:authorize>
						<div class="clear"></div>
					</div>
					
					<div class="right">
						<security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER, ROLE_XXX">
						<div class="menu-item">
							<span class="username"><security:authentication property="principal.username" /></span>
							<a href="<%= request.getContextPath() %>/logout.html" >Logout</a>
						</div>
						</security:authorize>
						<!--
						<div class="menu-item">
							<a href="#" onclick="jQuery('#new').toggle('fast', function() {jQuery('#address').focus();});">New link</a>
						</div>
						-->        		
					</div>
					
					<div class="clear"></div>
				</div>
			</div>
			
			<div id="content">		
				<div class="fixedwidth">
 
              	  <div class="left leftfixed"> 
                  	<div id="leftPan"> 
                  	
					<!-- login form -->
					<div class="panel" id="login" style="${param['loginfail'] == 1 ? ' ' : 'display: none;'}">
						<form id="jf" name="jf" action="<%= request.getContextPath() %>/j_spring_security_check" method="post">
							<div style="float: left">
								<div class="smalllabel">login</div>
								<div><input type="text" id="username" name="j_username" value="" size="5" class="enter" /></div>
							</div>
							<div style="float: left">
								<div class="smalllabel">&#160;password</div>
								<div>&#160;<input type="password" name="j_password" value="" size="6" class="enter" /></div>
							</div>
							<div style="float: left">
								<div>&#160;</div>
								<div style="vertical-align: bottom; padding-top: 3px;">&#160;<a href="#" onclick="jQuery('#jf').submit();">Login</a><!-- <input type="button" class="button" onclick="submit()" /> --><input type="submit" style="position: absolute; left: -9999px; width: 1px; height: 1px;"/>
									<c:if test="${!empty param.loginfail}"><span class="errors">&#160;Login error!</span></c:if>
								</div>
							</div>
							<div style="clear: both;"></div>
							<div><input type='checkbox' name='_spring_security_remember_me'/>&#160;Remember me</div>
						</form>
					</div>
						
					<!-- new link form -->
					<security:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER, ROLE_XXX">			
					<div class="panel" id="new" style="${someErrors ? '' : 'display: none;'}">
						<!-- <form method="post" action="add.html"> -->
						<form:form commandName="link" method="post" action="add.html" id="newform">
							<label>Address</label>&#160;<form:errors cssStyle="${someErrors ? '' : 'display: none;'}" path="address" cssClass="errors" /><br/>
							<input type="text" id="address" name="address" style="width: 404px;" value="${param['address']}" class="enter"/><br/>
							<label>Name</label><br/>
							<input type="text" name="name" style="width: 404px;" value="${param['name']}" class="enter"/><br/>
							<label>Description</label><br/>
							<textarea name="description" style="height: 70px; width: 648px;">${param['description']}</textarea><br/>
							<a href="#" onclick="jQuery('#newform').submit();">Add link</a>&#160;&#160;&#160;&#160;<a href="#" onclick="jQuery('#new').hide('fast');">Cancel</a>
							<input type="submit" style="position: absolute; left: -9999px; width: 1px; height: 1px;"/>
						</form:form>
					</div>
					<div class="panel" id="edit" style="${someSaveErrors ? '' : 'display: none;'}">
						<form:form commandName="link" method="post" action="save.html" id="editform">
							<input type="hidden" id="id" name="id"/>
							<label>Address</label>&#160;<form:errors cssStyle="${someSaveErrors ? '' : 'display: none;'}" path="address" cssClass="errors" /><br/>
							<input type="text" id="address2" name="address" style="width: 404px;" value="${param['address']}" class="enter"/><br/>
							<label>Name</label><br/>
							<input type="text" id="name" name="name" style="width: 404px;" value="${param['name']}" class="enter"/><br/>
							<label>Description</label><br/>
							<textarea id="description" name="description" style="height: 70px; width: 648px;">${param['description']}</textarea><br/>
							<a href="#" onclick="jQuery('#editform').submit();">Save link</a>&#160;&#160;&#160;&#160;<a href="#" onclick="jQuery('#edit').hide('fast');">Cancel</a>
							<input type="submit" style="position: absolute; left: -9999px; width: 1px; height: 1px;"/>
						</form:form>
					</div>					
					</security:authorize>
					
               		<!-- links -->
                   	<div class="block-header" >Links by date <span class="main-count">(${count})</span>
                   		<span id="topnavi">
                   		<c:if test="${count > 1}" >
                   			<c:if test="${page != 1}" >
                   				<a href="index.html?q=${q}&amp;p=${page -1}">&#171; Newer</a>
                   			</c:if>
                   			<c:if test="${page == 1}" >
                   				<span class="inactive">&#171; Newer</span>
                   			</c:if>
                   			<c:if test="${page < pagesCount}">
                   				<a href="index.html?q=${q}&amp;p=${page +1}">Older &#187;</a>
                   			</c:if>
                   			<c:if test="${page >= pagesCount}">
                   				<span class="inactive">Older &#187;</span>
                   			</c:if>                   			
                   		</c:if>
                   		</span>
                   		<!-- <span style="float: right; font-size: 12px;">Open links in new window&#160;<input type="checkbox"/></span> --></div>
                      	<c:forEach items="${links}" var="link" varStatus="i">
                      		<div class="link-item link-item_${link.id}">
                      			<div class="clicks count_${link.id}">${link.clicks}<div><img class="ajax_${link.id}" style="display:none;" src="images/ajax.gif"/></div></div>
                      			<div class="link-data">
                      				<div>
									<div class="link-item-title">
									<a href="#" onclick="openLink(${link.id},'${link.address}');" class="name">${link.name != '' ? link.name : link.address}</a>
									</div>                   				
									<a href="index.html?site=1&amp;q=${link.hostName}" class="link-item-source">${link.hostName}</a>
									<a href="${link.address}" class="link-item-teaser">— <span class="descr">${link.description}</span><span class="timestamp">${link.dateModFormatted}</span></a>										
                      				</div>
                      				<security:authorize access="hasRole('ROLE_ADMIN')">
                      				<div class="admin-funcs">
                      					<span class="func-item">
	                      					<a class="del" href="#" onclick="deleteLink(${link.id})"><img src="images/del.png"/><span class="button-text">delete</span></a>
	                      				</span>
	                      				<span class="func-item">	
	                      					<a class="edit" href="#" onclick="edit(${link.id})"><img src="images/ed.png"/><span class="button-text">edit</span></a>
                      					</span>
	                      				<span class="func-item">	
	                      					<a class="refresh" href="#" onclick="refresh(${link.id})"><img src="images/reload.png"/><span class="button-text">refresh</span></a>
                      					</span>                      					
                      				</div>                      				
                      				</security:authorize>
                      			</div>
                      			<div style="clear: both;"></div>
                      		</div>
						</c:forEach>
                     </div>
                     <div id="navi">
                     	<c:forEach items="${pages}" var="p" varStatus="i">
                     		<c:if test="${i.index == 0}">Pages:&#160;</c:if>
                     		<span>
                     			<c:if test="${p[0] != page}"><a href="index.html?q=${q}&amp;p=${p[0]}">${p[0]}</a></c:if>
                     			<c:if test="${p[0] == page}">${p[0]}</c:if>
                     			&#160;
                     		</span>
                     	</c:forEach>
                     </div>
                      
               	</div> 
                   
                   <div class="right rightfixed">
                   
					<!-- searches -->
					<div class="block-header" >Top searches</div>
					<div id="searches">
						<div style="padding-left: 10px; padding-top: 10px;"><img src="images/ajax.gif"/>&#160;Loading...</div>
					</div>
                   
                   	<!-- top links -->
					<div class="block-header" >Top clicks</div>
					<span id="toplinks">
						<div style="padding-left: 10px; padding-top: 10px;"><img src="images/ajax.gif"/>&#160;Loading...</div>
					</span>                        

                   </div> 
                   <!--end of right column --> 
				
					<div class="clear"></div>

				</div>
			</div>
		</div>
        <div id="push"></div>
	</div>
	
	<div id="footer">
		<div class="fixedwidth">
			<div class="pages"> 
				<ul> 
					<li><a href="index.html">Linkownia</a></li> 
					<li><a href="http://marioosh.net">Home</a></li> 
					<li><a href="http://marioosh.net/blog/">Blog</a></li> 
				</ul> 
			</div> 
			<div class="copyright"> 
				<p class="rights">&copy; 2011. Designed by: <strong><a href="http://marioosh.net" target="_blank">marioosh.net</a></strong></p> 
			</div> 
		</div>
	</div>
    
</body>
</html>

