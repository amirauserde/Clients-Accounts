package com.mysite.service.Validation;

import com.mysite.dto.ClientDto;
import com.mysite.dto.RealClientDto;
import com.mysite.facade.impl.ValidationContext;
import com.mysite.service.exception.ValidationException;

public class ClientValidationContext extends ValidationContext<ClientDto> {

    public ClientValidationContext() {

        addValidation(client -> {
            String fullName = client.getName();
            String name = fullName.substring(fullName.lastIndexOf(',') + 1);
            name = name.trim();
            if(name.isEmpty()) {
                throw new ValidationException("Name must not be empty or null");
            }
        });

        addValidation(client -> {
            if(client instanceof RealClientDto) {
                String family = client.getName().split(",")[0];
                if(family == null ||
                        family.trim().isEmpty()) {
                    throw new ValidationException("Family must not be empty or null");
                }
            }
        });
    }
}