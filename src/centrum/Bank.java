package centrum;
import java.util.*;

public class Bank {

	private String nazBank;
	private ArrayList<klient> klient;

	public Bank(){};

	public Bank (String naz){
		this.klient = new ArrayList<klient>();
		this.nazBank=naz;
		System.out.println("nowy bank: "+this.nazBank);

	}

	public void dodajKlient(ArrayList<Bank> banki){
		klient kl;
		Scanner scan = new Scanner (System.in);
		System.out.println("1)osoba\n2)firma\n0)anuluj operacje");
		int wyb2=-1;
		while (wyb2!=1||wyb2!=2||wyb2!=0){
			while (!scan.hasNextInt()) scan.next();
			wyb2 = scan.nextInt();
			if (wyb2 ==1){
				System.out.println("imie:");
				String im = scan.next();
				System.out.println("nazwisko:");
				String nazwisko = scan.next();
				while(true){
					System.out.println("pesel:");
					String psl = "1";
					while (psl.length()!=11){
					psl = scan.next();
					if (psl.length()!=11) System.out.println("podano za krotki numer pesel!");
					}
					boolean znal = false;

					for (int i = 0; i<banki.size();i++){
						ArrayList <klient> kli = banki.get(i).getKlient();
						for (int j = 0; j<kli.size();j++){
							String dane = im+" "+nazwisko;
							if (psl.equalsIgnoreCase(kli.get(j).getNumb()) && !(dane.equalsIgnoreCase(kli.get(j).getDane())) ){
								znal = true;
								System.out.println("podany numer pesel znaleziono przy danych innej osoby (badz tez przy numerze krs firmy w tym lub innym banku!");
								break;
							}
						}
						if (znal == true) break;
					}
					if (znal == false){
						this.klient.add(kl = new Osoba(im,nazwisko,psl));
						this.ManKarty(kl,1,banki);
						return;
					}
				}
			}
			else if (wyb2 ==2){
				System.out.println("nazwa firmy-klienta:");
				Scanner scan2= new Scanner(System.in);
				String nazkli = scan2.nextLine();

				while(true){
					System.out.println("numer krs:");
					String kr = scan.next();

					boolean znal = false;
					for (int i = 0; i<banki.size();i++){
						ArrayList <klient> kli = banki.get(i).getKlient();
						//System.out.println("rozmiar tablicy z klientami: "+kli.size());
						for (int j = 0; j<kli.size();j++){
							if (kr.equalsIgnoreCase(kli.get(j).getNumb()) && !(nazkli.equalsIgnoreCase(kli.get(j).getDane())) ){
								znal = true;
								System.out.println("podany numer pesel znaleziono przy danych innej osoby (badz tez przy numerze krs firmy w tym lub innym banku!");
								break;
							}
						}
						if (znal == true) break;
						if (znal == false){
							this.klient.add(kl = new Firma(nazkli,kr));
							this.ManKarty(kl,1,banki);
							return;
						}
					}
				}
			}
			else if (wyb2 ==0) {
				return;
			}
			else System.out.println("nieprawidlowa komenda!");
		}
	}

	public String bankToString(){
		return this.nazBank;
	}

	public ArrayList<klient> getKlient(){
		return this.klient;
	}

	public void ManKarty(klient kl,int wybor, ArrayList<Bank> banki){
		Scanner scan = new Scanner(System.in);

		if (wybor==1){
			while(true){
				boolean numbRep=false;
				System.out.println("podaj nr karty:");
				String nrK = scan.next();

				for (int i=0;i<banki.size();i++){
					ArrayList<klient> kli = banki.get(i).getKlient();
					for (int j = 0; j<kli.size();j++){
						ArrayList<karty> kart = kli.get(j).getKarta();
						for (int k = 0; k<kart.size();k++){
							if (nrK.equalsIgnoreCase(kart.get(k).getNr())){
								numbRep = true;
								System.out.println("znaleziono taki sam numer karty w rejestrze tego lub innego banku!");
								break;
							}
						}
						if (numbRep==true) break;
					}
					if (numbRep==true) break;
				}
				
				if (numbRep == false){
					double kwota = -1;
					System.out.println("podaj poczatkowe saldo konta:");
					while (kwota < 0){
					while(!scan.hasNextDouble())scan.next();
					kwota = scan.nextDouble();
					if (kwota <0) System.out.println("pocz¹tkowe saldo musi wynosiæ wiêcej ni¿ 0!");
					}
					kl.dodajKarta(new karty(nrK,kwota));
					return;
				}
			}
		}

		else if (wybor ==0){
			ArrayList<karty> karty = kl.getKarta();
			int kwyb = -1;
			while(true){
				System.out.println("1)Dodaj karte\n2)Usun karte\n3)wyswietl liste kart (wraz ze saldem)"
						+ "\n0)powrot do menu glownego");
				while (!scan.hasNextInt()) scan.next();
				kwyb = scan.nextInt();

				if (kwyb == 1){
					ManKarty(kl,1,banki);
					return;
					}

				else if (kwyb == 2){
					System.out.println("podaj nr karty:");
					String nrK = scan.next();
					boolean znal = false;
					for (int i=0;i<karty.size();i++){
						if (nrK.equalsIgnoreCase(karty.get(i).getNr())){
							znal = true;
							System.out.println("usuwanie karty o numerze "+karty.get(i).getNr()+"...");
							karty.remove(i);
							return;
						}
					}
					if (znal == false){
						System.out.println("nie znaleziono podanej karty!");
					}
				}

				else if (kwyb == 3){
					System.out.println("Lista dostepnych kart wraz ze saldem:");
					for (int i=0;i<karty.size();i++){
						System.out.println("numer: "+karty.get(i).getNr()+" saldo: "+karty.get(i).getSaldo());
					}
				}

				else if (kwyb == 0) return;
			}
		}


	}

}