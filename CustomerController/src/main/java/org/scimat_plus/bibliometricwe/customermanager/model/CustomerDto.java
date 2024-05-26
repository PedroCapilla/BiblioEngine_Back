package org.scimat_plus.bibliometricwe.customermanager.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "customers")
@Data
public class CustomerDto implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "pswd")
    private String pswd;

}
