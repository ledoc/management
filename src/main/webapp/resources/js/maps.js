var googlemap = function() {

	var map;
	var startContent = '<h6 class="mt10 mb0">';
	var middleContent = '</h6><div class="mt10 mb10"><a href="'
	var endContentOuvrage = '" class="btn btn-primary btn-sm btn-outline mr5"><i class="fa fa-edit"></i></a>'
			+ '<a class="btn btn-primary btn-sm btn-outline mr5"><i class="fa fa-line-chart"></i></a></div>';
	var endContent = '" class="btn btn-primary btn-sm btn-outline mr5"><i class="fa fa-edit"></i></a></div>';

	var cartoUrl = $('.cartoUrl').attr('href');
	var resourcesUrl = $('.resourcesUrl').attr('href');

    var etablissement_object = {
        value:'etablissement',
        select_chosen : $('#etablissement'),
        url: $('.etablissementUrl').attr('href')
    };

    var site_object = {
        value:'site',
        select_chosen : $('#site'),
        url: $('.siteUrl').attr('href')
    };

    var ouvrage_object = {
        value:'ouvrage',
        select_chosen : $('#ouvrage'),
        url: $('.ouvrageUrl').attr('href')
    };

    var objects =  [ouvrage_object, site_object, etablissement_object];

    return {
		init : function() {

			if (!$('#map').length) {
				return;
			}

			map = new GMaps({
				div : "#map",
				lat : 46.739861,
				lng : 2.329102,
				mapTypeId : google.maps.MapTypeId.HYBRID,
				zoom : 7
			});

			$.getJSON(cartoUrl + '/allItems', null, function(listMarkers) {
                map.removeMarkers();
				$.each(listMarkers, function(index, marker) {
                    map.addMarker(updateMarker(marker));
				});
			});

            $.getJSON(cartoUrl + '/init', null, function(marker) {
                if(isValid(marker)){
                    setCenter(map, marker);

                    $.each( objects, function( index, object ){
                        if (marker.type == object.value) {
                            object.select_chosen.val(marker.itemId);
                            object.select_chosen.trigger("chosen:updated");
                        }
                    });
                }
            });

            $.each( objects, function( index, object ){
                object.select_chosen.chosen().change(
                    function() {
                        $.getJSON(cartoUrl + '/' + object.value + '/' + $(this).val(), null,
                            function(marker) {
                                setCenter(map, marker);
                            });
                    });
            });

            function isValid(marker) {
                return marker.lat != 0.0 && marker.lng != 0.0;
            }

            function setCenter(map, marker) {
                map.setCenter(marker.lat, marker.lng);
                map.setZoom(12);
            }

            function updateMarker(marker) {
                marker.infoWindow.content = infoContent(marker);
                marker.icon = icon(marker);
                return marker;
            }

            function infoContent(marker) {
                return startContent + marker.itemName
                    + middleContent + urlFromMarker(marker) + marker.itemId
                    + endContent;
            }

            function icon(marker){
                return {
                    scaledSize:new google.maps.Size(22,32),
                    url : (resourcesUrl + marker.iconPath)
                };
            }

            function urlFromMarker(marker) {
                $.each( objects, function( index, object ){
                    if (marker.type == object.value) {
                        return object.url;
                    }
                });
                return "";
            }
		}
	};
}();

$(function() {
	"use strict";
	googlemap.init();

});


