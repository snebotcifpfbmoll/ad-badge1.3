package com.snebot.fbmoll.util;

import com.snebot.fbmoll.data.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class DummyUtilTests {
    @Test
    void tryGenerateObjects() {
        DummyUtils dummyUtils = new DummyUtils();
        int size = 50;
        List<Contact> contactList = dummyUtils.generateObjects(Contact.class, size);
        Assert.isTrue(contactList != null && contactList.size() == size, "failed to generate objects with DummyUtils");
    }
}
