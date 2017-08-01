$(document).ready(function(){
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
	        success: function (response) {
	        	
	        }
	    });
	});
});
