package  com.elton.app.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.springframework.util.Base64Utils;

import com.elton.app.exception.ManagedRedisException;


public final class RedisHelper {

	private static final String CATEGORIES_KEY_PARTNER = "categories:%s";

	public static String generateCategoriesKey(final Long category) {
		return String.format(CATEGORIES_KEY_PARTNER, category);
	}

	public static String deserializableToString(final Serializable object) {
		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.close();
			return Base64Utils.encodeToString(baos.toByteArray());
		} catch (final IOException e) {
			throw new ManagedRedisException("Exception on RedisKeysHelper.deserializableToString", e);
		}
	}

	public static <T> T serializableToObject(final String key, final Class<T> classType) {
		try {
			final byte[] data = Base64Utils.decodeFromString(key);
			final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			final Object o = ois.readObject();
			ois.close();
			return classType.cast(o);
		} catch (final IOException e) {
			throw new ManagedRedisException("IOException on RedisKeysHelper.serializableToObject", e);
		} catch (final ClassNotFoundException e) {
			throw new ManagedRedisException("ClassNotFoundException on RedisKeysHelper.serializableToObject", e);
		}
	}
}