function initMap() {
    var mapDiv = document.getElementById('map');
    map = new google.maps.Map(mapDiv, {
        center: {lat: 1.359599, lng: 103.812364},
        zoom: 12
    });
}

var INTERVAL = 20000;
var timer;
function refresh() {

    //retrieve new crisis
    retrieveNewCrisis();
    //check crisisState
    checkCrisisState();
    timer = setTimeout(refresh, INTERVAL);
}
function removePlot(oldmarkers, oldcirclesOverlays, oldpolylines) {
    removeplotsPerma(oldmarkers);
    removeplotsPerma(oldcirclesOverlays);
    removeplotsPerma(oldpolylines);
}
function checkCrisisState() {
    $.ajax({
        type: 'GET',
        url: 'http://155.69.149.181:8080/SSAD/CrisisStateServlet',
        async: true,
        dataType: 'json',
        success: function (result) {
            if (result.crisisState) {
                //if in crisisState
                $(".CrisisState span").removeClass("peace").addClass("crisis").html("Crisis");
            } else {
                //if not in crisisState
                $(".CrisisState span").removeClass("crisis").addClass("peace").html("Peace");
            }
        }
    });
}
function retrieveNewCrisis() {
    //standard function for retrieve data
    var parameter = {
        "action": "list",
        "crisisType": "ALL"
    }
    $.ajax({
        type: 'POST',
        url: 'http://155.69.149.181:8080/SSAD/CrisisServlet',
        data: parameter,
        async: true,
        dataType: 'json',
        success: function (result) {
            var oldmarkers = markers;
            var oldcirclesOverlays = circlesOverlays;
            var oldpolylines = polylines;
            markers = [];
            circlesOverlays = [];
            polylines = [];
            var data = JSON.parse(result.data);
            for (i = 0; i < data.length; i++) {
                eval(data[i].crisisType + ".plot(data[i]);");
            }
            removePlot(oldmarkers, oldcirclesOverlays, oldpolylines);
        }
    });
}
function formatDate(ms) {
    var date = new Date(parseInt(ms));
    return date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear() + " " + date.getHours() + ":" + date.getMinutes();
}