package br.com.controledehoras.core.sintonia;

import java.util.ArrayList;
import java.util.List;

import br.com.controledehoras.core.beans.RegistroArquivo;
import br.com.controledehoras.core.beans.Tempo;
import br.com.controledehoras.core.tempo.CalcTempoUtil;
/**
 * 
 * @author Cassio Lemos
 *
 */
public final class CSVHelper {

	/**
	 * Passando um caminho do arquivo csv para leitura, retorna uma lista de {@link RegistroArquivo}
	 * com os registros do arquivo
	 * @param caminhoDoArquivo diretorio do arquivo
	 * @return lista de registros
	 * @throws Exception
	 */
	public List<RegistroArquivo> obterRegistrosDoArquivo(
			String caminhoDoArquivo) throws Exception {
		return processarInformacoes(ModoLeituraEnum.ARQUIVO, caminhoDoArquivo);
	}

	/**
	 * Passando o conteudo do arquivo csv, retorna uma lista de {@link RegistroArquivo}
	 * @param texto Conteudo do arquivo csv
	 * @return lista de registros
	 * @throws Exception
	 */
	public List<RegistroArquivo> obterRegistrosDoTexto(String texto)
			throws Exception {
		return processarInformacoes(ModoLeituraEnum.TEXTO, texto);
	}

	private List<RegistroArquivo> processarInformacoes(
			ModoLeituraEnum modoLeitura, String info) throws Exception {
		List<String> linhas = modoLeitura.getLinhas(info);
		if (linhas.isEmpty()) {
			throw new Exception("Arquivo vazio");
		}
		List<RegistroArquivo> registros = new ArrayList<RegistroArquivo>();
		for (String linha : linhas) {
			RegistroArquivo reg = registroBuilder(linha);
			if (reg != null) {
				registros.add(reg);
			}
		}
		return registros;

	}

	private RegistroArquivo registroBuilder(String linha) {
		//trata a linha recebida separando pelo marcador #|#
		CalcTempoUtil calc = new CalcTempoUtil();
		String[] campos = linha.split("\\s*[#|#]+\\s*");
		if (campos != null && campos.length > 7) {
			RegistroArquivo reg = new RegistroArquivo();
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
