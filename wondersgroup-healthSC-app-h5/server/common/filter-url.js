/**
 * Created by stone on 16/3/1.
 * 需要验证的链接
 */

'use strict';
const urls = ['/healthRecords'];

function setUrls(){
    var _urls = [];
    for(var i = 0 ;i < urls.length ; i++){
        _urls.push(global.cf.root+urls[i]);
    }
    return _urls;
}

module.exports = setUrls();
