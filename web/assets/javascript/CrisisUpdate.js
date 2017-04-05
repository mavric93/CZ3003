function initMap() {
    var mapDiv = document.getElementById('map');
    map = new google.maps.Map(mapDiv, {
        center: {lat: 1.359599, lng: 103.812364},
        zoom: 12
    });
}

var INTERVAL = 20000;
var timer;
var markers = [];
var circlesOverlays = [];
var polylines = [];
function refresh() {
    //retrieve new crisis
    replotCrisis(crisisTypes);
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
function replotCrisis(crisistypes) {
    //remove old markers and overlays first
    removePlot(markers, circlesOverlays, polylines);
    //call on each individual crisis type to query server and plot their own markers
    for (var i = 0; i < crisistypes.length; i++) {
        console.log(crisistypes[i]);
        eval(crisistypes[i] + ".plot()");
    }
    //

}
function formatDate(ms) {
    var date = new Date(parseInt(ms));
    return date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear() + " " + date.getHours() + ":" + date.getMinutes();
}