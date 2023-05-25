package lv.venta.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Table(name="product_table") //db pusē izveidosies tabula
@Entity
public class Product {
	
	@Column(name="Id") //db kolonna
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	
	
	@Column(name="Title")
	@NotNull(message="Lauks nevar būt tukšs!")
	@Pattern(regexp="[A-ZĀČĒĪĶĻŅŠŪŽ]{1}[a-zāčēīķļņšūž\\ ]+", message="Pirmajam burtam jābūt lielajam")
	@Size(min=3, max=30, message="Atļauto zīmju skaits ir 3-30")
	private String title;
	
	@Column(name="Price")
	@Min(value=0)
	@Max(value=10000)
	private float price;
	
	@Column(name="Description")
	@NotNull (message="Lauks nevar būt tukšs!")
	@Pattern(regexp="[A-ZĀČĒĪĶĻŅŠŪŽ]{1}[a-zāčēīķļņšūž\\ ]+", message="Pirmajam burtam jābūt lielajam")
	@Size(min=3, max=100, message="Atļauto zīmju skaits ir 3-100")
	private String description;
	
	@Column(name="Quantity")
	@Min(value=0)
	@Max(value=10000)
	private int quantity;
	
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Product(String title, float price, String description, int quantity) {
		this.title = title;
		this.price = price;
		this.description = description;
		this.quantity = quantity;
	}
	
	public Product() {
	}
	
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", price=" + price + ", description=" + description
				+ ", quatity=" + quantity + "]";
	}
	
	

}
