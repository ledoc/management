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

<body class="bg-default">
	<div class="app bg-default horizontal-layout">
		<header class="header header-fixed navbar shadow">
			<div class="brand">
				<!-- toggle offscreen menu -->
				<a class="ti-menu navbar-toggle off-left visible-xs"
					data-toggle="collapse" data-target="#menu-collapse"></a>
				<!-- /toggle offscreen menu -->

				<!-- logo -->
				<a href="<c:url  value="/finance/list" />" class="navbar-brand">
					<img src="${urlResources}/img/logo_solices.png" alt="">
				</a>
				<!-- /logo -->
			</div>

			<div class="collapse navbar-collapse pull-left" id="menu-collapse">
				<ul class="nav navbar-nav">
					<li>
					<li><a href="<c:url  value="/finance/list" />"
						class="${param.menuFinanceActive}">Finances</a></li>
					<li><a href="<c:url  value="/aliment/list" />"
						class="${param.menuAlimentActive}">Aliments</a></li>
						<li><a href="<c:url  value="/aliment/list" />"
						class="${param.menuPlatActive}">Plats</a></li>
					<li><a href="<c:url  value="/plat/list" />"
						class="${param.menuRepasActive}">Repas</a></li>
					<li><a href="<c:url  value="/exercice/list" />"
						class="${param.menuExerciceActive}">Exercices</a></li>
					<li><a href="<c:url  value="/seance/list" />"
						class="${param.menuSeanceActive}">Séances</a></li>
					<li><a href="<c:url  value="/poids/list" />"
						class="${param.menuPoidsActive}">Poids</a></li>
				</ul>
			</div>
		</header>