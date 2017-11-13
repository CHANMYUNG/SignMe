$(document).ready(function(){
    $('#btn-serarch').click(function(){

        $.ajax({
            url: '/task',
            type: 'GET',
            data: data,
            success: function(result){
                var len=result.length;
                var event='';
                var startText="{events:[{";
                for(var i=0; i<len; i++) {
                    
                    var eventInput;
                    var write='';
                    
                    if(result[i].writeUid==1){
                        write='}],color:"#ff0000",textcolor:"#ffffff';
                    }else if(result[i].writeUid==2){
                        write='}],color:"#888888",textcolor:"#ffffff';
                    }else if(result[i].writeUid==3){
                        write='}],color:"#000000",textcolor:"#ffffff';
                    }

                    eventInput=startText.concat("title:",result[i].title,",","start:",result[i].startDate,",","end:",result[i].endDate,write);
                    event=event.concat('},',eventInput);
                }
                

            },
                error: function(xhr, status){
                alert('error occurred!!');
            }
        });
    });
})
