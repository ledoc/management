<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuUtilisateurActive" />
			<jsp:param value="Solices - Détails Etablissement" name="titreOnglet" />
</jsp:include>

<!-- content wrapper -->
<div class="content-wrap">

	<!-- inner content wrapper -->
	<div class="wrapper">
		<section class="panel"> <header class="panel-heading no-b">
		<h2 class="text-primary">Création d'un
			Etablissement</h2>
		<p class="text-muted">Le formulaire permet de créer un
			Etablissement.</p>

		</header> <!--En mode utilisateur mettre les champs en readonly--> <c:url
			var="createEtablissement" value="/etablissement/create" /> <form:form id="form"
			method="POST" action="${createEtablissement}"
			modelAttribute="etablissement" role="form" class="parsley-form"
			data-validate="parsley" data-show-errors="true">

			<form:hidden path="id" />

			<div class="panel-body">
				<div class="col-md-4">
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
						<label for="codeEtablissement">Code de l'établissement</label>
						<form:input type="text" class="form-control"
							id="codeEtablissement" path="codeEtablissement" placeholder=""
							data-parsley-required="true" data-parsley-trigger="change"
							data-parsley-required-message="Champ requis"
							data-parsley-mincheck="2"
							data-parsley-mincheck-message="2 caractères minimum" />
					</div>
					<div class="form-group">
						<label for="coordonneesGeographique">Coordonnées
							géographiques</label>
						<form:input type="text" class="form-control"
							id="coordonneesGeographique" path="coordonneesGeographique"
							placeholder="" data-parsley-required="true"
							data-parsley-trigger="change"
							data-parsley-required-message="Champ requis"
							data-parsley-mincheck="2"
							data-parsley-mincheck-message="2 caractères minimum" />
					</div>
					<div class="form-group">
						<label for="formeJuridique">Forme juridique</label>
						<form:input type="text" class="form-control" id="formeJuridique"
							path="formeJuridique" placeholder=""  
								data-parsley-trigger="change"
								data-parsley-mincheck="2"
								data-parsley-mincheck-message="2 caractères minimum" />
					</div>
					<div class="form-group">
						<label for="telephone">Téléphone</label>
						<form:input type="tel" class="form-control" id="telephone"
							path="telephone" placeholder="" data-parsley-required="true"
							data-parsley-trigger="change" data-parsley-type="digits"
							data-parsley-range="[10,11]"
							data-parsley-required-message="Champ requis"
							data-parsley-type-message="Ne doit comporter que des chiffres"
							data-parsley-range-message="comporte 10 chiffres minimum" />
					</div>
					<div class="form-group">
						<label for="siret">Siret</label>
						<form:input type="text" class="form-control" id="siret"
							path="siret" placeholder=""  data-parsley-required="true"
								data-parsley-trigger="change"
								data-parsley-required-message="Champ requis"
								data-parsley-mincheck="2"
								data-parsley-mincheck-message="2 caractères minimum" />
					</div>
					<div class="form-group">
						<label for="mail">Email</label>
						<form:input class="form-control" id="mail"
							path="mail" placeholder=""
								data-parsley-type="email" data-parsley-required="true"
								data-parsley-trigger="change" placeholder="my@email.fr"
								data-parsley-required-message="Champ requis"
								data-parsley-type-message="Entrer une adresse email valide"  />
					</div>

					<div class="form-group">
						<label for="siteWeb">Site web</label>
						<form:input class="form-control" id="siteWeb"
							path="siteWeb" data-parsley-type="url"
							data-parsley-type-message="Entrer une adresse web valide (voir l'exemple)"
							data-parsley-required="true" data-parsley-trigger="change"
							placeholder="http://website.fr" />
					</div>

					<div class="form-group">
						<label for="sites">Attacher un ou des sites</label>
						<form:select multiple="true" id="sitesCombo" name="sitesCombo"
							path="sites" items="${sitesCombo}" itemValue="id" itemLabel="id"
							data-placeholder=" Sélectionnez
							un ou des sites"
							class="form-control chosen">
						</form:select>
					</div>

					<div class="pull-right">
						<a href="<c:url  value="/etablissement/list" />"
							class="btn btn-default btn-outline">Retour</a>
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

