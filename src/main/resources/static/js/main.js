var tweetApp = angular.module("tweetApp", []);

/**
 * Tweet Controller
 */
tweetApp.controller("tweetCtrl", function($scope, $http){
	$scope.keyword;
	
	/* Start streaming for a given keyword */
	$scope.startStreaming = function(){
		var search = {};
		search["keyword"] = $scope.keyword;
		var data = JSON.stringify(search);
		
		$http.post("/api/search", data, {
			transformResponse: undefined
		}).
		then(function(response){
			setInterval(
			$scope.updateTable(search),
			5000);
		});
	}
	
	/* Update tweet table */
	$scope.updateTable = function(search){
		$http.post("/api/update", search).
		then(function(response){
			$scope.content = response.data;
		});
	}
});

/**
 * Tweet card directive
*/
tweetApp.directive("tweetCard", function(){
	return{
		restrict:"A",
		templateUrl:"/js/tweetCard.tpl.html"
	}
});
