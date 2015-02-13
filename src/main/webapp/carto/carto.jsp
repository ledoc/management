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
						<c:url var="mapUrl" value="/carto" />
						<a class="relayUrl" href="${mapUrl}"></a>
						<c:url var="resourcesUrl" value="/resources" />
						<a class="resourcesUrl" href="${resourcesUrl}"></a>
						<c:url var="ouvrageUrl" value="/ouvrage/update/" />
						<a class="ouvrageUrl" href="${ouvrageUrl}"></a>
						<c:url var="siteUrl" value="/site/update/" />
						<a class="siteUrl" href="${siteUrl}"></a>
						<c:url var="etablissementUrl" value="/etablissement/update/" />
						<a class="etablissementUrl" href="${etablissementUrl}"></a>

						<h3 class="h5 p15">
							<strong>Filtrer par:</strong>
						</h3>
					</header>
					<nav role="navigation">
						<div class="no-padding">
							<div class="form-group ml15 mr15">
								<select id="etablissement" data-placeholder="Etablissement"
									class="form-control js-find-location text-uppercase">
									<option value="O"></option>
									<c:forEach items="${etablissementsCombo}" var="etablissement">
										<option value="${etablissement.id}">${etablissement.codeEtablissement}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group ml15 mr15">
								<select id="site" data-placeholder="Site"
									class="form-control js-find-location  text-uppercase">
									<option value="O"></option>
									<c:forEach items="${sitesCombo}" var="site">
										<option value="${site.id}">${site.codeSite}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group ml15 mr15">
								<select id="ouvrage" data-placeholder="Ouvrage"
									class="form-control js-find-location  text-uppercase">
									<option value="0"></option>
									<c:forEach items="${ouvragesCombo}" var="ouvrage">
										<option value="${ouvrage.id}">${ouvrage.codeOuvrage}</option>
									</c:forEach>
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



		<script type="text/javascript">
			var $etablissement = $('#etablissement');
			var $site = $('#site');
			var $ouvrage = $('#ouvrage');
			
			
			$(function() {
			$etablissement.chosen({
				allow_single_deselect : true
			}, {
				disable_search_threshold : 10
			});
			
			$site.chosen({
				allow_single_deselect : true
			}, {
				disable_search_threshold : 10
			});
			
			$ouvrage.chosen({
				allow_single_deselect : true
			}, {
				disable_search_threshold : 10
			});

			});

			$etablissement.change(function() {
				$site.val(0);
				$ouvrage.val(0);

				$ouvrage.chosen({
					disable_search_threshold : 10
				}).trigger("chosen:updated");

				$site.chosen({
					disable_search_threshold : 10
				}).trigger("chosen:updated");
				
				console.log('ouvrage selectionné ' + $ouvrage.selected)
				console.log('site selectionné ' + $site.selected)
			})

			$site.change(function() {
				$etablissement.val(0);
				$ouvrage.val(0);
				
				
				
				$ouvrage.chosen({
					disable_search_threshold : 10
				}).trigger("chosen:updated");

				$etablissement.chosen({
					disable_search_threshold : 10
				}).trigger("chosen:updated");
				
				console.log('ouvrage selectionné ' + $ouvrage.selected)
				console.log('etab selectionné ' + $etablissement.selected)
			})

			$ouvrage.change(function() {
				$etablissement.val(0);
				$site.val(0);
				
				$site.chosen({
					disable_search_threshold : 10
				}).trigger("chosen:updated");

				$etablissement.chosen({
					disable_search_threshold : 10
				}).trigger("chosen:updated");
				
				console.log('etablissement selectionné ' + $etablissement.selected)
				console.log('site selectionné ' + $site.selected)

			})
		</script>