$( document ).ready(function() {
    var i=0;
    var j=0;
    var k=0;
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
                        <tr onclick="location.href='../html/showServey.html?${result[i].letterNumber}'">
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
            
            for(j=0; j<result.length;j++){
                $('tbody').append(
                    `
                        <tr onclick="location.href='../html/showLetter.html?${result[j].letterNumber}'">
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
        data:"",
        success: function(result){
            result = JSON.parse(result);
            // console.log(typeof(result));
            // console.log(result.length);
            console.log(result);
            
            for(var k=0; k<result.length;k++){
                $('tbody').append(
                    `
                        <tr onclick="location.href='../html/showLetter.html?${result[k].letterNumber}'">
                            <td>${k+1+i+j}</td>
                            <td>${result[k].title}</td>
                            <td>${result[k].openDate}</td>
                            <td>${result[k].writerName}</td>
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
    $('.login_trigger').click(function() {
        $('.modal-wrapper').toggleClass('open');
        $('#page_wrapper').toggleClass('blur');
        return false;
    });
    $('.modal').click(function(){
        return false;
    })
    $('.modal-wrapper').click(function(){
        $('.modal-wrapper').removeClass('open');
        $('#page_wrapper').removeClass('blur');
    });
    $("#alllist").click(function(){
        $("#alllist").css("color","#000000");
        $("#letterlist").css("color","#c2c2c2");
        $("#surveylist").css("color","#c2c2c2");
        $("#letterresponselist").css("color","#c2c2c2");
        
        $("tbody").empty();
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
                            <tr onclick="location.href='../html/showservey.html?${result[i].letterNumber}'">
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
            url:'/letter/response',
            type:'get',
            data:$('#search').val(),
            success: function(result){
                result = JSON.parse(result);
                console.log(result);
                
                // console.log(result.length);
                for(i=0; i<result.length;i++){
                    $('tbody').append(
                        `
                            <tr onclick="location.href='../html/showservey.html?${result[i].letterNumber}'">
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
                            <tr onclick="location.href='../html/showLetter.html?${result[j].letterNumber}'">
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

    })
    $("#letterlist").click(function(){
        $("#letterlist").css("color","#000000");
        $("#alllist").css("color","#c2c2c2");
        $("#surveylist").css("color","#c2c2c2");
        $("#letterresponselist").css("color","#c2c2c2");
        
        $("tbody").empty();
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
                            <tr onclick="location.href='../html/showLetter.html?${result[i].letterNumber}'">
                                <td>${j+1}</td>
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
        
    })
    $("#letterresponselist").click(function(){
        $("#letterlist").css("color","#c2c2c2");
        $("#alllist").css("color","#c2c2c2");
        $("#surveylist").css("color","#c2c2c2");
        $("#letterresponselist").css("color","#000000");
        
        $("tbody").empty();
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
                            <tr onclick="location.href='../html/showLetterResponse.html?${result[j].letterNumber}'">
                                <td>${j+1}</td>
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
        
    })
    $("#surveylist").click(function(){
        $("#surveylist").css("color","#000000");
        $("#letterlist").css("color","#c2c2c2");
        $("#alllist").css("color","#c2c2c2");
        $("#letterresponselist").css("color","#c2c2c2");
        
        $("tbody").empty();
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
                            <tr onclick="location.href='../html/showservey.html?${result[i].letterNumber}'">
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
        
    })
});
// function whoIs(who){
//     if(who==1){
//         return "자바쌤";
//     }
//     else if(who==2){
//         return "문학쌤";
//     }
//     else if(who==3){
//         return "진로쌤";
//     }
// }