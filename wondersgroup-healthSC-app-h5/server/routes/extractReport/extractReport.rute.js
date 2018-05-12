var express = require('express')
var router = express.Router()
var request = require('../../common/request')
var api = require('../../common/api-url-map')

//检验报告详情页
router.get('/inspectInfo', function(req, res) {
  request.get(api.inspectInfo,req.query,res)
    .then(function(data){
      if(data.code == 0){
        res.render('extractReport/inspectInfo',{
            data: data.data
        });
      }else{
        console.error('获取检验报告详情失败',data);
        res.render('extractReport/inspectInfo',{
            data: ''
        });
      }
    });
})

//检查报告详情页
router.get('/checkupInfo', function(req, res) {
  request.get(api.checkupInfo,req.query,res)
    .then(function(data){
      if(data.code == 0){
        res.render('extractReport/checkupInfo',{
            data: data.data
        });
      }else{
        console.error('获取检查报告详情失败',data);
        res.render('extractReport/checkupInfo',{
            data: ''
        });
      }
    });
})

module.exports = router
