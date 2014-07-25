package br.com.controledehoras.core.beans;

import java.util.Calendar;

public class Builder {

	public static Registrable builderRegistrable() {
		return RegistroArquivo.getInstance();
	}

	public static Discountable builderDiscountable(int ano, int mes, int dia,
			double taxa) {
		return Feriado.getInstance(ano, mes, dia, taxa);
	}

	public static Discountable builderDiscountable(Calendar data) {
		return Feriado.getInstance(data);
	}

	public static Discountable builderDiscountable(int ano, int mes, int dia) {
		return Feriado.getInstance(ano, mes, dia);
	}

	public static Discountable builderDiscountable() {
		return Feriado.getInstance();
	}

	public static ITempo builderITempo() {
		return Tempo.getInstance();
	}

	public static ITempo builderITempo(long minutos) {
		return Tempo.getInstance(minutos);
	}

	public static ITempo builderITempo(String hhmm) {
		return Tempo.getInstance(hhmm);
	}

	public static ISaldoDia builderISaldoDia() {
		return SaldoDia.getInstance();
	}
}
