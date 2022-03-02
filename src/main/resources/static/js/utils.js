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
        async: false,
        success: (result) => {
            token = result;
        }
    });
    return token;
}
