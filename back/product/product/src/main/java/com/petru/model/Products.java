package com.petru.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Products {
    @JsonProperty("data")
    private List<Product> products;
}

