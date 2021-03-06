package vn.com.truyen.usermanagent.service.mapper;


import vn.com.truyen.usermanagent.domain.*;
import vn.com.truyen.usermanagent.service.dto.UserInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserInfo} and its DTO {@link UserInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UsersMapper.class})
public interface UserInfoMapper extends EntityMapper<UserInfoDTO, UserInfo> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    UserInfoDTO toDto(UserInfo userInfo);

    @Mapping(source = "userId", target = "user")
    UserInfo toEntity(UserInfoDTO userInfoDTO);

    default UserInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        return userInfo;
    }
}
