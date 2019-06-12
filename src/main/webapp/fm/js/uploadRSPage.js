//上传文件
function renderUploadRSPage() {

    if (topOfArchFolder == 0) {
        alert('对不起系统根目录下禁止新建文件夹与上传文件');
    }
    else {
        var pid = archiveFolderInfo[topOfArchFolder - 1].id;
        $('#youyemian').empty();
        var text =
        "<fieldset><legend>上传文件</legend></fieldset>"
            + "<form class='form-horizontal' role='form' id='form2' enctype='multipart/form-data'>"
            + "<div class='form-group'>"
            + "<label for='folder' class='col-sm-2 control-label'>上传到文件夹:</label>"
            + "<div class='col-sm-10'>"
            + "<input type='text' class='form-control' name='folder' value="+archiveFolderInfo[topOfArchFolder - 1].folderName+" disabled='true'/></div></div>"
            + "<div class='form-group'>"
            + "<label for='fileUpload' class='col-sm-2 control-label'>文件:</label>"
            + "<div class='col-sm-10'>"
            + "<input type='hidden' name='pid' value=" + pid + "/>"
            + "<input type='file' class='btn btn-default'  name='fileUpload' /></div></div>"
            
            + "<div class='form-group'>"
            + "<div class='col-sm-offset-2 col-sm-10'>"
            + "<input type='button' class='btn btn-default' onclick='transmission()' value='上传' /></div></div></form>"
        $("#youyemian").append(text);
    }
}

//上传文件
function transmission() {
    var options = {
        type: 'post',
        url: "/upload",
        dataType: 'json',
        success: function (ret) {
            var flag = ret.tip;
            if (flag == "a") { alert('文件型号不匹配,文件上传失败'); }
            if (flag == "b") {
                alert('上传成功');
                hui();
            }
            if (flag == "c") { alert('大小超过限制,文件上传失败'); }
        },
        error: function (ret) {
            alert('cuowu')
        }
    }
    $("#form2").ajaxSubmit(options);
}