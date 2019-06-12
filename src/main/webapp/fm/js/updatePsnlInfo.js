function renderPsnlInfo() {
    $('#youyemian').empty();
    var text = "<fieldset><legend>更新个人信息</legend></fieldset>"
        + "<form class='form-horizontal' role='form' id='form3'>"
        + "<div class='form-group'>"
        + "<label for='id' class='col-sm-2 control-label'>id:</label>"
        + "<div class='col-sm-10'>"
        + "<input type='text' class='form-control' id='idShow' name='idShow' disabled='true'/></div></div>"
        + "<input type='hidden' class='form-control' name='id' id='id'/>"
        + "<div class='form-group'>"
        + "<label for='loginName' class='col-sm-2 control-label'>登录名：</label>"
        + "<div class='col-sm-10'>"
        + "<input type='text' class='form-control' id='loginName' name='loginName' /></div></div>"
        + "<div class='form-group'>"
        + "<label for='name' class='col-sm-2 control-label'>真实姓名：</label>"
        + "<div class='col-sm-10'>"
        + "<input type='text' class='form-control' id='name' name='name' /></div></div>"
        + "<div class='form-group'>"
        + "<label for='password' class='col-sm-2 control-label'>密码：</label>"
        + "<div class='col-sm-10'>"
        + "<input type='password' class='form-control' id='password' name='password'  placeholder='输入密码'/></div></div>"
        + "<div class='form-group'>"
        + "<label for='repassword' class='col-sm-2 control-label'>确认：</label>"
        + "<div class='col-sm-10'>"
        + "<input type='password' class='form-control' id='repassword' name='repassword' placeholder='重新输入密码'/></div></div>"
        + "<div class='form-group'>"
        + "<label for='mobileNumber' class='col-sm-2 control-label'>电话：</label>"
        + "<div class='col-sm-10'>"
        + "<input type='text' class='form-control' id='mobileNumber' name='mobileNumber' /></div></div>"
        + "<div class='form-group'>"
        + "<div class='col-sm-offset-2 col-sm-10'>"
        + "<input type='button'  class='btn btn-primary' onclick='updatePsnlInfo()' value='更新'></input></div></div></form>"
    $("#youyemian").append(text);
    $('#form3').validate({
        rules: {
            loginName: {
                required: true,
                checkLoginName: true
            },
            name: {
                required: true
            },
            password: {
                required: true,
                checkPassword: true
            },
            repassword: {
                required: true,
                equalTo: '#password'
            },
            mobileNumber: {
                required: true,
                checkMobileNumber: true
            }
        },
        messages: {
            loginName: {
                required: '登陆名不能为空！'
            },
            name: {
                required: '姓名不能为空！'
            },
            password: {
                required: '密码不能为空！'
            },
            repassword: {
                required: '确认密码不能为空！',
                equalTo: '确认密码不一致！'
            },
            mobileNumber: {
                required: '电话号码不能为空！'
            }
        }
    });

    $.validator.addMethod("checkLoginName", function (value, element, params) {
        var flag = true;
        $.ajax({
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "/checkloginname",//url
            data: JSON.stringify(value),
            contentType: "application/json",
            async: false,
            success: function (result) {
                console.log(result)
                if (result.data == 0) {
                    console.log("no")
                    flag = false
                }

            }
        });
        return this.optional(element) || flag;

    }, "用户名已被占用");

    $.validator.addMethod("checkPassword", function (value, element, params) {
        var checkPassword = /^\w{3,20}$/g;
        return this.optional(element) || (checkPassword.test(value));
    }, "密码长度必须在3~20之间！");

    $.validator.addMethod("checkMobileNumber", function (value, element, params) {
        var checkMobileNumber = /^1[34578]\d{9}$/g;
        return this.optional(element) || (checkMobileNumber.test(value));
    }, "电话号码格式错误！");

    $.ajax({
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: "/getpsnlmsg",//url
        data: null,
        contentType: "application/json",
        success: function (data) {
            if (data == null) {
                window.location.href = "/fm/error.html";
            } else {
                $('#id').val(data.id);
                $('#idShow').val(data.id);
                $('#loginName').val(data.loginName);
                $('#name').val(data.name);
                $('#mobileNumber').val(data.mobileNumber);
            }
        },
        error: function () {
            window.location.href = "/fm/error.html";
        }
    });
}

function updatePsnlInfo() {
    console.log(JSON.stringify($("#form3").serializeObject()))
    $.ajax({
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: "/updatePsnlInfo",//url
        data: JSON.stringify($("#form3").serializeObject()),
        contentType: "application/json",
        async: false,
        success: function () {
            hui();
        },
        error: function (result) {
            alert(result.msg);
        }
    });
}