package br.com.controledehoras.core.beans;

/**
 * 
 * @author Cassio Lemos
 *
 */
final class RegistroArquivo implements Registrable {

	// 13/Jun/14#|#AGUARDANDO
	// APROVAÇÃO#|#07:49#|#11:59#|#6538#|#004-2013#|#SIM#|#04:10#|#CODIFICACAO#|#Construção
	// ambiente

	private Long id;
	private int data;
	private ITempo tempo;
	private String horaInicial;
	private String horaFinal;

	private RegistroArquivo() {
		super();
	}
	
	public static Registrable getInstance(){
		return new RegistroArquivo();
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public ITempo getTempo() {
		return tempo;
	}

	public void setTempo(ITempo tempoBean) {
		this.tempo = tempoBean;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(String horaInicial) {
		this.horaInicial = horaInicial;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}

	@Override
	public String toString() {
		return "RegistroArquivoBean [data=" + data + ", totalHoras=" + tempo
				+ "]";
	}

}
