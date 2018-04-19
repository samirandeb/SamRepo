package com.sam.app.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevelopmentProfile implements iProfileService {

	public String getProfileName() {
		return "This is DEVELOPMENT profile.";
	}

}
