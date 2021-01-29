package com.snebot.fbmoll.data;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@XmlRootElement(name = "contactBook")
@XmlAccessorType(XmlAccessType.FIELD)
public class ContactBook implements Serializable {
    @XmlAttribute(name = "name")
    private String name = null;

    @XmlElementWrapper(name = "contacts")
    @XmlElement(name = "contact")
    private List<Contact> contacts = new ArrayList<>();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contact> getContacts() {
        return this.contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = new ArrayList<>();
        contacts.forEach(this::addContact);
    }

    public ContactBook() {
    }

    public ContactBook(String name, List<Contact> contacts) {
        this.name = name;
        setContacts(contacts);
    }

    /**
     * Finds contact by its unique identifier.
     *
     * @param uid Unique identifier.
     * @return Unique contact.
     */
    public Contact findByUUID(String uid) {
        for (Contact contact : this.contacts)
            if (StringUtils.equals(contact.getUUID(), uid)) return contact;
        return null;
    }

    /**
     * Generates a unique identifier and makes sure it is not already in use.
     *
     * @return Unique identifier.
     */
    public String generateUUID() {
        boolean repeated = false;
        String uuid = null;
        do {
            uuid = UUID.randomUUID().toString();
            for (Contact c : this.contacts) {
                repeated = StringUtils.equals(c.getUUID(), uuid);
                if (repeated) break;
            }
        } while (repeated);
        return uuid;
    }

    /**
     * Add contact to contact list.
     *
     * @param contact Contact to add.
     */
    public void addContact(Contact contact) {
        contact.setUUID(generateUUID());
        this.contacts.add(contact);
    }

    /**
     * Remove contact from contact list.
     *
     * @param uid Unique identifier.
     * @param name Contact name.
     * @param lastName Contact last name.
     * @param email Contact email.
     * @param address Contact address.
     * @param phone Contact phone.
     * @return List of removed contacts.
     */
    public List<Contact> removeContact(String uid, String name, String lastName, String email, String address, String phone) {
        List<Contact> contacts = getContacts(uid, name, lastName, email, address, phone);
        contacts.forEach(contact -> {
            this.contacts.remove(contact);
        });
        return contacts;
    }

    /**
     * Remove contact from contact list.
     *
     * @param contact Contact to remove.
     * @return List of removed contacts.
     */
    public List<Contact> removeContact(Contact contact) {
        return removeContact(contact.getUUID(), contact.getName(), contact.getLastName(), contact.getEmail(), contact.getAddress(), contact.getPhone());
    }

    /**
     * Update contact with new data.
     *
     * @param uid Unique identifier.
     * @param contact New contact data.
     * @return Changed contact data.
     */
    public Contact updateContact(String uid, Contact contact) {
        Contact c = findByUUID(uid);
        if (c != null) c.copy(contact);
        return c;
    }

    /**
     * Search contact by attributes.
     *
     * @param uid Unique identifier.
     * @param name Contact name.
     * @param lastName Contact last name.
     * @param email Contact email.
     * @param address Contact address.
     * @param phone Contact phone.
     * @return List of matching contacts.
     */
    public List<Contact> getContacts(String uid, String name, String lastName, String email, String address, String phone) {
        List<Contact> contactList = new ArrayList<>();
        for (Contact c : this.contacts) {
            if ((uid == null || StringUtils.equals(c.getUUID(), uid)) &&
                    (name == null || StringUtils.equals(c.getName(), name)) &&
                    (lastName == null || StringUtils.equals(c.getLastName(), lastName)) &&
                    (email == null || StringUtils.equals(c.getEmail(), email)) &&
                    (address == null || StringUtils.equals(c.getAddress(), address)) &&
                    (phone == null || StringUtils.equals(c.getPhone(), phone))) contactList.add(c);
        }
        return contactList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ContactBook) {
            ContactBook contactBook = (ContactBook) obj;
            boolean result = getName().equals(contactBook.getName());
            for (int i = 0; i < this.contacts.size() && !result; i++) {
                Contact contact = this.contacts.get(i);
                result = contact.equals(contactBook.contacts.get(i));
            }
            return result;
        }
        return super.equals(obj);
    }
}
