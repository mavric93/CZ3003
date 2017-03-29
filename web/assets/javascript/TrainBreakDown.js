//JS file for creation of trainbreakdown crisis

TrainBreakDown = {
};

TrainBreakDown.plot = function () {
};

//called when the submission form is loaded
TrainBreakDown.submitCrisisInit = function () {
    //read textfile
    readMRTFromText();

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
    var crisisType = document.getElementById("crisisType").value;
    var address = document.getElementById("fromStation").value;
    var address2 = document.getElementById("toStation").value;
    var fromLatitude = document.getElementById("fromStationLat").value;
    var fromLongitude = document.getElementById("fromStationLng").value;
    var toLatitude = document.getElementById("toStationLat").value;
    var toLongitude = document.getElementById("toStationLng").value;
    var description = document.getElementById("description").value;
    var action = "create";


    var parameter = {
        "crisisType": crisisType,
        "address": address,
        "secondMRTAddress": address2,
        "latitude": fromLatitude,
        "longitude": fromLongitude,
        "secondMRTLat": toLatitude,
        "secondMRTLng": toLongitude,
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
};


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