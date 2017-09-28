// $(document).ready(function(){
//     function modal_btn(){
//         let cover= document.getElementById("modal_cover");
//         let form= document.getElementById("modal_form");
//         cover.style.display="block";
//         cover.style.zIndex=90;
//         form.style.display="block";
//         form.style.zIndex=90;
//     }
//     // let btn = document.getElementById('btn');
//     // btn.addEventListener('click', () => {
//     //     let form = document.getElementById('form');
//     //     form.style.display = "block";
//     // });
// });
$( document ).ready(function() {
    $('.trigger').click(function() {
        $('.modal-wrapper').toggleClass('open');
        // $('.page-wrapper').toggleClass('blur');
        return false;
    });
  });
