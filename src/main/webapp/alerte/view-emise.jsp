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
	<jsp:param value="active" name="menuAlerteActive" />
	<jsp:param value="Solices - Détail Alerte" name="titreOnglet" />
</jsp:include>

<section class="layout">
	<!-- /sidebar -->
	<section class="main-content bg-white rounded shadow">
		<!-- content wrapper -->

		<div class="content-wrap clearfix pt15">
			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="panel">
					<header class="panel-heading no-b col-lg-offset-2">
						<h1 class="h3 text-primary mt0">Vue d'un Alerte émise</h1>
					</header>

					<div class="panel-body">
						<c:url var="createAlerteDescription"
							value="/alerte/description/create" />
						<form:form id="form" method="POST"
							action="${createAlerteDescription}" modelAttribute="alerte"
							role="form">

							<form:hidden path="id" />

							<div class="col-md-4 col-lg-4 col-md-4 col-xs-12 col-lg-offset-2">
								<fmt:formatDate var="dateAlerteEmise" value="${alerte.date}"
									pattern="dd-MM-yyyy hh:mm:ss" />
								acquittement
								<div class="form-group">
									<label for="acquittement">Acquittement</label> <input
										type="checkbox" class="form-control" id="acquittement"
										value="acquittement" readonly="readonly" />
								</div>
								<div class="form-group">
									<label for="date">Date</label> <input type="text"
										class="form-control" id="date" value="dateAlerteEmise"
										readonly="readonly" />
								</div>
								<div class="form-group">
									<label for="nom">Niveau d'Alerte</label>
									<form:input type="text" class="form-control" id="niveauAlerte"
										path="niveauAlerte.description" readonly="readonly" />
								</div>
								<div class="form-group">
									<label for="nom">Code</label>
									<form:input type="text" class="form-control" id="codeAlerte"
										path="codeAlerte" readonly="readonly" />
								</div>
								<div class="form-group">
									<label for="enregistreur">Enregistreur (identifiant DW)</label>
									<form:input type="text" class="form-control" id="enregistreur"
										path="enregistreur.mid" readonly="readonly" />
								</div>
								<div class="form-group">
									<label for="ouvrage">Ouvrage</label>
									<form:input type="text" class="form-control" id=""
										ouvrage""
										path="enregistreur.ouvrage.codeOuvrage"
										readonly="readonly" />
								</div>
								<div class="form-group">
									<label for="typeAlerte">Type</label>
									<form:input id="typeAlerte" path="typeAlerte.description"
										class="form-control" readonly="readonly" />
								</div>
								<div class="form-group">
									<label for="tendance">Tendance</label>
									<form:input id="tendance" class="form-control"
										path="tendance.description" readonly="readonly" />
								</div>
								<div class="form-group">
									<label for="mesureLevantAlerte">Seuil pré-alerte</label>
									<form:input type="text" class="form-control"
										id="mesureLevantAlerte" path="mesureLevantAlerte.valeur"
										readonly="readonly" />
								</div>
								<div class="form-group">
									<label for="seuilPreAlerte">Seuil pré-alerte</label>
									<form:input type="text" class="form-control"
										id="seuilPreAlerte" path="seuilPreAlerte" readonly="readonly" />
								</div>
								<div class="form-group">
									<label for="seuilAlerte">Seuil d'alerte</label>
									<form:input type="text" class="form-control" id="seuilAlerte"
										path="seuilAlerte" readonly="readonly" />
								</div>
								<div class="form-group">
									<label for="emailDEnvoi">Mail d'envoi</label>
									<form:input class="form-control" id="emailDEnvoi"
										path="emailDEnvoi" readonly="readOnlyValue" />
								</div>
								<div class="form-group">
									<label for="intitule">Intitulé</label>
									<form:input type="text" class="form-control" id="intitule"
										path="intitule" readonly="readOnlyValue" />
								</div>
								<div class="pull-right">
									<a href="<c:url  value="/alerte/list" />"
										class="btn btn-default btn-outline">Retour</a>
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