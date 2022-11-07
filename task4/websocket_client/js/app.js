let stompClient = null;
let currentUserName = "anonymous";
let messageRequest = {
    "id":0,
    "senderUsername":"anonymous",
    "text":"text"
};

function setConnected(connected) {
    $("#connected").prop("hidden", !connected);
    $("#messages-block").prop("hidden", false);
    $("#loading").prop("hidden", true);
    $("#connect").prop("hidden", connected);
    $("#connect").prop("disabled", connected);
    $("#username").prop("disabled", connected);
    $("#disconnect").prop("hidden", !connected);

    if(connected) {
        $("#error").prop("hidden", true);
    }

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

        authority();

        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/messages', function (message) {
            let jsonObject = JSON.parse(message.body);

            if (jsonObject["sender"]["username"] === "SYSTEM" && jsonObject["text"] === "Invalid credentials") {
                $("#error").html("Invalid credentials");
                $("#error").prop("hidden", false);
                disconnect();
            }

            showMessage(jsonObject);
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

function authority() {
    currentUserName = $("#username").val();
    let password = $("#password").val();
    stompClient.send("/app/connect", {}, JSON.stringify(
        {
            'username': currentUserName,
            'password': password
        })
    );
}

function sendMessage() {
    stompClient.send("/app/message", {}, JSON.stringify(
        {
            'usernameOfSender': currentUserName,
            'text': $("#message").val()
        }
    ));
    $("#message").val("");
}

function showMessage(message) {
    $("#messages").append("<tr><td><span class='label'>" + message["createdDt"] + "</span>, <span class='label'>" + message["sender"]["username"] + "</span>: " + message["text"] + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});