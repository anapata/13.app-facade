package jp.co.isken.appfacade.domain.observation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.isken.appfacade.domain.party.Party;
import jp.co.isken.appfacade.domain.phenomenon.BiologicalPhenomenonType;
import jp.co.isken.appfacade.domain.phenomenon.Phenomenon;
import jp.co.isken.appfacade.domain.phenomenon.PhenomenonValue;
import jp.co.isken.appfacade.domain.protocol.Protocol;

public class Observation {

	private int id;
	private Party patient;
	private ObservationConcept observationConcept;
	private Protocol protocol;
	private Date date;
	private Observation newObservation;
	
	private static List<Observation> $instances = new ArrayList<Observation>();
	private static int $counter = 0;

	public Observation(Party patient, BiologicalPhenomenonType phenomenonType, PhenomenonValue value) {
		this.id = $counter++;
		this.patient = patient;
		this.observationConcept = new Phenomenon(phenomenonType, value.getValue());
		$instances.add(this);
	}
	
	public Observation(Party patient, Protocol protocol, Date date) {
		this.patient = patient;
		this.protocol = protocol;
		this.date = date;
		$instances.add(this);
	}
	
	public int getId() {
		return id;
	}

	public Party getPatient() {
		return patient;
	}
	
	public ObservationConcept getObservationConcept() {
		return observationConcept;
	}
	
	public Protocol getProtocol() {
		return protocol;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void reject(Observation newObservation) {
		this.newObservation = newObservation;
	}

	public boolean isRejected() {
		return newObservation != null;
	}

	public static List<Observation> findByParty(Party party) {
		List<Observation> ret = new ArrayList<Observation>();
		for (Observation o : $instances) {
			if (o.getPatient().getId() == party.getId()) {
				ret.add(o);
			}
		}
		return ret;
	}
}
