package org.scimat_plus.bibliometricwe.customermanager.controller;


import org.scimat_plus.bibliometricwe.customermanager.model.CustomerDto;
import org.scimat_plus.bibliometricwe.customermanager.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})

public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> findAll(){
        List<CustomerDto> customerList = customerService.findAll();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<CustomerDto>> findById(@PathVariable Long id){
        Optional<CustomerDto> customerToReturn = customerService.findById(id);
        return new ResponseEntity<>(customerToReturn, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customer){
        CustomerDto customerCreated = customerService.save(customer);
        return new ResponseEntity<>(customerCreated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCustomer(@PathVariable Long id){
        Optional<CustomerDto> customerToDelete = customerService.findById(id);
        customerToDelete.ifPresent(customerDto -> customerService.delete(customerDto));

    }

    @GetMapping(value="/{username}/{pswd}")
    public CustomerDto validateLogin(@PathVariable String username, @PathVariable String pswd) {
        return customerService.validateLogin(username,pswd);
    }
}
