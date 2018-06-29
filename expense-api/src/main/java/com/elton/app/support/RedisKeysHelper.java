package  com.elton.app.support;

public final class RedisKeysHelper {

	private static final String CATEGORIES_KEY_PARTNER = "categories:%s";

	public static String generateCategoriesKey(final Long category) {
		return String.format(CATEGORIES_KEY_PARTNER, category);
	}
}
