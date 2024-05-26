package org.scimat_plus.bibliometricwe.customermanager.service;

import jakarta.transaction.Transactional;
import org.scimat_plus.bibliometricwe.customermanager.exception.CustomerErrorException;
import org.scimat_plus.bibliometricwe.customermanager.model.CustomerDto;
import org.scimat_plus.bibliometricwe.customermanager.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Transactional
    public List<CustomerDto> findAll(){
        return customerRepository.findAll();
    }

    @Transactional
    public CustomerDto save(CustomerDto customer){
        return customerRepository.save(customer);
    }

    public Optional<CustomerDto> findById(Long id){
        return customerRepository.findById(id);
    }

    @Transactional
    public void delete(CustomerDto customer){
        customerRepository.delete(customer);
    }

    @Transactional
    public CustomerDto validateLogin(String username, String pswd){
        if(customerRepository.existsByUsername(username) && customerRepository.existsUserByUsernameAndPassword(username,pswd)){
            return customerRepository.findUserByUsernameAndPassword(username, pswd);
        }else{
            throw new CustomerErrorException("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
