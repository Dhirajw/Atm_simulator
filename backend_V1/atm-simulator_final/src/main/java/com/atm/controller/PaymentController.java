package com.atm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atm.entities.Card;
import com.atm.entities.Customer;
import com.atm.entities.Transfer;
import com.atm.serviceinterface.CustomerServiceInterface;

@RestController
@CrossOrigin(origins={"*","https://localhost:3001"})
public class PaymentController {
	@Autowired
	private CustomerServiceInterface CustomerServiceRef;
	
	@PostMapping("/Login1")
	public Customer checkAuthorization(@RequestBody Card details) {
		System.out.println(details);
		return CustomerServiceRef.checkCardNoAndPin(details);
		
		
	}
	
}
