// var baseApiPath = "http://127.0.0.1:8080";
var baseApiPath = "";


function request(api, requestType, params, success) {
    layer.load();
    $.ajax({
        type: requestType,
        url: baseApiPath + api,
        data: params,
        timeout: 5000,
        beforeSend: function (xhr) {
            xhr.withCredentials = true
        },
        success: function (result) {
            layer.closeAll('loading');
            if (result.code == 0) {
                success(result)
            } else if (result.code == 10003) {
                // clearAllCookie()
                // window.location.href = "../login/index.html"
            } else {
                layer.msg(result.message)
            }
        },
        error: function (result) {
            var errordesc;
            if (result.responseText != null && result.responseText !== "") {
                var parseJSON = $.parseJSON(result.responseText);
                errordesc = parseJSON.message;
            } else {
                errordesc = "请求出现错误，请稍后重试！"
            }
            layer.closeAll('loading');
            layer.msg(errordesc)
        }
    })
}

function requestJson(api, params, success) {
    layer.load();
    $.ajax({
        type: "POST",
        url: baseApiPath + api,
        data: params,
        timeout: 5000,
        dataType:"json",
        beforeSend: function (xhr) {
            xhr.withCredentials = true
        },
        success: function (result) {
            layer.closeAll('loading');
            if (result.code == 0) {
                success(result)
            } else if (result.code == 10003) {
                // clearAllCookie()
                // window.location.href = "../login/index.html"
            } else {
                layer.msg(result.message)
            }
        },
        error: function (result) {
            var errordesc;
            if (result.responseText != null && result.responseText !== "") {
                var parseJSON = $.parseJSON(result.responseText);
                errordesc = parseJSON.message;
            } else {
                errordesc = "请求出现错误，请稍后重试！"
            }
            layer.closeAll('loading');
            layer.msg(errordesc)
        }
    })
}


//清除所有的cookie
function clearAllCookie() {
    var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
    if (keys) {
        for (var i = keys.length; i--;)
            document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString()
    }
}

//获取cookie
function getCookie(name) {
    var strcookie = document.cookie;//获取cookie字符串
    var arrcookie = strcookie.split("; ");//分割
    //遍历匹配
    for (var i = 0; i < arrcookie.length; i++) {
        var arr = arrcookie[i].split("=");
        if (arr[0] == name) {
            return arr[1];
        }
    }
    return "";
}

