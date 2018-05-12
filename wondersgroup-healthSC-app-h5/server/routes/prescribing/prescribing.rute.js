var express = require('express')
var router = express.Router()

router.get('/', function(req, res) {
    res.render('prescribing/index')
})
router.get('/inputName', function(req, res) {
    res.render('prescribing/inputName')
})
router.get('/selectTime', function(req, res) {
    res.render('prescribing/selectTime')
})
router.get('/list', function(req, res) {
    res.render('prescribing/list')
})
router.get('/info', function(req, res) {
    res.render('prescribing/info')
})
module.exports = router
