$(document).ready(function(){
                    //list number start 1
                    var listNum=1;
                    //add list 
                    $("#addBtn").click(function(){
                        listNum++;
                        $("#form_bd").append(
                            `
                            <div class="form_bd_list" id="form_bd_list_${listNum}">
                                <div class="form_title form_bd_list_title leftbox">
                                    <span class="num_list">${listNum}</span>
                                    <input type="text" placeholder="항목을 입력하세요">
                                </div>
                                <div class="form_check form_bd_list_check leftbox">
                                    <input type="radio">
                                </div>
                                <div class="form_check form_bd_list_check leftbox">
                                    <input type="radio">
                                </div>
                                <div class="form_check form_bd_list_check leftbox">
                                    <input type="radio">
                                </div>
                                <div class="form_check form_bd_list_check leftbox">
                                    <input type="radio">
                                </div>
                                <div class="form_check form_bd_list_check leftbox">
                                    <input type="radio">
                                </div>
                                <span class="clearbox"></span>
                            </div>`
                              
                        );
                    });
                    //remove list 
                    $("#removeBtn").click(function(){
                        if(listNum==1){
                            alert("1이 마지막이에요");
                            return 0;
                        }
                        var parent = document.getElementById("form_bd");
                        var child = document.getElementById("form_bd_list_"+listNum);
                        parent.removeChild(child);
                        listNum--;
                    });

                });