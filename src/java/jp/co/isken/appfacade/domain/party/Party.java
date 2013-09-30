package jp.co.isken.appfacade.domain.party;

import java.util.ArrayList;
import java.util.List;

import jp.co.isken.appfacade.domain.observation.Observation;
import jp.co.isken.appfacade.domain.phenomenon.BiologicalPhenomenonType;
import jp.co.isken.appfacade.domain.phenomenon.Phenomenon;

public class Party {

	private int id;
	private String name;
	
	private static List<Party> $instances = new ArrayList<Party>();
	private static int $counter = 0;

	public Party(String name) {
		this.id = $counter++;
		this.name = name;
		$instances.add(this);
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public static Party findById(int id) {
		for (Party p : $instances) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

	public Observation getObservation(BiologicalPhenomenonType phenomenonType) {
		List<Observation> observations = getObservations(phenomenonType);
		if (observations.size() <= 0) {
			return null;
		}
		return observations.get(0);
	}
	
	public String valueOf(BiologicalPhenomenonType phenomenonType) {
		return getPhenomenon(phenomenonType).getValue();
	}

	public List<Observation> getObservations(BiologicalPhenomenonType phenomenonType) {
		ArrayList<Observation> ret = new ArrayList<Observation>();
		for(Observation o : Observation.findByParty(this)) {
			Phenomenon phenomenon = (Phenomenon) o.getObservationConcept();
			if (o.isRejected() || phenomenon == null) {
				continue;
			}
			if (phenomenon.getPhenomenonType() == phenomenonType) {
				ret.add(o);
			}
		}
		return ret;
	}

	public List<Observation> getObservations() {
		ArrayList<Observation> ret = new ArrayList<Observation>();
		for(Observation o : Observation.findByParty(this)) {
			if (o.isRejected()) {
				continue;
			}
			ret.add(o);
		}
		return ret;
	}

	private Phenomenon getPhenomenon(BiologicalPhenomenonType phenomenonType) {
		Observation observation = getObservation(phenomenonType);
		Phenomenon phenomenon = (Phenomenon) observation.getObservationConcept();
		return phenomenon;
	}
}
