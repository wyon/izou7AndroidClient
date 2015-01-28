package com.izouqi.client.server.webservice.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import android.test.AndroidTestCase;

import com.izouqi.client.server.webservice.ServerAPI;

public class ServerAPITest extends AndroidTestCase {
	public void statLogin() {
		String request = "{\"username\":\"zy\",\"password\":\"654321\",\"source\":0}";

		String methodName = "login";

		Method[] methods = ServerAPI.class.getDeclaredMethods();

		Map<String, Method> methodMap = new HashMap<String, Method>(
				methods.length);

		for (Method method : methods) {
			methodMap.put(method.getName(), method);
		}

		try {
			String response = (String) methodMap.get(methodName).invoke(
					ServerAPI.class, request);
			System.out.println(response);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	public void login() {

		// String request =
		// "{\"username\":\"zy\",\"password\":\"654321\",\"source\":0}";
		//
		// String response = ServerAPI.login(request);
		//
		// System.out.println(response);

		assertNotNull("");
	}
}
