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

	
	/**
	 * Calcula a media diaria para eliminar saldo tanto positivo quanto negativo dentro de um periodo especificado
	 * @param registros
	 * @param quadrimetre
	 * @param feriados
	 * @param dataConsumirAte
	 * @param mediaHorasPorDia
	 * @return
	 */
	public ITempo calcularMediaDiariaParaEliminarSaldo(
			List<Registrable> registros, Quadrimestre quadrimetre,
			List<Discountable> feriados, int dataConsumirAte,
			int mediaHorasPorDia) {

		return calcularMediaDiariaParaEliminarSaldo(registros,
				quadrimetre.getDataInicial(), feriados, dataConsumirAte,
				mediaHorasPorDia);
	}
	
/**
 * Calcula a media diaria para eliminar saldo positivo ou negativo a partir de um periodo especificado
 * @param registros Registros de trabalhos
 * @param dataInicial Data inicial do periodo a ser calculado
 * @param feriados Lista de dias que não são uteis exceto finais de semana que o calculo é automatico
 * @param dataConsumirAte Data que pretende eliminar o saldo
 * @param mediaHorasPorDia Quantidade de horas que você precisa trabalhar
 * @return {@link ITempo}
 */
	public ITempo calcularMediaDiariaParaEliminarSaldo(
			List<Registrable> registros, int dataInicial,
			List<Discountable> feriados, int dataConsumirAte,
			int mediaHorasPorDia) {

		Map<String, Registrable> diasAgrupados = CalcTempoUtil.getInstance()
				.agruparRegistrosPorDia(registros);

		Calendar inicioQuadrimestre = CalcTempoUtil.getInstance().getCalendar(
				dataInicial);

		ITempo saldoDias = this.calcMedia.getSaldoTotalCalculadoPorDia(
				inicioQuadrimestre, diasAgrupados, feriados, mediaHorasPorDia);

		Calendar consumirAte = CalcTempoUtil.getInstance().getCalendar(
				dataConsumirAte);

		ITempo divisao = this.calcMedia.getHorasNecessariasParaOPeriodo(
				consumirAte, saldoDias, feriados);

		return divisao;
	}

}
