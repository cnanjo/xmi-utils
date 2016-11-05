package guru.mwangaza.core.utils;

/**
 * Created by cnanjo on 11/4/16.
 */
public class CompositeIdentifier {

    public static final char DEFAULT_DELIMITER = '#';
    private String compositeId;
    private String modelName;
    private String identifier;

    public CompositeIdentifier() {}

    public CompositeIdentifier(String modelName, String identifier) {
        this.modelName = modelName;
        this.identifier = identifier;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(String compositeId) {
        this.compositeId = compositeId;
    }

    public boolean isComposite() {
        if(modelName != null && identifier != null) {
            return true;
        } else {
            return false;
        }
    }

    public static CompositeIdentifier getCompositeIdentifier(String compositeIdentifier) {
        return getCompositeIdentifier(compositeIdentifier, DEFAULT_DELIMITER);
    }

    public static CompositeIdentifier getCompositeIdentifier(String compositeIdentifier, char delimiter) {
        String[] idComponents = compositeIdentifier.split("" + DEFAULT_DELIMITER);
        if(idComponents.length == 2) {
            CompositeIdentifier identifier = new CompositeIdentifier(idComponents[0], idComponents[1]);
            identifier.setCompositeId(compositeIdentifier);
            return identifier;
        } else if(idComponents.length == 1) {
            CompositeIdentifier identifier = new CompositeIdentifier(null, idComponents[0]);
            identifier.setCompositeId(compositeIdentifier);
            return identifier;
        } else {
            throw new RuntimeException("Invalid identifier " + compositeIdentifier);
        }
    }
}
