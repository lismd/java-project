var ws;
if (typeof WebSocket == 'undefined') {
    alert("WebSocket is not supported in current Browser!")
}

var ws = new WebSocket("ws://localhost:8080/socket/flight/hw-10010");
ws.onmessage = function (evt) {
    //console.log(evt.data);
    document.getElementById("flightData").innerText = evt.data;
}