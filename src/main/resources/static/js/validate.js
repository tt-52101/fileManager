
function validateLoginName() {  
    var id = "loginName";  
    var value = $("#" + id).val();
   
    var obj = {"loginname":""};
    obj.loginname=value;//获取输入框内容  
    /*  
    * 1. 非空校验  
    */  
    if(!value) {  
        /*  
        * 获取对应的label  
        * 添加错误信息  
        * 显示label  
        */  
        $("#" + id + "Error").text("用户名不能为空！");  
        showError($("#" + id + "Error"));  
        return false;  
    }  
    /*  
    * 2. 长度校验  
    */  
    if(value.length < 3 || value.length > 20) {  
        /*  
        * 获取对应的label  
        * 添加错误信息  
        * 显示label  
        */  
        $("#" + id + "Error").text("用户名长度必须在3 ~ 20之间！");  
        showError($("#" + id + "Error")); 
        return false;  
    }
    
    $.ajax({
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: "/checkloginname" ,//url
        data: JSON.stringify(obj),
        contentType:"application/json",
        success: function (result) {        	
        	if(result.tip!="")
        		{
        		   $("#" + id + "Error").text(result.tip);
                   showError($("#" + id + "Error"));
                   return false; 
        		}         
        },
        error : function() {
            alert("异常！");
        }
    });  
    return true;  
}  

/*  
* 登录密码校验方法  
*/  
function validatePassword() {  
    var id = "password";  
    var value = $("#" + id).val();//获取输入框内容  
    /*  
    * 1. 非空校验  
    */  
    if(!value) {  
        /*  
        * 获取对应的label  
        * 添加错误信息  
        * 显示label  
        */  
        $("#" + id + "Error").text("密码不能为空！");  
        showError($("#" + id + "Error"));  
        return false;  
    }  
    /*  
    * 2. 长度校验  
    */  
    if(value.length < 3 || value.length > 20) {  
        /*  
        * 获取对应的label  
        * 添加错误信息  
        * 显示label  
        */  
        $("#" + id + "Error").text("密码长度必须在3 ~ 20之间！");  
        showError($("#" + id + "Error"));  
        return false;  
    }  
    return true;      
}  

/*  
* 确认密码校验方法  
*/  
function validateRepassword() {  
    var id = "repassword";  
    var value = $("#" + id).val();//获取输入框内容  
    /*  
    * 1. 非空校验  
    */  
    if(!value) {  
        /*  
        * 获取对应的label  
        * 添加错误信息  
        * 显示label  
        */  
        $("#" + id + "Error").text("确认密码不能为空！");  
        showError($("#" + id + "Error"));  
        return false;  
    }  
    /*  
    * 2. 两次输入是否一致校验  
    */  
    if(value != $("#password").val()) {  
        /*  
        * 获取对应的label  
        * 添加错误信息  
        * 显示label  
        */  
        $("#" + id + "Error").text("两次输入不一致！");  
        showError($("#" + id + "Error"));  
        return false;  
    }  
    return true;      
}

function validateMobileNumber(){
    var id = "mobileNumber";  
    var value = $("#" + id).val();//获取输入框内容   
   
    if(!(/^1[34578]\d{9}$/.test(value))){ 
        $("#" + id + "Error").text("电话号码错误");  
        showError($("#" + id + "Error"));  
        return false;  
    }  
    return true;      
} 
/*  
* 判断当前元素是否存在内容，如果存在显示，不页面不显示！  
*/  
function showError(ele) {  
    var text = ele.text();//获取元素的内容  
    if(!text) {//如果没有内容  
        ele.css("display", "none");//隐藏元素  
    } else {//如果有内容  
        ele.css("display", "");//显示元素 
        ele.css("color","red"); 
    }  
}  





