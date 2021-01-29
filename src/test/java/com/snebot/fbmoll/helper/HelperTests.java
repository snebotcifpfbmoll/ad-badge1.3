package com.snebot.fbmoll.helper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class HelperTests {
    @Test
    void tryContactValidation() {
        Assert.isTrue(ContactValidator.validateEmail("snebot@cifpfbmoll.eu") &&
                !ContactValidator.validateEmail("snebotcifpfb$molleu@"), "failed to evaluate email address");
        Assert.isTrue(ContactValidator.validatePhone("123456789") &&
                !ContactValidator.validatePhone("12345s678") &&
                !ContactValidator.validatePhone("1234567890"), "failed to evaluate phone number");
    }
}
