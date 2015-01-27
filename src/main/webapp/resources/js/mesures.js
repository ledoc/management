var mesures = function () {

    return {
        init: function () {
            var reversed = true,
                $chart = $('#charts'),
                $reverseBtn = $('#js-reverseChart'),
                chart;

            function updateSerie(index, data){
                var serieData = [];
                $.each(data, function (i, line) {
                    var item = {};
                    item['x'] = Date.parse(line.date);
                    item['y'] = parseFloat(line.valeur);
                    serieData.push(item);
                });
                chart.series[index].update({
                    data: serieData
                })
            }

            $chart.highcharts({
                chart: {
                    zoomType: 'xy'
                },
                title: {
                    text: ''
                },
                subtitle: {
                    text: ''
                },
                xAxis: [{
                    type: 'datetime',
                    dateTimeLabelFormats: {
                        month: '%e. %b',
                        year: '%b'
                    }
                }],
                yAxis: [{ // Primary yAxis
                    labels: {
                        format: '{value} m',
                        style: {
                            color: '#94ECD9'
                        }
                    },
                    title: {
                        text: 'Hauteur d\'eau',
                        style: {
                            color: '#94ECD9'
                        }
                    },
                    min: 0,
                    plotLines: [{
                        value: 7.77,
                        width: 2,
                        color: '#FF604F',
                        dashStyle: 'dash',
                        label: {
                            text: 'Alerte C1',
                            align: 'right',
                            y: 12,
                            x: 0
                        }
                    }]
                },
                    { // Secondary yAxis
                        labels: {
                            format: '{value} mm',
                            style: {
                                color: '#20AAE5'
                            }
                        },
                        title: {
                            text: 'Pluviométrie',
                            style: {
                                color: '#20AAE5'
                            }
                        },
                        opposite: true,
                        min: 0,
                        reversed: reversed
                    }],
                tooltip: {
                    shared: true
                },
                legend: {
                    layout: 'vertical',
                    align: 'left',
                    x: 120,
                    verticalAlign: 'top',
                    y: 80,
                    floating: true,
                    backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
                },
                series: [{
                    name: 'Pluviométrie',
                    type: 'column',
                    yAxis: 1,
                    data: [],
                    color: '#20AAE5',
                    tooltip: {
                        valueSuffix: ' mm'
                    }

                }, {
                    name: 'Hauteur d\'eau',
                    type: 'line',
                    data: [],
                    yAxis: 0,
                    color: '#94ECD9',
                    tooltip: {
                        valueSuffix: ' m'
                    }
                }]
            });

            // get data

            chart = $chart.highcharts();

            $.getJSON('./js/data/hauteur_eau.json', function (data) {
                updateSerie(1, data);
            });

            $.getJSON('./js/data/pluviometrie.json', function (data) {
                updateSerie(0, data);
            });

            // reverse rainfall chart
            $reverseBtn.on('ifUnchecked', function (e) {
                chart.yAxis[1].update({
                    reversed: false
                });
                reversed = false;
            });
            $reverseBtn.on('ifChecked', function (e) {
                chart.yAxis[1].update({
                    reversed: true
                });
                reversed = true;
            });
        }
    };
}();

$(function () {
    "use strict";
    mesures.init();
});