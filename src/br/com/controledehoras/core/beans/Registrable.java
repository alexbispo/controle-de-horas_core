package br.com.controledehoras.core.beans;

public interface Registrable {

	public int getData();

	public void setData(int data);

	public ITempo getTempo();

	public void setTempo(ITempo tempoBean);

	public Long getId();

	public void setId(Long id);

	public String getHoraInicial();

	public void setHoraInicial(String horaInicial);

	public String getHoraFinal();

	public void setHoraFinal(String horaFinal);
	
}
