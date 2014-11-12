package br.com.controledehoras.core.beans;

/**
 * Interface para resultados e calculos dos saldos<br>
 * Use {@link CalcTempoUtil} para converter ou calcular
 * @author Cássio Lemos
 *
 */
public interface ITempo {
	
	/**
	 * Minutos
	 * @return Long de minutos
	 */
	public long getMinutos();

	public void setMinutos(long minutos);

}
