var express = require('express')
var router = express.Router()

//用户注册协议
router.get('/agreement', function(req, res) {
    var area = req.headers['city-code'];
    if(area && String(area) === '510122000000'){
        res.render('ruler/agreement-sl')
    }else{
        res.render('ruler/agreement')
    }
})

//版权声明
router.get('/copyright', function(req, res) {
    var area = req.headers['city-code'];

    if(area && String(area) === '510122000000'){
        res.render('ruler/copyright-sl')
    }else{
        res.render('ruler/copyright')
    }
})

//免责声明
router.get('/disclaimer', function(req, res) {
    var area = req.headers['city-code'];

    if(area && String(area) === '510122000000'){
        res.render('ruler/disclaimer-sl')
    }else{
        res.render('ruler/disclaimer')
    }
})

//实名认证规则
router.get('/authentication', function(req, res) {
    var area = req.headers['city-code'];

    if(area && String(area) === '510122000000'){
        res.render('ruler/authentication-sl')
    }else{
        res.render('ruler/authentication')
    }
})

//预约规则
router.get('/appointment', function(req, res) {
    var area = req.headers['city-code'];

    if(area && String(area) === '510122000000'){
        res.render('ruler/appointment-sl')
    }else{
        res.render('ruler/appointment')
    }
})

module.exports = router
