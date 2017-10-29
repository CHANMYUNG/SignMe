function makearray(len){
    var itemsInput="[";
    for(var j=1;j<=len;j++){
        itemsInput+="\"";
        itemsInput+=$("#list_"+j).children('input').val();
        itemsInput+="\"";        
        if(j!=len){
            itemsInput+=",";   
        }else{
            itemsInput+="]";
        }
    }
    return itemsInput;
}

$(document).ready(function(){
    //list number start 1
    var listNum=1;
    //add list 
    
    $("#add_btn").click(function(){
        listNum++;
        $("#c_addlist").append(
            `
            <div id="list_${listNum}">
                <label for="">질문 ${listNum} :</label>
                <input type="text">
            </div>
            `
        );
    });
    //remove list 
    $("#remove_btn").click(function(){
        if(listNum==1){
            alert("1이 마지막이에요");
            return 0;
        }
        var parent = document.getElementById("tb_bd");
        var child = document.getElementById("tb_list_"+listNum);
        parent.removeChild(child);
        listNum--;
    });

    $("#submit_btn").click(function(){
        
        var items=makearray(listNum);
        // console.log(items);
        // console.log($("#input_title").val());
        
        var dataInput={
            'title' : $("#input_title").val(),
            'summary' : $("#summary").val(),
            'items' : new Array(items).toString(),
            'openDate' : $("#openDate").val(),
            'closeDate' : $("#closeDate").val()
        };
        console.log(dataInput.items);
        $.ajax({
            url: '/survey',
            type: 'POST',
            data: dataInput,
            success: function (result) {
                alert("작성완료!");
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
    });
})
