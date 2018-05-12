'use strict';
var moment = require('moment');
var requestTools = require('../../common/superagent-tools');
var portUrl = requestTools.portUrl;

module.exports = {

    getArticleDetail: function (req, res) {
        requestTools.get(res, portUrl.articleDetail.key, req.query, function (_data) {
            var isapp = false;
            var area = req.query.area?req.query.area:"3101";
            var isuser = req.query.isuser?false:true;
            if (req.header('app-version')) {
                isapp = true;
            }

            var data = JSON.parse(_data).data;
            if (JSON.parse(_data).code === 0) {
                res.render('article/articleDetail', {
                    "data": {
                        "title": data && data.title || '',
                        "content": data && data.content || '',
                        "onlineTime": data && moment(data.update_time).format("YYYY年MM月DD日 HH:mm") || ''
                    },
                    "isapp": isapp,
                    "area" : area,
                    "isuser": isuser
                })
            } else {
                res.render('error', {
                    "message": JSON.parse(_data).msg
                });
            }
        });
    },
    getDoctorArticleDetail:function(req, res){
        requestTools.post(res, portUrl.doctorArticleAddPv.key, req.query, function (_data) {});
        requestTools.get(res, portUrl.doctorArticleDetail.key, req.query, function (_data) {
            var isapp = false;
            var area = req.query.area?req.query.area:"3101";
            var isuser = req.query.isuser?false:true;
            if (req.header('app-version')) {
                isapp = true;
            }

            var data = JSON.parse(_data).data;
            if (JSON.parse(_data).code === 0) {
                res.render('article/doctorArticleDetail', {
                    "data": {
                        "title": data && data.title || '',
                        "content": data && data.content || '',
                        "onlineTime": data && moment(data.update_time).format("YYYY年MM月DD日 HH:mm") || ''
                    },
                    "isapp": isapp,
                    "area" : area,
                    "isuser": isuser
                })
            } else {
                res.render('error', {
                    "message": JSON.parse(_data).msg
                });
            }
        });
    }
};
