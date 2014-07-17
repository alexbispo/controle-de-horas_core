package com.beans;

import java.text.DecimalFormat;

import com.tempo.CalcTempoUtil;

/**
 * Utilizada para marcar o tempo de qualquer registros
 * <br>Contem metodos auxiliares
 *   @author Cassio Lemos
 *
 */
public class Tempo {

	private long minutos;

	public Tempo() {
	}
	
	public Tempo(long minutos) {
		this.minutos = minutos;
	}

	/**
	 * @param hhmm
	 *            no formato HH:MM
	 */
	public Tempo(String hhmm) {
		setMinutosFromStringHHMM(hhmm);
	}

	public long getMinutos() {
		return minutos;
	}

	public void setMinutos(long minutos) {
		this.minutos = minutos;
	}

	/**
	 * Altera os minutos recebendo uma String no formato HH:MM ex.: 08:26
	 * @param hhmm
	 */
	public void setMinutosFromStringHHMM(String hhmm) {
		String ar[] = hhmm.split(":");
		long horas = Long.parseLong(ar[0]);
		long minutos = Long.parseLong(ar[1]);
		this.minutos = (minutos + new CalcTempoUtil()
				.transformarHorasEmMinutos(horas));
	}

	/**
	 * Retorna um Array de long sendo [0]= horas [1]= minutos
	 * @return
	 */
	public long[] getHoras() {
		long horas = (int) this.minutos / 60;
		long minutos = this.minutos % 60;
		return new long[] { horas, minutos };

	}

	/**
	 * Retorna horas em String no formato HH:MM ex.: 08:26
	 * @return
	 */
	public String getHorasString() {
		DecimalFormat decFormat = new DecimalFormat("#00");
		long x[] = getHoras();
		return x[0] + ":" + decFormat.format(Math.abs(x[1]));
	}
	
	@Override
	public String toString(){
		return getHorasString();
	}

}
