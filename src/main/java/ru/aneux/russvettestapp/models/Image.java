package ru.aneux.russvettestapp.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "data")
	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] data;

	@Column(name = "size")
	private long size;

	@OneToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	public Image() { }

	public Image(String contentType, byte[] data, long size) {
		this.contentType = contentType;
		this.data = data;
		this.size = size;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
