package com.sam.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sam.app.profile.iProfileService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class TestOneProfile {

	@Autowired
    iProfileService profileService;

    @Test
    public void testDefaultProfile() throws Exception {
        String profileName = profileService.getProfileName();
        assertThat(profileName).contains("This is DEVELOPMENT profile.");
    }

}
