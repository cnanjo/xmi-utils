package guru.mwangaza.uml;

import java.io.Serializable;
import java.util.List;

public class UmlParameter extends UmlComponent implements Serializable {

    private BaseClassifier type;
    private String typeId;
    private ParameterDirectionEnum direction = ParameterDirectionEnum.IN; //TODO Check that it is the default in UML 2.5
    private CardinalityRange multiplicity;
    private String defaultValue;
    private UmlVisibilityEnum visibility;
    private boolean isUnique;
    private boolean isOrdered;

    public UmlParameter() {}

    public UmlParameter(String name, String id) {
        this.setName(name);
        this.setId(id);
    }

    public UmlParameter(String name, String id, ParameterDirectionEnum direction, UmlVisibilityEnum visibility) {
        this.setName(name);
        this.setId(id);
        this.direction = direction;
        this.visibility = visibility;
    }

    public BaseClassifier getType() {
        return type;
    }

    public void setType(BaseClassifier type) {
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public ParameterDirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(ParameterDirectionEnum direction) {
        this.direction = direction;
    }

    public CardinalityRange getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(CardinalityRange multiplicity) {
        this.multiplicity = multiplicity;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public UmlVisibilityEnum getVisibility() {
        return visibility;
    }

    public void setVisibility(UmlVisibilityEnum visibility) {
        this.visibility = visibility;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public void setOrdered(boolean ordered) {
        isOrdered = ordered;
    }

    public boolean isReturnType() {
        return direction == ParameterDirectionEnum.RETURN;
    }
}
