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
	<jsp:param value="Solices - Liste Enregistreur" name="titreOnglet" />
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
						<h1 class="h3 text-primary mt0">Liste des enregistreurs</h1>

						<div class="pull-right mb15">

							<sec:authorize ifAllGranted="ADMIN">
								<a href="<c:url  value="/enregistreur/create" />"
									class="btn btn-outline btn-primary btn-m">Créer un
									enregistreur</a>
							</sec:authorize>
						</div>
					</header>
					<div class="panel-body">
						<c:if test="${empty enregistreurs }">

							<H2>Ooops !&nbsp;Liste vide.</H2>
						</c:if>
						<c:if test="${not empty enregistreurs }">
							<table class="table table-striped list no-m">
								<thead>
									<tr>
										<th>MID</th>
										<th>Sonde</th>
										<th>Niveau batterie</th>
										<th>Dernière mesure</th>
										<th>Niveau manuel</th>
										<th>Maintenance</th>
<!-- 										<th>mesures</th> -->
<!-- 										<th>Croquis</th> -->
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
											<td><c:out value="${enregistreur.niveauBatterie}" /></td>
											<td><c:out
													value="${enregistreur.mesureEnregistreur.valeur}" /></td>
											<td><c:out value="${enregistreur.niveauManuel.valeur}" /></td>
											<c:set var="maintenance" value="${enregistreur.maintenance}" />
											<c:if test="${ maintenance == true }">
												<td><c:out value="oui" /></td>
											</c:if>
											<c:if test="${ maintenance == false }">
												<td><c:out value="non" /></td>
											</c:if>
<!-- 											<td class="text-primary"><a -->
<%-- 												href="${urlMesuresView}">voir</a></td> --%>
<%-- 											<td><c:out value="${enregistreur.croquis}" /></td> --%>
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
							<p class="text-muted">ATTENTION : Les mesures associées seront supprimés</p>
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