function submit_chk(myForm) {
    
    var form_title=myForm.form_title.value;
    var form_date=myForm.form_date.value;
    var form_contant=myForm.form_contant.value;

    if (!form_title) {
        alert("제목 칸이 비어져 있습니다.");
        form_title.value = "";
        form_title.focus();
        return false;
    } 
    if (!form_date) {
        alert("작성일이 비어져 있습니다.");
        form_dec.value = "";
        form_dec.focus();
        return false;
    }
    if (!form_contant) {
        alert("내용이 비어져 있습니다.");
        form_startdate.value = "";
        form_startdate.focus();
        return false;
    }
}
$(document).ready(function(){
    $("#submit_btn").click(function(){
        var dataInput={
            'title' : $("#titleInput").val(),
<<<<<<< HEAD
            'contents' : $("#contentsInput").val(),
=======
            'contant' : $("#contentsInput").val(),
>>>>>>> 48383db8f90b92525301b6ff32d6018d108bd5ac
            'openData' : $("#openDateInput").val()
        };
        console.log(dataInput);
        $.ajax({
            url: '/letter/responseless',
            type: 'POST',
            data: dataInput,
            success: function (result) {
                alert("작성완료!");
<<<<<<< HEAD
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
=======
                Response.sendRedirect("../public/list.html");
>>>>>>> 48383db8f90b92525301b6ff32d6018d108bd5ac
            }
        });
    })
    
})