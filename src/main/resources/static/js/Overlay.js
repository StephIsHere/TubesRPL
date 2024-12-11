const overlay = document.getElementById('OverlayIconUser')
const iconUser = document.getElementsByClassName('userProfile')
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


const editOverlay = document.getElementsById('detailAttAdmin');
const editimg = document.getElementsByClassName('img-edit');
function showDetail(){
    if(editOverlay.style.visibility == 'hidden'){
        editOverlay.style.visibility = 'visible';
    }
    else{
        editOverlay.style.visibility = 'hidden';
    }
}
editimg.addEventListener('click', showDetail);
