<%@tag description="Body Wrapper" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta name="description" content="gallery" />
	<meta name="Keywords" lang="pl" content="galeria">
	<meta name="Keywords" lang="en" content="gallery">
	<script type="text/javascript">	var context = '<c:url value="/"/>';	if(context.substring(context.length-1,context.length) == '/') { context = context.substring(0, context.length - 1);} </script>
	<%-- jQuery Library + ALL jQuery Tools --%>
	<script type="text/javascript" src="<c:url value="/js/jquery.tools.min.js"/>"></script>
	<script type="text/javascript">jQuery.noConflict();</script>
  	<%--<script type="text/javascript" src="<c:url value="/js/slimbox2.js"/>"></script>--%>

	<script type="text/javascript" src="<c:url value="/js/prototype.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/scriptaculous.js"/>"></script>		
	<script type="text/javascript" src="<c:url value="/js/lightview.js"/>"></script>
		
	<script type="text/javascript" src="<c:url value="/js/main.js"/>"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>" media="screen">
	<%--<link rel="stylesheet" type="text/css" href="<c:url value="/css/sb/slimbox2.css"/>" />--%> 
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/lightview.css"/>" />
	<title><spring:message code="label.appname"/></title>
	
	<c:if test="${aid != null}">
		<script type="text/javascript">
			jQuery(document).ready(function(){
				photos(${aid != null ? aid : '\'\''},${ppage != null ? ppage : '\'\''});
				albums(${param.p != null ? param.p : '\'\''},${param.s != null ? param.s : '\'\''});
				<security:authorize ifAnyGranted="ROLE_ADMIN">
					setInterval("log()",1000);
				</security:authorize>
			});
		</script>
	</c:if>
	<c:if test="${aid == null}">
		<script type="text/javascript">
			jQuery(document).ready(function(){
				covers(${param.p != null ? param.p : '\'\''},${param.s != null ? param.s : '\'\''});
				albums(${param.p != null ? param.p : '\'\''},${param.s != null ? param.s : '\'\''});
				<security:authorize ifAnyGranted="ROLE_ADMIN">
					setInterval("log()",1000);
				</security:authorize>
			});
		</script>
	</c:if>
<c:if test="${servername != 'localhost'}">
<!-- google analytics -->	
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-17618439-8']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + 
'.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</c:if>
</head>
<body id="body">
	<%@include file="/WEB-INF/templates/debug.jsp" %>
	<div id="wrapper">	
		<div id="main">
			<div id="content">		
				<div class="fixedwidth">
					<jsp:doBody/>
				</div>
			</div>
		</div>
        <%-- <div id="push"></div> --%>
	</div>
	<%--
	<div id="footer">
		<div class="fixedwidth">
			<spring:message code="text.footer" />
		</div>
	</div>
	--%>	
	
	<%-- admin log --%>
	<t:modal id="logmodal" close="true" wide="true" alignleft="true">
		<div id="logdiv" style="height: 200px; overflow: auto;">
			<pre id="log"></pre>
		</div>		
	</t:modal>
	
</body>
</html>
