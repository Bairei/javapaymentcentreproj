package centrum;

import java.util.*;

public class Osoba extends klient {
	
	private String pesel,imie,nazwisko;
	
	
	public Osoba(String im, String naz, String psl){
		this.imie=im;
		this.nazwisko=naz;
		this.pesel=psl;
		this.karty = new ArrayList<karty>();
		System.out.println("dodano nowa osobe, imie: "+this.imie+" nazwisko: "+this.nazwisko+" pesel: "+this.pesel);
	}
	
	public String getDane(){
		return this.imie+" "+this.nazwisko;
	}
	
	public String getNumb(){
		return this.pesel;
	}
}
	
	
