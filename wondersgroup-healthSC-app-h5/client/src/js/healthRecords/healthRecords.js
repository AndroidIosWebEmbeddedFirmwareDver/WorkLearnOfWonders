(function(){
    function queryString(key){
        return (document.location.search.match(new RegExp("(?:^\\?|&)"+key+"=(.*?)(?=&|$)"))||['',null])[1];
    }

    var uid = queryString('uid') || '';
    var page = parseInt(queryString('page'));

    var healthRecords = {
        config: [
            {
                api: '/healthRecords/DoctorList?uid='+uid,
                params: {}
            },
            {
                api: '/healthRecords/ReportList?uid='+uid,
                params: {}
            },
            {
                api: '/healthRecords/Prescription?uid='+uid,
                params: {}
            },
            {
                api: '/healthRecords/Hospitalization?uid='+uid,
                params: {}
            },
        ],
        getData: function(key){
            $.ajax({
                    url: url + healthRecords.config[key].api,
                    type: 'get',
                    dataType: 'json',
                    data: healthRecords.config[key].params,
                    beforeSend: function () {
                        $('.loading').show();
                        $('#menulist,.nodata').hide();
                    }
                })
                .done(function(data) {
                    if(data.code == 0){
                        if(data.data.content && data.data.content.length){
                            healthRecords.render(key,data.data.content);
                        }else{
                            healthRecords.NoData();
                        }
                    }else{
                        healthRecords.NoData();
                    }
                })
                .fail(function() {
                    healthRecords.NoData();
                });
        },
        render: function(key,data){
            switch (key) {
                case 0:
                    renderDoctorList(data);
                    break;
                case 1:
                    renderReportList(data);
                    break;
                case 2:
                    renderPrescription(data);
                    break;
                case 3:
                    renderHospitalization(data);
                    break;
            }
            $('.loading').hide();
        },
        NoData: function(){
            $('#menulist,.loading').hide();
            $('.nodata').show();
        },
        UI: function(){
            $('#menulist,.nodata').hide();
            var iNow = 0;

            $('#menu li').on('tap',function(){
                iNow = $(this).index();
                //healthRecords.UIChange(iNow)
            });

            // $('#scrolling_wrap').on('swipeLeft', function(){
            //     iNow >= 3 ? iNow = 0 : iNow++;
            //     healthRecords.UIChange(iNow)
            // })

            // $('#scrolling_wrap').on('swipeRight', function(){
            //     iNow <= 0 ? iNow = 3 : iNow--;
            //     healthRecords.UIChange(iNow)
            // })

            healthRecords.UIChange(page);
        },
        UIChange: function(iNow){
            $('#menu li').eq(iNow).addClass('current').siblings().removeClass('current');
            $('#inner-wrap > div').eq(iNow).addClass('current').siblings().removeClass('current');

            healthRecords.getData(iNow);
        },
        hideImg: function(){
            $('.nodata').hide();
        }
    };

    healthRecords.UI();

    function renderDoctorList(data){
        healthRecords.hideImg();
        var html = '';
        for(var item in data){
            html += '<li>\
        <div class="p_line">\
            <label>就诊时间：</label>\
            <p>'+data[item].date+'</p>\
        </div>\
        <div class="p_line">\
            <label>医　　院：</label>\
            <div class="td_p">\
                <div class="max-hei-1">'+data[item].hospital_name+'</div>\
            </div>\
        </div>\
        <div class="p_line">\
            <label>科　　室：</label>\
            <p>'+data[item].office_name+'</p>\
        </div>\
        </li>';
        }
        $('#menulist').html(html).show();
        $('#scrolling_wrap').scrollTop(0);
    }

    function renderReportList(data){
        healthRecords.hideImg();
        var html = '';
        for(var item in data){
            html += '<li><a href="'+url+'/healthRecords/extractReportInfo?id='+data[item].id+'&name='+data[item].hospital_name+
                '&department='+data[item].department_name+'&type='+data[item].item_name+'&date='+data[item].date+'&uid='+uid+'">\
        <div class="p_line">\
            <label>检查时间：</label>\
            <p>'+data[item].date+'</p>\
        </div>\
        <div class="p_line">\
            <label>医　　院：</label>\
            <div class="td_p">\
                <div class="max-hei-1">'+data[item].hospital_name+'</div>\
            </div>\
        </div>\
        <div class="p_line">\
            <label>科　　室：</label>\
            <p>'+data[item].department_name+'</p>\
        </div>\
        <div class="p_line">\
          <label>检查类别：</label>\
          <div class="td_p">\
              <div class="max-hei-1">'+data[item].item_name+'</div>\
          </div>\
        </div>\
        <span class="rarrow"></span>\
      </a></li>';
        }
        $('#report').html(html).show();
        $('#scrolling_wrap').scrollTop(0);
    }

    function renderPrescription(data){
        healthRecords.hideImg();
        var html = '';
        for(var item in data){
            html += '<li><a href="'+url+'/healthRecords/prescribingInfo?id='+data[item].id+'&uid='+uid+'&name='+data[item].hospital_name+
                '&department='+data[item].department_name+'&amount='+data[item].prescription_amount+'&date='+data[item].print_time+'">\
      <div class="p_line">\
          <label>开方时间：</label>\
          <p>'+data[item].print_time+'</p>\
      </div>\
      <div class="p_line">\
          <label>医　　院：</label>\
          <div class="td_p">\
              <div class="max-hei-1">'+data[item].hospital_name+'</div>\
          </div>\
      </div>\
      <div class="p_line">\
          <label>科　　室：</label>\
          <p>'+data[item].department_name+'</p>\
      </div>\
      <div class="p_line">\
          <label>金　　额：</label>\
          <p>¥'+data[item].prescription_amount+'</p>\
      </div>\
      <span class="rarrow"></span>\
    </a></li>';
        }
        $('#prescribing').html(html).show();
        $('#scrolling_wrap').scrollTop(0);
    }

    function renderHospitalization(data){
        healthRecords.hideImg();
        var html = '';
        for(var item in data){
            html += '<li><a href="'+url+'/healthRecords/operationInfo?id='+data[item].id+'&uid='+uid+'&hospital_name='+data[item].hospital_name+'&office_name='+data[item].office_name+'" >\
        <div class="p_line">\
            <label>住院时间：</label>\
            <p>'+data[item].hospitalized_time+'</p>\
        </div>\
        <div class="p_line">\
            <label>医　　院：</label>\
            <div class="td_p">\
                <div class="max-hei-1">'+data[item].hospital_name+'</div>\
            </div>\
        </div>\
        <div class="p_line">\
            <label>科　　室：</label>\
            <p>'+data[item].office_name+'</p>\
        </div>\
        <span class="rarrow"></span>\
    </a></li>';
        }
        $('#operation').html(html).show();
        $('#scrolling_wrap').scrollTop(0);
    }

    function scroll(callback){
        $('#scrolling_wrap').on('scroll',function(){
            var scrollTop = $(this).scrollTop();
            var scrollHeight = $(this).height();
            var winHeight = $(window).height() - $('.topmenu').height();

            if(scrollTop + winHeight === scrollHeight + 1){
                callback && callback();
            }
        });
    }

    window.joinURL = function(key){
        var address = url + '/healthRecords?uid='+uid+'&page='+key+'&finish=true&forbidden=true';
        location.href = address;
    }

})();




