$(document).ready(function(){
    $('#btn-serarch').click(function(){

        $.ajax({
            url: './bookmarks',
            type: 'GET',
            data: data,
            success: function(result){
                for (var i=0;i< result.length;i++) {
                    if (object.hasOwnProperty(result[i])) {
                        var input='';
                        input= `<div class="listContant outer" id="${i}">
                                    <div class="listNameWhen listItem">
                                        <p>${result[i].date}</p>            
                                    </div>
                                    <div class=a"listNmeText listItem">
                                        <p >${result[i].text}</p>
                                    </div>
                                    <div class="listNameWho listItem">
                                        <p>${result[i].writer}</p>
                                    </div>
                                </div>`;
                        $('#listArtcle').html(input);
                    }
                }
            
            },
                error: function(xhr, status){
                alert('error occurred!!');
            }
        });
    });
})
