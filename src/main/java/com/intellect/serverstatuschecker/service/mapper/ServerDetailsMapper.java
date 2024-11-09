package com.intellect.serverstatuschecker.service.mapper;

import org.mapstruct.Mapper;

import com.intellect.serverstatuschecker.domain.ServerDetails;
import com.intellect.serverstatuschecker.service.dto.ServerDetailsDTO;


/**
 * Mapper for the entity {@link CardRates} and its DTO {@link CardRatesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServerDetailsMapper extends EntityMapper<ServerDetailsDTO, ServerDetails> {

}
