'use strict';
app.service('getSelectedCheckbox', function () {
    var selected = [];
    function updateSelected(action, id){
        if (action == 'add' && selected.indexOf(id) == -1){
            selected.push(id);
        }else if(action == 'remove' && selected.indexOf(id) != -1){
            selected.splice(selected.indexOf(id), 1);
        }
        return selected;
    };

    this.updateSelection=function($event, id){
        var checkbox = $event.target;
        var action = (checkbox.checked ? 'add' : 'remove');
        return updateSelected(action, id);
    };

    this.selectAll=function($event,questionList){
        var checkbox = $event.target;
        var action = (checkbox.checked ? 'add' : 'remove');
        var selectArr='';
        for (var i = 0; i < questionList.length; i++) {
            var question = questionList[i];
            selectArr=updateSelected(action, question.id);
        }
        return selectArr;
    };

    this.isSelected=function(id){
        return selected.indexOf(id) >= 0;
    };

    this.isSelectedAll=function(questionList){
        return selected.length === questionList.length;
    };
});