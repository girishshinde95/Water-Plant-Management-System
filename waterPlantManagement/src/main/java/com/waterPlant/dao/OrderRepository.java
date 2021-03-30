package com.waterPlant.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waterPlant.pojo.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer>{

}
