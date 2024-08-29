// document.addEventListener('DOMContentLoaded', function() {
//     // Attach event listeners to all like buttons
//     document.querySelectorAll('.like').forEach(function(likeElement) {
//         likeElement.addEventListener('click', function(event) {
//             // Find the closest .fa-heart element and get its data-action
//             let heartIcon = event.target.closest('.fa-heart');
//             if (!heartIcon) return; // Exit if not a like icon
            
//             let postId = likeElement.dataset.postId;
//             let action = heartIcon.dataset.action;
             
//             console.log(postId);
//             if (action === 'like') {
//                 likePost(postId, likeElement, heartIcon);
//                 addNote(postId);
//                 console.log("Like clicked");
//             } else if (action === 'unlike') {
//                 unlikePost(postId, likeElement, heartIcon);
//                 removeNote(postId);
//             }
//         });
//     });

//     // Attach event listeners to all share buttons
//     document.querySelectorAll('.share').forEach(function(shareElement) {
//         shareElement.addEventListener('click', function(event) {
//             let postId = shareElement.dataset.postId;
//             let formData = new URLSearchParams();
//             formData.append("postId", postId);

//             fetch("/repost", {
//                 method: "POST",
//                 body: formData
//             }).then(() => {
//                 let shareIcon = shareElement.querySelector(".fa-solid");
//                 let shareNum = shareElement.querySelector('.sharesNum');
//                 if (shareIcon) {
//                     shareIcon.style.color = "rgb(29, 221, 155)";
//                 }
//                 if (shareNum) {
//                     shareNum.style.color = "rgb(29, 221, 155)";
//                     let numberText = shareNum.textContent;
//                     shareNum.textContent = parseInt(numberText) + 1;
//                     let statisticsElement = shareElement.closest('.post').querySelector('.statistcsNum');
//                     if (statisticsElement) {
//                         let currentSts = parseInt(statisticsElement.textContent);
//                         currentSts++;
//                         statisticsElement.textContent = currentSts;
//                     }
//                 }
//             });
//         });
//     });
// });

// function likePost(postId, likeElement, heartIcon) {
//     let likesElement = likeElement.querySelector('.likesNum');
//     let currentLikes = parseInt(likesElement.textContent);

//     // Select the statistics element relative to the current post
//     let statisticsElement = likeElement.closest('.post').querySelector('.statistcsNum');
//     let currentSts = parseInt(statisticsElement.textContent);

//     heartIcon.classList.remove('fa-regular', 'fa-heart');
//     heartIcon.classList.add('fa-solid', 'fa-heart');
//     heartIcon.style.color = "rgb(221, 29, 155)";

//     currentLikes++;
//     currentSts++;
//     likesElement.textContent = currentLikes;
//     statisticsElement.textContent = currentSts;

//     let formData = new URLSearchParams();
//     formData.append('postId', postId);

//     fetch("/post/addLike", {
//         method: "PUT",
//         headers: {
//             'Content-Type': 'application/x-www-form-urlencoded'
//         },
//         body: formData
//     })
//     .then(response => response.json())
//     .then(data => {
//         console.log('Post liked successfully:', data);
//     })
//     .catch(error => {
//         console.error('Error adding like:', error);
//     });

//     heartIcon.dataset.action = 'unlike';
// }

// function unlikePost(postId, likeElement, heartIcon) {
//     let likesElement = likeElement.querySelector('.likesNum');
//     let currentLikes = parseInt(likesElement.textContent);

//     // Select the statistics element relative to the current post
//     let statisticsElement = likeElement.closest('.post').querySelector('.statistcsNum');
//     let currentSts = parseInt(statisticsElement.textContent);

//     heartIcon.classList.remove('fa-solid', 'fa-heart');
//     heartIcon.classList.add('fa-regular', 'fa-heart');
//     heartIcon.style.color = "rgb(113, 118, 123)";

//     currentLikes--;
//     currentSts--;
//     likesElement.textContent = currentLikes;
//     statisticsElement.textContent = currentSts;

//     let formData = new URLSearchParams();
//     formData.append('postId', postId);

//     fetch("/post/removelike", {
//         method: "PUT",
//         headers: {
//             'Content-Type': 'application/x-www-form-urlencoded'
//         },
//         body: formData
//     })
//     .then(response => response.json())
//     .then(data => {
//         console.log('Post disliked successfully:', data);
//     })
//     .catch(error => {
//         console.error('Error removing like:', error);
//     });

//     heartIcon.dataset.action = 'like';
// }

// function addNote(postId) {
//     let formData = new URLSearchParams();
//     formData.append("postId", postId);
//     fetch('/notes/addNote', {
//         method: "POST",
//         body: formData
//     })
// }

// function removeNote(postId) {
//     let formData = new URLSearchParams();
//     formData.append("postId", postId);
//     fetch('/notes/removeNoteByLike', {
//         method: "POST",
//         body: formData
//     })
// }
