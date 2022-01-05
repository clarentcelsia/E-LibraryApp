package com.project.app.testingapapun;

import java.util.Objects;

public class Pekerjaan {
    private String nama;

    public Pekerjaan(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public String toString() {
        return "Pekerjaan{" +
                "nama='" + nama + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pekerjaan pekerjaan = (Pekerjaan) o;
        return Objects.equals(nama, pekerjaan.nama);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nama);
    }
}
