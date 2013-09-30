package jp.co.isken.appfacade.facade.bloodDonation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import jp.co.isken.appfacade.domain.observation.Observation;
import jp.co.isken.appfacade.domain.party.Party;
import jp.co.isken.appfacade.domain.phenomenon.BiologicalPhenomenonType;
import jp.co.isken.appfacade.domain.protocol.Protocol;

public class BloodDonor {

	private Party target;

	public BloodDonor(Party target) {
		this.target = target;
	}

	public String getName() {
		return target.getName();
	}

	public BloodType getBlood() {
		return BloodType.valueOf(target.valueOf(BiologicalPhenomenonType.血液型));
	}
	
	public void updateBlood(BloodType bloodType) {
		// 過去の観測を検索
		Observation oldObs = target.getObservation(BiologicalPhenomenonType.血液型);
		// 新しい観測を生成
		Observation newObs = new Observation(target, BiologicalPhenomenonType.血液型, bloodType);
		// 過去の観測を否認する
		oldObs.reject(newObs);
	}

	public Date getLastBloodSupplyDate() {
		// 輸血の手続きを検索する
		ArrayList<Date> supplyDate = new ArrayList<Date>();
		for (Observation o : target.getObservations()) {
			if (o.getProtocol() == Protocol.輸血) {
				supplyDate.add(o.getDate());
			}
		}
		// 日付でソートする
		Collections.sort(supplyDate);
		if (supplyDate.size() <= 0) {
			return null;
		}
		// 一番日付が大きいものを返却する
		return supplyDate.get(supplyDate.size() - 1);
	}
	
	public void updateBloodSupplyDate(Date date) {
		if (!isValidSupplyDate(getLastBloodSupplyDate(), date)) {
			throw new IllegalArgumentException("エラー");
		}
		new Observation(target, Protocol.輸血, date);
	}
	
	public boolean isValidSupplyDate(Date last, Date update) {
		if (update.after(last)) {
			return true;
		}
		return false;
	}
	
	public static BloodDonor find(Party party) {
		return new BloodDonor(Party.findById(party.getId()));
	}

}
