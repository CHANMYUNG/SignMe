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

    temp = location.href.split("?");
    data=temp[1].split(":");
    content = data[0];
    console.log(content);
    $.ajax({
        url: '/letter/response/'+content,//letterId
        type: 'GET',
        data: "",
        success: function (result) {
            result = JSON.parse(result);
            console.log(result);
            console.log(result.closeDate);
            $("#titleInput").val(result.title);
            $("#openDateInput").val(result.openDate.substring(0,10));
            $("#contentsInput").val(result.contents);  
            // document.getElementById("output").value 
            
                      
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
    $("#submit_btn").click(function(){
        var dataInput={
            'title' : $("#titleInput").val(),
            'contents' : $("#contentsInput").val(),
            'closeDate' : $("#closeDateInput").val()
        };
        console.log(dataInput);
        $.ajax({
            url: '/letter/response/'+content,
            type: 'PUT',
            data: dataInput,
            success: function (result) {
                // alert("수정완료!");
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
    })
    
})