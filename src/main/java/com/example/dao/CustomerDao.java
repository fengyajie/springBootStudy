package com.example.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.domain.Customer;

@Repository
public interface CustomerDao {
     
	List<Customer> selectList();
}
