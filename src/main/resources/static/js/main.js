$(document).ready(function () {
    $("#search-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        search();
    });

});

function search() {

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
            $('#feedback').html(data);
            $("#btn-search").prop("disabled", false);

        }
    });

}