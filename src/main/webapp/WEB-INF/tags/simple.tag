<%@tag description="Body Wrapper" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<%@ attribute name="subtitle" required="false" type="java.lang.String" %>
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
	<title><spring:message code="label.appname"/><c:if test="${not empty subtitle}"> - <spring:message code="${subtitle}" /></c:if></title>
</head>
<body id="body" style="text-align: left;">
	<jsp:doBody/>
</body>
</html>
