/**
 * Created by stone on 16/5/19.
 */
$(document).ready(function(){
    var currentItem = $('body .question_item:first-child')

    currentItem.css({'display':'block'});

    $('li').on('touchstart',function(){
        $(this).parent().children().removeClass('choosed');
        $(this).addClass('choosed');
        var temp=$(this);
        setTimeout(function(){
            $('.question_item').css({'display':'none'});

            currentItem = temp.parents('.question_item').next('.question_item')
            currentItem.css({'display':'block'});

            var num=temp.parents('.question_item').find('.num').text().split('/')[0];

            if(num == 33){
                currentItem = $('#33')
            }

            btnState();

            if(num==32){
                $('#submit').css({'display':'block'});
            }
            if(num==33){
                temp.parents('.question_item').css({'display':'block'});
            }
        }, 100);
        var elem=document.getElementsByClassName('choosed');
        if(elem.length==33){
            $('#submit').css('background-color','#2E7AF0');
        }
    });

    $('.btn_up').on('touchstart',function(){
        var currentItemId = currentItem.attr('id')
        if(currentItemId > 1){
            $('#'+currentItemId).css({'display':'none'})
            $('#'+(currentItemId-1)).css({'display':'block'})
            currentItem = $('#'+(currentItemId-1))
        }
        if(currentItemId <= 33){
            $('#submit').css({'display':'none'})
        }

        btnState()
    })

    $('.btn_down').on('touchstart',function(){
        if(currentItem.find('.option_list .choosed').length !== 0 && currentItem.attr('id') != 33){
            if(currentItem.attr('id') == 32){
                $('#submit').css({'display':'block'});
            }
            var currentItemId = currentItem.attr('id')-0;
            $('#'+currentItemId).css({'display':'none'})
            $('#'+(currentItemId+1)).css({'display':'block'})
            currentItem = $('#'+(currentItemId+1))
            btnState()
        }
    })

    function btnState(){
        var up = $('.btn_up')
        var down = $('.btn_down')
        if(currentItem.attr('id') == 1){
            up.css('background-color','#cccccc')
        }else{
            up.css('background-color','#2E7AF0')
        }
        if(currentItem.find('.option_list .choosed').length === 0 || currentItem.attr('id') == 33){
            down.css('background-color','#cccccc')
        }else{
            down.css('background-color','#2E7AF0')
        }
    }

    btnState()

    var urlArr=window.location.href.split('/');
    $('#submit').on('touchstart',function(){
        var answers={};
        if($('.choosed').length==33){
            var parms = {
                registerid:registerid,
                content:""
            }
            var _cs = []
            for(var i=0;i<$('.choosed').length;i++){
                _cs.push($('.choosed')[i].value)
                //answers[$('.choosed')[i].attributes.key.value]=$('.choosed')[i].value;
            };
            parms.content = _cs.join(",");
            //if($.inArray('question', urlArr)!=-1){
                $.post(uri+'/measurement/submitAnswer',parms, function (data) {
                    if(data.code == 0 && data.data){
                        var item=JSON.stringify(data.data);
                    }else{
                        var item=JSON.stringify(data);
                    }
                    window.localStorage.setItem('param',item);

                    $('.question_item').find('.option_list.choosed').removeClass('choosed')

                    window.location.href=uri+'/measurement/answer?finish=true';
                });
            // }else{
            //     var valueArrs=window.location.href.split('?')[1].split('&');
            //     for(var j=0;j<valueArrs.length;j++){
            //         var itemArr=valueArrs[j].split('=');
            //         answers[itemArr[0]]=decodeURIComponent(itemArr[1]);
            //     }
            //     $.post(uri+'/measurement/submitDoctAnswer',answers, function (data) {
            //         window.location.href=uri+'/measurement/doctAnswer?param='+ JSON.stringify(data.data);
            //     });
            // }
        }
    });
});
