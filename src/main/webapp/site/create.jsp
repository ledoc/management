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
	<jsp:param value="active" name="menuSiteActive" />
	<jsp:param value="Solices - Détail Site" name="titreOnglet" />
</jsp:include>
<!-- Seulement une visualisation pour les clients -->
<sec:authorize ifAllGranted="ADMIN">
	<c:set var="readOnlyValue" value="false"></c:set>
	<c:if test="${empty site.id}">
		<c:set var="sentenceCreateUpdate" value="créer" />
		<c:set var="labelCreateUpdate" value="Créer" />
		<c:set var="textCreateUpdate" value="Création" />
	</c:if>
	<c:if test="${not empty site.id}">
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
						<h1 class="h3 text-primary mt0">${textCreateUpdate}&nbspd'un site</h1>
						<p class="text-muted">Permet de ${sentenceCreateUpdate} un
							site.</p>
					</header>
					<div class="panel-body">
						<c:url var="createSite" value="/site/create" />
						<form:form id="form" method="POST" action="${createSite}"
							modelAttribute="site" role="form" class="parsley-form"
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
									<label for="typesSiteCombo">Choisir un type</label>
									<form:select id="typesSiteCombo" name="typesSiteCombo"
										path="typeSite" class="form-control"
										data-parsley-required="true"
										data-parsley-required-message="Choix requis"
										disabled="${readOnlyValue }">
										<form:option value="" label="--- Choisir un type ---" />
										<form:options items="${typesSiteCombo}" />
									</form:select>
								</div>
								<div class="form-group">
									<label for="codeSite">Code</label>
									<form:input type="text" class="form-control" id="codeSite"
										path="codeSite" placeholder="" data-parsley-required="true"
										data-parsley-trigger="change"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="departement">Département</label>
									<form:input type="text" class="form-control" id="departement"
										path="departement" placeholder=""
										data-parsley-trigger="change" readonly="${readOnlyValue }" />
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
									<label for="stationMeteo">Codes des Stations météo</label>
									<form:input type="text" class="form-control" id="stationMeteo"
										path="stationMeteo" placeholder=""
										data-parsley-required="true" data-parsley-trigger="change"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readonly="${readOnlyValue }" />
								</div>
								<div class="form-group">
									<label for="etablissements">Rattacher à un
										établissement</label>

									<form:select class="form-control chosen-select"
										data-placeholder="Choisir un etablissement ..."
										data-parsley-required="true" data-parsley-trigger="change"
										data-parsley-required-message="Champ requis"
										path="etablissement.id" disabled="${readOnlyValue}">
										<form:option value="">--- Choisir un établissement ---</form:option>
										<form:options items="${etablissementsCombo}" itemValue="id"
											itemLabel="codeEtablissement" />
									</form:select>

								</div>
								<div class="pull-right">

									<a href="<c:url  value="/site/list" />"
										class="btn btn-default btn-outline">Retour</a>
									<sec:authorize ifAllGranted="ADMIN">
										<button type="submit" class="btn btn-outline btn-primary">${labelCreateUpdate}</button>
									</sec:authorize>
								</div>
							</div>

							<!-- deuxième colonne -->
							<c:if test="${not empty site.ouvrages }">
								<div class="col-md-4">
									<div class="panel panel-default no-b">
										<header class="panel-heading clearfix brtl brtr no-b">
											<div class="overflow-hidden">
												<span class="h4 show no-m pt10">Ouvrages rattachés</span> <small
													class="text-muted">Cliquer pour accéder à la fiche
													de l'ouvrage. </small>
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
												<c:forEach items="${site.ouvrages}" var="ouvrage">
													<tbody>
														<tr class="sommaire" >
															<td class="text-primary" ><c:url
																	var="urlOuvrageUpdate"
																	value="/ouvrage/update/${ouvrage.id}" /> <a
																href="${urlOuvrageUpdate}"
																class="list-group-item text-uppercase "><c:out
																		value="${ouvrage.codeOuvrage}" /> </a></td>
															<td><c:out value="${ouvrage.nom}" /></td>
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