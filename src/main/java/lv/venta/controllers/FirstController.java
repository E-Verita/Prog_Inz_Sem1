package lv.venta.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import lv.venta.models.Product;

@Controller
public class FirstController {
	ArrayList<Product> allProducts = new ArrayList<>(Arrays.asList(new Product("Ābols", 3.99f, "Sarkans", 3),
			new Product("Tomāts", 1.99f, "Dzeltens", 12), new Product("Avokado", 0.99f, "Zaļš", 10)));

	@GetMapping("/hello")
	public String helloFuntion() {
		System.out.println("Mans pirmais kontrolieris STRĀDĀ!!!");
		return "hello-page";
	}

	@GetMapping("/msg")
	public String msgFuntion(Model model) {
		model.addAttribute("myMsg", "Te ziņa no Everitas");
		model.addAttribute("myDate", "26/05/2023");
		return "msg-page";
	}

	@GetMapping("/product")
	public String productFunction(Model model) {
		Product prod = new Product("Ābols", 3.99f, "Sarkans", 3);
		model.addAttribute("myProduct", prod);
		return "product";
	}

	//http://localhost:8686/productOne?title=Avokado
	@GetMapping("/productOne")
	public String productByParamFunction(@RequestParam("title") String title, Model model) throws Exception {
		if (title != null) {
			for (Product temp : allProducts) {
				if (temp.getTitle().equals(title)) {
					model.addAttribute("myProduct", temp);
					return "product";
				}
			}
		}
		return "error-page";
	}
	
	@GetMapping("/product/{title}")
	public String productByParamFunction2(@PathVariable("title") String title, Model model) throws Exception {
		if (title != null) {
			for (Product temp : allProducts) {
				if (temp.getTitle().equals(title)) {
					model.addAttribute("myProduct", temp);
					return "product";
				}
			}
		}
		return "error-page";
	}
}
