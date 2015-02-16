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
	<jsp:param value="active" name="menuEtablissementActive" />
	<jsp:param value="Solices - Liste Etablissement" name="titreOnglet" />
</jsp:include>

<section class="layout">
	<!-- /sidebar -->
	<section class="main-content bg-white rounded shadow">
		<!-- start page content -->
		<!-- content wrapper -->
		<div class="content-wrap clearfix pt15">
			<div class="col-lg-12 col-md-12 col-xs-12">
				<section class="panel">
					<header class="panel-heading no-b">
						<h1 class="h3 text-primary mt0">Liste des établissements</h1>

						<div class="pull-right mb15">

							<sec:authorize ifAllGranted="ADMIN">
								<a href="<c:url  value="/etablissement/create" />"
									class="btn btn-outline btn-primary btn-m">Créer un
									établissement</a>
							</sec:authorize>
						</div>
					</header>
					<div class="panel-body">

						<c:if test="${empty etablissements }">

							<H2>Ooops !&nbsp;Liste vide.</H2>
						</c:if>
						<c:if test="${not empty etablissements }">

							<table class="table table-striped list no-m">
								<thead>
									<tr>
										<th>Code de l'établissement</th>
										<th>Nom</th>
										<th>Téléphone</th>
										<th>Email</th>
										<th>Site web</th>
										<sec:authorize ifAllGranted="ADMIN">
										<th class="nosort nosearch">Actions</th>
										</sec:authorize>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${etablissements}" var="etablissement">
										<c:url var="urlEtablissementDelete"
											value="/etablissement/delete/${etablissement.id}" />
										<c:url var="urlEtablissementUpdate"
											value="/etablissement/update/${etablissement.id}" />
										<tr>
											<td class="text-primary"><a
												href="${urlEtablissementUpdate}">${etablissement.codeEtablissement}</a></td>
											<td><c:out value="${etablissement.nom}" /></td>
											<td><c:out value="${etablissement.telephone}" /></td>
											<td><c:out value="${etablissement.mail}" /></td>
											<td class="text-primary"><a
												href="http://${etablissement.siteWeb}" target="_blank">${etablissement.siteWeb}</a></td>
											<sec:authorize ifAllGranted="ADMIN">
											<td><a data-url="${urlEtablissementDelete}"
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
			<!-- /inner content wrapper -->

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
							<p class="text-muted">ATTENTION : Les sites, ouvrages et
								enregistreurs associés seront supprimés</p>
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
		<!-- end page content -->
		<jsp:include page="/template/footer.jsp" />

		<script type="text/javascript">
			$('#confirmModal').modal();
			$('#confirmModal').on('show.bs.modal', function(e) {
				var url = $(e.relatedTarget).data('url');
				var $confirmButton = $('#js-confirm-button');
				$confirmButton.attr('href', url);
			});
		</script>