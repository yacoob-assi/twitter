const searchInput = document.getElementById('searchInput');
const resultsContainer = document.getElementById('searchResults');

document.addEventListener('DOMContentLoaded', () => {
    

    // Debounce function to limit the rate of function execution
    function debounce(func, wait) {
        let timeout;
        return function(...args) {
            clearTimeout(timeout);
            timeout = setTimeout(() => func.apply(this, args), wait);
        };
    }

    // Function to fetch search results
    function fetchSearchResults(query) {
        resultsContainer.innerHTML = '';
        if (!query) {
            resultsContainer.classList.add("hidden");
            return;
        } else {
            resultsContainer.classList.remove("hidden");
        }
    
        fetch(`/search?query=${encodeURIComponent(query)}`)
            .then(response => response.json())
            .then(users => {
                users.forEach(user => {
                    const userEle = document.createElement('a');
                    userEle.href = `/userprofile/${user.userId}`;
                    
                    // Use backticks for template literals
                    userEle.innerHTML = `
                        <div>
                            <div class="image" style="background-image: url('/user/image/${user.userId}')"></div>
                            <div class="info">
                                <span>${user.name}</span>
                                <span>@${user.tag}</span>
                            </div>
                        </div>
                    `;
                    
                    // Append the <a> element to the resultsContainer
                    resultsContainer.appendChild(userEle);
                });
            })
            .catch(error => {
                console.error('Error fetching search results:', error);
                resultsContainer.innerHTML = '<p>Error fetching results. Please try again.</p>';
            });
    }
    
    // Attach event listener to the search input with debounce
    searchInput.addEventListener('input', debounce(event => {
        fetchSearchResults(event.target.value);
    }, 300)); // Adjust debounce delay as needed
});


document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('searchInput');
    const clearButton = document.getElementById('clearButton');
    const resultsContainer = document.getElementById('searchResults');

    clearButton.addEventListener('click', () => {
        searchInput.value = ''; // Clear the text input
        resultsContainer.classList.add("hidden");
    });
   

});

document.addEventListener('click', (event) => {
    // Check if the click is outside the search input and results container
    if (!searchInput.contains(event.target) && !searchResults.contains(event.target)) {
        resultsContainer.classList.add("hidden");
    }
});

searchInput.addEventListener('click',(event)=>{
if(searchInput.value!=="") resultsContainer.classList.remove("hidden");

});