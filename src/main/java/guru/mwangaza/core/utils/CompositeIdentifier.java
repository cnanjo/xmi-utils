/**
 * Copyright (C) 2013 - 2017 Claude Nanjo
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created by cnanjo.
 */
package guru.mwangaza.core.utils;

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
