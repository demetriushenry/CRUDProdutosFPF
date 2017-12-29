package br.com.fpf.testecrudprodutos.model;

public enum Category {

	ELECTRONIC("Eletronico"), BOOK("Livro"), MUSIC("Musica");

	private String value;

	private Category(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
