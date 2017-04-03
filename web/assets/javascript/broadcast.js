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

function updateMessage(crisisType){
  switch (crisisType) {
    case "Terrorism":
      terrorismForm();
      break;
    case "MRTbreakdown":
      mrtForm();
      break;
    default:
    return "updateMessage Failed"
  }

  $.ajax({
    type: "GET",
    url: 'http://155.69.149.181:8080/SSAD/BroadcastController?agent=' + "sms" + '&message=' + message,
    success: function (result) {
      alert("http://155.69.149.181:8080/SSAD/BroadcastController?agent=" + "sms" + "message=" + message);
    }
  });

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
  "description: " + description "\n" +
  "address: " + address + "\n"
  "latitude: " + latitude + "\n"
  "longitude: " + longitude + "\n"
  "timeReported: " + timeReported + "\n"
  "typeOfAttack: " + typeOfAttack + "\n"
  "radius: " + radius + "\n"
  "status: " +  status + "\n";
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
  "description: " + description "\n" +
  "fromStation: " + fromStation + "\n"
  "fromStationLat: " + fromStationLat + "\n"
  "fromStationLng: " + fromStationLng + "\n"
  "toStation: " + toStation + "\n"
  "toStationLat: " + toStationLat + "\n"
  "toStationLng: " + toStationLng + "\n"
  "timeReported: " + timeReported + "\n"
  "typeOfAttack: " + typeOfAttack + "\n"
  "radius: " + radius + "\n"
  "status: " +  status + "\n";


}
