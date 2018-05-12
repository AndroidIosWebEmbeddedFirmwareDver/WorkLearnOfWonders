app.controller('imformationArticlesEditController',['$scope','$http','$state','$stateParams','$localStorage',function($scope,$http,$state,$stateParams,$localStorage) {
    $scope.editObj = {}
    $scope.getClassifyList = function(){
        $http
          .get($scope.baseURL + '/back/article/categoryList')
          .success(function(rs){
            if(rs.code == 0){
              $scope.classifyList = rs.data
            }else{
              layer.msg(rs.msg)
            }
          })
          .catch(function(e){
            layer.msg('Server Error', {icon: 5})
          })
    }
    $scope.getClassifyList() // 获取分类列表

    $scope.getData = function(){      
      $http
        .get($scope.baseURL + '/back/article/areaInfo?id='+$stateParams.id)
        .success(function(rs){
            if(rs.code == 0){
                if(rs.data){
                    $scope.editObj=rs.data;
                    $scope.editObj.article_id=$scope.editObj.id
                    for(var i=0;i<$scope.editObj.categories.length;i++){
                        if($scope.editObj.categories[i].belong_article==0){
                            $scope.selected.push($scope.editObj.categories[i].id)
                        }
                    }
                    $scope.editObj.ids = $scope.selected
                }else{
                    layer.msg('您输入的文章ID不存在，请检查')
                    $scope.editObj={}
                    $scope.editObj.thumb="img/nopic.gif"
                }
            }else{
              layer.msg(rs.msg)
            }
        })
        .catch(function(e){
          layer.msg('Server Error', {icon: 5})
        })
    }

    $scope.getArticleData = function(){
      if($scope.editObj.article_id =='' || $scope.editObj.article_id == undefined || $scope.editObj.article_id == null){
        return
      }
      $http
        .get($scope.baseURL + '/back/article/areaInfo?id='+$scope.editObj.article_id)
        .success(function(rs){
            if(rs.code == 0){
                if(rs.data){
                    $scope.editObj=rs.data;
                    $scope.editObj.article_id=$scope.editObj.id
                    for(var i=0;i<$scope.editObj.categories.length;i++){
                        if($scope.editObj.categories[i].belong_article==0){
                            $scope.selected.push($scope.editObj.categories[i].id)
                        }
                    }
                    $scope.editObj.ids = $scope.selected
                }else{
                    layer.msg('您输入的文章ID不存在，请检查')
                    $scope.editObj={}
                    $scope.editObj.thumb="img/nopic.gif"
                }
            }else{
              layer.msg(rs.msg)
            }
        })
        .catch(function(e){
          layer.msg('Server Error', {icon: 5})
        })
    }

    if($stateParams.id){
        $scope.title = "编辑资讯文章"
        $scope.idIsEdit = true
        $scope.getData()
    }else{
        $scope.title = "添加资讯文章"
        $scope.idIsEdit = false
    }

    $scope.selected = []

    var updateSelected = function(action,id){
        if(action == 'add' && $scope.selected.indexOf(id) == -1){
            $scope.selected.push(id)
        }
        if(action == 'remove' && $scope.selected.indexOf(id)!=-1){
            var idx = $scope.selected.indexOf(id)
            $scope.selected.splice(idx,1)
        }
        $scope.editObj.ids=$scope.selected
    }

    $scope.updateSelection = function($event, id){
        var checkbox = $event.target
        var action = (checkbox.checked?'add':'remove')
        updateSelected(action,id)
    }

    $scope.isSelected = function(id){
        return $scope.selected.indexOf(id)>=0
    }

    // 保存资讯文章
    $scope.saveData = function(){
        if($scope.editObj.ids==''){
            layer.msg('请选择文章分类');
            return false;
        }
        var pager = {
            category_ids:$scope.editObj.ids.join(','),
            article_id:$scope.editObj.article_id,
            is_visable: 1,
        }
        $http
          .post($scope.baseURL +'/back/article/areaArticleUpdate', pager)
          .success(function(rs) {
              if(rs.code==0){
                var _layer = layer.alert(rs.msg,function(){
                  $state.go('app.im.informationArticles',{id:$stateParams.c_id})
                  layer.close(_layer)
                })
              }else{
                  layer.msg(rs.msg)
              }
          }, function(rs) {
              layer.msg(rs.msg)
          })
          .catch(function(e){
            layer.msg('Server Error', {icon: 5})
          })
    }
    $scope.back = function(){
      $state.go('app.im.informationArticles',{id:$stateParams.c_id})
    }
}])
