'use strict';
var jsSHA = require('jssha');
var request = require('request');

var WxTools = {};

var createNonceStr = function() {
    return Math.random().toString(36).substr(2, 15);
};

var createTimestamp = function() {
    return parseInt(new Date().getTime() / 1000) + '';
};

var raw = function(args) {
    var keys = Object.keys(args);
    keys = keys.sort()
    var newArgs = {};
    keys.forEach(function(key) {
        newArgs[key.toLowerCase()] = args[key];
    });

    var string = '';
    for (var k in newArgs) {
        string += '&' + k + '=' + newArgs[k];
    }
    string = string.substr(1);
    return string;
};

/**
 * @synopsis 签名算法
 *
 * @param jsapi_ticket 用于签名的 jsapi_ticket
 * @param url 用于签名的 url ，注意必须动态获取，不能 hardcode
 *
 * @returns
 */
WxTools.sign = function(jsapi_ticket, url) {
    var ret = {
        jsapi_ticket: jsapi_ticket,
        nonceStr: createNonceStr(),
        timestamp: createTimestamp(),
        url: url
    };
    var string = raw(ret);
    jsSHA = require('jssha');
    var shaObj = new jsSHA(string, 'TEXT');
    ret.signature = shaObj.getHash('SHA-1', 'HEX');

    return ret;
};

WxTools.getAccessToken = function(appid, secret) {
    var url = 'https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=' + appid + '&secret=' + secret;
    var promise =  new Promise((resolve, reject) => {
        request(url,(err, res, body)=>{
            if (res && res.statusCode === 200) {
                var body = JSON.parse(body);
                resolve(body.access_token);
            } else {
                reject(' error - -');
            }
        });
    });
    return promise;
}

WxTools.getTicket = function(access_token) {
    var url = 'https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token='+access_token+'&type=jsapi';
    var promise =  new Promise((resolve, reject) => {
        request(url, (err, res, body) => {
            if (res && res.statusCode === 200) {
                var body = JSON.parse(body);
                resolve(body.ticket);
            } else {
                reject(' error - -');
            }
        });
    });
    return promise;
}


module.exports = WxTools;
