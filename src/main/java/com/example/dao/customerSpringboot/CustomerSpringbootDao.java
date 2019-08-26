package com.example.dao.customerSpringboot;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.Customer;


//@Mapper 这个注解springboot启动类扫描dao，其他@Component,@Repository都不行，或者在启动类加@MapperScan扫描dao包
public interface CustomerSpringbootDao {
     
	List<Customer> selectList();
	
	void deleteById(Long id);
	
	Customer findUserByAccountAndPassword(Customer customer);
}
