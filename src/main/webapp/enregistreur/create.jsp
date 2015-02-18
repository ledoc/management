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
	<jsp:param value="active" name="menuOuvrageActive" />
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
									<label for="mid">Identifiant DW</label>
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
									<label for="coeffTemperature">coefficient de
										température</label>
									<form:input type="text" class="form-control"
										id="coeffTemperature" path="coeffTemperature" placeholder=""
										data-parsley-trigger="change" step="any"
										data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="altitude">Altitude (mètre)</label>
									<form:input type="text" class="form-control" id="altitude"
										path="altitude" placeholder="" data-parsley-trigger="change"
										step="any" data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="echelleCapteur">Pleine échelle capteur</label>
									<form:input type="text" class="form-control"
										id="echelleCapteur" path="echelleCapteur" placeholder=""
										data-parsley-trigger="change" step="any"
										data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="sonde">n° de sonde</label>
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
									<sec:authorize ifAllGranted="ADMIN">
										<button type="submit" class="btn btn-outline btn-primary">${labelCreateUpdate}</button>
									</sec:authorize>
								</div>
							</div>
							<!--  2eme colonne -->
							<div class="col-md-4">
								<div class="panel panel-default no-b">
									<div class="form-group">
										<label for="niveauManuel">Niveau manuel</label>
										<sec:authorize ifAllGranted="ADMIN">
											<div class="text-primary">
												<a id="creation-niveau-manuel" data-toggle="modal"
													data-target="#modal-creation-niveau-manuel" class="text-info">Enregistrer
													un nouveau niveau manuel</a>
											</div>
										</sec:authorize>
										<form:input readonly="true" type="text"
											class="transfertInput form-control" id="niveauManuel"
											path="niveauManuel.valeur" />
									</div>
									<div class="form-group">
										<label for="derniereMesure">Dernière mesure</label> <input
											type="text" class="transfertInput form-control"
											id="derniereMesure" value="${derniereMesure.valeur}"
											readonly="true" />
									</div>
									<div class="form-group">
										<label for="batterie">Valeur maximum de la batterie en
											volts</label>
										<form:input type="text" class="transfertInput form-control"
											id="batterie" path="batterie" placeholder=""
											data-parsley-trigger="change" step="any"
											data-parsley-type="number"
											data-parsley-type-message="valeur numérique"
											data-parsley-mincheck="2"
											data-parsley-mincheck-message="2 caractères minimum"
											readonly="${readOnlyValue }" />
									</div>
									<div class="form-group">
										<label for="niveauBatterie">Niveau batterie en %</label>
										<form:input class="transfertInput form-control"
											id="niveauBatterie" path="niveauBatterie" placeholder=""
											data-parsley-trigger="change" data-parsley-type="number"
											readonly="${readOnlyValue }" />
									</div>

									<div class="checkbox checkbox-inline">
										<form:checkbox id="maintenance" path="maintenance"
											label="Maintenance" disabled="${readOnlyValue }" />
									</div>
									<c:if test="${not empty listNiveauxManuels }">
										<header class="panel-heading clearfix brtl brtr no-b">
											<div class="overflow-hidden">
												<span class="h4 show no-m pt10">Historiques niveaux
													manuels</span>
											</div>
										</header>
										<div class="list-group ">
											<table class="no-b no-m">
												<thead>
													<tr>
														<th>Date</th>
														<th>Valeur</th>
														<sec:authorize ifAllGranted="ADMIN">
															<th class="nosort nosearch">Actions</th>
														</sec:authorize>
													</tr>
												</thead>
												<c:forEach items="${listNiveauxManuels}" var="niveauManuel">
													<c:url var="urlMesureDelete"
														value="/mesure/delete/${niveauManuel.id}/${enregistreur.id}" />
													<tbody>
														<tr>

															<td class=""><fmt:formatDate
																	value="${niveauManuel.date}"
																	pattern="dd-MM-yyyy hh:mm:ss" /></td>
															<td><div class="list-group-item">
																	<c:out value="${niveauManuel.valeur}" />
																</div></td>
															<sec:authorize ifAllGranted="ADMIN">
																<td><a data-url="${urlMesureDelete}"
																	data-toggle="modal" data-target="#confirmModal"
																	class="btn btn-outline btn-danger btn-xs js-confirm-btn"><i
																		class="fa fa-remove"></i> </a></td>
															</sec:authorize>
														</tr>
													</tbody>
												</c:forEach>
											</table>
										</div>
									</c:if>

								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>

		<!-- Fenetre modale -->
		<div id="confirmModal" class="modal fade bs-modal-sm" tabindex="-1"
			role="dialog" aria-hidden="true" data-backdrop="false"
			data-show="false">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title">Confirmation de suppression</h4>
					</div>
					<div class="modal-body">
						<p>Supprimer cette ligne ?</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
						<a id="js-confirm-button" class="btn btn-success">Confirmer</a>
					</div>
				</div>
			</div>
		</div>
		<!-- /Fenetre modale -->


		<!--  modal window for create niveau manuel -->
		<div id="modal-creation-niveau-manuel" class="modal fade bs-modal-sm creation-niveau-manuel" tabindex="-1" role="dialog"
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
							<c:url var="createNiveauManuel"
								value="/enregistreur/redirect/enregistreur" />
							<form:form id="form" method="POST" action="${createNiveauManuel}"
								modelAttribute="enregistreur" role="form"
								class="parsley-form clearfix" data-validate="parsley"
								data-show-errors="true">

								<input type="hidden" name="niveauManuel.id" value="" />
								<input type="hidden" name="niveauManuel.typeMesure"
									value="NIVEAUMANUEL" />

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
								<form:hidden id="data-alerteDescriptions" path="alerteDescriptions" />
								<form:hidden id="data-echelleCapteur" path="echelleCapteur" />
								<form:hidden id="data-altitude" path="altitude" />
								<form:hidden id="data-coeffTemperature" path="coeffTemperature" />

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
									<label for="date">Date (dd-mm-yyyy hh:mm:ss)</label>
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
										class="btn btn-outline btn-primary">Créer nouveau</button>
								</div>
							</form:form>
						</div>
					</div>

				</div>
			</div>
		</div>
		<!--  modal window for createniveau manuel -->

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

			$('#modal-creation-niveau-manuel').modal();
			$('#modal-creation-niveau-manuel').on('show.bs.modal', function(e) {
				console.log(map);
				Object.keys(map).forEach(function(element, index, array) {
					$('#data-' + element).val(map[element]);
					console.log(map.element);
				});
			});

			$('#confirmModal').modal();
			$('#confirmModal').on('show.bs.modal', function(e) {
				var url = $(e.relatedTarget).data('url');
				var $confirmButton = $('#js-confirm-button');
				$confirmButton.attr('href', url);
			});
		</script>