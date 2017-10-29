
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
        var ajaxInput={
            'id':userId,
            'password':userPwd,
            'type':"ADMIN"
        };
        $.ajax({
            url:'/account/sign/in',
            type:'POST',
            data:ajaxInput,
            success: function(result){
                alert("로그인 되었습니다.");
                window.location.href="/public/html/mainpage_login.html";
                
            },
            statusCode: {
                404: function() {
                    alert("아이디 혹은 비밀번호가 틀렸습니다.");
                }
            },
            failure : function(e){
                alert("error");
                console.log(e);
            },
            finally : function(er){
                console.log("?????이게뭐람");
                console.log(er);
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
