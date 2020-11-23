package com.social.mapper;

import com.social.dto.JobOfferDto;
import com.social.model.JobOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobOfferMapper {

    @Mapping(target = "id",source = "id")
    @Mapping(target = "post",source = "post")
    @Mapping(target = "enterprise",source = "enterprise")
    @Mapping(target = "location",source = "location")
    @Mapping(target = "link",source = "link")
    @Mapping(target = "contractType",source = "contractType")
    @Mapping(target = "description",source = "description")
    @Mapping(target = "qualifications",source = "qualifications")
    @Mapping(target = "creationDate",expression = "java(jobOffer.getCreationDate().toString())")
    @Mapping(target = "expirationDate",expression = "java(jobOffer.getExpirationDate().toString())")
    @Mapping(target = "author",source = "author")
    @Mapping(target = "salary",source = "salary")
    public abstract JobOfferDto mapToDto(JobOffer jobOffer);
}
