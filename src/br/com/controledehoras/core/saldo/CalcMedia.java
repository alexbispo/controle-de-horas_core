package br.com.controledehoras.core.saldo;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import br.com.controledehoras.core.beans.Builder;
import br.com.controledehoras.core.beans.Discountable;
import br.com.controledehoras.core.beans.ISaldoDia;
import br.com.controledehoras.core.beans.ITempo;
import br.com.controledehoras.core.beans.Registrable;
import br.com.controledehoras.core.tempo.CalcTempoUtil;

/**
 * 
 * @author Cassio Lemos
 * 
 */
public final class CalcMedia {

	public CalcMedia() {
	}

	/**
	 * Totaliza saldo somando o mes inteiro
	 * 
	 * @param mes
	 * @param ano
	 * @param qtdadeFeriados
	 * @param tempo
	 * @return
	 */
	@Deprecated
	public ITempo getSaldoDoMes(int mes, int ano, int qtdadeFeriados,
			ITempo tempo, int mediaHorasPorDia) {

		int diasUteis = getDiasUteis(mes, ano, qtdadeFeriados);
		int totalMesDevido = diasUteis * mediaHorasPorDia;

		long minutosRealizados = tempo.getMinutos();

		long minutosDevidos = CalcTempoUtil.getInstance()
				.transformarHorasEmMinutos(totalMesDevido);

		long saldo = minutosRealizados - minutosDevidos;

		return Builder.buildITempo(saldo);

	}

	/**
	 * A partir de um registro, retorna o saldo do dia
	 * 
	 * @param registro
	 * @return
	 */
	public ISaldoDia getSaldoDoDia(Registrable registro, int mediaHorasPorDia) {
		CalcTempoUtil calc = CalcTempoUtil.getInstance();
		long minutosTrabalhados = registro.getTempo().getMinutos();
		long minutosNecessarios = calc
				.transformarHorasEmMinutos(mediaHorasPorDia);
		ISaldoDia saldo = Builder.buildISaldoDia();
		saldo.setData(registro.getData());
		saldo.setTotalDia(minutosTrabalhados);
		saldo.setSaldoDia(minutosTrabalhados - minutosNecessarios);
		return saldo;

	}

	/**
	 * Totaliza o saldo por dia a partir de uma lista ate a data atual, exceto
	 * HOJE
	 * 
	 * @param dataInicial
	 * @param registros
	 * @param feriados
	 * @return
	 */
	public ITempo getSaldoTotalCalculadoPorDia(Calendar dataInicial,
			Map<String, Registrable> registros, List<Discountable> feriados,
			int mediaHorasPorDia) {

		long saldoGeral = 0;
		CalcTempoUtil calc = CalcTempoUtil.getInstance();
		final long minutosNecessarios = calc
				.transformarHorasEmMinutos(mediaHorasPorDia);

		Calendar hoje = Calendar.getInstance();

		// conta os dias ate hoje (ignorando hoje)
		while (calc.getYYYYMMDD(dataInicial) < calc.getYYYYMMDD(hoje)) {

			boolean bonus = false;

			Registrable reg = registros.get(calc.getYYYYMMDD(dataInicial) + "");

			// verifica se o dia esta marcado como bonus (feriado, atestado ou
			// qualquer outro)
			Discountable feriado = verificarFeriado(dataInicial, feriados);
			if (feriado != null) {
				bonus = true;
			}

			saldoGeral += getSaldoPorDia(dataInicial, minutosNecessarios, bonus, reg, feriado);
			dataInicial.add(Calendar.DAY_OF_YEAR, 1);
		}

		return Builder.buildITempo(saldoGeral);
	}

	
//	public long getSaldoPorDia(final long minutosNecessarios, boolean bonus,
//			List<Registrable> registrosDoDia, Discountable feriado) {
//		
//		Map<String, Registrable> agrupado = CalcTempoUtil.getInstance().agruparRegistrosPorDia(registrosDoDia);
//		
//		
//	}
	
	
	/**
	 * Retorna o saldo do dia informado
	 * @param dataInicial2 
	 * 
	 * @param minutosNecessarios
	 *            Minutos necess�rio do trabalho
	 * @param bonus
	 *            Se o tempo trabalho � bonus
	 * @param registroDoDia
	 *            {@link Registrable} Registros do dia
	 * @param feriado
	 *            {@link Discountable} Feriado encontrado
	 * @return saldo do dia
	 */
	public long getSaldoPorDia(Calendar dataInicial, final long minutosNecessarios, boolean bonus,
			Registrable registroDoDia, Discountable feriado) {
		double saldoGerado;
//		if (registroDoDia != null) {
//			dataInicial = CalcTempoUtil.getInstance()
//					.getCalendar(registroDoDia.getData());
//
//		} 
		// verificar se nao e um fim de semana
		if ((dataInicial.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				|| (dataInicial.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
			// dia util
			bonus = true;
		}

		double minutosTrabalhados = 0;
		long saldoGeral = 0;
		if (bonus) {
			if (registroDoDia != null) {
				if (feriado != null) {
					saldoGerado = (registroDoDia.getTempo().getMinutos() * feriado
							.getTaxa());
				} else {
					saldoGerado = registroDoDia.getTempo().getMinutos();
				}

				minutosTrabalhados += saldoGerado;
				saldoGeral += (minutosTrabalhados);
			}
		} else {
			if (registroDoDia != null) {
				minutosTrabalhados = registroDoDia.getTempo().getMinutos();
				saldoGerado = (minutosTrabalhados - minutosNecessarios);
			} else {
				saldoGerado = minutosNecessarios * -1;
			}

			saldoGeral += saldoGerado;
		}
		return saldoGeral;
	}

	private Discountable verificarFeriado(Calendar data,
			List<Discountable> feriados) {

		int anoMesDiaAtual = CalcTempoUtil.getInstance().getYYYYMMDD(data);

		if (feriados != null) {
			for (Discountable feriado : feriados) {

				int anoMesDiaFeriado = CalcTempoUtil.getInstance().getYYYYMMDD(
						feriado.getData());

				if (anoMesDiaAtual == anoMesDiaFeriado) {
					return feriado;
				}
			}
		}
		return null;
	}

	private int getDiasUteis(int mes, int ano, int excecoes) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, ano);
		c.set(Calendar.MONTH, mes);
		int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		int diasUteis = 0;
		for (int i = 1; i <= maxDay; i++) {
			c.set(Calendar.DAY_OF_MONTH, i);

			if (!(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
					&& !(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
				diasUteis++;
			}
		}
		return diasUteis - excecoes;
	}

	/**
	 * Retorna a quantidade de horas necessarias para eliminar o saldo ate a
	 * data especificada (nao verifica se e positivo, caso queira consumir o
	 * saldo funciona da mesma forma)
	 * 
	 * @param consumirAte
	 * @param saldo
	 * @param feriados
	 * @return
	 */
	public ITempo getHorasNecessariasParaOPeriodo(Calendar consumirAte,
			ITempo saldo, List<Discountable> feriados) {

		int dias = contarDiasUteisAte(consumirAte, feriados);
		long divisao = saldo.getMinutos() / (dias < 1 ? 1 : dias);
		double resto = saldo.getMinutos() % (dias < 1 ? 1 : dias);

		if (Math.abs(resto) > 0d) {
			if (divisao > 0) {
				divisao += 1;
			} else {
				divisao -= 1;
			}

		}
		return Builder.buildITempo(divisao);

	}

	private int contarDiasUteisAte(Calendar consumirAte,
			List<Discountable> feriados) {
		Calendar hoje = Calendar.getInstance();

		int diasUteis = 0;
		int intConsumo = CalcTempoUtil.getInstance().getYYYYMMDD(consumirAte);
		int intHoje = 0;
		do {
			intHoje = CalcTempoUtil.getInstance().getYYYYMMDD(hoje);
			if (!(hoje.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
					&& !(hoje.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
				if (verificarFeriado(hoje, feriados) == null) {
					diasUteis++;
				}

			}
			hoje.add(Calendar.DAY_OF_MONTH, 1);
		} while (intHoje <= intConsumo);
		return diasUteis;
	}

}
