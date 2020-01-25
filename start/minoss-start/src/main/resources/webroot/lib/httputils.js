var baseApiPath = "";


function request(api, requestType, params, success) {
    layer.load();
    $.ajax({
        type: requestType,
        url: baseApiPath + api,
        data: params,
        timeout:5000,
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            layer.closeAll('loading');
            if (result.code == 0)
                success(result)
            else if (result.code == 10003)
                window.location.href = "../login/index.html"
            else {
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

