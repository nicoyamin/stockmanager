package com.example.stockmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "sequence")
    private int sequence;

    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product")
    private List<Size> sizes;

}