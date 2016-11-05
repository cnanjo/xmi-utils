package guru.mwangaza.uml;

/**
 * Created by cnanjo on 10/3/16.
 */
public class UmlTemplateParameter extends UmlComponent implements Identifiable, Cloneable {
    private UmlClass type;
    private String typeId;
    private String elementId;
    private boolean isParameterReference = true;

    public UmlTemplateParameter() {
    }

    public UmlClass getType() {
        return type;
    }

    public void setType(UmlClass type) {
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public boolean isParameterReference() {
        return isParameterReference;
    }

    public void setParameterReference(boolean parameterReference) {
        isParameterReference = parameterReference;
    }

    public void populateParameterType(UmlModel model) {
        if(typeId != null) {
            type = (UmlClass) model.getIdMap().get(typeId);
        } else {

        }
    }

    public String toString() {
        return getName() + "[id: " + getId() + ", type:" + getType() + ", element id: " + elementId + "]";
    }
}
