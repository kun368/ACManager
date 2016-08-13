package jskills.factorgraphs;

public class KeyedVariable<TKey, TValue> extends Variable<TValue> {

    private final TKey key;

    public KeyedVariable(TKey key, TValue prior, String name, Object... args) {
        super(prior, name, args);
        this.key = key;
    }

    public TKey getKey() {
        return key;
    }
}