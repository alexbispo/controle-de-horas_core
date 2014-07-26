package br.com.controledehoras.core.beans;

import java.util.Calendar;

public interface Discountable {
	public Calendar getData();

	public void setData(Calendar data);

	public void setData(int ano, int mes, int dia);

	public double getTaxa();

	public void setTaxa(double taxa);
}
