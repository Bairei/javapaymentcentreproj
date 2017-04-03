package centrum;

import java.util.*;

public abstract class klient {
	
	protected ArrayList<karty> karty;
	
	public void dodajKarta(karty karta){
		this.karty.add(karta);
	}
	
	public ArrayList<karty> getKarta(){
		return karty;
	}
	
	public abstract String getNumb();
	
	public abstract String getDane();

}
