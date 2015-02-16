<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuClientActive" />
	<jsp:param value="Solices - Détails Client" name="titreOnglet" />
</jsp:include>
<!-- Seulement une visualisation pour les clients -->
<sec:authorize ifAllGranted="ADMIN">
	<c:set var="readOnlyValue" value="false"></c:set>
	<c:if test="${empty client.id}">
		<c:set var="sentenceCreateUpdate" value="créer" />
		<c:set var="labelCreateUpdate" value="Créer" />
		<c:set var="textCreateUpdate" value="Création" />
	</c:if>
	<c:if test="${not empty client.id}">
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
							Client</h1>
						<p class="text-muted">Permet de ${sentenceCreateUpdate} un
							client.</p>
					</header>
					<div class="panel-body">
						<c:url var="createClient" value="/client/create" />
						<form:form id="form" method="POST" action="${createClient}"
							modelAttribute="client" role="form" class="parsley-form"
							data-validate="parsley" data-show-errors="true">


							<form:hidden path="id" />

							<div class="col-md-4 col-lg-4 col-md-4 col-xs-12 col-lg-offset-2">
								<form:errors path="*"
									cssClass="alert alert-danger alert-dismissible fade in"
									element="div" />
								<div class="form-group">
									<label for="nom">Nom</label>
									<form:input type="text" class="form-control" id="nom"
										path="nom" placeholder="" data-parsley-required="true"
										data-parsley-trigger="change"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum"
										readOnly="" />
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
										path="login" placeholder="" data-parsley-required="true"
										data-parsley-trigger="change" data-parsley-mincheck="2"
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
										itemValue="id" itemLabel="codeEtablissement"
										data-placeholder=" Sélectionnez
							une entité"
										class="form-control chosen" disabled="${readOnlyValue }">
									</form:select>
								</div>
								<div class="pull-right">
									<a href="<c:url  value="/client/list" />"
										class="btn btn-default btn-outline">Retour</a>
									<button type="submit" class="btn btn-outline btn-primary">${labelCreateUpdate}</button>
								</div>
							</div>

							<!-- deuxième colonne -->
							<c:if test="${not empty client.etablissements }">
								<div class="col-md-4">
									<div class="panel panel-default no-b">
										<header class="panel-heading clearfix brtl brtr no-b">
											<div class="overflow-hidden">
												<span class="h4 show no-m pt10">Etablissements
													rattachés</span> <small class="text-muted">Cliquer pour
													accéder à la fiche de l'établissement. </small>
											</div>
										</header>

										<div class="list-group">
											<table class="no-b">
												<thead>
													<tr>
														<th>Code</th>
														<th>Nom</th>
													</tr>
												</thead>
												<c:forEach items="${client.etablissements}"
													var="etablissement">
													<tbody>
														<tr>
															<td class="text-primary"><c:url
																	var="urlEtablissementUpdate"
																	value="/etablissement/update/${etablissement.id}" /> <a
																href="${urlEtablissementUpdate}"
																class="list-group-item text-uppercase"><c:out
																		value="${etablissement.codeEtablissement}" /> </a></td>
															<td><div class="list-group-item">
																	<c:out value="${etablissement.nom}" />
																</div></td>
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