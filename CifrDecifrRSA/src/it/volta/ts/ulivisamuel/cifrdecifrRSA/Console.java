package it.volta.ts.ulivisamuel.cifrdecifrRSA;

import java.util.Scanner;

import it.volta.ts.ulivisamuel.cifrdecifrRSA.biz.BizCifrDecifr;
import it.volta.ts.ulivisamuel.cifrdecifrRSA.events.CifrDecifrConsoleListener;
import it.volta.ts.ulivisamuel.cifrdecifrRSA.util.Util;

public class Console 
{
	private Scanner          scanner;
	private BizCifrDecifr bizCifrDecifr;
	
	//---------------------------------------------------------------------------------------------
	
	public Console()
	{
		bizCifrDecifr = new BizCifrDecifr();
	}
	
	//---------------------------------------------------------------------------------------------
	
	public void esegui()
	{
		scanner = new Scanner(System.in);
		bizCifrDecifr.setConsoleListener(new CifrDecifrConsoleListener());
		menu();
		scanner.close();
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void menu()
	{
		String  mess     = "\nMenu\n   1.Cifra un messaggio"
				         + "\n   2.Decifra un messaggio\n   0.Esci";
		boolean continua = true;
		while(continua)
		{
			int scelta = Util.leggiInt(scanner, mess, 0, 2, true, 0);
			switch(scelta)
			{
			case 0:
				continua = false;
				break;
			case 1:
				cifraMess();
				break;
			case 2:
				decifraMess();
				break;
			}
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void cifraMess()
	{
		System.out.println("\nModalità Cifratura");
		String mess2 = "\nInserisci il messaggio da cifrare.\nOppure premi invio per "
			     + "annullare l'operazione.";
		String messNoDecr = Util.leggiString(scanner, mess2, false, null);
		if(messNoDecr != null)
			bizCifrDecifr.cifraTesto(messNoDecr);
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void decifraMess()
	{
		System.out.println("\nModalità Decifratura");
		String mess2 = "\nInserisci il messaggio da decifrare.\nOppure premi invio per "
			     + "annullare l'operazione.";
		String messNoDecr = Util.leggiString(scanner, mess2, false, null);
		if(messNoDecr != null)
			bizCifrDecifr.decifraTesto(messNoDecr);
	}
}
