package br.com.fpf.testecrudprodutos.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "product", catalog = "fpfproductcrudtest")
public class Product implements Serializable {

	private Integer id;
	private String description;
	private Date purchaseDate;
	private byte[] image;
	private BigDecimal price;
	private Origin origin;
	private Category category;

	public Product() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false, columnDefinition = "INT")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "description", nullable = false, columnDefinition = "VARCHAR")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "purchasedate", columnDefinition = "TIMESTAMP", length = 19)
	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	@Lob
	@Column(name="image")
	@Type(type="org.hibernate.type.BinaryType")
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	@Digits(fraction = 2, integer = 9)
	@Column(name = "price", precision = 7, scale = 2, nullable = false)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "origin", nullable = false)
	public Origin getOrigin() {
		return origin;	
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "category", nullable = false)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
