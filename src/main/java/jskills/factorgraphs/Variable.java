package jskills.factorgraphs;

public class Variable<TValue>
{
    private final String name;
    private final TValue prior;
    private TValue value;

    public Variable(TValue prior, String name, Object... args) {
        this.name = "Variable[" + String.format(name, args) + "]";
        this.prior = prior;
        resetToPrior();
    }

    public TValue getValue() {
        return value;
    }

    public void setValue(TValue value) {
        this.value = value;
    }

    public void resetToPrior() { value = prior; }

    @Override public String toString() { return name; }
}