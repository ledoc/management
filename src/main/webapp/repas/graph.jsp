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


		<c:url var="repasUrl" value="/repas" />
		<a class="relayUrl" href="${repasUrl}"></a> <a class="resourcesUrl"
			href="${resourcesUrl}"></a>

		<!-- content wrapper -->
		<div class="content-wrap bg-default clearfix row">
			<div class="mainPanel col-lg-10 col-md-9 col-xs-12">


				<ul class="nav nav-tabs no-b">
					<li id="ongletChronique"><a href="#chronique"
						class="active" data-toggle="tab">Chroniques</a></li>
					
				</ul>
				<div class="tab-content text-center no-shadow">

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
			var $chartsGraph = $('#chartsGraph');
			Highcharts.setOptions({
				global : {
					timezoneOffset : -60
				}
			});

			$(document).ready(function() {
				loadGraph();
				$('#ongletChronique').click(loadGraph(), function() {
					$.getJSON(relayUrl + '/init/graph/points', function(graph) {

						updateSerieGraph(graph);
					});
				});
			});

			function loadGraph() {
				var $chartsGraph = $('#chartsGraph');
				$chartsGraph
						.highcharts(
								'StockChart',
								{
									chart : {
										type : 'column',
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
											text : 'Quantité',
											format : '{value}',
											style : {
												color : '#94ECD9'
											}
										},
										height : '100%',
										title : {
											text : 'suivi des Quantité',
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
										name : 'Calories',
										tooltip : {
											valueSuffix : ' ',
											xDateFormat : ' %d/%m/%Y'
										},
										data : [],
										yAxis : 0,
										color : '#94ECD9'
									}, {
										name : 'Protéines',
										tooltip : {
											valueSuffix : ' ',
											xDateFormat : ' %d/%m/%Y'
										},
										data : [],
										yAxis : 0,
										color : '#20F54A'
									}, {
										name : 'Glucides',
										tooltip : {
											valueSuffix : ' ',
											xDateFormat : ' %d/%m/%Y'
										},
										data : [],
										yAxis : 0,
										color : '#F51105'
									}, {
										name : 'Lipides',
										tooltip : {
											valueSuffix : ' ',
											xDateFormat : ' %d/%m/%Y'
										},
										data : [],
										yAxis : 0,
										color : '#0521F5'
									}

									]
								});
				chartsGraph = $chartsGraph.highcharts();
			}

			// get data
			function updateSerieGraph(graph) {
				var serieCalories = [];
				var serieProteines = [];
				var serieGlucides = [];
				var serieLipides = [];
				$.each(graph, function(i, bilan) {
					var itemCalories = {};
					var itemProteines = {};
					var itemGlucides = {};
					var itemLipides = {};
					itemCalories.x = moment(bilan.date).unix() * 1000,
							itemCalories.y = parseFloat(bilan.sommeCalories);

					itemProteines.x = moment(bilan.date).unix() * 1000,
							itemProteines.y = parseFloat(bilan.sommeProteines);

					itemGlucides.x = moment(bilan.date).unix() * 1000,
							itemGlucides.y = parseFloat(bilan.sommeGlucides);

					itemLipides.x = moment(bilan.date).unix() * 1000,
							itemLipides.y = parseFloat(bilan.sommeLipides);

					serieCalories.push(itemCalories);
					serieProteines.push(itemProteines);
					serieGlucides.push(itemGlucides);
					serieLipides.push(itemLipides);
				});

				console.log(serieCalories);
				chartsGraph.series[0].update({
					data : serieCalories,
				});
				chartsGraph.series[1].update({
					data : serieProteines,
				});
				chartsGraph.series[2].update({
					data : serieGlucides,
				});
				chartsGraph.series[3].update({
					data : serieLipides,
				});
			};

			function executeIfExist(fct) {
				if (fct != undefined && typeof fct == 'function') {
					fct();
				}
			}
		</script>