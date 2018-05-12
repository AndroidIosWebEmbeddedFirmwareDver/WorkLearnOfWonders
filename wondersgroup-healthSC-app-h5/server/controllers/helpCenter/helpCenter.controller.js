'use strict';
var requestTools = require('../../common/superagent-tools');
var portUrl = requestTools.portUrl;

module.exports = {
  getView: function(req, res) {
    requestTools.get(res, portUrl.helpCenter.key, req.query, function(_data) {
      // var isapp = false;
      // if (req.header('access-token')) {
      //   isapp = true;
      // }
      var data = JSON.parse(_data);
      if (data.code === 0) {
        res.render('helpCenter/helpCenter', {
          "data": data && data.data || []
        });
      } else {
        res.render('error', {
          "message": data.msg
        });
      }

    });

  }
}
