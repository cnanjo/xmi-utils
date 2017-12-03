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
package guru.mwangaza.eap.xmi.reader;

import groovy.xml.Namespace;

import javax.naming.Name;
import java.util.HashMap;
import java.util.Map;

/**
 * Common often shared state to configure XMI readers. The XmiReaderContext contains information that may
 * vary from XMI file to XMI file such as namespaces assigned to prefixes or information about the parsing
 * of extensions.
 */
public class XmiReaderContext {

    public static final String DEFAULT_XMI_NAMESPACE = "http://www.omg.org/spec/XMI/20131001";
    public static final String DEFAULT_UML_NAMESPACE = "http://www.omg.org/spec/UML/20131001";
    public static final String DEFAULT_XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String AML_RM_NAMESPACE = "http://www.omg.org/spec/AML/20150501/ReferenceModelProfile.xmi";

    Map<String, Namespace> namespaceMap = new HashMap<>();

    public XmiReaderContext() {}

    public void addNamespace(String namespacePrefix, Namespace namespace) {
        namespaceMap.put(namespacePrefix, namespace);
    }

    public void addNamespace(String namespacePrefix, String uri) {
        addNamespace(namespacePrefix, new groovy.xml.Namespace(uri, namespacePrefix));
    }

    public Namespace getNamespace(String namespacePrefix) {
        return namespaceMap.get(namespacePrefix);
    }
}
