<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuClientActive" />
	<jsp:param value="Solices - Détails Client" name="titreOnglet" />
</jsp:include>

<section class="layout">
	<!-- /sidebar -->
	<section class="main-content bg-white rounded shadow">
		<!-- content wrapper -->
		<div class="content-wrap clearfix pt15">
			<div class="col-lg-12 col-md-12 col-xs-12 rounded">
				<div class="panel">
					<header class="panel-heading no-b col-lg-offset-2">
						<h1 class="h3 text-primary mt0">Création d'un Client</h1>
						<p class="text-muted">Permet de créer ou mettre à jour un client.</p>
					</header>
					<div class="panel-body">
						<c:url var="createClient" value="/client/create" />
						<form:form id="form" method="POST" action="${createClient}"
							modelAttribute="client" role="form" class="parsley-form"
							data-validate="parsley" data-show-errors="true">

							<form:hidden path="id" />

							<div class="col-md-4 col-lg-4 col-md-4 col-xs-12 col-lg-offset-2">
								<div class="form-group">
									<label for="nom">Nom</label>
									<form:input type="text" class="form-control" id="nom"
										path="nom" placeholder="" data-parsley-required="true"
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
									<label for="login">Login</label>
									<form:input type="text" class="form-control" id="login"
										path="login" placeholder="" data-parsley-trigger="change"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum" />
								</div>
								<div class="form-group">
									<label for="posteOccupe">Poste occupé</label>
									<form:input type="text" class="form-control" id="posteOccupe"
										path="posteOccupe" placeholder=""
										data-parsley-trigger="change" data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum" />
								</div>
								<div class="form-group">
									<label for="motDePasse">Mot de passe</label>
									<form:input type="text" class="form-control" id="motDePasse"
										path="motDePasse" placeholder="" data-parsley-required="true"
										data-parsley-trigger="change"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="3"
										data-parsley-mincheck-message="3 caractères minimum" />
								</div>
								<div class="form-group">
									<label for="telFixe">Téléphone Fixe</label>
									<form:input class="form-control" id="telFixe" path="telFixe"
										placeholder="" data-parsley-trigger="change" />
								</div>
								<div class="form-group">
									<label for="telPortable">Téléphone Portable</label>
									<form:input class="form-control" id="telPortable"
										path="telPortable" placeholder=""
										data-parsley-trigger="change" data-parsley-required="true"
										data-parsley-required-message="Champ requis" />
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
										data-parsley-type="email" data-parsley-trigger="change"
										placeholder="my@email.fr"
										data-parsley-type-message="Entrer une adresse email valide" />
								</div>
								<div class="form-group">
									<label for="etablissements">Rattacher un ou des
										établissement</label>
									<form:select autocomplete="true" multiple="true"
										id="etablissementsCombo" name="etablissementsCombo"
										path="etablissements" items="${etablissementsCombo}"
										itemValue="id" itemLabel="nom"
										data-placeholder=" Sélectionnez
							une entité"
										class="form-control chosen">
									</form:select>
								</div>
								<div class="pull-right">
									<a href="<c:url  value="/client/list" />"
										class="btn btn-default btn-outline">Retour</a>
									<button type="submit" class="btn btn-outline btn-primary">Créer</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<jsp:include page="/template/footer.jsp" />