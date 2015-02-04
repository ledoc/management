<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuDocumentActive" />
	<jsp:param value="Solices - Gestion Document" name="titreOnglet" />
</jsp:include>

<c:url var="urlResources" value="/resources" />


<section class="layout">
	<!-- /sidebar -->
	<section class="main-content bg-white rounded shadow">
		<!-- content wrapper -->
		<div class="content-wrap clearfix pt15">
			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="panel">
					<header class="panel-heading no-b col-lg-offset-2">
						<h1 class="h3 text-primary mt0">Création d'un enregistreur</h1>
						<p class="text-muted">Permet de créer ou mettre à jour un
							enregistreur.</p>
					</header>
					<div class="panel-body">
						<c:url var="refreshClient" value="/document/refresh/client" />
						<c:url var="refreshOuvrages" value="/document/refresh/ouvrage" />
						<c:url var="documentUrl" value="/document" />
						<a class="relayUrl" href="${documentUrl}"></a>

						<form method="post" id="form" enctype="multipart/form-data">
							<div class="col-md-4 col-lg-4 col-md-4 col-xs-12 col-lg-offset-2">
								<div class="form-group">
									Fichier à Télécharger: <input type="file" name="file">
								</div>
								<div class="form-group">
									<label for="client">Choisir un client</label> <select
										id="client" name="client"
										data-placeholder=" Sélectionnez
							un client"
										class="form-control chosen">
										<c:forEach items="${clientsCombo}" var="client">
											<option value="${client.id}">${client.nom}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<label for="ouvrage">Choisir un ouvrage</label> <select
										id="ouvrage" name="ouvrage"
										data-placeholder=" Sélectionnez un
										ouvrage"
										class="form-control chosen">
										<c:forEach items="${ouvragesCombo}" var="ouvrage">
											<option value="${ouvrage.id}">${ouvrage.codeOuvrage}</option>
										</c:forEach>
									</select>
								</div>
								<div class="pull-right">
									<a href="<c:url  value="/document/list" />"
										class="btn btn-default btn-outline">Retour</a>
									<button type="submit"
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

			$("#client").change(
					function() {
						$.get($('.relayUrl').attr('href') + '/refresh/ouvrage/'
								+ $("#client").val(), // Le fichier cible côté serveur.
						null, function(responseData) {
							console.log('reponse refresh ouvrage '
									+ responseData);
							$('#ouvrage').attr('items', responseData)
						}, // Nous renseignons uniquement le nom de la fonction de retour.
						'json' // Format des données reçues.
						);
					})

			$("#ouvrage").change(
					function() {
						$.get($('.relayUrl').attr('href') + '/refresh/client/'
								+ $("#ouvrage").val(), // Le fichier cible côté serveur.
						null, function(responseData) {
							console.log('reponse refresh client '
									+ responseData);
							$('#client').attr('items', responseData)
						}, // Nous renseignons uniquement le nom de la fonction de retour.
						'json' // Format des données reçues.
						);
					})
		</script>