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
    tipe VARCHAR(50) -- type = bobotPenguji, bobotPembimbing, main(nilai presentasi dll)
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
    status VARCHAR(50) CHECK (status IN ('Belum Dimulai', 'Berlangsung', 'Selesai')),
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
    nilai DECIMAL(5,2) NOT NULL,
    PRIMARY KEY (idSidang, idKomponen),
    FOREIGN KEY (idSidang) REFERENCES sidang(idSidang) ON DELETE CASCADE,
    FOREIGN KEY (idKomponen) REFERENCES komponenNilai(idKomponen) ON DELETE CASCADE
);

CREATE TABLE gambarTTD (
    idGambar SERIAL PRIMARY KEY,
    ttd BYTEA,
    idUser INT NOT NULL,
    FOREIGN KEY (idUser) REFERENCES users(idUser)
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
(1, 'Mesin Cerdas', 'Implementasi AI pada Perangkat Lunak', 'Gedung Informatika Lt. 2', '2024-12-12', '08:00:00', 'catatan', 'Selesai',  null, null, null, null, null, null, 1, 10),
(2, 'Sistem Informasi', 'Sistem Informasi Pegawai Kafe', 'Gedung Informatika Lt. 3', '2024-12-12', '10:00:00', 'catatan', 'Selesai',  null, null, null, null, null, null, 1, 11),
(1, 'Cybersec', 'Pemecahan Enkripsi', 'PPAG lt 6', '2024-12-17', '11:00:00', 'catatan', 'Belum Dimulai',  null, null, null, null, null, null, 1, 12),
(2, 'Deep Learning', 'Chatbot AI', 'PPAG lt 3', '2024-12-18', '09:00:00', 'catatan', 'Belum Dimulai',  null, null, null, null, null, null, 1, 13),
(1, 'Rekayasa Perangkat Lunak', 'Aplikasi Sidang TA', 'Gedung Informatika 9 Lt. 2', '2024-12-15', '7:00:00', 'catatan', 'Berlangsung',  null, null, null, null, null, null, 1, 14),
(2, 'Devops', 'Rekayasa Development Git', 'PPAG lt 12', '2024-12-15', '8:00:00', 'catatan', 'Berlangsung',  null, null, null, null, null, null, 1, 15);

INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe)
VALUES
('Ketua Tim Penguji', 0.10, 'bobot nilai'),
('Anggota Tim Penguji', 0.35, 'bobot nilai'),
('Pembimbing', 0.25, 'bobot nilai'),
('Koordinator', 0.30, 'bobot nilai'),
('Tata Tulis Laporan', 0.20, 'penguji'),
('Kelengkapan Materi', 0.10, 'penguji'),
('Pencapaian Tujuan', 0.30, 'penguji'),
('Presentasi', 0.25, 'penguji'),
('Penguasaan Materi', 0.15, 'penguji'),
('Tata Tulis Laporan', 0.25, 'pembimbing'),
('Kelengkapan Materi', 0.25, 'pembimbing'),
('Proses Bimbingan', 0.40, 'pembimbing'),
('Penguasaan Materi', 0.10, 'pembimbing'),
('Tata Tulis Laporan', 0.20, 'penguji2'),
('Kelengkapan Materi', 0.10, 'penguji2'),
('Pencapaian Tujuan', 0.30, 'penguji2'),
('Presentasi', 0.25, 'penguji2'),
('Penguasaan Materi', 0.15, 'penguji2');
('Nilai Akhir', 1, 'nilaiAkhir');

INSERT INTO sidangDosen (idSidang, idUser, peran)
VALUES
(1, 5, 'Penguji 1'),
(1, 6, 'Penguji 2'),
(1, 7, 'Pembimbing 1'),
(1, 8, 'Pembimbing 2'),

(2, 8, 'Penguji 1'),
(2, 7, 'Penguji 2'),
(2, 9, 'Pembimbing 1'),
(2, 5, 'Pembimbing 2'),

(3, 6, 'Penguji 1'),
(3, 8, 'Penguji 2'),
(3, 9, 'Pembimbing 1'),
(3, 7, 'Pembimbing 2'),

(4, 7, 'Penguji 1'),
(4, 6, 'Penguji 2'),
(4, 5, 'Pembimbing 1'),
(4, 8, 'Pembimbing 2'),

(5, 9, 'Penguji 1'),
(5, 6, 'Penguji 2'),
(5, 7, 'Pembimbing 1'),
(5, 5, 'Pembimbing 2'),

(6, 9, 'Penguji 1'),
(6, 5, 'Penguji 2'),
(6, 8, 'Pembimbing 1'),
(6, 6, 'Pembimbing 2');

INSERT INTO komponenNilaiSidang (idSidang, idKomponen, nilai)
VALUES 
(1, 1, 80),
(1, 2, 80),
(1, 3, 80),
(1, 4, 80),
(1, 5, 80),
(1, 6, 80),
(1, 7, 80),
(1, 8, 80),
(1, 9, 80),
(1, 10, 80),
(1, 11, 80),
(1, 12, 80),
(1, 13, 80),
(1, 14, 80),
(1, 15, 80),
(1, 16, 80),
(1, 17, 80),
(1, 18, 80),
(1, 19, 80);