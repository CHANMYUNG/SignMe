function makearray(len){
    var itemsInput="[";
    for(var j=1;j<=len;j++){
<<<<<<< HEAD
        itemsInput+="\"";
        itemsInput+=$("#list_"+j).children('input').val();
        itemsInput+="\"";        
=======
        itemsInput+="{";
        itemsInput+=$("#list_"+len).children('input').val();
        itemsInput+="}";
>>>>>>> 48383db8f90b92525301b6ff32d6018d108bd5ac
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
<<<<<<< HEAD
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
=======
        console.log(items);

        var dataInput={
            'title' : $("#title").val(),
            'summary' : $("#summary").val(),
            'items' : items,
            'openDate' : $("#openDate").val(),
            'closeDate' : $("#closeDate").val()
        };
        $.ajax({
            url: '/account/sign/up',
            type: 'post',
            data: dataInput,
            success: function (result) {
                alert("작성완료");
                Response.sendRedirect("../public/registerDone.html");
            }
        })
>>>>>>> 48383db8f90b92525301b6ff32d6018d108bd5ac
    });
})
