package guru.mwangaza.uml;

import java.util.ArrayList;
import java.util.List;

public class BaseClassifier extends UmlComponent implements Identifiable, Cloneable {

    private String description;
    private List<BaseClassifier> generalizations = new ArrayList<BaseClassifier>();
    private List<String> generalizationIds = new ArrayList<String>();
    private List<UmlProperty> properties = new ArrayList<UmlProperty>();
    private UmlTemplateSignature templateSignature;
    private UmlTemplateBinding templateBinding;
    private UmlModel model;
    private UmlPackage parentPackage;
    private boolean isPrimitive = false;
    private boolean isAbstract = false;
    private boolean isBinding = false;

    public BaseClassifier() {}

    public BaseClassifier(String name) {
        this();
        setName(name);
    }

    public List<UmlProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<UmlProperty> attributes) {
        this.properties = attributes;
    }

    public void addProperty(UmlProperty attribute) {
        properties.add(attribute);
    }

    public List<BaseClassifier> getGeneralizations() {
        return generalizations;
    }

    public void setGeneralizations(List<BaseClassifier> generalizations) {
        this.generalizations = generalizations;
    }

    public void addGeneralization(UmlClass generalization) {
        generalizations.add(generalization);
    }

    public List<String> getGeneralizationIds() {
        return generalizationIds;
    }

    public void setGeneralizationIds(List<String> generalizationIds) {
        this.generalizationIds = generalizationIds;
    }

    public void addGeneralizationId(String generalizationId) {
        generalizationIds.add(generalizationId);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UmlModel getModel() {
        return model;
    }

    public void setModel(UmlModel model) {
        this.model = model;
    }

    public boolean isPrimitive() {
        return isPrimitive;
    }

    public void setPrimitive(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    public boolean isGenericType() {
        return templateSignature != null;
    }

    public UmlTemplateSignature getTemplateSignature() {
        return templateSignature;
    }

    public void setTemplateSignature(UmlTemplateSignature templateSignature) {
        this.templateSignature = templateSignature;
    }

    public UmlTemplateBinding getTemplateBinding() {
        return templateBinding;
    }

    public void setTemplateBinding(UmlTemplateBinding templateBinding) {
        this.templateBinding = templateBinding;
    }

    public boolean isBinding() {
        return isBinding;
    }

    public void setBinding(boolean binding) {
        isBinding = binding;
    }

    public UmlPackage getParentPackage() {
        return parentPackage;
    }

    public void setParentPackage(UmlPackage parentPackage) {
        this.parentPackage = parentPackage;
    }
}
