//JS file for creation of trainbreakdown crisis
Dengue = {
};

var heatmap;

Dengue.init = function () {

};

Dengue.plot = function () {
    if(heatmap!=null){
        heatmap.setMap(null);
    }
    
    heatmap = new google.maps.visualization.HeatmapLayer({
        radius: 20
    });
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
            heatmap.setData(denguehotspots);
            heatmap.setMap(map);
        }
    });
};

Dengue.getPoints = function () {

};
//called when the submission form is loaded
Dengue.submitCrisisInit = function () {
    document.getElementById('displayform').innerHTML = "<H3> Data from NEA </h3>";

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
    document.getElementById('details-content').innerHTML = "<H3> Data from NEA </h3>";
};
