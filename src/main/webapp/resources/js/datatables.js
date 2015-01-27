var demoDataTables = function () {
    return {
        init: function () {
            $('.list').dataTable({
                "sPaginationType": "bootstrap",
                //activer le tri sur les colonnes
                "aoColumns": [
                    null,
                    null,
                    {"bSortable": false},
                    {"bSortable": false},
                    {"bSortable": false}
                ]
            });
            //select box pour le nombre de résultats à afficher
            $('.chosen').chosen({
                width: "80px",
                disable_search_threshold: 10
            });
        }
    };
}();

$(function () {
    "use strict";
    demoDataTables.init();
});