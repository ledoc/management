var googlemap = function() {

	var map;
	var startContent = '<h6 class="mt10 mb0">';
	var middleContent = '</h6><div class="mt10 mb10"><a href="'
	var endContent = '" class="btn btn-primary btn-sm btn-outline mr5"><i class="fa fa-edit"></i></a></div>';

    var endContentOuvrage = '" class="btn btn-primary btn-sm btn-outline mr5"><i class="fa fa-edit"></i></a>'
        + '<a  href="/solices/mesure/list" class="btn btn-primary btn-sm btn-outline mr5"><i class="fa fa-line-chart"></i></a></div>';
    var endContentOuvrageBefore = '" class="btn btn-primary btn-sm btn-outline mr5"><i class="fa fa-edit"></i></a>'
        + '<a  href="';
    var endContentOuvrageAfter = '" class="btn btn-primary btn-sm btn-outline mr5"><i class="fa fa-line-chart"></i></a></div>';


    var cartoUrl = $('.cartoUrl').attr('href');
	var resourcesUrl = $('.resourcesUrl').attr('href');

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

    var objects =  [ouvrage_object, site_object];

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
                var endContentByType = endContent;
                if (marker.type == ouvrage_object.value) {
                    endContentByType = endContentOuvrageBefore + urlGraphFromOuvrage(marker) + endContentOuvrageAfter;
                }
                return startContent + marker.itemName
                    + middleContent + urlFromMarker(marker) + marker.itemId
                    + endContentByType;
            }

            function icon(marker){
                return {
                    scaledSize:new google.maps.Size(22,32),
                    url : (resourcesUrl + marker.iconPath)
                };
            }

            function urlGraphFromOuvrage(marker) { //
                return "../mesure/list/" + marker.itemId;
            }

            function urlFromMarker(marker) {
                var url = ""
                $.each( objects, function( index, object ){
                    if (marker.type == object.value) {
                        url = object.url;
                    }
                });
                return url;
            }
		}
	};
}();

$(function() {
	"use strict";
	googlemap.init();
});


