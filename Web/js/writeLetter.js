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
    $.ajax({
        url: '/letter/responseless',
        type: 'post',
        data: $('form').serialize(),
        success: function (result) {
            Response.sendRedirect("main.html");
        }
    });
})