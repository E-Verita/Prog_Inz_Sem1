package lv.venta.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.venta.models.Product;
import lv.venta.services.ICRUDProductService;
import lv.venta.services.IFilteringProductService;

@Controller
public class FirstController {

	@Autowired
	private IFilteringProductService filterService;

	@Autowired
	private ICRUDProductService crudService;

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
			try {
				Product temp = crudService.retrieveOneProductByTitle(title);
				model.addAttribute("myProduct", temp);
				return "product";
			} catch (Exception e) {
				e.printStackTrace();
				return "error-page";
			}
		}
		return "error-page";

	}

	// http://localhost:8686/product/Avokado
	@GetMapping("/product/{title}")
	public String productByParamFunction2(@PathVariable("title") String title, Model model) throws Exception {
		if (title != null) {
			try {
				Product temp = crudService.retrieveOneProductByTitle(title);
				model.addAttribute("myProduct", temp);
				return "product";
			} catch (Exception e) {
				e.printStackTrace();
				return "error-page";
			}
		}
		return "error-page";
	}

	@GetMapping("/allproducts")
	public String allProductsFunc(Model model) {
		model.addAttribute("myAllProducts", crudService.retrieveAllProducts());
		return "all-products-page";
	}

	// TODO kontrolieri, kas izfiltrē visus produktus, kuru cena ir mazaka par
	// padoto vērtību
	@GetMapping("/allproducts/{price}")
	public String allProductsByPrice(@PathVariable("price") float price, Model model) {

		if (price > 0) {
			ArrayList<Product> allProductsBellowPrice = filterService.filterByPriceLess(price);
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
		crudService.insertProductByParams(product.getTitle(), product.getPrice(), product.getDescription(),
				product.getQuantity());
		return "redirect:/allproducts"; // aiziet uz get mapping /allproducts
	}

	@GetMapping("/update/{id}")
	public String updateProductByIdGetFunc(@PathVariable("id") int id, Model model) {
		try {
			model.addAttribute("product", crudService.retrieveOneProductById(id));
			return "update-page";
		} catch (Exception e) {
			return "error-page";
		}
	}

	@PostMapping("/update/{id}")
	public String updateProductByIdPostFunc(@PathVariable("id") int id, Product product) { // ienāķ redigētais produkts
		try {
			Product temp = crudService.updateProductByParams(id, product.getTitle(), product.getPrice(),
					product.getDescription(), product.getQuantity());
			return "redirect:/product/" + temp.getTitle();
		} catch (Exception e) {
			return "redirect:/error";
		}
	}

	@GetMapping("/error")
	public String errorFunc() { // padots tukšs produkts
		return "error-page";
	}

	@GetMapping("/delete/{id}")
	public String deleteProductById(@PathVariable("id") int id, Model model) { // padots tukšs produkts
		try {
			crudService.deleteProductById(id);
			model.addAttribute("myAllProducts", crudService.retrieveAllProducts());
			return "all-products-page";
		} catch (Exception e) {
			return "error-page";
		}
	}

}
