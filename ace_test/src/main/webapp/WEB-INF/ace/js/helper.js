/**
 * some helpful functions for handle response from server
 *
 * Created by steven on 5/25/15.
 */

/**
 * define Response Error Code
 */
var ResponseErrorCode = {
    ERR_NOT_LOGGED: 1,
    ERR_UNAUTHORIZED: 2,
    ERR_NOT_FOUND: 3,
    ERR_DUPLICATE: 4,
    ERR_UNKNOWN: 99,
    WARNING: 100
};

/**
 * response handler
 *
 * how to change default options:
 *
 * $.handleResp.defaults.notFound = function (res) {
 *    console.log(res.code);
 * }
 *
 * how to use:
 *
 *  $.ajax({
 *       dataType: "json",
 *       type: "POST",
 *       url: url,
 *       success: function (resp) {
 *           helper.handleResp({
 *               response: resp,
 *               notFound: function(res) {
 *                  $.error("XXX is missing!") //handle
 *               },
 *               success: function (res) {
 *                  //handle
 *               }
 *           });
 *       }
 *   });
 *
 * @param options
 */
$.handleResp = function (options) {
    var opts = $.extend({}, $.handleResp.defaults, options);

    if (opts.response.success) {
        opts.success(opts.response);
    } else {
        switch (opts.response.code) {
            case ResponseErrorCode.ERR_NOT_LOGGED:
                opts.notLogged(opts.response);
                break;
            case ResponseErrorCode.ERR_UNAUTHORIZED:
                opts.unauthorized(opts.response);
                break;
            case ResponseErrorCode.ERR_NOT_FOUND:
                opts.notFound(opts.response);
                break;
            case ResponseErrorCode.ERR_DUPLICATE:
                opts.duplicate(opts.response);
                break;
            case ResponseErrorCode.ERR_UNKNOWN:
                opts.unknown(opts.response);
                break;
            case ResponseErrorCode.WARNING:
                opts.warning(opts.response);
                break;
            default:
                opts.other(opts.response);
        }
    }
};

$.handleResp.defaults = {
    response: null,
    notLogged: function () {
        window.location.href = "/login";
    },
    unauthorized: function (resp) {
        if (resp != null && resp.message != null) {
            $.warning("Unauthorized!");
        }
    },
    notFound: function (resp) {
        if (resp != null && resp.message != null) {
            $.error("Not found!");
        }
    },
    duplicate: function (resp) {
        if (resp != null && resp.message != null) {
            $.warning("Duplicate!");
        }
    },
    unknown: function (resp) {
        if (resp != null && resp.message != null) {
            $.error(resp.message);
        }
    },
    warning: function (resp) {
        if (resp != null && resp.message != null) {
            $.warning(resp.message);
        }
    },
    other: function (resp) {
        if (resp != null && resp.message != null) {
            $.error(resp.message);
        }
    },
    success: function (resp) {
        if (resp != null && resp.message != null) {
            $.success(resp.message);
        }
    }
};

/**
 * Display a msg under the page header with several levels
 * how to use:
 * $.success("xxx")
 *
 * @param type
 * @param msg
 */
$.alert = function (type, msg) {
    var typeClass;
    var icon;
    switch (type) {
        case "error":
            typeClass = "alert-danger";
            icon = '<i class="ace-icon fa fa-times"></i>';
            break;
        case "warning":
            typeClass = "alert-warning";
            icon = '<i class="ace-icon fa fa-exclamation-triangle"></i>';
            break;
        case "info":
            typeClass = "alert-info";
            icon = '';
            break;
        default :
            typeClass = "alert-success";
            icon = '<i class="ace-icon fa fa-check"></i>';
    }
    var html = '<div class="alert ' + typeClass + '">\
                        <button type="button" class="close" data-dismiss="alert">\
                        <i class="ace-icon fa fa-times"></i>\
                        </button>\
                        <strong>' + icon + '</strong>\
                    <span>' + msg + '</span>\
                    <br />\
                    </div>';

    debugger;
    $(html).insertAfter($(".page-header"));
};
$.success = function (msg) {
    $.alert("success", msg);
};
$.info = function (msg) {
    $.alert("info", msg);
};
$.warning = function (msg) {
    $.alert("warning", msg);
};
$.error = function (msg) {
    $.alert("error", msg);
};

/**
 * get param from url
 *
 * @param property
 * @returns {*}
 */
$.getParam = function (property) {
    var paramStr = location.href.split("?")[1];
    var params = [];
    if (paramStr != null && paramStr.indexOf("=") >= 0) {
        var combo = paramStr.split("&");
        for (var i = 0; i < combo.length; i++) {
            var cParam = combo[i].split('=');
            params[cParam[0]] = cParam[1];
        }
    }
    return params[property];
};

/**
 * simulate DELETE/PUT ajax request
 * (this is for some browsers which doesn't support DELETE/PUT method)
 *
 * how to use:
 *
 * $.request({
 *       type: "POST",
 *       url: url,
 *       data: data,
 *       success: function (resp) {
 *           //handle
 *       }
 *  });
 *
 * @param options
 */
$.request = function (options) {
    var opts = $.extend({}, $.request.defaults, options);
    var type = opts.type;
    if (type == "DELETE") {
        opts.data.push({name: "_method", value: "DELETE"});
        type = "POST";
    } else if (type == "PUT") {
        opts.data.push({name: "_method", value: "PUT"});
        type = "POST";
    }
    $.ajax({
        type: type,
        dataType: opts.dataType,
        url: opts.url,
        data: opts.data,
        traditional: opts.traditional,
        success: opts.success,
        error: opts.error
    });
};

$.request.defaults = {
    dataType: "json",
    type: "GET",
    url: "",
    data: {},
    traditional: false,
    success: function (resp) {
        $.handleResp({
            response: resp,
            success: function (re) {
                $.success("success");
            }
        });
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert(XMLHttpRequest.status);
        alert(XMLHttpRequest.readyState);
        alert(textStatus);
    }
};