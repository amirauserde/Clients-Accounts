package com.mysite.mapper;

import com.mysite.Model.Client;
import com.mysite.dto.ClientDto;

import java.lang.reflect.Field;

public interface ClientMapper {
    Field findMatchingField(Field sourceField, Field[] destinationFields);

    Field[] getAllFields(Class<?> clazz);

    <T, U> U map(T source, Class<U> destinationClass);

    Client mapToClient(ClientDto clientDto);
}
