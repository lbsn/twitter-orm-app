var tweetApp = angular.module("tweetApp", []);

tweetApp.controller("tweetCtrl", function($scope, $http){
	$scope.keyword;
	
	$scope.startStreaming = function(){
		var search = {};
		search["keyword"] = $scope.keyword;
		var data = JSON.stringify(search);
		
		$http.post("/api/search", data, {
			transformResponse: undefined
		}).
		then(function(response){
			$scope.updateTable(search);
		});
	}
	
	$scope.updateTable = function(search){
		$http.post("/api/update", search).
		then(function(response){
			$scope.content = response.data;
		});
	}
});