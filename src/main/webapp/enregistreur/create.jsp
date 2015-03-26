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
						<h1 class="h3 text-primary mt0">${textCreateUpdate}&nbspd'un
							Enregistreur</h1>
						<sec:authorize ifAllGranted="ADMIN">
							<c:if test="${empty enregistreur.id}">
								<p class="text-muted">Pour ajouter un ou des capteurs
									l'enregistreur doit d'abord être créé.</p>
							</c:if>
							<c:if test="${not empty enregistreur.id}">
								<p class="text-muted">Ajouter et consulter les capteurs en
									bas de page.</p>
							</c:if>
						</sec:authorize>
					</header>
					<div class="panel-body">
						<c:url var="createEnregistreur" value="/enregistreur/create" />
						<form:form id="form" method="POST" action="${createEnregistreur}"
							modelAttribute="enregistreur" role="form" class="parsley-form"
							data-validate="parsley" data-show-errors="true"
							readonly="${readOnlyValue }">

							<!--  Construction d'URL utiles -->
							<c:url var="urlResources" value="/resources" />
							<c:url var="capteurUrl" value="/capteur" />
							<a class="capteurUrl" href="${capteurUrl}"></a>

							<form:hidden class="transfertInput" path="id" />

							<div class="col-md-4 col-lg-4 col-md-4 col-xs-12 col-lg-offset-2">

								<form:errors path="*"
									cssClass="alert alert-danger alert-dismissible fade in"
									element="div" />

								<div class="form-group">
									<label for="typeEnregistreur">Analogique / numérique</label>
									<form:select id="typeEnregistreur" path="typeEnregistreur"
										data-parsley-required="true"
										data-parsley-required-message="Choix requis"
										class="form-control chosen" disabled="${readOnlyValue }">
										<form:option value="" label="--- Choisir un type ---" />
										<form:options items="${typesEnregistreurCombo}"
											itemLabel="description" />
									</form:select>
								</div>

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
									<label for="nom">Nom</label>
									<form:input type="text" class="transfertInput form-control"
										id="nom" path="nom" placeholder=""
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
									<label for="coeffTemperature">Coefficient de
										température</label>
									<form:input type="text" class="form-control"
										id="coeffTemperature" path="coeffTemperature" placeholder=""
										data-parsley-trigger="change" step="any"
										data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="salinite">Taux de salinité (g/kg)</label>
									<form:input class="form-control" id="salinite" path="salinite"
										placeholder="" data-parsley-trigger="change" step="any"
										data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										data-parsley-required="true"
										data-parsley-required-message="Choix requis"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="altitude">Altitude de la mesure (mètres)</label>
									<form:input type="text" class="form-control" id="altitude"
										path="altitude" placeholder="" data-parsley-trigger="change"
										step="any" data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										data-parsley-required="true"
										data-parsley-required-message="Choix requis"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="commentaire">Commentaire</label>
									<form:textarea type="text" class="form-control"
										id="commentaire" path="commentaire" placeholder=""
										data-parsley-trigger="change" data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										data-parsley-maxcheck="255"
										data-parsley-maxcheck-message="255 caractères maximum"
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

									<div class="checkbox checkbox-inline">
										<form:checkbox id="maintenance" path="maintenance"
											label="Maintenance" disabled="${readOnlyValue }" />
									</div>
								</div>
							</div>

							<!--  tableaux des capteurs associés -->
							<div class="col-lg-offset-2 col-lg-8 col-md-12 col-xs-12">

								<c:set var="capteurs" value="${enregistreur.capteurs}" />
								<c:if test="${empty capteurs }">
									<sec:authorize ifAllGranted="ADMIN">
										<c:if test="${not empty enregistreur.id}">
											<div class="mb15">
												<c:url var="urlCapteurCreate"
													value="/capteur/create/${enregistreur.id}" />
												<a id="creation-capteur" href="${urlCapteurCreate }"
													class="btn btn-outline btn-primary btn-s"> Nouveau
													capteur</a>
											</div>
										</c:if>
									</sec:authorize>
								</c:if>
								<c:if test="${not empty capteurs }">

									<sec:authorize ifAllGranted="ADMIN">

										<c:if test="${not empty enregistreur.id}">
											<div class="pull-right mb15">
												<c:url var="urlCapteurCreate"
													value="/capteur/create/${enregistreur.id}" />
												<a id="creation-capteur" href="${urlCapteurCreate }"
													class="btn btn-outline btn-primary btn-xs"> Nouveau
													capteur</a>
											</div>
										</c:if>

									</sec:authorize>
									<table class="table table-striped no-m">
										<thead>
											<tr>
												<th>Capteur</th>
												<th>Dernière mesure</th>
												<th>Niveau manuel</th>
												<sec:authorize ifAllGranted="ADMIN">
													<th class="nosort nosearch">Actions</th>
												</sec:authorize>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${capteurs}" var="capteur">
												<c:url var="urlCapteurDelete"
													value="/capteur/delete/${capteur.id}" />
												<c:url var="urlCapteurUpdate"
													value="/capteur/update/${capteur.id}" />
												<c:url var="urlMesuresView"
													value="/mesure/list/capteur/${capteur.id}" />
												<tr>
													<td class="text-primary"><a href="${urlCapteurUpdate}">${capteur.typeCaptAlerteMesure.description}</a></td>
													<td><c:out value="${capteur.derniereMesure.valeur}" /></td>
													<td><c:out value="${capteur.niveauManuel.valeur}" /></td>
													<sec:authorize ifAllGranted="ADMIN">
														<td><a data-url="${urlCapteurDelete}"
															data-toggle="modal" data-target="#confirmModal"
															class="btn btn-outline btn-danger btn-xs js-confirm-btn">
																<i class="fa fa-remove"></i>
														</a></td>
													</sec:authorize>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
							</div>
							<!--  \tableaux des capteurs associés -->
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
						<h4 class="modal-title text-primary">Confirmation de suppression</h4>
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

		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<jsp:include page="/template/footer.jsp" />


		<script type="text/javascript">
			$('#confirmModal').modal();
			$('#confirmModal').on('show.bs.modal', function(e) {
				var url = $(e.relatedTarget).data('url');
				var $confirmButton = $('#js-confirm-button');
				$confirmButton.attr('href', url);
			});
		</script>