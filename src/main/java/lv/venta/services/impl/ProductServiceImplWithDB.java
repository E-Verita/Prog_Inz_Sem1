package lv.venta.services.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Product;
import lv.venta.repo.IProductRepo;
import lv.venta.services.ICRUDProductService;
import lv.venta.services.IFilteringProductService;

@Service
public class ProductServiceImplWithDB implements ICRUDProductService, IFilteringProductService {

	@Autowired
	private IProductRepo productRepo;

	@Override
	public ArrayList<Product> retrieveAllProducts() {
		return (ArrayList<Product>) productRepo.findAll();
	}

	@Override
	public Product retrieveOneProductById(int id) throws Exception {
		if (productRepo.existsById(id)) {
			return productRepo.findById(id).get();
		} else {
			throw new Exception("Wrong Id");
		}
	}

	@Override
	public ArrayList<Product> retrieveAllProductsByTitle(String title) throws Exception {
		if(title!=null) {
			ArrayList<Product> allProductsWithTitle = productRepo.retrieveAllProductsByTitle(title);
			return allProductsWithTitle;
		} else {
			throw new Exception("Wrong title!");
		}
	}

	@Override
	public Product insertProductByParams(String title, float price, String description, int quantity) {
		Product temp = productRepo.save(new Product(title, price, description, quantity));
		return temp;
	}

	@Override
	public Product updateProductByParams(int id, String title, float price, String description, int quantity)
			throws Exception {
		if (productRepo.existsById(id)) {
			Product updatedPr = productRepo.findById(id).get(); // pie findId vienmēr klāt vajag get!
			updatedPr.setTitle(title);
			updatedPr.setDescription(description);
			updatedPr.setQuantity(quantity);
			return productRepo.save(updatedPr);
		} else {
			throw new Exception("Wrong Id");
		}
	}

	@Override
	public void deleteProductById(int id) throws Exception {
		if (productRepo.existsById(id)) {
		productRepo.deleteById(id);
		} else {
			throw new Exception("Wrong Id");
		}
	}

	@Override
	public ArrayList<Product> filterByPriceLess(float price) {
		return productRepo.findByPriceLessThan(price);
	}

}
