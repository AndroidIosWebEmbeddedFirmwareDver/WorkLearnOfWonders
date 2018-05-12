'use strict'
var express = require('express')
var router = express.Router()
var request = require('../../common/request')
var api = require('../../common/api-url-map')

router.get('/detail', function(req, res) {
    var isapp = req.header('app-version')?false:true;
    request
        .get(
            api.getArticle
            ,req.query
            ,res
        )
        .then(function(data){
            if(data.code == 0){
                data.data = data.data || ""
                data.isapp = isapp
                res.render('article/details',data)
            }
        })

})

module.exports = router
