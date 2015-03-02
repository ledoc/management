<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<!-- meta -->
<meta charset="utf-8">
<meta name="description"
	content="Flat, Clean, Responsive, application admin template built with bootstrap 3">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1, maximum-scale=1">
	
	
<c:url var="urlResources" value="/resources" />
<link rel="shortcut icon" href="${urlResources}/favicon.ico">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300,700'
	rel='stylesheet' type='text/css'>
<!-- /meta -->


<title>Solices - Connexion</title>

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
</head>

<body  style="background-color: #B2DAF7;">
	<div class="app">
		<section class="layout">
			<div class="cover" ></div>

			<div class="overlay" style="background-color: #B2DAF7;"></div>

			<div class="center-wrapper">
				<div class="center-content">
					<div class="row">
						<div
							class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 col-md-4 col-md-offset-4">
							<section class="panel bg-white no-b">
								<div class="panel-heading p15 no-b text-center">
									<img src="${urlResources}/img/logo_solices_full.png"
										alt="Logo de la plateforme Solices">
									<h3>Connectez-vous à  la plateforme</h3>
								</div>
								<div class="p15">

									<form method="post" action="<c:url value="/authentification" />" >
										<c:if test="${param.error != null}">
											<p>Invalid username and password.</p>
										</c:if>
										<c:if test="${param.logout != null}">
											<p>You have been logged out.</p>
										</c:if>

										<input type="text" class="form-control input-lg mb25"
											placeholder="login" name='j_username' autofocus> <input
											type="password" class="form-control input-lg mb25"
											placeholder="password" name='j_password'>
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
										<button type="submit" name="submit"
											class="btn btn-primary btn-lg btn-block">Connexion</button>
									</form>
								</div>
							</section>
							<p class="text-center">
								&copy; <span id="year" class="mr5">2015</span> <span>AH2D</span>
							</p>
						</div>
					</div>

				</div>
			</div>
		</section>
	</div>

	<!-- core scripts -->
	<script src="${urlResources}/plugins/jquery-1.11.1.min.js"></script>
	<script src="${urlResources}/bootstrap/js/bootstrap.js"></script>
	<script src="${urlResources}/plugins/jquery.slimscroll.min.js"></script>
	<script src="${urlResources}/plugins/jquery.easing.min.js"></script>
	<script src="${urlResources}/plugins/appear/jquery.appear.js"></script>
	<script src="${urlResources}/plugins/fastclick.js"></script>
	<script src="${urlResources}/plugins/moment.js"></script>
	<!-- /core scripts -->

	<!-- page level scripts -->
	<script src="http://maps.google.com/maps/api/js?sensor=true"></script>
	<script src="${urlResources}/plugins/gmaps.js"></script>
	<script src="${urlResources}/plugins/chosen/chosen.jquery.min.js"></script>
	<script src="${urlResources}/plugins/datatables/jquery.dataTables.js"></script>
	<script src="${urlResources}/plugins/icheck/icheck.js"></script>
	<script src="${urlResources}/plugins/hightcharts/js/highcharts.js"></script>
	<!-- /page level scripts -->

	<!-- template scripts -->
	<script src="${urlResources}/js/offscreen.js"></script>
	<script src="${urlResources}/js/main.js"></script>
	<!-- /template scripts -->

	<!-- page script -->
	<script src="${urlResources}/js/bootstrap-datatables.js"></script>
	<script src="${urlResources}/js/datatables.js"></script>
	<script src="${urlResources}/js/maps.js"></script>

	<!-- /page script -->
</body>

</html>
