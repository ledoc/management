<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuUtilisateurActive" />
	<jsp:param value="Solices - Liste Administrateur" name="titreOnglet" />
</jsp:include>

<!-- content wrapper -->
<div class="content-wrap bg-white shadow rounded">

	<!-- inner content wrapper -->
	<div class="wrapper">
		<section class="panel">
			<header class="panel-heading no-b">
				<h2 class="text-primary">Présentation Liste</h2>

				<p class="text-muted">La présentation liste permet d'afficher
					une grande quantité d'informations de façon lisible et structurée</p>

				<div class="pull-right mb15">
					<a href="<c:url  value="/administrateur/create" />"
						class="btn btn-outline btn-primary btn-m">Créer un
						administrateur</a>
				</div>
			</header>
			<div class="panel-body">

				<table class="table table-striped list no-m">
					<thead>
						<tr>
							<th>Identifiant</th>
							<th>Nom</th>
							<th>Prénom</th>
							<th>Téléphone Fixe</th>
							<th>Email</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${administrateurs}" var="administrateur">

							<c:url var="urlAdminstrateurDelete"
								value="/administrateur/delete/${administrateur.id}" />
							<c:url var="urlAdminstrateurUpdate"
								value="/administrateur/update/${administrateur.id}" />
							<tr>
								<td class="text-primary"><a
									href="${urlAdminstrateurUpdate}">${administrateur.identifiant}</a></td>
								<td><c:out value="${administrateur.nom}" /></td>
								<td><c:out value="${administrateur.prenom}" /></td>
								<td><c:out value="${administrateur.telFixe}" /></td>
								<td><c:out value="${administrateur.mail1}" /></td>
								<td><a  data-toggle="modal"
									data-target="#confirmModal"
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
					<a href="${urlAdminstrateurDelete}" class="btn btn-success">Confirmer</a>
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


