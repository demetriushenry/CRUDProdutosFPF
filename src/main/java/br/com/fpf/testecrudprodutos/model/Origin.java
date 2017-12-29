package br.com.fpf.testecrudprodutos.model;

public enum Origin {

	NATIONAL("Nacional"), IMPORTED("Importado");

	private String value;

	private Origin(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
