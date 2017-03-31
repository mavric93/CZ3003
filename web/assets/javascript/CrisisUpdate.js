function initMap() {
    var mapDiv = document.getElementById('map');
    map = new google.maps.Map(mapDiv, {
        center: {lat: 1.359599, lng: 103.812364},
        zoom: 12
    });
    //autocomplete
    var locationInput = document.getElementById("locationInput");
    var autocomplete = new google.maps.places.Autocomplete(locationInput);
    autocomplete.addListener('place_changed', function () {
        var place = autocomplete.getPlace();
        console.log(place);
        var geometry = place.geometry;
        if (geometry) {
            var icon = Icon_path + "atm.png";
            var content = place.adr_address;
            var marker = plot(map, geometry.location, icon, true, null, null);
            //dragDrop and set address to textbox 
            google.maps.event.addListener(marker, "dragend", function (event) {
                var lat = event.latLng.lat();
                var lng = event.latLng.lng();
                var location = event.latLng;
                convertToAddress(location, function (results, status) {
                    if (status === 'OK') {
                        locationInput.value = results[0].formatted_address;
                    }
                });
            });
            markers.push(marker);
        }
    });
    var directionsService = new google.maps.DirectionsService;
    var directionsDisplay = new google.maps.DirectionsRenderer;
    //timer to retrieve new Crisis
    //setTimeout(retrieveNewCrisis, 3000);
    console.log("retrieve crisis");
    retrieveNewCrisis();
    directionsDisplay.setMap(map);
    calculateAndDisplayRoute(directionsService, directionsDisplay);
}
$(document).ready(function () {
    $(".filterWrapper input[type='checkbox'].type").on("change", function () {
        var types = [];
        $(".filterWrapper input[type='checkbox'].type").each(function () {
            if (this.checked) {
                types.push($(this).attr("name").toUpperCase());
            }
        });
        $(".chronoList .content").html("");
        removeplots(markers);
        var j = markers.length - 1;
        for (var k = 0; k < markers.length; k++) {
            var marker = markers[k];
            var markerType = marker.json.type.toUpperCase();
            if (types.indexOf(markerType) > -1) {
                marker.setMap(map);
                var onclick = function () {
                    alert(marker.json.description);
                };
                addEntry(marker.json, onclick, j);
                j++;
            }
        }
    });
});

function plotMarkers(json) {
    var j = markers.length;
    for (var i = 0; i < json.length; i++) {
        var crisis = json[i];
        console.log(crisis);
        var location = {lat: crisis.latitude, lng: crisis.longitude};
        //var icon = crisis.icon;
        var onclick = function () {
            displayCorrespondingForms(crisis.crisisType, crisis.crisisID);
        };
        addEntry(crisis, onclick, j);
        j++;
        var marker = plot(map, location, null, false, null, onclick);
        marker.json = crisis;

        markers.push(marker);
    }
    //setTimeout(retrieveNewCrisis, 3000);
}

function refresh(){
	//retrieve new crisis
	retrieveNewCrisis();
	//check crisisState
	checkCrisisState();
	setTimeout(refresh,INTERVAL)
}
function checkCrisisState(){
	$.ajax({
        type: 'POST',
        url: 'http://155.69.149.181:8080/SSAD/CrisisStateServlet',
        async: true,
        dataType: 'json',
        success: function (result) {
            console.log(result.crisisState);
			if(result.crisisState){
				//if in crisisState
				$(".CrisisState span").removeClass("peace").addClass("crisis").val("Crisis");
			}else{
				//if not in crisisState
				$(".CrisisState span").removeClass("crisis").addClass("peace").val("Peace");
			}
        }
    });
}
function retrieveNewCrisis() {
    //standard function for retrieve data
    var parameter = {
        "action":"list",
        "crisisType":"ALL"
    }
    $.ajax({
        type: 'POST',
        url: 'http://155.69.149.181:8080/SSAD/CrisisServlet',
        data:parameter,
        async: true,
        dataType: 'json',
        success: function (result) {
            var data = JSON.parse(result.data);
            for(i=0;i<data.length;i++){
                eval(data[i].crisisType+".plot(data[i]);");
            }
        }
    });
}

function addEntry(crisis, onclick, number) {
    var even = "even"
    if (number % 2 == 1) {
        even = "odd";
    }
    var dateString = formatDate(crisis.timeReported);
    var contentArea = $(".chronoList .content");
    var address = $("<div class='address'><p>" + crisis.address + "</p></div>");
    var type = $("<div class='type'><p>" + crisis.type + "</p></div>")
    var timeReported = $("<div class='timeReported'><p>" + dateString + "</p></div>");
    var entry = $("<div class='entry " + even + "'></div>");
    entry.append(address).append(type).append(timeReported);
    contentArea.append(entry);
    entry.on('click', onclick);
}
function formatDate(ms) {
    var date = new Date(parseInt(ms));
    return date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear() + " " + date.getHours() + ":" + date.getMinutes();
}

function addAction() {
    var rows = $("#actionTable tr");
    var htmlcode = "<tr><td></td><td><input name='newAction' type='text'/><input type='button' onclick='removeAction(this)' value='remove'/></td></tr>";
    if (rows.length == 1) {
        var row = $(rows[rows.length - 1]);
        row.before(htmlcode);
    } else {
        var row = $(rows[rows.length - 2]);
        row.before(htmlcode);
    }
}
function removeAction(button) {
    var row = $(button).parent().parent();
    row.remove();
}
function displayCorrespondingForms(crisisType, crisisID) {
    //load form 
    $(".CrisisContent .Crisis .content .form").load(crisisType + "form.html", function () {
        // load json from database
        $.ajax({
            type: 'GET',
            url: 'http://155.69.149.181:8080/SSAD/CrisisServlet?action=read&crisisID=' + crisisID,
            async: true,
            dataType: 'json',
            success: function (result) {
                //change content of form with json
                $(".CrisisContent .Crisis .content .form #crisisName").val("haha");
            }
        });
    });
}