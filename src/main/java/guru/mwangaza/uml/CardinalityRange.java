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

public class CardinalityRange implements Cloneable {
	
	private Integer low;
	private Integer high;
	
	public CardinalityRange() {}

	public CardinalityRange(Integer low, Integer high) {
		this();
		this.low = low;
		this.high = high;
	}

	public Integer getLow() {
		return low;
	}

	public void setLow(Integer low) {
		this.low = low;
	}

	public Integer getHigh() {
		return high;
	}

	public void setHigh(Integer high) {
		this.high = high;
	}
	
	public void validate(CardinalityRange range) {
		if(range.getLow() != null && getLow() != null && getLow() > range.getLow()) {
			throw new RuntimeException("Invalid Constraint - Cannot loosen low cardinality bound");
		}
		if(range.getHigh() != null && getHigh() != null && getHigh() != -1) {
			if (range.getHigh() == -1 || getHigh() < range.getHigh()) {
				throw new RuntimeException("Invalid Constraint - Cannot loosen high cardinality bound");
			}
		}
	}
	
	public CardinalityRange clone() {
		CardinalityRange range = new CardinalityRange();
		range.setLow(this.low);
		range.setHigh(this.high);
		return range;
	}
}
