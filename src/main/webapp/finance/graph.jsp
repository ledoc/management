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
			<div class="sidePanelForGraph col-lg-2 col-md-3 col-xs-12 tools">
				<div class="bg-white shadow pb15">
					<div class="tools-inner">
						<nav role="navigation">
							<div class="no-padding">
								<h3 class="h5 p15 mt0 mb0">
									<b>Sélection</b>
								</h3>


								<div class="form-group ml15 mr15">
									<label>Dates</label> <input id="dateDebut" type="date"
										class="form-control" />
								</div>

								<div class="form-group ml15 mr15">
									<input id="dateFin" type="date" class="form-control" />
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
								<div>&nbsp</div>
								<div>&nbsp</div>
								<div>&nbsp</div>
								<div>&nbsp</div>
								<div>&nbsp</div>
								<div>&nbsp</div>
								<div>&nbsp</div>
								<div>&nbsp</div>
								<div>&nbsp</div>


							</div>
						</nav>
					</div>
				</div>
			</div>
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
			var chartHeight = $('.tools-inner').innerHeight() / 1.5;

			$(document).ready(function() {

				$('#ongletQualitatif').click(function() {
					switchToList()
				});
				$('#ongletQuantitatif').click(function() {
					switchToGraph()
				});

				$.getJSON(relayUrl + '/init/graph/points', function(graph) {

					loadGraph();
					updateSerie(0, graph);
				});

			});

			function loadGraph() {
				Highcharts.setOptions({
					global : {
						timezoneOffset : -60
					}
				});

				$chart
						.highcharts(
								'StockChart',
								{
									chart : {
										zoomType : 'xy',
										height : chartHeight
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
											style : {
												color : '#94ECD9'
											}
										},
										top : '100%',
										height : '100%',
										title : {
											style : {
												color : '#94ECD9'
											}
										},
										min : 0,
										opposite : false

									} ],
									tooltip : {
										formatter : function() {

											var s = '<b>'
													+ Highcharts
															.dateFormat(
																	' %d/%m/%Y %H:%M:%S',
																	this.x)
													+ '</b>';

											$.each(this.points, function() {
												s += '<br/>' + this.y;
											});

											return s
										},
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
				var graphName = graph.name;
				$.each(graph, function(i, line) {
					var item = {};
					item.x = moment(line.date);
					item.y = parseFloat(line.valeur);

					chart.yAxis[0].update({
						title : {
							text : graphName
						},
						labels : {

							format : '{value} '
						}

					}, true);
					serieData.push(item);
				});
				chart.series[index].update({
					data : serieData,
					name : graphName,
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