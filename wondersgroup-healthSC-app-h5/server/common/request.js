'use strict'
var fetch = require('node-fetch')
var queryString = require('query-string')
var _ = require('lodash')
var Mock = require('mockjs')
var errorEntituy= require('./errorEntity')

var request = {};

const baseUrl = global.cf.server

request.get = function(url, params){
    url = baseUrl+url
    if(params){
        url += '?' + queryString.stringify(params)
    }

    return fetch(url)
            .then(function(rp){
                if(rp.ok){
                    return rp.json()
                }else{
                    var _Error = new errorEntituy('API Server',"GET",rp.url,rp.statusText)
                    console.error(_Error.toString())
                    return _Error
                }
            })
            .then(function(json){
                if(global.cf.isMock){
                    json = Mock.mock(json)
                }
                return json
            })
            .catch(function(e){
                var _Error = new errorEntituy('API Server','GET',url, e.message)
                console.error(_Error.toString())
                return _Error
            })
}

request.post = function(url, body , headers){
    url = baseUrl+url

    var options = {
        method: 'POST'
    }

    if(headers){
        options = _.extend(options,{headers: headers})
    }

    options = _.extend(options,{body: JSON.stringify(body)})

    return fetch(url, options)
            .then(function(rp){
                return rp.json()
            })
            .then(function(json){
                if(global.cf.isMock){
                    json = Mock.mock(json)
                }
                return json
            })
            .catch(function(e){
                var _Error = new errorEntituy('API Server','POST',url, e.message)

                console.error(_Error.toString())

                return _Error
            })
}

module.exports = request
