package com.example.dao.customerShiro;

import org.apache.ibatis.annotations.Mapper;

import com.example.configuration.annotation.SystemLogModel;

@Mapper
public interface SystemLogDao {

	//保存日志
    public int saveOrUpdate(SystemLogModel systemLogModel);
}
