package com.social.service;

import com.social.dto.JobOfferDto;
import com.social.mapper.JobOfferMapper;
import com.social.model.JobOffer;
import com.social.model.Provider;
import com.social.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Autowired
    private JobOfferMapper jobOfferMapper;

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
                .author(jobOffer.getAuthor())
                .build();

        jobOfferRepository.save(job);
    }


    public List<JobOfferDto> getJobs(){

        List<JobOffer> jobOffers = jobOfferRepository.findAll();
        return jobOffers.stream()
                .map(jobOfferMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<JobOfferDto> getUserJobOffers(String username){
        List<JobOffer> jobOffers = jobOfferRepository.findByAuthor(username);
        return jobOffers.stream()
                .map(jobOfferMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public JobOfferDto getJobOffer(Long id){
        JobOffer jobOffer = jobOfferRepository.findById(id).get();
        return jobOfferMapper.mapToDto(jobOffer);
    }

    public List<JobOfferDto> find(String name,String location){
        List<JobOffer> jobOffers = jobOfferRepository.findByPostContainsOrLocationContains(name,location);
        return jobOffers.stream()
                .map(jobOfferMapper::mapToDto)
                .collect(Collectors.toList());
    }


}
