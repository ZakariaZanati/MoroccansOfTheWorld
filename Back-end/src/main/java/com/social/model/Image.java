package com.social.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "picByte", length = 1048570)
    private byte[] picByte;

    @OneToOne(mappedBy = "image")
    private User user;

    public Image(String originalFilename, String contentType, byte[] compressBytes) {
        this.name = originalFilename;
        this.type = contentType;
        this.picByte = compressBytes;
    }
}
