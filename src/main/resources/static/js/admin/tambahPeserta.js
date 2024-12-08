function generate(event) {
    let nama = document.getElementById("nama").value.toLowerCase();
    let email = document.getElementById("email");
    let password = document.getElementById("password");
    let peran = document.getElementById("role");
    let npm = document.getElementById("npm");

    email.value = nama + "@unpar";

    if (peran.value == "Admin") {
        password.value = "admin";
        npm.value = "-";
        npm.disabled = true;
    } else if (peran.value == "Koordinator") {
        password.value = "koord";
        npm.value = "-";
        npm.disabled = true;
    } else if (peran.value == "Dosen") {
        password.value = "dosen";
        npm.value = "-";
        npm.disabled = true;
    } else if (peran.value == "Mahasiswa") {
        password.value = nama;
        npm.value = "";
        npm.disabled = false;
    }
}

function showSuccessOverlay() {
    let overlay = document.createElement("div");
    overlay.id = "successOverlay";
    overlay.className = "overlay";

    overlay.innerHTML = `
            <div class="messageBox">
                <p>User successfully added!</p>
                <button onclick="confirmSuccess()">OK</button>
            </div>
        `;
    document.body.appendChild(overlay);
}

function confirmSuccess() {
    const overlay = document.getElementById('successOverlay');
    if (overlay) {
        overlay.remove();
    }
    const form = document.getElementById('addUserForm');
    form.submit();
}

function handleSubmit(event) {
    event.preventDefault();
    showSuccessOverlay();
}