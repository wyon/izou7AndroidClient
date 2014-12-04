package com.izouqi.client.server.webservice;

import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import android.content.Context;

public class CustomSSLSocketFactory {

	private CustomSSLSocketFactory() {
	}

	public static SSLSocketFactory getDefault(Context context, int cerRawid, String password) {
		if (context == null) {
			throw new IllegalArgumentException("context can not be null");
		}

		if(password == null){
			throw new IllegalArgumentException("password can not be null");
		}
		
		try {
			SSLContext sslContext;
			
			KeyStore keystore = KeyStore.getInstance("BKS");
			keystore.load(context.getResources().openRawResource(cerRawid), password.toCharArray());
			
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
			tmf.init(keystore);
			
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
			kmf.init(keystore, password.toCharArray());
			
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			
			return sslContext.getSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
