$(document).ready(function(){
	var tweetCardRender = function(data){
		var tweetDate = "Date";
		var repDim = "Reputation dimension";
		var sentiment = 1;
		// Set sentiment color
		var sentColor = "";
		if(sentiment == 1){
			sentColor = "bg-danger text-white";
		}
		if(sentiment == -1){
			sentColor = "bg-success text-white";
		}
		
		var html = "";
		html += "<div class='card'>";
		html += "<div class='card-block p-0'>";
		html += "<div class='tweet-header text-right " + sentColor + "'>";
        html += tweetDate;
        html += "</div>"; // Close header
        html += "<div class='tweet-test'>"
        html += "<p class='card-text'>" + data + "</p>";
        html += "</div>"; // Close text
        html += "<div class='tweet-footer text-muted'>";
        html += repDim;
        html += "</div>"; // Close footer
        html += "</div>"; // Close card-block
        html += "</div>"; // Close card
	}

	/* DATATABLE */
	var search = {};
	search["keyword"] = "london";
	$('#tweet-table').DataTable({
		'info':false,
		'searching':false,
		'lengthChange':false,
		'ordering':false,
		'columnDefs':[{'width':'20%', 'targets':0}],
		'ajax': {
			type: "POST",
			contentType: "application/json",
			url: "/api/update",
			data: function(d){
				d.keyword = "london";
				return JSON.stringify(d);
			},
			dataSrc: function(json){
				var result = [];
				var tweets = json.tweets;		
				return tweets;
			}
		},
		"columns": [
			{ "data": "id" },
			{ "data": "text",
			//"render": tweetCardRender
			}
			]
	});

	/* SEARCH */
	$('#btn-search').click(function(){
		var search = {};
		search["keyword"] = $("#keyword-control").val();
		$("#btn-search").prop("disabled", true);
		$.ajax({
			type: "POST",
			contentType: "application/json",
			url: "/api/search",
			data: JSON.stringify(search),
			dataType: 'text',
			cache: false,
			timeout: 600000,
			success: function (data) {
				$("#btn-search").prop("disabled", false);
			}
		});
	});

	/* UPDATE */
	$('#test-btn').click(function(){
		var search = {};
		search['keyword'] = "london";
		$.ajax({
			type: "POST",
			contentType: "application/json",
			url: "/api/update",
			data: JSON.stringify(search),
			dataType: 'json',
			cache: false,
			timeout: 600000,
			success: function (data) {
				alert(data.tweets[0].text);
			}
		});
	});
});
