function removeNote(noteId) {
    let formData = new URLSearchParams();
    formData.append('noteId', noteId);
  
    fetch('/notes/removeNote', {
        method: "POST",
        body: formData
    })
    .then(response => console.log(response))
   
}

document.addEventListener('DOMContentLoaded', function() {
    const closeIcons = document.querySelectorAll('.fa-xmark.close');

    closeIcons.forEach(function(icon) {
        icon.addEventListener('click', function() {
            const noteId = this.getAttribute('data-note-id');
            removeNote(noteId);
        });
    });
});


let closeBuuttons= document.querySelectorAll('.note .close');
console.log(closeBuuttons);

for( let button of closeBuuttons ){
button.addEventListener('click',(e)=>{e.currentTarget.parentElement.remove()})
}


let main =document.querySelector('main');
