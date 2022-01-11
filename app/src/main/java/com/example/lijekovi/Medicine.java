package com.example.lijekovi;

import android.graphics.Bitmap;

import java.util.BitSet;

public class Medicine {
    private String sifra;
    private String naziv;
    private String proizvodac;
    private String primjena_dan;
    private String primjena_vrijeme;
    private String kolicina_na_raspolaganju;
    private String slika;

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setProizvodac(String proizvodac) {
        this.proizvodac = proizvodac;
    }

    public void setPrimjena_dan(String primjena_dan) {
        this.primjena_dan = primjena_dan;
    }

    public void setPrimjena_vrijeme(String primjena_vrijeme) {
        this.primjena_vrijeme = primjena_vrijeme;
    }

    public void setKolicina_na_raspolaganju(String kolicina_na_raspolaganju) {
        this.kolicina_na_raspolaganju = kolicina_na_raspolaganju;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public Medicine() {

    }

    public Medicine(String sifra, String naziv, String proizvodac, String primjena_dan, String primjena_vrijeme, String kolicina_na_raspolaganju, String slika) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.proizvodac = proizvodac;
        this.primjena_dan = primjena_dan;
        this.primjena_vrijeme = primjena_vrijeme;
        this.kolicina_na_raspolaganju = kolicina_na_raspolaganju;
        this.slika = slika;
    }

    public String getSifra() {
        return sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getProizvodac() {
        return proizvodac;
    }

    public String getPrimjena_dan() {
        return primjena_dan;
    }

    public String getPrimjena_vrijeme() {
        return primjena_vrijeme;
    }

    public String getKolicina_na_raspolaganju() {
        return kolicina_na_raspolaganju;
    }

    public String getSlika() {
        return slika;
    }
}
