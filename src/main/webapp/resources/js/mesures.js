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
									inputDateFormat : '%d/%m/%Y',
									inputEditDateFormat : '%d/%m/%Y'
								},
								subtitle : {
									text : ''
								},
								xAxis : [ {
									type : 'datetime',
									dateTimeLabelFormats : {
										month : '%d %m',
										year : '%b'
									}
								} ],
								yAxis : [ { // Primary yAxis
									labels : {
										format : '{value} m',
										style : {
											color : '#94ECD9'
										}
									},
									title : {
										text : 'Conductivité',
										style : {
											color : '#94ECD9'
										}
									},
									min : 0,
									opposite : false

								} ],
								tooltip : {
									shared : true
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
									name : 'Conductivité',
									type : 'line',
									data : [],
									yAxis : 0,
									color : '#94ECD9',
									tooltip : {
										valueSuffix : ' m'
									}
								} ]
							});

			// render chart

			chart = $chart.highcharts();

			// get data
			function updateSerie(index, data) {
				var serieData = [];
				$.each(data, function(i, line) {
					var item = {};
					item.x = moment(line.date);
					item.y = parseFloat(line.valeur);
					$('.mid').text(line.midEnregistreur);

					serieData.push(item);
				});
				chart.series[index].update({
					data : serieData
				})
			}
			;

			// initialise le graph avec les valeurs d'un enregistreur
			$.getJSON(relayUrl + '/init/graph/points', function(data) {
				updateSerie(0, data);
			});

			// initialise le graph avec les alertes d'un enregistreur
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

			$('#enregistreur').chosen().change(
					function() {
						var id = $(this).val();
						$.getJSON(relayUrl + '/enregistreur/points/' + id,
								function(data) {
									updateSerie(0, data);

								});
					});

			$('#enregistreur')
					.chosen()
					.change(
							function() {
								var id = $(this).val();
								$
										.getJSON(
												relayUrl
														+ '/enregistreur/plotLines/'
														+ id,
												function(alerte) {

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
													chart.yAxis[0]
															.addPlotLine({
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
													chart.yAxis[0]
															.addPlotLine({
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
						var enregistreurId = $("#enregistreur").val();
						var dateDebut = $("#dateDebut").val();
						var dateFin = $("#dateFin").val();
						var url = urlBase + '/enregistreur/points' + '/'
								+ enregistreurId + '/' + dateDebut + '/'
								+ dateFin;

						$.getJSON(url, function(data) {
							updateSerie(1, data);
							chart.xAxis[0].setExtremes(moment(dateDebut),
									moment(dateFin))
						});

					});

			// function showAlert() {
			// var plotLines = {
			// value : 0.4,
			// color : '#ff0000',
			// width : 2,
			// zIndex : 4,
			// label : {
			// text : 'prealerte'
			// }
			// }
			//
			// chart.yAxis[0].addPlotLine(plotLines);
			//
			// }

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

			// range selector

			// $rangePicker.on('apply.daterangepicker', function (ev, picker) {
			// var startDate = moment(picker.startDate).utc().format();
			// var endDate = moment(picker.endDate).utc().format();
			// console.log(startDate );
			// console.log(endDate);
			// if (startDate && endDate) {
			// chart.xAxis[0].setExtremes(startDate, endDate);
			// }
			// });
		}
	};
}();

$(function() {
	"use strict";
	mesures.init();

});