$(document).ready(function(){
    $("#login_form").submit(function (e){
        e.preventDefault();
        var form_data = $('#login_form').serialize();
        form_data += "&type=ADMIN";
        console.log(form_data);
        $.ajax({
            url: 'http://192.168.1.101:8000/account/sign/in',
            headers: {
                'Access-Control-Allow-Origin':'http://192.168.1.101',
                'Content-Type' : 'application/x-www-form-urlencoded'
            },
            // header:('Access-Control-Allow-Origin: *'),
            type: 'POST',
            data: form_data,
            success: function (result) {
                console.log(result);
                alert("success");
                // Response.sendRedirect("main.html");
            },
            complete: function(data, err){
                console.log(data);
                console.log(err);
                alert("complete");
            }
        });
    })
    
})