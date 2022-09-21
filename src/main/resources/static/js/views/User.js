// US6-C: Create client-side User view
import CreateView from "../createView.js"

let prep;
export default function prepareUserHTML(props) {
	prep = props.prep;

	const userPostHTML = createPostHTML(prep);

	return `
        <h1>User Info</h1>
        <h2>${props.prep.username}</h2>
        <h2>${props.prep.email}</h2>
        
        <form>
            <label for="oldPassword">Please enter your current password</label>
            <input type="password" id="oldPassword" name="oldPassword">
            <br>
            <label for="newPassword">New password</label>
            <input type="password" id="newPassword" name="newPassword">
            <br>
            <label for="confirmPassword">Confirm new password</label>
            <input type="password" id="confirmPassword" name="confirmPassword">
            
            <br>
            <button id="togglePasswordVisibility" name="togglePasswordVisibility">Show Password?</button>
            <button id="submitPassword" name="submitPassword">Save New Password</button>
        </form>
        
        <hr>
        ${userPostHTML}
    `;
}

function createPostHTML(user) {
	let html = `
		<tbody class = "table">
		<thead>
		<tr>
			<th scope = "col"> Title </th>
			<th scope = "col"> Content </th>
		</tr>
		</thead>
		<tbody>
	`;

	// Add a row to the table for each user post
	if(user.posts) {
		for(let i = 0; i < user.posts.length; i++) {
			const post = user.post[i];
			html += `
			<tr>
			<td>${post.title}</td>
			<td>${post.content}</td>
			</tr>
			`;
		}
	}

	html += `
	</tbody>
	</table>
	`;
	return html;
}

// US6-E: Implement the client-side ability to update the user's password
export function prepareUserJS() {
	submitPasswordHandler();
	togglePasswordHandler();
}

function submitPasswordHandler() {
	const btn = document.querySelector("#submitPassword");
	btn.addEventListener("click", function (e) {
		// Grab password field values
		const oldPasswordField = document.querySelector("#oldPassword");
		const newPasswordField = document.querySelector("#newPassword");
		const confirmPasswordField = document.querySelector("#confirmPassword");

		const oldPassword = oldPasswordField.value;
		const newPassword = newPasswordField.value;
		const confirmPassword = confirmPasswordField.value

		const url = `${USER_API_BASE_URL}/${me.id}/submitPassword?oldPassword=${oldPassword}&newPassword=${newPassword}`

		fetch(url, request)
			.then(function(response) {
				CreateView("/");
			});
	});
}

function togglePasswordHandler() {
	const btn = document.querySelector("#togglePasswordVisibility");
	btn.addEventListener("click", function(event) {
		// Grab reference to confirm password
		const oldPassword = document.querySelector("#oldPassword");
		const newPassword = document.querySelector("#newPassword");
		const confirmPassword = document.querySelector("#confirmPassword");
		if(confirmPassword.getAttribute("type") === "password") {
			confirmPassword.setAttribute("type", "text");
			oldPassword.setAttribute("type", "text");
			newPassword.setAttribute("type", "text");
		} else {
			confirmPassword.setAttribute("type", "password");
			oldPassword.setAttribute("type", "password");
			newPassword.setAttribute("type", "password");
		}
	});
}