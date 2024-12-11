function ambilNamaMahasiswa() {
    let nik = document.getElementById("nik").value;

    if (nik.length > 0) {
        fetch(`/home/addSidang/getUserByNik?nik=${nik}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                console.log(data.nama);
                if (data.nama) {
                    document.getElementById("namaMahasiswa").value = data.nama;
                } else {
                    document.getElementById("namaMahasiswa").value = "Nama tidak ditemukan";
                }
            })
            .catch(error => {
                console.error('Error:', error);
                document.getElementById("mahasiswa").value = "Error fetching data";
            });
    } else {
        document.getElementById("mahasiswa").value = "";
    }
}

function ambilNamaPembimbingUtama() {
    let nik = document.getElementById("nikPembimbingUtama").value;

    if (nik.length > 0) {
        fetch(`/home/addSidang/getUserByNik?nik=${nik}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                console.log(data.nama);
                if (data.nama) {
                    document.getElementById("namaPembimbingUtama").value = data.nama;
                } else {
                    document.getElementById("namaPembimbingUtama").value = "Nama tidak ditemukan";
                }
            })
            .catch(error => {
                console.error('Error:', error);
                document.getElementById("namaPembimbingUtama").value = "Error fetching data";
            });
    } else {
        document.getElementById("namaPembimbingUtama").value = "";
    }
}

function ambilNamaPembimbingPendamping() {
    let nik = document.getElementById("nikPembimbingPendamping").value;

    if (nik.length > 0) {
        fetch(`/home/addSidang/getUserByNik?nik=${nik}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                console.log(data.nama);
                if (data.nama) {
                    document.getElementById("namaPembimbingPendamping").value = data.nama;
                } else {
                    document.getElementById("namaPembimbingPendamping").value = "Nama tidak ditemukan";
                }
            })
            .catch(error => {
                console.error('Error:', error);
                document.getElementById("namaPembimbingPendamping").value = "Error fetching data";
            });
    } else {
        document.getElementById("namaPembimbingPendamping").value = "";
    }
}

function ambilNamaKetuaPenguji() {
    let nik = document.getElementById("nikKetuaPenguji").value;

    if (nik.length > 0) {
        fetch(`/home/addSidang/getUserByNik?nik=${nik}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                console.log(data.nama);
                if (data.nama) {
                    document.getElementById("namaKetuaPenguji").value = data.nama;
                } else {
                    document.getElementById("namaKetuaPenguji").value = "Nama tidak ditemukan";
                }
            })
            .catch(error => {
                console.error('Error:', error);
                document.getElementById("namaKetuaPenguji").value = "Error fetching data";
            });
    } else {
        document.getElementById("namaKetuaPenguji").value = "";
    }
}

function ambilNamaAnggotaPenguji() {
    let nik = document.getElementById("nikAnggotaPenguji").value;

    if (nik.length > 0) {
        fetch(`/home/addSidang/getUserByNik?nik=${nik}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                console.log(data.nama);
                if (data.nama) {
                    document.getElementById("namaAnggotaPenguji").value = data.nama;
                } else {
                    document.getElementById("namaAnggotaPenguji").value = "Nama tidak ditemukan";
                }
            })
            .catch(error => {
                console.error('Error:', error);
                document.getElementById("namaAnggotaPenguji").value = "Error fetching data";
            });
    } else {
        document.getElementById("namaAnggotaPenguji").value = "";
    }
}