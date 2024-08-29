let fileToload=null;
window.onload = function() {
  const userId = 1; // Replace with the actual user ID you want to use
  getToFollow(userId);
};
function autoResize() {
    const textarea = document.getElementById('myTextarea');
    textarea.style.height = 'auto'; 
    textarea.style.height = textarea.scrollHeight + 'px'; 
}


function getToFollow(id) {
  fetch(`/getUsersToFollow/1`)
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));
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
            fileToload=file;
        });


        function displayImage(file) {
            var reader = new FileReader();
            reader.onload = function(e) {
                var imagePreview = document.getElementById('imagePreview');
                let image=document.createElement('img');
                image.classList.add('postImage');
                image.src= e.target.result;
                imagePreview.appendChild(image);
                // selectedImage=  imagePreview.src;
                // imagePreview.style.display = 'block';
            };
            reader.readAsDataURL(file);
        }


function submitForm(event) {
    let postText = textarea.value;
    let formData = new FormData(); // Use FormData for file uploads
    formData.append('text', postText);
    formData.append('image', fileToload);

    fetch("/newpost", {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
    
        addPostNote(data['postId']);
        location.reload();
    })
    .catch(error => {
        console.error('Error adding new post:', error);
    });
}



function addPostNote(postId){

 
  let formData = new URLSearchParams();
  formData.append("postId",postId);

  fetch("/addPostNote",{
    method:"POST",
    body:formData
  })
}

document.addEventListener('DOMContentLoaded', function() {
  fetch(`getNotesNumber`, {
      method: "GET"
  })
  .then(response => response.text())  // Use .json() if the server returns JSON
  .then(number => {
      let profileLink = document.querySelector('.leftBar > a:nth-child(2)');

      if (parseInt(number, 10) === 0) {
          profileLink.classList.add('hide-after');
      } else {
         profileLink.classList.add('show-after');
          profileLink.setAttribute('data-count', number);
          changeNotesSaw();
      }
  })
  .catch(error => console.error('Error fetching notes number:', error));
});


function changeNotesSaw( ){
  fetch('/notesUserSaw',{
    method:"POST"
  })
}



function redirectToType(type) {
  
  var url = "/?type=" + encodeURIComponent(type);
  // Navigate to the URL
  window.location.href = url;
}

 




function following(element) {
  document.getElementById("forYou").classList.remove("active");
  document.getElementById('following').classList.add("active");
  const user = element.getAttribute('data-user');
  fetch('getFollowersPosts', {
      method: 'GET'
  })
  .then(response => {
      if (!response.ok) {
          throw new Error('Network response was not ok ' + response.statusText);
      }
      return response.json();
  })
  .then(posts => {
      // Clear existing posts
     
      const postsContainer = document.getElementById('postsContainer');
      postsContainer.innerHTML = '';

      // Create and append new posts
      posts.forEach(post => {
       implementPost(post,postsContainer,user);      
    });
    postListener();

  })
  .catch(error => {
      console.error('There was a problem with the fetch operation:', error);
  });
}


function getAllPosts(element) {
    document.getElementById("forYou").classList.add("active");
    document.getElementById('following').classList.remove("active");
    const user = element.getAttribute('data-user');
    console.log(user);
    fetch('getAllPosts', {
      method: 'GET'
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok ' + response.statusText);
      }
      return response.json();
    })
    .then(posts => {
      // Clear existing posts
      const postsContainer = document.getElementById('postsContainer');
      postsContainer.innerHTML = '';
  
      // Create and append new posts
      posts.forEach(post => {
        // Create post container
       implementPost(post,postsContainer,user);
      });
      postListener();

    })
    .catch(error => {
      console.error('There was a problem with the fetch operation:', error);
    });
  }


  function implementPost(post,postsContainer,user){
    
    const postElement = document.createElement('div');
    postElement.classList.add('post');

    // Create person info div
    const personInfo = document.createElement('div');
    personInfo.classList.add('personInfo');

    // Create inner div for person info
    const personInnerDiv = document.createElement('div');

    // Create user profile link
    const userProfileLink = document.createElement('a');
    userProfileLink.href = `/userprofile/${post.user.userId}`;
    const userProfileSpan = document.createElement('span');
    userProfileSpan.classList.add('image');
    userProfileLink.appendChild(userProfileSpan);

    // Create user name link
    const userNameLink = document.createElement('a');
    userNameLink.href = '';
    userNameLink.classList.add('name');
    userNameLink.style.color = 'white';
    userNameLink.textContent = post.user.name;

    // Create tag span
    const tagSpan = document.createElement('span');
    tagSpan.classList.add('tag');
    tagSpan.textContent = post.tag || '';

    // Create date span
    const dateSpan = document.createElement('span');
    dateSpan.classList.add('date');
    dateSpan.textContent = post.date;

    // Append elements to person inner div
    personInnerDiv.appendChild(userProfileLink);
    personInnerDiv.appendChild(userNameLink);
    personInnerDiv.appendChild(tagSpan);
    personInnerDiv.appendChild(dateSpan);

    // Create ellipsis icon
    const ellipsisIcon = document.createElement('i');
    ellipsisIcon.classList.add('fa-solid', 'fa-ellipsis', 'postDots');

    // Append inner div and icon to person info div
    personInfo.appendChild(personInnerDiv);
    personInfo.appendChild(ellipsisIcon);

    // Create post text paragraph
    const postText = document.createElement('p');
    postText.textContent = post.text;

    // Create image container
    const imageContainer = document.createElement('div');
    imageContainer.style.display = 'flex';
    imageContainer.style.justifyContent = 'center';
    imageContainer.style.marginBottom = '10px';

    // Create image element
    const imageElement = document.createElement('img');
    imageElement.src = `/post/image/${post.postId}`;
    imageElement.style.width = '70%';
    imageElement.alt = 'Post Image';
    imageContainer.appendChild(imageElement);

    // Create post stats div
    const postStats = document.createElement('div');
    postStats.classList.add('postStats');

    // Create comment link and stats
    const commentLink = document.createElement('a');
    commentLink.href = `/comments/${post.postId}`;
    const commentDiv = document.createElement('div');
    commentDiv.classList.add('comment');
    const commentIcon = document.createElement('i');
    commentIcon.classList.add('fa-regular', 'fa-comment');
    const commentNum = document.createElement('span');
    commentNum.classList.add('commentsNum');
    commentNum.textContent = post.comments.length;
    commentDiv.appendChild(commentIcon);
    commentDiv.appendChild(commentNum);
    commentLink.appendChild(commentDiv);

    // Create share div and stats
    const shareDiv = document.createElement('div');
    shareDiv.classList.add('share');
    shareDiv.setAttribute('data-post-id', post.postId);
    const shareIcon = document.createElement('i');
    shareIcon.classList.add('fa-solid', 'fa-arrows-rotate');
    
 
    const shareNum = document.createElement('span');
    shareNum.classList.add('sharesNum');
    let shared = false;

    // Loop through the usersRepost array
    for (let i = 0; i < post.usersRepost.length; i++) {

        if (post.usersRepost[i] == user) {
            shared = true;
            break; // Exit the loop early if a match is found
        }
    }
    if (shared) {
      shareIcon.style.color = 'rgb(29, 221, 155)';
      shareNum.style.color = 'rgb(29, 221, 155)';
    }
    shareNum.textContent = post.repostNum;
    shareDiv.appendChild(shareIcon);
    shareDiv.appendChild(shareNum);

    // Create like div and stats
    const likeDiv = document.createElement('div');
    likeDiv.classList.add('like');
    likeDiv.setAttribute('data-post-id', post.postId);
    const likeIcon = document.createElement('i');
    likeIcon.classList.add('fa-heart');
    let liked = false;

    // Loop through the likes array
    for (let i = 0; i < post.likes.length; i++) {
      
        if (post.likes[i] == user) {
          
            liked = true;
            break; 
        }
    }
    
   
    if (liked) {
      likeIcon.classList.add('fa-solid');
    } else {
      likeIcon.classList.add('fa-regular');
    }
    console.log(liked);
    likeIcon.setAttribute('data-action', liked ? 'unlike' : 'like');
    const likeNum = document.createElement('span');
    likeNum.classList.add('likesNum');
    likeNum.textContent = post.likes.length;
    likeDiv.appendChild(likeIcon);
    likeDiv.appendChild(likeNum);

    // Create statistics div
    const statistcsDiv = document.createElement('div');
    statistcsDiv.classList.add('statistcs');
    const statistcsIcon = document.createElement('i');
    statistcsIcon.classList.add('fa-solid', 'fa-chart-simple');
    const statistcsNum = document.createElement('span');
    statistcsNum.classList.add('statistcsNum');
    statistcsNum.textContent = post.statisics; 
    statistcsDiv.appendChild(statistcsIcon);
    statistcsDiv.appendChild(statistcsNum);

    // Append all elements to postStats
    postStats.appendChild(commentLink);
    postStats.appendChild(shareDiv);
    postStats.appendChild(likeDiv);
    postStats.appendChild(statistcsDiv);

    // Append all elements to postElement
    postElement.appendChild(personInfo);
    postElement.appendChild(postText);
    postElement.appendChild(imageContainer);
    postElement.appendChild(postStats);

    // Append the post to the container
    postsContainer.appendChild(postElement);
  }
  
  



  document.addEventListener('DOMContentLoaded', postListener());

function likePost(postId, likeElement, heartIcon) {
    let likesElement = likeElement.querySelector('.likesNum');
    let currentLikes = parseInt(likesElement.textContent);

    // Select the statistics element relative to the current post
    let statisticsElement = likeElement.closest('.post').querySelector('.statistcsNum');
    let currentSts = parseInt(statisticsElement.textContent);

    heartIcon.classList.remove('fa-regular', 'fa-heart');
    heartIcon.classList.add('fa-solid', 'fa-heart');
    heartIcon.style.color = "rgb(221, 29, 155)";

    currentLikes++;
    currentSts++;
    likesElement.textContent = currentLikes;
    statisticsElement.textContent = currentSts;

    let formData = new URLSearchParams();
    formData.append('postId', postId);

    fetch("/post/addLike", {
        method: "PUT",
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        console.log('Post liked successfully:', data);
    })
    .catch(error => {
        console.error('Error adding like:', error);
    });

    heartIcon.dataset.action = 'unlike';
}

function unlikePost(postId, likeElement, heartIcon) {
    let likesElement = likeElement.querySelector('.likesNum');
    let currentLikes = parseInt(likesElement.textContent);

    // Select the statistics element relative to the current post
    let statisticsElement = likeElement.closest('.post').querySelector('.statistcsNum');
    let currentSts = parseInt(statisticsElement.textContent);

    heartIcon.classList.remove('fa-solid', 'fa-heart');
    heartIcon.classList.add('fa-regular', 'fa-heart');
    heartIcon.style.color = "rgb(113, 118, 123)";

    currentLikes--;
    currentSts--;
    likesElement.textContent = currentLikes;
    statisticsElement.textContent = currentSts;

    let formData = new URLSearchParams();
    formData.append('postId', postId);

    fetch("/post/removelike", {
        method: "PUT",
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        console.log('Post disliked successfully:', data);
    })
    .catch(error => {
        console.error('Error removing like:', error);
    });

    heartIcon.dataset.action = 'like';
}

function addNote(postId) {
    let formData = new URLSearchParams();
    formData.append("postId", postId);
    fetch('/notes/addNote', {
        method: "POST",
        body: formData
    })
}

function removeNote(postId) {
    let formData = new URLSearchParams();
    formData.append("postId", postId);
    fetch('/notes/removeNoteByLike', {
        method: "POST",
        body: formData
    })
}


function postListener(){
    
        // Attach event listeners to all like buttons
        document.querySelectorAll('.like').forEach(function(likeElement) {
            likeElement.addEventListener('click', function(event) {
                // Find the closest .fa-heart element and get its data-action
                let heartIcon = event.target.closest('.fa-heart');
                if (!heartIcon) return; // Exit if not a like icon
                
                let postId = likeElement.dataset.postId;
                let action = heartIcon.dataset.action;
                 
                console.log(postId);
                if (action === 'like') {
                    likePost(postId, likeElement, heartIcon);
                    addNote(postId);
                    console.log("Like clicked");
                } else if (action === 'unlike') {
                    unlikePost(postId, likeElement, heartIcon);
                    removeNote(postId);
                }
            });
        });
    
        // Attach event listeners to all share buttons
        document.querySelectorAll('.share').forEach(function(shareElement) {
            shareElement.addEventListener('click', function(event) {
                let postId = shareElement.dataset.postId;
                let formData = new URLSearchParams();
                formData.append("postId", postId);
    
                fetch("/repost", {
                    method: "POST",
                    body: formData
                }).then(() => {
                    let shareIcon = shareElement.querySelector(".fa-solid");
                    let shareNum = shareElement.querySelector('.sharesNum');
                    if (shareIcon) {
                        shareIcon.style.color = "rgb(29, 221, 155)";
                    }
                    if (shareNum) {
                        shareNum.style.color = "rgb(29, 221, 155)";
                        let numberText = shareNum.textContent;
                        shareNum.textContent = parseInt(numberText) + 1;
                        let statisticsElement = shareElement.closest('.post').querySelector('.statistcsNum');
                        if (statisticsElement) {
                            let currentSts = parseInt(statisticsElement.textContent);
                            currentSts++;
                            statisticsElement.textContent = currentSts;
                        }
                    }
                });
            });
        });
    }

    function hashPosts(hashText) {
        const user = document.querySelector(".active").getAttribute('data-user');
        const postsContainer = document.getElementById('postsContainer');
        postsContainer.innerHTML = '';
    
        fetch(`/getHashPosts?hashText=${encodeURIComponent(hashText)}`, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(posts => {
    
            // Create and append new posts
            posts.forEach(post => {
              implementPost(post,postsContainer,user);
            });
            postListener();

        })
        .catch(error => {
            console.error('Error fetching posts:', error);
        });
    }
    
    document.addEventListener('DOMContentLoaded', () => {
        const trends = document.querySelectorAll('.trend');
        trends.forEach(trend => trend.addEventListener('click', () => {
            const textEle = trend.querySelector('.trendName');
            let hashText = textEle.textContent;
            hashPosts(hashText);
        }));
    });
    

    document.querySelector(".postsButton").addEventListener("click",postListener);
    document.querySelector(".likesButton").addEventListener("click",postListener);
