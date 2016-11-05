package guru.mwangaza.uml;

/**
 * Created by cnanjo on 11/3/16.
 */
public class ParameterSubstitution {

    private String Id;
    private UmlTemplateParameter formalParameter;
    private String formalParameterRefId;
    private UmlClass actualParameter;
    private String actualParameterRefId;

    public ParameterSubstitution() {}

    public ParameterSubstitution(UmlTemplateParameter formal, UmlClass actual) {
        this.formalParameter = formal;
        this.actualParameter = actual;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public UmlTemplateParameter getFormalParameter() {
        return formalParameter;
    }

    public void setFormalParameter(UmlTemplateParameter formalParameter) {
        this.formalParameter = formalParameter;
    }

    public String getFormalParameterRefId() {
        return formalParameterRefId;
    }

    public void setFormalParameterRefId(String formalParameterRefId) {
        this.formalParameterRefId = formalParameterRefId;
    }

    public UmlClass getActualParameter() {
        return actualParameter;
    }

    public void setActualParameter(UmlClass actualParameter) {
        this.actualParameter = actualParameter;
    }

    public String getActualParameterRefId() {
        return actualParameterRefId;
    }

    public void setActualParameterRefId(String actualParameterRefId) {
        this.actualParameterRefId = actualParameterRefId;
    }

    public void populateParameterSubstitution(UmlModel model) {
        formalParameter = (UmlTemplateParameter) model.getIdMap().get(formalParameterRefId);
        actualParameter = (UmlClass) model.getIdMap().get(actualParameterRefId);
    }
}
