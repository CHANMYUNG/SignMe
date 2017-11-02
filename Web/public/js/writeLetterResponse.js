
$(document).ready(function(){
    $("#submit_btn").click(function(){
        var form_title=$("#titleInput");
<<<<<<< HEAD
        var form_date=$("#closeDateInput");
=======
        var form_date=$("#openDateInput");
>>>>>>> 48383db8f90b92525301b6ff32d6018d108bd5ac
        var form_content=$("#contentsInput");
        // var form_req1=$("#req1");
        // var form_req2=$("#req2");
        if (!form_title.val()) {
            alert("제목 칸이 비어져 있습니다.");
            // form_title = "";
            form_title.focus();
            return false;
        } 
        else if (!form_date.val()) {
<<<<<<< HEAD
            alert("종료일이 비어져 있습니다.");
=======
            alert("작성일이 비어져 있습니다.");
>>>>>>> 48383db8f90b92525301b6ff32d6018d108bd5ac
            // form_dec= "";
            form_date.focus();
            return false;
        }
        else if (!form_content.val()) {
            alert("내용이 비어져 있습니다.");
            // form_content = "";
            form_content.focus();
            return false;
        }
        // else if (!form_req1.val()) {
        //     alert("내용이 비어져 있습니다.");
        //     // form_content = "";
        //     form_req1.focus();
        //     return false;
        // }
        // else if (!form_req2.val()) {
        //     alert("내용이 비어져 있습니다.");
        //     // form_content = "";
        //     form_req2.focus();
        //     return false;
        // }
        else {
            var dataInput={
<<<<<<< HEAD
                'title' : form_title.val(),
                'contents' : form_content.val(),
                'closeDate' : form_date.val()
                // 'agree' : form_req1.val(),
                // 'disagree' : form_req2.val()
            };
            // console.log(dataInput);
            $.ajax({
                url: '/letter/response',
=======
                'title' : form_date.val(),
                'contents' : form_content.val(),
                'openData' : form_date.val()
                // 'agree' : form_req1.val(),
                // 'disagree' : form_req2.val()
            };
            console.log(dataInput);
            $.ajax({
                url: '/letter/responseless',
>>>>>>> 48383db8f90b92525301b6ff32d6018d108bd5ac
                type: 'POST',
                data: dataInput,
                success: function (result) {
                    // alert("작성완료!");
                    window.location.href="/public/html/list.html";
                },
                statusCode: {
                    400: function() {
                        alert("입력정보를 다 채워주세요");
                    }
                },
                failure : function(e){
                    console.log("error");
                    console.log(e);
                },
                finally : function(er){
                    console.log("?????");
                    console.log(er);
                }
            });
        }
        
    })
    
})