'use strict'

var crypto = require('crypto')
var request = require('./request')
var api = require('./api-url-map')

const headers = function (req) {
    this['app-version'] = req.header('app-version')
    this['channel'] = req.header('channel')
    this['device'] = req.header('device')
    this['main-area'] = req.header('main-area')
    this['model'] = req.header('model')
    this['os-version'] = req.header('os-version')
    this['platform'] = req.header('platform')
    this['screen-height'] = req.header('screen-height')
    this['screen-width'] = req.header('screen-width')
    this['spec-area'] = req.header('spec-area')
    this['version'] = req.header('version')
}

const otherHeaders = function (req) {
    this['request-id'] = req.header('request-id')
    this['client-request-time'] = req.header('client-request-time')
    this['signature'] = req.header('signature')
}

const app_secret = 'b4fad2ff-368c-4ab5-aa0f-e703cd617954'

var signatureTools = {}

signatureTools.verification = function(req){
    return verification(req)
}

/**
 * 签名验证
 * @param req
 */
function verification(req) {
    var token = req.header('access-token') ? req.header('access-token') : ""
    return request.get(api.session,{token: token})
        .then(function(json){
            if(json.code == 0){
                var session = json.data
                var secret = session.secret == "" ? "d5891405-77f9-4c4f-b939-9442aa075836" : session.secret
                var signatureStr = joinSignatureStr(req,token) //拼接签名明文
                var encrypted = getSignatureStr(signatureStr, secret)  //生成签名密文
                var clientSignature = req.header('signature') ? req.header('signature') : ""   //客户端密文

                if (encrypted != clientSignature) { //签名不一致
                    console.log('verification','签名不一致')
                    return {code:1001,msg:'签名不一致'}
                } else {
                    return {code:0,msg:'签名成功'}
                }
            }else{
                json.msg = '请登录后再查看'
               return json
            }
        })
}

/**
 * 拼接头信息
 * @param req
 * @returns {string}
 */
function getHeaders(req) {
    var headersStr = ""
    var header = new headers(req)
    for (var key in header) {
        if (key !== "app-version") {
            headersStr += "&"
        }
        headersStr += key
        headersStr += "="
        headersStr += header[key]
    }
    return headersStr
}

/**
 * 获取有序的参数
 * @param req
 * @returns {string}
 */
function parmSort(parmsrc) {
    return parmsrc?parmsrc.split('&').sort().join('&'):""
}

/**
 * 拼接签名串
 * @param req
 */
function joinSignatureStr(req,token) {
    var oh = new otherHeaders(req)
    var path = req.originalUrl.split('?')[0].toLowerCase()
    var parm = parmSort(req.originalUrl.split('?')[1])
    var prefix = "welovepanda!\n"
    var headerStr = getHeaders(req)

    var signatureStr = prefix
        + "request-id=" + oh['request-id']
        + "@client-request-time=" + oh['client-request-time'] + "\n"
        + path + "+access-token=" + token + "&" + headerStr + "+" + parm + "\n"
        + app_secret

    return signatureStr
}

/**
 * 加密源签名串
 * @param signatureStr
 * @param secret
 */
function getSignatureStr(signatureStr, secret) {
    return crypto.createHmac('SHA256', secret).update(signatureStr).digest('base64')
}

module.exports = signatureTools
