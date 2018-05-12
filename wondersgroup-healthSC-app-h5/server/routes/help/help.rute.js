'use strict'
var express = require('express')
var router = express.Router()
var request = require('../../common/request')
var api = require('../../common/api-url-map')
var _  = require('lodash')


router.get('/', function(req, res) {
    var area = req.headers['city-code'];
    var parms = req.query;
    if (area && String(area) === '510122000000') {
      parms.platform = 2;
    }else{
      parms.platform = 1;
    }
    request.get(api.getHelpCenter,parms,res).then(function(data){
            if(data.code == 0){
                var content = _.filter(data.data.content, function(n) {
                    return n.is_visable == '0';
                });
                res.render('help/index',{data:content,query:req.query});
            }
        },function(){
            console.log('query error');
        });


})

router.get('/feedback', function(req, res) {
    var area = req.headers['city-code'];
    var platform;
    if (area && String(area) === '510122000000') {
      platform = 2
    }else{
      platform = 1
    }
    res.render('help/feedback',{platform:platform,query:req.query});
})

router.post('/feedback/add', function(req, res){
    request.post(api.feedback,req.body,
        {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        })
        .then(function(data){
            res.send(data)
        })

})

module.exports = router
