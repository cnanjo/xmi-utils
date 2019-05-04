package guru.mwangaza.uml;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UmlOperation extends UmlComponent {

    private List<String> argumentIds;
    private List<UmlParameter> parameters;
    private UmlVisibilityEnum visibility;
    private boolean isStatic;
    private boolean isFinal;
    private boolean isAbstract;
    private boolean isConstructor;
    private String methodBody;

    public UmlOperation() {
        argumentIds = new ArrayList<>();
        parameters = new ArrayList<>();
    }

    public UmlOperation(String name, List<UmlParameter> parameters, boolean isStatic, boolean isFinal, boolean isConstructor) {
        this();
        setName(name);
        this.parameters = parameters;
        this.isStatic = isStatic;
        this.isFinal = isFinal;
        this.isConstructor = isConstructor;
    }

    public UmlOperation(String name, List<UmlParameter> parameters, boolean isConstructor) {
        this();
        setName(name);
        this.parameters = parameters;
        this.isConstructor = isConstructor;
    }

    public UmlParameter getReturnType() {
        BaseClassifier returnType = null;
        List<UmlParameter> returnTypes = getParameters().stream().filter(param -> param.isReturnType()).collect(Collectors.toList());
        if(returnTypes == null || returnTypes.size() == 0) {
            return null;
        } else if(returnTypes.size() == 1) {
            return returnTypes.get(0);
        } else {
            throw new RuntimeException("More than one return type defined for operation");
        }

    }

    public void setReturnType(UmlParameter returnType) {
        this.getParameters().add(returnType);
    }

    public List<String> getArgumentIds() {
        return argumentIds;
    }

    public void setArgumentIds(List<String> argumentIds) {
        this.argumentIds = argumentIds;
    }

    public List<UmlParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<UmlParameter> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(UmlParameter parameter) {
        this.parameters.add(parameter);
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public boolean isConstructor() {
        return isConstructor;
    }

    public boolean isNotConstructor() {
        return !isConstructor();
    }

    public void setConstructor(boolean constructor) {
        isConstructor = constructor;
    }

    public String getMethodBody() {
        return methodBody;
    }

    public void setMethodBody(String methodBody) {
        this.methodBody = methodBody;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }

    public UmlVisibilityEnum getVisibility() {
        return visibility;
    }

    public void setVisibility(UmlVisibilityEnum visibility) {
        this.visibility = visibility;
    }
}
