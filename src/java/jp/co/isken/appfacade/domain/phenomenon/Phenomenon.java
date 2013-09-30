package jp.co.isken.appfacade.domain.phenomenon;

import jp.co.isken.appfacade.domain.observation.ObservationConcept;

public class Phenomenon extends ObservationConcept {

	private BiologicalPhenomenonType phenomenonType;
	private String value;

	public Phenomenon(BiologicalPhenomenonType phenomenonType, String value) {
		this.phenomenonType = phenomenonType;
		this.value = value;
	}
	
	public BiologicalPhenomenonType getPhenomenonType() {
		return phenomenonType;
	}
	
	public String getValue() {
		return value;
	}

}
