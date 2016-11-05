package guru.mwangaza.uml;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cnanjo on 10/3/16.
 */
public class UmlTemplateSignature extends UmlComponent implements Identifiable, Cloneable {
    private List<UmlTemplateParameter> parameters;
    private UmlClass owningClass;
    private Map<String, UmlTemplateParameter> idToParameterMap;

    public UmlTemplateSignature() {
        parameters = new ArrayList<UmlTemplateParameter>();
        idToParameterMap = new HashMap<String, UmlTemplateParameter>();
    }

    public List<UmlTemplateParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<UmlTemplateParameter> parameters) {
        this.parameters = parameters;
    }

    public UmlClass getOwningClass() {
        return owningClass;
    }

    public void setOwningClass(UmlClass owningClass) {
        this.owningClass = owningClass;
    }

    public void addTemplateParameter(UmlTemplateParameter parameter) {
        parameters.add(parameter);
        idToParameterMap.put(parameter.getId(), parameter);
    }

    public UmlTemplateParameter getTemplateparameter(String parameterId) {
        return idToParameterMap.get(parameterId);
    }

    public void populateTemplateParameters(UmlModel model) {
        for(UmlTemplateParameter parameter : parameters) {
                parameter.populateParameterType(model);
        }
    }

    public void populateTemplateSignature(UmlClass parent, UmlModel model) {
        populateTemplateParameters(model);
        setOwningClass(parent);
    }

    public String toString() {
        return "id: " + getId() + " - params: " + parameters;
    }
}
