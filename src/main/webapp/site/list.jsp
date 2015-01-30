<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuSiteActive" />
	<jsp:param value="Solices - Liste Site" name="titreOnglet" />
</jsp:include>

<!-- start page content -->
<!-- content wrapper -->
<div class="content-wrap clearfix pt15">
	<div class="col-lg-12 col-md-12 col-xs-12">
		<section class="panel">
			<header class="panel-heading no-b">
				<h1 class="h3 text-primary mt0">Liste des sites</h1>

				<p class="text-muted">La présentation liste permet d'afficher
					une grande quantité d'informations de façon lisible et structurée</p>

				<div class="pull-right mb15">
				<sec:authorize ifAllGranted="ADMIN">	
					<a href="<c:url  value="/site/create" />"
						class="btn btn-outline btn-primary btn-m">Créer un site</a>
				</sec:authorize>
				</div>
			</header>
			<div class="panel-body">

				<c:if test="${empty sites }">
					 <H2>Ooops !&nbsp;Liste vide.</H2>
				</c:if>
				<c:if test="${not empty sites }">
					
					<table class="table table-striped list no-m">
						<thead>
							<tr>
								<th>Code</th>
								<th>Type</th>
								<th>Nom</th>
								<th>Département</th>
								<th>Station météo</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${sites}" var="site">
								<c:url var="urlSiteDelete" value="/site/delete/${site.id}" />
								<c:url var="urlSiteUpdate" value="/site/update/${site.id}" />
								<tr>
									<td class="text-primary"><a href="${urlSiteUpdate}">${site.code}</a></td>
									<td><c:out value="${site.typeSite}" /></td>
									<td><c:out value="${site.nom}" /></td>
									<td><c:out value="${site.departement}" /></td>
									<td><c:out value="${site.stationMeteo}" /></td>
									<td><a data-url="${urlSiteDelete}" data-toggle="modal" data-target="#confirmModal"
										class="btn btn-outline btn-danger btn-xs js-confirm-btn"> <i
											class="fa fa-remove"></i>
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
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
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
	$('#confirmModal').on('show.bs.modal', function (e) {
		  var url = $(e.relatedTarget).data('url');
		  var $confirmButton = $('#js-confirm-button');
		  $confirmButton.attr('href', url);
		});
</script>