<%@tag description="Body Wrapper" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta name="description" content="PAYAP" />
	<meta name="Keywords" lang="pl" content="płatności">
	<meta name="Keywords" lang="en" content="payments">
	<!-- jQuery Library + ALL jQuery Tools -->
	<script src="http://cdn.jquerytools.org/1.2.5/full/jquery.tools.min.js"></script>
	<script type="text/javascript" src="<t:context/>/js/main.js"></script>
	<link rel="stylesheet" type="text/css" href="<t:context/>/css/main.css" media="screen">
	<!--[if !IE 7]><style type="text/css">#wrap {display:table;height:100%}</style><![endif]-->
	<title><spring:message code="label.appname"/></title>
</head>
<body id="body">
	<%@include file="/WEB-INF/templates/debug.jsp" %>
	<div id="wrapper">
		<div id="header">
			<div class="fixedwidth">
				<div class="left" style="padding-top: 10px;">
					<a href="<t:context/>/index.html" class="logoref" href=""><img src="<t:context/>/images/logo.png"/></a>
				</div>
				<div class="right">
					<a href="?lang=de">DE</a>|<a href="?lang=en">EN</a>
				</div>
				<div class="clear"></div>
			</div>		
		</div>
	
		<div id="main">
			<div id="menu">
				<div class="fixedwidth">
					<%@include file="/WEB-INF/templates/menu.jsp" %>
				</div>
			</div>
			
			<div id="content">		
				<div class="fixedwidth">
					<jsp:doBody/>
				</div>
			</div>
		</div>
        <div id="push"></div>
	</div>
	
	<div id="footer">
		<div class="fixedwidth">
			<spring:message code="text.footer" />
		</div>
	</div>	
</body>
</html>
