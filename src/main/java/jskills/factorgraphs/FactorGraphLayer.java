package jskills.factorgraphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class FactorGraphLayer<TParentFactorGraph extends FactorGraph<TParentFactorGraph>, TValue,
        TBaseVariable extends Variable<TValue>, TInputVariable extends Variable<TValue>, TFactor extends Factor<TValue>,
        TOutputVariable extends Variable<TValue>> extends FactorGraphLayerBase<TValue> {

    private final List<TFactor> localFactors;
    private final List<List<TOutputVariable>> outputVariablesGroups;
    private List<List<TInputVariable>> inputVariablesGroups;
    protected TParentFactorGraph parentFactorGraph;

    protected FactorGraphLayer(TParentFactorGraph parentGraph) {
        this.localFactors = new ArrayList<TFactor>();
        this.outputVariablesGroups = new ArrayList<List<TOutputVariable>>();
        this.inputVariablesGroups = new ArrayList<List<TInputVariable>>();
        this.parentFactorGraph = parentGraph;
    }

    protected List<List<TInputVariable>> getInputVariablesGroups() {
        return inputVariablesGroups;
    }

    protected Schedule<TValue> ScheduleSequence(Collection<Schedule<TValue>> itemsToSequence,
                                                String nameFormat, Object... args) {
        final String formattedName = String.format(nameFormat, args);
        return new ScheduleSequence<TValue>(formattedName, itemsToSequence);
    }

    protected void AddLayerFactor(TFactor factor) {
        localFactors.add(factor);
    }

    public TParentFactorGraph getParentFactorGraph() { return parentFactorGraph; }

    public List<List<TOutputVariable>> getOutputVariablesGroups(){
        return outputVariablesGroups;
    }
    
    public void addOutputVariableGroup(List<TOutputVariable> group) {
        outputVariablesGroups.add(group);
    }
    
    public void addOutputVariable(TOutputVariable var) {
        List<TOutputVariable> g = new ArrayList<TOutputVariable>(1); g.add(var);
        addOutputVariableGroup(g);
    }

    public List<TFactor> getLocalFactors() {
        return localFactors;
    }

    @Override
    @SuppressWarnings("unchecked") // TODO there has to be a safer way to do this
    public Collection<Factor<TValue>> getUntypedFactors() {
        return (Collection<Factor<TValue>>) localFactors;
    }

    @Override
    @SuppressWarnings("unchecked") // TODO there has to be a safer way to do this
    public void setRawInputVariablesGroups(Object value) {
        inputVariablesGroups = (List<List<TInputVariable>>)value;
    }

    @Override
    public Object getRawOutputVariablesGroups() {
        return outputVariablesGroups;
    }
}