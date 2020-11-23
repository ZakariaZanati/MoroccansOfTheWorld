package com.social.repository;

import com.social.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobOfferRepository extends JpaRepository<JobOffer,Long> {

    List<JobOffer> findByAuthor(String author);
}
