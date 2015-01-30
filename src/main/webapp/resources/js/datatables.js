var demoDataTables = function () {
    return {
        init: function () {
            $('.list').dataTable({
                "sPaginationType": "bootstrap",
                	'aoColumnDefs': [{
                        'bSortable': false,
                        'aTargets': ['nosort']
                    },
                    {
                        'bSearchable': false,
                        'aTargets': ['nosearch']
                    }]
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