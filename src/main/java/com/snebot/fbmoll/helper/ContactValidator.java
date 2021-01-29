package com.snebot.fbmoll.helper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactValidator {
    private static final Logger log = LoggerFactory.getLogger(ContactValidator.class);
    private static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String PHONE_REGEX = "^\\d{9}$";

    public static boolean validateEmail(String email) {
        try {
            Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
            Matcher m = pattern.matcher(StringUtils.defaultIfEmpty(email, StringUtils.EMPTY));
            return m.matches();
        } catch (Exception e) {
            log.error("failed to evaluate email address ", e);
            return false;
        }
    }

    public static boolean validatePhone(String phone) {
        try {
            Pattern pattern = Pattern.compile(PHONE_REGEX, Pattern.CASE_INSENSITIVE);
            Matcher m = pattern.matcher(StringUtils.defaultIfEmpty(phone, StringUtils.EMPTY));
            return m.matches();
        } catch (Exception e) {
            log.error("failed to evaluate phone number ", e);
            return false;
        }
    }
}
