<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuSiteActive" />
			<jsp:param value="Solices - Détail Site" name="titreOnglet" />
</jsp:include>

<!-- content wrapper -->
<div class="content-wrap">

	<!-- inner content wrapper -->
	<div class="wrapper">
		<section class="panel"> <header class="panel-heading no-b">
		<h2 class="text-primary">Création d'un Site</h2>
		<p class="text-muted">Le formulaire permet de créer un Site.</p>

		</header> <!--En mode utilisateur mettre les champs en readonly--> <c:url
			var="createSite" value="/site/create" /> <form:form id="form"
			method="POST" action="${createSite}" modelAttribute="site" role="form" class="parsley-form"
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
							data-parsley-mincheck-message="2 caractères minimum"  />
					</div>
					
					<div class="form-group">
						<label for="typesSiteCombo" >Choisir un type de site</label>
						<form:select 
							id="typesSiteCombo" name="typesSiteCombo"
							path="typeSite" items="${typesSiteCombo}"
							data-placeholder="Sélectionnez
							un type"
							class="form-control chosen" data-parsley-required="true">
						</form:select>
					</div>
					<div class="form-group">
						<label for="departement">Département</label>
						<form:input class="form-control" id="departement"
							path="departement" placeholder=""  data-parsley-required="true"
							data-parsley-type="digits"
							data-parsley-type-message="numéro du département"
							data-parsley-length="[2, 3]"
							data-parsley-trigger="change"
							data-parsley-required-message="Champ requis"
							data-parsley-length-message="2 ou 3 chiffres" />
					</div>
					<div class="form-group">
						<label for="coordonneesGeographique">Coordonnées géographiques</label>
						<form:input type="text" class="form-control" id="coordonneesGeographique"
							path="coordonneesGeographique" placeholder=""  data-parsley-required="true"
							data-parsley-trigger="change"
							data-parsley-required-message="Champ requis"
							data-parsley-mincheck="2"
							data-parsley-mincheck-message="2 caractères minimum"  />
					</div>
					<div class="form-group">
						<label for="stationMeteo">Station météo</label>
						<form:input type="text" class="form-control" id="stationMeteo"
							path="stationMeteo" placeholder=""  data-parsley-required="true"
							data-parsley-trigger="change"
							data-parsley-required-message="Champ requis"
							data-parsley-mincheck="2"
							data-parsley-mincheck-message="2 caractères minimum" />
					</div>
					<div class="form-group">
						<label for="code">Code</label>
						<form:input type="text" class="form-control" id="code"
							path="code" placeholder=""  data-parsley-required="true"
							data-parsley-trigger="change"
							data-parsley-required-message="Champ requis"
							data-parsley-mincheck="2"
							data-parsley-mincheck-message="2 caractères minimum" />
					</div>

	

					<div class="form-group">
						<label for="ouvrages">Rattacher une entité (liste
							déroulante avec recherche)</label>
						<form:select autocomplete="true" multiple="true"
							id="ouvragesCombo" name="ouvragesCombo"
							path="ouvrages" items="${ouvragesCombo}"
							itemValue="id" itemLabel="nom"
							data-placeholder=" Sélectionnez
							une entité"
							class="form-control chosen">
						</form:select>
					</div>

					<div class="pull-right">
						<a href="<c:url  value="/site/list" />" class="btn btn-default btn-outline">Retour</a>
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

