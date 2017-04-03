package projekt;
import centrum.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.*;

public class Projekt {

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {

		CentrumPlatnicze cent = new CentrumPlatnicze();
		cent.ArchPreLoad();

		Scanner scan = new Scanner(System.in);

		while (true){
			System.out.println("1) zarzadzanie\n2) Bank (zarzadzanie klientami)"
					+ "\n3) Transakcje"
					+ "\n5) Wyszukiwanie archiwum"
					+ "\n0)wyjscie");
			while (!scan.hasNextInt()) scan.next();
			int wyb = scan.nextInt();

			if (wyb==1){
				System.out.println("1)banki\n2)firmy wspolpracujace z centrum\n0)powrot do menu glownego");
				int wyb1=-1;
				while (wyb1!=0){
					while (!scan.hasNextInt()) scan.next();
					wyb1 = scan.nextInt();

					if (wyb1==1){
						cent.manBank();
						break;
					}
					else if (wyb1==2){
						cent.manWspFirmy();
						break;
					}
					else if(wyb1==0) break;

					else System.out.println("nieprawidlowa komenda!");
				}

			}

			else if (wyb==2){
				String wybkli = "wybkli";
				Scanner two = new Scanner (System.in);
				System.out.println("podaj nazwe banku:");
				ArrayList <Bank> lista = cent.getBank();
				String bnk = two.nextLine();
				boolean znalbank = false;
				for (int i = 0; i<lista.size();i++){
					if (bnk.equalsIgnoreCase(lista.get(i).bankToString()))
					{
						String thisbank = lista.get(i).bankToString();
						znalbank = true;
						System.out.println("znaleziono bank!\n");
						ArrayList<klient> kli = lista.get(i).getKlient();
						System.out.println("1) Nowy klient");
						if (kli.size()!=0) System.out.println("2) Dodaj karte do juz istniejacego klienta");
						System.out.println("3) Wydruk wszystkich klientow tego banku");
						wybkli=scan.next();
						System.out.println(wybkli);
						if (wybkli.equalsIgnoreCase("1")){
							lista.get(i).dodajKlient(lista);
							break;
						}
						else if (wybkli.equalsIgnoreCase("2") && kli.size()!=0){
							System.out.println("podaj numer pesel lub krs klienta");
							String nr = scan.next();
							ArrayList <klient> KList = lista.get(i).getKlient();
							for (int j = 0; j<KList.size();j++){
								if (nr.equalsIgnoreCase(KList.get(j).getNumb())){
									lista.get(i).ManKarty(KList.get(j),0,lista);
									break;
								}
							}
						}
						else if (wybkli.equalsIgnoreCase("3")){
							ArrayList<klient> KList = lista.get(i).getKlient();
							System.out.println("lista klientow banku "+thisbank);
							for (int x=0;x<KList.size();x++){
								System.out.println(KList.get(x).getDane());
							}
						}
						else System.out.println("wybrano zla opcje! Powrot do menu glownego...");
						break;
					}
				}
				if (znalbank == false)	System.out.println("nie znaleziono podanego banku!");
			}


			else if (wyb == 3){
				Scanner nazsc = new Scanner (System.in);
				System.out.println("podaj nazwe firmy wspolpracujacej z centrum");
				String NazwF = nazsc.nextLine();
				ArrayList<WspFirmy> wsp = cent.getWspFirmy();
				String dat = "0";
				boolean zn = false;
				for (int i=0;i<wsp.size();i++){

					if (NazwF.equalsIgnoreCase(wsp.get(i).WspToString())){
						zn = true;
						while (dat.length()!=10){
							System.out.println("podaj date transakcji (FORMAT: DD-MM-RRRR):");
							dat = scan.next();
							if (dat.length()!=10){
								System.out.println("podano zly format daty transakcji! poprawny format: DD-MM-RRRR");
							}
						}
						System.out.println("podaj nr karty:");
						String nrK= scan.next();
						System.out.println("podaj kwote transakcji:");
						while (!scan.hasNextDouble())scan.next();
						double kwota = scan.nextDouble();
						System.out.println("rezultat transakcji:");

						if (cent.ZapytZatw(dat,NazwF,kwota,nrK)==true) System.out.println("TRANSAKCJA ZATWIERDZONA");
						else System.out.println("TRANSAKCJA ODRZUCONA");

						break;
					}
				}
				if (zn = false)System.out.println("nie znaleziono takiej firmy!");
			}

			else if (wyb ==5){
				String kryt = "89454";
				String war = "0";
				String czyn1,czyn2,czyn3;
				System.out.println("podaj od 1 do max. 3 warunkow (wpisujac je jako jako jedna liczbe, w kolejnosci rosnacej):\n1 - nazwa firmy wspolpracujacej"
						+ "\n2 - nazwa banku"
						+ "\n3 - nr karty\n4 - wlasciciel karty (imie LUB nazwisko / nazwa firmy)\n5)kwota");
				while (!(kryt.length()>=1 && kryt.length()<=3)){
					kryt=scan.next();
					if (!(kryt.length()>=1 && kryt.length()<=3)) System.out.println("zly format warunku!");
				}

				Scanner s1 = new Scanner (System.in);
				System.out.println("podaj wartosc (ciag znakow) warunku nr1:");
				czyn1=s1.nextLine();
				System.out.println(czyn1);
				if(kryt.length()>=2){
					Scanner s2 = new Scanner (System.in);
					System.out.println("podaj wartosc (ciag znakow) warunku nr2:");
					czyn2=s2.nextLine();
				}
				else czyn2="0";

				if(kryt.length()==3){
					Scanner s3 = new Scanner (System.in);
					System.out.println("podaj wartosc (ciag znakow) warunku nr3:");
					czyn3=s3.nextLine();
				}
				else czyn3="0";

				if (kryt.length()==1){
					cent.ArchSeek(kryt, "OR", czyn1, czyn2, czyn3);
				}
				else{
					System.out.println("warunki:\n1)OR\n2)AND");

					while (!(war.equals("1")||war.equals("2"))){
						war = scan.next();
						if (!(war.equals("1")||war.equals("2"))) System.out.println("podano zly format warunku!");
					}
				}

				if (war.equals("1")) cent.ArchSeek(kryt,"OR",czyn1,czyn2,czyn3);
				else if (war.equals("2")) cent.ArchSeek(kryt,"AND",czyn1,czyn2,czyn3);


			}


			else if (wyb==0){
				scan.close();
				return;
			}
		}
	}
}
