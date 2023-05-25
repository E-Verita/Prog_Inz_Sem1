package lv.venta.services;

import java.util.ArrayList;

import lv.venta.models.Product;

public interface ICRUDProductService {
	//CRUD  -create - retrieve - update - delete 

		//retrieve all
		ArrayList<Product> retrieveAllProducts();

		//retrieve one by id
		Product retrieveOneProductById(int id) throws Exception;

		//retrieve one by title
		ArrayList<Product> retrieveAllProductsByTitle(String title) throws Exception;

		//create (insert)
		Product insertProductByParams(String title, float price, String description, int quantity);

		//update
		Product updateProductByParams(int id, String title, float price, String description, int quantity) throws Exception;

		//delete
		void deleteProductById(int id) throws Exception;

		ArrayList<Product> filterByPriceLess(float price);

	

}