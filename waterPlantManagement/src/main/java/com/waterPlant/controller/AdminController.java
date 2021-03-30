package com.waterPlant.controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waterPlant.pojo.Orders;
import com.waterPlant.pojo.Product;
import com.waterPlant.pojo.User;
import com.waterPlant.services.OrderServiceImpl;
import com.waterPlant.services.ProductServiceImpl;
import com.waterPlant.services.UserServiceImpl;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private OrderServiceImpl orderService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

//Admin Dashboard -----------------------------------------------------------------------------------
	@RequestMapping("/dashboard")
	public String dashboard() {
		
		System.out.println("In user Dashboard");
		return "admin/admin_dashboard";
	}
	
	
//Manage Product Page	------------------------------------------------------------------------------
	@RequestMapping(value="/mproducts", method=RequestMethod.GET)
	public String viewProduct(Model m) 
	{
		m.addAttribute("title", "Product-WaterPlantManagment");
		System.out.println("in Product page");
		
		m.addAttribute("products", productService.getAllProduct());
		return "admin/mproducts";
	}
	
	
	
		//handler for add product
		@RequestMapping(value="/addProduct", method=RequestMethod.GET)
		public String addProductGet(Model m) 
		{
			m.addAttribute("title", "Product-WaterPlantManagment");
			System.out.println("in add Product page");
			
			m.addAttribute("product", new Product());
			return "admin/addProduct";
		}
	
		
		//product adding process post
		@RequestMapping(value="/adding_product", method=RequestMethod.POST)
		public String productAddPost(@ModelAttribute("product") Product product,
				HttpSession session) {
	
			Product p=new Product();
				p.setId(product.getId());
				p.setProductName(product.getProductName());
				p.setQtyInLtr(product.getQtyInLtr());
				p.setPrice(product.getPrice());
				p.setDescription(product.getDescription());
				
				productService.addProduct(product);
	         	
	
				System.out.println("DATA:" + product);
				System.out.println("Added to database");
				
	
			return "admin/admin_dashboard";
		}
		
		//Product Delete
		@RequestMapping(value="/deleteProduct/{id}", method=RequestMethod.GET)
		public String deleteProduct(@PathVariable long id) 
		{
			System.out.println("Admin Deleted product");
			
			productService.deleteProductById(id);
			return "admin/admin_dashboard";
		}
	
		
		//update product get
		@RequestMapping(value="/updateProduct/{id}", method=RequestMethod.GET)
		public String updateProduct(@PathVariable long id, Model m) 
		{
			System.out.println("Admin Updated product");
			Product product=productService.getProductById(id).get();
			
			Product p=new Product();
			
			p.setId(product.getId());
			p.setProductName(product.getProductName());
			p.setQtyInLtr(product.getQtyInLtr());
			p.setPrice(product.getPrice());
			p.setDescription(product.getDescription());
			
			m.addAttribute("product", p);
			
			return "admin/addProduct";
		}

//Order Management---------------------------------------------------------------------------
		@RequestMapping(value="/myorders", method=RequestMethod.GET)
		public String viewOrders(Model m) 
		{
			m.addAttribute("title", "Order-WaterPlantManagment");
			System.out.println("in Order page");
			
		  m.addAttribute("orders", orderService.getAllOrders());
			return "admin/myorders";
		}
		
		//handler for add Order
				@RequestMapping(value="/addOrders", method=RequestMethod.GET)
				public String addOrderGet(Model m) 
				{
					m.addAttribute("title", "Order-WaterPlantManagment");
					System.out.println("in add Order page");
					
					m.addAttribute("orders", new Orders());
					return "admin/addOrders";
				}
				
				//Order adding process post
				@RequestMapping(value="/adding_orders", method=RequestMethod.POST)
				public String productAddPost(@ModelAttribute("orders") Orders orders,
						HttpSession session) {
			
					Orders p=new Orders();
						p.setId(orders.getId());;
						p.setProduct_id(orders.getProduct_id());
						p.setUser_id(orders.getUser_id());
						p.setQuantity(orders.getQuantity());
						p.setShippingAddress(orders.getShippingAddress());
						p.setShippingDate(orders.getShippingDate());
						p.setTotal(orders.getTotal());
						orderService.addOrder(orders);
			         	
			
						System.out.println("DATA:" + orders);
						System.out.println("Added to database");
						
			
					return "admin/admin_dashboard";
				}	
				
				//Order Delete
				@RequestMapping(value="/deleteOrder/{id}", method=RequestMethod.GET)
				public String deleteOrder(@PathVariable int id) 
				{
					System.out.println("Admin Deleted Order");
					
					orderService.deleteOrderById(id);
					return "admin/admin_dashboard";
				}
				

//User Management---------------------------------------------------------------------------
	  //user Manage page
		@RequestMapping(value="/manageUser", method=RequestMethod.GET)
		public String viewUser(Model m) 
		{
			m.addAttribute("title", "adminUser-WaterPlantManagment");
			System.out.println("in User Management page");
			
			m.addAttribute("users", userService.getAllUser() );
			return "admin/manageUser";
		}
		
		//User Delete
				@RequestMapping(value="/deleteUser/{id}", method=RequestMethod.GET)
				public String deleteUser(@PathVariable int id) 
				{
					System.out.println("Admin Deleted User");
					
					userService.deleteUserById(id);
					return "admin/admin_dashboard";
				}
		/*
				//product adding process post
				@RequestMapping(value="/adding_user", method=RequestMethod.POST)
				public String userAddPost(@ModelAttribute("user") User user,
						HttpSession session) {
			
					User p=new User();
					p.setUserName(user.getUserName());
					p.setEmail(user.getEmail());
					p.setMobile(user.getMobile());
					p.setAddress(user.getAddress());
					p.setPassword(user.getPassword());
					p.setEnable(true);
					p.setRole("ROLE_USER");
						
						userService.addUser(user);
			         	
			
						System.out.println("DATA:" + user);
						System.out.println("Added to database");
						
			
					return "admin/admin_dashboard";
				}
			*/	
				//update User get
				@RequestMapping(value="/updateUser/{id}", method=RequestMethod.GET)
				public String updateUser(@PathVariable int id, Model m) 
				{
					System.out.println("Admin Updated User");
					User user=userService.getUserById(id).get();
					user.setPassword(passwordEncoder.encode(user.getPassword()));
					User p=new User();
				
					p.setUserName(user.getUserName());
					p.setEmail(user.getEmail());
					p.setMobile(user.getMobile());
					p.setAddress(user.getAddress());
					p.setPassword(user.getPassword());
					p.setEnable(true);
					p.setRole("ROLE_USER");
					
					m.addAttribute("user", p);
					
					return "/signup";
				}
		
}

