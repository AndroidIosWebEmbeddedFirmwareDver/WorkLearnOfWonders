'use strict';
var rtc = require('./routes-config.json');
var routesTools = {};
var routeFileBasePath = './server/routes';
var errorEntituy= require('./server/common/errorEntity');
var httpsignature =  require('./server/common/httpsignature');
var filter = require('./server/common/filter-url');
var _ = require('lodash');

global.cf.root && (rtc.root = global.cf.root);

function needSignature(req){
    var xhr = req.header('x-requested-with') ? req.header('x-requested-with') : "";
    if(xhr === "XMLHttpRequest" || filter.length === 0 || global.cf.filterLinks === false){
        return false
    }else{
        var reqUrl = req.url.split("?")[0]; //请求的url
        return _.includes(filter,reqUrl);
    }
    return false
}

/**
 * 批量启用路由
 */
routesTools.uesRoute = function(app){
    setGlobalHandler(app);
    setRoute(app);
    setErrorHandler(app);
};

/**
 * 用于签名过滤
 * @param app
 */
function setGlobalHandler(app){
    app.use(function (req, res, next) {
        if(needSignature(req)){
            httpsignature.verification(req)
                .then(function(json){
                    if(json.code == 0){
                        next();
                    }else{
                        res.render('error',{message:json.msg})
                    }
                })
        }else{
            next();
        }
    });
}

/**
 * 设置错误监听
 * @param app
 */
function setErrorHandler(app){
    app.use(function (req, res, next) {
        res.render('404');
        next();
    });

    app.use(function (err, req, res, next) {
        res.status(err.status || 500);

        var _Error = new errorEntituy('node',req.method,req.url,err.stack);

        console.error(_Error.toString());

        res.render('error', {
            message: _Error.toString()
        });
    });
}

/**
 * 设置路由将routes-config.json中配置的路由批量设置
 * @param app
 */
function setRoute(app){
    var rts = rtc.routes;
    for(var i in rts){
        var rd = rts[i];
        app.use(rtc.root+rd.parentUri,getRouteFile(rd.routePath));
    }
}

function getRouteFile(routePath){
    return require(routeFileBasePath+routePath);
}

routesTools.routeConfig = rtc;
module.exports = routesTools;
