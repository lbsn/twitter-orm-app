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

	<%-- Include header --%>
	<%@ include file="/WEB-INF/include/header.jspf"%>
	<div class="container-fluid">
		<div class="row" data-ng-app="tweetApp" data-ng-controller="tweetCtrl">
			<%-- Include sidebar --%>
			<%@ include file="/WEB-INF/include/sidebar.jspf"%>
			
			<!-- Tweets container -->
			<div class="col-md-10 tweets-container" id="tweets-container" data-streaming="true">
				<div class="card">
                  <div class="card-block">
                     <h4 class="card-title">{{content.keyword}}</h4>
                  </div>
               </div>
				<%-- Include tweets table --%>
				<%@ include file="/WEB-INF/include/tweetTable.jspf"%>
			</div>
		</div>
	</div>

	<!-- jQuery -->
	<script type="text/javascript" src="webjars/jquery/3.1.1/jquery.min.js"></script>
	<!-- Bootstrap js-->
	<script type="text/javascript"
		src="webjars/bootstrap/4.0.0-alpha/js/bootstrap.min.js"></script>
	<!-- AngularJS -->
	<script type="text/javascript" src="webjars/angularjs/1.6.4/angular.min.js"></script>
	<!-- Main js-->
	<script type="text/javascript" src="js/main.js"></script>
	
</body>
</html>