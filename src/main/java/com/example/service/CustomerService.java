package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.domain.Customer;

@Service
public interface CustomerService {
	List<Customer> selectList();
	
	void deleteById (Long id) throws Exception;
	
	List<Customer> selectSpringBoot();
}
