//JS file for creation of terrorism crisis

Terrorism = {
};

Terrorism.plot = function () {
    //fetch specialise field based on crisis id

    //display ontop map
};

//called when the submission form is loaded
Terrorism.submitCrisisInit = function () {
    //set the form onsubmit to submtiCrisis
    document.getElementById("submit").onclick = function(){Terrorism.submitCrisis()};;

    //autocomplete
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
            document.getElementById("latitude").value = marker.getPosition().lat();
            document.getElementById("longitude").value = marker.getPosition().lng();

        }
    });
};

//called when a crisis is submited
Terrorism.submitCrisis = function () {
    var url = "http://155.69.149.181:8080/SSAD/CrisisServlet";
	var action = "create";
	//general
    var crisisType = document.getElementById("crisisType").value;
	var description = document.getElementById("description").value;
    var address = document.getElementById("address").value;
    var latitude = document.getElementById("latitude").value;
    var longitude = document.getElementById("longitude").value;
	//specialized
	var typeOfAttack = document.getElementById("typeOfAttack").value;
    var radius = document.getElementById("radius").value;

    var parameter = {
        "crisisType": crisisType,
        "address": address,
        "latitude": latitude,
        "longitude": longitude,
        "description": description,
		"radius": radius,
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
        error: function (data) {
            alert("Server returned an error");
        }
    });



};

//called when the update form is loaded
Terrorism.updateCrisisInit = function () {
    //set the form onsubmit to submtiCrisis
    document.getElementById("submit").onclick = updateCrisis();
};

//called when the update form is submmited
Terrorism.updateCrisis = function () {
};

//called when a terrorism crisis is onclick
Terrorism.onClick = function () {
};
