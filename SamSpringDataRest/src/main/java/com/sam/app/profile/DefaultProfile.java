package com.sam.app.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"sam","default"})
public class DefaultProfile implements iProfileService {

	public String getProfileName() {
		return "This is DEFAULT profile.";
	}

}
