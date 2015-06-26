<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menupoidsActive" />
	<jsp:param value="Solices - Détails poids" name="titreOnglet" />
</jsp:include>

<c:url var="urlResources" value="/resources" />

<!-- Seulement une visualisation pour les clients -->
	<c:set var="readOnlyValue" value="false"></c:set>
	<c:if test="${empty poids.id}">
		<c:set var="sentenceCreateUpdate" value="créer" />
		<c:set var="labelCreateUpdate" value="Créer" />
		<c:set var="textCreateUpdate" value="Création" />
	</c:if>
	<c:if test="${not empty poids.id}">
		<c:set var="sentenceCreateUpdate" value="mettre à jour" />
		<c:set var="labelCreateUpdate" value="Mettre à jour" />
		<c:set var="textCreateUpdate" value="Mise à jour" />
	</c:if>

<section class="layout">
	<!-- /sidebar -->
	<section class="main-content bg-white rounded shadow">
		<!-- content wrapper -->


		<div class="content-wrap clearfix pt15">
			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="panel">
					<header class="panel-heading no-b col-lg-offset-2">
						<h1 class="h3 text-primary mt0">${textCreateUpdate}&nbspd'un
							poids</h1>
						<p class="text-muted">Permet de ${sentenceCreateUpdate} un
							poids.</p>
					</header>
					<div class="panel-body">
						<c:url var="createPoids" value="/poids/create" />
						<form:form id="form" method="POST" action="${createPoids}"
							modelAttribute="poids" role="form" class="parsley-form"
							data-validate="parsley" data-show-errors="true">

							<form:hidden path="id" />

							<div class="col-md-4 col-lg-4 col-md-4 col-xs-12 col-lg-offset-2">
								<div class="form-group">
									<label for="nom">Poids</label>
									<form:input type="text" class="form-control" id="valeur"
										path="valeur" placeholder="" data-parsley-required="true"
										data-parsley-trigger="change"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum" />
								</div>
								<div class="form-group">
									<label for="date">Date</label>
									<form:input type="date" class="form-control" id="date"
										path="date" placeholder="" data-parsley-required="true"
										data-parsley-trigger="change"
										data-parsley-required-message="Champ requis" />
								</div>
								
								<div class="pull-right">
									<a href="<c:url  value="/poids/list" />"
										class="btn btn-default btn-outline">Retour</a>
										<button type="submit" class="btn btn-outline btn-primary">${labelCreateUpdate}</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<jsp:include page="/template/footer.jsp" />