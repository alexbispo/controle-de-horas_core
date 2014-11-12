package br.com.controledehoras.core.teste;

import java.util.List;

import br.com.controledehoras.core.beans.ITempo;
import br.com.controledehoras.core.beans.Registrable;
import br.com.controledehoras.core.csv.CSVHelper;
import br.com.controledehoras.core.saldo.CalcMedia;
import br.com.controledehoras.core.saldo.CalculadoraMediaHelper;
import br.com.controledehoras.core.tempo.CalcTempoUtil;

public class Teste {

	public static void main(String[] args) throws Exception {

		//exemplo
		CSVHelper csv = CSVHelper.getInstance();
		
		csv.adicionarArquivo("C:\\temp\\AtividadesTrabalhadasPeriodo.csv");
		csv.adicionarArquivo("C:\\temp\\AtividadesTrabalhadasPeriodo2.csv");
		csv.adicionarTexto("");
		
		List<Registrable> registrosDoArquivo = csv.obterRegistrosDosArquivosAdicionados();
		
		CalculadoraMediaHelper calculadoraMediaHelper = CalculadoraMediaHelper.getInstance();
		
		ITempo tempo = calculadoraMediaHelper.calcularMediaDiariaParaEliminarSaldo(registrosDoArquivo, 20141001, null, 20141223, 8);
		
		CalcTempoUtil calcTempo = CalcTempoUtil.getInstance();
		String horasString = calcTempo.getHorasString(tempo.getMinutos());
		
		CalcMedia media = new CalcMedia();
		ITempo saldoTotalCalculadoPorDia = media.getSaldoTotalCalculadoPorDia(calcTempo.getCalendar(20141001), calcTempo.agruparRegistrosPorDia(registrosDoArquivo), null, 8);
		
		System.out.println(calcTempo.getHorasString(saldoTotalCalculadoPorDia.getMinutos()));
		System.out.println(horasString);
		
	}

}
