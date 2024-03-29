package com.mysite.util;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Converter
public class IntArrayToStringConverter implements AttributeConverter<List<Integer>,String> {
    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        return attribute == null ? null : StringUtils.join(attribute,",");
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData))
            return Collections.emptyList();

        try (Stream<String> stream = Arrays.stream(dbData.split(","))) {
            return stream.map(Integer::parseInt).collect(Collectors.toList());
        }
    }

}
