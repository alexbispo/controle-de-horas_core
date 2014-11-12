package br.com.controledehoras.core.csv;

public class Informacao {
	
	private String caminhoDoArquivo;
	private String texto;
	private ModoLeituraEnum modoLeituraEnum;
	
	
	private Informacao() {
	}

	/**
	 * Cria e define o tipo da informação para processamento de arquivos via arquivo
	 * @param caminhoDoArquivo Caminho do arquivo a ser processado
	 * @return Informação com o tipo definido
	 */
	public static Informacao buildInformcacaoArquivo(String caminhoDoArquivo){
		Informacao inf = new Informacao();
		inf.caminhoDoArquivo = caminhoDoArquivo;
		inf.modoLeituraEnum = ModoLeituraEnum.ARQUIVO;
		return inf;
	}
	
	/**
	 * Cria e define o tipo da informação para processamento de arquivos via texto (Conteudo)
	 * @param caminhoDoArquivo Caminho do arquivo a ser processado
	 * @return Informação com o tipo definido
	 */
	public static Informacao buildInformcacaoTexto(String texto){
		Informacao inf = new Informacao();
		inf.texto = texto;
		inf.modoLeituraEnum = ModoLeituraEnum.ARQUIVO;
		return inf;
	}

	/**
	 * Retorna a informação de acordo com o tipo de leitura
	 * @return
	 */
	public String getInfo() {
		switch (this.modoLeituraEnum) {
		case ARQUIVO:
			return caminhoDoArquivo;
		case TEXTO:
			return texto;
		}
		return caminhoDoArquivo;
	}

	public ModoLeituraEnum getModoLeituraEnum() {
		return modoLeituraEnum;
	}
	
	
	

}
