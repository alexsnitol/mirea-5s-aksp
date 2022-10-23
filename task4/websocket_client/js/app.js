let stompClient = null;
let currentUserName = "anonymous";

function setConnected(connected) {
    $("#connected").prop("hidden", !connected);
    $("#messages-block").prop("hidden", !connected);
    $("#loading").prop("hidden", true);
    $("#connect").prop("hidden", connected);
    $("#connect").prop("disabled", connected);
    $("#username").prop("disabled", connected);
    $("#disconnect").prop("hidden", !connected);

    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }

    $("#messages").html("");
}

function connect() {
    let socket = new SockJS('http://localhost:8080/websocket');
    stompClient = Stomp.over(socket);

    $("#connect").prop("hidden", true);
    $("#loading").prop("hidden", false);

    stompClient.connect({}, function (frame) {
        setConnected(true);

        sendName();

        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            let jsonObject = JSON.parse(message.body);
            showMessage(jsonObject["sender"], jsonObject["message"]);
        });
    });
}

function disconnect() {
    stompClient.send("/app/disconnect", {}, JSON.stringify(
        {
            'username': currentUserName
        }
    ));

    if (stompClient !== null) {
        stompClient.disconnect();
    }

    currentUserName = "anonymous";
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    currentUserName = $("#username").val();
    stompClient.send("/app/connect", {}, JSON.stringify(
        {
            'username': currentUserName
        }
        ));
}

function sendMessage() {
    stompClient.send("/app/message", {}, JSON.stringify(
        {
            'sender': currentUserName,
            'message': $("#message").val()
        }
    ));
    $("#message").val("");
}

function showMessage(username, message) {
    let today = new Date();
    let time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    $("#messages").append("<tr><td><span class='label'>" + time + "</span>, <span class='label'>" + username + "</span>: " + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});