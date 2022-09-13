import CreateView from "../createView.js";

let posts;

export default function PostIndex(props) {
    const postsHTML = generatePostsHTML(props.posts);
    posts = props.posts;
    return `
        <header>
            <h1>Posts Page</h1>
        </header>
        <main>
            <h3>Post Lists</h3>
            <div>
                ${postsHTML}
            </div>
            <h3>Add Post</h3>
            <form>
                <label for="title">Title</label><br>
                <input id="title" name="title" type="text" placeholder="Title...">
                <br>
                <label for="content">Content</label><br>
                <textarea id="content" name="content" rows="10" cols="50" placeholder="Content..."></textarea>
                <br>
                <button data-id="0" id="submitPost" name="submitPost" class="button btn-primary">Submit Post</button>
            </form>
        </main>
    `;
}

function generatePostsHTML(posts) {
    let postsHTML = `
        <table class = "table">
        <thead>
        <tr>
            <th scope = "col"> Title </th>
            <th scope = "col"> Content </th>
            <th scope="col">Categories</th>
            <th scope="col">Author</th>
        </tr>
        </thead>
        <tbody>
    `;

    for(let i = 0; i < posts.length; i++) {
        const post = posts[i];

        let categories = '';
        for (let j = 0; j < post.categories.length; j++) {
            if(categories !== "") {
                categories += ", ";
            }
            categories += post.categories[j].name;
        }

        postsHTML += `
        <tr>
        <td>${post.title}</td>
        <td>${post.content}</td>
        <td>${categories}</td>
        <td>${post.author.username}</td>
        <td><button data-id = ${post.id} class = "button btn-primary editPost">Edit</button></td>
        <td><button data-id = ${post.id} class = "button btn-danger deletePost">Delete</button></td>
        </tr>
        `;
    }
    postsHTML += `</tbody></table>`;
    return postsHTML;
}

export function postSetup() {
    submitHandler();
    editHandler();
    deleteHandler();
}

function editHandler() {
    // Target all edit buttons
    const editBtn = document.querySelectorAll(".editPost");
    // Add click handler to all edit buttons
    for(let i = 0; i < editBtns.length; i++) {
        editBtn[i].addEventListener("click", function (e) {
            // Get post id of the edit button
            const postID = parseInt(this.getAttribute("data-id"));
            // Edit post
            editPost(postID);
        });
    }
}

function editPost(postID) {
    // Find post in posts lists with matching ID
    const post = fetchByID(postID);
    // If match NOT found console log error
    if(!post){
        console.log("Simulation Glitch: id not found");
        return;
    }
    // If match found load data to form
    const titleField = document.querySelector("#title");
    const contentField = document.querySelector("#content")
    titleField.value = post.title;
    contentField.value = post.content;
    const saveBtn = document.querySelector("#savePost");
    saveBtn.setAttribute("data-id", postID);
}

function fetchByID(postID) {
    for (let i = 0; i < posts.length; i++) {
        if (posts[i].id === postID) {
            return posts[i];
        }
    }
    // Return falsy if NOT found
    return false;
}

function deleteHandler() {
    // Target all delete buttons
    const deleteBtn = document.querySelectorAll(".deletePost");
    // Add click handler to all delete buttons
    for (let i = 0; i < deleteBtns.length; i++) {
        deleteBtn[i].addEventListener("click", function(event) {
            // Get post id of the delete button
            const postId = this.getAttribute("data-id");
            // Delete post
            deletePost(postId);
        });
    }
}

function deletePost(postID) {
    const request = {
        method: "DELETE",
        headers: {"Content-Type": "application/json"},
    }
    const url = POST_API_BASE_URL + `/${postID}`;
    fetch(url, request)
        .then(function(response) {
            if(response.status !== 200) {
                console.log("fetch returned bad status code: " + response.status);
                console.log(response.statusText);
                return;
            }
            CreateView("/posts");
        })
}

function submitHandler() {
    // Target save button
    const submitBtn = document.querySelector("#submitPost");
    submitBtn.addEventListener("click", function (e) {
        const postID = parseInt(this.getAttribute("data-id"));
        submitPost(postID);
    });
}

function submitPost(postID) {
    // Get title and content for new/updated post
    const titleField = document.querySelector("#title");
    const contentField = document.querySelector("#content");
    // Create new/updated post object
    const post = {
        title: titleField.value,
        content: contentField.value
    }
    // Create request
    const request = {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(post)
    }
    let url = POST_API_BASE_URL;
    // Change the request and url if updating post
    if(postID > 0) {
        request.method = "PUT";
        url += `/${postID}`;
    }
    fetch(url, request)
        .then(function(response) {
           if(response.status !== 200) {
               console.log("Simulation Glitch: stale fetch")
               console.log(response.statusText);
               return;
        }
        CreateView("/posts");
    })
}