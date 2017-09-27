$(document).ready(function(){
    $('#btn-serarch').click(function(){

        $.ajax({
            url: '/task',
            type: 'GET',
            data: data,
            success: function(result){
                
            
            },
                error: function(xhr, status){
                alert('error occurred!!');
            }
        });
    });
})
