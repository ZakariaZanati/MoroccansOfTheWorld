package com.social.service;

import com.social.dto.JobOfferDto;
import com.social.model.JobOffer;
import com.social.model.Provider;
import com.social.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Autowired
    private AuthService authService;

    public void create(JobOfferDto jobOffer){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime creationDate = LocalDateTime.parse(jobOffer.getCreationDate(),formatter);
        LocalDateTime expirationDate = LocalDateTime.parse(jobOffer.getExpirationDate(),formatter);

        JobOffer job = JobOffer.builder()
                .post(jobOffer.getPost())
                .location(jobOffer.getLocation())
                .enterprise(jobOffer.getEnterprise())
                .contractType(jobOffer.getContractType())
                .creationDate(creationDate)
                .expirationDate(expirationDate)
                .jobLink(jobOffer.getLink())
                .provider((Provider) authService.getCurrentUser())
                .description(jobOffer.getDescription())
                .qualifications(jobOffer.getQualifications())
                .salary(jobOffer.getSalary())
                .build();

        jobOfferRepository.save(job);
    }
}
