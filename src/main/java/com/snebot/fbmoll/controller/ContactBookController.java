package com.snebot.fbmoll.controller;

import com.snebot.fbmoll.data.Contact;
import com.snebot.fbmoll.data.ContactBook;
import com.snebot.fbmoll.util.DummyUtils;
import com.snebot.fbmoll.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ContactBookController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String FILE_NAME = "contacts.xml";
    private static final FileUtils fileUtils = FileUtils.getInstance();
    private static final String FILE_PATH = fileUtils.getUserFile(FILE_NAME);
    private final ContactBook book = fileUtils.unmarshalContent(FILE_PATH, ContactBook.class);

    /**
     * Save contact book data to XML file.
     */
    private void saveContactBook() {
        fileUtils.marshalContent(this.book, FILE_PATH);
    }

    /**
     * Generate contact book with a name and n amount of contacts.
     * For testing purposes only.
     *
     * @param name  Contact book name.
     * @param count Amount of contacts.
     * @return Generated contact book.
     */
    @RequestMapping(value = "/generate/{name}/{q}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> test(@PathVariable(name = "name") String name,
                                       @PathVariable(name = "q") Integer count) {
        try {
            DummyUtils dummyUtils = new DummyUtils();
            List<Contact> contactList = dummyUtils.generateObjects(Contact.class, count);
            return new ResponseEntity<>(new ContactBook(name, contactList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Request mapping for "/contacts" with multiple request methods:
     * - GET: Find contacts that match with specified data.
     * - POST: Create a new contact.
     * - PUT: Update contact data.
     * - DELETE: Remove contacts matching parameters.
     *
     * @param uuid      Unique identifier.
     * @param name     Contact name.
     * @param lastName Contact last name.
     * @param email    Contact email.
     * @param address  Contact address.
     * @param phone    Contact phone.
     * @param contact  Contact data.
     * @param request  Request method.
     * @return ResponseEntity
     */
    @RequestMapping(value = "/contacts",
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> contacts(@RequestParam(value = "uuid", required = false) String uuid,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "lastName", required = false) String lastName,
                                           @RequestParam(value = "email", required = false) String email,
                                           @RequestParam(value = "address", required = false) String address,
                                           @RequestParam(value = "phone", required = false) String phone,
                                           @RequestBody(required = false) Contact contact,
                                           HttpServletRequest request) {
        try {
            Object result = null;
            HttpStatus status = HttpStatus.OK;

            switch (RequestMethod.valueOf(request.getMethod())) {
                case GET:
                    result = this.book.getContacts(uuid, name, lastName, email, address, phone);
                    break;
                case POST:
                    if (contact != null) {
                        this.book.addContact(contact);
                        saveContactBook();
                        result = contact;
                    } else {
                        log.warn("missing request body");
                        status = HttpStatus.BAD_REQUEST;
                    }
                    break;
                case PUT:
                    if (contact != null && uuid != null) {
                        Contact updatedContact = this.book.updateContact(uuid, contact);
                        saveContactBook();
                        result = updatedContact;
                    } else {
                        log.warn("missing request body");
                        status = HttpStatus.BAD_REQUEST;
                    }
                    break;
                case DELETE:
                    List<Contact> removed = this.book.removeContact(uuid, name, lastName, email, address, phone);
                    saveContactBook();
                    result = removed;
                    break;
                default:
                    log.warn("received unexpected request method");
                    break;
            }

            return new ResponseEntity<>(result, status);
        } catch (Exception e) {
            log.error("failed to get contact by name ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
