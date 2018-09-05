package  com.elton.app.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.springframework.util.Base64Utils;

public final class RedisKeysHelper {

	private static final String CATEGORIES_KEY_PARTNER = "categories:%s";

	public static String generateCategoriesKey(final Long category) {
		return String.format(CATEGORIES_KEY_PARTNER, category);
	}

	public static String deserializableToString(final Serializable object) throws Exception  {
		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.close();
			return Base64Utils.encodeToString(baos.toByteArray());
		} catch (final IOException e) {
			throw new Exception(e.getMessage());
		}
	}

	public static <T> T serializableToObject(final String key, final Class<T> classType) throws Exception {
		try {
			final byte[] data = Base64Utils.decodeFromString(key);
			final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			final Object o = ois.readObject();
			ois.close();
			return classType.cast(o);
		} catch (final IOException e) {
			throw new Exception("IOException on AbstractRedisRepository.getSerializable", e);
		} catch (final ClassNotFoundException e) {
			throw new Exception("ClassNotFoundException on AbstractRedisRepository.getSerializable", e);
		}
	}
}
