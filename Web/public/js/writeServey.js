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
<<<<<<< HEAD
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
=======
>>>>>>> 48383db8f90b92525301b6ff32d6018d108bd5ac

$(document).ready(function(){
    //list number start 1
    var listNum=1;
    //add list 
<<<<<<< HEAD
=======
    
>>>>>>> 48383db8f90b92525301b6ff32d6018d108bd5ac
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
<<<<<<< HEAD
        var answer=make_answer_array(listNum);
        // console.log(items);
        // console.log(new Array(answer).toString());
       
        
=======
        console.log(items);
>>>>>>> 48383db8f90b92525301b6ff32d6018d108bd5ac
        // console.log($("#input_title").val());
        
        var dataInput={
            'title' : $("#input_title").val(),
            'summary' : $("#summary").val(),
            'items' : new Array(items).toString(),
            // 'openDate' : $("#openDate").val(),
<<<<<<< HEAD
            'closeDate' : $("#closeDate").val(),
            'answerForms' : new Array(answer).toString()
=======
            'closeDate' : $("#closeDate").val()
>>>>>>> 48383db8f90b92525301b6ff32d6018d108bd5ac
        };
        $.ajax({
            url: '/survey',
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
    });
})
