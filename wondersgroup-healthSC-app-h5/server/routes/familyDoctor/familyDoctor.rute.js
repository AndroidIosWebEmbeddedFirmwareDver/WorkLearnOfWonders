var express = require('express')
var router = express.Router()

// 团队详情
router.get('/teamDetail', function(req, res) {
    res.render('familyDoctor/teamDetail')
})

// 服务包列表
router.get('/servicePackList', function(req, res) {
    res.render('familyDoctor/servicePackList')
})

// 服务包详情
router.get('/servicePackDetail', function(req, res) {
    res.render('familyDoctor/servicePackDetail')
})

// 医生详情
router.get('/doctorDetail', function(req, res) {
  var isDoc = req.headers['doctor'];
  if(isDoc){
    res.render('familyDoctor/doctorDetail-doc')
  }else{
    res.render('familyDoctor/doctorDetail')
  }
})

module.exports = router
