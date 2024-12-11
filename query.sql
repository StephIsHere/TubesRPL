-- BELUM BERES, perlu double check + BAP belum

DROP TABLE IF EXISTS komponenNilaiSidang CASCADE;
DROP TABLE IF EXISTS sidangDosen CASCADE;
DROP TABLE IF EXISTS sidang CASCADE;
DROP TABLE IF EXISTS komponenNilai CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS gambarTTD CASCADE;

CREATE TABLE users (
    idUser SERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nama VARCHAR(100) NOT NULL,
    peran VARCHAR(20) NOT NULL, -- admin, koord, dosen, mahasiswa
    nik VARCHAR(20),
    status BOOLEAN
);

CREATE TABLE komponenNilai (
    idKomponen SERIAL PRIMARY KEY,
    namaKomponen VARCHAR(100) NOT NULL,
    bobotKomponen DECIMAL(5,2) NOT NULL,
    -- tahunAkademik INT NOT NULL, diilangin sementara gr2 cuma ada di sini, sidang dll ga ada jd ga kepake
    tipe VARCHAR(50), -- type = bobotPenguji, bobotPembimbing, main(nilai presentasi dll)
    idKoordinator INT NOT NULL,
    FOREIGN KEY (idKoordinator) REFERENCES users(idUser)
);

CREATE TABLE sidang (
    idSidang SERIAL PRIMARY KEY,
    jenisTA VARCHAR(10) NOT NULL,
    topik VARCHAR(100),
    judul VARCHAR(200),
    tempat VARCHAR(100),
    tanggal DATE,
    waktu TIME,
    catatan VARCHAR(10000),
    status VARCHAR(50),
    ttdKetuaPenguji BYTEA,
    ttdTimPenguji BYTEA,
    ttdPembimbing1 BYTEA,
    ttdPembimbing2 BYTEA,
    ttdMahasiswa BYTEA,
    ttdKoordinator BYTEA,
    idKoordinator INT NOT NULL,
    idMahasiswa INT NOT NULL,
    FOREIGN KEY (idKoordinator) REFERENCES users(idUser),
    FOREIGN KEY (idMahasiswa) REFERENCES users(idUser)
);

CREATE TABLE sidangDosen (
	idSidang INT NOT NULL,
    idUser INT NOT NULL,
    peran VARCHAR(20) NOT NULL,
    PRIMARY KEY (idSidang, idUser),
    FOREIGN KEY (idSidang) REFERENCES sidang(idSidang) ON DELETE CASCADE,
    FOREIGN KEY (idUser) REFERENCES users(idUser) ON DELETE CASCADE
);

CREATE TABLE komponenNilaiSidang (
	idSidang INT NOT NULL,
    idKomponen INT NOT NULL,
    nilai INT NOT NULL,
    PRIMARY KEY (idSidang, idKomponen),
    FOREIGN KEY (idSidang) REFERENCES sidang(idSidang) ON DELETE CASCADE,
    FOREIGN KEY (idKomponen) REFERENCES komponenNilai(idKomponen) ON DELETE CASCADE
);


INSERT INTO users (email, password, nama, peran, nik, status)
VALUES 
('adul@unpar', 'admin', 'Adul', 'Admin', '10',true),
('agus@unpar', 'admin', 'Agus', 'Admin', '12',true),

('budi@unpar', 'koord', 'Budi', 'Koordinator', '20',true),
('barak@unpar', 'koord', 'Barak', 'Koordinator', '22',true),

('gede@unpar', 'dosen', 'Gede', 'Dosen', '30',true),
('vania@unpar', 'dosen', 'Vania', 'Dosen', '32',true),
('raymond@unpar', 'dosen', 'Raymond', 'Dosen', '34',true),
('keenan@unpar', 'dosen', 'Keenan', 'Dosen', '36',true),
('lionov@unpar', 'dosen', 'Lionov', 'Dosen', '38',true),

('steven@unpar', 'steven', 'Steven', 'Mahasiswa', '80',true),
('sebastian@unpar', 'sebastian', 'Sebastian', 'Mahasiswa', '82',true),
('olivia@unpar', 'olivia', 'Olivia', 'Mahasiswa', '84',true),
('samuel@unpar', 'samuel', 'Samuel', 'Mahasiswa', '86',true),
('eugene@unpar', 'eugene', 'Eugene', 'Mahasiswa', '88',true),
('bombom@unpar', 'bombom', 'Bombom', 'Mahasiswa', '90', true);

INSERT INTO sidang (jenisTA, topik, judul, tempat, tanggal, waktu, catatan, status, ttdKetuaPenguji, ttdTimPenguji, ttdPembimbing1, ttdPembimbing2, ttdMahasiswa, ttdKoordinator, idKoordinator, idMahasiswa)
VALUES
(1, 'Mesin Cerdas', 'Implementasi AI pada Perangkat Lunak', 'Gedung Informatika Lt. 2', '2024-12-15', '08:00:00', 'catatan', 'Selesai',  null, null, null, null, null, null, 1, 1);

CREATE TABLE gambarTTD (
    idGambar SERIAL PRIMARY KEY,
    ttd BYTEA,
    idUser INT NOT NULL,
    FOREIGN KEY (idUser) REFERENCES users(idUser)
);