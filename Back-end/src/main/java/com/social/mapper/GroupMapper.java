package com.social.mapper;

import com.social.dto.GroupResponse;
import com.social.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mapping(target = "id",source = "id")
    @Mapping(target = "name",source = "name")
    @Mapping(target = "description",source = "description")
    @Mapping(target = "adminUserName",source = "adminUserName")
    @Mapping(target = "imageBytes",source = "imageBytes")
    public abstract GroupResponse mapToDto(Group group);

}
