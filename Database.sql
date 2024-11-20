CREATE TABLE User (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Nama VARCHAR(100) NOT NULL,
    Status VARCHAR(50)
);

CREATE TABLE Koordinator (
    UserId INT PRIMARY KEY,
    FOREIGN KEY (UserId) REFERENCES User(Id)
);

CREATE TABLE Mahasiswa (
    UserId INT PRIMARY KEY,
    NPM VARCHAR(20) UNIQUE NOT NULL,
    FOREIGN KEY (UserId) REFERENCES User(Id)
);

CREATE TABLE Dosen (
    UserId INT PRIMARY KEY,
    Peran VARCHAR(50) NOT NULL,
    Jabatan VARCHAR(100),
    FOREIGN KEY (UserId) REFERENCES User(Id)
);

CREATE TABLE Admin (
    UserId INT PRIMARY KEY,
    FOREIGN KEY (UserId) REFERENCES User(Id)
);

CREATE TABLE KomponenNilai (
    IdKomponen INT PRIMARY KEY AUTO_INCREMENT,
    Nama_Komponen VARCHAR(100) NOT NULL,
    Bobot_Komponen DECIMAL(5,2) NOT NULL,
    Peran VARCHAR(50) NOT NULL,
    Tahun_Akademik VARCHAR(20) NOT NULL
);

CREATE TABLE Sidang (
    IdSidang INT PRIMARY KEY AUTO_INCREMENT,
    JenisTA VARCHAR(20) NOT NULL,
    Topik VARCHAR(100),
    JudulTA VARCHAR(200),
    Tempat VARCHAR(100),
    Waktu TIME,
    Tanggal DATE,
    Status VARCHAR(50),
    KoordinatorId INT NOT NULL,
    FOREIGN KEY (KoordinatorId) REFERENCES Koordinator(UserId)
);

CREATE TABLE SidangMahasiswa (
    MahasiswaId INT NOT NULL,
    SidangId INT NOT NULL,
    PRIMARY KEY (MahasiswaId, SidangId),
    FOREIGN KEY (MahasiswaId) REFERENCES Mahasiswa(UserId),
    FOREIGN KEY (SidangId) REFERENCES Sidang(IdSidang)
);

CREATE TABLE SidangDosen (
    DosenId INT NOT NULL,
    SidangId INT NOT NULL,
    Peran VARCHAR(50) NOT NULL,
    PRIMARY KEY (DosenId, SidangId, Peran),
    FOREIGN KEY (DosenId) REFERENCES Dosen(UserId),
    FOREIGN KEY (SidangId) REFERENCES Sidang(IdSidang)
);

CREATE TABLE Nilai (
    NilaiId INT PRIMARY KEY AUTO_INCREMENT,
    SidangId INT NOT NULL,
    DosenId INT NOT NULL,
    MahasiswaId INT NOT NULL,
    IdKomponen INT NOT NULL,
    Nilai DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (SidangId) REFERENCES Sidang(IdSidang),
    FOREIGN KEY (DosenId) REFERENCES Dosen(UserId),
    FOREIGN KEY (MahasiswaId) REFERENCES Mahasiswa(UserId),
    FOREIGN KEY (IdKomponen) REFERENCES KomponenNilai(IdKomponen)
);

CREATE TABLE Catatan (
    IdCatatan INT PRIMARY KEY AUTO_INCREMENT,
    IsiCatatan TEXT NOT NULL,
    Tanggal DATE NOT NULL,
    SidangId INT NOT NULL,
    DosenId INT NOT NULL,
    MahasiswaId INT NOT NULL,
    FOREIGN KEY (SidangId) REFERENCES Sidang(IdSidang),
    FOREIGN KEY (DosenId) REFERENCES Dosen(UserId),
    FOREIGN KEY (MahasiswaId) REFERENCES Mahasiswa(UserId)
);

CREATE TABLE BAP (
    BapId INT PRIMARY KEY AUTO_INCREMENT,
    SidangId INT NOT NULL,
    StatusKoordinator BOOLEAN DEFAULT FALSE,
    StatusPenguji1 BOOLEAN DEFAULT FALSE,
    StatusPenguji2 BOOLEAN DEFAULT FALSE,
    StatusPembimbing BOOLEAN DEFAULT FALSE,
    StatusMahasiswa BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (SidangId) REFERENCES Sidang(IdSidang)
);

