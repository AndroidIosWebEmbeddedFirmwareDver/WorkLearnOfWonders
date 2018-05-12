/**
 * 定时任务用每7200秒获取一次微信access_token
 */
'use strict'
var WxTools = require('../common/wxTools');

var WxTask = {};

global.wx = {
    appid:"wxd3ecfc34d7d289b4",
    secret:"82012b6cdb0fe03e223671b2e3c7ab87",
    access_token:"",
    ticket:""
}
function getToken(appid,secret){
    WxTools.getAccessToken(appid,secret)
    .then(function(token){
        if(token){
            global.wx.access_token = token;
            console.log('token',token);
            getTicket(token);
        }
    });
}

function getTicket(access_token){
    WxTools.getTicket(access_token)
    .then(function(ticket){
        global.wx.ticket = ticket;
        console.log('ticket',ticket);
    });
}

WxTask.start = function(){
    getToken(global.wx.appid,global.wx.secret);
    setInterval(function(){
        getToken(global.wx.appid,global.wx.secret);
    },7200*1000);
};

module.exports = WxTask;
