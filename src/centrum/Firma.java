package centrum;

import java.util.*;

public class Firma extends klient {
	
	private String nazwa,krs;
	
	public Firma(){}
	
	public Firma(String naz, String kr){
		this.nazwa = naz;
		this.krs = kr;
		this.karty = new ArrayList<karty>();
		System.out.println("nowa firma: "+this.nazwa+" o numerze krs: "+this.krs);
	}
	
	public String getNumb(){
		return this.krs;
	}
	
	public String getDane(){
		return this.nazwa;
	}

}
