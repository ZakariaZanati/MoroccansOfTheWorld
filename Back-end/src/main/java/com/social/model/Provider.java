package com.social.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;


@AllArgsConstructor
@Entity
public class Provider extends User {

    //@OneToMany(mappedBy = "provider")
    //private Set<Group> groups;
}
