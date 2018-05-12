'use strict'
var express = require('express')
var router = express.Router()
//var request = require('../../common/request')
//var api = require('../../common/api-url-map')

router.get('/', function(req, res) {
    res.render('archives/index',{

    });
})

module.exports = router