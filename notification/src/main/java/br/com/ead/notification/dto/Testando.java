package br.com.ead.notification.dto;

import java.io.Serializable;

public 	class Testando implements Serializable{
	private static final long serialVersionUID = 1L;
	private String valor;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Testando [valor=" + valor + "]";
	}
	
	
}
