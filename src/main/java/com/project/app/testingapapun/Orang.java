package com.project.app.testingapapun;

public class Orang {
    private String nama;
    private Pekerjaan pekerjaan;

    public Orang(String nama, Pekerjaan pekerjaan) {
        this.nama = nama;
        this.pekerjaan = pekerjaan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Pekerjaan getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(Pekerjaan pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    @Override
    public String toString() {
        return "Orang{" +
                "nama='" + nama + '\'' +
                ", pekerjaan=" + pekerjaan +
                '}';
    }
}
