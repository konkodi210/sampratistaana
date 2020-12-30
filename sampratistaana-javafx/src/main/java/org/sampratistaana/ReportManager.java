package org.sampratistaana;

import static org.sampratistaana.ConnectionFactory.dbSession;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.sampratistaana.beans.Ledger;

public class ReportManager {

	public List<Ledger> generateFinacialAuditReport(LocalDate fromDate, LocalDate toDate) {
		try(Session session= dbSession()){
			return session
					.createQuery("FROM Ledger l WHERE l.entryDate >=:fromDate AND l.entryDate <= :toDate",Ledger.class)
					.setParameter("fromDate", fromDate)
					.setParameter("toDate", toDate)
					.getResultList();
		}
	}
}
