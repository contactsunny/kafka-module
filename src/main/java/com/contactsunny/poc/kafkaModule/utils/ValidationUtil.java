package com.contactsunny.poc.kafkaModule.utils;

import com.contactsunny.poc.kafkaModule.exceptions.ValidationException;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;

public class ValidationUtil {

    private static final Logger logger = Logger.getLogger(ValidationUtil.class);

    public static void runValidation(List<String> properties, List<String> requiredFields) throws ValidationException {

        for (String requiredField : requiredFields) {

            if (!properties.contains(requiredField)) {
                String errorMessage = requiredField + " not found in message, skipping!";
                logger.error(errorMessage);

                throw new ValidationException(errorMessage);
            }
        }

        for (String property : properties) {
            if (property == null) {
                String errorMessage = property + " is null, skipping!";
                logger.error(errorMessage);

                throw new ValidationException(errorMessage);
            }
        }
    }
}
