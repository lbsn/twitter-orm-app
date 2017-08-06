var tweetApp = angular.module("tweetApp", []);

/**
 * App controller
 */
tweetApp.controller("appCtrl", function($scope, $http){
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
			var tweets = response.data['tweets'];
			
			// Convert sentiment from int to string
			angular.forEach(tweets, function(tweet, index){
				switch(tweet.sentiment){
				case "0":
					tweet.sentiment = "neutral";
					break;
				case "1":
					tweet.sentiment = "positive";
					break;
				case "-1":
					tweet.sentiment = "negative";
					break;
				}					
			$scope.tweets = tweets;
			});
		});
	}
});
/**
 * Tweet card directive
*/
tweetApp.directive("tweetCard", function(){
	return{
		restrict:"A",
		templateUrl:"/templates/tweetCard.tpl.html"
	}
});
