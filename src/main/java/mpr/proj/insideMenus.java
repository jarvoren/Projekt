package mpr.proj;

public abstract class insideMenus {
	
	public static void dodawanieWpisu(){
		
		EasyIn.clear();
		System.out.println("Menu");
		System.out.println("1) Dopisz konia");
		System.out.println("2) Dopisz Hodowce");
		System.out.println("3) Dopisz Kraj");
		System.out.println("4) Dopisz Kolor");
		switch(EasyIn.getInt())
		{
			
		case 1:
		{
			EasyIn.clear();
			dataBaseOperations.dodajKonia();
			break;
		}
		
		case 2:
		{
			EasyIn.clear();
			dataBaseOperations.dodajHodowce();
			break;	
		}
		
		case 3:
		{
			EasyIn.clear();
			dataBaseOperations.dodajKraj();
			break;
		}
		case 4:
		{
			EasyIn.clear();
			dataBaseOperations.dodajKolor();
			break;
		}
		
		default:{
			EasyIn.clear();
			System.out.println("nieprawidłowy wybór");
			EasyIn.getChar();
			break;
		}
		}
	}
	public static void wypisywanieDanych(){
		EasyIn.clear();
		System.out.println("Menu");
		System.out.println("1) Wyświetl konie");
		System.out.println("2) Wyświetl hodowcow");
		System.out.println("3) Wyświetl Kraje");
		System.out.println("4) Wyświetl Kolory");
		
		
		switch(EasyIn.getInt())
		{
			
		case 1:
		{
			EasyIn.clear();
			dataBaseOperations.pokazKonie();
			break;
		}
		
		case 2:
		{
			EasyIn.clear();
			dataBaseOperations.pokazHodowcow();
			break;	
		}
		case 3:
		{
			EasyIn.clear();
			dataBaseOperations.pokazKraje();
			break;	
		}
		case 4:
		{
			EasyIn.clear();
			dataBaseOperations.pokazKolory();
			break;	
		}
		
		
		
		default:{
			EasyIn.clear();
			System.out.println("nieprawidłowy wybór");
			EasyIn.getChar();
			break;
		}
		}
		
	}
	public static void updateDanych(){
		
		EasyIn.clear();
		System.out.println("Menu");
		System.out.println("1) Modyfikuj wpis konia");
		System.out.println("2) Modyfikuj wpis hodowcy");
		System.out.println("3) Modyfikuj Kraje");
		System.out.println("4) Modyfikuj Kolory");
		
		switch(EasyIn.getInt())
		{
			
		case 1:
		{
			EasyIn.clear();
			dataBaseOperations.modyfikujKonia();
			break;
		}
		
		case 2:
		{
			EasyIn.clear();
			dataBaseOperations.modyfikujHodowce();
			break;	
		}
		case 3:
		{
			EasyIn.clear();
			dataBaseOperations.modyfikujKraj();
			break;
		}
		
		case 4:
		{
			EasyIn.clear();
			dataBaseOperations.modyfikujKolor();
			break;	
		}
		
		
		default:{
			EasyIn.clear();
			System.out.println("nieprawidłowy wybór");
			EasyIn.getChar();
			break;
		}
		}
		
	}
	public static void kasowanieDanych(){
		EasyIn.clear();
		System.out.println("Menu");
		System.out.println("1)Skasuj wpis konia");
		System.out.println("2)Skasuj wpis hodowcy");
		System.out.println("3)Skasuj wpis Koloru");
		System.out.println("3)Skasuj wpis Kraju");
		
		switch(EasyIn.getInt())
		{
			
		case 1:
		{
			EasyIn.clear();
			dataBaseOperations.kasujKonia();
			break;
		}
		
		case 2:
		{
			EasyIn.clear();
			dataBaseOperations.kasujHodowce();
			break;	
		}
		case 3:
		{
			EasyIn.clear();
			dataBaseOperations.kasujKolor();
			break;	
		}
		case 4:
		{
			EasyIn.clear();
			dataBaseOperations.kasujKraj();
			break;	
		}
		
		
		default:{
			EasyIn.clear();
			System.out.println("nieprawidłowy wybór");
			EasyIn.getChar();
			break;
		}
		}
		
	}
	public static void wyszukiwaniePotomstwa(){
		
		EasyIn.clear();
		dataBaseOperations.wyszukajPotomstwoKonia();
		
	}
	public static void generowanieRodowodu(){
		EasyIn.clear();
		dataBaseOperations.wygenerujRodowodKonia();
	}

}
