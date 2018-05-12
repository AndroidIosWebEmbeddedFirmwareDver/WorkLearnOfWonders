'use strict';
var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'client/views'));
app.set('view engine', 'ejs');
app.use(global.cf.root,require('./routers/home'));

//app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));
app.use(cookieParser());
//静态资源访问路径设置
app.use(global.cf.root,express.static(path.join(__dirname,'client/static')));
//favicon 设置
//app.use(favicon(path.join(__dirname,resourcesPath.static, 'favicon.ico')));
module.exports = app;
