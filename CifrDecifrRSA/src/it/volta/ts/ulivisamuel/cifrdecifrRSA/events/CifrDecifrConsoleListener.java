package it.volta.ts.ulivisamuel.cifrdecifrRSA.events;

public class CifrDecifrConsoleListener implements CifrDecifrListener
{
	@Override
	public void mostra(CifrDecifrEvent event) 
	{
		System.out.println(event.getSource());
	}
	
	//---------------------------------------------------------------------------------------------
	
	@Override
	public void mostraErrore(CifrDecifrEvent event) 
	{
		System.err.println(event.getSource());
	}
}
