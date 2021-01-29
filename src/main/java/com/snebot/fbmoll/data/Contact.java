package com.snebot.fbmoll.data;

import com.snebot.fbmoll.helper.ContactValidator;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "contact")
@XmlAccessorType(XmlAccessType.FIELD)
public class Contact implements Serializable {
    @XmlElement(name = "uuid")
    private String uuid = null;

    @XmlElement(name = "name")
    private String name = null;

    @XmlElement(name = "lastName")
    private String lastName = null;

    @XmlElement(name = "email")
    private String email = null;

    @XmlElement(name = "address")
    private String address = null;

    @XmlElement(name = "phone")
    private String phone = null;

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        if (ContactValidator.validateEmail(email)) this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        if (ContactValidator.validatePhone(phone)) this.phone = phone;
    }

    public Contact() {
    }

    /**
     * Copies the contact data.
     *
     * @param contact Contact data to copy.
     */
    public void copy(Contact contact) {
        if (contact.getName() != null) setName(contact.getName());
        if (contact.getLastName() != null) setLastName(contact.getLastName());
        if (contact.getEmail() != null) setEmail(contact.getEmail());
        if (contact.getAddress() != null) setAddress(contact.getAddress());
        if (contact.getPhone() != null) setPhone(contact.getPhone());
    }

    /**
     * Checks if contacts have same data.
     *
     * @param contact Contact data.
     * @return True if similar, false otherwise.
     */
    public boolean similar(Contact contact) {
        return StringUtils.equals(contact.getName(), this.name) &&
                StringUtils.equals(contact.getLastName(), this.lastName) &&
                StringUtils.equals(contact.getEmail(), this.email) &&
                StringUtils.equals(contact.getAddress(), this.address) &&
                StringUtils.equals(contact.getPhone(), this.phone);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Contact) {
            Contact contact = (Contact) obj;
            return StringUtils.equals(contact.getUUID(), this.uuid);
        }
        return super.equals(obj);
    }
}