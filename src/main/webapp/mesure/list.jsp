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
			<div class="col-lg-2 col-md-3 col-xs-12 tools">
				<div class="bg-white shadow tools-inner">

					<header>
						<div class="form-group ml15 mr15 h7 text-left mt0 mb0">
							<span class="codeOuvrage text-muted hidden-sm hidden-xs"></span><span
								class="mid text-primary"></span>
						</div>
					</header>

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
									data-placeholder="choisir un ENREGISTREUR" class="form-control">
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
							<h3 class="h5 p15 mt0 mb0">
								<b>Hauteur d'eau</b>
							</h3>

							<div class="form-group ml15 mr15">
								<select data-placeholder="Options d'affichage"
									class="chosen form-control display-options"
									id="js-change-water-display">
									<option value=""></option>
									<option value="spline">Courbe</option>
									<option value="column">Histogramme</option>
								</select>
							</div>
							<div class="form-group ml15 mr15 mb0">
								<select data-placeholder="Sélectionner les alertes"
									class="chosen form-control show-alerts" multiple
									id="js-show-alerts">
									<option value=""></option>
									<option value="78.25">C1 - 78,25m</option>
									<option value="18.50">C2 - 18,50m</option>
								</select>
							</div>
							<h3 class="h5 p15 mt0 mb0">
								<b>Conductivité</b>
							</h3>

							<div class="form-group ml15 mr15">
								<select data-placeholder="Options d'affichage"
									class="chosen form-control display-options"
									id="js-change-rainfall-display">
									<option value=""></option>
									<option value="spline">Courbe</option>
									<option value="column">Histogramme</option>
								</select>
							</div>
							<div class="form-group icheck ml15 mr15 mt5">
								<input type="checkbox" id="js-reverseChart" checked> <label
									for="js-reverseChart" class="">Inverser la courbe</label>
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
			<div class="col-lg-10 col-md-9 col-xs-12">
				<div class="box-tab no-b p15 bg-white shadow content-inner">
					<ul class="nav nav-tabs no-b">
						<li class="active"><a href="#quantitatif" data-toggle="tab">Données
								quantitatives</a></li>
						<li><a href="#qualitatif" data-toggle="tab">Données
								qualitatives</a></li>
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
												<table class="table table-striped list no-m">
													<thead>
														<tr>
															<th>Date</th>
															<th>Valeur</th>
															<th>Ouvrage</th>
															<th>Enregistreur (mid)</th>
															<th>Type</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${mesures}" var="mesure">
															<c:url var="urlMesureDelete"
																value="/mesure/delete/${mesure.id}" />
															<c:url var="urlMesureUpdate"
																value="/mesure/update/${mesure.id}" />
															<tr>
																<td><fmt:formatDate value="${mesure.date}"
																		pattern="dd-MM-yyyy HH:mm:ss" /></td>
																<td><c:out value="${mesure.valeur}" /></td>
																<td><c:out
																		value="${mesure.enregistreur.ouvrage.codeOuvrage}" /></td>
																<td><c:out value="${mesure.enregistreur.mid}" /></td>
																<td><c:out
																		value="${mesure.typeMesureOrTrame.description}" /></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</c:if>
										</div>
									</section>
								</div>
								<!-- /inner content wrapper -->

							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /content wrapper -->
			<a class="exit-offscreen"></a>
			<!-- end page content -->
			<jsp:include page="/template/footer.jsp" />

			<script type="text/javascript">
				$(function() {
					initListSite();
					initListOuvrage();
					initListEnregistreur();
				});

				function checkAllFieldsHaveValues() {

					var enregistreurVal = $('#enregistreur').val();
					var dateDebutVal = $('#dateDebut').val();
					var dateFinVal = $('#dateFin').val();
					console.log('enregistreur : ' + enregistreurVal);
					console.log('dateDebut : ' + dateDebutVal);
					console.log('dateFin : ' + dateFinVal);

					if (enregistreurVal && dateDebutVal && dateFinVal) {

						$('#confirmBetweenDate').attr('disabled', false);
					}
				}

				function initListSite() {

					var $site = $('#site');

					$
							.getJSON(
									$('.relayUrl').attr('href') + '/init/site',
									null,
									function(listSite) {

										var output = []
										$
												.each(
														listSite,
														function(index, site) {
															var tpl = '<option value="NONE"></option><option value="' + site.id + '">'
																	+ site.codeSite
																	+ '</option>';
															output.push(tpl);
														});
										$site.html(output.join(''));
										$site.chosen({
											allow_single_deselect : true
										}, {
											disable_search_threshold : 10
										});
										$site.trigger("chosen:updated");
									});
				};

				function initListOuvrage() {

					var $ouvrage = $('#ouvrage');

					$
							.getJSON(
									$('.relayUrl').attr('href')
											+ '/init/ouvrage',
									null,
									function(listOuvrage) {

										var output = []
										$
												.each(
														listOuvrage,
														function(index, ouvrage) {
															var tpl = '<option value="NONE"></option><option value="' + ouvrage.id + '">'
																	+ ouvrage.codeOuvrage
																	+ '</option>';
															output.push(tpl);
															console.log(tpl);
														});
										$ouvrage.html(output.join(''));
										$ouvrage.chosen({
											allow_single_deselect : true
										}, {
											disable_search_threshold : 10
										});
										$ouvrage.trigger("chosen:updated");
									});
				};

				function initListEnregistreur() {

					var $enregistreur = $('#enregistreur');

					$
							.getJSON(
									$('.relayUrl').attr('href')
											+ '/init/enregistreur',
									null,
									function(listEnregistreur) {

										var output = []
										$
												.each(
														listEnregistreur,
														function(index,
																enregistreur) {
															var tpl = '<option value="NONE"></option><option value="' + enregistreur.id + '">'
																	+ enregistreur.mid
																	+ '</option>';
															output.push(tpl);
														});
										$enregistreur.html(output.join(''));
										$enregistreur.chosen({
											allow_single_deselect : true
										}, {
											disable_search_threshold : 10
										});
										$enregistreur.trigger("chosen:updated");
									});
				};
				// Modifie la selectBox ouvrage filtrer par le site
				$("#site")
						.change(

								function() {
									var $ouvrage = $('#ouvrage');
									$
											.get(
													$('.relayUrl').attr('href')
															+ '/site/refresh/ouvrage/'
															+ $(this).val(),
													null,
													function(listOuvrage) {
														var output = [];
														$ouvrage
																.attr(
																		'data-placeholder',
																		'Filtrer par Ouvrage');
														$
																.each(
																		listOuvrage,
																		function(
																				index,
																				ouvrage) {
																			var tpl = '<option value="NONE"></option><option value="' + ouvrage.id + '">'
																					+ ouvrage.codeOuvrage
																					+ '</option>';
																			output
																					.push(tpl);
																		});
														$ouvrage.html(output
																.join(''));
														if (!listOuvrage.length) {
															$ouvrage
																	.attr(
																			'data-placeholder',
																			'Aucun ouvrage pour ce Site');

														}

														$ouvrage.selected = false;
														$ouvrage
																.chosen(
																		{
																			disable_search_threshold : 10
																		})
																.trigger(
																		"chosen:updated");
													});
								});
				// Modifie la selectBox enregistreur filtrer par le site
				$("#site")
						.change(

								function() {
									var $enregistreur = $('#enregistreur');
									$
											.get(
													$('.relayUrl').attr('href')
															+ '/site/refresh/enregistreur/'
															+ $(this).val(),
													null,
													function(listEnregistreur) {
														var output = [];
														$enregistreur
																.attr(
																		'data-placeholder',
																		'Choisir l\'ENREGISTREUR');
														$
																.each(
																		listEnregistreur,
																		function(
																				index,
																				enregistreur) {
																			var tpl = '<option value="NONE"></option><option value="' + enregistreur.id + '">'
																					+ enregistreur.mid
																					+ '</option>';
																			output
																					.push(tpl);
																		});
														$enregistreur
																.html(output
																		.join(''));
														if (!listEnregistreur.length) {
															$enregistreur
																	.attr(
																			'data-placeholder',
																			'Aucun enregistreur pour ce filtre');

														}

														$enregistreur.selected = false;
														$enregistreur
																.chosen(
																		{
																			disable_search_threshold : 10
																		})
																.trigger(
																		"chosen:updated");
													});
								});

				// Modifie la selectBox enregistreur filtrer par l'ouvrage
				$("#ouvrage")
						.change(

								function() {
									var $enregistreur = $('#enregistreur');
									var $site = $('#site');
									$('.codeOuvrage').text(
											$(this).attr('text') + ' >');
									$
											.get(
													$('.relayUrl').attr('href')
															+ '/ouvrage/refresh/enregistreur/'
															+ $(this).val(),
													null,
													function(listEnregistreur) {
														var output = [];
														$enregistreur
																.attr(
																		'data-placeholder',
																		'Choisir l\'ENREGISTREUR');
														$
																.each(
																		listEnregistreur,
																		function(
																				index,
																				enregistreur) {
																			var tpl = '<option value="NONE"></option><option value="' + enregistreur.id + '">'
																					+ enregistreur.mid
																					+ '</option>';
																			output
																					.push(tpl);
																		});
														$enregistreur
																.html(output
																		.join(''));
														if (!listEnregistreur.length) {
															$enregistreur
																	.attr(
																			'data-placeholder',
																			'Aucun enregistreur pour ce filtre');

														}

														$enregistreur.selected = false;
														$enregistreur
																.chosen(
																		{
																			disable_search_threshold : 10
																		})
																.trigger(
																		"chosen:updated");
													});
								});

				$("#ouvrage").change(function() {
					$('#site').selected = false, initListSite()
				});
				$("#ouvrage").change(initListSite());

				$("#dateDebut").change(function() {
					checkAllFieldsHaveValues()
				});
				$("#dateFin").change(function() {
					checkAllFieldsHaveValues()
				});
				$("#enregistreur").change(
						function() {
							$('#site').selected = false,
									checkAllFieldsHaveValues(), initListSite(),
									$('#ouvrage').selected = false,
									initListOuvrage()
						})
			</script>