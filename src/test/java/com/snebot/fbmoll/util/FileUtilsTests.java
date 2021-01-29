package com.snebot.fbmoll.util;

import com.snebot.fbmoll.data.Contact;
import com.snebot.fbmoll.data.ContactBook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;

@SpringBootTest
public class FileUtilsTests {
    private static final FileUtils fileUtils = FileUtils.getInstance();
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String TEST_NAME = "test";
    private static final String TEST_FILE_NAME = TEST_NAME + ".txt";
    private static final String TEST_FILE_PATH = USER_HOME + FILE_SEPARATOR + TEST_FILE_NAME;

    @Test
    void tryUserHome() {
        String userHome = fileUtils.getUserHome();
        Assert.isTrue(USER_HOME.equals(userHome), "user home path is invalid");
    }

    @Test
    void tryAddPathComponent() {
        String path = fileUtils.addPathComponent(USER_HOME, TEST_FILE_PATH);
        Assert.isTrue(path.equals(TEST_FILE_PATH), "failed to add path component");
    }

    @Test
    void tryLastPathComponent() {
        String fileName = fileUtils.getLastPathComponent(TEST_FILE_PATH);
        Assert.isTrue(fileName.equals(TEST_FILE_NAME), "failed to get file name");
    }

    @Test
    void tryRemoveFileExtension() {
        String name = fileUtils.removeFileExtension(TEST_FILE_PATH);
        String expected = fileUtils.addPathComponent(USER_HOME, TEST_NAME);
        Assert.isTrue(name.equals(expected), "failed to remove file extension");
    }

    @Test
    void tryGetUserFile() {
        String userFile = fileUtils.getUserFile(TEST_FILE_NAME);
        Assert.isTrue(userFile.equals(TEST_FILE_PATH), "failed to get user file");
    }

    @Test
    void tryOpenFile() {
        File file = fileUtils.openFile(TEST_FILE_NAME);
        Assert.notNull(file, String.format("failed to open file: %s", TEST_FILE_NAME));
    }

    @Test
    void tryMarshalContent() {
        DummyUtils dummyUtils = new DummyUtils();
        List<Contact> contactList = dummyUtils.generateObjects(Contact.class, 10);
        ContactBook contactBook = new ContactBook("Contact Book", contactList);

        String path = fileUtils.getUserFile("contacts.xml");
        File file = fileUtils.marshalContent(contactBook, path);
        Assert.notNull(file, "failed to marshal content");

        ContactBook loadedContactBook = fileUtils.unmarshalContent(path, ContactBook.class);
        Assert.isTrue(contactBook.equals(loadedContactBook), "failed to load saved content");
    }
}
