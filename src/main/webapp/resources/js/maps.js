var googlemap = function () {

    var map;

    return {
        init: function () {
        	
        	if(!$('#map').length){
        		return;
        	}
        	
        	
            map = new GMaps({
                div: "#map",
                lat: 48.8652902,
                lng: 2.4444396,
                mapTypeId: google.maps.MapTypeId.HYBRID,
                zoom: 8
            });

            // geocoding
            $('.js-find-location').chosen().change(function () {
                var that = this;
                GMaps.geocode({
                    address: $(that).val(),
                    callback: function (results, status) {
                        if (status == 'OK') {
                            var latlng = results[0].geometry.location;
                            map.setCenter(latlng.lat(), latlng.lng());
                            map.zoomIn(8);
                        }
                    }
                });
            });

            // ajouter un marqueur
            map.addMarker({
                lat: 48.86542,
                lng: 2.44385,
                title: "M1 - Pz1",
                infoWindow: {
                    content: '<h3 class="mt10 mb0">M1 - Pz1</h3>' +
                    '<small>Eau de Surface</small>' +
                    '<div class="mt10 mb10">' +
                    '<a class="btn btn-primary btn-sm btn-outline mr5"><i class="fa fa-edit"></i></a>' +
                    '<a class="btn btn-primary btn-sm btn-outline mr5"><i class="fa fa-line-chart"></i></a>' +
                    '</div>'
                }
            });
            map.addMarker({
                lat: 48.9596696,
                lng: 4.3354447,
                title: "MZ51 - Pz1",
                infoWindow: {
                    content: '<h3 class="mt10 mb0">M1 - Pz2</h3>' +
                    '<small>Eau de Bouteille</small>' +
                    '<div class="mt10 mb10">' +
                    '<a class="btn btn-default btn-sm mr5"><i class="fa fa-edit"></i></a>' +
                    '<a class="btn btn-default btn-sm mr5"><i class="fa fa-line-chart"></i></a>' +
                    '</div>'
                }
            });
        }
    };
}();

$(function () {
    "use strict";
    googlemap.init();
});
