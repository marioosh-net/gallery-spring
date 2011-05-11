<%@tag description="Body Wrapper" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta name="description" content="gallery" />
	<meta name="Keywords" lang="pl" content="galeria">
	<meta name="Keywords" lang="en" content="gallery">
	<%-- jQuery Library + ALL jQuery Tools --%>
	<script src="http://cdn.jquerytools.org/1.2.5/full/jquery.tools.min.js"></script>
	<script type="text/javascript">jQuery.noConflict();</script>
  	<%--<script type="text/javascript" src="<t:context/>/js/slimbox2.js"></script>--%>
		
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/prototype/1.6.0.3/prototype.js"></script>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/scriptaculous/1.8.2/scriptaculous.js"></script>
	<script type="text/javascript" src="<t:context/>/js/lightview.js"></script>
		
	<script type="text/javascript" src="<t:context/>/js/main.js"></script>
	<link rel="stylesheet" type="text/css" href="<t:context/>/css/main.css" media="screen">
	<%--<link rel="stylesheet" type="text/css" href="<t:context/>/css/sb/slimbox2.css" />--%> 
	<link rel="stylesheet" type="text/css" href="<t:context/>/css/lightview.css" />
	<title><spring:message code="label.appname"/></title>
	
	<c:if test="${aid != null}">
		<script type="text/javascript">
			jQuery(document).ready(function(){
				/*modals();*/
				photos(${aid != null ? aid : '\'\''},${ppage != null ? ppage : '\'\''});
				albums(${param.p != null ? param.p : '\'\''},${param.s != null ? param.s : '\'\''});
				/*searches();*/
			});
		</script>
	</c:if>
	<c:if test="${aid == null}">
		<script type="text/javascript">
			jQuery(document).ready(function(){
				/*modals();*/
				covers(${param.p != null ? param.p : '\'\''},${param.s != null ? param.s : '\'\''});
				albums(${param.p != null ? param.p : '\'\''},${param.s != null ? param.s : '\'\''});
				/*searches();*/
			});
		</script>
	</c:if>
<!-- google analytics -->	
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-17618439-7']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
<%--	
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-17618439-6']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
--%>
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
</body>
</html>
