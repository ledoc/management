<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuSiteActive" />
</jsp:include>

<!-- content wrapper -->
<div class="content-wrap">

	<!-- inner content wrapper -->
	<div class="wrapper">
		<section class="panel"> <header class="panel-heading no-b">
		<h2 class="text-primary">Formulaire de création d'un Client</h2>
		<p class="text-muted">Le formulaire permet de créer un Client.</p>

		</header> <!--En mode utilisateur mettre les champs en readonly--> <c:url
			var="createSite" value="/site/create" /> <form:form
			method="POST" action="${createSite}" modelAttribute="site">

			<form:hidden path="id" />

			<div class="panel-body">
				<div class="col-md-4">

					<div class="form-group">
						<label for="nom">Nom</label>
						<form:input type="text" class="form-control" id="nom" path="nom"
							placeholder="" />
					</div>
					<div class="form-group">
						<label for="type">Type</label>
						<form:input type="text" class="form-control" id="type"
							path="type" placeholder="" />
					</div>
					<div class="form-group">
						<label for="departement">Département</label>
						<form:input type="text" class="form-control" id="departement"
							path="departement" placeholder="" />
					</div>
					<div class="form-group">
						<label for="coordonneesGeographique">Coordonnées géographiques</label>
						<form:input type="text" class="form-control" id="coordonneesGeographique"
							path="coordonneesGeographique" placeholder="" />
					</div>
					<div class="form-group">
						<label for="stationMeteo">Station météo</label>
						<form:input type="text" class="form-control" id="stationMeteo"
							path="stationMeteo" placeholder="" />
					</div>
					<div class="form-group">
						<label for="code">Code</label>
						<form:input type="text" class="form-control" id="code"
							path="code" placeholder="" />
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

