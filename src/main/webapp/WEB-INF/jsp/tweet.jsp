<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<link rel="stylesheet"
	href="webjars/bootstrap/4.0.0-alpha/css/bootstrap.min.css">
<link rel="stylesheet" href="css/main.css"/>
</head>
<body>

	<%-- Include header --%>
	<%@ include file="/WEB-INF/include/header.jspf"%>
	<div class="container-fluid">
		<div class="row">
			<%-- Include sidebar --%>
			<%@ include file="/WEB-INF/include/sidebar.jspf"%>
		</div>
	</div>


	<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="webjars/bootstrap/4.0.0-alpha/js/bootstrap.min.js"></script>
</body>
</html>