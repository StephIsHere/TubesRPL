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