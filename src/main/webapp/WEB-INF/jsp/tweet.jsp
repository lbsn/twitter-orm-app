<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
<link rel="stylesheet"
	href="webjars/bootstrap/4.0.0-alpha/css/bootstrap.min.css">
<link rel="stylesheet" href="webjars/datatables/1.10.12/css/jquery.dataTables.min.css"></link>
<link rel="stylesheet" href="css/main.css"/>
</head>
<body>

	<%-- PAGE HEADER --%>
	<%@ include file="/WEB-INF/include/header.jspf"%>
	
	<%-- PAGE CONTAINER --%>
	<div class="container-fluid">
		<div class="row" data-ng-app="tweetApp" data-ng-controller="appCtrl">
			<%-- PAGE SIDEBAR --%>
			<%@ include file="/WEB-INF/include/sidebar.jspf"%>

			<%-- TWEETS CONTAINER --%>
			<%@ include file="/WEB-INF/include/tweetContainer.jspf"%>
		</div>
	</div><%-- END PAGE CONTAINER --%>

	<!-- jQuery -->
	<script type="text/javascript" src="webjars/jquery/3.1.1/jquery.min.js"></script>
	<!-- Bootstrap js-->
	<script type="text/javascript"
		src="webjars/bootstrap/4.0.0-alpha/js/bootstrap.min.js"></script>
	<!-- AngularJS -->
	<script type="text/javascript" src="webjars/angularjs/1.6.4/angular.min.js"></script>
	<!-- AngularJS Bootstrap UI -->
	<script type="text/javascript" src="webjars/angular-ui-bootstrap/2.2.0/ui-bootstrap-tpls.min.js"></script>
	<!-- D3 js -->
	<script type="text/javascript" src="webjars/d3js/3.5.17/d3.min.js"></script>
	<!-- Main js-->
	<script type="text/javascript" src="js/app.js"></script>
	
</body>
</html>