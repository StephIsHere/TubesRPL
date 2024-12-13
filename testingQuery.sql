-- Drop existing tables to ensure clean slate
DROP TABLE IF EXISTS komponenNilaiSidang CASCADE;
DROP TABLE IF EXISTS sidangDosen CASCADE;
DROP TABLE IF EXISTS sidang CASCADE;
DROP TABLE IF EXISTS komponenNilai CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS gambarTTD CASCADE;

-- Create Users Table
CREATE TABLE users (
    idUser SERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nama VARCHAR(100) NOT NULL,
    peran VARCHAR(20) NOT NULL, -- admin, koord, dosen, mahasiswa
    nik VARCHAR(20),
    status BOOLEAN
);

-- Create Komponen Nilai Table
CREATE TABLE komponenNilai (
    idKomponen SERIAL PRIMARY KEY,
    namaKomponen VARCHAR(100) NOT NULL,
    bobotKomponen DECIMAL(5,2) NOT NULL,
    tipe VARCHAR(50), -- type = bobotPenguji, bobotPembimbing, main(nilai presentasi dll)
    idKoordinator INT NOT NULL,
    FOREIGN KEY (idKoordinator) REFERENCES users(idUser)
);

-- Create Sidang Table
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

-- Create Sidang Dosen Table
CREATE TABLE sidangDosen (
    idSidang INT NOT NULL,
    idUser INT NOT NULL,
    peran VARCHAR(20) NOT NULL,
    PRIMARY KEY (idSidang, idUser),
    FOREIGN KEY (idSidang) REFERENCES sidang(idSidang) ON DELETE CASCADE,
    FOREIGN KEY (idUser) REFERENCES users(idUser) ON DELETE CASCADE
);

-- Create Komponen Nilai Sidang Table
CREATE TABLE komponenNilaiSidang (
    idSidang INT NOT NULL,
    idKomponen INT NOT NULL,
    nilai INT NOT NULL,
    PRIMARY KEY (idSidang, idKomponen),
    FOREIGN KEY (idSidang) REFERENCES sidang(idSidang) ON DELETE CASCADE,
    FOREIGN KEY (idKomponen) REFERENCES komponenNilai(idKomponen) ON DELETE CASCADE
);

-- Create Gambar TTD Table
CREATE TABLE gambarTTD (
    idGambar SERIAL PRIMARY KEY,
    ttd BYTEA,
    idUser INT NOT NULL,
    FOREIGN KEY (idUser) REFERENCES users(idUser)
);

-- Insert Users
INSERT INTO users (email, password, nama, peran, nik, status)
VALUES 
('adul@unpar', 'admin', 'Adul', 'Admin', '10', true),
('agus@unpar', 'admin', 'Agus', 'Admin', '12', true),
('budi@unpar', 'koord', 'Budi', 'Koordinator', '20', true),
('barak@unpar', 'koord', 'Barak', 'Koordinator', '22', true),
('gede@unpar', 'dosen', 'Gede', 'Dosen', '30', true),
('vania@unpar', 'dosen', 'Vania', 'Dosen', '32', true),
('raymond@unpar', 'dosen', 'Raymond', 'Dosen', '34', true),
('keenan@unpar', 'dosen', 'Keenan', 'Dosen', '36', true),
('lionov@unpar', 'dosen', 'Lionov', 'Dosen', '38', true),
('steven@unpar', 'steven', 'Steven', 'Mahasiswa', '80', true),
('sebastian@unpar', 'sebastian', 'Sebastian', 'Mahasiswa', '82', true),
('olivia@unpar', 'olivia', 'Olivia', 'Mahasiswa', '84', true),
('samuel@unpar', 'samuel', 'Samuel', 'Mahasiswa', '86', true),
('eugene@unpar', 'eugene', 'Eugene', 'Mahasiswa', '88', true),
('bombom@unpar', 'bombom', 'Bombom', 'Mahasiswa', '90', true);

-- Insert Komponen Nilai
INSERT INTO komponenNilai (namaKomponen, bobotKomponen, tipe, idKoordinator)
VALUES 
('Presentasi', 20.00, 'main', 3),
('Penguasaan Materi', 25.00, 'main', 3),
('Metodologi', 15.00, 'main', 3),
('Sistematika Penulisan', 10.00, 'main', 3),
('Substansi Materi', 20.00, 'main', 3),
('Kemampuan Menjawab', 15.00, 'main', 3),
('Bobot Penguji 1', 25.00, 'bobotPenguji', 3),
('Bobot Penguji 2', 25.00, 'bobotPenguji', 3),
('Bobot Pembimbing 1', 25.00, 'bobotPembimbing', 3),
('Bobot Pembimbing 2', 25.00, 'bobotPembimbing', 3);

-- Insert Sidang
INSERT INTO sidang (jenisTA, topik, judul, tempat, tanggal, waktu, catatan, status, ttdKetuaPenguji, ttdTimPenguji, ttdPembimbing1, ttdPembimbing2, ttdMahasiswa, ttdKoordinator, idKoordinator, idMahasiswa)
VALUES
('TA1', 'Mesin Cerdas', 'Implementasi AI pada Perangkat Lunak', 'Gedung Informatika Lt. 2', '2024-12-15', '08:00:00', 'catatan sidang lengkap', 'Selesai', null, null, null, null, null, null, 3, 10),
('TA2', 'Jaringan Komputer', 'Analisis Keamanan Jaringan Berbasis Machine Learning', 'Gedung Informatika Lt. 3', '2024-12-16', '10:00:00', 'catatan sidang komprehensif', 'Berlangsung', null, null, null, null, null, null, 3, 11),
('TA1', 'Basis Data', 'Perancangan Sistem Basis Data Terdistribusi', 'Gedung Informatika Lt. 4', '2024-12-17', '14:00:00', 'catatan sidang detail', 'Terjadwal', null, null, null, null, null, null, 4, 12),
('TA2', 'Kecerdasan Buatan', 'Pengembangan Chatbot Menggunakan NLP', 'Gedung Informatika Lt. 2', '2024-12-18', '09:00:00', 'catatan sidang mendalam', 'Belum Dimulai', null, null, null, null, null, null, 4, 13),
('TA1', 'Rekayasa Perangkat Lunak', 'Metodologi Pengembangan Aplikasi Berbasis Microservices', 'Gedung Informatika Lt. 5', '2024-12-19', '11:00:00', 'catatan sidang komprehensif', 'Selesai', null, null, null, null, null, null, 3, 14),
('TA2', 'Grafika Komputer', 'Rendering 3D Menggunakan Ray Tracing', 'Gedung Informatika Lt. 1', '2024-12-20', '13:00:00', 'catatan sidang teknis', 'Berlangsung', null, null, null, null, null, null, 4, 15),
('TA1', 'Keamanan Informasi', 'Analisis Kerentanan Sistem Enkripsi Modern', 'Gedung Informatika Lt. 3', '2024-12-21', '15:00:00', 'catatan sidang mendalam tentang keamanan', 'Terjadwal', null, null, null, null, null, null, 3, 10),
('TA2', 'Internet of Things', 'Desain Sistem Monitoring Lingkungan Berbasis IoT', 'Gedung Informatika Lt. 4', '2024-12-22', '16:00:00', 'catatan sidang tentang inovasi teknologi', 'Belum Dimulai', null, null, null, null, null, null, 4, 11),
('TA1', 'Pemrosesan Citra Digital', 'Pengembangan Algoritma Pengenalan Wajah Lanjutan', 'Gedung Informatika Lt. 2', '2024-12-23', '09:30:00', 'catatan sidang tentang pemrosesan citra', 'Berlangsung', null, null, null, null, null, null, 3, 12),
('TA2', 'Komputasi Awan', 'Arsitektur Scalable untuk Layanan Cloud Computing', 'Gedung Informatika Lt. 5', '2024-12-24', '14:30:00', 'catatan sidang tentang komputasi awan', 'Selesai', null, null, null, null, null, null, 4, 13),
('TA1', 'Sistem Embedded', 'Desain Sistem Kontrol Cerdas untuk Robotika', 'Gedung Informatika Lt. 1', '2024-12-25', '11:30:00', 'catatan sidang tentang sistem embedded', 'Terjadwal', null, null, null, null, null, null, 3, 14),
('TA2', 'Machine Learning', 'Prediksi Penyakit Menggunakan Deep Learning', 'Gedung Informatika Lt. 3', '2024-12-26', '10:30:00', 'catatan sidang tentang aplikasi machine learning', 'Belum Dimulai', null, null, null, null, null, null, 4, 15);

-- Insert Sidang Dosen
INSERT INTO sidangDosen (idSidang, idUser, peran)
VALUES 
-- Sidang 1 (Steven - Mesin Cerdas)
(1, 6, 'Ketua Penguji'),   -- Gede
(1, 7, 'Penguji 1'),        -- Vania
(1, 8, 'Penguji 2'),        -- Raymond
(1, 5, 'Pembimbing 1'),    -- Gede
(1, 9, 'Pembimbing 2'),    -- Keenan

-- Sidang 2 (Sebastian - Jaringan Komputer)
(2, 7, 'Ketua Penguji'),   -- Vania
(2, 8, 'Penguji 1'),        -- Raymond
(2, 6, 'Penguji 2'),        -- Gede
(2, 5, 'Pembimbing 1'),    -- Gede
(2, 9, 'Pembimbing 2'),    -- Keenan

-- Sidang 3 (Olivia - Basis Data)
(3, 8, 'Ketua Penguji'),   -- Raymond
(3, 6, 'Penguji 1'),        -- Gede
(3, 7, 'Penguji 2'),        -- Vania
(3, 5, 'Pembimbing 1'),    -- Gede
(3, 9, 'Pembimbing 2'),    -- Keenan

-- Sidang 4 (Samuel - Kecerdasan Buatan)
(4, 9, 'Ketua Penguji'),   -- Keenan
(4, 7, 'Penguji 1'),        -- Vania
(4, 8, 'Penguji 2'),        -- Raymond
(4, 6, 'Pembimbing 1'),    -- Gede
(4, 5, 'Pembimbing 2'),    -- Gede

-- Sidang 5 (Eugene - Rekayasa Perangkat Lunak)
(5, 5, 'Ketua Penguji'),   -- Gede
(5, 8, 'Penguji 1'),        -- Raymond
(5, 7, 'Penguji 2'),        -- Vania
(5, 9, 'Pembimbing 1'),    -- Keenan
(5, 6, 'Pembimbing 2'),    -- Gede

-- Sidang 6 (Bombom - Grafika Komputer)
(6, 9, 'Ketua Penguji'),   -- Keenan
(6, 6, 'Penguji 1'),        -- Gede
(6, 7, 'Penguji 2'),        -- Vania
(6, 8, 'Pembimbing 1'),    -- Raymond
(6, 5, 'Pembimbing 2'),    -- Gede

-- Sidang 7 (Steven - Keamanan Informasi)
(7, 7, 'Ketua Penguji'),   -- Vania
(7, 5, 'Penguji 1'),        -- Gede
(7, 6, 'Penguji 2'),        -- Gede
(7, 8, 'Pembimbing 1'),    -- Raymond
(7, 9, 'Pembimbing 2'),    -- Keenan

-- Sidang 8 (Sebastian - Internet of Things)
(8, 8, 'Ketua Penguji'),   -- Raymond
(8, 9, 'Penguji 1'),        -- Keenan
(8, 7, 'Penguji 2'),        -- Vania
(8, 6, 'Pembimbing 1'),    -- Gede
(8, 5, 'Pembimbing 2'),    -- Gede

-- Sidang 9 (Olivia - Pemrosesan Citra Digital)
(9, 6, 'Ketua Penguji'),   -- Gede
(9, 5, 'Penguji 1'),        -- Gede
(9, 9, 'Penguji 2'),        -- Keenan
(9, 7, 'Pembimbing 1'),    -- Vania
(9, 8, 'Pembimbing 2'),    -- Raymond

-- Sidang 10 (Samuel - Komputasi Awan)
(10, 5, 'Ketua Penguji'),  -- Gede
(10, 8, 'Penguji 1'),       -- Raymond
(10, 9, 'Penguji 2'),       -- Keenan
(10, 7, 'Pembimbing 1'),   -- Vania
(10, 6, 'Pembimbing 2'),   -- Gede

-- Sidang 11 (Eugene - Sistem Embedded)
(11, 7, 'Ketua Penguji'),  -- Vania
(11, 9, 'Penguji 1'),       -- Keenan
(11, 5, 'Penguji 2'),       -- Gede
(11, 8, 'Pembimbing 1'),   -- Raymond
(11, 6, 'Pembimbing 2'),   -- Gede

-- Sidang 12 (Bombom - Machine Learning)
(12, 8, 'Ketua Penguji'),  -- Raymond
(12, 6, 'Penguji 1'),       -- Gede
(12, 7, 'Penguji 2'),       -- Vania
(12, 5, 'Pembimbing 1'),   -- Gede
(12, 9, 'Pembimbing 2');   -- Keenan

-- Insert Komponen Nilai Sidang
INSERT INTO komponenNilaiSidang (idSidang, idKomponen, nilai)
VALUES 
(1, 1, 85),  -- Presentasi
(1, 2, 90),  -- Penguasaan Materi
(1, 3, 88),  -- Metodologi
(1, 4, 87),  -- Sistematika Penulisan
(1, 5, 92),  -- Substansi Materi
(1, 6, 89),  -- Kemampuan Menjawab
(1, 7, 88),  -- Bobot Penguji 1
(1, 8, 87),  -- Bobot Penguji 2
(1, 9, 90),  -- Bobot Pembimbing 1
(1, 10, 91); -- Bobot Pembimbing 2

-- Insert Gambar TTD
INSERT INTO gambarTTD (ttd, idUser)
VALUES 
(null, 1),   -- Admin Adul
(null, 6),   -- Dosen Gede
(null, 10);  -- Mahasiswa Steven