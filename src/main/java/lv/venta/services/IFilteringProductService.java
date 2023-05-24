package lv.venta.services;

import java.util.ArrayList;

import lv.venta.models.Product;

public interface IFilteringProductService {
	ArrayList <Product> filterByPriceLess(float price);
}
