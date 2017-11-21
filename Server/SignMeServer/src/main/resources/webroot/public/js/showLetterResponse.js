
$(document).ready(function(){
    temp = location.href.split("?");
    data=temp[1].split(":");
    content = data[0];
    console.log(content);
    // content=int.parse(content);
    // document.write(name + " " + content);
    $.ajax({
        url: '/letter/response/'+content,
        type: 'GET',
        data: '',
        success: function (result) {
            result = JSON.parse(result);
            
           console.log(result.title);
           console.log(result);
           $("#titleshow").html(result.title);
           $("#dateshow").html(result.openDate);               
           $("#artcleshow").html(result.contents);
        },
        statusCode: {
            400: function() {
                alert("잘못된 요청입니다.");
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