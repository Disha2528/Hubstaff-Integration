package com.Integration.hubstaff.Util;

import com.Integration.hubstaff.Exception.BadRequestException;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class ValidationUtil {

    public static void validateDto(Object dto) {
        if (Objects.isNull(dto)) {
            throw new BadRequestException("Request DTO cannot be null");
        }
    }

    public static boolean isNullOrEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            return ((String) object).trim().isEmpty();
        }
        if (object instanceof Collection<?>) {
            return ((Collection<?>) object).isEmpty();
        }
        if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).isEmpty();
        }
        return false;
    }

    public static void checkNotNull(Object obj, String fieldName) {
        if (Objects.isNull(obj)) {
            throw new BadRequestException(fieldName + " cannot be null");
        }

    }
    public static void checkNotNullOrBlank(String value, String fieldName) {
        if (!StringUtils.hasText(value)) {
            throw new BadRequestException(fieldName + " cannot be null, empty, or blank");
        }
    }
}