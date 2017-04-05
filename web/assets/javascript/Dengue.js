//JS file for creation of trainbreakdown crisis
Dengue = {
};

var heatmap;

Dengue.init = function () {
    heatmap = new google.maps.visualization.HeatmapLayer({
        data: [new google.maps.LatLng(1.337368, 103.838391)],
        map: map,
        radius: 50
    });

};

Dengue.plot = function () {
    
};

function getPoints() {
    return [
        new google.maps.LatLng(1.337368, 103.838391),
    ];
}
//called when the submission form is loaded
Dengue.submitCrisisInit = function () {

};

//called when a crisis is submited
Dengue.submitCrisis = function () {
    //do translation
};

//called when the update form is loaded
Dengue.updateCrisisInit = function () {

};

//called when the update form is submitted
Dengue.updateCrisis = function () {

};

//called when a terrorism crisis is onclick
Dengue.onClick = function () {

};

// called togehter with on click
// will enable or disable dispatch for each crisistype
function editButton() {

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