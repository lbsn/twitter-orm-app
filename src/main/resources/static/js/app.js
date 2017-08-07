var tweetApp = angular.module("tweetApp", []);

/**
 * App controller
 */
tweetApp.controller("appCtrl", function($scope, $http, $filter){
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
			$scope.sentKeyword = $scope.keyword;
			setInterval(
				$scope.updateTable(search),
				5000
			);
		});
	}
	
	/* Update tweet table */
	$scope.updateTable = function(search){
		$http.post("/api/update", search).
		then(function(response){
			var tweets = response.data['tweets'];
			var users = response.data['users'];
			
			angular.forEach(tweets, function(tweet, index){
				// Convert sentiment from int to string
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
				
				// Add user info to each tweet
				tweet.user = $filter('filter')(users, {id: tweet.userId});
			});
			$scope.tweets = tweets;
		});
	}
});

/**
 * Test tweetList controller
 */
tweetApp.controller("tweetListCtrl", function($scope){
	var parentTweets = $scope.tweets;
	var parentUsers = $scope.users;
	var childTweets = [];
	angular.forEach(parentTweets, function(tweet, index){
		var item = {};
		item.tweet = tweet;
		item.user = $filter('filter')(parentUsers, {id: tweet.userId});
		this.push(item);
	}, childTweets);
	$scope.childTweets = childTweets;
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

/**
 * User card directive
*/
tweetApp.directive("userCard", function(){
	return{
		restrict:"A",
		templateUrl:"/templates/userCard.tpl.html"
		
	}
});

/**
 * A directive to alias a scope property (user for $scope.tweet.user[0].profile)
 * Thanks to: https://stackoverflow.com/a/32051955
 */
tweetApp.directive('alias', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var splits = attrs['alias'].trim().split(/\s+as\s+/);
            scope.$watch(splits[0], function(val) {
                scope.$eval(splits[1]+'=('+splits[0]+')');
            });
        }
    };
});
