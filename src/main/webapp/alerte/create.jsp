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
	<jsp:param value="active" name="menuAlerteActive" />
	<jsp:param value="Solices - Détail Alerte" name="titreOnglet" />
</jsp:include>

<!-- Seulement une visualisation pour les clients -->
<sec:authorize ifAllGranted="ADMIN">
	<c:set var="readOnlyValue" value="false"></c:set>
	<c:if test="${empty alerte.id}">
		<c:set var="sentenceCreateUpdate" value="créer" />
		<c:set var="labelCreateUpdate" value="Créer" />
		<c:set var="textCreateUpdate" value="Création" />
	</c:if>
	<c:if test="${not empty alerte.id}">
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
						<h1 class="h3 text-primary mt0">${textCreateUpdate} d'une
							Alerte</h1>
						<p class="text-muted">Permet de ${sentenceCreateUpdate} une
							alerte</p>
					</header>

					<div class="panel-body">
						<c:url var="createAlerteDescription"
							value="/alerte/description/create" />
						<form:form id="form" method="POST"
							action="${createAlerteDescription}" modelAttribute="alerte"
							role="form" class="parsley-form" data-validate="parsley"
							data-show-errors="true">

							<form:hidden path="id" />
							<form:hidden path="compteurRetourNormal" />
							<form:hidden path="aSurveiller" />

							<div class="col-md-4 col-lg-4 col-md-4 col-xs-12 col-lg-offset-2">
								<div class="form-group">
									<label for="nom">Code</label>
									<form:input type="text" class="form-control" id="codeAlerte"
										path="codeAlerte" placeholder="" data-parsley-trigger="change"
										data-parsley-required="true"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="checkbox checkbox-inline">
									<form:checkbox id="activation" path="activation"
										label="Activation" disabled="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="site">Rattacher à un Enregistreur
										(identifiant DW)</label>
									<form:select class="form-control chosen-select"
										data-placeholder="Choisir un enregistreur ..."
										data-parsley-required="true" data-parsley-trigger="change"
										data-parsley-required-message="Champ requis"
										path="enregistreur.id" disabled="${readOnlyValue }">
										<form:option value=""></form:option>
										<form:options items="${enregistreursCombo}" itemValue="id"
											itemLabel="mid" />
									</form:select>
								</div>
								<div class="form-group">
									<label for="typeAlerte">Type de l'alerte</label>
									<form:select id="typeAlerte" name="typeAlerte"
										path="typeAlerte" data-parsley-required="true"
										data-parsley-required-message="Champ requis"
										class="form-control chosen" disabled="${readOnlyValue }">
										<form:option value="" label="--- Choisir un type ---" />
										<form:options items="${typesAlerteCombo}"
											itemLabel="description" />
									</form:select>
								</div>
								<div class="form-group">
									<label for="tendance">Tendance</label>
									<form:select id="tendance" name="tendance" path="tendance"
										data-parsley-required="true"
										data-parsley-required-message="Champ requis"
										class="form-control chosen" disabled="${readOnlyValue }">
										<form:option value="" label="--- Choisir une tendance ---" />
										<form:options items="${tendancesAlerteCombo}"
											itemLabel="description" />
									</form:select>
								</div>

								<div class="form-group">
									<label for="seuilPreAlerte">Seuil pré-alerte</label>
									<form:input type="text" class="form-control"
										id="seuilPreAlerte" path="seuilPreAlerte" placeholder=""
										data-parsley-trigger="change" data-parsley-required="true"
										data-parsley-required-message="Champ requis" step="any"
										data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="seuilAlerte">Seuil d'alerte</label>
									<form:input type="text" class="form-control" id="seuilAlerte"
										path="seuilAlerte" placeholder=""
										data-parsley-trigger="change" data-parsley-required="true"
										data-parsley-required-message="Champ requis" step="any"
										data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="intitule">Intitulé</label>
									<form:input type="text" class="form-control" id="intitule"
										path="intitule" placeholder="" data-parsley-trigger="change"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="pull-right">
									<a href="<c:url  value="/alerte/list" />"
										class="btn btn-default btn-outline">Retour</a>
									<sec:authorize ifAllGranted="ADMIN">
										<button type="submit" class="btn btn-outline btn-primary">${labelCreateUpdate}</button>
									</sec:authorize>
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