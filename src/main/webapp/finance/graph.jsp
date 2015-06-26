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
					<li id="ongletQuantitatif"><a href="#quantitatif"
						class="active" data-toggle="tab">Graphe</a></li>
					<li><a id="ongletQualitatif" href="#qualitatif"
						data-toggle="tab">Chroniques</a></li>
				</ul>
				<div class="tab-content text-center no-shadow">

					<div class="tab-pane fade active in" id="quantitatif">
						<div id="charts"></div>
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
			var $chart = $('#charts');
			Highcharts.setOptions({
				global : {
					timezoneOffset : -60
				}
			});

			loadGraph();
			
			$(document).ready(function() {

				$('#ongletQualitatif').click(function() {
					switchToList()
				});
				$('#ongletQuantitatif').click(function() {
					switchToGraph()
				});

				$.getJSON(relayUrl + '/init/graph/points', function(graph) {

					updateSerie(0, graph);
				});

			});

			function loadGraph() {
				var $chart = $('#charts');
				$chart
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
				chart = $chart.highcharts();
			}

			// get data
			function updateSerie(index, graph) {
				var serieData = [];
				$.each(graph, function(i, line) {
					var item = {};
					item.x = moment(line.date).unix() * 1000,
					item.y = parseFloat(line.valeur);
					serieData.push(item);
				});
				chart.series[index].update({
					data : serieData,
				});
			};


			function switchToGraph() {
				$('.sidePanelForGraph').attr('style', 'display: block;');
				$('.mainPanel').attr('class',
						"mainPanel col-lg-10 col-md-9 col-xs-12");
				$('.sidePanelForGraph').show();

			}

			function switchToList() {
				$('.sidePanelForGraph').attr('style', 'display: none;');
				$('.mainPanel').attr('class',
						"mainPanel col-lg-12 col-md-12 col-xs-12");
				$('.sidePanelForGraph').hide();

			}

			function executeIfExist(fct) {
				if (fct != undefined && typeof fct == 'function') {
					fct();
				}
			}
		</script>