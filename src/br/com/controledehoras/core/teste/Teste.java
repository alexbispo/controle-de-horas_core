package br.com.controledehoras.core.teste;

import java.util.ArrayList;
import java.util.List;

import br.com.controledehoras.core.beans.Builder;
import br.com.controledehoras.core.beans.Discountable;
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
		
		csv.adicionarArquivo("C:\\temp\\AtividadesTrabalhadasPeriodo2.csv");
		csv.adicionarArquivo("C:\\temp\\AtividadesTrabalhadasPeriodo1.csv");
		
		List<Registrable> registrosDoArquivo = csv.obterRegistrosDosArquivosAdicionados();
		
		CalculadoraMediaHelper calculadoraMediaHelper = CalculadoraMediaHelper.getInstance();
		
		Discountable discountable = Builder.buildDiscountable(2014, 11, 20, 1.4);
		
		List<Discountable> listaDescontos = new ArrayList<Discountable>();
		listaDescontos.add(discountable);
		
		
		ITempo tempo = calculadoraMediaHelper.calcularMediaDiariaParaEliminarSaldo(registrosDoArquivo, 20141001, listaDescontos, 20141220, 8);
		
		CalcTempoUtil calcTempo = CalcTempoUtil.getInstance();
		String horasString = calcTempo.getHorasString(tempo.getMinutos());
		
		CalcMedia media = new CalcMedia();
		ITempo saldoTotalCalculadoPorDia = media.getSaldoTotalCalculadoPorDia(calcTempo.getCalendar(20141001), calcTempo.agruparRegistrosPorDia(registrosDoArquivo), listaDescontos, 8);
		
		System.out.println(calcTempo.getHorasString(saldoTotalCalculadoPorDia.getMinutos()));
		System.out.println(horasString);
		
	}

}
