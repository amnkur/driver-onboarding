package com.uber.driver.driver.onboarding.util;

import com.uber.driver.driver.onboarding.exception.InvalidParamException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ValidationUtil {

    public static void validateData(Pair<String, String> ... args) {
        String invalidArgs = Arrays.stream(args)
                                    .filter(arg -> StringUtils.isEmpty(arg.getRight()))
                                    .map(arg -> arg.getLeft())
                                    .collect(Collectors.joining(", "));

        if(StringUtils.isNotEmpty(invalidArgs)) {
            throw new InvalidParamException(String.format("Parameter(s) [%s] are invalid", invalidArgs));
        }

    }
}
