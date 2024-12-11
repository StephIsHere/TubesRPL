const overlay = document.getElementById('OverlayIconUser');
const iconUser = document.getElementsByClassName('userProfile');
function showElement(){
    if(overlay.style.visibility == 'hidden'){
        overlay.style.visibility = 'visible';
    }
    else{
        overlay.style.visibility = 'hidden';
    }
}
function hideElement(){
    overlay.style.visibility = 'hidden';
}
iconUser.addEventListener('click', showElement);


let selectedUserId = null;
function showDeleteOverlay(userId) {
    selectedUserId = userId;
    const overlay = document.getElementById('deleteOverlay');
    overlay.style.visibility='visible';
}

function hideDeleteOverlay() {
    selectedUserId = null;
    const overlay = document.getElementById('deleteOverlay');
    overlay.style.visibility='hidden';
}

document.getElementById('confirmDeleteButton').addEventListener('click', function () {
    if (selectedUserId !== null) {
        window.location.href = '/home/deleted/' + selectedUserId;
    }
});
document.getElementById('denyDeleteButton').addEventListener('click',hideDeleteOverlay);
// Existing sort logic
const sortButton = document.getElementById("sortButton");
if (sortButton) {
    sortButton.addEventListener("click", function () {
    const hiddenInput = document.getElementById("sortValue");
if (hiddenInput) {
    hiddenInput.value = hiddenInput.value === "true" ? "false" : "true";
    hiddenInput.form.submit();
    }
    });
}

function showDetail(button) {
    const userId = button.getAttribute('data-id');
    const nama = button.getAttribute('data-nama');
    const email = button.getAttribute('data-email');
    const password = button.getAttribute('data-password');
    const role = button.getAttribute('data-role');
    const nik = button.getAttribute('data-nik');

    // Set data ke dalam form overlay
    document.querySelector('#detailAttAdmin input[name="id"]').value = userId;
    document.querySelector('#detailAttAdmin input[name="nama"]').value = nama;
    document.querySelector('#detailAttAdmin input[name="email"]').value = email;
    document.querySelector('#detailAttAdmin input[name="password"]').value = password;
    document.querySelector('#detailAttAdmin select[name="role"]').value = role;
    document.querySelector('#detailAttAdmin input[name="nik"]').value = nik;

    // Tampilkan overlay edit
    const editOverlay = document.getElementById('detailAttAdmin');
    if(editOverlay.style.visibility =='hidden'){
        editOverlay.style.visibility = 'visible';
    }
    else{
        editOverlay.style.visibility ='hidden';
    }
}
