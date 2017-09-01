var tweetApp = angular.module("tweetApp", ['ui.bootstrap']);
var promise;

/**
 * App controller
 */
tweetApp.controller("appCtrl", function($scope, $http, $filter, $interval){
		
	$scope.keyword = "";
	$scope.tweets = [];
	$scope.updating = false;
	
	/* Start streaming for a given keyword */
	$scope.startStreaming = function(){
		var search = {};
		search["keyword"] = $scope.keyword;
		var data = JSON.stringify(search);
		
		$http.post("/api/search", data, {
			transformResponse: undefined
		}).
		then(function(response){
			// TODO: This is probably never used
			$scope.sentKeyword = $scope.keyword;
			$scope.toggleUpdate();			
		});
	}
	
	/* Start updating tweet table */
	$scope.toggleUpdate = function(){
		// If updating, then stop
		if($scope.updating){
			$scope.stopUpdate();
		}
		
		//Else start
		else{
			$scope.startUpdate();				
		}
	}
	
	/* Stop updating tweet table */
	$scope.stopUpdate = function(){
		$interval.cancel(promise);
		$scope.updating = false;
	}
	
	/* Start updating tweet table */
	$scope.startUpdate = function(){
		promise = $interval(function(){
					$scope.updateTable()
				},
				5000
		);
		$scope.updating = true;
	}
	
	
	/* Update tweet table */
	$scope.updateTable = function(){
		var search = {};
		search["keyword"] = $scope.keyword;
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
	
	/* Get clusters */
	$scope.getClusters = function(){
		var search = {};
		search["keyword"] = $scope.keyword;
		var data = JSON.stringify(search);
		$http.post('/api/cluster', search)
		.then(function(response){
			$scope.clusters = response.data;
		});
	}
	
	/* Get tweet per id */
	$scope.getTweetPerId = function(_id){
		var tweet = $filter('filter')($scope.tweets, {id:_id})[0];
		return tweet;
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
 * Pagination controller
 */
tweetApp.controller("paginationCtrl", function($scope){
	$scope.totalItems = 0;
	$scope.itemsPerPage = 10;
	$scope.currentPage = 1;
	$scope.setPage = function (pageNo) {
		$scope.currentPage = pageNo;
	};
	$scope.$watch('tweets',function(){
		$scope.totalItems = $scope.tweets.length;
		setTweetsPerPage($scope.currentPage);
	});
	$scope.$watch('currentPage', function(){
		setTweetsPerPage($scope.currentPage);
	});

	function setTweetsPerPage(page){
		var pagedData = $scope.tweets.slice(
				(page - 1) * $scope.itemsPerPage,
				page * $scope.itemsPerPage
		);
		$scope.tweetsPerPage = pagedData;
	}
});

/**
 * Tabs controller
 */
tweetApp.controller('TabsCtrl', function ($scope, jsonResponseToClusterData) {
	$scope.switchToList = function(){
		console.log("To list");
	}

	$scope.switchToChart = function(){
		// Stop updating tweets list
		$scope.stopUpdate(promise);
		$scope.getClusters();
	}
});

/**
 * Bubble chart controller
 */
tweetApp.controller("bubbleChartCtrl", function($scope, $http, jsonResponseToClusterData){
	$scope.tweetFromChartList = [];
	
	$scope.getList = function(clusterId){
		var tweetList = [];

		$scope.clusters.forEach(function(d){
			if(d.cluster == clusterId){
				tweetList.push(d.id);
			}
		});
		$scope.tweetFromChartList = tweetList;
	};
	
	// Reset tweetFromChartList when tab is deselected
	$scope.$watch('active', function(){
		if($scope.active != 1){
			$scope.tweetFromChartList = [];
		}
	});
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

/**
 * Json to clusters service
*/
tweetApp.service("jsonResponseToClusterData", function(){
    this.getClusterData = function(data){
//        var data = JSON.parse(jsonData);
        var clusterData = [];
        clusterData.name = "Clusters";

        var clusterMap = d3.map();
        data.forEach(function(item){
          if(!clusterMap.has(item.cluster)){
            clusterMap.set(item.cluster, 1);
          }
          else{
            clusterMap.set(item.cluster, clusterMap.get(item.cluster) + 1);
          }
        });

        clusterData.children = clusterMap
          .entries()
          .map(function(d){
            d.name = d.key;
            delete d.key;
            return d;
          });
        return clusterData;
	};
});

/**
* Directive for drawing the bubble chart
*/
tweetApp.directive("bubbleChart", function(jsonResponseToClusterData, $compile){
    return{
      restrict:"A",
      link: function(scope, elem){
          scope.$watch("clusters", function(){
            if(angular.isDefined(scope.clusters)){
              var data = jsonResponseToClusterData.getClusterData(scope.clusters);
              
              /* Draw chart */
              var diameter = elem.width();
              var color = d3.scale.category20();
              
              // Remove existing svg
              d3.select("#bubbleChart").selectAll("*").remove();
              
              // Append svg element to body
              var svg = d3.select("#bubbleChart")
                .append("svg")
                .attr("width", diameter)
                .attr("height", diameter)
                .attr("class", "bubble");

              // Initialize pack layout
              var pack = d3.layout
	              .pack()
	              .size([diameter,diameter])
	              .padding(1.5);
              
              // Run pack layout and return nodes array
              var nodes = pack.nodes(data);

              // Exclude root node (has no circle element)
              nodes = nodes.filter(function(d){
                return !d.children;
              });

              // Setup the chart
              var bubbles = svg.append("g")
                 .attr("transform", "translate(0,0)")
                 .selectAll(".bubble")
                 .data(nodes)
                 .enter();

              // Create the bubbles
              bubbles.append("circle")
                .attr("r", function(d){
                  return d.r
                })
                .attr("cx", function(d){
                  return d.x;
                })
                .attr("cy", function(d){
                  return d.y;
                })
                .attr("ng-click", function(d){
                  return "getList(" + d.name + ")";
                })
                .style("fill", function(d) {
                  return color(d.value);
                });

              // Format the text for each bubble
              bubbles.append("text")
                .attr("x", function(d){
                  return d.x;
                })
                .attr("y", function(d){
                  return d.y + 5;
                })
                .attr("text-anchor", "middle")
                .text(function(d){
                  return d["value"];
                })
                .style({
                  "fill":"white", 
                  "font-family":"Helvetica Neue, Helvetica, Arial, san-serif",
                  "font-size": "12px"
                });

              /* Compile */
              $compile(elem.contents())(scope);
            }
          });
        }
    }
    
  });

