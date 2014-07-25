package br.com.controledehoras.core.beans;

import br.com.controledehoras.core.tempo.CalcTempoUtil;

/**
 * Utilizada para marcar o tempo de qualquer registros
 * 
 * @author Cassio Lemos
 *
 */
final class Tempo implements ITempo {

	private long minutos;

	private Tempo() {
	}

	private Tempo(long minutos) {
		this.minutos = minutos;
	}

	/**
	 * @param hhmm
	 *            no formato HH:MM
	 */
	private Tempo(String hhmm) {
		this.minutos = CalcTempoUtil.getInstance().getMinutosFromStringHHMM(
				hhmm);
	}

	public long getMinutos() {
		return minutos;
	}

	public void setMinutos(long minutos) {
		this.minutos = minutos;
	}

	public static ITempo getInstance() {
		return new Tempo();
	}

	public static ITempo getInstance(long minutos) {
		return new Tempo(minutos);
	}

	public static ITempo getInstance(String hhmm) {
		return new Tempo(hhmm);
	}

}
