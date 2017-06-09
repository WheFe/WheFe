$(document).ready(function() {

    $("#hot_small").click(function(event) {
        if ($("#hot_small").is(":checked")) {
            $("#hot_small_box").removeAttr("disabled");
        } else {
            $("#hot_small_box").attr("disabled", "disabled");
        }
    });

    $("#hot_medium").change(function(event) {
        if ($("#hot_medium").is(":checked")) {
            $("#hot_medium_box").removeAttr("disabled");
        } else {
            $("#hot_medium_box").attr("disabled", "disabled");
        }
    });

    $("#hot_large").change(function(event) {
        if ($("#hot_large").is(":checked")) {
            $("#hot_large_box").removeAttr("disabled");
        } else {
            $("#hot_large_box").attr("disabled", "disabled");
        }
    });

    $("#hot_none").change(function(event) {
        if ($("#hot_none").is(":checked")) {
            $("#hot_none_box").removeAttr("disabled");
        } else {
            $("#hot_none_box").attr("disabled", "disabled");
        }
    });

    $("#ice_small").change(function(event) {
        if ($("#ice_small").is(":checked")) {
            $("#ice_small_box").removeAttr("disabled");
        } else {
            $("#ice_small_box").attr("disabled", "disabled");
        }
    });

    $("#ice_medium").change(function(event) {
        if ($("#ice_medium").is(":checked")) {
            $("#ice_medium_box").removeAttr("disabled");
        } else {
            $("#ice_medium_box").attr("disabled", "disabled");
        }
    });


    $("#ice_large").change(function(event) {
        if ($("#ice_large").is(":checked")) {
            $("#ice_large_box").removeAttr("disabled");
        } else {
            $("#ice_large_box").attr("disabled", "disabled");
        }
    });

    $("#ice_none").change(function(event) {
        if ($("#ice_none").is(":checked")) {
            $("#ice_none_box").removeAttr("disabled");
        } else {
            $("#ice_none_box").attr("disabled", "disabled");
        }
    });

    $("#none_small").change(function(event) {
        if ($("#none_small").is(":checked")) {
            $("#none_small_box").removeAttr("disabled");
        } else {
            $("#none_small_box").attr("disabled", "disabled");
        }
    });

    $("#none_medium").change(function(event) {
        if ($("#none_medium").is(":checked")) {
            $("#none_medium_box").removeAttr("disabled");
        } else {
            $("#none_medium_box").attr("disabled", "disabled");
        }
    });

    $("#none_large").change(function(event) {
        if ($("#none_large").is(":checked")) {
            $("#none_large_box").removeAttr("disabled");
        } else {
            $("#none_large_box").attr("disabled", "disabled");
        }
    });

    $("#none_none").change(function(event) {
        if ($("#none_none").is(":checked")) {
            $("#none_none_box").removeAttr("disabled");
        } else {
            $("#none_none_box").attr("disabled", "disabled");
        }
    });

});
