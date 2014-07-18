package br.com.controledehoras.core.saldo;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import br.com.controledehoras.core.beans.Feriado;
import br.com.controledehoras.core.beans.RegistroArquivoBean;
import br.com.controledehoras.core.beans.SaldoDia;
import br.com.controledehoras.core.beans.Tempo;
import br.com.controledehoras.core.tempo.CalcTempoUtil;
/**
 * 
 * @author Cassio Lemos
 *
 */
public class CalcMedia {

	private CalcTempoUtil calc;

	public CalcMedia(CalcTempoUtil calc) {
		this.calc = calc;
	}

	/**
	 * Totaliza saldo somando o mes inteiro
	 * @param mes
	 * @param ano
	 * @param qtdadeFeriados
	 * @param tempo
	 * @return
	 */
	public Tempo getSaldoDoMes(int mes, int ano, int qtdadeFeriados, Tempo tempo) {

		int diasUteis = getDiasUteis(mes, ano, qtdadeFeriados);
		int totalMesDevido = diasUteis * Config.MEDIA_HORAS;

		long minutosRealizados = tempo.getMinutos();

		long minutosDevidos = this.calc
				.transformarHorasEmMinutos(totalMesDevido);

		long saldo = minutosRealizados - minutosDevidos;

		return new Tempo(saldo);

	}

	/**
	 * A partir de um registro, retorna o saldo do dia
	 * @param registro
	 * @return
	 */
	public SaldoDia getSaldoDoDia(RegistroArquivoBean registro) {
		CalcTempoUtil calc = new CalcTempoUtil();
		long minutosTrabalhados = registro.getTempo().getMinutos();
		long minutosNecessarios = calc
				.transformarHorasEmMinutos(Config.MEDIA_HORAS);
		SaldoDia saldo = new SaldoDia();
		saldo.setData(registro.getData());
		saldo.setTotalDia(minutosTrabalhados);
		saldo.setSaldoDia(minutosTrabalhados - minutosNecessarios);
		return saldo;

	}

	/**
	 * Totaliza o saldo por dia a partir de uma lista
	 * @param dataInicial
	 * @param registros
	 * @param feriados
	 * @return
	 */
	public Tempo getSaldoTotalCalculadoPorDia(Calendar dataInicial,
			Map<String, RegistroArquivoBean> registros, List<Feriado> feriados) {

		long saldoGeral = 0;
		CalcTempoUtil calc = new CalcTempoUtil();
		final long minutosNecessarios = calc
				.transformarHorasEmMinutos(Config.MEDIA_HORAS);

		Calendar hoje = Calendar.getInstance();

		//conta os dias ate hoje (ignorando hoje)
		while (calc.getYYYYMMDD(dataInicial) < calc.getYYYYMMDD(hoje)) {

			boolean bonus = false;
			long minutosTrabalhados = 0;
			double saldoGerado = 0;

			RegistroArquivoBean reg = registros.get(calc
					.getYYYYMMDD(dataInicial) + "");

			//verifica se o dia esta marcado como bonus (feriado, atestado ou qualquer outro)
			Feriado feriado = verificarFeriado(dataInicial, feriados);
			if (feriado != null) {
				bonus = true;
			}

			//verificar se nao e um fim de semana
			if ((dataInicial.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
					|| (dataInicial.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
				// dia util
				bonus = true;
				System.out.println("FDS");
			}

			if (bonus) {
				if (reg != null) {
					if (feriado != null) {
						saldoGerado = (reg.getTempo().getMinutos() * feriado
								.getTaxa());
					} else {
						saldoGerado = reg.getTempo().getMinutos();
					}

					minutosTrabalhados += saldoGerado;
					saldoGeral += (minutosTrabalhados);
					System.out.println("Feriado ou fds trabalhado");
				}else{
					System.out.println("Feriado");
				}
				
			} else {
				if (reg != null) {
					minutosTrabalhados = reg.getTempo().getMinutos();
					saldoGerado = (minutosTrabalhados - minutosNecessarios);
				} else {
					saldoGerado = minutosNecessarios * -1;
				}

				saldoGeral += saldoGerado;
			}

			System.out.println(calc.getDateFormatDDMMMYY(dataInicial));
			System.out.println("Horas trabalhadas: "
					+ calc.transformarMinutosEmHoras(minutosTrabalhados)
							.getHorasString());
			System.out.println("Saldo gerado: "
					+ calc.transformarMinutosEmHoras((long) saldoGerado)
							.getHorasString());
			System.out.println("Saldo acumulado: "
					+ calc.transformarMinutosEmHoras(saldoGeral)
							.getHorasString());
			System.out.println("-------------------------------------------");

			dataInicial.add(Calendar.DAY_OF_YEAR, 1);
		}

		return new Tempo(saldoGeral);
	}

	private Feriado verificarFeriado(Calendar data, List<Feriado> feriados) {

		int anoMesDiaAtual = this.calc.getYYYYMMDD(data);

		for (Feriado feriado : feriados) {

			int anoMesDiaFeriado = this.calc.getYYYYMMDD(feriado.getData());

			if (anoMesDiaAtual == anoMesDiaFeriado) {
				return feriado;
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
	 * Retorna a quantidade de horas necessarias para eliminar o saldo ate a data especificada
	 * (nao verifica se e positivo, caso queira consumir o saldo funciona da mesma forma)
	 * @param consumirAte
	 * @param saldo
	 * @param feriados
	 * @return
	 */
	public Tempo getHorasNecessariasParaOPeriodo(Calendar consumirAte,
			Tempo saldo, List<Feriado> feriados) {

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
		return new Tempo(divisao);

	}

	private int contarDiasUteisAte(Calendar consumirAte, List<Feriado> feriados) {
		Calendar hoje = Calendar.getInstance();

		int diasUteis = 0;
		int intConsumo = this.calc.getYYYYMMDD(consumirAte);
		int intHoje = 0;
		do {
			intHoje = this.calc.getYYYYMMDD(hoje);
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
