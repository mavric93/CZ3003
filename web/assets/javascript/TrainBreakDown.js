//JS file for creation of trainbreakdown crisis

TrainBreakDown = {
};

TrainBreakDown.plot = function (crisis) {
    console.log(crisis);
    var locationfrom = {lat: crisis.latitude, lng: crisis.longitude};
    var locationto = {lat: crisis.secondMRTLat, lng: crisis.secondMRTLng};
    var fromMarker = plot(map, locationfrom, null, false, null, TrainBreakDown.onClick);
    var toMarker = plot(map, locationto, null, false, null, TrainBreakDown.onClick);
    fromMarker.json = crisis;
    toMarker.json = crisis;
    var directionsService = new google.maps.DirectionsService;
    var directionsDisplay = new google.maps.DirectionsRenderer;
    calculateAndDisplayRoute(directionsService,directionsDisplay,crisis.address,crisis.secondMRTAddress);
};

//called when the submission form is loaded
TrainBreakDown.submitCrisisInit = function () {
    //read textfile
    readMRTFromText();
    //hide fields
    $("#status").parent().parent().css("display","none");
    $("#timeReported").parent().parent().css("display","none");
    $("#timeResolved").parent().parent().css("display","none");
	
    //set submit parameters
    document.getElementById("submit").onclick = function () {
        TrainBreakDown.submitCrisis()
    };

    //set event for onchange for from mrt and to mrt
    document.getElementById("fromStation").onchange = function () {
        translateLatLongFromAddress(document.getElementById("fromStation"))
    };

    document.getElementById("toStation").onchange = function () {
        translateLatLongFromAddress(document.getElementById("toStation"))
    };
};

//called when a crisis is submited
TrainBreakDown.submitCrisis = function () {
    console.log("form submiited");
    var url = "http://155.69.149.181:8080/SSAD/CrisisServlet";
    var action = "create";
    //general
    var crisisType = document.getElementById("crisisType").value;
    var action = "create";
    var description = document.getElementById("description").value;
    var address = document.getElementById("fromStation").value;
    var fromLatitude = document.getElementById("fromStationLat").value;
    var fromLongitude = document.getElementById("fromStationLng").value;
    //specialized
    var address2 = document.getElementById("toStation").value;
    var toLatitude = document.getElementById("toStationLat").value;
    var toLongitude = document.getElementById("toStationLng").value;

    var parameter = {
        "crisisType": crisisType,
        "address": address,
        "latitude": fromLatitude,
        "longitude": fromLongitude,
		"description": description,
		"secondMRTAddress": address2,
        "secondMRTLat": toLatitude,
        "secondMRTLng": toLongitude,
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

    //do translation
};

//called when the update form is loaded
TrainBreakDown.updateCrisisInit = function () {
};

//called when the update form is submitted
TrainBreakDown.updateCrisis = function () {
};

//called when a terrorism crisis is onclick
TrainBreakDown.onClick = function () {
    var crisis = this.json;
    $(".crisisDetails_container>div").load(crisis.crisisType + "form.html", function () {
        readMRTFromText();
        $("#crisisType").val(crisis.crisisType).attr("disabled",true);
        $("#crisisID").val(crisis.crisisID);
        $("#description").val(crisis.description);
        $("#fromStation").val(crisis.address).attr("disabled",true);
        $("#toStation").val(crisis.secondMRTAddress).attr("disabled",true);
        $("#timeReported").val(crisis.timereported).attr("disabled",true);
        $("#timeResolved").attr("disabled",true);
        if(crisis.status=="resolved"){
            $("#status").val(crisis.status).attr("disabled",true);
        }
        if(crisis.timeresolved=="null"){
            $("#timeResolved").parent().parent().css("display","none");
        }
    });
    editButton();
};

// called togehter with on click
// will enable or disable buttons for each crisistype
function editButton(){
    
}

function readMRTFromText() {
    //Load MRT Stations 
    file = "/SSAD/assets/mrtStation.txt"
    var rawFile = new XMLHttpRequest();
    rawFile.open("GET", file, false);
    rawFile.onreadystatechange = function ()
    {
        if (rawFile.readyState === 4) {
            if (rawFile.status === 200 || rawFile.status == 0) {
                var mrtListText = rawFile.responseText;
                var mrtList = mrtListText.split('\n');
                var fromStation = document.getElementById("fromStation");
                var toStation = document.getElementById("toStation");

                for (i in mrtList) {
                    option = document.createElement("option");
                    option.value = mrtList[i];
                    option.innerHTML = mrtList[i];
                    fromStation.append(option);

                    option = document.createElement("option");
                    option.value = mrtList[i];
                    option.innerHTML = mrtList[i];
                    toStation.append(option);


                }
            }
        }
    }
    rawFile.send(null);
}

function translateLatLongFromAddress(input) {
    var address = document.getElementById(input.id).value + "MRT";

    convertToLatLng(address, function (result) {
        document.getElementById(input.id + "Lat").value = result[0].geometry.location.lat();
        document.getElementById(input.id + "Lng").value = result[0].geometry.location.lng();
    });
}