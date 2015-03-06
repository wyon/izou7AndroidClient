package com.izouqi.client.business;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.RetrofitError;

import android.content.Context;

import com.android.common.BackgroundExecutor;
import com.android.common.StringUtil;
import com.android.common.Tools;
import com.izouqi.client.server.webservice.ServerAPI;
import com.izouqi.client.server.webservice.SupportCache;
import com.izouqi.client.toolkit.Utils;

public class ServerAPIPlugin extends CordovaPlugin {

	private static final String CACHE = "Cache";

	private Map<String, Method> methodMap;

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		// TODO initialzie
		// super.initialize(cordova, webView);
		getMethods();
	}

	private void getMethods() {
		Method[] methods = ServerAPI.class.getDeclaredMethods();

		methodMap = new HashMap<String, Method>(methods.length);

		for (Method method : methods) {
			if (Modifier.isPublic(method.getModifiers())) {
				methodMap.put(method.getName(), method);

				if (method.getAnnotation(SupportCache.class) != null) {
					methodMap.put(method.getName() + CACHE, method);
				}
			}
		}
	}

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		Method method = methodMap.get(action);

		if (method != null) {
			String methodName = method.getName();

			if (!methodName.equals(action)
					&& (methodName + CACHE).equals(action)) {
				// cache run
				return true;
			}

			Object[] varargs;
			if (method.isVarArgs()) {
				varargs = new Object[0];
			} else {
				varargs = parseArrayArgs(args);
			}
			invokeRequest(method, varargs, callbackContext);
			return true;
		}

		return false;
	}

	private Object[] parseArrayArgs(JSONArray args) {
		Object[] ret = null;
		if (args != null) {
			int length = args.length();
			ret = new Object[length];
			if (length > 0) {
				for (int i = 0; i < length; i++) {
					ret[i] = args.opt(i);
				}
			}
		}
		return ret;
	}

	private void invokeRequest(Method method, Object[] args,
			CallbackContext callbackContext) {
		ServerRequestRunnable runnable = new ServerRequestRunnable(method,
				args, callbackContext);
		BackgroundExecutor.execute(runnable);
	}

	private Context getContext() {
		// return cordova.getActivity();
		// TODO Plugin getContext
		return Utils.getApp();
	}

	class ServerRequestRunnable implements Runnable {
		public static final String MSG_UNEXPECTED = "unexpected";
		public static final String MSG_RESPONSE_JSON_ERROR = "server reponse data convert to json fail";

		private Method method;
		private Object[] args;
		private CallbackContext callbackContext;

		ServerRequestRunnable(Method method, Object[] args,
				CallbackContext callbackContext) {
			this.method = method;
			this.args = args;
			this.callbackContext = callbackContext;
		}

		@Override
		public void run() {
			if (!Tools.isNetworkConnected(getContext())) {
				PluginResult pluginResult = new PluginResult(
						PluginResult.Status.IO_EXCEPTION);
				callbackContext.sendPluginResult(pluginResult);
				return;
			}

			try {
				Object result = method.invoke(null, args);
				if (result == null) {
					invokeMethodFailed(PluginResult.Status.NO_RESULT);
					return;
				}

				String strResult = result.toString();
				if (StringUtil.isNullOrWhiteSpace(strResult)) {
					callbackContext.success(strResult);
				} else {
					JSONObject json = new JSONObject(strResult);
					callbackContext.success(json);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				invokeMethodFailed(PluginResult.Status.ILLEGAL_ACCESS_EXCEPTION);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				invokeMethodFailed(PluginResult.Status.ILLEGAL_ACCESS_EXCEPTION);
			} catch (InvocationTargetException e) {
				if (e.getCause() != null) {
					try {
						throw e.getCause();
					} catch (JSONException e1) {
						e.printStackTrace();
						PluginResult cr = new PluginResult(
								PluginResult.Status.JSON_EXCEPTION,
								MSG_RESPONSE_JSON_ERROR);
						callbackContext.sendPluginResult(cr);
					} catch (RetrofitError e1) {
						e.printStackTrace();
						handleRetrofitError(e1);
					} catch (Throwable e1) {
						e1.printStackTrace();
					}
					return;
				}

				e.printStackTrace();
				invokeMethodFailed(PluginResult.Status.ILLEGAL_ACCESS_EXCEPTION);
			} catch (JSONException e) {
				e.printStackTrace();
				PluginResult cr = new PluginResult(
						PluginResult.Status.JSON_EXCEPTION,
						MSG_RESPONSE_JSON_ERROR);
				callbackContext.sendPluginResult(cr);
			} catch (RetrofitError e) {
				e.printStackTrace();
				handleRetrofitError(e);
			} catch (Throwable t) {
				t.printStackTrace();
				callbackContext.error(MSG_UNEXPECTED);
			}
		}

		private void invokeMethodFailed(PluginResult.Status status) {
			PluginResult cr = new PluginResult(status);
			callbackContext.sendPluginResult(cr);
		}

		private void handleRetrofitError(RetrofitError e) {
			PluginResult cr = null;
			switch (e.getKind()) {
			case NETWORK:
				cr = new PluginResult(PluginResult.Status.IO_EXCEPTION);
				break;
			case CONVERSION:
				cr = new PluginResult(PluginResult.Status.ERROR,
						"plugin internal logic error");
				break;
			case HTTP:
				cr = new PluginResult(PluginResult.Status.ERROR, "http non 200");
				break;
			default:
				cr = new PluginResult(PluginResult.Status.ERROR, MSG_UNEXPECTED);
			}
			callbackContext.sendPluginResult(cr);
		}
	}
}
