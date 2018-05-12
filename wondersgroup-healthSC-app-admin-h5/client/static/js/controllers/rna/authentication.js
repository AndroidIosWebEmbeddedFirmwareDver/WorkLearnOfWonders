"use strict";


app.controller('authenticationController', ['$scope', '$http', '$state','$rootScope','$localStorage', function($scope, $http, $state,$rootScope,$localStorage){
	$scope.pager = {
		"total_pages":0,
		"total_elements":0,
		"size": 10,
		"number": 1,
		"parameter":{
			"name": "",
			"mobile": "",
			"idcard": "",
			"verificationLevel": 0,
		},
		"data": []
	}

	$scope.params = {
		"name": "",
		"mobile": "",
		"idcard": "",
		"verificationLevel": "待审核",
		"reason":"个人信息不匹配"
	}

	$scope.setData = {
		"verificationLevel": ["待审核","认证通过","认证未通过"],
	}

	$scope.goPage = function(number){
		Object.keys($scope.params).forEach(function(ele){
			$scope.pager.parameter[ele] = $scope.params[ele];
			if (ele === 'verificationLevel') {
				switch ($scope.params[ele].length){
					case 2: $scope.pager.parameter[ele] = "";break;
					case 3: $scope.pager.parameter[ele] = 0;break;
					case 4: $scope.pager.parameter[ele] = 1;break;
					case 5: $scope.pager.parameter[ele] = -1;break;
				}
			}
		})
		let pager = $scope.pager;
		pager.number = number;
		$http.post(server+'/admin/verification/manage/list',pager)
		.then(function(xhr) {
			$scope.pager = xhr.data;
		},function(err){
			layer.msg("操作失败");
		})
	}

	//初始化
	$scope.goPage(1);


	//搜索
	$scope.search = function(){
		$scope.goPage(1);
	}

	//审核通过与驳回

	let httpReq = function(url,para){
		$http.post(server+url,para)
		.then(function(xhr){
			layer.msg(xhr.data.msg);
			$scope.goPage(1);
		},function(err){
			layer.msg("操作失败");
		})
	}

	let passUrls = {
		"pass": "/admin/verification/manage/verified",
		"refuse": "/admin/verification/manage/refuse"
	}
	$scope.getPass = function(flag,id){
		let url,rtnVal,para = {};
		para.id = id;
		switch(flag){
			case true:
			url = passUrls.pass;
			layer.confirm("确定通过么？",{},function(index){
				rtnVal = true;
				rtnVal&&httpReq(url,para);
			})
			break;
			case false:
			url = passUrls.refuse;
			layer.confirm("确定驳回么？", {}, function(index){
				let _options,_reason = ["自定义原因","图片无效","上传图片模糊","个人信息不匹配"];
				_reason.forEach(function(ele){
					_options = "<option value='"+ele+"'>"+ele+"</option>"+_options;
				})
				let _fragment = '<form class="form-inline" id="reasons" style="width:360px;padding:15px;"><div class="form-group"><label class="control-label">&nbsp;&nbsp;驳回原因：</label><select class="form-control" style="width: 220px">'+_options+'</select></div><div id="_intro" style="height:180px;display:none"><br><label class="control-label">&nbsp;&nbsp;说明原因：</label><textarea class="form-control" id="_reasons" style="width:220px;height:150px;resize:none;" placeholder="请填写原因,不超过30字" maxlength="30"></textarea></div></form>';
				layer.confirm(_fragment,{area:['400px','380px']},function(index){
					let _select = angular.element("#reasons").find('select').val();
					if (_select != _reason[0]) {
						para.reason = _select;
					} else {
						para.reason = angular.element("#_reasons").val();
						if (!para.reason) {
							layer.open({
								content:'自定义原因不能为空。',
								type:1,
								time: 2500,
								area:['250px','150px'],
								btn:'确定',
							});
							return false;
						}
					}
					rtnVal = true;
					rtnVal&&httpReq(url,para);
				})
				angular.element("#reasons select").on('change',function(){
					let _select = angular.element("#reasons").find('select').val();
					if (_select == _reason[0]) {
						angular.element("#_intro").css('display','block');
					}else{
						angular.element("#_intro").css('display','none');
					};
				})
			});
			break;
		}
	}

	$scope.zoomPic = function(name,idcard,url){
		layer.open({
			title: "姓名："+name+"&nbsp;&nbsp;&nbsp;&nbsp;ID："+idcard,
			content:"<div style='width:100%;height:100%;text-align:center'><img style='max-width:100%;max-height:100%;' src='"+url+"' />"+
			"<p><a href='"+url+"' target='_blank' title='查看大图' style='color: blue'>查看大图</a></p></div>",
			area:['500px','500px']
		})
	}
}])