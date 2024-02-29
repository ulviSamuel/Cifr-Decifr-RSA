package it.volta.ts.ulivisamuel.cifrdecifrRSA.biz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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
		verificaEsistenzaChiavi();
		generaChiper();
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void verificaEsistenzaChiavi()
	{
        String percorsoFileKpub = "pubKey.dat";
        String percorsoFileKpriv = "privKey.dat";
        File filePub = new File(percorsoFileKpub);
        File filePrv = new File(percorsoFileKpriv);
        if(filePub.exists() && filePrv.exists())
        {
        	recuperaChiavePubblica();
        	recuperaChiavePrivata();
        }
        else
        {
        	salvaEcreaChiavePubblica();
        	salvaEcreaChiavePrivata();
        }
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void salvaEcreaChiavePubblica()
	{
		pubKey = pair.getPublic();
		byte[] publicBytes = pubKey.getEncoded();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("pubKey.dat");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			fos.write(publicBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void recuperaChiavePubblica()
	{
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("pubKey.dat");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		byte[] encodedKey = null;
		try {
			encodedKey = new byte[fis.available()];
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fis.read(encodedKey);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		X509EncodedKeySpec spec = new X509EncodedKeySpec(encodedKey);
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			pubKey = keyFactory.generatePublic(spec);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void verificaEsistenzaChiavePriv()
	{
		String percorsoFile = "privKey.dat";
        File file = new File(percorsoFile);
        if(!file.exists())
        	salvaEcreaChiavePrivata();
        else
        	recuperaChiavePrivata();
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void salvaEcreaChiavePrivata()
	{
		priKey = pair.getPrivate();
		byte[] privateBytes = this.priKey.getEncoded();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("privKey.dat");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			fos.write(privateBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		priKey = pair.getPrivate();
	}
	
	//---------------------------------------------------------------------------------------------
	
	private void recuperaChiavePrivata()
	{
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("privKey.dat");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		byte[] encodedKey = null;
		try {
			encodedKey = new byte[fis.available()];
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fis.read(encodedKey);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(encodedKey);
		KeyFactory kf = null;
		try {
			kf = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		try {
			this.priKey = kf.generatePrivate(ks);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
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
