package com.example.demo.models;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idService;

    @NotBlank
    private String typeService;

    private double tarifService;

    @OneToMany(mappedBy = "service")
    private List<CommandeService> commandeServices;
}
