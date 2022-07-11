package it.polito.tdp.nyc.model;

public class Adiacenza implements Comparable <Adiacenza>{

	private String c1;

	private String c2;

	private Double peso;

	public Adiacenza(String c1, String c2, Double peso) {

		super();

		this.c1 = c1;

		this.c2 = c2;

		this.peso = peso;

	}

	public String getC1() {

		return c1;

	}

	public void setC1(String c1) {

		this.c1 = c1;

	}

	public String getC2() {

		return c2;

	}

	public void setC2(String c2) {

		this.c2 = c2;

	}

	public Double getPeso() {

		return peso;

	}

	public void setPeso(Double peso) {

		this.peso = peso;

	}

	@Override

	public int compareTo(Adiacenza o) {

		// TODO Auto-generated method stub

		return peso.compareTo(o.getPeso());

	}

	

	

	

	

	



}