package org.sampratistaana;

import org.sampratistaana.beans.Member;
import static org.sampratistaana.ConnectionFactory.dbSession;

import org.hibernate.Session;

public class CreditOperationManager {
	public Member saveMember(Member member) {
		try(Session session=dbSession()){
			member=(Member)session.save(member);
		}
		return member;
	}
}
