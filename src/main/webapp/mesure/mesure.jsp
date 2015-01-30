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


		<!-- content wrapper -->
		<div class="content-wrap bg-default clearfix row">
			<div class="col-lg-2 col-md-3 col-xs-12 tools">
				<div class="bg-white shadow tools-inner">
					<header>
						<h1 class="h3 text-left p15 mt0 mb0">
							<span class="text-muted hidden-sm hidden-xs">Site 1 ></span> <span
								class="text-primary">Ouvrage 1</span>
						</h1>
					</header>

					<nav role="navigation">
						<div class="no-padding">
							<h3 class="h5 p15 mt0 mb0">
								<b>Selection</b>
							</h3>

							<div class="form-group ml15 mr15">
								<select data-placeholder="Site"
									class="chosen form-control text-uppercase">
									<option value=""></option>
									<option value="">Site 1</option>
									<option value="">Site 2</option>
								</select>
							</div>
							<div class="form-group ml15 mr15">
								<select data-placeholder="Ouvrage" class="chosen form-control">
									<option value=""></option>
									<option>Ouvrage 1</option>
									<option>Ouvrage 2</option>
								</select>
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
									<option>Tableau</option>
									<option>Export CSV</option>
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
								<b>Pluviométrie</b>
							</h3>

							<div class="form-group ml15 mr15">
								<select data-placeholder="Options d'affichage"
									class="chosen form-control display-options"
									id="js-change-rainfall-display">
									<option value=""></option>
									<option value="spline">Courbe</option>
									<option value="column">Histogramme</option>
									<option>Tableau</option>
									<option>Export CSV</option>
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
						<li class="active"><a href="#qualitatif" data-toggle="tab">Données
								qualitatives</a></li>
						<li><a href="#quantitatif" data-toggle="tab">Données
								quantitatives</a></li>
					</ul>
					<div class="tab-content text-center no-shadow">
						<div class="tab-pane fade active in" id="qualitatif">
							<div id="charts"></div>
						</div>
						<div class="tab-pane fade" id="quantitatif">
							<h1 class="text-muted bolder pb25 pt25">Quantitatif</h1>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<!-- end page content -->
		<jsp:include page="/template/footer.jsp" />