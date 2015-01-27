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
			Ouvrage</h2>
		<p class="text-muted">Le formulaire permet de créer un
			Ouvrage.</p>

		</header> <!--En mode utilisateur mettre les champs en readonly--> <c:url
			var="createOuvrage" value="/ouvrage/create" /> <form:form
			method="POST" action="${createOuvrage}"
			modelAttribute="ouvrage">

			<form:hidden path="id" />

			<div class="panel-body">
				<div class="col-md-4">
					<div class="form-group">
						<label for="nom">Nom</label>
						<form:input type="text" class="form-control" id="nom" path="nom"
							placeholder="" />
					</div>
					<div class="form-group">
						<label for="codeOuvrage">Code de l'établissement</label>
						<form:input type="text" class="form-control"
							id="codeOuvrage" path="codeOuvrage" placeholder="" />
					</div>
					<div class="form-group">
						<label for="coordonneesGeographique">Coordonnées
							géographiques</label>
						<form:input type="text" class="form-control"
							id="coordonneesGeographique" path="coordonneesGeographique"
							placeholder="" />
					</div>
					<div class="form-group">
						<label for="formeJuridique">Forme juridique</label>
						<form:input type="text" class="form-control" id="formeJuridique"
							path="formeJuridique" placeholder="" />
					</div>
					<div class="form-group">
						<label for="telephone">Téléphone</label>
						<form:input type="tel" class="form-control" id="telephone"
							path="telephone" placeholder="" />
					</div>
					<div class="form-group">
						<label for="siret">Siret</label>
						<form:input type="text" class="form-control" id="siret"
							path="siret" placeholder="" />
					</div>
					<div class="form-group">
						<label for="mail">Email</label>
						<form:input type="email" class="form-control" id="mail"
							path="mail" placeholder="" />
					</div>

					<div class="form-group">
						<label for="enregistreurWeb">Enregistreur web</label>
						<form:input type="text" class="form-control" id="enregistreurWeb"
							path="enregistreurWeb" placeholder="" />
					</div>

					<div class="form-group">
						<label for="enregistreurs">Attacher un ou des enregistreurs</label>
						<form:select multiple="true" id="enregistreursCombo" name="enregistreursCombo"
							path="enregistreurs" items="${enregistreursCombo}" itemValue="id" itemLabel="id"
							data-placeholder=" Sélectionnez
							un ou des enregistreurs"
							class="form-control chosen">
						</form:select>
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

