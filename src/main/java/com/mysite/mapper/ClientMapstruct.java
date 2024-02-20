package com.mysite.mapper;

import com.mysite.Model.LegalClient;
import com.mysite.Model.RealClient;
import com.mysite.dto.LegalClientDto;
import com.mysite.dto.RealClientDto;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapstruct {
    RealClient mapToRealClient(RealClientDto realClientDto);

    LegalClient mapToLegalClient(LegalClientDto legalClient);
}
