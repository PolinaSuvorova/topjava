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

function enabled(id,checked){
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + "enabled",
        dataType: 'json',
        data:  { id : id , enabled : checked }
        //data:  { "id" : id , "enabled" : checked }
            // data: "id=" + id +", enabled=" + checked
    }).done(function () {
        ctx.updateTable()
        successNoty("Saved change enable/disable");
    });
}
