
$(document).ready(function(){
    // console.log("here");
    temp = location.href.split("?");
    data=temp[1].split(":");
    content = data[0];
    console.log(content);
    // content=8;
    // document.write(name + " " + content);
    $.ajax({
        url: '/survey/'+content,
        type: 'GET',
        data: '',
        success: function (result) {
            result = JSON.parse(result);
            
           console.log(result.title);
           console.log(result);
           $("#input_title").html(result.title);
           $("#openDate").html(result.closeDate);
           $("#artcleshow").html(result.summary);
           for(var i=0;i<result.items.length;i++){
                $("#c_addlist").append(
                    `
                    <div id="list_${i}">
                        <label for="">질문 ${i+1} :</label>
                        <div>
                           <span>${result.items[i]}</span>
                           <span>  항목</span>
                           <span>${result.answerForms[i]}</span>
                        </div>
                    </div>
                    `
                );
           }
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
    $('#download').prop('href','http://localhost:7800//statistics/survey/'+content);
    
})