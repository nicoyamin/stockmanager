package com.example.stockmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @JsonIgnore
    private Integer sizeId;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "size_id")
    private Size size;

    private Integer quantity;

}
