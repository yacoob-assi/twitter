
let closeBuuttons= document.querySelectorAll('.note .close');
console.log(closeBuuttons);

for( let button of closeBuuttons ){
button.addEventListener('click',(e)=>{e.currentTarget.parentElement.remove()})
}


let main =document.querySelector('main');
