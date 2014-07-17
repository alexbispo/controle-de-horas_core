package com.sintonia;

import java.util.ArrayList;
import java.util.List;

import com.beans.RegistroArquivoBean;
import com.beans.Tempo;
import com.tempo.CalcTempoUtil;
/**
 * 
 * @author Cassio Lemos
 *
 */
public class SintoniaHelper {

	/**
	 * Passando um caminho do arquivo csv para leitura, retorna uma lista de {@link RegistroArquivoBean}
	 * com os registros do arquivo
	 * @param caminhoDoArquivo diretorio do arquivo
	 * @return lista de registros
	 * @throws Exception
	 */
	public List<RegistroArquivoBean> obterRegistrosDoArquivo(
			String caminhoDoArquivo) throws Exception {
		return processarInformacoes(ModoLeituraEnum.ARQUIVO, caminhoDoArquivo);
	}

	/**
	 * Passando o conteudo do arquivo csv, retorna uma lista de {@link RegistroArquivoBean}
	 * @param texto Conteudo do arquivo csv
	 * @return lista de registros
	 * @throws Exception
	 */
	public List<RegistroArquivoBean> obterRegistrosDoTexto(String texto)
			throws Exception {
		return processarInformacoes(ModoLeituraEnum.TEXTO, texto);
	}

	private List<RegistroArquivoBean> processarInformacoes(
			ModoLeituraEnum modoLeitura, String info) throws Exception {
		List<String> linhas = modoLeitura.getLinhas(info);
		if (linhas.isEmpty()) {
			throw new Exception("Arquivo vazio");
		}
		List<RegistroArquivoBean> registros = new ArrayList<RegistroArquivoBean>();
		for (String linha : linhas) {
			RegistroArquivoBean reg = registroBuilder(linha);
			if (reg != null) {
				registros.add(reg);
			}
		}
		return registros;

	}

	private RegistroArquivoBean registroBuilder(String linha) {
		//trata a linha recebida separando pelo marcador #|#
		CalcTempoUtil calc = new CalcTempoUtil();
		String[] campos = linha.split("\\s*[#|#]+\\s*");
		if (campos != null && campos.length > 7) {
			RegistroArquivoBean reg = new RegistroArquivoBean();
			reg.setData(calc.getIntDate(campos[0]));
			reg.setHoraInicial(campos[2]);
			reg.setHoraFinal(campos[3]);
			Tempo tempoBean = new Tempo();
			tempoBean.setMinutosFromStringHHMM(campos[7]);
			reg.setTempo(tempoBean);
			return reg;
		}
		return null;
	}

}
