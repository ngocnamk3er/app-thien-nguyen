package danentang.app_thien_nguyen.mappers;

import danentang.app_thien_nguyen.models.DataModels.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import danentang.app_thien_nguyen.models.DTOs.UserDTO;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "role.name()", target = "role")
    })
    UserDTO userToUserDTO(User user);
}
