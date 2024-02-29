package it.volta.ts.ulivisamuel.cifrdecifrRSA.biz;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import it.volta.ts.ulivisamuel.cifrdecifrRSA.events.CifrDecifrConsoleListener;
import it.volta.ts.ulivisamuel.cifrdecifrRSA.events.CifrDecifrEvent;

public class BizCifrDecifr 
{
	private KeyPair                   pair;
	private PublicKey                 pubKey;
	private PrivateKey                priKey;
	private Cipher                    cipher;
	private CifrDecifrConsoleListener consoleListener;
	
	//---------------------------------------------------------------------------------------------
	
	
	public BizCifrDecifr()
	{
		generaChiavi();
		estraiChiavi();
		generaChiper();
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void generaChiavi()
	{
		KeyPairGenerator generator = null;
		try {
			generator = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		generator.initialize(1024);
		pair = generator.generateKeyPair();
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void estraiChiavi()
	{
		pubKey = pair.getPublic();
		priKey = pair.getPrivate();
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void generaChiper()
	{
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	public void setConsoleListener(CifrDecifrConsoleListener consoleListener)
	{
		this.consoleListener = consoleListener;
	}
	
	
	//---------------------------------------------------------------------------------------------
	
	public String cifraTesto(String plainText)
	{
		if(plainText != null)
		{
			try {
				cipher.init(this.cipher.ENCRYPT_MODE, pubKey);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
			byte[] cipherText = null;
			try {
				cipherText = cipher.doFinal(plainText.getBytes());
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
			String cipherTextString = Base64.getEncoder().encodeToString(cipherText);
			consoleListener.mostra(new CifrDecifrEvent("\nTesto cifrato: " + cipherTextString));
			return cipherTextString;
		}
		consoleListener.mostraErrore(new CifrDecifrEvent("\nNon è stato possibile cifrare il messaggio!"));
		return null;
	}
	
	//---------------------------------------------------------------------------------------------
	
	public String decifraTesto(String cipherText)
	{
		
		if(cipherText != null)
    	{
			try {
				cipher.init(Cipher.DECRYPT_MODE, priKey);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
			byte[] plainText = null;
			try {
				plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
			}
    		consoleListener.mostra(new CifrDecifrEvent("\nTesto decifrato: " + new String(plainText)));
    		return new String(plainText);
    	}
    	consoleListener.mostraErrore(new CifrDecifrEvent("\nNon è stato possibile cifrare il messaggio!"));
		return null;
	}
}
