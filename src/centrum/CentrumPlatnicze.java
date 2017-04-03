package centrum;
import java.util.*;
import java.io.*;
import java.lang.*;


public class CentrumPlatnicze {

	private ArrayList<Bank> bank;
	private ArrayList <WspFirmy> wspfirmy;
	private ArrayList <trans> transakcje;

	public CentrumPlatnicze(){
		this.bank = new ArrayList<Bank>();
		this.wspfirmy = new ArrayList<WspFirmy>();
		this.transakcje = new ArrayList<trans>();
	}

	public void manBank(){
		System.out.println("1)dodaj bank\n2)usun bank\n3)wydruk wszystkich bankow");
		Scanner in = new Scanner (System.in);
		
		
		int wyb =-1;


		while (wyb!=1||wyb!=2||wyb!=3){

			while (!in.hasNextInt()) in.next();
			wyb = in.nextInt();

			if (wyb==1){
				boolean znal = false;
				Scanner scan = new Scanner (System.in);
				System.out.println("podaj nazwe banku:");
				String naz = scan.nextLine();
				for (int i=0;i<this.bank.size();i++){
					if (naz.equalsIgnoreCase(this.bank.get(i).bankToString())){
						znal = true;
						System.out.println("taki bank juz istnieje w naszych rejestrach!");
						return;
					}
				}
				if (znal ==false){
				bank.add(new Bank(naz));
				}
				return;

			}
			else if (wyb==2){
				System.out.println("podaj nazwe banku:");
				Scanner scan2 = new Scanner (System.in);
				String naz = scan2.nextLine();

				for (int i=0;i<bank.size();i++){
					if (bank.get(i).bankToString().equals(naz)){
						System.out.println("znaleziono bank "+naz+"!");
						bank.remove(i);
						System.out.println("bank usuniety z rejestru");
						return;
					}
				}
				System.out.println("nie znaleziono danego banku!");
				return;

			}
			else if (wyb ==3){
				ArrayList<Bank> list = this.getBank();
				System.out.println("lista bankow ktore wspolpracuja z centrum:");
				for (Bank b : list){
					System.out.println(b.bankToString());
				}
				return;

			}
			else System.out.println("podano nieprawidlowa komende!");
		}

	}

	public void manWspFirmy(){
		Scanner in = new Scanner (System.in);

		System.out.println("1)nowa firma wspolpracujaca\n2)usun firme\n3)przeglad wspolpracujacych firm");

		while (!in.hasNextInt()) in.next();
		int wyb = in.nextInt();

		if (wyb==1){
			Scanner in2 = new Scanner (System.in);
			boolean znal = false;
			System.out.println("podaj nazwe firmy:");
			String naz = in2.nextLine();
			for (int i=0;i<this.wspfirmy.size();i++){
				if (naz.equalsIgnoreCase(this.wspfirmy.get(i).WspToString())){
					znal = true;
					System.out.println("taka firma juz znajduje sie w naszych rejestrach!");
					return;
				}
			}
			if (znal == false) 	wspfirmy.add(new WspFirmy(naz));
			return;

		}
		else if (wyb==2){
			Scanner in2 = new Scanner (System.in);
			System.out.println("podaj nazwe firmy:");
			String naz = in2.nextLine();

			for (int i=0;i<wspfirmy.size();i++){
				if (wspfirmy.get(i).WspToString().equals(naz)){
					System.out.println("znaleziono firme "+naz+"!");
					wspfirmy.remove(i);
					System.out.println("firma wspolpracownicza usuniety z rejestru");
					return;
				}
			}
			System.out.println("nie znaleziono danej firmy!");
			return;

		}
		else if (wyb ==3){
			ArrayList<WspFirmy> list = this.getWspFirmy();
			System.out.println("lista firm ktore wspolpracuja z centrum:");
			for (WspFirmy w : list){
				System.out.println(w.WspToString());
			}
			return;

		}
		else System.out.println("podano nieprawidlowa komende!");
	}


	public ArrayList<WspFirmy> getWspFirmy(){
		return this.wspfirmy;	
	}

	public ArrayList<Bank> getBank(){
		return this.bank;
	}

	public boolean ZapytZatw(String dat,String NazwF, double kwota, String nrK) throws UnsupportedEncodingException, FileNotFoundException{
		Random rand = new Random();
		ArrayList<Bank> bnk = this.getBank();
		for (Bank b : bnk){
			ArrayList<klient> kli = b.getKlient();
			for (klient k :kli){
				ArrayList<karty> kart = k.getKarta();
				for (karty kr :kart){
					if (kr.getNr().equals(nrK)){
						if (kr.getSaldo()>=kwota){
							int los = rand.nextInt(100)+1;
							if (los>=4){
								kr.obciaz(kwota);
								System.out.println("SPRZEDAZ");
								this.transakcje.add(new trans(dat,NazwF,b.bankToString(),kr.getNr(),k.getDane(),kwota,true));
								this.ArchSave();
								return true;
							}
							else
							{
								System.out.println("NIEOKRESLONY BLAD TRANSAKCJI");
								this.transakcje.add(new trans(dat,NazwF,b.bankToString(),kr.getNr(),k.getDane(),kwota,false));
								this.ArchSave();
								return false;
							}

						}
						else
						{
							System.out.println("BRAK WYSTARCZAJACEJ KWOTY");
							this.transakcje.add(new trans(dat,NazwF,b.bankToString(),kr.getNr(),k.getDane(),kwota,false));
							this.ArchSave();
							return false;
						}
					}
				}
			}
		}
		System.out.println("NIE ZNALEZIONO TAKIEGO NUMERU");
		return false;
	}

	public void ArchSave () throws FileNotFoundException, UnsupportedEncodingException{

		ArrayList <trans> trans = this.getTrans();

		PrintWriter print = new PrintWriter("archiwum.txt", "UTF-8");
		for (int i=0;i<trans.size();i++){

			print.println(trans.get(i).data+","+trans.get(i).nazwaF+","+trans.get(i).nazBank+","+trans.get(i).nrK+","+trans.get(i).nazWl+","+trans.get(i).kwota+","+trans.get(i).sprzed);
		}

		print.close();


	}

	public void ArchPreLoad() throws FileNotFoundException{

		BufferedReader plik = new BufferedReader (new FileReader("archiwum.txt"));
		Scanner scan = new Scanner(plik);
		StringTokenizer token;

		while(scan.hasNextLine()){
			token = new StringTokenizer(scan.nextLine(),",");
			while(token.hasMoreTokens()){
				String dat = token.nextToken();
				String wspf = token.nextToken();
				String bnk = token.nextToken();
				String nrk = token.nextToken();
				String wl = token.nextToken();
				Double kw = Double.parseDouble(token.nextToken());
				boolean sprz = Boolean.parseBoolean(token.nextToken());
				

				
				if (this.bank.size()==0){
					this.bank.add(new Bank(bnk));
				}
				else{
					boolean znal = false;
					for (int i=0;i<this.bank.size();i++){
						if (bnk.equalsIgnoreCase(this.bank.get(i).bankToString())){
							znal = true;
							break;
						}
					}
					if (znal == false){
						 this.bank.add(new Bank(bnk));
					}
					}
				
				
				if (this.wspfirmy.size()==0){
					this.wspfirmy.add(new WspFirmy(wspf));
				}
				else{
					boolean znal = false;
					for (int i=0;i<this.wspfirmy.size();i++){
						if (wspf.equalsIgnoreCase(wspfirmy.get(i).WspToString())){
							znal=true;
							break;
						}
					}
						if (znal == false){
							this.wspfirmy.add(new WspFirmy(wspf));
						}
					}
				
				
				transakcje.add(new trans (dat,wspf,bnk,nrk,wl,kw,sprz));
			}
		}

	}

	public ArrayList<trans> getTrans(){
		return this.transakcje;
	}
	

	public void ArchSeek(String kryt, String war,String czyn1, String czyn2, String czyn3){
		ArrayList<trans> trans = this.getTrans();
		System.out.println("trwa przeszukiwanie archiwum za podanymi warunkami...");

		if (kryt.equals("12")){
			for (int i=0; i<trans.size();i++){
				if (war=="OR")
				{
					if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF)) || (czyn2.equalsIgnoreCase(trans.get(i).nazBank))){
						System.out.println(trans.get(i).toString());

					}
				}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF)) && (czyn2.equalsIgnoreCase(trans.get(i).nazBank))){
					System.out.println(trans.get(i).toString());

				}
			}


		}
		else if (kryt.equals("13")){
			for (int i=0; i<trans.size();i++){
				if (war == "OR"){
					if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF)) || (czyn2.equalsIgnoreCase(trans.get(i).nrK))){
						System.out.println(trans.get(i).toString());

					}
				}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF)) && (czyn2.equalsIgnoreCase(trans.get(i).nrK))){
					System.out.println(trans.get(i).toString());

				}
			}

		}
		else if (kryt.equals("14")){
			for (int i=0; i<trans.size();i++){
				if (war == "OR"){
					if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF)) || (trans.get(i).nazWl.toLowerCase().contains(czyn2.toLowerCase()))){
						System.out.println(trans.get(i).toString());

					}
				}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF)) && (trans.get(i).nazWl.toLowerCase().contains(czyn2.toLowerCase()))){
					System.out.println(trans.get(i).toString());

				}
			}

		}
		else if (kryt.equals("15"))
		{	
			for (int i=0; i<trans.size();i++){
				if (war =="OR"){
					if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF)) || (trans.get(i).kwota == Double.parseDouble(czyn2))) {System.out.println(trans.get(i).toString()); }
				}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF)) && (trans.get(i).kwota == Double.parseDouble(czyn2))) {System.out.println(trans.get(i).toString()); }

			}
		}
		else if (kryt.equals("23")){
			for (int i=0; i<trans.size();i++){
				if (war=="OR"){ if ((czyn1.equalsIgnoreCase(trans.get(i).nazBank)) || (czyn2.equalsIgnoreCase(trans.get(i).nrK))) {System.out.println(trans.get(i).toString()); }}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazBank)) && (czyn2.equalsIgnoreCase(trans.get(i).nrK))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("24")){
			for (int i=0; i<trans.size();i++){
				if (war =="OR") {if((czyn1.equalsIgnoreCase(trans.get(i).nazBank)) || (trans.get(i).nazWl.toLowerCase().contains(czyn2.toLowerCase()))) {System.out.println(trans.get(i).toString()); }}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazBank)) && (trans.get(i).nazWl.toLowerCase().contains(czyn2.toLowerCase()))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("25")){
			for (int i=0; i<trans.size();i++){
				if (war =="OR") {if((czyn1.equalsIgnoreCase(trans.get(i).nazBank)) || (trans.get(i).kwota == Double.parseDouble(czyn2))) {System.out.println(trans.get(i).toString()); }}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazBank) && trans.get(i).kwota == Double.parseDouble(czyn2))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("34")){
			for (int i=0; i<trans.size();i++){
				if (war =="OR") {if((czyn1.equalsIgnoreCase(trans.get(i).nrK)) || (trans.get(i).nazWl.toLowerCase().contains(czyn2.toLowerCase()))) {System.out.println(trans.get(i).toString()); }}
				else if((czyn1.equalsIgnoreCase(trans.get(i).nrK)) && (trans.get(i).nazWl.toLowerCase().contains(czyn2.toLowerCase()))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("35")){
			for (int i=0; i<trans.size();i++){
				if (war =="OR") {if((czyn1.equalsIgnoreCase(trans.get(i).nrK)) || (trans.get(i).kwota == Double.parseDouble(czyn2))) {System.out.println(trans.get(i).toString()); }}
				else if((czyn1.equalsIgnoreCase(trans.get(i).nrK)) && (trans.get(i).kwota == Double.parseDouble(czyn2))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("45")){
			for (int i=0; i<trans.size();i++){
				if (war =="OR") {if((trans.get(i).nazWl.toLowerCase().contains(czyn1.toLowerCase())) || (trans.get(i).kwota == Double.parseDouble(czyn2))) {System.out.println(trans.get(i).toString()); }}
				else if((trans.get(i).nazWl.toLowerCase().contains(czyn1.toLowerCase())) && (trans.get(i).kwota == Double.parseDouble(czyn2))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("123")){
			for (int i=0; i<trans.size();i++){
				if (war=="OR"){ if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF))||(czyn2.equalsIgnoreCase(trans.get(i).nazBank)) || (czyn3.equalsIgnoreCase(trans.get(i).nrK))) {System.out.println(trans.get(i).toString()); }}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF)) && (czyn2.equalsIgnoreCase(trans.get(i).nazBank)) && (czyn3.equalsIgnoreCase(trans.get(i).nrK))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("124")){
			for (int i=0; i<trans.size();i++){
				if (war=="OR"){ if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF))||(czyn2.equalsIgnoreCase(trans.get(i).nazBank)) || (trans.get(i).nazWl.toLowerCase().contains(czyn3.toLowerCase()))) {System.out.println(trans.get(i).toString()); }}
				else if (czyn1.equalsIgnoreCase((trans.get(i).nazwaF)) && (czyn2.equalsIgnoreCase(trans.get(i).nazBank)) && (trans.get(i).nazWl.toLowerCase().contains(czyn3.toLowerCase()))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("125")){
			for (int i=0; i<trans.size();i++){
				if (war=="OR"){ if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF))||(czyn2.equalsIgnoreCase(trans.get(i).nazBank)) || (trans.get(i).kwota == Double.parseDouble(czyn3))) {System.out.println(trans.get(i).toString()); }}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF)) && (czyn2.equalsIgnoreCase(trans.get(i).nazBank)) && (trans.get(i).kwota == Double.parseDouble(czyn3))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("134")){
			for (int i=0; i<trans.size();i++){
				if (war=="OR"){ if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF))||(czyn2.equalsIgnoreCase(trans.get(i).nrK)) || (trans.get(i).nazWl.toLowerCase().contains(czyn3.toLowerCase()))) {System.out.println(trans.get(i).toString()); }}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF)) && (czyn2.equalsIgnoreCase(trans.get(i).nrK) && (trans.get(i).nazWl.toLowerCase().contains(czyn3.toLowerCase())))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("135")){
			for (int i=0; i<trans.size();i++){
				if (war=="OR"){ if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF))||(czyn2.equalsIgnoreCase(trans.get(i).nrK)) || (trans.get(i).kwota == Double.parseDouble(czyn3))) {System.out.println(trans.get(i).toString()); }}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF)) && (czyn2.equalsIgnoreCase(trans.get(i).nrK) && (trans.get(i).kwota == Double.parseDouble(czyn3)))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("145")){
			for (int i=0; i<trans.size();i++){
				if (war=="OR"){ if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF))||(trans.get(i).nazWl.toLowerCase().contains(czyn2.toLowerCase())) || (trans.get(i).kwota == Double.parseDouble(czyn3))) {System.out.println(trans.get(i).toString()); }}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF)) && (trans.get(i).nazWl.toLowerCase().contains(czyn2.toLowerCase())) && (trans.get(i).kwota == Double.parseDouble(czyn3))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("234")){
			for (int i=0; i<trans.size();i++){
				if (war=="OR"){ if ((czyn1.equalsIgnoreCase(trans.get(i).nazBank)) || (czyn2.equalsIgnoreCase(trans.get(i).nrK)) || (trans.get(i).nazWl.toLowerCase().contains(czyn3.toLowerCase()))) {System.out.println(trans.get(i).toString()); }}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazBank)) && (czyn2.equalsIgnoreCase(trans.get(i).nrK)) && (trans.get(i).nazWl.toLowerCase().contains(czyn3.toLowerCase()))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("235")){
			for (int i=0; i<trans.size();i++){
				if (war=="OR"){ if ((czyn1.equalsIgnoreCase(trans.get(i).nazBank)) || (czyn2.equalsIgnoreCase(trans.get(i).nrK)) || (trans.get(i).kwota == Double.parseDouble(czyn3))) {System.out.println(trans.get(i).toString()); }}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazBank)) && (czyn2.equalsIgnoreCase(trans.get(i).nrK)) && (trans.get(i).kwota == Double.parseDouble(czyn3))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("245")){
			for (int i=0; i<trans.size();i++){
				if (war=="OR"){ if ((czyn1.equalsIgnoreCase(trans.get(i).nazBank))||(trans.get(i).nazWl.toLowerCase().contains(czyn2.toLowerCase())) || (trans.get(i).kwota == Double.parseDouble(czyn3))) {System.out.println(trans.get(i).toString()); }}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nazwaF)) && (trans.get(i).nazWl.toLowerCase().contains(czyn2.toLowerCase())) && (trans.get(i).kwota == Double.parseDouble(czyn3))) {System.out.println(trans.get(i).toString()); }
			}
		}
		else if (kryt.equals("345")){
			for (int i=0; i<trans.size();i++){
				if (war =="OR") {if((czyn1.equalsIgnoreCase(trans.get(i).nrK)) || (trans.get(i).nazWl.toLowerCase().contains(czyn2.toLowerCase()))|| (trans.get(i).kwota == Double.parseDouble(czyn3))) {System.out.println(trans.get(i).toString()); }}
				else if ((czyn1.equalsIgnoreCase(trans.get(i).nrK)) && (trans.get(i).nazWl.toLowerCase().contains(czyn2.toLowerCase())) && (trans.get(i).kwota == Double.parseDouble(czyn3))) {System.out.println(trans.get(i).toString());};
			}
		}
		else if (kryt.equals("1")){
			for (int i=0;i<trans.size();i++){
				if (czyn1.equalsIgnoreCase(trans.get(i).nazwaF)){
					System.out.println(trans.get(i).toString());
				}
			}
		}
		else if (kryt.equals("2")){
			for (int i=0;i<trans.size();i++){
				if (czyn1.equalsIgnoreCase(trans.get(i).nazBank)) {System.out.println(trans.get(i).toString());}
			}
		}
		else if (kryt.equals("3")){
			for (int i=0;i<trans.size();i++){
				if (czyn1.equalsIgnoreCase(trans.get(i).nrK)) {System.out.println(trans.get(i).toString());}
			}
		}
		else if (kryt.equals("4")){
			for (int i=0;i<trans.size();i++){
				if (trans.get(i).nazWl.toLowerCase().contains(czyn1.toLowerCase())) {System.out.println(trans.get(i).toString());}
			}
		}
		else if (kryt.equals("5")){
			for (int i=0;i<trans.size();i++){
				if (trans.get(i).kwota == Double.parseDouble(czyn1)) {System.out.println(trans.get(i).toString());}
			}
		}
		
		
		else{
			System.out.println("podano zly warunek!");
			return;

		}
	}
}
