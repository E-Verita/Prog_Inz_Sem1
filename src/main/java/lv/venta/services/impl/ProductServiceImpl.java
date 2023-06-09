package lv.venta.services.impl;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import lv.venta.models.Product;
import lv.venta.services.ICRUDProductService;
import lv.venta.services.IFilteringProductService;

public class ProductServiceImpl implements ICRUDProductService, IFilteringProductService {

	ArrayList<Product> allProducts = new ArrayList<>(Arrays.asList(new Product("Abols", 3.99f, "Sarkans", 3),
			new Product("Tomats", 1.99f, "Dzeltens", 12), new Product("Avokado", 0.99f, "Zals", 10)));

	@Override
	public ArrayList<Product> retrieveAllProducts() {
		return allProducts;
	}

	@Override
	public Product retrieveOneProductById(int id) throws Exception {
		for (Product temp : allProducts) {
			if (temp.getId() == id) {
				return temp;
			}
		}
		throw new Exception("Wrong id");
	}

	@Override
	public ArrayList<Product> retrieveAllProductsByTitle(String title) throws Exception {
		if(title != null) {
			ArrayList<Product> allProductsWithTitle = new ArrayList<>();
			for (Product temp : allProducts) {
				if (temp.getTitle().equals(title)) {
					allProductsWithTitle.add(temp);
		
				}
			}
			return allProductsWithTitle;
		}
		else
		{
			throw new Exception("Wrong title");
		}
	}

	@Override
	public Product insertProductByParams(String title, float price, String description, int quantity) {
		for (Product temp : allProducts) {
			if (temp.getTitle().equals(title) && temp.getPrice() == price
					&& temp.getDescription().equals(description)) {
				temp.setQuantity(temp.getQuantity() + quantity);
				return temp;
			}
		}

		Product newProduct = new Product(title, price, description, quantity);
		allProducts.add(newProduct);
		return newProduct;
	}

	@Override
	public Product updateProductByParams(int id, String title, float price, String description, int quantity)
			throws Exception {
		for (Product temp : allProducts) {
			if (temp.getId() == id) {
				temp.setTitle(title);
				temp.setPrice(price);
				temp.setDescription(description);
				temp.setQuantity(quantity);
				return temp;
			}
		}
		throw new Exception("Wrong id");
	}

	@Override
	public void deleteProductById(int id) throws Exception {
		boolean isFound = false;
		for (Product temp : allProducts) {
			if (temp.getId() == id) {
				allProducts.remove(temp);
				isFound = true;
				break;
			}
		}
		if(!isFound)
		{
			throw new Exception("Wrong id");
		}

	}

	@Override
	public ArrayList<Product> filterByPriceLess(float price) {
		if (price > 0) {

			ArrayList<Product> allProductsWithPriceLess = new ArrayList<>();
			for (Product temp : allProducts) {
				if (temp.getPrice() < price) {
					allProductsWithPriceLess.add(temp);
				}
			}
			return allProductsWithPriceLess;
		}
		return new ArrayList<>();

	}
}