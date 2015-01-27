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
<div class="content-wrap">

	<!-- inner content wrapper -->
	<div class="wrapper">
		<section class="panel"> <header class="panel-heading no-b">
		<h2 class="text-primary">Formulaire de création d'un
			Administrateur</h2>
		<p class="text-muted">Le formulaire permet de créer un
			Administrateur.</p>

		</header> <!--En mode utilisateur mettre les champs en readonly--> <c:url
			var="createAdministrateur" value="/administrateur/create" /> <form:form
			method="POST" action="${createAdministrateur}"
			modelAttribute="administrateur">

			<form:hidden path="id" />

			<div class="panel-body">
				<div class="col-md-4">
					<div class="form-group">
						<label for="identifiant">Identifiant</label>
						<form:input type="text" class="form-control" id="identifiant"
							path="identifiant" placeholder="" />
					</div>
					<div class="form-group">
						<label for="nom">Nom</label>
						<form:input type="text" class="form-control" id="nom" path="nom"
							placeholder="" />
					</div>
					<div class="form-group">
						<label for="prenom">Prénom</label>
						<form:input type="text" class="form-control" id="prenom"
							path="prenom" placeholder="" />
					</div>
					<div class="form-group">
						<label for="poste">Mot de passe</label>
						<form:input type="text" class="form-control" id="motDePasse"
							path="motDePasse" placeholder="" />
					</div>
					<div class="form-group">
						<label for="mobile">Téléphone Portable</label>
						<form:input type="tel" class="form-control" id="telPortable"
							path="telPortable" placeholder="" />
					</div>
					<div class="form-group">
						<label for="phone">Téléphone Fixe</label>
						<form:input type="tel" class="form-control" id="telFixe"
							path="telFixe" placeholder="" />
					</div>
					<div class="form-group">
						<label for="email">Email 1</label>
						<form:input type="email" class="form-control" id="mail1"
							path="mail1" placeholder="" />
					</div>
					
					<div class="form-group">
						<label for="email">Email 2</label>
						<form:input type="email" class="form-control" id="mail1"
							path="mail2" placeholder="" />
					</div>
					

					<div class="pull-right">
						<a href="liste.html" class="btn btn-default btn-outline">Retour</a>
						<button type="submit" class="btn btn-outline btn-primary">Créer</button>
					</div>
				</div>
			</div>
		</form:form></section>
	</div>
	<!-- /inner content wrapper -->
</div>
<!-- /content wrapper -->
<jsp:include page="/template/footer.jsp" />

