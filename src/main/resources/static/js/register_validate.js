$(document).ready(function () {
    $.validator.setDefaults({
        submitHandler: function (form) {
            sbmt();
        }
    });

    $('#registForm').validate({
        rules: {
            loginName: {
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

    $.validator.addMethod("checkPassword", function (value, element, params) {
        var checkPassword = /^\w{3,20}$/g;
        return this.optional(element) || (checkPassword.test(value));
    }, "密码长度必须在3~20之间！");

    $.validator.addMethod("checkMobileNumber", function (value, element, params) {
        var checkMobileNumber = /^1[34578]\d{9}$/g;
        return this.optional(element) || (checkMobileNumber.test(value));
    }, "电话号码格式错误！");
});