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


		<c:url var="financeUrl" value="/finance" />
		<a class="relayUrl" href="${financeUrl}"></a> <a class="resourcesUrl"
			href="${resourcesUrl}"></a>

		<!-- content wrapper -->
		<div class="content-wrap bg-default clearfix row">
			<div class="mainPanel col-lg-10 col-md-9 col-xs-12">


				<ul class="nav nav-tabs no-b">
					<li id="ongletRepartition"><a href="#repartition"
						class="active" data-toggle="tab">Répartitions</a></li>
					<li><a id="ongletChronique" href="#chronique"
						data-toggle="tab">Chroniques</a></li>
				</ul>
				<div class="tab-content text-center no-shadow">

					<div class="tab-pane fade active in" id="repartition">
						<div id="chartsCamenbert"></div>
					</div>
					<div class="tab-pane fade active in" id="chronique">
						<div id="chartsGraph"></div>
					</div>
				</div>
			</div>
		</div>

		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<!-- end page content -->
		<jsp:include page="/template/footer.jsp" />

		<c:url var="urlResources" value="/resources" />

		<script type="text/javascript">
			var resourcesUrl = $('.resourcesUrl').attr('href');
			var relayUrl = $('.relayUrl').attr('href');
			var $chartsCamenbert = $('#chartsCamenbert');
			var $chartsGraph = $('#chartsGraph');
			Highcharts.setOptions({
				global : {
					timezoneOffset : -60
				}
			});

			$(document).ready(
					function() {

						loadGraphCamenbert();
						$.getJSON(relayUrl + '/init/camenbert/points',
								function(graph) {

									updateSerieCamenbert("categorie", graph);
								});

						$('#ongletChronique').click(
								loadGraph(),
								function() {
									$.getJSON(relayUrl + '/init/graph/points',
											function(graph) {

												updateSerieGraph(
														"categorie", graph);
											});
								});
						$('#ongletRepartition').click(
								function() {
									$.getJSON(relayUrl
											+ '/init/camenbert/points',
											function(graph) {

										updateSerieCamenbert(
														"categorie", graph);
											});
								});

					});

			function loadGraphCamenbert() {
				var $chartsCamenbert = $('#chartsCamenbert');

				// Radialize the colors
				Highcharts.getOptions().colors = Highcharts.map(Highcharts
						.getOptions().colors, function(color) {
					return {
						radialGradient : {
							cx : 0.5,
							cy : 0.3,
							r : 0.7
						},
						stops : [
								[ 0, color ],
								[
										1,
										Highcharts.Color(color).brighten(-0.3)
												.get('rgb') ] // darken
						]
					};
				});

				// Build the chart
				$chartsCamenbert
						.highcharts({
							chart : {
								plotBackgroundColor : null,
								plotBorderWidth : null,
								plotShadow : false,
								type : 'pie'
							},
							title : {
								text : 'Répartition par catégorie Total'
							},
							tooltip : {
								pointFormat : '{series.name}: <b>{point.y:.1f} €</b>'
							},
							plotOptions : {
								pie : {
									allowPointSelect : true,
									cursor : 'pointer',
									dataLabels : {
										enabled : true,
										format : '<b>{point.name}</b>: {point.percentage:.1f} %',
										style : {
											color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
													|| 'black'
										},
										connectorColor : 'silver'
									}
								}
							},
							series : [ {
								name : "categorie",
								data : []
							} ]
						});

				chartsCamenbert = $chartsCamenbert.highcharts();

			}

			// get data
			function updateSerieCamenbert(index, graph) {
				var serieData = [];
				$.each(graph, function(i, line) {
					var item = {};
					item.name = line.categorie,
							item.y = parseFloat(line.valeur);
					serieData.push(item);
				});
				chartsCamenbert.series[0].update({
					data : serieData,
				});
			};

			function loadGraph() {
				var $chartsGraph = $('#chartsGraph');
				$chartsGraph
						.highcharts(
								'StockChart',
								{
									chart : {
										zoomType : 'xy',
										height : $('.tools-inner')
												.innerHeight()
									},
									title : {
										text : ''
									},
									rangeSelector : {
										selected : false,
										inputEnabled : false
									},
									subtitle : {
										text : ''
									},

									xAxis : [ {
										minRange : 3600000,
										type : 'datetime',
										dateTimeLabelFormats : {
											month : '%d %m',
											year : '%b'
										}
									} ],
									yAxis : [ { // Primary yAxis
										labels : {
											text : 'Montant',
											format : '{value} ' + '€',
											style : {
												color : '#94ECD9'
											}
										},
										height : '100%',
										title : {
											text : 'suivi des Finances',
											style : {
												color : '#94ECD9'
											}
										},
										opposite : false

									} ],
									tooltip : {
										shared : true,
										backgroundColor : {
											linearGradient : {
												x1 : 0,
												y1 : 0,
												x2 : 0,
												y2 : 1
											},
											stops : [ [ 0, 'white' ],
													[ 1, '#EEE' ] ]
										},
										borderColor : 'gray',
										borderWidth : 1
									},
									legend : {
										enabled : true,
										verticalAlign : 'top',
										title : {
											text : 'Cliquez sur la légende pour afficher / cacher une courbe',
											style : {
												fontWeight : 'normal',
												color : '#b3b3b3'
											}
										},
										backgroundColor : (Highcharts.theme && Highcharts.theme.legendBackgroundColor)
												|| '#FFFFFF'
									},
									series : [

									{
										tooltip : {
											valueSuffix : ' ' + '€',
											xDateFormat : ' %d/%m/%Y'
										},
										type : 'line',
										data : [],
										yAxis : 0,
										color : '#94ECD9'
									} ]
								});
				chartsGraph = $chartsGraph.highcharts();
			}

			// get data
			function updateSerieGraph(index, graph) {
				var serieData = [];
				$.each(graph, function(i, line) {
					var item = {};
					item.x = moment(line.date).unix() * 1000,
							item.y = parseFloat(line.valeur);
					serieData.push(item);
				});
				
				console.log(serieData);
				chartsGraph.series[0].update({
					data : serieData,
				});
			};

			function executeIfExist(fct) {
				if (fct != undefined && typeof fct == 'function') {
					fct();
				}
			}
		</script>