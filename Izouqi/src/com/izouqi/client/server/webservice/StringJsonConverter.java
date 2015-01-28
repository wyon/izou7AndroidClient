package com.izouqi.client.server.webservice;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class StringJsonConverter implements Converter {
	private String charset;
	private GsonConverter gsonConverter;

	public StringJsonConverter() {
		this("UTF-8");
	}

	public StringJsonConverter(String charset) {
		this.charset = charset;
		gsonConverter = new GsonConverter(new Gson(), this.charset);
	}

	@Override
	public Object fromBody(TypedInput body, Type type)
			throws ConversionException {
		if (String.class == type) {
			String charset = this.charset;
			if (body.mimeType() != null) {
				charset = MimeUtil.parseCharset(body.mimeType(), charset);
			}
			InputStreamReader isr = null;
			try {
				isr = new InputStreamReader(body.in(), charset);
				char[] buffer = new char[1024];
				int len = -1;
				StringWriter sw = new StringWriter();
				while ((len = isr.read(buffer)) > 0) {
					sw.write(buffer, 0, len);
				}
				return sw.toString();
			} catch (IOException e) {
				throw new ConversionException(e);
			} catch (JsonParseException e) {
				throw new ConversionException(e);
			} finally {
				if (isr != null) {
					try {
						isr.close();
					} catch (IOException ignored) {
					}
				}
			}
		} else {
			return gsonConverter.fromBody(body, type);
		}
	}

	@Override
	public TypedOutput toBody(Object object) {
		if (String.class.isInstance(object)) {
			try {
				return new JsonTypedOutput(object.toString().getBytes(charset),
						charset);
			} catch (UnsupportedEncodingException e) {
				throw new AssertionError(e);
			}
		} else {
			return gsonConverter.toBody(object);
		}
	}

	private static class JsonTypedOutput implements TypedOutput {
		private final byte[] jsonBytes;
		private final String mimeType;

		JsonTypedOutput(byte[] jsonBytes, String encode) {
			this.jsonBytes = jsonBytes;
			this.mimeType = "application/json; charset=" + encode;
		}

		@Override
		public String fileName() {
			return null;
		}

		@Override
		public String mimeType() {
			return mimeType;
		}

		@Override
		public long length() {
			return jsonBytes.length;
		}

		@Override
		public void writeTo(OutputStream out) throws IOException {
			out.write(jsonBytes);
		}
	}
}
