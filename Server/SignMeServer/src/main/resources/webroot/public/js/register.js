function submit_chk(myForm) {
    var reg1 = /^([A-Za-z]{1,10}[0-9]{1,10}|[0-9]{1,10}[A-Za-z]{1,10})$/;
    var reg2 = /^[A-Za-z|A-Za-z0-9]{1,16}@[A-Za-z|A-Za-z0-9]{1,16}\.(com|net)$/;
    var reg3 = /^[A-Za-z|A-Za-z0-9]{1,16}$/;

    var id = myForm.login_id;
    var mail = myForm.login_mail;
    var pw = myForm.login_pwd;
    var pw_confirm = myForm.login_rpwd;

    var result1 = reg1.test(id.value);
    var result2 = reg2.test(mail.value);
    var result3 = reg3.test(pw.value);

    if (!result1) {
        alert("아이디 형식이 잘못되었습니다.");
        id.value = "";
        id.focus();
        return false;
    } else if (!result3) {
        alert("비밀번호 형식이 잘못되었습니다.");
        pw.value = "";
        pw.focus();
        return false;
    } else if (pw.value != pw_confirm.value) {
        alert("비밀번호 재확인이 잘못되었습니다.")
        pw_confirm.value = "";
        pw_confirm.focus();
        return false;
    } else if (!result2) {
        alert("이메일 형식이 잘못되었습니다.");
        mail.value = "";
        mail.focus();
        return false;
    }
}

$(document).ready(function () {
<<<<<<< HEAD
    var registerInput={};
    var check=0;
=======
>>>>>>> 48383db8f90b92525301b6ff32d6018d108bd5ac
    $("#button_id").click(function(){
        $.ajax({
            url: '/account/id/check/'+$("#login_id").val(),
            type: 'get',
            data: "",
            success: function (result) {
                alert("사용가능한 아이디입니다..");
<<<<<<< HEAD
                registerInput.id=$("#login_id").val();
            },
            statusCode: {
                400: function() {
                    alert("이미 존재하는 아이디입니다.");
                }
            },
            failure : function(e){
                console.log("error");
=======
            },
            failure : function(e){
                alert("이미 존재하는 아이디입니다.");
>>>>>>> 48383db8f90b92525301b6ff32d6018d108bd5ac
                console.log(e);
            },
            finally : function(er){
                console.log("?????이게뭐람");
                console.log(er);
            }
        })
    });
    $("#button_email").click(function(){
        $.ajax({
<<<<<<< HEAD
            url: '/account/email/check/'+$("#login_email").val(),
            type: 'get',
            data: "",
            success: function (result) {
                alert("인증되었습니다");
                registerInput.email=$("#login_email").val();  
            },
            statusCode: {
                400: function() {
                    alert("이미 존재하는 이메일입니다.");
                }
            },
            failure : function(e){
                console.log("error");
                console.log(e);
            },
            finally : function(er){
                console.log("?????이게뭐람");
                console.log(er);
            }
        })
    });
    $("#button_serial").click(function(){
        var input=""+$("#serial_1").val()+$("#serial_2").val()+$("#serial_3").val()+$("#serial_4").val();
        $.ajax({
            url: '/account/uid/check/'+input,
            type: 'get',
            data: "",
            success: function (result) {
                result = JSON.parse(result);
                alert("인증되었습니다");
                registerInput.uid=input;
                console.log(registerInput);
                $('#login_name').value = "1";
                
            },
            statusCode: {
                400: function() {
                    alert("존재하지 않는 시리얼키입니다.");
                }
            },
            failure : function(e){
                console.log("error");
                console.log(e);
            },
            finally : function(er){
                console.log("?????이게뭐람");
                console.log(er);
            }
        })
    });
    $("#submit_btn").click(function(){
        if($("#login_pwd").val()==$("#login_rpwd").val()){
            registerInput.password=$("#login_pwd").val();
           $.ajax({
                url: '/account/sign/up',
                type: 'post',
                data: registerInput,
                success: function (result) {
                    window.location.href="/public/html/registerDone.html";
                },
                statusCode: {
                    400: function() {
                        alert("모든 정보를 올바르게 입력해주세요");
                    }
                },
                failure : function(e){
                    console.log("error");
                    console.log(e);
                },
                finally : function(er){
                    console.log("error");
                    console.log(er);
                }
            }) 
        }
        
=======
            url: '/account/email/check/:'+$("#login_email").val(),
            type: 'get',
            data: "",
            success: function (result) {
                alert("인증되었습니다")
            }
        })
    });
    $("#submit_btn").click(function(){
        $.ajax({
            url: '/account/sign/up',
            type: 'post',
            data: $('form').serialize(),
            success: function (result) {
                Response.sendRedirect("../html/registerDone.html");
            }
        })
>>>>>>> 48383db8f90b92525301b6ff32d6018d108bd5ac
    })
    
})