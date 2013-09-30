package jp.co.isken.appfacade.bloodDonation.facade;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.isken.appfacade.domain.observation.Observation;
import jp.co.isken.appfacade.domain.party.Party;
import jp.co.isken.appfacade.domain.phenomenon.BiologicalPhenomenonType;
import jp.co.isken.appfacade.domain.protocol.Protocol;
import jp.co.isken.appfacade.facade.bloodDonation.BloodDonor;
import jp.co.isken.appfacade.facade.bloodDonation.BloodType;

import org.junit.Before;
import org.junit.Test;

public class BloodDonorTest {
	public static Party patient;

	@Before
	public void setUp() {
		patient = new Party("患者さん");
		new Observation(patient, BiologicalPhenomenonType.血液型, BloodType.B);
		new Observation(patient, Protocol.輸血, date("2013/10/11"));
	}
	
	@Test
	public void ファサードのテスト() {
		BloodDonor donor = BloodDonor.find(patient);

		// 属性が取り出せることの確認
		assertThat(donor.getName(), is("患者さん"));
		assertThat(donor.getBlood(), is(BloodType.B));
		assertThat(donor.getLastBloodSupplyDate(), is(date("2013/10/11")));

		// 属性が更新できることの確認
		donor.updateBlood(BloodType.AB);
		donor.updateBloodSupplyDate(date("2013/10/13"));
		assertThat(donor.getBlood(), is(BloodType.AB));
		assertThat(donor.getLastBloodSupplyDate(), is(date("2013/10/13")));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void 最終輸血日を前に更新しようとしたらエラーになること() {
		BloodDonor donor = BloodDonor.find(patient);
		donor.updateBloodSupplyDate(date("2013/10/10"));
	}
	
	private Date date(String sDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = sdf.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
