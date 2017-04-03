//Constant Global Variables
var crisisType;
var crisisID;
var description;
var latitude;
var longitude;
var timeReported;
var timeResolved;
var status;
var selectedCrisis;
var message;
var msgParameter;

//Terrorism Specific Variable
var address;
var typeOfAttack;
var radius;

//MRTbreakdown Specific Variables
var fromStation;
var fromStationLat;
var fromStationLng;
var toStation;
var toStationLat;
var toStationLng;




function broadcastMessage(x) {
  message = document.getElementById("message").value;


  $.ajax({
    type: "GET",
    url: 'http://155.69.149.181:8080/SSAD/BroadcastController?agent=' + x.value + '&message=' + message,
    success: function (result) {
      alert("http://155.69.149.181:8080/SSAD/BroadcastController?agent=" + x.value + "message=" + message);
    }
  });
}

function updateMessage(crisisType,recipient){
  switch (crisisType) {
    case "Terrorism":
      terrorismForm(recipient);
      break;
    case "TrainBreakDown":
      mrtForm(recipient);
      break;
    default:
    return "updateMessage Failed"
  }

  $.ajax({
    type: "POST",
    data:msgParameter,
    //url: 'http://155.69.149.181:8080/SSAD/BroadcastController?agent=' + "sms" + '&message=' + message,
    url:"http://155.69.149.181:8080/SSAD/BroadcastController",
    success: function (result) {
      alert(result);
    }
  });

}

function terrorismForm(recipient){
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
  
msgParameter = {
        "agent":"SMSAgent",
        "message":message,
        "recipient":recipient
    };
  
}

function mrtForm(recipient){

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
  
  msgParameter = {
        "agent":"SMSAgent",
        "message":message,
        "recipient":recipient
    };
}
