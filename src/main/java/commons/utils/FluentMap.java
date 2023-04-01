package commons.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author guorui1
 */
public class FluentMap<K, V> extends HashMap<K, V> {
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    static final int MAX_POWER_OF_TWO = 1 << (Integer.SIZE - 2);

    public FluentMap() {
        super();
    }

    public FluentMap(int initialCapacity) {
        super(expectedCapacity(initialCapacity));
    }

    /**
     * value不为null才放进map，否则忽略
     */
    public FluentMap<K, V> putIfNotNull(K key, V value) {
        if (value == null) {
            return this;
        }
        if (value instanceof String && StringUtils.isEmpty((String) value)) {
            return this;
        }
        super.put(key, value);
        return this;
    }

    /**
     * from guava
     */
    public static int expectedCapacity(int expectedSize) {
        if (expectedSize < 0) {
            throw new IllegalArgumentException("expectedSize cannot be negative but was: " + expectedSize);
        }
        if (expectedSize < DEFAULT_INITIAL_CAPACITY) {
            return expectedSize + 1;
        }
        if (expectedSize < MAX_POWER_OF_TWO) {
            return (int) ((float) expectedSize / 0.75F + 1.0F);
        }
        return Integer.MAX_VALUE;
    }

    public Map<String, String> toStringMap() {
        Map<String, String> result = new HashMap<>(expectedCapacity(this.size()));
        for (Entry<K, V> entry : this.entrySet()) {
            result.put(Objects.toString(entry.getKey()), Objects.toString(entry.getValue()));
        }
        return result;
    }
}
