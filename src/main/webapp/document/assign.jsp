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
	<jsp:param value="active" name="menuDocumentActive" />
	<jsp:param value="Solices - Gestion Document" name="titreOnglet" />
</jsp:include>

<c:url var="urlResources" value="/resources" />
<!-- Seulement une visualisation pour les clients -->
<sec:authorize ifAllGranted="ADMIN">
	<c:set var="readOnlyValue" value="false"></c:set>
</sec:authorize>
<sec:authorize ifAllGranted="CLIENT">
	<c:set var="readOnlyValue" value="true"></c:set>
</sec:authorize>


<section class="layout">
	<!-- /sidebar -->
	<section class="main-content bg-white rounded shadow">
		<!-- content wrapper -->
		<div class="content-wrap clearfix pt15">
			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="panel">
					<header class="panel-heading no-b col-lg-offset-2">
						<h1 class="h3 text-primary mt0">Assigner un document</h1>
						<p class="text-muted">Permet d'uploader un document et
							l'assigner à un ouvrage</p>
					</header>
					<div class="panel-body">
						<c:url var="refreshClient" value="/document/refresh/client" />
						<c:url var="refreshOuvrages" value="/document/refresh/ouvrage" />
						<c:url var="documentUrl" value="/document" />
						<a class="relayUrl" href="${documentUrl}"></a>

						<form method="post" id="form" enctype="multipart/form-data">
							<div class="col-md-4 col-lg-4 col-md-4 col-xs-12 col-lg-offset-2">
								<div class="form-group">
									<label for="file">Choisir un fichier :</label> <input id="file"
										type="file" name="file" class="filestyle" data-input="false"
										data-buttonText="&nbspChoisir un fichier">
								</div>
								<div class="form-group">
									<label for="client">Choisir un client :</label>
									<!-- ATTENTION : Retrait de la classe chosn pour utiliser l'ajax -->
									<select id="client" name="client"
										data-placeholder=" Sélectionnez
							un client"
										class="form-control">
									</select>
								</div>
								<div class="form-group">
									<label for="ouvrage">Choisir un ouvrage :</label>
									<!-- ATTENTION : Retrait de la classe chosen pour utiliser l'ajax -->
									<select id="ouvrage" name="ouvrage"
										data-placeholder=" Sélectionnez un
										ouvrage"
										class="form-control">
									</select>
								</div>
								<div class="pull-right">
									<a href="<c:url  value="/document/list" />"
										class="btn btn-default btn-outline">Retour</a>
									<button id="submit" disabled="disabled" type="submit"
										class="btn btn-outline btn-primary submit">Créer</button>
								</div>
							</div>

						</form>
					</div>
				</div>
			</div>
		</div>

		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<jsp:include page="/template/footer.jsp" />

		<script type="text/javascript">
			function checkAllFieldsHaveValues() {

				var ouvrageVal = $('#ouvrage').val();
				var clientVal = $('#client').val();
				var fileVal = $('#file').val();
				console.log('ouvrageVal : ' + ouvrageVal);
				console.log('clientVal : ' + clientVal);
				console.log('fileVal : ' + fileVal);

				if (ouvrageVal && clientVal && fileVal) {

					$('#submit').attr('disabled', false);
				}
			}

			function initListClients() {

				var $select = $('#client');

				$
						.getJSON(
								$('.relayUrl').attr('href') + '/init/client', // Le fichier cible côté serveur.
								null,
								function(listClient) {

									var output = []
									$
											.each(
													listClient,
													function(index, client) {
														var tpl = '<option value="NONE"></option><option value="' + client.id + '">'
																+ client.nom
																+ '</option>';
														output.push(tpl);
													});
									$select.html(output.join(''));
									$select.chosen({
										allow_single_deselect : true
									}, {
										disable_search_threshold : 10
									});
								});
			}

			$(function() {
				initListClients();
			});

			$(".submit").click(
					function() {
						var urlBase = $('.relayUrl').attr('href');
						var clientId = $("#client").val();
						var ouvrageId = $("#ouvrage").val();
						$('.relayUrl').attr(
								'href',
								urlBase + '/upload/file' + '/' + clientId + '/'
										+ ouvrageId);
						$('#form').attr('action', $('.relayUrl').attr('href'));
					});

			$("#ouvrage").change(function() {
				checkAllFieldsHaveValues()
			});

			$("#file").change(function() {
				checkAllFieldsHaveValues()
			});

			$("#client")
					.change(

							function() {
								checkAllFieldsHaveValues();
								var $select = $('#ouvrage');
								$
										.get(
												$('.relayUrl').attr('href')
														+ '/refresh/ouvrage/'
														+ $(this).val(),
												null,
												function(listOuvrage) {
													var output = [];
													$select
															.attr(
																	'data-placeholder',
																	'Sélectionnez un ouvrage');
													$
															.each(
																	listOuvrage,
																	function(
																			index,
																			ouvrage) {
																		var tpl = '<option value="NONE"></option><option value="' + ouvrage.id + '">'
																				+ ouvrage.codeOuvrage
																				+ '</option>';
																		output
																				.push(tpl);
																	});
													$select.html(output
															.join(''));
													if (!listOuvrage.length) {
														$select
																.attr(
																		'data-placeholder',
																		'Aucun ouvrage pour ce client');

													}

													$select.selected = false;
													$select
															.chosen(
																	{
																		disable_search_threshold : 10
																	})
															.trigger(
																	"chosen:updated");
												});
							});
		</script>