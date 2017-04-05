//JS file for creation of trainbreakdown crisis
Dengue = {
};

var heatmap;

Dengue.init = function () {

};

Dengue.plot = function () {
    heatmap = null;
    $.ajax({
        type: 'GET',
        url: 'http://155.69.149.181:8080/SSAD/assets/dengue_cases.json',
        async: true,
        dataType: 'json',
        success: function (results) {
            var data = results.cases;
            var denguehotspots = [];
            for (var i = 0; i < data.length; i++) {
                var hotspot = data[i];
                var location = new google.maps.LatLng(hotspot.lat, hotspot.long);
                denguehotspots.push(location);
                var marker = plot(map, location, "https://maps.gstatic.com/intl/en_us/mapfiles/markers2/measle_blue.png", false, null, Dengue.onClick);
            }
            heatmap = new google.maps.visualization.HeatmapLayer({
                data: denguehotspots,
                map: map,
                radius: 10
            });
            heatmap.setMap(map);
        }
    });
};

Dengue.getPoints = function () {

};
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
