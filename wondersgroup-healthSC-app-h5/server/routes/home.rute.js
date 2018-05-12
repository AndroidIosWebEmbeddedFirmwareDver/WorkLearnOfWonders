var express = require('express')
var router = express.Router()

router.get('/', function(req, res) {
    res.render('home')
})

//引导页
router.get('/guide', function(req, res) {
    var code = req.headers['city-code'];
    var _title = '';

    _title = String(code) === '510122000000' ? '健康双流' : '四川微健康';

    res.render('home/guide',{
        title: _title
    })
})
module.exports = router
