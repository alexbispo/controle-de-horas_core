package br.com.controledehoras.core.beans;

/**
 * 
 * @author Cassio Lemos
 *
 */
class SaldoDia implements ISaldoDia {

	private int data;
	private Long totalDia;
	private Long saldoDia;
	
	private SaldoDia(){
		
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public Long getTotalDia() {
		return totalDia;
	}

	public void setTotalDia(Long totalDia) {
		this.totalDia = totalDia;
	}

	public Long getSaldoDia() {
		return saldoDia;
	}

	public void setSaldoDia(Long saldoDia) {
		this.saldoDia = saldoDia;
	}

	public static ISaldoDia getInstance() {
		return new SaldoDia();
	}

}
