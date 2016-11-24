package guru.mwangaza.uml;

import guru.mwangaza.core.utils.CompositeIdentifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cnanjo on 11/3/16.
 */
public class UmlTemplateBinding {
    private String id;
    private UmlTemplateSignature signature;
    private String signatureRefId;
    private List<ParameterSubstitution> bindings;

    public UmlTemplateBinding() {
        this.bindings = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UmlTemplateSignature getSignature() {
        return signature;
    }

    public void setSignature(UmlTemplateSignature signature) {
        this.signature = signature;
    }

    public String getSignatureRefId() {
        return signatureRefId;
    }

    public void setSignatureRefId(String signatureRefId) {
        this.signatureRefId = signatureRefId;
    }

    public List<ParameterSubstitution> getBindings() {
        return bindings;
    }

    public void setBindings(List<ParameterSubstitution> bindings) {
        this.bindings = bindings;
    }

    public void addBinding(ParameterSubstitution parameterSubstitution) {
        bindings.add(parameterSubstitution);
    }

    public void addBinding(UmlTemplateParameter param, UmlClass umlClass) {
        ParameterSubstitution parameter = new ParameterSubstitution(param, umlClass);
        bindings.add(parameter);
    }

    public void populateTemplateBinding(UmlModel model) {
        signature = (UmlTemplateSignature) model.getObjectById(signatureRefId);
        if(signature == null) {
            throw new RuntimeException("Signature not found for ID: " + signatureRefId);
        }
        for(ParameterSubstitution substitution : bindings) {
            CompositeIdentifier compositeIdentifier = CompositeIdentifier.getCompositeIdentifier(substitution.getFormalParameterRefId());
            UmlTemplateParameter formal = signature.getTemplateparameter(compositeIdentifier.getIdentifier());
            substitution.setFormalParameter(formal);
            UmlClass actual = (UmlClass)model.getObjectById(substitution.getActualParameterRefId());
            if(actual == null) {
                throw new RuntimeException("Class not found for ID: " + substitution.getActualParameterRefId());
            }
            substitution.setActualParameter(actual);
        }
    }
}
