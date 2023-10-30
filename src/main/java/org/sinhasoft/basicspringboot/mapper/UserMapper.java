package org.sinhasoft.basicspringboot.mapper;

import org.sinhasoft.basicspringboot.dto.UserDTO;
import org.sinhasoft.basicspringboot.entity.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Named("toEntity")
    User toEntity(UserDTO dto);
    @Named("toDto")
    UserDTO toDto(User entity);
    @IterableMapping(qualifiedByName = "toDto")
    List<UserDTO> toDtoList(List<User> userList);
    @IterableMapping(qualifiedByName = "toEntity")
    List<User> toEntityList(List<UserDTO> userDTOList);
}
