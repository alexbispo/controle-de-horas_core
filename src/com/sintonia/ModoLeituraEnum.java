package com.sintonia;

import java.util.List;
/**
 * 
 * @author Cassio Lemos
 *
 */
enum ModoLeituraEnum {

	ARQUIVO {

		@Override
		public List<String> getLinhas(String info) {
			return new LeitorDeArquivos().getLinhas(info);
		}

	},
	TEXTO {

		@Override
		public List<String> getLinhas(String info) {
			return new LeitorDeTexto().getLinhas(info);
		}

	};

	public abstract List<String> getLinhas(String info);

}
