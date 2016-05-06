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
	<jsp:param value="active" name="menurepasActive" />
	<jsp:param value="Solices - Détails repas" name="titreOnglet" />
</jsp:include>

<c:url var="urlResources" value="/resources" />

<!-- Seulement une visualisation pour les clients -->
<c:set var="readOnlyValue" value="false"></c:set>
<c:if test="${empty repas.id}">
	<c:set var="sentenceCreateUpdate" value="créer" />
	<c:set var="labelCreateUpdate" value="Créer" />
	<c:set var="textCreateUpdate" value="Création" />
</c:if>
<c:if test="${not empty repas.id}">
	<c:set var="sentenceCreateUpdate" value="mettre à jour" />
	<c:set var="labelCreateUpdate" value="Mettre à jour" />
	<c:set var="textCreateUpdate" value="Mise à jour" />
</c:if>

<section class="layout">
	<!-- /sidebar -->
	<section class="main-content bg-white rounded shadow">
		<!-- content wrapper -->


		<div class="content-wrap clearfix pt15">



			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="panel">

					<div class="panel-body">
						<div class="col-md-6 col-lg-6 col-md-6 col-xs-12 col-lg-offset-2">
							<fieldset class="fsStyle">

								<legend class="legendStyle"
									style="width: auto; border-bottom: 0px">
									<a data-toggle="collapse" data-target="#formPlat" href="#">Créer
										un nouveau plat pour ce repas?</a>
								</legend>
								<div class="row collapse" id="formPlat">
									<c:if test="${not empty repas.id}">
										<c:url var="createPlat" value="/plat/create/${repas.id}" />
									</c:if>
									<c:if test="${empty repas.id}">
										<c:url var="createPlat" value="/plat/create/0" />
									</c:if>
									<form:form id="form" method="POST" action="${createPlat}"
										modelAttribute="plat" role="form"
										class="parsley-form form-inline" data-validate="parsley"
										data-show-errors="true">

										<form:hidden path="id" />

										<div
											class="col-md-12 col-lg-12 col-md-12 col-xs-12 col-lg-offset-2">

											<div class="form-group">
												<label class="sr-only" for="nom">Nom</label>
												<form:input type="text" class="form-control" id="nomPlat"
													path="nom" placeholder="Nom" data-parsley-trigger="change" />
											</div>

											<div class="form-group" style="margin-top: -10px">
												<label class="sr-only" for="nom">Aliment</label>
												<form:select class="form-control chosen"
													style="min-width: 200px; width:200px"
													data-placeholder="Choisir un aliment ..." path="aliment.id">
													<form:option value="">--- Choisir un aliment ---</form:option>
													<form:options items="${listAlimentCombo}" itemValue="id"
														itemLabel="nom" />
												</form:select>
											</div>

											<div class="form-group">
												<label class="sr-only" for="quantite">Quantité</label>
												<form:input type="text" class="form-control" id="quantite"
													path="quantite" placeholder="Quantité"
													data-parsley-trigger="change" />
											</div>

											<div>
												<a href="<c:url  value="/plat/list" />"
													class="btn btn-default btn-outline">Retour</a>
												<button type="submit" class="btn btn-outline btn-primary">${labelCreateUpdate}</button>
											</div>
										</div>
									</form:form>
								</div>
							</fieldset>
						</div>

						<div class="panel-body">
							<c:url var="createRepas" value="/repas/create" />
							<form:form id="form" method="POST" action="${createRepas}"
								modelAttribute="repas" role="form" class="parsley-form"
								data-validate="parsley" data-show-errors="true">

								<div class="col-md-6 col-lg-6 col-md-6 col-xs-12 ">
									<header class="panel-heading no-b col-lg-offset-4">
										<h1 class="h3 text-primary mt0">${textCreateUpdate}&nbspd'un
											repas</h1>
										<p class="text-muted">Permet de ${sentenceCreateUpdate} un
											repas.</p>
									</header>
									<form:hidden path="id" />
								</div>
								<div
									class="col-md-6 col-lg-6 col-md-6 col-xs-12 col-lg-offset-2">



									<div class="form-group">
										<label for="nom">Nom</label>
										<form:input type="text" class="form-control" id="nom"
											path="nom" placeholder="" />
									</div>
									<div class="form-group">
										<c:if test="${empty repas.listPlats }">

											<H2>Ooops !&nbsp;Liste vide.</H2>
										</c:if>
										<c:if test="${not empty repas.listPlats }">

											<table class="table table-striped list no-m">
												<thead>
													<tr>
														<th>Aliment</th>
														<th>Quantité</th>
														<th>Calories</th>
														<th class="nosort nosearch">Actions</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${repas.listPlats}" var="plat">
														<c:url var="urlPlatDelete" value="/plat/delete/${plat.id}" />
														<c:url var="urlPlatUpdate" value="/plat/update/${plat.id}" />
														<tr>
															<td class="text-primary"><a href="${urlPlatUpdate}">${plat.aliment.nom}</a></td>
															<td><c:out value="${plat.quantite}" /></td>
															<td><c:out value="${plat.nutritionBilan.calories}" /></td>
															<td><a data-url="${urlPlatDelete}"
																data-toggle="modal" data-target="#confirmModal"
																class="btn btn-outline btn-danger btn-xs js-confirm-btn">
																	<i class="fa fa-remove"></i>
															</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>

									</div>
									<div class="form-group">
										<label for="date">Date</label>
										<form:input type="date" class="form-control" id="date"
											path="date" placeholder="" data-parsley-required="true"
											data-parsley-trigger="change"
											data-parsley-required-message="Champ requis" />
									</div>
									<div class="form-group">
										<label for="repasCopy">Copier un Repas???</label>

										<form:select class="form-control chosen"
											data-placeholder="--- Copier un repas??? ---"
											path="copyRepasId">
											<form:option value="--- Copier un repas??? ---"></form:option>
											<form:options items="${listRepasCombo}" itemLabel="nom"
												itemValue="id" />
										</form:select>
									</div>
									<div class="form-group">
										<label for="repasCopy">List Plat</label>

										<form:select class="form-control chosen"
											data-placeholder="--- List des plats ---" path="listPlats">
											<form:options items="${listPlatCombo}" itemLabel="nom"
												itemValue="id" />
										</form:select>
									</div>
									<div class="form-group">
										<label for="repas">Repas</label>

										<form:select class="form-control chosen"
											data-placeholder="--- Choisir un repas ---" path="typeRepas">
											<form:option value="-- Choisir un repas ---"></form:option>
											<form:options items="${typesRepasCombo}"
												itemLabel="description" />
										</form:select>
									</div>
									<div class="pull-right">
										<a href="<c:url  value="/repas/list" />"
											class="btn btn-default btn-outline">Retour</a>
										<button type="submit" class="btn btn-outline btn-primary">${labelCreateUpdate}</button>
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