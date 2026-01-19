package com.electroshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElectroShopController {
	
	@GetMapping("/bienvenida")

	public String Bienvenida() {
		return "Bienvenidos a ElectroShop";
	}
}
