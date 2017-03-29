//JS file for creation of terrorism crisis
function CrisisInit() {
    //map stuff

    var locationInput = document.getElementById("address");
    var marker;
    var autocomplete = new google.maps.places.Autocomplete(locationInput);
    autocomplete.setComponentRestrictions({country: ['sg']});
    autocomplete.addListener('place_changed', function () {
        var place = autocomplete.getPlace();
        var geometry = place.geometry;
        var lat, lng;
        if (geometry) {
            if (marker) {
                marker.setMap(null);
            }
            marker = plot(map, geometry.location, null, true, null, null);
            //dragDrop and set address to textbox 
            
            google.maps.event.addListener(marker, "dragend", function (event) {
                lat = event.latLng.lat();
                lng = event.latLng.lng();
                var location = event.latLng;
                convertToAddress(location, function (results, status) {
                    if (status === 'OK') {
                        locationInput.value = results[0].formatted_address;
                    }
                });
            });
            markers.push(marker);
            //display onto the form too
            console.log("a");
            document.getElementById("latitude").value = marker.getPosition().lat();
            document.getElementById("longitude").value = marker.getPosition().lng();
        }
    });

    //document.getElementById("submit").onclick = submitCrisis();
}

function submitCrisis() {
    var url = "http://155.69.149.181:8080/SSAD/CrisisServlet"
    var crisisName = document.getElementById("crisisName").value;
    var address = document.getElementById("address").value;
    var latitude = document.getElementById("latitude").value;
    var longitude = document.getElementById("longitude").value;
    var typeOfAttack = document.getElementById("typeOfAttack").value;
    var radius = document.getElementById("radius").value;
    var description = document.getElementById("description").value;
    var crisistype = document.getElementById("crisistype").value;
    var action = "create";

    var parameter = {
        "radius": radius,
        "crisistype": crisistype,
        "address": address,
        "latitude": latitude,
        "longitude": longitude,
        "description": description,
        "typeOfAttack": typeOfAttack,
        "action": action
    };
    $.ajax({
        type: 'POST',
        url: url,
        data: parameter,
        async: true,
        beforeSend: function (xhr) {
            if (xhr && xhr.overrideMimeType) {
                xhr.overrideMimeType('application/json;charset=utf-8');
            }
        },
        dataType: 'json',
        success: function (data) {
            if (data.status == 'success') {
                alert("Crisis has been submitted");
            } else if (data.status == 'error') {
                alert("Error on submission");
            }
        },
        error: function(data){
            alert("Server returned an error");
        }
    });

}