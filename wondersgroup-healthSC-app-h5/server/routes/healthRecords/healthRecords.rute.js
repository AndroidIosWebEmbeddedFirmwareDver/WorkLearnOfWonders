var express = require('express')
var router = express.Router()
var request = require('../../common/request')
var api = require('../../common/api-url-map')

// 就诊记录
router.get('/', function(req, res) {
    res.render('healthRecords/index')
})

// 查验报告详情
router.get('/extractReportInfo', function(req, res) {
  request.get(api.ReportInfo,req.query,res)
    .then(function(data){
      if(data.code == 0){
        res.render('healthRecords/extractReportInfo',{
            data: data.data
        });
      }else{
        console.error('获取查验报告详情失败',data);
        res.render('healthRecords/extractReportInfo',{
            data: ''
        });
      }
    });
})
// 电子处方详情
router.get('/prescribingInfo', function(req, res) {
    var type = req.query.type;
    var url = null;
    if(type === 'app'){
        url = api.PrescriptionInfos;
    }else{
        url = api.PrescriptionInfo;
    }
    request.get(url,req.query,res)
        .then(function(data){
            if(data.code == 0){
                res.render('healthRecords/prescribingInfo',{
                    list: data.data
                });
            }else{
              console.error('获取电子处方详情失败',data);
              res.render('healthRecords/prescribingInfo',{
                  list: []
              });
            }
        });
})

// 住院史详情
router.get('/operationInfo', function(req, res) {
    var query = req.query
    request.get(api.HospitalizationInfo,req.query,res)
        .then(function(data){
            if(data.code == 0){
                res.render('healthRecords/operationInfo',{
                    data: data.data,
                    query: query
                });
            }else{
              console.error('获取住院史详情失败',data);
              res.render('healthRecords/operationInfo',{
                  data: ''
              });
            }
        });
})



/****  接口信息   ******/
// 就诊记录列表
router.get('/DoctorList', function(req, res) {
    request.get(api.DoctorList,req.query,res)
    .then(function(data){
       if(data.code == 0){
           res.send(data);
       }else{
           res.send(data.msg);
       }
    });
})

// 查验报告列表
router.get('/ReportList', function(req, res) {
    request.get(api.ReportList,req.query,res)
        .then(function(data){
            if(data.code == 0){
                res.send(data);
            }else{
                res.send(data.msg);
            }
        });
})

//电子处方列表
router.get('/Prescription', function(req, res) {
    request.get(api.Prescription,req.query,res)
        .then(function(data){
            if(data.code == 0){
                res.send(data);
            }else{
                res.send(data.msg);
            }
        });
})

// 住院史列表
router.get('/Hospitalization', function(req, res) {
    request.get(api.Hospitalization,req.query,res)
        .then(function(data){
            if(data.code == 0){
                res.send(data);
            }else{
                res.send(data.msg);
            }
        });
})

module.exports = router
