const overlay = document.getElementById('OverlayIconUser')
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
overlay.addEventListener('click', showElement)
