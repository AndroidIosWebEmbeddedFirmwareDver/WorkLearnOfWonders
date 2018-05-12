'use strict'

module.exports = function(type,method,url,msg){
    this.type = type
    this.method = method
    this.url = url
    this.msg = msg
    this.code = 1000

    this.toString = function(){
        var str = 'request server : ' + this.type + '\n'
                + 'request method : ' + this.method + '\n'
                + 'request url : ' + this.url + '\n'
                + 'error message : ' + this.msg + '\n'
                + 'error code : ' + this.code + '\n'

        return str
    }
}