/*单选、全选指令公用*/
app.directive("choose",function () {
    return{
        restrict:"A",
        template:"<td><label><input type='checkbox' value='{{itemId}}' class='checkboxList'>{{name}}</label></td>",
        scope:{
            itemId:"=",
            name:"="
        },
        controller: function () {
            Array.prototype.remove= function (val) {
                var index=this.indexOf(val);
                    this.splice(index,1);
            }
        },
        link: function (scope, element) {
            element.find("input:first").on("change", function () {
                if(this.checked && idList.indexOf(this.value)==-1){
                    idList.push(this.value);
                }else{
                    idList.remove(this.value);
                }
            })
        }
    }
});
app.directive("chooseAll",function () {
    return{
        restrict:"E",
        template:'<span><label><input type="checkbox"> 选择</label></span>',
        replace:"true",
        link: function (scope, element) {
            element.find("input:first").on("click", function () {
                if (this.checked) {
                    $('.checkboxList').each(function(el){
                        var _this=$(this)[0];
                        _this.checked=true;
                        if(_this.checked && idList.indexOf(_this.value) == -1){
                            idList.push(_this.value);
                        }
                    })
                }else{
                    $(".checkboxList").removeAttr("checked");
                    idList=[];
                }
            })
        }
    }
});
