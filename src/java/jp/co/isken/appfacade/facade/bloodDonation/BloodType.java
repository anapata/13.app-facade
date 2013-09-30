package jp.co.isken.appfacade.facade.bloodDonation;

import jp.co.isken.appfacade.domain.phenomenon.PhenomenonValue;

public enum BloodType implements PhenomenonValue {
	A, B, O, AB;

	@Override
	public String getValue() {
		return this.toString();
	}
}
