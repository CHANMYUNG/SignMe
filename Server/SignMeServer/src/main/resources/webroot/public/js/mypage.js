$(document).ready(function () {
    $.ajax({
        url:'/letter/survey',
        type:'get',
        data:$('#search').val(),
        success: function(result){
            result = JSON.parse(result);
            console.log(result);
            
            // console.log(result.length);
            for(i=0; i<result.length;i++){
                $('tbody').append(
                    `
                        <tr onclick="location.href='../html/alterservey.html?${result[i].letterNumber}'">
                            <td>${i+1}</td>
                            <td>${result[i].title}</td>
                            <td>${result[i].openDate}~${result[i].closeDate}</td>
                            <td>${result[i].writerName}</td>
                        </tr>
                    `
                )      
            }       
        },
        statusCode: {
            400: function() {
                alert("잘못된 요청");
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
    $.ajax({
        url:'/letter/responseless',
        type:'get',
        data:$('#search').val(),
        success: function(result){
            result = JSON.parse(result);
            // console.log(typeof(result));
            // console.log(result.length);
            console.log(result);
            
            for(var j=0; j<result.length;j++){
                $('tbody').append(
                    `
                        <tr onclick="location.href='../html/alterLetter.html?${result[i].letterNumber}'">
                            <td>${j+1+i}</td>
                            <td>${result[j].title}</td>
                            <td>${result[j].openDate}</td>
                            <td>${result[j].writerName}</td>
                        </tr>
                
                    `
                )      
            }       
        },
        statusCode: {
            400: function() {
                alert("잘못된 요청");
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
    $.ajax({
        url:'/letter/response',
        type:'get',
        data:$('#search').val(),
        success: function(result){
            result = JSON.parse(result);
            // console.log(typeof(result));
            // console.log(result.length);
            console.log(result);
            
            for(var j=0; j<result.length;j++){
                $('tbody').append(
                    `
                        <tr onclick="location.href='../html/alterLetterresponse.html?${result[i].letterNumber}'">
                            <td>${j+1+i}</td>
                            <td>${result[j].title}</td>
                            <td>${result[j].closeDate}</td>
                            <td>${result[j].writerName}</td>
                        </tr>
                
                    `
                )      
            }       
        },
        statusCode: {
            400: function() {
                alert("잘못된 요청");
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