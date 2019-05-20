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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import guru.mwangaza.eap.xmi.reader.XmiReaderUtils;

public abstract class UmlComponent implements Taggable, Identifiable {
	
	private String name;
	private String id;
	private List<TaggedValue> tags = new ArrayList<TaggedValue>();
	private Map<String, TaggedValue> indexedLabels = new HashMap<String, TaggedValue>();
	private String documentation;
	private Map<String, List<UmlStereotype>> stereotypes = new HashMap<>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public List<TaggedValue> getTags() {
		return tags;
	}
	
	public List<TaggedValue> getTagsWithPrefix(String prefix) {
		List<TaggedValue> tagsWithPrefix = new ArrayList<TaggedValue>();
		for(TaggedValue tag: tags) {
			if(tag.getKey().startsWith(prefix)) {
				tagsWithPrefix.add(tag);
			}
		}
		return tagsWithPrefix;
	}

	public void setTags(List<TaggedValue> tags) {
		this.tags = tags;
	}
	
	public void addTag(TaggedValue tag) {
		this.tags.add(tag);
	}
	
	/**
	 * Method replaces a tag with a new tag of the same key if
	 * the tag exists. Otherwise, it simply adds the tag to the collection.
	 * @param tag
	 */
	public void replaceTag(TaggedValue tag) {
		TaggedValue existing = getTaggedValueByKey(tag.getKey());
		if(existing != null) {
			tags.remove(tag);
		}
		addTag(tag);
		indexedLabels.put(tag.key, tag);
	}
	
	public Map<String,TaggedValue> getIndexedLabels() {
		return indexedLabels;
	}
	
	public void setIndexedLabels(Map<String, TaggedValue> indexedLabels) {
		this.indexedLabels = indexedLabels;
	}
	
	/**
	 * Returns the boolean value corresponding to the parsed tagged string value ("true", "false")
	 * otherwise returns null;
	 * 
	 * @param tag
	 * @return
	 */
	public Boolean getTaggedValueAsBoolean(String key) {
		TaggedValue tag = getTaggedValueByKey(key);
		Boolean value = null;
		if(tag != null && tag.getValue() != null) {
			value = Boolean.parseBoolean(tag.getValue());
		}
		return value;
	}
	
	/**
	 * Returns the tag's value as a string.
	 * Returns null if the value of the tag is null or if the
	 * tag does not exist.
	 * 
	 * @param tag
	 * @return
	 */
	public String getTaggedValueAsString(String key) {
		TaggedValue tag = getTaggedValueByKey(key);
		if(tag != null) {
			return tag.getValue();
		} else {
			return null;
		}
	}

	public Map<String, List<UmlStereotype>> getStereotypes() {
		return stereotypes;
	}

	public void setStereotype(Map<String, List<UmlStereotype>> stereotypes) {
		this.stereotypes = stereotypes;
	}

	public void addStereotype(UmlStereotype stereotype) {
		List<UmlStereotype> stereotypes = this.stereotypes.get(stereotype.getName());
		if(stereotypes == null) {
			stereotypes = new ArrayList<>();
			this.stereotypes.put(stereotype.getName(), stereotypes);
		}
		stereotypes. add(stereotype);
	}
	
	/**
	 * Finds tags that differ only in their numeric suffix and groups them. For instance,
	 * 
	 * profile.fhir.element.type.1=A
	 * profile.fhir.element.type.2=B
	 * 
	 * where the prefix is profile.fhir.element.type
	 * 
	 * @param tagPrefix
	 * @return
	 */
	public List<TaggedValue> groupIndexedTags(String tagPrefix) {
		List<TaggedValue> groupedTags = new ArrayList<TaggedValue>();
		for(TaggedValue tag : tags) {
			if(tag.getKey().length() > tagPrefix.length() && tag.getKey().startsWith(tagPrefix)) {
				String suffix = tag.getKey().substring(tagPrefix.length() + 1, tag.getKey().length());
				if(XmiReaderUtils.isInteger(suffix)) {
					groupedTags.add(tag);
				}
			}
		}
		return groupedTags;
	}
	
	public boolean hasTags() {
		return tags.size() > 0;
	}
	
	public boolean hasNoTags() {
		return !hasTags();
	}

	public void buildTaggedValueIndex() {
		indexedLabels.clear();
		for(TaggedValue tag : tags) {
			indexedLabels.put(tag.key, tag);
		}
	}
	
	public TaggedValue getTaggedValueByKey(String key) {
		return indexedLabels.get(key);
	}
	
	public UmlComponent clone() {
		try {
			UmlComponent clone = (UmlComponent)super.clone();
			clone.setName(this.getName());
			clone.setId(this.getId());
			clone.setTags(new ArrayList<TaggedValue>());
			clone.getTags().addAll(this.getTags());
			clone.setIndexedLabels(new HashMap<String, TaggedValue>());
			clone.getIndexedLabels().putAll(clone.getIndexedLabels());
			return clone;
	      } catch (CloneNotSupportedException e) {
	        throw new RuntimeException("Error cloning object", e);
	      }
	}
	
}
