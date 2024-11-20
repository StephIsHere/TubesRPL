const addSidang = document.getElementById('addSidangButton');
addSidang.addEventListener('click', (event) => {
    const sidangContainer = document.getElementsByClassName('sidangContainer');
    const addSidangContainer = document.getElementsByClassName('addSidangContainer');
    sidangContainer[0].classList.add('hidden');
    addSidangContainer[0].classList.remove('hidden');
});
