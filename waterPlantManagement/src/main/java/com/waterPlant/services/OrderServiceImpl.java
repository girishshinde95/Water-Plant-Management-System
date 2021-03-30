package com.waterPlant.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterPlant.dao.OrderRepository;
import com.waterPlant.pojo.Orders;



@Service
public class OrderServiceImpl {

	@Autowired
	OrderRepository orderRepository;
	
    public List<Orders> getAllOrders() {
		
		return orderRepository.findAll();
	}
    
    public void addOrder(Orders orders) {
    	orderRepository.save(orders);
	}
    
    public void deleteOrderById(int id) {
		orderRepository.deleteById(id);
	}

	public Optional<Orders> getOrdersByUserId(int id)
	{
		return orderRepository.findById(id);
	}

}
