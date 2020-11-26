package com.social.controller;

import com.social.dto.JobOfferDto;
import com.social.repository.JobOfferRepository;
import com.social.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @PostMapping
    public ResponseEntity<String> createJobOffer(@RequestBody JobOfferDto jobOffer){
        jobOfferService.create(jobOffer);
        return new ResponseEntity<>("Job offer created Successfully", HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<JobOfferDto>> getJobsList(){

        List<JobOfferDto> jobOfferDtos = jobOfferService.getJobs();
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferDtos);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<JobOfferDto>> getUserJobOffers(@PathVariable String username){
        List<JobOfferDto> jobOfferDtos = jobOfferService.getUserJobOffers(username);
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOfferDto> getJobOffer(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.getJobOffer(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobOffer(@PathVariable Long id){
        jobOfferRepository.delete(jobOfferRepository.findById(id).get());
        return new ResponseEntity<>("Job deleted Successfully",HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobOfferDto>> findByNameOrLocation(@Param("name") String name,
                                                                  @Param("location") String location){
        return ResponseEntity.status(HttpStatus.OK).body(jobOfferService.find(name, location));
    }


}
