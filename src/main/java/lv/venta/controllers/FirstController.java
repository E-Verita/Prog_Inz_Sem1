package lv.venta.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.venta.models.Product;

@Controller
public class FirstController {
	ArrayList<Product> allProducts = new ArrayList<>(Arrays.asList(new Product("Abols", 3.99f, "Sarkans", 3),
			new Product("Tomats", 1.99f, "Dzeltens", 12), new Product("Avokado", 0.99f, "Zals", 10)));

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

	// http://localhost:8686/productOne?title=Avokado
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

	// http://localhost:8686/product/Avokado
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

	@GetMapping("/allproducts")
	public String allProductsFunc(Model model) {
		model.addAttribute("myAllProducts", allProducts);
		return "all-products-page";
	}

	// TODO kontrolieri, kas izfiltrē visus produktus, kuru cena ir mazaka par
	// padoto vērtību
	@GetMapping("/allproducts/{price}")
	public String allProductsByPrice(@PathVariable("price") float price, Model model) {

		if (price > 0) {
			ArrayList<Product> allProductsBellowPrice = new ArrayList<>();
			for (Product temp : allProducts) {
				if (temp.getPrice() <= price) {
					allProductsBellowPrice.add(temp);
				}
			}
			model.addAttribute("myAllProducts", allProductsBellowPrice);
			return "all-products-page";
		}
		return "error-page";
	}

	@GetMapping("/insert")
	public String insertProductFunc(Product product) { // padots tukšs produkts
		return "insert-page";
	}

	@PostMapping("/insert")
	public String insertProductPostFunc(Product product) { // saņemts aizpildīts (no form) produkts
		Product prod = new Product(product.getTitle(), product.getPrice(), product.getDescription(),
				product.getQuantity());
		allProducts.add(prod);
		return "redirect:/allproducts"; // aiziet uz get mapping /allproducts
	}

	@GetMapping("/update/{id}")
	public String updateProductByIdGetFunc(@PathVariable("id") int id, Model model) {
		for (Product temp : allProducts) {
			if (temp.getId() == id) {
				model.addAttribute("product", temp);
				return "update-page";
			}
		}
		return "error-page";
	}

	@PostMapping("/update/{id}")
	public String updateProductByIdPostFunc(@PathVariable("id") int id, Product product) { // ienāķ redigētais produkts
		for (Product temp : allProducts) {
			if (temp.getId() == id) {
				temp.setTitle(product.getTitle());
				temp.setPrice(product.getPrice());
				temp.setDescription(product.getDescription());
				temp.setQuantity(product.getQuantity());
				return "redirect:/product/" + product.getTitle();
			}
		}
		return "redirect:/error";
	}
	
	@GetMapping("/error")
	public String errorFunc() { // padots tukšs produkts
		return "error-page";
	}

	@GetMapping("/delete/{id}")
	public String deleteProductById(@PathVariable("id") int id, Model model) { // padots tukšs produkts
		for (Product temp : allProducts) {
			if (temp.getId() == id) {
				allProducts.remove(temp);
				model.addAttribute("myAllProducts", allProducts);
				return "all-products-page";
			}
		}
		return "error-page";
		
	}

	
}
