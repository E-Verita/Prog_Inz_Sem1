package lv.venta.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.Product;

// <datatype model, datatype of primary key>
public interface IProductRepo extends CrudRepository<Product, Integer>{
	
	//SELECT * FROM product_table WHERE price < var;
	ArrayList<Product> findByPriceLessThan(float var);
	
	//atlasa pēc nosaukuma
	ArrayList<Product> findByTitle(String var);
	
	//atlasa produktus, kuru daudzums ir lielāks par 10 un cena mazāka par 4 
	ArrayList<Product> findByQuantityGreaterThanAndPriceLessThan(int varQ, int varP);
	
	ArrayList<Product> retrieveAllProductsByTitle(String title);

	void deleteById();

}
