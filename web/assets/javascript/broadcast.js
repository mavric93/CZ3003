function broadcastMessage(x) {
    var message = document.getElementById("message").value;


    $.ajax({
        type: "GET",
        url: 'http://155.69.149.181:8080/SSAD/BroadcastController?agent=' + x.value + '&message=' + message,
        success: function (result) {
            alert("http://155.69.149.181:8080/SSAD/BroadcastController?agent=" + x.value + "message=" + message);
        }
    });
}