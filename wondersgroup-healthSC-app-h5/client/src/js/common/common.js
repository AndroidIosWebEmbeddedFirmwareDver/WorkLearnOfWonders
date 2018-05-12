(function(wd){
    var YmCommon = wd.YmCommon = {};

    YmCommon.isWX = function(){
        var ua = navigator.userAgent.toLowerCase();
        return ua.match(/MicroMessenger/i) == "micromessenger" ? true : false;
    }

    // YmCommon.WxShare = function(wxEntity){
    //     this.shareInfo = wxEntity.shareInfo;
    //
    //     this.
    //
    // }

})(window);

// var WxShare = function(){
//     var key = JSON.parse('<%-key%>');
//
//     // 微信分享
//     function WXshare(){
//         var shareInfo = {
//             title: '健康云-XIAO传递', // 分享标题
//             desc: '[1000+人正在参与]传说8090后都在这样爱父母，你呢?',  // 分享标题
//             link: '<%=_shareUrl%>',
//             imgUrl: 'http://www.wdjky.com/holiday/img/icon.jpg' // 分享图标
//         };
//
//         wx.config({
//             debug: false,
//             appId: '<%=_appid%>',
//             timestamp: key.timestamp, // 必填，生成签名的时间戳
//             nonceStr: key.nonceStr, // 必填，生成签名的随机串
//             signature: key.signature,// 必填，签名，见附录1
//             jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
//         });
//
//         wx.ready(function(){
//
//             //分享到朋友圈
//             wx.onMenuShareTimeline({
//                 title: shareInfo.title,
//                 link: shareInfo.link,
//                 imgUrl: shareInfo.imgUrl,
//                 success: function() {
//
//                 },
//                 cancel: function() {
//
//                 }
//             });
//             //分享给朋友
//             wx.onMenuShareAppMessage({
//                 title: shareInfo.title,
//                 desc: shareInfo.desc,
//                 link: shareInfo.link,
//                 imgUrl: shareInfo.imgUrl,
//                 success: function() {
//
//                 },
//                 cancel: function() {
//
//                 }
//             });
//             //分享到QQ
//             wx.onMenuShareQQ({
//                 title: shareInfo.title,
//                 desc: shareInfo.desc,
//                 link: shareInfo.link,
//                 imgUrl: shareInfo.imgUrl,
//                 success: function() {
//
//                 },
//                 cancel: function() {
//
//                 }
//             });
//             //分享到腾讯微博
//             wx.onMenuShareWeibo({
//                 title: shareInfo.title,
//                 desc: shareInfo.desc,
//                 link: shareInfo.link,
//                 imgUrl: shareInfo.imgUrl,
//                 success: function() {
//
//                 },
//                 cancel: function() {
//
//                 }
//             });
//             //分享到QQ空间
//             wx.onMenuShareQZone({
//                 title: shareInfo.title, // 分享标题
//                 desc: shareInfo.desc, // 分享描述
//                 link: shareInfo.link, // 分享链接
//                 imgUrl: shareInfo.imgUrl, // 分享图标
//                 success: function() {
//
//                 },
//                 cancel: function() {
//
//                 }
//             });
//         });
//     }
//
//     is_weixn() && WXshare();
// }
