<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuEtablissementActive" />
	<jsp:param value="Solices - Détails Etablissement" name="titreOnglet" />
</jsp:include>

<c:url var="urlResources" value="/resources" />

<!-- Seulement une visualisation pour les clients -->
<sec:authorize ifAllGranted="ADMIN">
	<c:set var="readOnlyValue" value="false"></c:set>
	<c:if test="${empty etablissement.id}">
		<c:set var="sentenceCreateUpdate" value="créer" />
		<c:set var="labelCreateUpdate" value="Créer" />
		<c:set var="textCreateUpdate" value="Création" />
	</c:if>
	<c:if test="${not empty etablissement.id}">
		<c:set var="sentenceCreateUpdate" value="mettre à jour" />
		<c:set var="labelCreateUpdate" value="Mettre à jour" />
		<c:set var="textCreateUpdate" value="Mise à jour" />
	</c:if>
</sec:authorize>
<sec:authorize ifAllGranted="CLIENT">
	<c:set var="readOnlyValue" value="true"></c:set>
	<c:set var="sentenceCreateUpdate" value="visualiser" />
	<c:set var="labelCreateUpdate" value="OK" />
	<c:set var="textCreateUpdate" value="Détails" />
</sec:authorize>



<section class="layout">
	<!-- /sidebar -->
	<section class="main-content bg-white rounded shadow">
		<!-- content wrapper -->


		<div class="content-wrap clearfix pt15">
			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="panel">
					<header class="panel-heading no-b col-lg-offset-2">
						<h1 class="h3 text-primary mt0">${textCreateUpdate}d'un
							Etablissement</h1>
						<p class="text-muted">Permet de ${sentenceCreateUpdate} un
							établissement.</p>
					</header>
					<div class="panel-body">
						<c:url var="createEtablissement" value="/etablissement/create" />
						<form:form id="form" method="POST" action="${createEtablissement}"
							modelAttribute="etablissement" role="form" class="parsley-form"
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
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="codeEtablissement">Code de l'établissement</label>
									<form:input type="text" class="form-control"
										id="codeEtablissement" path="codeEtablissement" placeholder=""
										data-parsley-required="true" data-parsley-trigger="change"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="coordonneesGeographique">Coordonnées
										géographiques (format Google map)</label>
									<form:input type="text" class="form-control"
										id="coordonneesGeographique" path="coordonneesGeographique"
										placeholder="" data-parsley-required="true"
										data-parsley-trigger="change"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="coordonneesLambert">Coordonnées
										géographiques (format Lambert93)</label>
									<form:input type="text" class="form-control"
										id="coordonneesLambert" path="coordonneesLambert"
										placeholder="" data-parsley-trigger="change"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue}" />
								</div>
								<div class="form-group">
									<label for="formeJuridique">Forme juridique</label>
									<form:input type="text" class="form-control"
										id="formeJuridique" path="formeJuridique" placeholder=""
										data-parsley-trigger="change" data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue}" />
								</div>
								<div class="form-group">
									<label for="telephone">Téléphone</label>
									<form:input type="tel" class="form-control" id="telephone"
										path="telephone" placeholder="" data-parsley-required="true"
										data-parsley-required-message="Champ requis"
										data-parsley-trigger="change" readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="siret">Siret</label>
									<form:input type="text" class="form-control" id="siret"
										path="siret" placeholder="" data-parsley-trigger="change"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="mail">Email</label>
									<form:input class="form-control" id="mail" path="mail"
										data-parsley-type="email" data-parsley-required="true"
										data-parsley-trigger="change" placeholder="my@email.fr"
										data-parsley-required-message="Champ requis"
										data-parsley-type-message="Entrer une adresse email valide"
										readonly="${readOnlyValue }" />
								</div>

								<div class="form-group">
									<label for="siteWeb">Site web</label>
									<form:input class="form-control" id="siteWeb" path="siteWeb"
										data-parsley-type="url"
										data-parsley-type-message="Entrer une adresse web valide (voir l'exemple)"
										data-parsley-trigger="change" placeholder="http://website.fr"
										readonly="${readOnlyValue }" />
								</div>
								<div class="pull-right">
									<a href="<c:url  value="/etablissement/list" />"
										class="btn btn-default btn-outline">Retour</a>
									<button type="submit" class="btn btn-outline btn-primary">${labelCreateUpdate}</button>
								</div>
							</div>

							<!-- deuxième colonne -->
							<c:if test="${not empty etablissement.sites }">
								<div class="col-md-4">
									<div class="panel panel-default no-b">
										<header class="panel-heading clearfix brtl brtr no-b">
											<div class="overflow-hidden">
												<span class="h4 show no-m pt10">Sites rattachés</span> <small
													class="text-muted">Cliquer pour accéder à la fiche
													du site. </small>
											</div>
										</header>
										<div class="list-group ">
											<table class="no-b">
												<thead>
													<tr>
														<th>Code</th>
														<th>Nom</th>
													</tr>
												</thead>
												<c:forEach items="${etablissement.sites}" var="site">
													<tbody>
														<tr>
															<td class="text-primary"><c:url var="urlSiteUpdate"
																	value="/site/update/${site.id}" /> <a
																href="${urlSiteUpdate}"
																class="list-group-item text-uppercase"><c:out
																		value="${site.codeSite}" /> </a></td>
															<td><div class="list-group-item">
																	<c:out value="${site.nom}" />
																</div></td>
														</tr>
													</tbody>
												</c:forEach>
											</table>
										</div>
									</div>
								</div>
							</c:if>
							<!-- /deuxième colonne -->

						</form:form>
					</div>
				</div>
			</div>
		</div>
		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<jsp:include page="/template/footer.jsp" />