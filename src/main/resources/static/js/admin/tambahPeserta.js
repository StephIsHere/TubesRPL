function generate(event){
    let nama = document.getElementById("nama").value.toLowerCase();
    let email = document.getElementById("email");
    let password = document.getElementById("password");
    let peran = document.getElementById("role");
    let npm = document.getElementById("npm");

    email.value = nama+"@unpar";

    if (peran.value == "admin"){
        password.value = "admin";
        npm.value = "-";
        npm.disabled = true;
    }
    else if (peran.value == "koord"){
        password.value = "koord";
        npm.value = "-";
        npm.disabled = true;
    }
    else if (peran.value == "dosen"){
        password.value = "dosen";
        npm.value = "-";
        npm.disabled = true;
    }
    else if (peran.value == "mahasiswa"){
        password.value = nama;
        npm.value = "";
        npm.disabled = false;
    }
}

