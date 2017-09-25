$(document).ready(function(){
                    //list number start 1
                    var listNum=1;
                    //add list 
                    $("#add_btn").click(function(){
                        listNum++;
                        $("tbody").append(
                            `
                            <tr class="tb_list" id="tb_list_${listNum}">
                                <td class="tb_title tb_list_title ">
                                    <div class="td_list_title_number leftbox">
                                        <span>${listNum}.</span>
                                    </div>
                                    <div class="td_list_title_input leftbox">
                                        <input id="servey_list_${listNum}" type="text">
                                    </div>
                                    <div class="clearbox"></div>
                                </td>
                                <td class="tb_check tb_list_check">
                                    <input type="radio">
                                </td>
                                <td class="tb_check tb_list_check">
                                    <input type="radio">                                            
                                </td>
                                <td class="tb_check tb_list_check">
                                    <input type="radio">
                                </td>
                                <td class="tb_check tb_list_check">
                                    <input type="radio">
                                </td>
                                <td class="tb_check tb_list_check">
                                    <input type="radio">
                                </td>
                            </tr>
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
    
});