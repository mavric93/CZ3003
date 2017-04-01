function call() {
    var selectedIndex = document.getElementById("selectedAgencyContacts").selectedIndex;
    
    var update = document.getElementById("selectedAgencyContacts")[selectedIndex].value;
    var crisisID = document.getElementById("selectedCrisis").value;

    
     $.ajax({
        type: "GET",
        url: 'http://155.69.149.181:8080/SSAD/CrisisUpdateController?action=add&update=' + update + '&crisisID=' + crisisID,
        success: function (result) {
            alert("http://155.69.149.181:8080/SSAD/CrisisUpdateController?update=" + update + "crisisID=" + crisisID);
        }
    });
}