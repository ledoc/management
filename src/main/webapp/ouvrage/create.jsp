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
	<jsp:param value="Solices - Détail Ouvrage" name="titreOnglet" />
</jsp:include>

<c:url var="initTypeOuvrage" value="/ouvrage/init/type/ouvrage" />
<a class="relayUrl" href="${initTypeOuvrage}"></a>


<!-- Seulement une visualisation pour les clients -->
<sec:authorize ifAllGranted="ADMIN">
	<c:set var="readOnlyValue" value="false"></c:set>
	<c:if test="${empty ouvrage.id}">
		<c:set var="sentenceCreateUpdate" value="créer" />
		<c:set var="labelCreateUpdate" value="Créer" />
		<c:set var="textCreateUpdate" value="Création" />
	</c:if>
	<c:if test="${not empty ouvrage.id}">
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
							Ouvrage</h1>
						<p class="text-muted">Pour ajouter un ou des enregistreurs
							l'ouvrage doit d'abord être créer</p>
					</header>

					<div class="panel-body">
						<c:url var="createOuvrage" value="/ouvrage/create" />
						<form:form id="form" method="POST" action="${createOuvrage}"
							modelAttribute="ouvrage" role="form" class="parsley-form"
							data-validate="parsley" data-show-errors="true">

							<form:hidden path="id" />

							<div class="col-md-4 col-lg-4 col-md-4 col-xs-12 col-lg-offset-2">
								<div class="form-group">
									<label for="typeOuvrage">Type de l'ouvrage</label> <input
										id="typeOuvrage">


									<!-- 										<select -->
									<!-- 										id="typeOuvrage" -->
									<!-- 										data-placeholder="--- Choisir un type ---" -->
									<!-- 										class="form-control" data-parsley-required="true" -->
									<!-- 										data-parsley-required-message="Champ requis" -->
									<!-- 										onchange="javascript:activateNappeOrSurface();" class="" -->
									<%-- 										disabled="${readOnlyValue}"> --%>
									<!-- 									</select> -->
								</div>
								<div class="form-group">
									<label for="nom">Nom</label>
									<form:input type="text" class="form-control" id="nom"
										path="nom" placeholder="" data-parsley-trigger="change"
										data-parsley-required="true"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="codeOuvrage">Code de l'ouvrage</label>
									<form:input type="text" class="form-control" id="codeOuvrage"
										path="codeOuvrage" placeholder="" data-parsley-required="true"
										data-parsley-trigger="change"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="site">Rattacher à un site</label>
									<form:select class="form-control chosen-select"
										data-placeholder="Choisir un etablissement ..."
										data-parsley-required="true" data-parsley-trigger="change"
										data-parsley-required-message="Champ requis" path="site.id"
										readonly="${readOnlyValue }">
										<form:option value=""></form:option>
										<form:options items="${sitesCombo}" itemValue="id"
											itemLabel="codeSite" />
									</form:select>


								</div>
								<div class="form-group">
									<label for="coordonneesGeographique">Coordonnées
										géographiques (format Lambert93)</label>
									<form:input type="text" class="form-control"
										id="coordonneesGeographique" path="coordonneesGeographique"
										placeholder="" data-parsley-required="true"
										data-parsley-trigger="change"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="checkbox checkbox-inline">
									<form:checkbox id="asservissement" path="asservissement"
										onchange="javascript:activateOuvrageMaitre();"
										label="Asservissement" disabled="${readOnlyValue }" />
								</div>
								<div style="display: none;" id="ouvrageMaitre"
									class="form-group">
									<label for="ouvrageMaitre">Ouvrage maître</label>
									<form:select autocomplete="true" id="ouvragesCombo"
										name="ouvragesCombo" path="ouvrageMaitre.id"
										class="form-control"
										data-placeholder=" Sélectionnez
							un ouvrage maître"
										readonly="${readOnlyValue }">
										<form:option value=""></form:option>
										<form:options items="${ouvragesCombo}" itemValue="id"
											itemLabel="codeOuvrage" />

									</form:select>
								</div>
								<div class="form-group">
									<label for="commentaire">Commentaire</label>
									<form:textarea type="text" class="form-control"
										id="commentaire" path="commentaire" placeholder=""
										data-parsley-trigger="change" data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="pull-right">
									<a href="<c:url  value="/ouvrage/list" />"
										class="btn btn-default btn-outline">Retour</a>
									<button type="submit" class="btn btn-outline btn-primary">${labelCreateUpdate}</button>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
									<label for="coteSolNGF">Cote Sol / NGF</label>
									<form:input type="text" class="form-control" id="coteSolNGF"
										path="coteSolNGF" placeholder="" data-parsley-trigger="change"
										step="any" data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="coteRepereNGF">Cote repère NGF</label>
									<form:input type="text" class="form-control" id="coteRepereNGF"
										path="coteRepereNGF" placeholder=""
										data-parsley-trigger="change" step="any"
										data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div style="display: none;" class="eaudesurface form-group">
									<label for="coteSol">Cote Sol Berge (eau de surface)</label>
									<form:input class="form-control" id="coteSol" path="coteSol"
										placeholder="" data-parsley-trigger="change" step="any"
										data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div style="display: none;" class="nappesouterraine form-group">
									<label for="numeroBSS">Numéro BSS (nappe souterraine)</label>
									<form:input type="text" class="form-control" path="numeroBSS"
										placeholder="" data-parsley-trigger="change"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div style="display: none;" class="nappesouterraine form-group">
									<label for="mesureProfondeur">Mesure repère NGF /
										Niveau Statique initial, en mètres</label>
									<form:input type="number" class="form-control"
										id="mesureProfondeur" path="mesureProfondeur" placeholder=""
										data-parsley-trigger="change" step="any"
										data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div style="display: none;" class="nappesouterraine form-group">
									<label for="mesureRepereNGFSol">Mesure repère NGF /
										Fond, en mètres </label>
									<form:input type="number" class="form-control"
										path="mesureRepereNGFSol" placeholder=""
										data-parsley-trigger="change" step="any"
										data-parsley-type="number"
										data-parsley-type-message="valeur numérique"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
							</div>


							<!--  tableaux des enregistreurs associés -->
							<div class="col-lg-offset-2 col-lg-8 col-md-12 col-xs-12">

								<c:set var="enregistreurs" value="${ouvrage.enregistreurs}" />
								<c:if test="${empty enregistreurs }">
									<sec:authorize ifAllGranted="ADMIN">
										<c:if test="${not empty ouvrage.id}">
											<div class="mb15">
												<c:url var="urlEnregistreurCreate"
													value="/enregistreur/create/${ouvrage.id}" />
												<a id="creation-enregistreur"
													href="${urlEnregistreurCreate }"
													class="btn btn-outline btn-primary btn-s"> nouvel
													enregistreur</a>
											</div>
										</c:if>
									</sec:authorize>
								</c:if>
								<c:if test="${not empty enregistreurs }">

									<sec:authorize ifAllGranted="ADMIN">

										<c:if test="${not empty ouvrage.id}">
											<div class="pull-right mb15">
												<c:url var="urlEnregistreurCreate"
													value="/enregistreur/create/${ouvrage.id}" />
												<a id="creation-enregistreur"
													href="${urlEnregistreurCreate }"
													class="btn btn-outline btn-primary btn-xs"> nouvel
													enregistreur</a>
											</div>
										</c:if>

									</sec:authorize>
									<table class="table table-striped no-m">
										<thead>
											<tr>
												<th>MID</th>
												<th>Sonde</th>
												<th>Dernière mesure</th>
												<th>Niveau manuel</th>
												<th class="nosort nosearch">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${enregistreurs}" var="enregistreur">
												<c:url var="urlEnregistreurDelete"
													value="/enregistreur/delete/${enregistreur.id}" />
												<c:url var="urlEnregistreurUpdate"
													value="/enregistreur/update/${enregistreur.id}" />
												<c:url var="urlMesuresView"
													value="/mesure/list/enregistreur/${enregistreur.id}" />
												<tr>
													<td class="text-primary"><a
														href="${urlEnregistreurUpdate}">${enregistreur.mid}</a></td>
													<td><c:out value="${enregistreur.sonde}" /></td>
													<td><c:out
															value="${enregistreur.derniereMesure.valeur}" /></td>
													<td><c:out value="${enregistreur.niveauManuel.valeur}" /></td>
													<td><a data-url="${urlEnregistreurDelete}"
														data-toggle="modal" data-target="#confirmModal"
														class="btn btn-outline btn-danger btn-xs js-confirm-btn">
															<i class="fa fa-remove"></i>
													</a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
							</div>
							<!--  \tableaux des enregistreurs associés -->
						</form:form>
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
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Annuler</button>
							<a id="js-confirm-button" class="btn btn-success">Confirmer</a>
						</div>
					</div>
				</div>
			</div>
			<!-- /Fenetre modale -->
		</div>


		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<jsp:include page="/template/footer.jsp" />

		<script type="text/javascript">

		function initTypeOuvrage() {
		$.getJSON({ // instead of writing the function to execute the request we use Select2's convenient helper
			url : $('.relayUrl').attr('href'),
			dataType : 'json',
			results : function(data, page) { // parse the results into the format expected by Select2.
				// since we are using custom formatting functions we do not need to alter remote JSON data

				console.log(data)
				return {
					results : data
				};
			}
		});}
		
		
			$(document).ready(function() {

				$("#typeOuvrage").select2({
					placeholder : "choisr un type",
					minimumInputLength : 1,
					initTypeOuvrage(),
				});

				// 								var $select = $('#typeOuvrage');

				// 								$
				// 										.getJSON(
				// 												$('.relayUrl').attr('href'),
				// 												null,
				// 												function(listTypeOuvrage) {
				// console.log(listTypeOuvrage)
				// 													var output = []
				// 													$
				// 															.each(
				// 																	listTypeOuvrage,
				// 																	function(
				// 																			index,
				// 																			typeOuvrage) {
				// 																		var tpl = '<option value="NONE"></option><option value="' + typeOuvrage.id + '">'
				// 																				+ typeOuvrage.description
				// 																				+ '</option>';
				// 																		output
				// 																				.push(tpl);
				// 																	});
				// 													$select.html(output
				// 															.join(''));
				// 													$("#typeOuvrage").select2({
				// 														tags : {id: listTypeOuvrage.id, text: listTypeOuvrage.decription }
				// 													})
				// 												});

				activateNappeOrSurface();
				activateOuvrageMaitre();

			});

			$('#confirmModal').modal();
			$('#confirmModal').on('show.bs.modal', function(e) {
				var url = $(e.relatedTarget).data('url');
				var $confirmButton = $('#js-confirm-button');
				$confirmButton.attr('href', url);
			});

			function activateNappeOrSurface() {

				// 2 = nappe souterraine
				if ($('#typeOuvrage :selected').val() == 2) {
					console.log('nappe  ' + $('#typeOuvrage').value)
					$('.nappesouterraine').attr('style', 'display: block;');
					$('.nappesouterraine').attr('disabled', false);
					$('.nappesouterraine').show();

					$('.eaudesurface').attr('style', 'display: none;');
					$('.eaudesurface').attr('disabled', true);
					$('.eaudesurface').hide();
				}
				// 1 = eau de surface
				if ($('#typeOuvrage :selected').val() == 1) {
					$('.nappesouterraine').attr('style', 'display: none;');
					$('.nappesouterraine').attr('disabled', true);
					$('.nappesouterraine').hide();

					$('.eaudesurface').attr('style', 'display: block;');
					$('.eaudesurface').attr('disabled', false);
					$('.eaudesurface').show();
				}

				console.log('nappe  ' + $('#typeOuvrage :selected').val())
			}

			function activateOuvrageMaitre() {
				if ($('#asservissement').is(':checked')) {
					$('#ouvrageMaitre').attr('style', 'display: block;');
					$('#ouvrageMaitre').attr('disabled', false);
					$('#ouvrageMaitre').show();
					$('#ouvragesCombo').chosen({
						allow_single_deselect : true
					});
				}
				if (!$('#asservissement').is(':checked')) {
					$('#ouvrageMaitre').attr('style', 'display: none;');
					$('#ouvrageMaitre').attr('disabled', true);
					$('#ouvrageMaitre').hide();
					$('#ouvragesCombo option:first').prop('selected', true);

					$('#ouvragesCombo').trigger("chosen:updated");
				}
				console.log($('#asservissement').is(':checked'));
			}
		</script>