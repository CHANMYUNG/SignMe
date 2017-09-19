$(document).ready(function(){
    $.ajax({
        url: '/account/sign/in',
        type: 'post',
        data: $('form').serialize(),
        success: function (result) {
            Response.sendRedirect("main.html");
        }
    });
})