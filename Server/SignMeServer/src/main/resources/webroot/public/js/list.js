$( document ).ready(function() {
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

});
function goSearch(){
    $.ajax({
        url:'/letter/responseless',
        type:'get',
        data:$('#search').val(),
        success: function(result){
            for(var i=0; i<result.length();i++){
                $('tbody').append(
                    `
                        <tr>
                            <td>${i+1}</td>
                            <td>${result[i].des}</td>
                            <td>${result[i].startday}~${result[i].endday}</td>
                            <td>${result[i].writter}</td>
                        </tr>
                    `
                )      
            }       
        }
    });  
}