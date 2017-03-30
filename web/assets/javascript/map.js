	var Icon_path="asset/img/nearbyImg/";
	var cluster_icon_path='asset/img/clusterImg/m';
	var types = ["gym","atm","school","shopping_mall","library"];
	var all_types=["gym","atm","school","shopping_mall","library"];
	var API_key = "AIzaSyD_D5gb2uHoSAOnR_i9sWrQxjMUzKKK-Io";
	var map;
	var radius = 500;

	var prevInfoWindow;
	var HDBMarkers = [];
	var HDBLocation = [];
	var NearbyMarkers = [];
	var NearbyClusterMarker = [];
	var circlesOverlays = [];
	var noOfAjax = 0;
	
	var markers=[];
		//dun nid to change
		function findnearby(map,latlng,type,radius,callback){
			var request = {
				location: latlng,
				radius: radius,
				type: type
			};
			service = new google.maps.places.PlacesService(map);
			service.nearbySearch(request, callback);
		}
		function plotCircle(center,radius){
			var Circle = new google.maps.Circle({
				strokeColor: '#070778',
				strokeOpacity: 0.3,
				strokeWeight: 2,
				fillColor: '#9191F9',
				fillOpacity: 0.35,
				map: map,
				center: center,
				radius: radius
			});
			return Circle;
		}
		//remove clusters from the map and delete clusters
		//remove the markers from the map (for replotting purposes)
		function removeplots(markers){
			for(i=0;i<markers.length;i++){
				markers[i].setMap(null);
			}
		}
		function removeplotsPerma(markers){
			/*while(NearbyClusterMarker.length>0){
				NearbyClusterMarker[NearbyClusterMarker.length-1].clearMarkers();
				NearbyClusterMarker.pop();
			}
			while(circlesOverlays.length>0){
				circlesOverlays[circlesOverlays.length-1].setMap(null);
				circlesOverlays.pop();
			}*/
			while(markers.length>0){
				markers[markers.length-1].setMap(null);
				markers.pop();
			}
		}
		//how to plot
		function plot(map,location,icon,draggable,content,onclickCallback){
			var marker;
			if(icon==null){
				marker = new google.maps.Marker({
					position: location,
					map: map,
					draggable: draggable,
					animation: google.maps.Animation.DROP,
				});
			}else{
				marker = new google.maps.Marker({
					position: location,
					map: map,
					icon: icon,
					draggable: draggable,
					animation: google.maps.Animation.DROP,
				});
			}
			if(content){
				var infowindow = new google.maps.InfoWindow({
                    content: content,
				});
				marker.infowindow = infowindow;
				google.maps.event.addListener(marker, 'click', function() {
					if(prevInfoWindow!=null){
						prevInfoWindow.close();
					}
					prevInfoWindow = infowindow
					infowindow.open(map,marker);
				});
			}
			if(onclickCallback){
				google.maps.event.addListener(marker, 'click',onclickCallback);
			}
			return marker;
		}
		
		function createMarker(location,icon,content){
			var marker;
			if(icon==null){
				marker = new google.maps.Marker({
					position: location,
					map: null
				});
			}else{
				marker = new google.maps.Marker({
					position: location,
					map: null,
					icon: icon
				});
			}
			var infowindow = new google.maps.InfoWindow({
                    content: content,
            });
			marker.infowindow = infowindow;
			google.maps.event.addListener(marker, 'click', function() {
				if(prevInfoWindow!=null){
					prevInfoWindow.close();
				}
				prevInfoWindow = infowindow
                infowindow.open(map,marker);
            });
			return marker;
		}
		//how to convert address to latlng
		//callback is the function to handle when the latlng is received
		function convertToLatLng(address,callback){
			var geocoder = new google.maps.Geocoder();
			geocoder.geocode( { 'address': address }, callback);
		}
		function convertToAddress(latlng,callback){
			var geocoder = new google.maps.Geocoder();
			geocoder.geocode( { 'location': latlng }, callback);
		}
		/*
			
			//A LatLngBounds should be SW corner first, NE corner second:
			var strictBounds = new google.maps.LatLngBounds(
                    new google.maps.LatLng(60.88770, -0.83496), 
                    new google.maps.LatLng(49.90878, -7.69042)
            );

            // Listen for the dragend event
            google.maps.event.addListener(mymap, 'bounds_changed', function() {
                if (strictBounds.contains(mymap.getCenter())) return;

                // We're out of bounds - Move the map back within the bounds
                var c = mymap.getCenter(),
                x = c.lng(),
                y = c.lat(),
                maxX = strictBounds.getNorthEast().lng(),
                maxY = strictBounds.getNorthEast().lat(),
                minX = strictBounds.getSouthWest().lng(),
                minY = strictBounds.getSouthWest().lat();

                if (x < minX) x = minX;
                if (x > maxX) x = maxX;
                if (y < minY) y = minY;
                if (y > maxY) y = maxY;

                mymap.setCenter(new google.maps.LatLng(y, x));
            });
		*/
function calculateAndDisplayRoute(directionsService,directionsDisplay,from,to){
    directionsService.route({
        origin: from+" MRT",
        destination: to+" MRT",
        travelMode: 'TRANSIT'
    }, function (response, status) {
        if (status === 'OK') {
            var flightPath = new google.maps.Polyline({
                path: response.routes[0].overview_path,
                geodesic: true,
                strokeColor: '#000000',
                strokeOpacity: 1.0,
                strokeWeight: 2
            });
            flightPath.setMap(map);
        } else {
            window.alert('Directions request failed due to ' + status);
        }
    });
}