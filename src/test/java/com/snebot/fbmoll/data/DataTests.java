package com.snebot.fbmoll.data;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class DataTests {
    @Test
    void tryContactCopy() {
        Contact contact = new Contact();
        Contact contactData = new Contact();
        contactData.setName("name1");
        contactData.setLastName("lastName1");
        contactData.setEmail("email1@test.com");
        contactData.setAddress("address1");
        contactData.setPhone("111111111");
        contact.copy(contactData);
        Assert.isTrue(contact.similar(contactData), "failed to copy contact data");
    }

    @Test
    void tryContactBook() {
        ContactBook book = new ContactBook();
        book.setName("Test contact book");

        Contact contact1 = new Contact();
        contact1.setName("name1");
        contact1.setLastName("lastName1");
        contact1.setEmail("email1@test.com");
        contact1.setAddress("address1");
        contact1.setPhone("111111111");
        book.addContact(contact1);

        Contact contact2 = new Contact();
        contact2.setName("name2");
        contact2.setLastName("lastName2");
        contact2.setEmail("email2@test.com");
        contact2.setAddress("address2");
        contact2.setPhone("222222222");
        book.addContact(contact2);

        List<Contact> contactList = book.getContacts();
        Assert.isTrue(contactList.get(0).equals(contact1) &&
                        contactList.get(1).equals(contact2),
                "failed to add contacts");

        Contact search1 = book.findByUUID(contact1.getUUID());
        Contact search2 = book.findByUUID(contact2.getUUID());
        Assert.isTrue(search1.equals(contact1) &&
                        search2.equals(contact2),
                "failed to search contact by UUID");

        search1 = book.getContacts(null, contact1.getName(), contact1.getLastName(), contact1.getEmail(), contact1.getAddress(), contact1.getPhone()).get(0);
        search2 = book.getContacts(null, contact2.getName(), null, null, null, null).get(0);
        Assert.isTrue(search1.equals(contact1) &&
                        search2.equals(contact2),
                "failed to search contact by parameters");

        book.removeContact(contact1);
        book.removeContact(contact2);
        Assert.isTrue(book.getContacts().size() == 0, "failed to remove contact");
    }
}
