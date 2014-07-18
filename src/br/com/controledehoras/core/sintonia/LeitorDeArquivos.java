package br.com.controledehoras.core.sintonia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class LeitorDeArquivos implements Leitor {
	
	
	public List<String> getLinhas(String caminhoDoArquivo){
		List<String> linhas = new ArrayList<String>();
		try {
	        BufferedReader in = new BufferedReader(new FileReader(caminhoDoArquivo));
	            while (in.ready()) {
	            	String l = in.readLine();
	                linhas.add(l);
	            }
	            in.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
		return linhas;
		
	}
	

}
