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

import java.util.List;

/**
 * Contract supporting the tagging of resources.
 * A tag is a name-value pair that can be used to annotate
 * a resource.
 * 
 * @author cnanjo
 *
 */
public interface Taggable {
	
	public List<TaggedValue> getTags();
	public void setTags(List<TaggedValue> tags);
	public void addTag(TaggedValue tag);
	public void buildTaggedValueIndex();
	public TaggedValue getTaggedValueByKey(String key);
	public Boolean getTaggedValueAsBoolean(String key);
	public String getTaggedValueAsString(String key);
	public boolean hasTags();
	public boolean hasNoTags();
}
