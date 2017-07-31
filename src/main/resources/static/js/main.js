$(document).ready(function () {
//	setInterval("update()", 10000);
    $("#search-form").submit(function (event) {
        event.preventDefault();
        startStreaming();
    });

});

function startStreaming() {
    search_keyword = $("#keyword-control").val();
    $("#btn-search").prop("disabled", true);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/search",
        data: {keyword: search_keyword},
        dataType: 'text',
        cache: false,
        timeout: 600000,
        success: function (data) {
            update(search_keyword);
            $("#btn-search").prop("disabled", false);
        }
    });
}

function update(keyword){
	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/update",
        data: {keyword: keyword},
        dataType: 'text',
        cache: false,
        timeout: 600000,
        success: function (data) {
            update(search_keyword);
            $("#btn-search").prop("disabled", false);
        }
    });
}