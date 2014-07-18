package br.com.controledehoras.core.tempo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import br.com.controledehoras.core.beans.Feriado;
import br.com.controledehoras.core.beans.RegistroArquivoBean;
import br.com.controledehoras.core.beans.Tempo;
import br.com.controledehoras.core.saldo.CalcMedia;
import br.com.controledehoras.core.sintonia.SintoniaHelper;

public class TesteTempo {

	public static void main(String[] args) throws Exception {

		System.out.println("Lendo arquivos");
		SintoniaHelper sintonia = new SintoniaHelper();
		List<RegistroArquivoBean> registros = sintonia
				.obterRegistrosDoArquivo("c:/arquivos/AtividadesTrabalhadasPeriodo.csv");

		CalcTempoUtil calc = new CalcTempoUtil();
		Tempo tempoRealizado = calc.totalizarHoras(registros);

		System.out.println("Arquivo - Horas: "
				+ tempoRealizado.getHorasString());

		CalcMedia media = new CalcMedia(calc);

		System.out.println("Saldo por dia");
		System.out.println("----------------------------------------");

		List<Feriado> feriados = new ArrayList<Feriado>();
		feriados.add(new Feriado(calc.getCalendar(20140612), 1d));
		feriados.add(new Feriado(calc.getCalendar(20140619), 1d));
		feriados.add(new Feriado(calc.getCalendar(20140709), 1d));

		Map<String, RegistroArquivoBean> diasAgrupados = calc
				.agruparRegistrosPorDia(registros);

		Calendar inicioQuadrimestre = calc.getCalendar(20140601);
		
		Tempo saldoDias = media.getSaldoTotalCalculadoPorDia(inicioQuadrimestre, diasAgrupados,
				feriados);

		System.out.println("Saldo final: " + saldoDias.getHorasString());
		
		int recuperarAte = 20140831;
		Calendar consumirAte = calc.getCalendar(recuperarAte);
		
		Tempo divisao = media.getHorasNecessariasParaOPeriodo(consumirAte,
				saldoDias, feriados);
		
		System.out.println("Realizar at� " + recuperarAte + " - "
				+ divisao.getHorasString());

	}

}
