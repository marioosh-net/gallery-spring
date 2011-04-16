<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>Layout with sticky footer</title>
	<style type="text/css">
	/** Layout with sticky fixed footer **/
	* {
		margin:0;
		padding:0;
	}	
	body,html {
		margin: 0px;
		padding: 0px;
		height: 100%;
		text-align: center;
	}
	.fixedwidth {
		width: 800px; /* content width */
		margin: 0 auto;
		text-align: left;
	}
	#wrapper {
		min-height: 100%;
		height: auto !important;
		height: 100%;
		margin: 0 auto -7em; /* -(#footer/#push height) */ 
	}
	#header {
		background-color: #000;
		color: #fff;
		height: 40px; /* header height */
	}
	#main {
	}
	#menu {
	}
	#content {
	}
	#footer {
		clear:both;
		background-color: #444;
	}
	#push, #footer {
	    height: 7em; /* footer height */
	}
	/** Layout with sticky fixed footer END **/
	</style>	
</head>
<body id="body">
	<div id="wrapper">
		<div id="header">
			<div class="fixedwidth">
				HEADER
			</div>		
		</div>
	
		<div id="main">
			<div id="menu">
				<div class="fixedwidth">
					MENU
				</div>
			</div>
			
			<div id="content">		
				<div class="fixedwidth">
					CONTENT
				</div>
			</div>
		</div>
        <div id="push"></div>
	</div>
	
	<div id="footer">
		<div class="fixedwidth">
			FOOTER
		</div>
	</div>	
</body>
</html>
