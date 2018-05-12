"use strict";


app.controller('HospitalEvalutionController', ['$scope', '$http', '$state','$rootScope','$localStorage', function($scope, $http, $state,$rootScope,$localStorage){
	$scope.pager = {
		"total_pages":0, 
		"total_elements":0, 
		"size": 10, 
		"number": 1, 
		"parameter":{
			"mobile": "",
			"hospitalName": "",
			"status":"" ,
			"isTop": ""
		},
		"data": []
	}

	$scope.params = {
		"mobile": "",
		"hospitalName": "",
		"status":"全部" ,
		"isTop": "全部"
	}

	$scope.setData = {
		"status": ["全部","待审核","审核通过","审核未通过"],
		"isTop": ['全部','置顶','未置顶']
	}

	$scope.goPage = function(number){
		Object.keys($scope.params).forEach(function(ele){
			$scope.pager.parameter[ele] = $scope.params[ele];
			if (ele === 'status') {
				switch ($scope.params[ele].length){
					case 2: $scope.pager.parameter[ele] = '';break;
					case 3: $scope.pager.parameter[ele] = 0;break;
					case 4: $scope.pager.parameter[ele] = 1;break;
					case 5: $scope.pager.parameter[ele] = 2;break;
				}
			}else if(ele === "isTop"){
				let num,value = $scope.pager.parameter["isTop"];
				switch(value){
					case "全部": num='';break;
					case "置顶": num=1;break;
					case "未置顶": num=0;break;
				}
				$scope.pager.parameter["isTop"]=num;
			}
		})
		let pager = $scope.pager;
		pager.number = number;
		$http.post(server+'/admin/evaluate/hospital/list',pager)
		.then(function(xhr){
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


	//批量删除
	let idList = [];
	$scope.selectAll = false;

	angular.element("#delete").on('click',function(){
		let options = {
			"id": "list",
			"attr": "data-item-id"
		}
		checkedAll(options,$scope.goPage,1);
		layer.confirm("确定删除么？",{},function(index){
			$http.post(server + '/admin/evaluate/hospital/batchDelete',idList)
			.then(function(xhr){
				layer.msg(xhr.data.msg);
				$scope.goPage(1);
			},function(err){
				layer.msg(err.message);
			})
		})
	})

	//通过
	$scope.pass = false;
	
	$scope.goPass = function(id,statusBool,bool){
		let sUrl = "";
		let params = null;
		let urls = {
			"status": '/admin/evaluate/hospital/updateStatus',
			"isTop": '/admin/evaluate/hospital/updateIsTop'
		},words=["通过","删除","取消置顶","置顶"];
		switch (statusBool){
			case 1: 
			case 2: sUrl = urls.status;params = {"id":id,"status":statusBool};break;
			case 3: 
			case 4: sUrl = urls.isTop;params = {"id":id,"isTop":statusBool-3};break;
		}
		layer.confirm("确定"+words[statusBool-1]+"么？",{},function(index){
			$http.post(server + sUrl,params)
			.then(function(xhr){
				if (xhr.code == 0) {
					statusBool===1?($scope.pass = true):false;
				}
				layer.msg(xhr.data.msg);
				$scope.goPage(1);
			},function(err){
				layer.msg("操作失败");
			})
		})
	}

	function checkedAll(options){
		let inputArr = Array.from(angular.element("#"+options.id).find("input:checkbox"));
		idList.length = 0;
		inputArr.forEach(function(ele){
			ele.checked == true?idList.push(ele.getAttribute(options.attr)):false;
		})
	}
}])