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
	<jsp:param value="active" name="menuEnregistreurActive" />
	<jsp:param value="Solices - Détails Enregistreur" name="titreOnglet" />
</jsp:include>

<c:url var="urlResources" value="/resources" />

<c:url var="initNiveauManuelUrl"
	value="/enregistreur/init/niveau/manuel" />
<a class="relayUrl" href="${initNiveauManuelUrl}"></a>



<!-- Seulement une visualisation pour les clients -->
<sec:authorize ifAllGranted="ADMIN">
	<c:set var="readOnlyValue" value="false"></c:set>
	<c:if test="${empty enregistreur.id}">
		<c:set var="sentenceCreateUpdate" value="créer" />
		<c:set var="labelCreateUpdate" value="Créer" />
		<c:set var="textCreateUpdate" value="Création" />
	</c:if>
	<c:if test="${not empty enregistreur.id}">
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
						<h1 class="h3 text-primary mt0">${textCreateUpdate}d'un
							Enregistreur</h1>
						<p class="text-muted">Permet de ${sentenceCreateUpdate} un
							enregistreur.</p>
					</header>
					<div class="panel-body">
						<c:url var="createEnregistreur" value="/enregistreur/create" />
						<form:form id="form" method="POST" action="${createEnregistreur}"
							modelAttribute="enregistreur" role="form" class="parsley-form"
							data-validate="parsley" data-show-errors="true"
							readonly="${readOnlyValue }">

							<form:hidden class="transfertInput" path="id" />

							<div class="col-md-4 col-lg-4 col-md-4 col-xs-12 col-lg-offset-2">
								<div class="form-group">
									<label for="mid">MID</label>
									<form:input type="text" class="transfertInput form-control"
										id="mid" path="mid" placeholder=""
										data-parsley-required="true" data-parsley-trigger="change"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="ouvrage">Ouvrage </label>
									<form:input readonly="true" type="text" class="form-control"
										id="ouvrage" path="ouvrage.codeOuvrage" />
								</div>
								<div class="form-group">
									<label for="niveauManuel">Niveau manuel</label>
									<div class="text-primary">
										<a id="creation-niveau-manuel" data-toggle="modal"
											data-target=".bs-modal-sm" class="text-info">Enregistrer
											un nouveau niveau manuel</a>
									</div>
									<%-- 									<form:hidden path="niveauManuel.id" /> --%>
									<form:input readonly="true" type="text"
										class="transfertInput form-control" id="niveauManuel"
										path="niveauManuel.valeur" />
								</div>

								<div class="form-group">
									<label for="derniereMesure">Dernière mesure</label>
									<form:hidden path="derniereMesure.id" />
									<input type="text" class="transfertInput form-control"
										id="derniereMesure" value="${derniereMesure.valeur}"
										readonly="true" />
								</div>
								<div class="form-group">
									<label for="sonde">Sonde</label>
									<form:input type="text" class="form-control" id="sonde"
										path="sonde" data-parsley-trigger="change" placeholder=""
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="trasmission">Transmission</label>
									<form:input type="text" class="transfertInput form-control"
										id="transmission" path="transmission" placeholder=""
										data-parsley-trigger="change" data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="sim">SIM</label>
									<form:input type="text" class="transfertInput form-control"
										id="sim" path="sim" placeholder=""
										data-parsley-trigger="change" data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="panneauSolaire">Panneau solaire</label>
									<form:input type="text" class="transfertInput form-control"
										id="panneauSolaire" path="panneauSolaire" placeholder=""
										data-parsley-trigger="change" data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="pull-right">
									<a
										href="<c:url  value="/ouvrage/update/${enregistreur.ouvrage.id}" />"
										class="btn btn-default btn-outline">Retour</a>
									<button type="submit" class="btn btn-outline btn-primary">${labelCreateUpdate}</button>
								</div>
							</div>
							<!--  2eme colonne -->
							<div class="col-md-4">
								<div class="panel panel-default no-b">
									<div class="form-group">
										<label for="batterie">Batterie</label>
										<form:input type="text" class="transfertInput form-control"
											id="batterie" path="batterie" placeholder=""
											data-parsley-trigger="change" data-parsley-mincheck="2"
											data-parsley-mincheck-message="2 caractères minimum"
											readonly="${readOnlyValue }" />
									</div>
									<div class="form-group">
										<label for="niveauBatterie">Niveau batterie</label>
										<form:input class="transfertInput form-control"
											id="niveauBatterie" path="niveauBatterie" placeholder=""
											data-parsley-trigger="change" data-parsley-type="number"
											readonly="${readOnlyValue }" />
									</div>
									<div class="form-group">
										<label for="maintenance">Maintenance</label>
										<form:radiobutton class="transfertInput" id="maintenance"
											path="maintenance" label="oui" value="true" />
										<form:radiobutton class="transfertInput" id="maintenance"
											path="maintenance" label="non" value="false"
											disabled="${readOnlyValue }" />
									</div>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>

		<!--  modal window for create or update niveau manuel -->
		<div class="modal fade bs-modal-sm" tabindex="-1" role="dialog"
			aria-hidden="true" data-backdrop="false" data-show="false">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title">Nouvelle mesure de niveau manuel</h4>
					</div>
					<div class="modal-body">
						<div>
							<c:url var="createNiveauManuel" value="/enregistreur/init" />
							<form:form id="form" method="POST" action="${createNiveauManuel}"
								modelAttribute="enregistreur" role="form"
								class="parsley-form clearfix" data-validate="parsley"
								data-show-errors="true">

								<form:hidden id="data-id" path="id" />
								<form:hidden id="data-mid" path="mid" />
								<form:hidden id="data-maintenance" path="maintenance" />
								<form:hidden id="data-derniereMesure" path="derniereMesure" />
								<form:hidden id="data-modem" path="modem" />
								<form:hidden id="data-transmission" path="transmission" />
								<form:hidden id="data-sim" path="sim" />
								<form:hidden id="data-batterie" path="batterie" />
								<form:hidden id="data-niveauBatterie" path="niveauBatterie" />
								<form:hidden id="data-panneauSolaire" path="panneauSolaire" />
								<form:hidden id="data-sonde" path="sonde" />
								<form:hidden id="data-croquis" path="croquis" />
								<form:hidden id="data-alertesActives" path="alertesActives" />

								<div class="col-xs-6">
									<label for="typesMesureCombo">Type de mesure</label>
									<form:select id="typesMesureCombo" name="typesMesureCombo"
										path="niveauManuel.typeMesure" items="${typesMesureCombo}"
										data-placeholder="Sélectionnez 
							un type"
										itemLabel="description" data-parsley-required="true"
										class="form-control" disabled="${readOnlyValue }">
									</form:select>
								</div>

								<div class="col-xs-6">
									<label for="valeur">Valeur sans unité</label>
									<form:input class="form-control" id="valeur"
										path="niveauManuel.valeur" placeholder=""
										data-parsley-trigger="change" step="any"
										data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										disabled="${readOnlyValue }" />
								</div>
								<div class="col-xs-6">
									<label for="date">Date: dd-mm-yyyy hh:mm:ss</label>
									<form:input class="form-control" id="date"
										path="niveauManuel.date" placeholder="dd-mm-yyyy hh:mm:ss"
										data-parsley-required="true" data-parsley-trigger="change"
										data-parsley-pattern="([0-2]{1}\d{1}|[3]{1}[0-1]{1})(?:\-)?([0]{1}\d{1}|[1]{1}[0-2]{1})(?:\-)?(\d{2}|\d{4})(?:\s)?([0-1]{1}\d{1}|[2]{1}[0-3]{1})(?::)?([0-5]{1}\d{1})(?::)?([0-5]{1}\d{1})"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										disabled="${readOnlyValue }" />
								</div>
								<div class="modal-footer col-xs-12 no-b">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
									<button id="validate-creation-niveau-manuel" type="submit"
										class="btn btn-outline btn-primary">${labelCreateUpdate}</button>
								</div>
							</form:form>
						</div>
					</div>

				</div>
			</div>
		</div>
		<!--  modal window for create or update niveau manuel -->

		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<jsp:include page="/template/footer.jsp" />


		<script type="text/javascript">
			var map = {};
			$('#creation-niveau-manuel').click(function() {
				$(".transfertInput").each(function() {
					map[$(this).attr("id")] = $(this).val();
				});
				console.log(map);
			})

			$('.bs-modal-sm').modal();
			$('.bs-modal-sm').on('show.bs.modal', function(e) {
				console.log(map);
				Object.keys(map).forEach(function(element, index, array) {
					$('#data-' + element).val(map[element]);
					console.log(map.element);
				});
			});
		</script>