package org.sampratistaana;

import org.sampratistaana.beans.Member;
import static org.sampratistaana.ConnectionFactory.dbSession;

import org.hibernate.Session;

public class CreditOperationManager {
	
	/**
	 * Saves the member into the database
	 * @param member 
	 * @return Member number 
	 */
	public Long saveMember(Member member) {
		try(Session session=dbSession()){
			session.save(member);
			return member.getMemberNo();
		}
	}
	
	public Member getMember(int memberId) {
		try(Session session=dbSession()){
			return null;
		}
	}
}
