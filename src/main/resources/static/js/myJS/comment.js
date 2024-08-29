document.getElementById('fileInput').addEventListener('change', function(event) {
    fileToload = event.target.files[0];
    console.log("Selected file:", fileToload); // Debugging info
});

function addComment(postId) {
    console.log("Function called with postId:", postId);

    let formData = new FormData();
    var textarea = document.getElementById('myTextarea');
    var text = textarea.value;

    if (fileToload) {
        formData.append('image', fileToload);
    } // No else condition needed here

    formData.append("postId", postId);
    formData.append("text", text);

    fetch('/addNewComment', {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (response.ok) {
            location.reload(); // Reload page to show the new comment
        } else {
            // Handle error response
            response.text().then(text => console.error("Error:", text));
        }
    })
    .catch(error => console.error('Fetch error:', error));
}
