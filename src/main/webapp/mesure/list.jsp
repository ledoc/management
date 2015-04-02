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
	<jsp:param value="active" name="menuMesureActive" />
	<jsp:param value="Solices - Mesure" name="titreOnglet" />
</jsp:include>

<section class="layout">
	<section class="main-content" id="carto">


		<c:url var="mesureUrl" value="/mesure" />
		<a class="relayUrl" href="${mesureUrl}"></a>
		<c:url var="mapUrl" value="/carto" />
		<a class="cartoUrl" href="${mapUrl}"></a>
		<c:url var="resourcesUrl" value="/resources" />
		<a class="resourcesUrl" href="${resourcesUrl}"></a>

		<!-- content wrapper -->
		<div class="content-wrap bg-default clearfix row">
			<div class="sidePanelForGraph col-lg-2 col-md-3 col-xs-12 tools">
				<div class="bg-white shadow pb15">
					<div class="tools-inner">
						<nav role="navigation">
							<div class="no-padding">
								<h3 class="h5 p15 mt0 mb0">
									<b>Sélection</b>
								</h3>

								<div class="form-group ml15 mr15">
									<select id="site" name="site"
										data-placeholder="Filtrer par SITE" class="form-control">
									</select>
								</div>

								<div class="form-group ml15 mr15">
									<select id="ouvrage" name="ouvrage"
										data-placeholder="Filtrer par OUVRAGE" class="form-control">
									</select>
								</div>

								<div class="form-group ml15 mr15">
									<select id="enregistreur" name="enregistreur"
										data-placeholder="Filtrer par ENREGISTREUR"
										class="form-control">
									</select>
								</div>

								<div class="form-group ml15 mr15">
									<select id="capteur" name="capteur"
										data-placeholder="choix CAPTEUR" class="form-control">
									</select>
								</div>

								<div class="form-group ml15 mr15">
									<label>Dates</label> <input id="dateDebut" type="date"
										class="form-control" />
								</div>

								<div class="form-group ml15 mr15">
									<input id="dateFin" type="date" class="form-control" />
								</div>

								<div class="form-group ml15 mr15">
									<button id="confirmBetweenDate" disabled="disabled"
										class="btn btn-outline btn-success btn-xs ">
										<i class="fa fa-check"></i>
									</button>
								</div>

								<div class="form-group ml15 mr15">
									<select id="alerte" class="form-control display-options"
										data-placeholder="Choix Alerte">
										<option value="NONE"></option>
									</select>
								</div>

								<div class="form-group ml15 mr15">
									<select data-placeholder="Options d'affichage"
										class="chosen form-control display-options"
										id="js-change-water-display">
										<option value=""></option>
										<option value="spline">Courbe</option>
										<option value="column">Histogramme</option>
									</select>
								</div>

								<h3 class="h5 p15 mt0 mb0">
									<b>Localiser</b>
								</h3>

								<div class="ml15 mr15">
									<div id="map"></div>
								</div>
							</div>
						</nav>
					</div>
				</div>
			</div>
			<div class="mainPanel col-lg-10 col-md-9 col-xs-12">
				<div class="box-tab no-b p15 bg-white shadow">
					<ul class="nav nav-tabs no-b">
						<li id="ongletQuantitatif" class="active"><a
							href="#quantitatif" data-toggle="tab">Graphe</a></li>
						<li><a id="ongletQualitatif" href="#qualitatif"
							data-toggle="tab">Chroniques</a></li>
					</ul>
					<div class="tab-content text-center no-shadow">

						<div class="tab-pane fade active in" id="quantitatif">
							<div id="charts"></div>
						</div>
						<div class="tab-pane fade text-left" id="qualitatif">


							<!-- content wrapper -->
							<div class="content-wrap clearfix pt15">
								<div class="col-lg-12 col-md-12 col-xs-12">
									<section class="panel">
										<header class="panel-heading no-b">
											<h1 class="h3 text-primary mt0">Liste des mesures</h1>

										</header>
										<div class="panel-body">
											<c:if test="${empty mesures }">

												<H2>Ooops !&nbsp;Liste vide.</H2>
											</c:if>
											<c:if test="${not empty mesures }">
												<table id="tableMesures">
													<thead>
														<tr>
															<th>Date</th>
															<th>Valeur</th>
															<th>Ouvrage</th>
															<th>Enregistreur (mid)</th>
															<th>Type</th>
															<th class="nosort nosearch">Actions</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${mesures}" var="mesure">
															<c:url var="urlAffectAsNewNiveauManuel"
																value="/mesure/affect/niveau/manuel/${mesure.id}" />
															<tr>
																<td><fmt:formatDate value="${mesure.date}"
																		type="both" pattern="dd-MM-yyyy HH:mm:ss" /></td>
																<%-- 																<td><c:out value="${mesure.date}" /></td> --%>
																<td><c:out value="${mesure.valeur}" /></td>
																<td><c:out
																		value="${mesure.capteur.enregistreur.ouvrage.codeOuvrage}" /></td>
																<td><c:out
																		value="${mesure.capteur.enregistreur.mid}" /></td>
																<td><c:out
																		value="${mesure.typeCaptAlerteMesure.description}" /></td>
																<td><a data-url="${urlAffectAsNewNiveauManuel}"
																	data-toggle="modal" data-target="#confirmModal"
																	class="btn btn-outline btn-success btn-xs js-confirm-btn">
																		<i class="fa fa-check"></i>
																</a></td>
															</tr>
														</c:forEach>
													</tbody>

												</table>
											</c:if>
										</div>
									</section>
								</div>
								<!-- /inner content wrapper -->
								<!-- Fenetre modale -->
								<div id="confirmModal" class="modal fade bs-modal-sm"
									tabindex="-1" role="dialog" aria-hidden="true"
									data-backdrop="false" data-show="false">
									<div class="modal-dialog modal-sm">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"
													aria-hidden="true">×</button>
												<h4 class="modal-title">Nouveau niveau manuel</h4>
											</div>
											<div class="modal-body">
												<p>Assigner cette mesure comme nouveau niveau manuel ?</p>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">Annuler</button>
												<a id="js-confirm-button" class="btn btn-success">Confirmer</a>
											</div>
										</div>
									</div>
								</div>
								<!-- /Fenêtre modale -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<!-- end page content -->
		<jsp:include page="/template/footer.jsp" />

        <script src="list-charts.js"></script>
        <script src="list-charts-form.js"></script>
