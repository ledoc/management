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
	<jsp:param value="active" name="menuMesureActive" />
	<jsp:param value="Solices - Détails Mesure" name="titreOnglet" />
</jsp:include>

<c:url var="urlResources" value="/resources" />
<!-- Seulement une visualisation pour les clients -->
<sec:authorize ifAllGranted="ADMIN">
	<c:set var="readOnlyValue" value="false"></c:set>
	<c:if test="${empty mesure.id}">
		<c:set var="sentenceCreateUpdate" value="créer" />
		<c:set var="labelCreateUpdate" value="Créer" />
		<c:set var="textCreateUpdate" value="Création" />
	</c:if>
	<c:if test="${not empty mesure.id}">
		<c:set var="sentenceCreateUpdate" value="mettre à jour" />
		<c:set var="labelCreateUpdate" value="Mettre à jour" />
		<c:set var="textCreateUpdate" value="Mise à jour" />
	</c:if>
</sec:authorize>
<sec:authorize ifAllGranted="CLIENT">
	<c:set var="readOnlyValue" value="true"></c:set>
	<c:set var="sentenceCreateUpdate" value="visualiser" />
	<c:set var="labelCreateUpdate" value="OK" />
	<c:set var="textCreateUpdate" value="Détails" />
</sec:authorize>


<section class="layout">
	<!-- /sidebar -->
	<section class="main-content bg-white rounded shadow">
		<!-- content wrapper -->
		<div class="content-wrap clearfix pt15">
			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="panel">
					<header class="panel-heading no-b col-lg-offset-2">
						<h1 class="h3 text-primary mt0">Création d'une mesure
							manuelle</h1>
						<p class="text-muted">Le formulaire permet de ${sentenceCreateUpdate} une mesure
							manuelle.</p>
					</header>
					<div class="panel-body">
						<c:url var="createMesure" value="/mesure/create" />
						<form:form id="form" method="POST" action="${createMesure}"
							modelAttribute="mesure" role="form" class="parsley-form"
							data-validate="parsley" data-show-errors="true">

							<form:hidden path="id" />

							<div class="col-md-4 col-lg-4 col-md-4 col-xs-12 col-lg-offset-2">

								<div class="form-group">
									<label for="ouvrage">Attacher un enregistreur</label>
									<form:select id="enregistreursCombo" name="enregistreursCombo"
										path="enregistreur" items="${enregistreursCombo}"
										itemValue="id" itemLabel="mid"
										data-placeholder=" Sélectionnez
							un enregistreur"
										class="form-control chosen">
									</form:select>
								</div>
								<div class="form-group">
									<label for="typesMesureCombo">Choisir un type de mesure</label>
									<form:select id="typesMesureCombo" name="typesMesureCombo"
										path="typeMesure" items="${typesMesureCombo}"
										data-placeholder="Sélectionnez 
							un type"
										itemLabel="description" class="form-control chosen"
										data-parsley-required="true">
									</form:select>
								</div>
								<div id="valeur" class="form-group">
									<label for="valeur">Valeur</label>
									<form:input class="form-control" id="valeur" path="valeur"
										placeholder="" data-parsley-trigger="change" step="any"
										data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum" />
								</div>
								<div class="form-group">
									<label for="date">Date</label>
									<form:input class="form-control" id="date"
										path="date" placeholder="dd-mm-yyyy hh:mm:ss"
										data-parsley-required="true" data-parsley-trigger="change"
										data-parsley-pattern="([0-2]{1}\d{1}|[3]{1}[0-1]{1})(?:\-)?([0]{1}\d{1}|[1]{1}[0-2]{1})(?:\-)?(\d{2}|\d{4})(?:\s)?([0-1]{1}\d{1}|[2]{1}[0-3]{1})(?::)?([0-5]{1}\d{1})(?::)?([0-5]{1}\d{1})"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum" />
								</div>

								<div class="pull-right">
									<a href="<c:url  value="/mesure/list" />"
										class="btn btn-default btn-outline">Retour</a>
									<button type="submit" class="btn btn-outline btn-primary">Créer</button>
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