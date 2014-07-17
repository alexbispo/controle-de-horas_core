package com.system;

import java.util.ArrayList;
import java.util.List;

import com.beans.Feriado;
import com.beans.RegistroArquivoBean;
import com.beans.Tempo;
import com.dao.RegistroDAO;
import com.saldo.CalcMedia;
import com.saldo.CalculadoraMediaHelper;
import com.saldo.Quadrimestre;
import com.tempo.CalcTempoUtil;

public class SystemRelatorio {

	public static void main(String[] args) throws Exception {

		CalcTempoUtil calc = new CalcTempoUtil();
		CalcMedia calcMedia = new CalcMedia(calc);
		CalculadoraMediaHelper calculadora = new CalculadoraMediaHelper(
				calcMedia, calc);

		List<RegistroArquivoBean> registros = new RegistroDAO().findByDate(20140601, 20140715);
		
		List<Feriado> feriados = new ArrayList<Feriado>();
		feriados.add(new Feriado(calc.getCalendar(20140612), 1d));
		feriados.add(new Feriado(calc.getCalendar(20140619), 1d));
		feriados.add(new Feriado(calc.getCalendar(20140709), 1d));
		
		
		Tempo tempo = calculadora.calcularMediaDiariaParaEliminarSaldo(registros,
				Quadrimestre.SEGUNDO, feriados, 20140831);
		System.out.println(tempo.getHorasString());
		System.exit(0);

	}

}
