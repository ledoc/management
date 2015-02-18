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
	<jsp:param value="Solices - Liste Alerte" name="titreOnglet" />
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
		<!-- start page content -->
		<!-- content wrapper -->
		<div class="content-wrap clearfix pt15">
			<div class="col-lg-12 col-md-12 col-xs-12">
				<section class="panel">
					<header class="panel-heading no-b">
						<h1 class="h3 text-primary mt0">Liste des alertes</h1>
						<p class="text-muted">Alertes actives : ${alertesActives} /
							${alertesTotales}</p>
						<div class="pull-right mb15">
							<sec:authorize ifAllGranted="ADMIN">
								<a data-url="<c:url  value="/alerte/create" />"
									data-toggle="modal" data-target="#creationModal"
									class="btn btn-outline btn-primary btn-m">Créer un alerte</a>
							</sec:authorize>
						</div>
					</header>
					<div class="panel-body">
						<c:if test="${empty alertes }">

							<H2>Ooops !&nbsp;Liste vide.</H2>
						</c:if>
						<c:if test="${not empty alertes }">
							<table class="table table-striped list no-m">
								<thead>
									<tr>
										<th>Code</th>
										<th>Enregistreur</th>
										<th>Activation</th>
										<th>Type</th>
										<th>Tendance</th>
										<th>Seuil pré-alerte</th>
										<th>Seuil alerte</th>
										<th>Email d'envoi</th>
										<sec:authorize ifAllGranted="ADMIN">
											<th class="nosort nosearch">Actions</th>
										</sec:authorize>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${alertes}" var="alerte">
										<c:url var="urlAlerteDelete"
											value="/alerte/delete/${alerte.id}" />
										<c:url var="urlAlerteUpdate"
											value="/alerte/update/${alerte.id}" />
										<tr>
											<td class="text-primary"><a href="${urlAlerteUpdate}">${alerte.codeAlerte}</a></td>
											<td><c:out value="${alerte.enregistreur.mid}" /></td>
											<c:set var="activation" value="${alerte.activation}" />
											<c:if test="${ activation == false }">
												<td><c:out value="non" /></td>
											</c:if>
											<c:if test="${ activation == true }">
												<td><c:out value="oui" /></td>
											</c:if>
											<td><c:out value="${alerte.typeAlerte.description}" /></td>
											<td><c:out value="${alerte.tendance.description}" /></td>
											<td><c:out value="${alerte.seuilPreAlerte}" /></td>
											<td><c:out value="${alerte.seuilAlerte}" /></td>
											<td><c:out value="${alerte.emailDEnvoi}" /></td>
											<sec:authorize ifAllGranted="ADMIN">
												<td><a data-url="${urlAlerteDelete}"
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
				</section>
			</div>
			<c:if test="${empty historiqueAlertes }">

				<H2>&nbspPas d'historique</H2>
			</c:if>
			<c:if test="${not empty historiqueAlertes }">
				<div class="col-lg-12 col-md-12 col-xs-12">
					<section class="panel">
						<header class="panel-heading no-b">
							<h1 class="h3 text-primary mt0">Historique des alertes</h1>
						</header>
						<div class="panel-body">

							<table class="table table-striped list no-m">
								<thead>
									<tr>
										<th>Date</th>
										<th>Code</th>
										<th>Enregistreur</th>
										<th>Type</th>
										<th>Seuil pré-alerte</th>
										<th>Seuil alerte</th>
										<th>Mesure relevée</th>
										<th>Etat</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${historiqueAlertes}" var="alerte">
										<tr>
											<td><fmt:formatDate value="${alerte.date}"
													pattern="dd-MM-yyyy hh:mm:ss" /></td>
											<td><c:out value="${alerte.enregistreur.mid}" /></td>
											<td><c:out value="${alerte.typeAlerte.description}" /></td>
											<td><c:out value="${alerte.seuilPreAlerte}" /></td>
											<td><c:out value="${alerte.seuilAlerte}" /></td>
											<td><c:out value="${alerte.mesureLevantAlerte}" /></td>
											<c:if test="${ acquittement == false }">
												<td><output style="color: green">ALERTE </output></td>
											</c:if>
											<c:if test="${ acquittement == true }">
												<td><output style="color: red">acquittée</output></td>
											</c:if>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</section>
				</div>
			</c:if>

			<!-- /inner content wrapper -->

			<!-- Fenetre modale creation nouvelle alerte -->
			<div id="creationModal" class="modal fade bs-modal-sm" tabindex="-1"
				role="dialog" aria-hidden="true" data-backdrop="false"
				data-show="false">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">×</button>
							<h4 class="text-primary modal-title">Créer nouvelle alerte</h4>
						</div>
						<div class="modal-body">
							<div class="panel-body">
								<c:url var="createAlerte" value="/alerte/create" />
								<form:form id="form" method="POST" action="${createAlerte}"
									modelAttribute="alerte" role="form" class="parsley-form"
									data-validate="parsley" data-show-errors="true">

									<form:hidden path="id" />

									<div class="col-md-6 col-lg-6 col-md-6 col-xs-12 ">

										<div class="form-group">
											<label for="nom">Code</label>
											<form:input type="text" class="form-control" id="codeAlerte"
												path="codeAlerte" placeholder=""
												data-parsley-trigger="change" data-parsley-required="true"
												data-parsley-required-message="Champ requis"
												data-parsley-mincheck="2"
												data-parsley-mincheck-message="2 caractères minimum"
												readonly="${readOnlyValue }" />
										</div>
										<div class="form-group">
											<label for="site">Rattacher à un Enregistreur</label>
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
												class="form-control chosen-select"
												disabled="${readOnlyValue }">
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
												class="form-control chosen-select"
												disabled="${readOnlyValue }">
												<form:option value="" label="--- Choisir une tendance ---" />
												<form:options items="${tendancesAlerteCombo}"
													itemLabel="description" />
											</form:select>
										</div>
										<div class="checkbox checkbox-inline">
											<form:checkbox id="activation" path="activation"
												label="Activation" disabled="${readOnlyValue }" />
										</div>
										<div class="pull-right">
											<a href="<c:url  value="/alerte/list" />"
												class="btn btn-default btn-outline">Retour</a>
											<button type="submit" class="btn btn-outline btn-primary">${labelCreateUpdate}</button>
										</div>
									</div>
									<div class="col-md-6">
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
											<label for="emailDEnvoi">Mail d'envoi</label>
											<form:input class="form-control" id="emailDEnvoi"
												path="emailDEnvoi" data-parsley-type="email"
												data-parsley-required="true" data-parsley-trigger="change"
												placeholder="my@email.fr"
												data-parsley-required-message="Champ requis"
												data-parsley-type-message="Entrer une adresse email valide" />
										</div>

										<div class="form-group">
											<label for="intitule">Intitulé</label>
											<form:input type="text" class="form-control" id="intitule"
												path="intitule" placeholder="" data-parsley-trigger="change"
												data-parsley-mincheck="2"
												data-parsley-mincheck-message="2 caractères minimum"
												readonly="${readOnlyValue }" />
										</div>
									</div>
								</form:form>
							</div>
						</div>

					</div>
				</div>
			</div>
			<!-- /Fenetre modale creation nouvelle alerte -->


			<!-- Fenetre modale Confirmation suppression-->
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
			<!-- /Fenetre modale Confirmation suppression-->
		</div>
		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<!-- end page content -->
		<jsp:include page="/template/footer.jsp" />

		<!-- script pour passer l'URL de suppression d'une entité à la modal -->
		<script type="text/javascript">
			$('#confirmModal').modal();
			$('#confirmModal').on('show.bs.modal', function(e) {
				var url = $(e.relatedTarget).data('url');
				var $confirmButton = $('#js-confirm-button');
				$confirmButton.attr('href', url);
			});

			$('#creationModal').modal();
			$('#creationModal').on('show.bs.modal', function(e) {
				var url = $(e.relatedTarget).data('url');
				var $confirmButton = $('#js-confirm-button');
				$confirmButton.attr('href', url);
			});
		</script>