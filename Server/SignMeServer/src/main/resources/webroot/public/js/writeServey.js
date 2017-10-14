function makearray(len){
    var itemsInput="[";
    for(var j=1;j<=len;j++){
        itemsInput+="{";
        itemsInput+=$("#list_"+len).children('input').val();
        itemsInput+="}";
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
    });
})
