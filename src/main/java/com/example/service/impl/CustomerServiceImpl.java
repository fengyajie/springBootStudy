package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.customerShiro.CustomerDao;
import com.example.dao.customerSpringboot.CustomerSpringbootDao;
import com.example.domain.Customer;
import com.example.service.CustomerService;;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	
	@Autowired
	private CustomerSpringbootDao customerSpringbootDao;
	
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

	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	//@Transaction一般注解到方法，因为查询并不需要事务,注解到类，所有方法都会被事务管理,propagation事务传播，isolation事务隔离,rollback定义事务回滚
	public void deleteById (Long id) throws Exception {
			customerDao.deleteById(id);
			throw new RuntimeException();
			/*
			 * 这里有个小细节,不要写在try里面，直接跑出去在controller统一处理，写在try里在catch就处理了，不抛出去spring不知道报错，无法回滚
			 * try{
				customerDao.deleteById(id);
				throw new RuntimeException();
			}catch(Exception e){
				e.printStackTrace();
			}*/
	}

	@Override
	public List<Customer> selectSpringBoot() {
		return customerSpringbootDao.selectList();
	}

}
