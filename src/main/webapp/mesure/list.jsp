<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@
        taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>

<jsp:include page="/template/header.jsp">
    <jsp:param value="active" name="menuMesureActive"/>
    <jsp:param value="Solices - Mesure" name="titreOnglet"/>
</jsp:include>

<section class="layout">
<section class="main-content" id="carto">


<c:url var="mesureUrl" value="/mesure"/>
<a class="relayUrl" href="${mesureUrl}"></a>
<c:url var="mapUrl" value="/carto"/>
<a class="cartoUrl" href="${mapUrl}"></a>
<c:url var="resourcesUrl" value="/resources"/>
<a class="resourcesUrl" href="${resourcesUrl}"></a>

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
                            <select id="site" name="site"
                                    data-placeholder="Filtrer par SITE" class="form-control">
                            </select>
                        </div>

                        <div class="form-group ml15 mr15">
                            <select id="ouvrage" name="ouvrage"
                                    data-placeholder="Filtrer par OUVRAGE" class="form-control">
                            </select>
                        </div>

                        <div class="form-group ml15 mr15">
                            <select id="capteur" name="capteur"
                                    data-placeholder="choix CAPTEUR" class="form-control">
                            </select>
                        </div>

                        <div class="form-group ml15 mr15">
                            <label>Dates</label> <input id="dateDebut" type="date"
                                                        class="form-control"/>
                        </div>

                        <div class="form-group ml15 mr15">
                            <input id="dateFin" type="date" class="form-control"/>
                        </div>

                        <div class="form-group ml15 mr15">
                            <select id="alerte" class="form-control display-options"
                                    data-placeholder="Choix Alerte">
                                <option value="NONE"></option>
                            </select>
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

                        <h3 class="h5 p15 mt0 mb0">
                            <b>Localiser</b>
                        </h3>

                        <div class="ml15 mr15">
                            <a href="../carto/carto"><div id="map"></div></a>
                        </div>
                    </div>
                </nav>
            </div>
        </div>
    </div>
    <div class="mainPanel col-lg-10 col-md-9 col-xs-12">
        <div class="box-tab no-b p15 bg-white shadow">
            <ul class="nav nav-tabs no-b">
                <li id="ongletQuantitatif" class="active"><a
                        href="#quantitatif" data-toggle="tab">Graphe</a></li>
                <li><a id="ongletQualitatif" href="#qualitatif"
                       data-toggle="tab">Chroniques</a></li>
            </ul>
            <div class="tab-content text-center no-shadow">

                <div class="tab-pane fade active in" id="quantitatif">
                    <div id="charts"></div>
                </div>
                <div class="tab-pane fade text-left" id="qualitatif">

                    <!-- content wrapper -->
                    <div class="content-wrap clearfix pt15">
                        <div class="col-lg-12 col-md-12 col-xs-12">
                            <section class="panel">
                                <header class="panel-heading no-b">
                                    <h1 class="h3 text-primary mt0">Liste des mesures</h1>

                                </header>
                                <div class="panel-body">
                                    <c:if test="${empty mesures }">
                                        <H2>Ooops !&nbsp;Liste vide.</H2>
                                    </c:if>
                                    <c:if test="${not empty mesures }">
                                        <table id="tableMesures">
                                            <thead>
                                            <tr>
                                                <th>Date</th>
                                                <th>Valeur</th>
                                                <th>Ouvrage</th>
                                                <th>Enregistreur (mid)</th>
                                                <th>Type</th>
                                                <sec:authorize ifAllGranted="ADMIN">
                                                    <th class="nosort nosearch">Actions</th>
                                                </sec:authorize>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${mesures}" var="mesure">
                                                <c:url var="urlAffectAsNewNiveauManuel"
                                                       value="/mesure/affect/niveau/manuel/${mesure.id}"/>
                                                <tr>
                                                    <td><fmt:formatDate value="${mesure.date}"
                                                                        type="both" pattern="dd-MM-yyyy HH:mm:ss"/></td>
                                                        <%-- 																<td><c:out value="${mesure.date}" /></td> --%>
                                                    <td><c:out value="${mesure.valeur}"/></td>
                                                    <td><c:out
                                                            value="${mesure.capteur.enregistreur.ouvrage.codeOuvrage}"/></td>
                                                    <td><c:out
                                                            value="${mesure.capteur.enregistreur.mid}"/></td>
                                                    <td><c:out
                                                            value="${mesure.typeCaptAlerteMesure.description}"/></td>
                                                    <sec:authorize ifAllGranted="ADMIN">
                                                        <td><a data-url="${urlAffectAsNewNiveauManuel}"
                                                               data-toggle="modal" data-target="#confirmModal"
                                                               class="btn btn-outline btn-success btn-xs js-confirm-btn">
                                                            <i class="fa fa-check"></i>
                                                        </a></td>
                                                    </sec:authorize>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:if>
                                </div>
                            </section>
                        </div>
                        <!-- /inner content wrapper -->
                        <!-- Fenetre modale -->
                        <div id="confirmModal" class="modal fade bs-modal-sm"
                             tabindex="-1" role="dialog" aria-hidden="true"
                             data-backdrop="false" data-show="false">
                            <div class="modal-dialog modal-sm">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-hidden="true">×
                                        </button>
                                        <h4 class="modal-title">Nouveau niveau manuel</h4>
                                    </div>
                                    <div class="modal-body">
                                        <p>Assigner cette mesure comme nouveau niveau manuel ?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default"
                                                data-dismiss="modal">Annuler
                                        </button>
                                        <a id="js-confirm-button" class="btn btn-success">Confirmer</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /Fenêtre modale -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /content wrapper -->
<a class="exit-offscreen"></a>
<!-- end page content -->
<jsp:include page="/template/footer.jsp"/>

<c:url var="urlResources" value="/resources" />
<script src="http://maps.google.com/maps/api/js?sensor=true"></script>
<script src="${urlResources}/js/maps.js"></script>
<script src="${urlResources}/plugins/gmaps.js"></script>

<script type="text/javascript">

var mesures = function () {

    return {
        init: function () {
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
                            global: {
                                timezoneOffset: -60
                            }
                        });

                $chart
                        .highcharts(
                        'StockChart',
                        {
                            chart: {
                                zoomType: 'xy',
                                height: chartHeight
                            },
                            title: {
                                text: ''
                            },
                            rangeSelector: {
                                selected: false,
                                inputEnabled: false
                            },
                            subtitle: {
                                text: ''
                            },

                            xAxis: [
                                {
                                    minRange: 3600000,
                                    type: 'datetime',
                                    dateTimeLabelFormats: {
                                        month: '%d %m',
                                        year: '%b'
                                    }
                                }
                            ],
                            yAxis: [
                                { // Primary yAxis
                                    labels: {
                                        style: {
                                            color: '#94ECD9'
                                        }
                                    },
                                    title: {
                                        style: {
                                            color: '#94ECD9'
                                        }
                                    },
                                    min: 0,
                                    opposite: false

                                }
                            ],
                            tooltip: {
                                formatter: function () {

                                    var s = '<b>'
                                            + Highcharts
                                                    .dateFormat(
                                                    ' %d/%m/%Y %H:%M:%S',
                                                    this.x)
                                            + '</b>';

                                    $.each(this.points, function () {
                                        s += '<br/>' + this.y + ' '
                                                + unite;
                                    });

                                    return s
                                },
                                backgroundColor: {
                                    linearGradient: {
                                        x1: 0,
                                        y1: 0,
                                        x2: 0,
                                        y2: 1
                                    },
                                    stops: [
                                        [ 0, 'white' ],
                                        [ 1, '#EEE' ]
                                    ]
                                },
                                borderColor: 'gray',
                                borderWidth: 1
                            },
                            legend: {
                                enabled: true,
                                verticalAlign: 'top',
                                title: {
                                    text: 'Cliquez sur la légende pour afficher / cacher une courbe',
                                    style: {
                                        fontWeight: 'normal',
                                        color: '#b3b3b3'
                                    }
                                },
                                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor)
                                        || '#FFFFFF'
                            },
                            series: [

                                {
                                    type: 'line',
                                    data: [],
                                    yAxis: 0,
                                    color: '#94ECD9'
                                }
                            ]
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
                        function (i, line) {
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
                                        title: {
                                            text: typeCaptAlerteMesure
                                        },
                                        labels: {

                                            format: '{value} '
                                                    + unite
                                        }

                                    }, true);
                            serieData.push(item);
                        });
                chart.series[index].update({
                    data: serieData,
                    name: typeCaptAlerteMesure,
                    tooltip: {
                        valueSuffix: unite
                    }
                })
            }
            ;

            /**
             * SECTION D'INTIALISTAION DU GRAPH
             */

                // initialise le graph avec les valeurs d'un capteur
            $.getJSON(relayUrl + '/init/graph/points', function (data) {
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
            $.getJSON(relayUrl + '/init/graph/plotLines/', function (alerte) {

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
                    id: 1,
                    value: alerte.seuilPreAlerte,
                    color: '#E68A00',
                    width: 1,
                    zIndex: 4,
                    label: {
                        text: 'pré-alerte',
                        verticalAlign: alignPreAlerte,
                        y: yPrealerte,
                        x: 0
                    }
                });
                chart.yAxis[0].addPlotLine({
                    id: 1,
                    value: alerte.seuilAlerte,
                    color: '#A30000',
                    width: 1,
                    zIndex: 4,
                    label: {
                        text: 'alerte',
                        verticalAlign: alignAlerte,
                        y: yAlerte,
                        x: 0
                    }
                });
            });

            /**
             * FIN : SECTION D'INTIALISTAION DU GRAPH
             */

            $('#capteur').chosen().change(
                    function () {
                        var capteurId = $(this).val();
                        $.getJSON(relayUrl + '/capteur/points/'
                                + capteurId, function (data) {
                            var point = data[0];
                            var unite = ''
                            if (point != null && point.unite != null) {
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
                    function () {
                        var capteurId = $(this).val();
                        $.getJSON(relayUrl + '/capteur/plotLines/'
                                + capteurId, function (alerte) {

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
                                id: 1,
                                value: alerte.seuilPreAlerte,
                                color: '#E68A00',
                                width: 1,
                                zIndex: 4,
                                label: {
                                    text: 'pré-alerte',
                                    verticalAlign: alignPreAlerte,
                                    y: yPrealerte,
                                    x: 0
                                }
                            });
                            chart.yAxis[0].addPlotLine({
                                id: 1,
                                value: alerte.seuilAlerte,
                                color: '#A30000',
                                width: 1,
                                zIndex: 4,
                                label: {
                                    text: 'alerte',
                                    verticalAlign: alignAlerte,
                                    y: yAlerte,
                                    x: 0
                                }
                            });
                        });
                    });

            /**
             * FIN : INIT de l'alerte visible après changement de capteur
             */

            $('#alerte').chosen().change(
                    function () {
                        var id = $(this).val();
                        $.getJSON(relayUrl + '/change/alerte/plotLines/' + id,
                                function (alerte) {

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
                                        id: 1,
                                        value: alerte.seuilPreAlerte,
                                        color: '#E68A00',
                                        width: 1,
                                        zIndex: 4,
                                        label: {
                                            text: 'pré-alerte',
                                            verticalAlign: alignPreAlerte,
                                            y: yPrealerte,
                                            x: 0
                                        }
                                    });
                                    chart.yAxis[0].addPlotLine({
                                        id: 1,
                                        value: alerte.seuilAlerte,
                                        color: '#A30000',
                                        width: 1,
                                        zIndex: 4,
                                        label: {
                                            text: 'alerte',
                                            verticalAlign: alignAlerte,
                                            y: yAlerte,
                                            x: 0
                                        }
                                    });
                                });
                    });

            function removeAlert(plotLineId) {
                return chart.yAxis[0].removePlotLine(plotLineId);
            }

            $('#js-show-alerts').chosen().change(function (e, params) {
                console.log($(this).textContent);
                showAlert($(this).text(), params.selected)
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

            // change waterlevel chart display

            $('#js-change-water-display').chosen().change(function (e) {
                chart.series[1].update({
                    type: $(this).val()
                })
            });

            // change rainfall chart display
            $('#js-change-rainfall-display').chosen().change(function (e) {
                chart.series[0].update({
                    type: $(this).val()
                })
            });
        }
    };
}();

$(function () {
    "use strict";
    mesures.init();

});


$(document).ready(
        function () {
            $.fn.dataTable.moment('dd-MM-yyyy HH:mm:ss');
            $('#tableMesures').DataTable({
                "columnDefs": [
                    {
                        "sType": "datetime-moment",
                        "targets": [ 0 ]
                    }
                ],
                "order": [
                    [ 0, 'desc' ]
                ]
            });

            $('#tableMesures').attr('class',
                    "table table-striped list no-m");
            //select box pour le nombre de résultats à afficher
            $('.chosen').chosen({
                width: "80px",
                disable_search_threshold: 10
            });
        });

$('#ongletQualitatif').click(function () {
    switchToList()
});
$('#ongletQuantitatif').click(function () {
    switchToGraph()
});

function switchToGraph() {
    $('.sidePanelForGraph').attr('style', 'display: block;');
    $('.mainPanel').attr('class',
            "mainPanel col-lg-10 col-md-9 col-xs-12");
    $('.descriptionEntities').show();
    $('.sidePanelForGraph').show();

}

function switchToList() {
    $('.sidePanelForGraph').attr('style', 'display: none;');
    $('.mainPanel').attr('class',
            "mainPanel col-lg-12 col-md-12 col-xs-12");
    $('.descriptionEntities').hide();
    $('.sidePanelForGraph').hide();

}

$('#confirmModal').modal();
$('#confirmModal').on('show.bs.modal', function (e) {
    var url = $(e.relatedTarget).data('url');
    var $confirmButton = $('#js-confirm-button');
    $confirmButton.attr('href', url);
});


var $site = $('#site');
var $ouvrage = $('#ouvrage');
var $capteur = $('#capteur');

$(function () {
    $.getJSON($('.relayUrl').attr('href') + '/init/site',
            null, function (listSite) {
                fillSelect($site, listSite);
                <c:if test="${not empty ouvrageId }">
                    setOuvrage(${ouvrageId});
                </c:if>
            });
});

$site.chosen().change(function () {
    $.getJSON($('.relayUrl').attr('href') + '/site/refresh/ouvrage/' + $(this).val(),
            null, function (listOuvrage) {
                fillSelect($ouvrage, listOuvrage);
                $capteur.empty();
                $capteur.val(-1);
                $capteur.trigger("chosen:updated");
            });
});

$ouvrage.chosen().change(function () {
    refreshCapteur($(this).val());
});

$capteur.chosen();

function refreshCapteur(ouvrageId){
    $.getJSON($('.relayUrl').attr('href') + '/ouvrage/refresh/capteur/' + ouvrageId,
            null, function (listCapteur) {
                fillSelect($capteur, listCapteur);
            });
}

function fillSelect(select, list){
    select.empty();
    $.each(list, function (index, item) {
        var $option = $('<option>', {
            value: item.id,
            text: item.name
        });
        select.append($option);
    });
    select.val(-1);
    select.trigger("chosen:updated");
}

function setOuvrage(ouvrageId){
    $.getJSON($('.relayUrl').attr('href') + '/init/site/ouvrage/' + ouvrageId,
            null, function (site) {
                $site.val(site.id);
                $site.trigger("chosen:updated");
                fillSelect($ouvrage, site.ouvrages);
                $ouvrage.val(ouvrageId);
                $ouvrage.trigger("chosen:updated");
                refreshCapteur(ouvrageId);
            });
}

</script>
