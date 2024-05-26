package org.scimat_plus.bibliometricwe.customermanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.antlr.v4.runtime.misc.Pair;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
public class ProjectDto implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="customerId")
    private Integer customerId;

    @Column(name = "projectName")
    private String projectName;

    @Column(name="dProjeatabaseUrl")
    private String databaseUrl;

    @Column(name="searchPeriods")
    private String searchPeriods;
}
