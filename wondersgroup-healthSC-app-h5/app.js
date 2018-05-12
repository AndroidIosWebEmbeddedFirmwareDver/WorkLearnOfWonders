'use strict'
var express = require('express')
var path = require('path')
var logger = require('morgan')
var cookieParser = require('cookie-parser')
var bodyParser = require('body-parser')
var routeTools = require('./routes-tools')
var WxTask = require('./server/schedule/wxTask')
var app = express()

var resources = {
    'views': 'client/views',
    'static': 'client/static'
}
//微信定时获取token
//WxTask.start()

//模板引擎设置
app.set('views', path.join(__dirname,resources.views))
app.set('view engine', 'ejs')
global.cf.flg=="lo"&&app.use(logger('dev'))
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: false}))
app.use(cookieParser())
//设置全局函数
app.locals['_src'] = function(src){ return routeTools.routeConfig.root+src }
//静态资源访问路径设置
app.use(routeTools.routeConfig.root,express.static(path.join(__dirname,resources.static)))
//路由配置
routeTools.uesRoute(app)

//获取secret
//secrMetodel.getSecret();

module.exports = app
