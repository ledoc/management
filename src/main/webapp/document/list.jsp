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
	<jsp:param value="Solices - Liste Document" name="titreOnglet" />
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
						<h1 class="h3 text-primary mt0">Liste des documents</h1>

						<div class="pull-right mb15">
							<sec:authorize ifAllGranted="ADMIN">
								<a href="<c:url  value="/document/assign" />"
									class="btn btn-outline btn-primary btn-m">Télécharger un
									document</a>
							</sec:authorize>
						</div>
					</header>
					<div class="panel-body">

						<c:if test="${empty documents }">

							<H2>Ooops !&nbsp;Liste vide.</H2>
						</c:if>
						<c:if test="${not empty documents }">

							<table class="table table-striped list no-m">
								<thead>
									<tr>
										<th>Nom</th>
										<th>Client</th>
										<th>Ouvrage</th>
										<th>Type</th>
										<th>Date</th>
										<th>taille</th>
										<sec:authorize ifAllGranted="ADMIN">
										<th class="nosort nosearch">Actions</th>
										</sec:authorize>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${documents}" var="document">
										<c:url var="urlDocumentDelete"
											value="/document/delete/${document.id}" />
										<c:url var="urlDocumentDownload"
											value="/document/download/file/${document.id}" />
										<tr>
											<td class="text-primary"><a
												href="${urlDocumentDownload}">${document.nom}</a></td>
											<td><c:out value="${document.client.identifiant}" /></td>
											<td><c:out value="${document.ouvrage.codeOuvrage}" /></td>
											<td><c:out value="${document.type}" /></td>
											<td><c:out value="${document.date}" /></td>
											<td><c:out value="${document.taille}" /></td>
											<sec:authorize ifAllGranted="ADMIN">
											<td><a data-url="${urlDocumentDelete}"
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

		<!-- script pour passer l'URL de suppression d'une entité à la modal -->
		<script type="text/javascript">
			$('#confirmModal').modal();
			$('#confirmModal').on('show.bs.modal', function(e) {
				var url = $(e.relatedTarget).data('url');
				var $confirmButton = $('#js-confirm-button');
				$confirmButton.attr('href', url);
			});
		</script>