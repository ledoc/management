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
                            <select id="enregistreur" name="enregistreur"
                                    data-placeholder="Filtrer par ENREGISTREUR"
                                    class="form-control">
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
                            <button id="confirmBetweenDate" disabled="disabled"
                                    class="btn btn-outline btn-success btn-xs ">
                                <i class="fa fa-check"></i>
                            </button>
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
                            <div id="map"></div>
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

<script type="text/javascript">

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



















$(document).ready(
        function() {
            $.fn.dataTable.moment( 'dd-MM-yyyy HH:mm:ss' );
            $('#tableMesures').DataTable({
                "columnDefs" : [ {
                    "sType" : "datetime-moment",
                    "targets" : [ 0 ]
                } ],
                "order" : [ [ 0, 'desc' ] ]
            });

            $('#tableMesures').attr('class',
                    "table table-striped list no-m");
            //select box pour le nombre de résultats à afficher
            $('.chosen').chosen({
                width : "80px",
                disable_search_threshold : 10
            });
        });

$('#ongletQualitatif').click(function() {
    switchToList()
});
$('#ongletQuantitatif').click(function() {
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
$('#confirmModal').on('show.bs.modal', function(e) {
    var url = $(e.relatedTarget).data('url');
    var $confirmButton = $('#js-confirm-button');
    $confirmButton.attr('href', url);
});

$(function() {
    initListSite();
    initListOuvrage();
    initListEnregistreur();
});

function checkAllFieldsHaveValues() {

    var capteurVal = $('#capteur').val();
    var dateDebutVal = $('#dateDebut').val();
    var dateFinVal = $('#dateFin').val();
    console.log('capteur : ' + capteurVal);
    console.log('dateDebut : ' + dateDebutVal);
    console.log('dateFin : ' + dateFinVal);

    if ((capteurVal && capteurVal != 'NONE') && dateDebutVal
            && dateFinVal) {

        $('#confirmBetweenDate').attr('disabled', false);
    } else {
        $('#confirmBetweenDate').attr('disabled', true);
    }
}

// Modifie la selectBox ouvrage filtrer par le site

$("#site")
        .change(

        function() {
            var $ouvrage = $('#ouvrage');
            $
                    .get(
                            $('.relayUrl').attr('href')
                            + '/site/refresh/ouvrage/'
                            + $(this).val(),
                    null,
                    function(listOuvrage) {
                        var output = [];
                        $ouvrage
                                .attr(
                                'data-placeholder',
                                'Filtrer par ouvrage');
                        $
                                .each(
                                listOuvrage,
                                function(
                                        index,
                                        ouvrage) {
                                    var tpl = '<option value="NONE"></option><option value="' + ouvrage.id + '">'
                                            + ouvrage.codeOuvrage
                                            + '</option>';
                                    output
                                            .push(tpl);
                                });
                        $ouvrage.html(output
                                .join(''));
                        if (!listOuvrage.length) {
                            $ouvrage
                                    .attr(
                                    'data-placeholder',
                                    'Aucun ouvrage pour ce Site');
                        }

                        $ouvrage.selected = false;
                        $ouvrage
                                .chosen(
                                {
                                    disable_search_threshold : 10
                                })
                                .trigger(
                                "chosen:updated");
                    });

        });
// Modifie la selectBox enregistreur filtrer par le site
$("#site")
        .change(
        function() {
            $('.codeOuvrage').text('');
            var $enregistreur = $('#enregistreur');
            $
                    .get(
                            $('.relayUrl').attr('href')
                            + '/site/refresh/enregistreur/'
                            + $(this).val(),
                    null,
                    function(listEnregistreur) {
                        var output = [];
                        $enregistreur
                                .attr(
                                'data-placeholder',
                                'Choisir l\'ENREGISTREUR');
                        $
                                .each(
                                listEnregistreur,
                                function(
                                        index,
                                        enregistreur) {
                                    var tpl = '<option value="NONE"></option><option value="' + enregistreur.id + '">'
                                            + enregistreur.mid
                                            + '</option>';
                                    output
                                            .push(tpl);
                                });
                        $enregistreur.html(output
                                .join(''));
                        if (!listEnregistreur.length) {
                            $enregistreur
                                    .attr(
                                    'data-placeholder',
                                    'Aucun enregistreur pour ce filtre');

                        }

                        $enregistreur.selected = false;
                        $enregistreur
                                .chosen(
                                {
                                    disable_search_threshold : 10
                                })
                                .trigger(
                                "chosen:updated");
                        checkAllFieldsHaveValues();
                    });

        });

$("#ouvrage").change(function() {
    $('#site').selected = false, initListSite()
});

// Modifie la selectBox enregistreur filtrer par l'ouvrage
$("#ouvrage")
        .change(
        function(e) {
            var currentOuvrage = e.currentTarget.innerText;
            var $enregistreur = $('#enregistreur');
            var $site = $('#site');

            if (!currentOuvrage) {
                return;

            } else {
                $('.codeOuvrage').text(
                                currentOuvrage + ' > ');
            }
            $
                    .get(
                            $('.relayUrl').attr('href')
                            + '/ouvrage/refresh/enregistreur/'
                            + $(this).val(),
                    null,
                    function(listEnregistreur) {
                        var output = [];
                        $enregistreur
                                .attr(
                                'data-placeholder',
                                'Choisir l\'ENREGISTREUR');
                        $
                                .each(
                                listEnregistreur,
                                function(
                                        index,
                                        enregistreur) {
                                    var tpl = '<option value="NONE"></option><option value="' + enregistreur.id + '">'
                                            + enregistreur.mid
                                            + '</option>';
                                    output
                                            .push(tpl);
                                });
                        $enregistreur.html(output
                                .join(''));
                        if (!listEnregistreur.length) {
                            $enregistreur
                                    .attr(
                                    'data-placeholder',
                                    'Aucun enregistreur pour ce filtre');

                        }

                        $enregistreur.selected = false;
                        $enregistreur
                                .chosen(
                                {
                                    disable_search_threshold : 10
                                })
                                .trigger(
                                "chosen:updated");
                        checkAllFieldsHaveValues()
                    });

        });

$("#dateDebut").change(function() {
    checkAllFieldsHaveValues()
});
$("#dateFin").change(function() {
    checkAllFieldsHaveValues()
});
$("#enregistreur").change(function() {
    $('#site').selected = false;
    $('#ouvrage').selected = false;
    checkAllFieldsHaveValues();
    initListSite();
    initListOuvrage();
});

$("#enregistreur")
        .change(
        function() {

            var $capteur = $('#capteur');

            $
                    .getJSON(
                            $('.relayUrl').attr('href')
                            + '/enregistreur/refresh/capteur/'
                            + $(this).val(),
                    null,
                    function(listCapteur) {
                        var output = []
                        $
                                .each(
                                listCapteur,
                                function(
                                        index,
                                        capteur) {
                                    var tpl = '<option value="NONE"></option><option value="' + capteur.id + '">'
                                            + capteur.typeCaptAlerteMesure.description
                                            + '</option>';
                                    output
                                            .push(tpl);
                                });
                        $capteur
                                .attr(
                                'data-placeholder',
                                'Choisir le capteur');
                        $capteur.html(output
                                .join(''));
                        $capteur
                                .chosen(
                                {
                                    allow_single_deselect : true
                                },
                                {
                                    disable_search_threshold : 10
                                });
                        $capteur
                                .trigger("chosen:updated");
                    });
        });

$("#capteur")
        .change(
        function() {

            var $alerte = $('#alerte');

            $
                    .get(
                            $('.relayUrl').attr('href')
                            + '/capteur/refresh/alerte/'
                            + $(this).val(),
                    null,
                    function(listAlerte) {
                        var output = [];
                        $alerte
                                .attr(
                                'data-placeholder',
                                'Choisir l\'Alerte');
                        $
                                .each(
                                listAlerte,
                                function(
                                        index,
                                        alerte) {
                                    var tpl = '<option value="NONE"></option><option value="' + alerte.id + '">'
                                            + alerte.codeAlerte
                                            + '</option>';
                                    output
                                            .push(tpl);
                                });
                        $alerte.html(output
                                .join(''));
                        if (!listAlerte.length) {
                            $alerte
                                    .attr(
                                    'data-placeholder',
                                    'Aucune alerte active');

                        }
                        $alerte.selected = false;
                        $alerte
                                .chosen(
                                {
                                    disable_search_threshold : 10
                                })
                                .trigger(
                                "chosen:updated");
                    });

        })

$("#capteur")
        .change(
        function() {
            var $alerte = $('#alerte');
            $('#site').selected = false,
                    $('#ouvrage').selected = false,
                    checkAllFieldsHaveValues(),
                    initListSite(),
                    initListOuvrage(),
                    $
                            .get(
                                    $('.relayUrl').attr(
                                            'href')
                                    + '/capteur/refresh/alerte/'
                                    + $(this).val(),
                            null,
                            function(listAlerte) {
                                var output = [];
                                $alerte
                                        .attr(
                                        'data-placeholder',
                                        'Choisir l\'Alerte');
                                $
                                        .each(
                                        listAlerte,
                                        function(
                                                index,
                                                alerte) {
                                            var tpl = '<option value="NONE"></option><option value="' + alerte.id + '">'
                                                    + alerte.codeAlerte
                                                    + '</option>';
                                            output
                                                    .push(tpl);
                                        });
                                $alerte.html(output
                                        .join(''));
                                if (!listAlerte.length) {
                                    $alerte
                                            .attr(
                                            'data-placeholder',
                                            'Aucune alerte active');

                                }
                                $alerte.selected = false;
                                $alerte
                                        .chosen(
                                        {
                                            disable_search_threshold : 10
                                        })
                                        .trigger(
                                        "chosen:updated");
                            });

        })

function initListSite() {

    var $site = $('#site');

    $
            .getJSON(
                    $('.relayUrl').attr('href') + '/init/site',
            null,
            function(listSite) {

                var output = []
                $
                        .each(
                        listSite,
                        function(index, site) {
                            var tpl = '<option value="NONE"></option><option value="' + site.id + '">'
                                    + site.codeSite
                                    + '</option>';
                            output.push(tpl);
                        });
                $site.html(output.join(''));
                $site.chosen({
                    allow_single_deselect : true
                }, {
                    disable_search_threshold : 10
                });
                $site.trigger("chosen:updated");
            });
};

function initListOuvrage() {

    var $ouvrage = $('#ouvrage');

    $
            .getJSON(
                    $('.relayUrl').attr('href') + '/init/ouvrage',
            null,
            function(listOuvrage) {

                var output = []
                $
                        .each(
                        listOuvrage,
                        function(index, ouvrage) {
                            var tpl = '<option value="NONE"></option><option value="' + ouvrage.id + '">'
                                    + ouvrage.codeOuvrage
                                    + '</option>';
                            output.push(tpl);
                        });
                $ouvrage.html(output.join(''));
                $ouvrage.chosen({
                    allow_single_deselect : true
                }, {
                    disable_search_threshold : 10
                });
                $ouvrage.trigger("chosen:updated");
            });
};

function initListEnregistreur() {

    var $enregistreur = $('#enregistreur');

    $
            .getJSON(
                    $('.relayUrl').attr('href')
                    + '/init/enregistreur',
            null,
            function(listEnregistreur) {

                var output = []
                $
                        .each(
                        listEnregistreur,
                        function(index,
                                 enregistreur) {
                            var tpl = '<option value="NONE"></option><option value="' + enregistreur.id + '">'
                                    + enregistreur.mid
                                    + '</option>';
                            output.push(tpl);
                        });
                $enregistreur.html(output.join(''));
                $enregistreur.chosen({
                    allow_single_deselect : true
                }, {
                    disable_search_threshold : 10
                });
                $enregistreur.trigger("chosen:updated");
            });
};
</script>
