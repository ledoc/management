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
	<jsp:param value="active" name="menuAccueilActive" />
	<jsp:param value="Solices - Cartographie" name="titreOnglet" />
</jsp:include>

<section class="layout">
	<section class="main-content" id="carto">
		<!-- content wrapper -->
		<div class="content-wrap bg-default clearfix row">
			<!-- start sidebar -->
			<div class="col-lg-2 col-md-3 col-xs-12 tools">
				<div class="bg-white shadow tools-inner">
					<header>
						<h1 class="h3 p15 text-primary mt0">Cartographie</h1>

						<h3 class="h5 p15">
							<strong>Filtrer par:</strong>
						</h3>
					</header>
					<nav role="navigation">
						<div class="no-padding">
							<div class="form-group ml15 mr15">
								<select data-placeholder="Etablissement"
									class="chosen form-control js-find-location text-uppercase">
									<option value=""></option>
									<option value="32 bd paul vaillant couturier 93100 Montreuil">AH2D</option>
									<option
										value="12 rue Léopold Frison 51000 chalons-en-champagne">MORGAGNI-ZEIMETT
									</option>
								</select>
							</div>
							<div class="form-group ml15 mr15">
								<select data-placeholder="Site"
									class="chosen form-control js-find-location">
									<option value=""></option>
									<option>Montreuil</option>
									<option>Marcilly sur Seine</option>
								</select>
							</div>
							<div class="form-group ml15 mr15">
								<select data-placeholder="Ouvrage"
									class="chosen form-control js-find-location">
									<option value=""></option>
									<option>M1-PZ1</option>
									<option>M1-PZ2</option>
									<option>M53-PZ1</option>
								</select>
							</div>
						</div>
					</nav>
				</div>
			</div>
			<!-- end sidebar -->
			<div class="col-lg-10 col-md-9 col-xs-12">
				<div class="bg-white p15 shadow content-inner">

					<div id="map"></div>
				</div>
			</div>
		</div>
		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<!-- end page content -->
		<jsp:include page="/template/footer.jsp" />