<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url var="urlBase" value="/" />

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuOuvrageActive" />
	<jsp:param value="Solices - Liste Ouvrage" name="titreOnglet" />
</jsp:include>

<!-- content wrapper -->
<div class="content-wrap bg-white shadow rounded">

	<!-- inner content wrapper -->
	<div class="wrapper">
		<section class="panel"> <header class="panel-heading no-b">
		<h2 class="text-primary">Liste d'ouvrage</h2>

		<p class="text-muted">La présentation liste permet d'afficher une
			grande quantité d'informations de façon lisible et structurée</p>

		<div class="pull-right mb15">
			<a href="<c:url  value="/ouvrage/create" />"
				class="btn btn-outline btn-primary btn-m">Créer un ouvrage</a>
		</div>
		</header>
		<div class="panel-body">

			<table class="table table-striped list no-m">
				<thead>
					<tr>
						<th>Code de l'etablissement</th>
						<th>Nom</th>
						<th>Coordonnées géographique</th>
						<th>Forme juridique</th>
						<th>siret</th>
						<th>Téléphone</th>
						<th>Email</th>
						<th>Site web</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${etablissements}" var="etablissement">
						<c:url var="urlEtablissementDelete"
							value="/etablissement/delete/${etablissement.id}" />
						<c:url var="urlEtablissementUpdate"
							value="/etablissement/update/${etablissement.id}" />
						<tr>
							<td class="text-primary"><a href="${urlEtablissementUpdate}">${etablissement.codeEtablissement}</a></td>
							<td><c:out value="${etablissement.nom}" /></td>
							<td><c:out value="${etablissement.coordonneesGeographique}" /></td>
							<td><c:out value="${etablissement.formeJuridique}" /></td>
							<td><c:out value="${etablissement.siret}" /></td>
							<td><c:out value="${etablissement.telephone}" /></td>
							<td><c:out value="${etablissement.mail}" /></td>
							<td><c:out value="${etablissement.siteWeb}" /></td>
							<td><a data-toggle="modal" data-target="#confirmModal"
								class="btn btn-outline btn-danger btn-xs js-confirm-btn"> <i
									class="fa fa-remove"></i>
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
		</section>
	</div>
	<!-- /inner content wrapper -->

	<!-- Fenetre modale -->
	<div id="confirmModal" class="modal fade bs-modal-sm" tabindex="-1"
		role="dialog" aria-hidden="true" data-backdrop="false"
		data-show="false">
		<div class="modal-dialog">
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
					<a href="${urlOuvrageDelete}" class="btn btn-success">Confirmer</a>
				</div>
			</div>
		</div>
	</div>

</div>
<!-- /content wrapper -->
<jsp:include page="/template/footer.jsp" />

<script type="text/javascript">
	$('#confirmModal').modal();
</script>


