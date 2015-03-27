var mesures = function() {

	return {
		init : function() {
			var relayUrl = $('.relayUrl').attr('href');
			var reversed = true, $chart = $('#charts'), $reverseBtn = $('#js-reverseChart'), $rangePicker = $('#js-range-selector'), chartHeight = $(
					'.tools-inner').innerHeight() / 1.5, chart;
			if (!$chart.length) {
				return;
			}

			// chart constructor
			function loadGraph(unite) {
			Highcharts.setOptions(
			{
                global:
                {
                    timezoneOffset: -60
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
												s += '<br/>' + this.y + ' '
														+ unite;
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

				// render chart

				chart = $chart.highcharts();

			}

			// get data
			function updateSerie(index, data) {
				var serieData = [];
				var typeCaptAlerteMesure;
				var unite = '';
				$
						.each(
								data,
								function(i, line) {
									var item = {};
									typeCaptAlerteMesure = line.typeCaptAlerteMesure.description;
									if (line.unite != null) {

										unite = line.unite;
									}
									item.x = moment(line.date);
									item.y = parseFloat(line.valeur);
									$('.description')
											.text(
													line.mid
															+ ' > '
															+ typeCaptAlerteMesure);

									chart.yAxis[0]
											.update(
													{
														title : {
															text : typeCaptAlerteMesure,
														},
														labels : {

															format : '{value} '
																	+ unite
														}

													}, true);
									serieData.push(item);
								});
				chart.series[index].update({
					data : serieData,
					name : typeCaptAlerteMesure,
					tooltip : {
						valueSuffix : unite,
					}
				})
			}
			;

			/**
			 * SECTION D'INTIALISTAION DU GRAPH
			 */

			// initialise le graph avec les valeurs d'un capteur
			$.getJSON(relayUrl + '/init/graph/points', function(data) {
				var point = data[0];
				var unite = ''
				console.log(point);
				if (point.unite != null) {

					unite = point.unite;
				}
				loadGraph(unite);
				updateSerie(0, data);

			});

			// initialise le graph avec les alertes d'un capteur
			$.getJSON(relayUrl + '/init/graph/plotLines/', function(alerte) {

				if (alerte.tendance == 'INFERIEUR') {
					var alignPreAlerte = 'top';
					var alignAlerte = 'bottom';
					var yPrealerte = -6;
					var yAlerte = 12;

				} else {
					var alignPreAlerte = 'bottom';
					var alignAlerte = 'top';
					var yPrealerte = 12;
					var yAlerte = -6;
				}

				chart.yAxis[0].addPlotLine({
					id : 1,
					value : alerte.seuilPreAlerte,
					color : '#E68A00',
					width : 1,
					zIndex : 4,
					label : {
						text : 'pré-alerte',
						verticalAlign : alignPreAlerte,
						y : yPrealerte,
						x : 0
					}
				});
				chart.yAxis[0].addPlotLine({
					id : 1,
					value : alerte.seuilAlerte,
					color : '#A30000',
					width : 1,
					zIndex : 4,
					label : {
						text : 'alerte',
						verticalAlign : alignAlerte,
						y : yAlerte,
						x : 0
					}
				});
			});

			/**
			 * FIN : SECTION D'INTIALISTAION DU GRAPH
			 */

			$('#capteur').chosen().change(
					function() {
						var capteurId = $(this).val();
						$.getJSON(relayUrl + '/capteur/points/'
								+ capteurId, function(data) {
							var point = data[0];
							var unite = ''
							if (point.unite != null) {

								unite = point.unite;
							}
							loadGraph(unite);
							updateSerie(0, data);

						});
					});

			/**
			 * INIT de l'alerte visible après changement de capteur
			 */

			$('#capteur').chosen().change(
					function() {
						var capteurId = $(this).val();
						$.getJSON(relayUrl + '/capteur/plotLines/'
								+ capteurId, function(alerte) {

							if (alerte.tendance == 'INFERIEUR') {
								var alignPreAlerte = 'top';
								var alignAlerte = 'bottom';
								var yPrealerte = -6;
								var yAlerte = 12;

							} else {
								var alignPreAlerte = 'bottom';
								var alignAlerte = 'top';
								var yPrealerte = 12;
								var yAlerte = -6;
							}

							removeAlert(1);
							chart.yAxis[0].addPlotLine({
								id : 1,
								value : alerte.seuilPreAlerte,
								color : '#E68A00',
								width : 1,
								zIndex : 4,
								label : {
									text : 'pré-alerte',
									verticalAlign : alignPreAlerte,
									y : yPrealerte,
									x : 0
								}
							});
							chart.yAxis[0].addPlotLine({
								id : 1,
								value : alerte.seuilAlerte,
								color : '#A30000',
								width : 1,
								zIndex : 4,
								label : {
									text : 'alerte',
									verticalAlign : alignAlerte,
									y : yAlerte,
									x : 0
								}
							});
						});
					});

			/**
			 * FIN : INIT de l'alerte visible après changement de capteur
			 */

			$('#alerte').chosen().change(
					function() {
						var id = $(this).val();
						$.getJSON(relayUrl + '/change/alerte/plotLines/' + id,
								function(alerte) {

									console.log(alerte.tendance);

									if (alerte.tendance == 'INFERIEUR') {
										var alignPreAlerte = 'top';
										var alignAlerte = 'bottom';
										var yPrealerte = -6;
										var yAlerte = 12;

									} else {
										var alignPreAlerte = 'bottom';
										var alignAlerte = 'top';
										var yPrealerte = 12;
										var yAlerte = -6;
									}
									console.log(yPrealerte);
									removeAlert(1);
									chart.yAxis[0].addPlotLine({
										id : 1,
										value : alerte.seuilPreAlerte,
										color : '#E68A00',
										width : 1,
										zIndex : 4,
										label : {
											text : 'pré-alerte',
											verticalAlign : alignPreAlerte,
											y : yPrealerte,
											x : 0
										}
									});
									chart.yAxis[0].addPlotLine({
										id : 1,
										value : alerte.seuilAlerte,
										color : '#A30000',
										width : 1,
										zIndex : 4,
										label : {
											text : 'alerte',
											verticalAlign : alignAlerte,
											y : yAlerte,
											x : 0
										}
									});
								});
					});

			$('#confirmBetweenDate').click(
					function() {
						var urlBase = $('.relayUrl').attr('href');
						var capteurId = $("#capteur").val();
						var dateDebut = $("#dateDebut").val();
						var dateFin = $("#dateFin").val();
						var url = urlBase + '/capteur/points' + '/'
								+ capteurId + '/' + dateDebut + '/'
								+ dateFin;

						$.getJSON(url, function(data) {
							updateSerie(1, data);
							chart.xAxis[0].setExtremes(moment(dateDebut),
									moment(dateFin))
						});

					});

			function removeAlert(plotLineId) {
				return chart.yAxis[0].removePlotLine(plotLineId);
			}

			$('#js-show-alerts').chosen().change(function(e, params) {
				console.log($(this).textContent);
				showAlert($(this).text(), params.selected)
			});

			// reverse rainfall chart
			$reverseBtn.on('ifUnchecked', function(e) {
				chart.yAxis[1].update({
					reversed : false
				});
				reversed = false;
			});
			$reverseBtn.on('ifChecked', function(e) {
				chart.yAxis[1].update({
					reversed : true
				});
				reversed = true;
			});

			// change waterlevel chart display

			$('#js-change-water-display').chosen().change(function(e) {
				chart.series[1].update({
					type : $(this).val()
				})
			});

			// change rainfall chart display
			$('#js-change-rainfall-display').chosen().change(function(e) {
				chart.series[0].update({
					type : $(this).val()
				})
			});
		}
	};
}();

$(function() {
	"use strict";
	mesures.init();

});