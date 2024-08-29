function goToProfile(userId){

    let formData = new URLSearchParams();
    formData.append('userid', userId); 
console.log(userId);
      // Send data to server using fetch
      fetch("/userprofile", {
        method: "GET", 
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        addPost(data["text"]);
        textarea.value = "";
    })
    .catch(error => {
        console.error('Error adding new post:', error);
    });
}

document.getElementById('postsButton').addEventListener('click',function(){
    document.getElementById('postSection').style.display = 'block';
    document.getElementById('likesSection').style.display = 'none';
    this.classList.add('active'); // Change color.style.color='rgb(29, 155, 240) !important';
    document.getElementById('likesButton').classList.remove('active');

})


document.getElementById('likesButton').addEventListener('click',function(){
    document.getElementById('postSection').style.display = 'none';
    document.getElementById('likesSection').style.display = 'block';
    this.classList.add('active'); // Change color.style.color='rgb(29, 155, 240) !important';
    document.getElementById('postsButton').classList.remove('active');

})

document.getElementById('imageFront').addEventListener('click', function() {
    document.getElementById('fileInput').click();
});


document.getElementById('fileInput').addEventListener('change', function(event) {
    var file = event.target.files[0];
    changeUserImage(file);
});


function changeUserImage(file){

    let formData=new FormData();
    formData.append("image",file);
    
    fetch('/changeUserImage',{
        method:"POST",
        body:formData
    });

    location.reload();


}