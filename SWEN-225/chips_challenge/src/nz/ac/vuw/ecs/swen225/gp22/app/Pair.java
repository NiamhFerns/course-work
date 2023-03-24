package nz.ac.vuw.ecs.swen225.gp22.app;

/**
 * Represents a pair of data. Conceptually acts as a single entry in a map.
 * @param <K> the key object for this pair.
 * @param <V> the data object for this pair.
 *
 * @author niamh
 */
public class Pair<K, V> {
    private K key;
    private V value;

    /**
     * Retrieve the key.
     * @return the key.
     *
     * @author niamh
     */
    public K key() { return key; }

    /**
     * Retrieve the value.
     * @return the value.
     *
     * @author niamh
     */
    public V value() { return value; }

    /**
     * Sets the key to a new value.
     * @param key the new value for the key you want.
     *
     * @author niamh
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Sets the value to a new value.
     * @param value the new value for the value you want.
     *
     * @author niamh
     */
    public void setValue(V value) {
        this.value = value;
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
