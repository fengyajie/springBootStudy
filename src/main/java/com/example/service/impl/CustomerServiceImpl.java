package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.CustomerDao;
import com.example.domain.Customer;
import com.example.service.CustomerService;;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public List<Customer> selectList() {
		List<Customer> customerList=null;
		try{
			customerList = customerDao.selectList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return customerList;
	}

}
