function autoResize() {
    const textarea = document.getElementById('myTextarea');
    textarea.style.height = 'auto'; 
    textarea.style.height = textarea.scrollHeight + 'px'; 
}

let postButton=document.getElementById('post');
let textarea=document.getElementById("myTextarea");

let main=document.querySelector('.main');

let selectedImage="";

// postButton.addEventListener('click',()=>addPost())


 document.getElementById('imagePicker').addEventListener('click', function() {
            document.getElementById('fileInput').click();
        });

     document.getElementById('fileInput').addEventListener('change', function(event) {
            var file = event.target.files[0];
            displayImage(file);
        });


        function displayImage(file) {
            var reader = new FileReader();
            reader.onload = function(e) {
                var imagePreview = document.getElementById('imagePreview');
                imagePreview.src = e.target.result;
                selectedImage=  imagePreview.src;
                imagePreview.style.display = 'block';
            };
            reader.readAsDataURL(file);
        }

function addPost(AreaText){
  let postText=AreaText;
  let post=document.createElement('div');
  let personInfo=document.createElement('div');
  post.classList.add('post');
  personInfo.classList.add('personInfo');
  let personInfoDiv=document.createElement('div');




  personInfoDiv.appendChild(addPostSpan('image'));
  personInfoDiv.appendChild(addPostSpan('name'));
  personInfoDiv.appendChild(addPostSpan('tag'));
  personInfoDiv.appendChild(addPostSpan('date'));

  personInfo.appendChild(personInfoDiv);
  let i=document.createElement('i');
// Add the first class
i.classList.add("fa-solid");

// Add the second class
i.classList.add("fa-ellipsis");

  i.classList.add('postDots');
  personInfo.appendChild(i);
  post.appendChild(personInfo);

  let p=document.createElement('p');
  let text=document.createTextNode(postText);
  p.appendChild(text);
  post.appendChild(p);
 
  if(selectedImage!==""){
   let image=document.createElement('img');
  image.classList.add('postImage');
  image.src=selectedImage;
 post.appendChild(image);
 selectedImage="";


  }

   let postImage=document.createElement('div');
  postImage.classList.add('postImage')
  post.appendChild(postImage);

    let postStats=document.createElement('div');
  postStats.classList.add('postStats')
  post.appendChild(postStats);
  

  
 postStats.appendChild(addPostStatus("comment","fa-regular","fa-comment","commentsNum"));
 postStats.appendChild(addPostStatus("share","fa-solid","fa-arrows-rotate","sharesNum"));
 postStats.appendChild(addPostStatus("like","fa-regular", "fa-heart","likesNum"));
 postStats.appendChild(addPostStatus("statistcs","fa-solid", "fa-chart-simple","statistcsNum"));


 post.appendChild(postStats);
 main.appendChild(post);

}

function addPostSpan(classname){

  let span=document.createElement('span');
  span.classList.add(classname);
  return span;
}

function addPostStatus(type,i1,i2,span){
let toAdd=document.createElement('div');
toAdd.classList.add(type);

let i=document.createElement('i');

 i.classList.add(i1);
 i.classList.add(i2);


 toAdd.appendChild(i);

 let spanEle=document.createElement('span');
 let value=document.createTextNode("0");
 spanEle.appendChild(value);
 spanEle.classList.add(span);
  toAdd.appendChild(spanEle);

  return toAdd;
}

function submitForm(event) {
    // Get the value from textarea
    let postText = textarea.value;

    // Create FormData object and append the text
    let formData = new FormData();
    formData.append('text', postText);

    // Send data to server using fetch
    fetch("/newpost", {
        method: "POST",
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
function likeplus(id, event) {
    let postElement = event.target.closest('.post');

    if (postElement) {
        let likesElement = postElement.querySelector('.likesNum');

        if (likesElement) {
            let currentLikes = parseInt(likesElement.textContent);
            currentLikes++;
            likesElement.textContent = currentLikes;

            let formData = new URLSearchParams();
            
            formData.append('postId', parseInt(id));

            fetch("/post/addLike", {
                method: "PUT",
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                console.log('Post liked successfully:', data);
            })
            .catch(error => {
                console.error('Error adding like:', error);
            });
        }
    }
}

