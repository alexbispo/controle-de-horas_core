package br.com.controledehoras.core.beans;

import java.util.Calendar;

public class Builder {

	public static Registrable buildRegistrable() {
		return RegistroArquivo.getInstance();
	}

	public static Discountable buildDiscountable(int ano, int mes, int dia,
			double taxa) {
		return Feriado.getInstance(ano, mes, dia, taxa);
	}

	public static Discountable buildDiscountable(Calendar data) {
		return Feriado.getInstance(data);
	}

	public static Discountable buildDiscountable(int ano, int mes, int dia) {
		return Feriado.getInstance(ano, mes, dia);
	}

	public static Discountable buildDiscountable() {
		return Feriado.getInstance();
	}

	public static ITempo buildITempo() {
		return Tempo.getInstance();
	}

	public static ITempo buildITempo(long minutos) {
		return Tempo.getInstance(minutos);
	}

	public static ITempo buildITempo(String hhmm) {
		return Tempo.getInstance(hhmm);
	}

	public static ISaldoDia buildISaldoDia() {
		return SaldoDia.getInstance();
	}
}
