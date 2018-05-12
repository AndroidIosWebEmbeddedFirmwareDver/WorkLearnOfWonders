"use strict";
var template = [];
template.push('<div style="position:relative;width: {{upw}}px;height: {{uph}}px">');
template.push('<input id="up_load" style="position:absolute;opacity:0;width: {{upw}}px;height: {{uph}}px" type="file" accept="image/*"/>');
template.push('<img id="up_image" style="border-radius: 5px;border: 1px solid #CFDADD;width: {{upw}}px;height: {{uph}}px" ng-src="{{url}}" src="img/up2.png">');
template.push('<span id="up_close" style="top: 0px;display:none;border-radius: 4px;position: absolute;right: 0px;font-size: 35px;line-height: 35px;width: 35px;text-align: center;background: rgba(0,0,0,.3);color: #fff;">×</span>');
template.push('</div>');
angular.module("app").directive("fileread", [
    'QiNiu',
    function(QiNiu) {
        return {
            restrict: 'A',
            scope: {
                upw: '=',
                uph: '=',
                upSize: '=',
                getUrl: '&',
                onClose: '&',
                url: '=',
                flg: '='
            },
            template: template.join(''),
            link: function(scope, element) {
                element.bind("change", function(changeEvent) {
                    var _filesize = changeEvent.target.files[0].size / 1024;

                    if (scope.upSize) {
                        if (_filesize > scope.upSize) {
                            layer.msg('图片大小必须小于' + scope.upSize + 'KB,请重新选择');
                            clearInputFile(element[0].childNodes[0].childNodes[0]);
                            return false;
                        }
                    } else {
                        if (_filesize > 120) {
                            layer.msg('图片大小必须小于120KB,请重新选择');
                            clearInputFile(element[0].childNodes[0].childNodes[0]);
                            return false;
                        }
                    }

                    QiNiu.upload(changeEvent.target.files[0], function(url) {
                        scope.getUrl({url: url, flg: scope.flg});
                        element.find('#up_load').hide(); //hide input
                        element.find('#up_close').hide();
                    });
                    clearInputFile(element[0].childNodes[0].childNodes[0]);
                });

                element.bind('mouseover', function() {
                    if (!isNewImg()) {
                        element.find('#up_close').show();
                    }
                })

                element.bind('mouseleave', function() {
                    if (!isNewImg()) {
                        element.find('#up_close').hide();
                    }
                });
                function clearInputFile(f) {
                    if (f.value) {
                        try {
                            f.value = ''; //for IE11, latest Chrome/Firefox/Opera...
                        } catch (err) {}
                        if (f.value) { //for IE5 ~ IE10
                            var form = document.createElement('form'),
                                ref = f.nextSibling,
                                p = f.parentNode;
                            form.appendChild(f);
                            form.reset();
                            p.insertBefore(f, ref);
                        }
                    }
                };

                function isNewImg() {
                    var _img = element.find('#up_image');
                    return _img.attr('src') === 'img/up2.png'
                }

                element.find('#up_close').bind('click', function() {
                    if (scope.onClose) {
                        scope.onClose({flg: scope.flg});
                    }
                    var _img = element.find('#up_image');
                    _img.attr('src', 'img/up2.png');
                    element.find('#up_load').show();
                });

                element.find('#up_image').bind('click', function() {
                    if (!isNewImg()) {
                        var url = this.src;
                        var img = document.createElement('img');
                        img.style.display = 'none';
                        img.src = url;
                        document.body.appendChild(img);
                        img.onload = function() {
                            var html = '<div style="height:100%;weigth:100%;text-align:center"><img src="' + url + '" /><div>'
                            layer.open({
                                style: 'padding:0',
                                type: 1,
                                title: false,
                                closeBtn: 0,
                                shadeClose: true,
                                skin: 'img-box',
                                area: [
                                    this.width, this.height
                                ],
                                content: html
                            });
                            document.body.removeChild(img);
                        }
                    }
                })

                setTimeout(function() {
                    if (!isNewImg()) {
                        element.find('#up_load').hide();
                    }
                }, 500);
            }
        };
    }
]);

//----------------upload-------------------//
angular.module("app").directive("fileUpload", [
    '$timeout',
    function($timeout) {
        return {
            restrict: 'A',
            scope: {
                content: '=',
                url: '=',
                name: '=',
                uid:'=',
                gopage: '='
            },
            template: '<i class="fa fa-file-o"></i>{{content}}<input style="position:absolute;opacity:0;margin-top: -24px;margin-left: -11px;width: 100px;height: 30px;" type="file" accept="file/*"/>',
            link: function(scope, element) {
                $timeout(function() {
                    var w = element.context.offsetWidth;
                    var h = element.context.offsetHeight;
                    var $input = $(element.context).find('input')[0];
                    $input.style.width = w + 'px';
                    $input.style.height = h + 'px';
                })

                element.bind("change", function(changeEvent) {
                    layer.confirm('确定导入'+changeEvent.target.files[0].name+'吗？', {
                        btn: ['确定','取消']
                    }, function(){
                        var data = new FormData();
                        var patt1 = /.(xls|xlsx)$/;
                        if(!patt1.test(changeEvent.target.files[0].name)){
                            return layer.msg("仅支持Excel的*.xls 格式的文件");
                        }
                        data.append(scope.name, changeEvent.target.files[0]);
                        data.append('uid', scope.uid);
                        $(element.context).find('input')[0].value = '';
                        var index = layer.load(1, {
                            shade: [0.1, '#fff']
                        });
                        $.ajax({
                            data: data,
                            type: "POST",
                            url: scope.url,
                            cache: false,
                            contentType: false,
                            processData: false,
                            async: false,
                            success: function(data) {
                                var _layer = layer.alert(data.msg,function(){
                                    layer.close(_layer);
                                    layer.close(index);
                                    scope.gopage(1);
                                })
                            },
                            error: function() {
                                layer.msg('上传失败')
                                layer.close(index)
                            }
                        });
                    },function () {
                        $(element.context).find('input')[0].remove();
                        var input = '<input style="position:absolute;opacity:0;margin-top: -24px;margin-left: -11px;width: 100px;height: 30px;" type="file" accept="file/*"/>';
                        $(element).append(input);
                    });
                });
            }
        };
    }
]);
