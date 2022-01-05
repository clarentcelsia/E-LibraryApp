package com.project.app.testingapapun;

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
}
