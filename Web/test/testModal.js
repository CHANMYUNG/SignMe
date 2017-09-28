// $( document ).ready(function() {
//     $('.trigger').click(function() {
//        $('.modal-wrapper').toggleClass('open');
//       $('.page-wrapper').toggleClass('blur');
//        return false;
//     });
//   });

let btn = document.getElementById('btn');
btn.addEventListener('click', () => {
    let form = document.getElementById('form');
    form.style.display = "block";
});