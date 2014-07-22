package br.com.controledehoras.core.beans;

import java.util.Calendar;

/**
 * 
 * @author Cassio Lemos
 *
 */
public class Feriado {

	private Long id;
	private Calendar data;
	private double taxa = 1d;

	public Feriado() {
		super();
	}

	public Feriado(Calendar data, double taxa) {
		super();
		this.data = data;
		this.taxa = taxa;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public double getTaxa() {
		return taxa;
	}

	public void setTaxa(double taxa) {
		this.taxa = taxa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
