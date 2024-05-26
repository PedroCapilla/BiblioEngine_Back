package org.scimat_plus.bibliometricwe.customermanager.repository;

import org.scimat_plus.bibliometricwe.customermanager.model.CustomerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<CustomerDto, Long > {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM CustomerDto u WHERE u.name = :name")
    boolean existsByUsername(@Param("name") String name);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM CustomerDto u WHERE u.name = :name AND u.pswd = :pswd")
    boolean existsUserByUsernameAndPassword(@Param("name") String name, @Param("pswd") String pswd);

    @Query("SELECT u FROM CustomerDto u WHERE u.name = :name AND u.pswd = :pswd")
    CustomerDto findUserByUsernameAndPassword(@Param("name") String username, @Param("pswd") String pswd);
}
