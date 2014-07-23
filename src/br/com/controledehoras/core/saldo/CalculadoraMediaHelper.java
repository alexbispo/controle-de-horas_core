package br.com.controledehoras.core.saldo;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import br.com.controledehoras.core.beans.Feriado;
import br.com.controledehoras.core.beans.RegistroArquivo;
import br.com.controledehoras.core.beans.Tempo;
import br.com.controledehoras.core.tempo.CalcTempoUtil;

/**
 * 
 * @author Cassio Lemos
 *
 */
public class CalculadoraMediaHelper {

	private CalcMedia calcMedia;

	private CalculadoraMediaHelper() {
		this.calcMedia = new CalcMedia();
	}
	
	public static CalculadoraMediaHelper getInstance(){
		return new CalculadoraMediaHelper();
	}

	public Tempo calcularMediaDiariaParaEliminarSaldo(
			List<RegistroArquivo> registros, Quadrimestre quadrimetre,
			List<Feriado> feriados, int dataConsumirAte) {

		Map<String, RegistroArquivo> diasAgrupados = CalcTempoUtil.getInstance()
				.agruparRegistrosPorDia(registros);

		Calendar inicioQuadrimestre = quadrimetre.getCalendarDataInicial();

		Tempo saldoDias = this.calcMedia.getSaldoTotalCalculadoPorDia(
				inicioQuadrimestre, diasAgrupados, feriados);

		Calendar consumirAte = CalcTempoUtil.getInstance().getCalendar(dataConsumirAte);

		Tempo divisao = this.calcMedia.getHorasNecessariasParaOPeriodo(
				consumirAte, saldoDias, feriados);

		return divisao;
	}

}
