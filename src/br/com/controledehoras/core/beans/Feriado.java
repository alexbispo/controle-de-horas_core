package br.com.controledehoras.core.beans;

import java.util.Calendar;

import br.com.controledehoras.core.tempo.CalcTempoUtil;

/**
 * 
 * @author Cassio Lemos
 *
 */
public class Feriado {

	private Calendar data;
	private double taxa = 1d;

	public Feriado() {
		super();
	}

	public Feriado(int ano, int mes, int dia, double taxa) {
		super();
		setData(ano, mes, dia);
		this.taxa = taxa;
	}
	
	public Feriado(Calendar data){
		this.data = data;
	}

	public Feriado(int ano, int mes, int dia){
		setData(ano, mes, dia);
	}
			
	public Calendar getData() {
		return (Calendar) data.clone();
	}

	public void setData(Calendar data) {
		this.data = data;
	}
	
	public void setData(int ano, int mes, int dia) {
		this.data = CalcTempoUtil.getInstance().getCalendar(ano, mes, dia);
	}

	public double getTaxa() {
		return taxa;
	}

	public void setTaxa(double taxa) {
		this.taxa = taxa;
	}

}
