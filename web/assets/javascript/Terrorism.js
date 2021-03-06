//JS file for creation of terrorism crisis

Terrorism = {
};

Terrorism.init = function () {
    alert("Terrorism");
};

Terrorism.plot = function () {
    console.log("terrorism plot")
    var parameter = {
        "action": "list",
        "crisisType": "Terrorism"
    };
    $.ajax({
        type: 'GET',
        url: 'http://155.69.149.181:8080/SSAD/CrisisServlet',
        data: parameter,
        async: true,
        dataType: 'json',
        success: function (results) {
            for (var i = 0 ; i < results.length; i++){
                var crisis = results[i];
                var location = {lat: crisis.latitude, lng: crisis.longitude};
                var icon = crisis.icon + "_" + crisis.status + ".png";
                var marker = plot(map, location, icon, false, null, Terrorism.onClick);
                marker.json = crisis;
                var circle = plotCircle(location, crisis.radius);
                markers.push(marker);
                circlesOverlays.push(circle);
            }
        }
    });
};

//called when the submission form is loaded
Terrorism.submitCrisisInit = function () {
    //set the form onsubmit to submtiCrisis
    document.getElementById("submit").onclick = function () {
        Terrorism.submitCrisis();
    };

    //hide fields
    $("#status").parent().parent().css("display", "none");
    $("#timeReported").parent().parent().css("display", "none");
    $("#timeResolved").parent().parent().css("display", "none");
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
    var mobilenumber = document.getElementById("mobilenumber").value;
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
        "action": action,
        "mobilenumber": mobilenumber
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
};

//called when the update form is submmited
Terrorism.updateCrisis = function () {
    var url = "http://155.69.149.181:8080/SSAD/CrisisServlet";
    var action = "update";
    //general
    var crisisID = document.getElementById("crisisID").value;
    var crisisType = document.getElementById("crisisType").value;
    var description = document.getElementById("description").value;
    var status = document.getElementById("status").value;

    var parameter = {
        "crisisID": crisisID,
        "crisisType": crisisType,
        "status": status,
        "description": description,
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
                clearTimeout(timer);
                refresh();
                alert("Crisis has been updated");

            } else if (data.status == 'error') {
                alert("Error on submission");
            }
        },
        error: function (data) {
            alert("Server returned an error");
        }
    });
};

//called when a terrorism crisis is onclick
Terrorism.onClick = function () {
    var crisis = this.json;

    $.ajax({
        type: 'GET',
        url: "http://155.69.149.181:8080/SSAD/CrisisUpdateController?action=list&crisisID=" + crisis.crisisID,
        async: true,
        beforeSend: function (xhr) {
            if (xhr && xhr.overrideMimeType) {
                xhr.overrideMimeType('application/json;charset=utf-8');
            }
        },
        dataType: 'json',
        success: function (data) {
            var rows = $(".crisisUpdates_container>div>table tr");
            var rowlength = rows.length;
            while (rowlength > 1) {
                $(rows[1]).remove();
                rows = $(".crisisUpdates_container>div>table tr");
                rowlength = rows.length;
            }

            for (i = 0; i < data.length; i++) {
                var updateInfo = data[i];
                $(".crisisUpdates_container>div>table").append("<tr><td>" + updateInfo.update + "</td><td>" + updateInfo.timeUpdated + "</td></tr>");
            }
        },
        error: function (data) {
            alert("Server returned an error");
        }
    });

    $(".crisisDetails_container>div").load(crisis.crisisType + "form.html", function () {
        if (public == false) {
            document.getElementById("submit").onclick = function () {
                Terrorism.updateCrisis();
            };
            if (crisis.status == "Resolved") {
                $("#status").attr("disabled", true);
                $("#description").attr("disabled", true);
                $("#submit").attr("disabled", true);
            }
            if (crisis.timeresolved == "null") {
                $("#timeResolved").parent().parent().css("display", "none");
            }

            $("#crisisType").val(crisis.crisisType).attr("disabled", true);
            $("#crisisID").val(crisis.crisisID);
            $("#description").val(crisis.description);
            $("#address").val(crisis.address).attr("disabled", true);
            $("#mobilenumber").val(crisis.mobilenumber).attr("disabled", true);
            $("#latitude").val(crisis.latitude).attr("disabled", true);
            $("#longitude").val(crisis.longitude).attr("disabled", true);
            $("#timeReported").val(crisis.timereported).attr("disabled", true);
            $("#timeResolved").val(crisis.timeresolved).attr("disabled", true);
            $("#typeOfAttack").val(crisis.typeOfAttack).attr("disabled", true);
            $("#radius").val(crisis.radius).attr("disabled", true);
            $("#status").val(crisis.status);
        } else {
            $("#submit").css("display", "none");
            if (crisis.timeresolved == "null") {
                $("#timeResolved").parent().parent().css("display", "none");
            }
            $("#crisisType").val(crisis.crisisType).attr("disabled", true);
            $("#crisisID").val(crisis.crisisID).attr("disabled", true);
            $("#description").val(crisis.description).attr("disabled", true);
            $("#address").val(crisis.address).attr("disabled", true);
            $("#mobilenumber").val(crisis.mobilenumber).attr("disabled", true);
            $("#latitude").val(crisis.latitude).attr("disabled", true);
            $("#longitude").val(crisis.longitude).attr("disabled", true);
            $("#timeReported").val(crisis.timereported).attr("disabled", true);
            $("#timeResolved").val(crisis.timeresolved).attr("disabled", true);
            $("#typeOfAttack").val(crisis.typeOfAttack).attr("disabled", true);
            $("#radius").val(crisis.radius).attr("disabled", true);
            $("#status").val(crisis.status).attr("disabled", true);
        }

        //$("#selectedCrisis").val(crisis.crisisID);  //hidden input for selected crisis
    });
};
