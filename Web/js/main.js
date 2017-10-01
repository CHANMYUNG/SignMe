
$( document ).ready(function() {
    //login js
    $('.login_trigger').click(function() {
        $('.modal-wrapper').toggleClass('open');
        $('#page_wrapper').toggleClass('blur');
        return false;
    });
    $('#submit_btn').click(function(){
        var userId=$('input[type="text"]').val();
        var userPwd=$('input[type="password"]').val();
        if(!userId){
            alert("아이디가 비어있습니다.");
            return false;
        }
        if(!userPwd){
            alert("비밀번호가 비어있습니다.");
            return false;
        }
        $.ajax({
            url:'',
            type:'POST',
            data:$('form').serialize(),
            success: function(result){
                Response.sendRedireect("main.html");
            }
        })

    })
    $('.modal').click(function(){
        return false;
    })
    $('.modal-wrapper').click(function(){
        $('.modal-wrapper').removeClass('open');
        $('#page_wrapper').removeClass('blur');
    })
    //menu js
    $('.menu_trigger').click(function(){
        alert("helloworld");
    })

});
