-- BELUM BERES, perlu double check + BAP belum

DROP TABLE IF EXISTS "komponenNilaiSidang" CASCADE;
DROP TABLE IF EXISTS "sidangDosen" CASCADE;
DROP TABLE IF EXISTS "sidang" CASCADE;
DROP TABLE IF EXISTS "komponenNilai" CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;

CREATE TABLE "user" (
    "idUser" SERIAL PRIMARY KEY,
    "email" VARCHAR(100) UNIQUE NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    "nama" VARCHAR(100) NOT NULL,
    "peran" VARCHAR(20) NOT NULL, -- admin, koord, dosen, mahasiswa
    "npm" VARCHAR(20)
);

CREATE TABLE "komponenNilai" (
    "idKomponen" SERIAL PRIMARY KEY,
    "namaKomponen" VARCHAR(100) NOT NULL,
    "bobotKomponen" DECIMAL(5,2) NOT NULL,
    "tahunAkademik" VARCHAR(20) NOT NULL,
    "tipe" VARCHAR(50), -- type = bobotPenguji, bobotPembimbing, main(nilai presentasi dll)
	"idKoordinator" INT NOT NULL,
    FOREIGN KEY ("idKoordinator") REFERENCES "user"("idUser")
);

CREATE TABLE "sidang" (
    "idSidang" SERIAL PRIMARY KEY,
    "jenisTA" VARCHAR(20) NOT NULL,
    "topik" VARCHAR(100),
    "judul" VARCHAR(200),
    "tempat" VARCHAR(100),
    "waktu" TIME,
    "tanggal" DATE,
	"catatan" VARCHAR(8000),
    "status" VARCHAR(50),
    "idKoordinator" INT NOT NULL,
	"idMahasiswa" INT NOT NULL,
    FOREIGN KEY ("idKoordinator") REFERENCES "user"("idUser"),
    FOREIGN KEY ("idMahasiswa") REFERENCES "user"("idUser")
);

CREATE TABLE "sidangDosen" (
	"idSidang" INT NOT NULL,
	"idUser" INT NOT NULL,
	"peran" VARCHAR(20) NOT NULL,
	PRIMARY KEY ("idSidang", "idUser"),
	FOREIGN KEY ("idSidang") REFERENCES "sidang"("idSidang") ON DELETE CASCADE,
    FOREIGN KEY ("idUser") REFERENCES "user"("idUser") ON DELETE CASCADE
);

CREATE TABLE "komponenNilaiSidang" (
	"idSidang" INT NOT NULL,
	"idKomponen" INT NOT NULL,
	"nilai" INT NOT NULL,
	PRIMARY KEY ("idSidang", "idKomponen"),
	FOREIGN KEY ("idSidang") REFERENCES "sidang"("idSidang") ON DELETE CASCADE,
    FOREIGN KEY ("idKomponen") REFERENCES "komponenNilai"("idKomponen") ON DELETE CASCADE
);

INSERT INTO "user" ("email", "password", "nama", "peran", "npm")
VALUES 
('admin@unpar', 'admin', 'admin1', 'admin', NULL),
('koord@unpar', 'koord', 'koord1', 'koordinator', NULL),
('dosen@unpar', 'dosen', 'gede', 'dosen', NULL),
('steven@unpar', '123', 'steven', 'mahasiswa', '84');


