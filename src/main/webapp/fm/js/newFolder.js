//新建文件夹将内容改变显示出来
function renderNewFolderForm() {
    if (topOfArchFolder == 0) {
        alert('对不起系统根目录下禁止新建文件夹与上传文件');
    }
    else {
        var pid = archiveFolderInfo[topOfArchFolder - 1].id;
        console.log("上传文件到" + pid)
        $('#youyemian').empty();
        var text = "<fieldset><legend>新建文件夹</legend></fieldset>"
            + "<form class='form-horizontal' role='form' id='form1'>"
                    + "<div class='form-group'>"
            + "<label for='folder' class='col-sm-2 control-label'>上传到文件夹:</label>"
            + "<div class='col-sm-10'>"
            + "<input type='text' class='form-control' name='folder' value="+archiveFolderInfo[topOfArchFolder - 1].folderName+" disabled='true'/></div></div>"
            + "<div class='form-group'>"
            + "<label  class='col-sm-2 control-label'>文件夹名称</label>"
            + "<div class='col-sm-10'>"
            + "<input type='text' class='form-control' onchange='check($(this).val())' name='foldername' placeholder='输入文件夹名'><span style='color:red' id='warningInfo'></span></div></div>"
            + "<input type='hidden' name='pid' value=" + pid + ">"
            + "<div class='form-group'>"
            + "<label  class='col-sm-2 control-label'>描述</label>"
            + "<div class='col-sm-10'>"
            + "<input type='text' class='form-control' name='description' value='无'></div></div>"
            + "<div class='form-group'>"
            + "<div class='col-sm-offset-2 col-sm-10'>"
            + "<input type='button'  class='btn btn-default' onclick='newFolder()' value='创建'></input></div></div></form>"
        $("#youyemian").append(text);
    }
}

//新建文件夹然后刷新返回
function newFolder() {
    console.log(JSON.stringify($("#form1").serializeObject()))
    $.ajax({
        type: "POST",//方法类型
        dataType: "json",//预期服务器返回的数据类型
        url: "/newFolder",//url
        data: JSON.stringify($("#form1").serializeObject()),
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
//检查文件夹名是否重复
function check(val) {
    var pid = archiveFolderInfo[topOfArchFolder - 1].id;
    console.log("检查文件夹" + pid);
    var flag = 0;
    require(pid, '/getAllFolder');
    console.log("检查文件夹" + resultFromRequire);
    for (var i = 0; i < resultFromRequire.length; i++) {
        if (resultFromRequire[i].folderName == val) {
            flag = 1;
            console.log("检查文件夹名是否重复" + resultFromRequire[i].folderName + "+" + i);
        }
    }
    if (flag == 0) {
        $("#warningInfo").css("color", "green")
        $("#warningInfo").html("可以使用该名字")
    }
    else {
        $("#warningInfo").css("color", "red")
        $("#warningInfo").html("文件夹已存在请更改名字")
    }
}
