package centrum;

public class trans {
	
	protected String data;
	protected String nazwaF;
	protected String nazBank;
	protected String nrK;
	protected String nazWl;
	protected double kwota;
	protected boolean sprzed;
	
	public trans(){}
	
	public trans (String dat,String wspf, String bnk, String nrk, String wl, Double kw, Boolean sprz){
		this.data=dat;
		this.nazwaF = wspf;
		this.nazBank = bnk;
		this.nrK = nrk;
		this.nazWl = wl;
		this.kwota=kw;
		this.sprzed=sprz;
	}

	public String toString(){
		return "data: "+this.data+" nazwa firmy: "+this.nazwaF+" nazwa banku: "+this.nazBank+"\nnumer karty platniczej: "+this.nrK
				+ " nazwa wlasciciela: "+this.nazWl+ " kwota: "+this.kwota+"\nsprzedaz: "+this.sprzed+"\n";
	}

}
