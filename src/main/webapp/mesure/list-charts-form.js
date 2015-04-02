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
            "table table-striped list no-m")
        //select box pour le nombre de résultats à afficher
        $('.chosen').chosen({
            width : "80px",
            disable_search_threshold : 10
        });
    });

$('#ongletQualitatif').click(function() {
    switchToList()
})
$('#ongletQuantitatif').click(function() {
    switchToGraph()
})

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