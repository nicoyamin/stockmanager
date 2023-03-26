package com.example.stockmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "size")
public class Size {

    @Id
    @Column(name = "id")
    private int id;

    @JsonIgnoreProperties("sizes")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "backsoon")
    private boolean backsoon;

    @Column(name = "special")
    private boolean special;

    @JsonIgnoreProperties("stock")
    @OneToOne(mappedBy = "size")
    private Stock stock;
}
