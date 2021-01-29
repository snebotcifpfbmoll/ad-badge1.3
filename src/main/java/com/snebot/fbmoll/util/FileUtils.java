package com.snebot.fbmoll.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.Serializable;

/**
 * Utility class for file interaction.
 * This class is a singleton.
 *
 * @author Serafi Nebot Ginard
 */
public class FileUtils {
    private static FileUtils instance = null;
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * Get FileUtils instance.
     *
     * @return FileUtils instance.
     */
    public static FileUtils getInstance() {
        if (instance == null) instance = new FileUtils();
        return instance;
    }

    private FileUtils() {
    }

    /**
     * Returns the user's home directory path.
     *
     * @return User directory path.
     */
    public String getUserHome() {
        return USER_HOME;
    }

    /**
     * Appends a path component.
     *
     * @param component New path component.
     * @return Full path.
     */
    public String addPathComponent(String path, String component) {
        String componentName = getLastPathComponent(component);
        StringBuilder builder = new StringBuilder();
        builder.append(path);
        char lastChar = path.charAt(path.length() - 1);
        if (!Character.toString(lastChar).equals(FILE_SEPARATOR)) builder.append(FILE_SEPARATOR);
        builder.append(componentName);
        return builder.toString();
    }

    /**
     * Returns the name of the file in the specified path.
     *
     * @param path File path.
     * @return Name of file.
     */
    public String getLastPathComponent(String path) {
        String ret = StringUtils.substringAfterLast(path, FILE_SEPARATOR);
        if (ret.equals(StringUtils.EMPTY)) ret = path;
        return ret;
    }

    /**
     * Removes the file extension.
     *
     * @param path File path.
     * @return File path without file extension.
     */
    public String removeFileExtension(String path) {
        String ret = StringUtils.substringBeforeLast(path, FILE_EXTENSION_SEPARATOR);
        if (ret.equals(StringUtils.EMPTY)) ret = path;
        return ret;
    }

    /**
     * Returns user file path.
     *
     * @param fileName File name.
     * @return Path to file in user home.
     */
    public String getUserFile(String fileName) {
        String name = getLastPathComponent(fileName);
        return addPathComponent(USER_HOME, name);
    }

    /**
     * Opens a file from the specified path and checks that it is not a directory.
     *
     * @param path File path.
     * @return Opened file.
     */
    public File openFile(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            log.error(String.format("%s: is a directory", path));
            file = null;
        }
        return file;
    }

    /**
     * Marshal Java object.
     *
     * @param content Object to marshal.
     * @param path    Destination file path.
     * @param <T>     Object to marshal.
     * @return File with marshal content.
     */
    public <T extends Serializable> File marshalContent(T content, String path) {
        File file = null;
        try {
            JAXBContext context = JAXBContext.newInstance(content.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            file = openFile(path);
            marshaller.marshal(content, file);
        } catch (Exception e) {
            log.error("failed to marshal content ", e);
        }
        return file;
    }

    /**
     * Unmarshal content from file.
     *
     * @param path      File path.
     * @param classType Class of object to unmarshal.
     * @param <T>       Content object.
     * @return Content object.
     */
    public <T extends Serializable> T unmarshalContent(String path, Class<T> classType) {
        T content = null;
        try {
            content = classType.getDeclaredConstructor().newInstance();
            JAXBContext context = JAXBContext.newInstance(content.getClass());
            Unmarshaller um = context.createUnmarshaller();
            File file = openFile(path);
            if (file.exists() && !file.isDirectory()) {
                Object obj = um.unmarshal(file);
                if (content.getClass().isAssignableFrom(obj.getClass())) content = classType.cast(obj);
            }
        } catch (Exception e) {
            log.error("failed to unmarshal file ", e);
        }
        return content;
    }
}
