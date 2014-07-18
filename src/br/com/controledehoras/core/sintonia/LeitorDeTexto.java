package br.com.controledehoras.core.sintonia;

import java.util.ArrayList;
import java.util.List;

final class LeitorDeTexto implements Leitor {

	@Override
	public List<String> getLinhas(String info) {
		String[] arLinhas = info.split("\n");
		List<String> linhas = new ArrayList<String>();
		for (String linha : arLinhas) {
			linhas.add(linha);
		}
		return linhas;//TODO: TESTAR QUEBRA DE LINHAS
	}

}
