function makearray(len){
    var itemsInput="[";
    for(var j=1;j<=len;j++){
        itemsInput+="\"";
        itemsInput+=$("#list_"+len).children('input').val();
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

    var listNum=1;
    
    temp = location.href.split("?");
    data=temp[1].split(":");
    content = data[0];
    console.log(content);
    $.ajax({
        url: '/survey/'+content,//letterId
        type: 'GET',
        data: "",
        success: function (result) {
            result = JSON.parse(result);
            console.log(result);
            console.log(result.items.length);
            $("#input_title").val(result.title);
            $("#summary").val(result.summary);
            $("#openDate").val(result.openDate.substring(0,10));
            $("#closeDate").val(result.closeDate.substring(0,10));
            $("#list_1_input").val(result.items[0]);
            listNum=result.items.length;
            for(var i=1;i<result.items.length;i++){
                $("#c_addlist").append(
                    `
                    <div id="list_${i+1}">
                        <label for="">질문 ${i+1} :</label>
                        <input id="list_${i+1}_input" type="text">
                    </div>
                    `
                );
                let idtest=i+1;
                $("#list_"+idtest+"_input").val(result.items[i]);
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
    //list number start 1
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
        $.ajax({
            url: '/survey/'+content,
            type: 'PUT',
            data: dataInput,
            success: function (result) {
                alert("수정완료!");
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
