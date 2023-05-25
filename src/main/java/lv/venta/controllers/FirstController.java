package lv.venta.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lv.venta.models.Product;
import lv.venta.services.ICRUDProductService;
import lv.venta.services.IFilteringProductService;

@Controller
public class FirstController {

	@Autowired
	private IFilteringProductService filterService;

	@Autowired
	private ICRUDProductService crudService;

	@GetMapping("/hello") // tiks izsaukta, ja url būs localhost:8080/hello
	public String helloFunc() {
		System.out.println("Mans pirmais kontrolieris ir nostrādājis");
		return "hello-page"; // tiks parādīta hello-page.html lapa
	}

	@GetMapping("/msg") // tiks izsaukta, ja url būs localhost:8080/msg
	public String msgFunc(Model model) {
		model.addAttribute("myMsg", "Te ziņa no manis");
		model.addAttribute("myDate", "23.03.2023.");
		return "msg-page"; // tiks parādīta msg-pagr.html lapa un tajā parādīsies myMsga un myDate
	}

	@GetMapping("/product") // tiks izsaukta, ja url būs localhost:8080/product
	public String productFunc(Model model) {
		Product prod = new Product("Ābols", 3.99f, "Sarkans", 3);
		model.addAttribute("myProduct", prod);
		return "product-page"; // tiks parādīta product-page.html lapa un tajā parādīsies
	}

	// TODO /productOne?title=Ābols

	// localhost:8080/productOne?title=Ābols
	@GetMapping("/productOne")
	public String productByParamFunc(@RequestParam("title") String title, Model model) {
		if (title != null) {
			try {
				ArrayList<Product> temp = crudService.retrieveAllProductsByTitle(title);
				model.addAttribute("myAllProducts", temp);
				return "all-products-page";
			} catch (Exception e) {
				return "error-page";// parādīs error-page.html lapu
			}
		}

		return "error-page";// parādīs error-page.html lapu

	}

	// TODO /product/Ābols
	@GetMapping("/product/{title}")
	public String productByParamFunc2(@PathVariable("title") String title, Model model) {
		if (title != null) {
			try {
				ArrayList<Product> temp = crudService.retrieveAllProductsByTitle(title);
				model.addAttribute("myAllProducts", temp);
				return "all-products-page";
			} catch (Exception e) {
				return "error-page";// parādīs error-page.html lapu
			}
		}

		return "error-page";// parādīs error-page.html lapu
	}

	// TODO kontrolieri, kas atgriežis visus produktus
	@GetMapping("/allproducts") // localhost:8080/allproducts
	public String allProductsFunc(Model model) {
		model.addAttribute("myAllProducts", crudService.retrieveAllProducts());
		return "all-products-page";
	}

	// TODO kontrolieri, kas izfiltrē visus produktus, kuru cena ir mazaka par
	// padoto vērtību
	// localhost:8080/allproducts/1.99
	@GetMapping("/allproducts/{price}")
	public String allProductsByPrice(@PathVariable("price") float price, Model model) {
		ArrayList<Product> allProductsWithPriceLess = filterService.filterByPriceLess(price);
		model.addAttribute("myAllProducts", allProductsWithPriceLess);
		return "all-products-page";

		// return "error-page";//parādīs error-page.html lapu

	}

	@GetMapping("/insert") // localhost:8080/insert
	public String insertProductFunc(Product product) // tiek padots tukšs produkts
	{
		return "insert-page";// parādīs insert-page.html lapu
	}

	// TODO uzlikt visām validācijām savus ziņojumus
	// TODO nodrošināt validāciju arī update gadījumā

	@PostMapping("/insert")
	public String insertProductPostFunc(@Valid Product product, BindingResult result)// tiek saņemts aizpildīts produkts
	{
		if (!result.hasErrors()) {
			// TODO var izveidot dažādas pāŗbaudes
			crudService.insertProductByParams(product.getTitle(), product.getPrice(), product.getDescription(),
					product.getQuantity());
			return "redirect:/allproducts";// izsaucam get kontrolieri localhost:8080/allproducts
		} else {
			return "insert-page";
		}

	}

	// TODO update
	// TODO izveidot get kontrolieri, kas nolasīs produkta id un pēc ta atradīs
	// produktu un nosūtīs
	// caur model objektu uz frontend + parādīt update-page

	@GetMapping("/update/{id}") // localhost:8080/update/1
	public String updateProductByIdGetFunc(@PathVariable("id") int id, Model model) {
		try {
			model.addAttribute("product", crudService.retrieveOneProductById(id));
			return "update-page";// parādīs update-page.html
		} catch (Exception e) {
			return "error-page";
		}

	}

	// izveidot uupdate-page.html, kas strādās uz cita endpoint
	// izveidot post kontrolieri, kas saņemoto objektu redigē arī allProducts
	// sarakstā

	@PostMapping("/update/{id}")
	public String updateProductByIdPostFunc(@PathVariable("id") int id, @Valid Product product, BindingResult result)// ienāk
																														// redigētais
																														// produkts
	{
		if (!result.hasErrors()) {

			try {
				Product temp = crudService.updateProductByParams(id, product.getTitle(), product.getPrice(),
						product.getDescription(), product.getQuantity());
				return "redirect:/product/" + temp.getTitle(); // tiks izsaukst localhost:8080/product/Abols
			} catch (Exception e) {
				return "redirect:/error";// tiks izsaukts localhost:8080/error
			}
		} else {
			return "update-page";
		}

	}

	@GetMapping("/error")
	public String errorFunc() {
		return "error-page";
	}

	// TODO izveidot delete funkcionalitāti

	@GetMapping("/delete/{id}")
	public String deleteProductById(@PathVariable("id") int id, Model model) {
		try {
			crudService.deleteProductById(id);
			model.addAttribute("myAllProducts", crudService.retrieveAllProducts());
			return "all-products-page";// parāda all-products-page.html lapu
		} catch (Exception e) {
			return "error-page";
		}

	}

}