package br.com.controledehoras.core.saldo;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import br.com.controledehoras.core.beans.Discountable;
import br.com.controledehoras.core.beans.ITempo;
import br.com.controledehoras.core.beans.Registrable;
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

	public static CalculadoraMediaHelper getInstance() {
		return new CalculadoraMediaHelper();
	}

	public ITempo calcularMediaDiariaParaEliminarSaldo(
			List<Registrable> registros, Quadrimestre quadrimetre,
			List<Discountable> feriados, int dataConsumirAte, int mediaHorasPorDia) {

		Map<String, Registrable> diasAgrupados = CalcTempoUtil.getInstance()
				.agruparRegistrosPorDia(registros);

		Calendar inicioQuadrimestre = quadrimetre.getCalendarDataInicial();

		ITempo saldoDias = this.calcMedia.getSaldoTotalCalculadoPorDia(
				inicioQuadrimestre, diasAgrupados, feriados, mediaHorasPorDia);

		Calendar consumirAte = CalcTempoUtil.getInstance().getCalendar(
				dataConsumirAte);

		ITempo divisao = this.calcMedia.getHorasNecessariasParaOPeriodo(
				consumirAte, saldoDias, feriados);

		return divisao;
	}

}
