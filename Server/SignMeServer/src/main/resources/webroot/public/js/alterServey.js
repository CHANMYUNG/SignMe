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
function make_answer_array(len){
    var array="[";
    for(var i=1;i<=len;i++){
        array+="["
        for(var j=1;j<=5;j++){
    
            array+="\"";
            array+=$("#list_"+i+"_"+j).val();
            
            array+="\"";        
            if(j!=5){
                array+=",";   
            }else{
                array+="]";
            }
        }
        if(i!=len){
            array+=",";   
        }else{
            array+="]";
        }
    }
    return array;
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
            $("#list_1_1").val(result.answerForms[0][0]);
            $("#list_1_2").val(result.answerForms[0][1]);
            $("#list_1_3").val(result.answerForms[0][2]);
            $("#list_1_4").val(result.answerForms[0][3]);
            $("#list_1_5").val(result.answerForms[0][4]);
            listNum=result.items.length;
            var numtest;
            for(var i=1;i<result.items.length;i++){
                $("#c_addlist").append(
                    `
                    <div id="list_${i+1}">
                        <label for="">질문 ${i+1} :</label>
                        <input id="list_${i+1}_input" type="text">
                        <div>
                        <label for="">1.</label>
                        <input id="list_${i+1}_1" type="text">
                        <label for="">2.</label>
                        <input id="list_${i+1}_2" type="text">
                        <label for="">3.</label>
                        <input id="list_${i+1}_3" type="text">
                        <label for="">4.</label>
                        <input id="list_${i+1}_4" type="text">
                        <label for="">5.</label>
                        <input id="list_${i+1}_5" type="text">
                    </div>
                    </div>
                    `
                );
                numtest=1+i;
                $("#list_"+numtest+"_input").val(result.items[i]);
                $("#list_"+numtest+"_1").val(result.answerForms[i][0]);
                $("#list_"+numtest+"_2").val(result.answerForms[i][1]);
                $("#list_"+numtest+"_3").val(result.answerForms[i][2]);
                $("#list_"+numtest+"_4").val(result.answerForms[i][3]);
                $("#list_"+numtest+"_5").val(result.answerForms[i][4]);
                
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
                <div>
                    <label for="">1.</label>
                    <input id="list_${listNum}_1" type="text">
                    <label for="">2.</label>
                    <input id="list_${listNum}_2" type="text">
                    <label for="">3.</label>
                    <input id="list_${listNum}_3" type="text">
                    <label for="">4.</label>
                    <input id="list_${listNum}_4" type="text">
                    <label for="">5.</label>
                    <input id="list_${listNum}_5" type="text">
                </div>
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
        var answer=make_answer_array(listNum);
        
        // console.log(items);
        // console.log($("#input_title").val());
        
        var dataInput={
            'title' : $("#input_title").val(),
            'summary' : $("#summary").val(),
            'items' : new Array(items).toString(),
            // 'openDate' : $("#openDate").val(),
            'closeDate' : $("#closeDate").val(),
            'answerForms' : new Array(answer).toString()            
        };
        $.ajax({
            url: '/survey/'+content,
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
    });
})
