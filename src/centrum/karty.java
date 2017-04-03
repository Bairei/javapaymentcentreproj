package centrum;

public class karty {
	
	private double sld;
	private String nrkarty;
	
	public karty(String nr, double kwota){
		this.nrkarty=nr;
		this.sld = kwota;
		System.out.println("dodano nowa karte o numerze "+this.getNr()+" i saldzie poczatkowym "+this.getSaldo());
	}
	
	public void setSaldo (double saldo){
		this.sld=saldo;
	}
	
	public double getSaldo(){
		return this.sld;
	}
	
	public String getNr(){
		return this.nrkarty;
	}
	
	/*public void setNr(String numer){
		this.nrkarty=numer;
	}*/
	
	public void uznanie (double kwota)
	{
		this.sld+=kwota;
	}
	
	public void obciaz (double kwota)
	{
		this.sld-=kwota;
	}

}
