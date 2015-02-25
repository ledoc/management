var googlemap = function() {

	var map;
	var startContent = '<h6 class="mt10 mb0">';
	var middleContent = '</h6><div class="mt10 mb10"><a href="'
	var endContentOuvrage = '" class="btn btn-primary btn-sm btn-outline mr5"><i class="fa fa-edit"></i></a>'
			+ '<a class="btn btn-primary btn-sm btn-outline mr5"><i class="fa fa-line-chart"></i></a></div>';
	var endContent = '" class="btn btn-primary btn-sm btn-outline mr5"><i class="fa fa-edit"></i></a></div>';

	var cartoUrl = $('.cartoUrl').attr('href');
	var resourcesUrl = $('.resourcesUrl').attr('href');

	var ouvrageUrl = $('.ouvrageUrl').attr('href');
	var etablissementUrl = $('.etablissementUrl').attr('href');
	var siteUrl = $('.siteUrl').attr('href');
	var url;
	var content;

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
				$.each(listMarkers, function(index, marker) {
					if (marker.type == 'ouvrage') {
						url = ouvrageUrl;
						
						content = startContent + marker.itemName
								+ middleContent + url + marker.itemId
								+ endContentOuvrage;
						
					}
					if (marker.type == 'etablissement') {
						url = etablissementUrl;
						
						content = startContent + marker.itemName
								+ middleContent + url + marker.itemId
								+ endContent;

					}
					if (marker.type == 'site') {
						url = siteUrl;

						content = startContent + marker.itemName
								+ middleContent + url + marker.itemId
								+ endContent;

					}
					marker.infoWindow.content = content;
					marker.icon = { 
							scaledSize:new google.maps.Size(22,32),
							url : (resourcesUrl + marker.iconPath)
								};
					console.log(marker.icon);
				});
				map.addMarkers(listMarkers);
			});

			$('#etablissement').chosen().change(
					function() {
						var id = $(this).val();
						map.removeMarkers();
						$.getJSON(cartoUrl + '/etablissement/' + id, null,
								function(marker) {
									url = etablissementUrl;
									marker.icon = { 
											scaledSize:new google.maps.Size(22,32),
											url : (resourcesUrl + marker.iconPath)
												};
									
									marker.infoWindow.content = startContent
											+ marker.itemName + middleContent
											+ url + marker.itemId + endContent;

									console.log('marker d\'établissement : '
											+ marker);

									map.addMarker(marker);
									map.setCenter(marker.lat, marker.lng);
									map.zoomIn(1);
								});
					});

			$('#site').chosen().change(
					function() {
						var id = $(this).val();
						map.removeMarkers();

						$.getJSON(cartoUrl + '/site/' + id, null, function(
								marker) {
							url = siteUrl;

							marker.icon = { 
									scaledSize:new google.maps.Size(22,32),
									url : (resourcesUrl + marker.iconPath)
										};
							
							marker.infoWindow.content = startContent
									+ marker.itemName + middleContent + url
									+ marker.itemId + endContent;

							console.log(marker);

							map.addMarker(marker);
							map.setCenter(marker.lat, marker.lng);
							map.zoomIn(1);
						});
					});

			$('#ouvrage').chosen().change(
					function() {
						var id = $(this).val();
						map.removeMarkers();
						$.getJSON(cartoUrl + '/ouvrage/' + id, null, function(
								marker) {

							url = ouvrageUrl;

							marker.icon = { 
									scaledSize:new google.maps.Size(22,32),
									url : (resourcesUrl + marker.iconPath)
										};
							
							marker.infoWindow.content = startContent
									+ marker.itemName + middleContent + url
									+ marker.itemId + endContentOuvrage;

							console.log(marker);

							map.addMarker(marker);
							map.setCenter(marker.lat, marker.lng);
							map.zoomIn(1);
						});
					});
		}
	};
}();

$(function() {
	"use strict";
	googlemap.init();

});
