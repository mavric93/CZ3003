function broadcastMessage(x) {
  message = document.getElementById("message").value;
  $.ajax({
    type: "GET",
    url: 'http://155.69.149.181:8080/SSAD/BroadcastController?agent=' + x.value + '&message=' + message,
    success: function (result) {
      alert("http://155.69.149.181:8080/SSAD/BroadcastController?agent=" + x.value + "&message=" + message);
    }
  });
}

function updateMessage(recipient){
	var element = $("#crisisType");
	if(element.length>0){
		crisisType = element.val();
		var message = "";
		if(crisisType=="Terrorism"){
			message = terrorismForm()
		}else if(crisisType=="TrainBreakDown"){
			message = mrtForm();
		}
		var msgParameter = {
			"agent":"SMSAgent",
			"message":message,
			"recipient":recipient
		};
		//log msg sent
        var CrisisID = document.getElementById("crisisID").value;
        var update = recipient+" has been notified and dispatched";
        if(CrisisID!=""&&update!=""){
			var url = "http://155.69.149.181:8080/SSAD/CrisisUpdateController?";
            var parameter = "action=add&crisisID="+CrisisID+"&update="+update;
            $.ajax({
                type: 'GET',
                url: url+parameter,
                async: true,
                dataType: 'json',
                success: function (result) {
                    if(result.success){
                        //alert("update has been created");
                    }else{
                        alert("ERROR update has not been created");
                    }
                }
            });
        }
		//send sms
		$.ajax({
			type: "POST",
			data:msgParameter,
			url:"http://155.69.149.181:8080/SSAD/BroadcastController",
			success: function (result) {
				alert(result);
			}
		});
	}
}

function terrorismForm(){
	crisisType = $("#crisisType").val();
	//crisisID = $("#crisisID").val();
	description = $("#description").val();
	address = $("#address").val();
	latitude = $("#latitude").val();
	longitude = $("#longitude").val();
	timeReported = $("#timeReported").val();
	//timeResolved = $("#timeResolved").val();
	typeOfAttack = $("#typeOfAttack").val();
	radius = $("#radius").val();
	status = $("#status").val();
	//selectedCrisis = $("#selectedCrisis").val();

	message =
		"Crisis: " + crisisType + "\n" +
		"Desc: " + description +"\n" +
		"Add: " + address + "\n" +
		//"latitude: " + latitude + "\n" +
		//"longitude: " + longitude + "\n" +
		"Time: " + timeReported + "\n" +
		"Attack: " + typeOfAttack + "\n" +
		//"radius: " + radius + "\n" +
		"Status: " +  status + "\n"; 
	return message;
}

function mrtForm(){
	crisisType = $("#crisisType").val();
	//crisisID = $("#crisisID").val();
	description = $("#description").val();
	fromStation = $("#fromStation").val();
	fromStationLat = $("#fromStationLat").val();
	fromStationLng = $("#fromStationLng").val();
	toStation = $("#toStation").val();
	toStationLat = $("#toStationLat").val();
	toStationLng = $("#toStationLng").val();
	timeReported = $("#timeReported").val();
	//timeResolved = $("#timeResolved").val();
	status = $("#status").val();
	selectedCrisis = $("#selectedCrisis").val();
	message =
		"Crisis: " + crisisType + "\n" +
		"Desc: " + description +"\n" +
		"From: " + fromStation + "\n" +
		//"fromStationLat: " + fromStationLat + "\n" +
		//"fromStationLng: " + fromStationLng + "\n" +
		"To: " + toStation + "\n" +
		//"toStationLat: " + toStationLat + "\n" +
		//"toStationLng: " + toStationLng + "\n" +
		"Time: " + timeReported + "\n" +
		"status: " +  status + "\n";
	return message;
}
