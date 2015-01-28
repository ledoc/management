<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuUtilisateurActive" />
		<jsp:param value="Solices - Détails Administrateur" name="titreOnglet" />
</jsp:include>

<!-- content wrapper -->
<div class="content-wrap">

	<!-- inner content wrapper -->
	<div class="wrapper">
		<section class="panel">
			<header class="panel-heading no-b">
				<h2 class="text-primary">Création d'un
					Administrateur</h2>
				<p class="text-muted">Le formulaire permet de créer un
					Administrateur.</p>

			</header>
			<!--En mode utilisateur mettre les champs en readonly-->
			<c:url var="createAdministrateur" value="/administrateur/create" />
			<form:form id="form" method="POST" action="${createAdministrateur}"
				modelAttribute="administrateur" role="form" class="parsley-form"
				data-validate="parsley" data-show-errors="true">

				<form:hidden path="id" />

				<div class="panel-body">
					<div class="col-md-4">
						<div class="form-group">
							<label for="identifiant">Identifiant</label>
							<form:input type="text" class="form-control" id="identifiant"
								path="identifiant" placeholder="" data-parsley-required="true"
								data-parsley-trigger="change"
								data-parsley-required-message="Champ requis"
								data-parsley-mincheck="2"
								data-parsley-mincheck-message="2 caractères minimum" />
						</div>
						<div class="form-group">
							<label for="nom">Nom</label>
							<form:input type="text" class="form-control" id="nom" path="nom"
								placeholder="" data-parsley-required="true"
								data-parsley-trigger="change"
								data-parsley-required-message="Champ requis"
								data-parsley-mincheck="2"
								data-parsley-mincheck-message="2 caractères minimum" />
						</div>
						<div class="form-group">
							<label for="prenom">Prénom</label>
							<form:input type="text" class="form-control" id="prenom"
								path="prenom" placeholder="" data-parsley-required="true"
								data-parsley-trigger="change"
								data-parsley-required-message="Champ requis"
								data-parsley-mincheck="2"
								data-parsley-mincheck-message="2 caractères minimum" />
						</div>
						<div class="form-group">
							<label for="motDePasse">Mot de passe</label>
							<form:input type="password" class="form-control" id="motDePasse"
								path="motDePasse" placeholder="" data-parsley-required="true"
								data-parsley-trigger="change"
								data-parsley-required-message="Champ requis"
								data-parsley-mincheck="3"
								data-parsley-mincheck-message="3 caractères minimum" />
						</div>

						<div class="form-group">
							<label for="confirmation">Confirmation du mot de passe</label> <input
								type="password" class="form-control" id="confirmation"
								placeholder="" data-parsley-required="true"
								data-parsley-trigger="change"
								data-parsley-required-message="Champ requis"
								data-parsley-equalto="#motDePasse"
								data-parsley-equalto-message="Les valeurs sont différentes" />
						</div>


					<div class="form-group">
						<label for="telFixe">Téléphone Fixe</label>
						<form:input class="form-control" id="telFixe"
							path="telFixe" placeholder="" data-parsley-required="true"
							data-parsley-trigger="change" data-parsley-type="digits"
							data-parsley-length="[10,11]"
							data-parsley-required-message="Champ requis"
							data-parsley-type-message="Ne doit comporter que des chiffres"
							data-parsley-range-message="comporte 10 chiffres minimum" />
					</div>

					<div class="form-group">
						<label for="telPortable">Téléphone Portable</label>
						<form:input class="form-control" id="telPortable"
							path="telPortable" placeholder="" 
							data-parsley-trigger="change" data-parsley-type="digits"
							data-parsley-length="[10,11]"
							data-parsley-required-message="Champ requis"
							data-parsley-type-message="Ne doit comporter que des chiffres"
							data-parsley-range-message="comporte 10 chiffres minimum" />
					</div>

						<div class="form-group">
							<label for="mail1">Email 1</label>
							<form:input class="form-control" id="mail1" path="mail1"
								data-parsley-type="email" data-parsley-required="true"
								data-parsley-trigger="change" placeholder="my@email.fr"
								data-parsley-required-message="Champ requis"
								data-parsley-type-message="Entrer une adresse email valide" />
						</div>

						<div class="form-group">
							<label for="mail2">Email 2</label>
							<form:input class="form-control" id="mail2" path="mail2"
								data-parsley-type="email" data-parsley-required="true"
								data-parsley-trigger="change" placeholder="my@email.fr"
								data-parsley-type-message="Entrer une adresse email valide" />
						</div>

						<div class="pull-right">
							<a href="<c:url  value="/administrateur/list" />"
								class="btn btn-default btn-outline">Retour</a>
							<button type="submit" class="btn btn-outline btn-primary">Créer</button>
						</div>
					</div>
				</div>
			</form:form>
		</section>
	</div>
	<!-- /inner content wrapper -->
</div>
<!-- /content wrapper -->
<jsp:include page="/template/footer.jsp" />

