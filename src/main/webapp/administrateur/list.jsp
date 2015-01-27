<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuUtilisateurActive" />
</jsp:include>

<!-- content wrapper -->
<div class="content-wrap bg-white shadow rounded">

	<!-- inner content wrapper -->
	<div class="wrapper">
		<section class="panel"> <header class="panel-heading no-b">
		<h2 class="text-primary">Présentation Liste</h2>

		<p class="text-muted">La présentation liste permet d'afficher une
			grande quantité d'informations de façon lisible et structurée</p>

		<div class="pull-right mb15">
			<a href="formulaire.html" class="btn btn-outline btn-primary btn-m">Créer
				un utilisateur</a>
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
							<td class="text-primary"><a href="${urlAdminstrateurUpdate}">${administrateur.identifiant}</a></td>
							<td><c:out value="${administrateur.nom}" /></td>
							<td><c:out value="${administrateur.prenom}" /></td>
							<td><c:out value="${administrateur.telFixe}" /></td>
							<td><c:out value="${administrateur.mail1}" /></td>
							<td><a href="${urlAdminstrateurDelete}" class="btn btn-outline btn-danger btn-xs">
									<i class="fa fa-remove"></i>
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
		</section>
	</div>
	<!-- /inner content wrapper -->

</div>
<!-- /content wrapper -->
<jsp:include page="/template/footer.jsp" />


