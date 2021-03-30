package com.waterPlant.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waterPlant.dao.UserRepository;
import com.waterPlant.exceptions.UserException;
import com.waterPlant.pojo.CartData;

import com.waterPlant.pojo.User;
import com.waterPlant.services.OrderServiceImpl;
import com.waterPlant.services.ProductServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@SuppressWarnings("unused")
	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private OrderServiceImpl orderService;
	
	
	//@Autowired
	//private OrderServiceImpl orderService;
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("username:" + userName);
		User user = userRepository.getUserByUserName(userName);
		System.out.println("user:" + user);
		model.addAttribute("user", user);
		model.addAttribute("cartCount", CartData.cart.size());
	}

	@RequestMapping("/dashboard")
	public String dashboard(Model m, Principal principal) {
		
		System.out.println("In user Dashboard");
		return "user/user_dashboard";
	}
	
	//your Profile
	// your profile handler
	     @RequestMapping("/profile")
		public String yourProfile(Model model) {
			model.addAttribute("title", "Profile Page");
			return "user/profile";
		}
	     
	 // change password
	     @RequestMapping("/password")
			public String passwordChange(Model model) {
				model.addAttribute("title", "password Page");
				return "user/password";
			}
	     
	     @RequestMapping(value="/change_password", method=RequestMethod.POST)
	 	public String changePassword(@RequestParam("oldPassword") String oldPassword,
	 			@RequestParam("newPassword") String newPassword, Principal principal, HttpSession session) {
	 		System.out.println("OLD PASSWORD " + oldPassword);
	 		System.out.println("NEW PASSWORD " + newPassword);

	 		String userName = principal.getName();
	 		User currentUser = this.userRepository.getUserByUserName(userName);
	 		System.out.println(currentUser.getPassword());
	 		try {
	 		if (this.passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
	 			currentUser.setPassword(this.passwordEncoder.encode(newPassword));
	 			this.userRepository.save(currentUser);
	 			

	 		} session.setAttribute("message", new UserException("Password Change Successfully", "alert-success"));
	 		}catch (Exception e) {
	 			e.printStackTrace();
				
				session.setAttribute("message", new UserException("Something Went Wrong.."+e.getMessage(), "alert-error"));
	 			return "redirect:/user/profile";
	 		}
	 		return "redirect:/user/password";
	 	}
	     
	     
//Order Manage-------------------------------------------------------------------------------------------	     
	     
	  
			//Order Delete
			@RequestMapping(value="/deleteOrder/{id}", method=RequestMethod.GET)
			public String deleteOrder(@PathVariable int id) 
			{
				System.out.println("User Deleted Order");
				
				orderService.deleteOrderById(id);
				return "user/user_dashboard";
			}
			

	     
	     
	     
	     
	     
	     
	     
	     
	     
// cart Mapping-------------------------------------------------------------------------
/*
	@RequestMapping(value="/addToCart/{id}", method=RequestMethod.GET)
	public String addToCart(@PathVariable long id) 
	{
		
		CartData.cart.add(productService.getProductById(id).get());
		System.out.println("Adding To cart product");
		
		return "/products";
	}
	
	@RequestMapping(value="/cart", method=RequestMethod.GET)
	public String cartGet(Model m) 
	{
		m.addAttribute("cartCount", CartData.cart.size());
		m.addAttribute("amount", CartData.cart.stream().mapToDouble(Product::getPrice).sum());
		m.addAttribute("cart", CartData.cart);
		return "user/cart";
	}
*/

/*
	//handler for Order product
	@RequestMapping(value="/orderPage", method=RequestMethod.GET)
	public String addProductGet(Model m, @PathVariable int id) 
	{
		m.addAttribute("title", "Order-WaterPlantManagment");
		System.out.println("in add Order page");
		m.addAttribute("products", productService.getAllProduct());
		//m.addAttribute("orders", orderService.getOrderById(id));
		//m.addAttribute("product", new Product());
		return "user/orderPage";
	}	
	
	
			//product adding process post
			@RequestMapping(value="/adding_order", method=RequestMethod.POST)
			public String productAddPost(@ModelAttribute("Order") Order order,
					HttpSession session) {
		
				Order o=new Order();
					
					o.setOrderId(order.getOrderId());
					o.setpId(order.getpId());
					o.setuId(order.getuId());
					o.setQuantity(order.getQuantity());
					o.setAmount(order.getAmount());
					o.setShippingAddress(order.getShippingAddress());
					o.setShippingDate(order.getShippingDate());
					
					orderService.addOrder(order);
		         	
		
					System.out.println("DATA:" + order);
					System.out.println("Added to database");
					
		
				return "admin/admin_dashboard";
			}
		*/	
}
