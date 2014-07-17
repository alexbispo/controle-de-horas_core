package com.beans;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Cassio Lemos
 *
 */
@Entity
public class RegistroArquivoBean implements Serializable{

	// 13/Jun/14#|#AGUARDANDO
	// APROVAÇÃO#|#07:49#|#11:59#|#6538#|#004-2013#|#SIM#|#04:10#|#CODIFICACAO#|#Construção
	// ambiente

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int data;
	@Embedded
	private Tempo tempo;
	private String horaInicial;
	private String horaFinal;

	public RegistroArquivoBean() {
		super();
	}



	public int getData() {
		return data;
	}



	public void setData(int data) {
		this.data = data;
	}



	public Tempo getTempo() {
		return tempo;
	}

	public void setTempo(Tempo tempoBean) {
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
