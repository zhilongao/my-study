$(document).ready(function() {
        $("#zkAddress").click(function() {
            var zkInfo = $("#zkInfo").val();
            $.ajax({
                type: "POST",
                url: "http://127.0.0.1:8084/config",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({"zkUrl": zkInfo}),
                dataType: "json",
                success: function(data) {
                    console.log(data);
                    if(data) {
                        // 请求接口列表
                        getInterface();
                    } else {
                        alert("zk配置失败");
                    }
                },
                error: function() {
                    console.log();
                }
            });
        });
        // interfaceName选中事件
        $("#interfaceName").change(function() {
            var interfacePath = $('#interfaceName').val();
            getMethods(interfacePath);
        });
        // 点击调用按钮触发
        $("#invokeMethod").click(function() {
            var interfacePath = $('#interfaceName').val();
            var methodName = $('#methodName').val();
            var param = {
                "attachment": attachment,
                "params": {
                    "interface": interfacePath,
                    "method": methodName,
                    "values": params
                }
            };
            $("#rpcParams").val(JSON.stringify(param));
            $.ajax({
                type: "POST",
                url: "http://127.0.0.1:8084/rpc",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(param),
                dataType: "json",
                success: function(data) {
                    console.log(data);
                    $("#rpcResult").val(JSON.stringify(data));
                },
                error: function(e) {
                    console.log(e);
                }
            });
        });

    });

    function getInterface() {
        // 请求接口列表
        $.ajax({
            type: "GET",
            url: "http://127.0.0.1:8084/path",
            dataType: "json",
            success: function(data) {
                console.log(data);
                for (let i = 0; i < data.length; i++) {
                    var interfaceName = data[i].interfaceName;
                    $("#interfaceName").append("<option value=' "+ interfaceName +" '>" + interfaceName + "</option>");
                }
            },
            error: function() {

            }
        });
    };

    function getMethods(catalog) {
        // 请求接口方法
        $.ajax({
            type: "GET",
            url: "http://127.0.0.1:8084/method?catalog=" + catalog,
            dataType: "json",
            success: function(data) {
                console.log(data);
                for (let i = 0; i < data.length; i++) {
                    var methodName = data[i];
                    $("#methodName").append("<option value=' "+ methodName +" '>" + methodName + "</option>");
                }
            }
        });
    };

    // 添加上下文输入框
    function addAttachmentOpt() {
        var el = "<div class='form-group attach-element-item'>" +
                    "<label class='col-sm-1 control-label'>key:</label>" +
                    "<div class='col-sm-2'>" +
                        "<input type='text' class='form-control keyIndex'/>" +
                    "</div>" +
                    "<label class='col-sm-1 control-label'>value:</label>" +
                    "<div class='row'>" +
                        "<div class='col-sm-3'>" +
                            "<input type='text' class='form-control valIndex' placeholder=''>" +
                        "</div>" +
                        "<button class='btn btn-danger' type='button' onclick='deleteSelf(this)'><span>删除</span></button>" +
                    "</div>" +
                "</div>";
        $("#attachment-element-box").append(el);
    };

    // 添加参数输入框
    function addParamOpt() {
        var el = "<div class='form-group param-element-item'>" +
                    "<label class='col-sm-1 control-label'>type:</label>" +
                    "<div class='col-sm-2'>" +
                        "<input type='text' class='form-control keyIndex'>" +
                    "</div>" +
                    "<label class='col-sm-1 control-label'>value:</label>" +
                    "<div class='row'>" +
                        "<div class='col-sm-3'>" +
                            "<input type='text' class='form-control valIndex' placeholder=''>" +
                        "</div>" +
                        "<button class='btn btn-danger' type='button' onclick='deleteSelf(this)'><span>删除</span></button>" +
                    "</div>" +
                "</div>";
        $("#param-element-box").append(el);
    };

    // 上下文参数的保存
    var attachment = {};
    function saveAttachmentOpt() {
        // 构建上下文参数
        //var attachment = {};
        var a = $("#attachment-element-box").children(".attach-element-item");
        $(a).each(function(index, e) {
            var m1 = $(e).find(".keyIndex").val();
            var m2 = $(e).find(".valIndex").val();
            attachment[m1] = m2;
        });
        console.log(attachment);
    };

    // 请求参数得保存
    var params = [];
    function saveParamOpt() {
        // 构建请求参数
        //var params = {};
        var a = $("#param-element-box").children(".param-element-item");
        $(a).each(function(index, e) {
            var m1 = $(e).find(".keyIndex").val();
            var m2 = $(e).find(".valIndex").val();
            var item = {"type": m1, "value": m2};
            //item[type] = m1;
            //item[value] = m2;
            params[index] = item;
        });
        console.log(params);
    };

    // 删除动态添加的元素
    function deleteSelf(el) {
        $(el).closest('.form-group').remove();
    };