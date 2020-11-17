package org.sampratistaana.controllers;

import java.io.IOException;

public class DonationEditController extends BaseController{
	public static final String CACHE_KEY="DonationEdit";
	
	public void loadDonations() throws IOException {
		loadForm("DonationList");
	}

	public void saveDonation() throws IOException{
		loadForm("DonationList");
	}
}
