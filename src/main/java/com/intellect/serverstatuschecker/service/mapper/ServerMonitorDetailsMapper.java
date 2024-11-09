package com.intellect.serverstatuschecker.service.mapper;

import org.mapstruct.Mapper;

import com.intellect.serverstatuschecker.domain.ServerMonitorDetails;
import com.intellect.serverstatuschecker.service.dto.ServerMonitorDetailsDTO;

/**
 * Mapper for the entity {@link CardRates} and its DTO {@link CardRatesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServerMonitorDetailsMapper extends EntityMapper<ServerMonitorDetailsDTO, ServerMonitorDetails> {

}
