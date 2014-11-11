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
		
		List<Registrable> registrosDoArquivo = csv.obterRegistrosDoArquivo("C:\\temp\\AtividadesTrabalhadasPeriodo.csv");
		
		CalculadoraMediaHelper calculadoraMediaHelper = CalculadoraMediaHelper.getInstance();
		
		ITempo tempo = calculadoraMediaHelper.calcularMediaDiariaParaEliminarSaldo(registrosDoArquivo, 20140901, null, 20140930, 8);
		
		CalcTempoUtil calcTempo = CalcTempoUtil.getInstance();
		String horasString = calcTempo.getHorasString(tempo.getMinutos());
		
		CalcMedia media = new CalcMedia();
		ITempo saldoTotalCalculadoPorDia = media.getSaldoTotalCalculadoPorDia(calcTempo.getCalendar(20140901), calcTempo.agruparRegistrosPorDia(registrosDoArquivo), null, 8);
		
		System.out.println(calcTempo.getHorasString(saldoTotalCalculadoPorDia.getMinutos()));
		System.out.println(horasString);
		
	}

}
