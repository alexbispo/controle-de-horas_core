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
	private CalcTempoUtil calcTempoUtil;

	public CalculadoraMediaHelper() {
		this.calcTempoUtil = new CalcTempoUtil();
		this.calcMedia = new CalcMedia(this.calcTempoUtil);
	}

	public Tempo calcularMediaDiariaParaEliminarSaldo(
			List<RegistroArquivo> registros, Quadrimestre quadrimetre,
			List<Feriado> feriados, int dataConsumirAte) {

		Map<String, RegistroArquivo> diasAgrupados = this.calcTempoUtil
				.agruparRegistrosPorDia(registros);

		Calendar inicioQuadrimestre = quadrimetre.getCalendarDataInicial();

		Tempo saldoDias = this.calcMedia.getSaldoTotalCalculadoPorDia(
				inicioQuadrimestre, diasAgrupados, feriados);

		Calendar consumirAte = this.calcTempoUtil.getCalendar(dataConsumirAte);

		Tempo divisao = this.calcMedia.getHorasNecessariasParaOPeriodo(
				consumirAte, saldoDias, feriados);

		return divisao;
	}

}
