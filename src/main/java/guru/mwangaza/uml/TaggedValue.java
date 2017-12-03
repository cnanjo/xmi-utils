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
package guru.mwangaza.uml;

/**
 * Represents a Name-Value pair used to further
 * annotate a UML component.
 * 
 * @author cnanjo
 *
 */
public class TaggedValue {
	
	public String key;
	public String value;

	public TaggedValue(String key) {
		this.key = key;
	}
	
	public TaggedValue(String key, String value) {
		this(key);
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder(key);
		if(value != null) {
			builder.append("=").append(value);
		}
		return builder.toString();
	}
}
