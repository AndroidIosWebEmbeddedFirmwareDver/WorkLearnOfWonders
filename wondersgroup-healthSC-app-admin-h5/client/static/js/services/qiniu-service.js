'use strict';

/**
 * 七牛上传图片
 */
app.service('QiNiu', ['$http','$rootScope', function ($http,$rootScope) {
    /**
     * 拼装 formData
     * @param file
     * @param rdata
     * @returns {*}
     */
    function getData(file, rdata) {
        var timestamp = new Date().getTime();
        var data = new FormData();
        var _arr = file.name.split('.');
        var suffix = _arr[_arr.length-1];
        data.append("file", file);
        data.append("key", timestamp+'.'+suffix);
        data.append("token", rdata.token);
        return data;
    }

    /**
     * 调用七牛服务接口
     * @param fileData
     * @param domain
     * @param call
     */
    function qiniuUp(fileData, domain, call) {
        $.ajax({
            data: fileData,
            type: "POST",
            url: "http://upload.qiniu.com",
            cache: false,
            contentType: false,
            processData: false,
            async: false,
            success: function (data) {
                call(domain + data.key);    //回调
            },
            error: function () {
                alert('上传失败')
            }
        });
    }

    /**
     * @param file  上传的文件
     * @param call  回调函数返回成功的url
     */
    this.upload = function (file, call) {
        $http.get($rootScope.baseURL+'/admin/common/getQiniuToken').success(function (data) {     //调用本地服务获取七牛uptoken
            var domain = data.data.domain;                      //七牛文件存储地址
            qiniuUp(getData(file, data.data), domain, call);
        });
    }
}]);
