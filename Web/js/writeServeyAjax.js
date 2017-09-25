function submit_chk(myForm) {
    var listCount=$('.tb_list').length;

    var form_title=myForm.servey_title.value;
    var form_dec=myForm.servey_dec.value;
    var form_startdate=myForm.servey_startdate.value;
    var form_enddate=myForm.servey_enddate.value;
    if (!form_title) {
        alert("제목 칸이 비어져 있습니다.");
        form_title.value = "";
        form_title.focus();
        return false;
    } 
    if (!form_dec) {
        alert("소개글 칸이 비어져 있습니다.");
        form_dec.value = "";
        form_dec.focus();
        return false;
    }
    if (!form_startdate) {
        alert("시작일이 비어져 있습니다.");
        form_startdate.value = "";
        form_startdate.focus();
        return false;
    }
    if (!form_enddate) {
        alert("종료일이 비어져 있습니다.");
        form_enddate.value = "";
        form_enddate.focus();
        return false;
    }
    for(var i=1; i<listCount; i++){
        if(!myForm.$("#servey_list_"+i).value){
            alert(i+".번째 항목이 비어져 있습니다.");
            myForm.$("#servey_list_"+i).value="";
            myForm.$("#servey_list_"+i).focus();
            return false;
        }
        
    }
}
$(document).ready(function(){
    $.ajax({
        url: '/survey',
        type: 'post',
        data: $('form').serialize(),
        success: function (result) {
            Response.sendRedirect("main.html");
        }
    });
})