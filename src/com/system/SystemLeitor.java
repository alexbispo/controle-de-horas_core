package com.system;

import java.util.List;

import javax.swing.JOptionPane;

import com.beans.RegistroArquivoBean;
import com.dao.RegistroDAO;
import com.sintonia.SintoniaHelper;

public class SystemLeitor {

	public static void main(String[] args) throws Exception {

		String caminhoArquivo = JOptionPane
				.showInputDialog("Caminho do arquivo");

		if (caminhoArquivo == null || "".equals(caminhoArquivo)) {
			JOptionPane.showMessageDialog(null, "Arquivo invalido");
			System.exit(0);
		}

		SintoniaHelper sintonia = new SintoniaHelper();
		List<RegistroArquivoBean> registros = sintonia
				.obterRegistrosDoArquivo(caminhoArquivo);

		for (RegistroArquivoBean registro : registros) {

			// verificar no banco
			List<RegistroArquivoBean> regsSalvos = new RegistroDAO()
					.findByRegistroArquivoBeans(registro);
			if(regsSalvos==null || regsSalvos.isEmpty()){
				new RegistroDAO().salvar(registro);
			}

		}
		
		JOptionPane.showMessageDialog(null, "Finalizado");

	}

}
