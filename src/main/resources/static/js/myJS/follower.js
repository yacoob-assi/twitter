document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.toFollowButton').forEach(function(toFollowButton) {
        toFollowButton.addEventListener('click', function(event) {
            event.preventDefault();

            let userId = toFollowButton.dataset.userId;
            let followerId = toFollowButton.dataset.followerId;

            let action = toFollowButton.dataset.action;

            if (action === 'unfollow') {
                toFollowButton.textContent = "Follow";
                toFollowButton.dataset.action = "follow";
                unfollow(userId, followerId);
            } else {
                toFollowButton.textContent = "Unfollow";
                toFollowButton.dataset.action = "unfollow";
                follow(userId, followerId);
            }
        });
    });
});




function follow(userId, followerId) {
    let formData = new URLSearchParams();
    formData.append('user', userId);
    formData.append('follower', followerId);
    
    fetch("/addFollower", {
        method: "POST",
        body: formData
    }).then(()=>{
        addFollowNote(followerId);
    })
}

function unfollow(userId, followerId) {
    let formData = new URLSearchParams();
    formData.append('user', userId);
    formData.append('follower', followerId);
    
    fetch("/removeFollower", {
        method: "POST",
        body: formData
    }).then(()=>{
        removeFollowNote(followerId);
    });
}

function addFollowNote(followerId){

    let formData=new URLSearchParams();
    formData.append("followerId",followerId);

    fetch("/addFollowNote",{
    method:"POST",
    body:formData

});

function removeFollowNote(followerId){

    let formData=new URLSearchParams();

    formData.append("followerId",followerId);

    fetch('/removeFollowNote',{
        method:"POST",
        body:formData
    })
}

}