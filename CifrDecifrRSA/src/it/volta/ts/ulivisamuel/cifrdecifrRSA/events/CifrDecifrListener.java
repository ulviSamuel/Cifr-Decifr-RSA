package it.volta.ts.ulivisamuel.cifrdecifrRSA.events;

import java.util.EventListener;

public interface CifrDecifrListener extends EventListener 
{
	public void mostra(CifrDecifrEvent event);
	public void mostraErrore(CifrDecifrEvent event);
}
