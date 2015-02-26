<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<c:url var="urlResources" value="/resources" />

<!-- meta -->
<meta charset="utf-8">
<meta name="description"
	content="Flat, Clean, Responsive, application admin template built with bootstrap 3">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1, maximum-scale=1">
<link rel="shortcut icon" href="${urlResources}/favicon.ico">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300,700'
	rel='stylesheet' type='text/css'>
<!-- /meta -->

<title>${param.titreOnglet}</title>

<!-- page level plugin styles -->
<link rel="stylesheet"
	href="${urlResources}/plugins/chosen/chosen.min.css">
<link rel="stylesheet"
	href="${urlResources}/plugins/datatables/jquery.dataTables.css">
<!-- /page level plugin styles -->

<!-- core styles -->
<link rel="stylesheet"
	href="${urlResources}/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${urlResources}/css/font-awesome.css">
<link rel="stylesheet" href="${urlResources}/css/themify-icons.css">
<link rel="stylesheet" href="${urlResources}/css/animate.min.css">
<!-- /core styles -->

<!-- template styles -->
<link rel="stylesheet" href="${urlResources}/css/skins/palette.css">
<link rel="stylesheet" href="${urlResources}/css/fonts/font.css">
<link rel="stylesheet" href="${urlResources}/css/main.css">
<!-- template styles -->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<!-- load modernizer -->
<script src="${urlResources}/plugins/modernizr.js"></script>

<sec:authentication var="principal" property="principal" />
<c:url var="urlGetPrincipalId" value="/client/principal/id" />

</head>
<body class="bg-primary">

	<!-- error wrapper -->
	<div class="center-wrapper">

		<div class="center-content text-center">

			<div class="error-number animated bounceIn">404</div>

			<div class="mb25">PAGE NON TROUVEE</div>

			<p>Désolé, mais la page que vous essayez d'afficher n'existe pas.</p>

			<!-- end layout -->
			<footer class="bg-primary clearfix mt15 pt15 pb15">
				<div class="col-lg-6 col-md-6 col-xs-6 text-left">
					<p>&copy; AH2D 2015</p>
				</div>

				<div class="col-lg-6 col-md-6 col-xs-6 text-right">
					<a class="btn btn-social btn-xs btn-facebook mr5"><i
						class="fa fa-facebook"></i>Facebook </a> <a
						class="btn btn-social btn-xs btn-twitter mr5"><i
						class="fa fa-twitter"></i>Twitter </a>
				</div>
			</footer>
		</div>
	</div>
	<!-- /error wrapper -->

	<script type="text/javascript">
		var el = document.getElementById("year"), year = (new Date()
				.getFullYear());
		el.innerHTML = year;
	</script>
</body>
<!-- /body -->

</html>
