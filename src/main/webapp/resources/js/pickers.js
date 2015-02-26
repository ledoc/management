var demoFormPickers = function () {

    var contentStyle = $('.main-content')[0].style;

    function plugins() {

        $('#js-range-selector').daterangepicker({
            timePicker: true,
            timePickerIncrement: 30,
            format: 'DD/MM/YYYY h:mm A',
            locale: {
                startLabel: 'DU',
                endLabel: 'AU',
                applyLabel: 'Choisir',
                cancelLabel: 'Annuler'
            }
        });
    }

    return {
        init: function () {
            plugins();
        }
    };
}();

$(function () {
    "use strict";
    demoFormPickers.init();
});