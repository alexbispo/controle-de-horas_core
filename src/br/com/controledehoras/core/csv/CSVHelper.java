package br.com.controledehoras.core.csv;

import java.util.ArrayList;
import java.util.List;

import br.com.controledehoras.core.beans.Builder;
import br.com.controledehoras.core.beans.ITempo;
import br.com.controledehoras.core.beans.Registrable;
import br.com.controledehoras.core.tempo.CalcTempoUtil;

/**
 * 
 * @author Cassio Lemos
 *
 */
public final class CSVHelper {

	private CSVHelper() {
	}

	public static CSVHelper getInstance() {
		return new CSVHelper();
	}

	/**
	 * Passando um caminho do arquivo csv para leitura, retorna uma lista de
	 * {@link RegistroArquivo} com os registros do arquivo
	 * 
	 * @param caminhoDoArquivo
	 *            diretorio do arquivo
	 * @return lista de registros
	 * @throws Exception
	 */
	public List<Registrable> obterRegistrosDoArquivo(String caminhoDoArquivo)
			throws Exception {
		return processarInformacoes(ModoLeituraEnum.ARQUIVO, caminhoDoArquivo);
	}

	/**
	 * Passando o conteudo do arquivo csv, retorna uma lista de
	 * {@link RegistroArquivo}
	 * 
	 * @param texto
	 *            Conteudo do arquivo csv
	 * @return lista de registros
	 * @throws Exception
	 */
	public List<Registrable> obterRegistrosDoTexto(String texto)
			throws Exception {
		return processarInformacoes(ModoLeituraEnum.TEXTO, texto);
	}

	private List<Registrable> processarInformacoes(ModoLeituraEnum modoLeitura,
			String info) throws Exception {
		List<String> linhas = modoLeitura.getLinhas(info);
		if (linhas.isEmpty()) {
			throw new Exception("Arquivo vazio");
		}
		List<Registrable> registros = new ArrayList<Registrable>();
		for (String linha : linhas) {
			Registrable reg = registroBuilder(linha);
			if (reg != null) {
				registros.add(reg);
			}
		}
		return registros;

	}

	private Registrable registroBuilder(String linha) {
		// trata a linha recebida separando pelo marcador #|#
		CalcTempoUtil calc = CalcTempoUtil.getInstance();
		String[] campos = linha.split("\\s*[#|#]+\\s*");
		if (campos != null && campos.length > 7) {
			Registrable reg = Builder.builderRegistrable();
			reg.setData(calc.getIntDate(campos[0]));
			reg.setHoraInicial(campos[2]);
			reg.setHoraFinal(campos[3]);
			ITempo tempoBean = Builder.builderITempo(CalcTempoUtil
					.getInstance().getDiferecaHoras(reg.getHoraInicial(), reg.getHoraFinal()));
			reg.setTempo(tempoBean);
			return reg;
		}
		return null;
	}

}
