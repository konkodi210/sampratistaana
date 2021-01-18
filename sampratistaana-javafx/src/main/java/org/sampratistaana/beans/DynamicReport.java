package org.sampratistaana.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.sampratistaana.Messages;

@Entity
@Table(name = "DYN_SQL_REPORT")
public class DynamicReport implements Serializable{
	private static final long serialVersionUID = -6785881677914552478L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DYN_SQL_REPORT_ID", nullable = false)
	private long reportId;
	
	@Column(name = "REPORT_NAME", nullable = false)
	private String reportName;
	
	@Column(name = "REPORT_QUERY", nullable = false)
	private String reportQuery;

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportQuery() {
		return reportQuery;
	}

	public void setReportQuery(String reportQuery) {
		this.reportQuery = reportQuery;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return Messages.getMessage(reportName);
	}
}
