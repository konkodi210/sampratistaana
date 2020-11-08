package org.sampratistaana;

import static org.sampratistaana.ConnectionFactory.dbSession;

import org.hibernate.Session;
import org.sampratistaana.beans.Donation;
import org.sampratistaana.beans.Member;

public class CreditOperationManager {
	
	/**
	 * Saves the member into the database
	 * @param member 
	 * @return Member number 
	 */
	public Long saveMember(Member member) {
		try(Session session=dbSession()){			
			session.saveOrUpdate(member);
			return member.getMemberNo();
		}
	}
	
	/**
	 * Fetches the Member by id
	 * @param memberId
	 * @return
	 */
	public Member getMember(long memberId) {
		try(Session session=dbSession()){
			return session.get(Member.class, memberId);
		}
	}
	
	/**
	 * Saves the Donation into the database
	 * @param donation 
	 * @return Member number 
	 */
	public Long saveDonation(Donation donation) {
		try(Session session=dbSession()){			
			session.saveOrUpdate(donation);
			return donation.getDonationId();
		}
	}
	
	/**
	 * Fetches the Donation by id
	 * @param donationId
	 * @return
	 */
	public Donation getDonation(long donationId) {
		try(Session session=dbSession()){
			return session.get(Donation.class, donationId);
		}
	}
}
