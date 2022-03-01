let getRequestParam = function(name) {
    let s = window.location.search.substring(1);
    let values = s.split("&");
    for(let i = 0;i < values.length;++i) {
        let kv = values[i].split("=");
        if(kv[0] === name) {
            return kv[1];
        }
    }
    return null;
}

let getToken = function() {
    let token;
    $.ajax({
        url: "getToken",
        sync: false,
        success: (result) => {
            token = result;
            console.log(token);
        }
    });
    return token;
}

let getRoomList = function () {
    let result = [];
    $.ajax({
        url: "/getRoomList",
        type: "GET",
        sync: false,
        success: (resultJSON) => {
            result = $.parseJSON(resultJSON);
            console.log(result);
            for (let i = 0; i < 10; ++i) {
                let textRoom = document.getElementById("textRoom" + (i + 1));
                textRoom.innerHTML = "room" + (i + 1) + " " + result[i].length + "/6";
            }
        }
    });
    return result;
}

let send = function(message) {
    if(!window.WebSocket) {
        return ;
    }
    if(socket.readyState == WebSocket.OPEN) {
        socket.send(message);
    } else {
        alert("connection havent opened");
    }
}
