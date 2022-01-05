package com.example.lijekovi;

import android.graphics.Bitmap;

import java.util.BitSet;

public class Medicine {
    private String sifra;
    private String naziv;
    private String proizvodac;
    private String propisana_primjena;
    private String kolicina_na_raspolaganju;
    private String korisnik_ime;
    private String slika;
    private User user;

    public Medicine() {

    }

    public Medicine(String sifra, String naziv, String proizvodac, String propisana_primjena, String kolicina_na_raspolaganju, String slika) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.proizvodac = proizvodac;
        this.propisana_primjena = propisana_primjena;
        this.kolicina_na_raspolaganju = kolicina_na_raspolaganju;
        this.slika = slika;
    }

    public Medicine(String sifta, String naziv, User user) {
        this.sifra = sifta;
        this.naziv = naziv;
        this.korisnik_ime = user.getFullName();
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

    public String getPropisana_primjena() {
        return propisana_primjena;
    }

    public String getKolicina_na_raspolaganju() {
        return kolicina_na_raspolaganju;
    }

    public String getSlika() {
        return slika;
    }
}
