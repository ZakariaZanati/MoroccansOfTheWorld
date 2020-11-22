package com.social.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Provider extends User {

    @JsonIgnore
    @OneToMany(mappedBy = "provider")
    private Set<JobOffer> jobOffers = new HashSet<>();
}
