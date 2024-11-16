package com.intellect.serverstatuschecker.service.mapper;

import org.mapstruct.Mapper;

import com.intellect.serverstatuschecker.domain.Users;
import com.intellect.serverstatuschecker.service.dto.UsersDTO;

@Mapper(componentModel = "spring")
public interface UsersMapper extends EntityMapper<UsersDTO, Users> {

}
