package com.social.controller;

import com.social.dto.JobOfferDto;
import com.social.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    @PostMapping
    public ResponseEntity<String> createJobOffer(@RequestBody JobOfferDto jobOffer){
        jobOfferService.create(jobOffer);
        return new ResponseEntity<>("Job offer created Successfully", HttpStatus.OK);
    }
}
