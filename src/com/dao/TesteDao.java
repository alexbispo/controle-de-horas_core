package com.dao;

import java.util.List;

import com.beans.RegistroArquivoBean;
import com.beans.Tempo;

/**
 * 
 * @author Cassio Lemos
 *
 */
public class TesteDao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		RegistroArquivoBean reg = new RegistroArquivoBean();
		reg.setData(20140814);
		Tempo tempo = new Tempo();
		tempo.setMinutos(10);
		reg.setTempo(tempo);
		
		RegistroDAO dao = new RegistroDAO();
		dao.salvar(reg);
		
		
		try {
			List<RegistroArquivoBean> lista = new RegistroDAO().findAll();
			
			for (RegistroArquivoBean registro : lista) {
				System.out.println(registro);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
