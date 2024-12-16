package com.example.TubesRPL;

import com.example.TubesRPL.komponenNilai.KomponenNilai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TubesRplApplicationTests {

    private KomponenNilai komponenNilai;

    @BeforeEach
    void setUp() {
        komponenNilai = new KomponenNilai(1, "Ujian Tengah Semester", 30.0);
        System.out.println("Before each test");
    }

    @Test
    void testGetIdKomponen() {
        assertEquals(1, komponenNilai.getIdKomponen());
    }

    @Test
    void testGetNamaKomponen() {
        assertEquals("Ujian Tengah Semester", komponenNilai.getNamaKomponen());
    }

    @Test
    void testGetBobotKomponen() {
        assertEquals(30.0, komponenNilai.getBobotKomponen());
    }

    @Test
    void testSetIdKomponen() {
        komponenNilai.setIdKomponen(2);
        assertEquals(2, komponenNilai.getIdKomponen());
    }

    @Test
    void testSetNamaKomponen() {
        komponenNilai.setNamaKomponen("Ujian Akhir Semester");
        assertEquals("Ujian Akhir Semester", komponenNilai.getNamaKomponen());
    }

    @Test
    void testSetBobotKomponen() {
        komponenNilai.setBobotKomponen(40.0);
        assertEquals(40.0, komponenNilai.getBobotKomponen());
    }

    @Test
    void testObjectNotNull() {
        assertNotNull(komponenNilai);
    }
}
