<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
<!-- /meta -->

<title>Solices - Vue liste</title>

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


<body>
	<div class="app bg-default">
		<header class="header header-fixed navbar shadow">

		<div class="brand">
			<!-- logo -->
			<a href="carto.html" class="navbar-brand text-center"> <img
				src="${urlResources}/img/logo_solices.png" alt="">
			</a>
			<!-- /logo -->
		</div>

		<div class="pull-left">
			<ul class="nav navbar-nav">
				<li><a href="carto.html" class="${param.menuAccueilActive}">Accueil</a></li>
				<li><a href="liste.html" class="${param.menuUtilisateurActive}">Utilisateurs</a></li>
				<li><a href="#" class="${param.menuSiteActive}">Sites</a></li>
				<li><a href="#" class="${param.menuOuvrageActive}">Ouvrages</a></li>
				<li><a href="mesures.html" class="${param.menuMesureActive}">Mesures</a></li>
				<li><a href="#" class="${param.menuDocumentActive}">Documents</a></li>
				<li><a href="#" class="${param.menuMessagerieActive}">Messagerie</a></li>
			</ul>
		</div>
		<ul class="nav navbar-nav navbar-right">

			<li class="off-right"><a href="#" data-toggle="dropdown"> <span
					class="hidden-xs ml10">Philippe Leon</span> <i
					class="ti-angle-down ti-caret hidden-xs"></i>
			</a>
				<ul class="dropdown-menu animated fadeInRight">
					<li><a href="#">Mon compte</a></li>
					<li><a href="javascript:;">
							<div class="badge bg-danger pull-right">3</div> <span>Messagerie</span>
					</a></li>
					<li><a href="index.html">Déconnexion</a></li>
				</ul></li>
		</ul>
		</header>


		<section class="layout"> <!-- /sidebar --> <section
			class="main-content">