const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl,
    updateTable: function () {
        $.get(userAjaxUrl, refreshViewTable);
    },

};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function enabled(elementEnable,id,checked) {
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + id + "/enable",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(checked),
    }).done(function () {
        elementEnable.closest("tr").attr("row-user-enable", checked);
        successNoty("Saved change enable/disable");
    }).fail(function (){
        $(elementEnable).prop("checked", !checked)
    });
}
