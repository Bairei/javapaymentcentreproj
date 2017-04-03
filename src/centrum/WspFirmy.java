package centrum;

public class WspFirmy {
	
	private String nazwaF;
	
	public WspFirmy (String naz){
		this.nazwaF=naz;
		System.out.println("nowy wspolpracownik: "+this.nazwaF);
	}
	
	public String WspToString(){
		return this.nazwaF;
	}

}
