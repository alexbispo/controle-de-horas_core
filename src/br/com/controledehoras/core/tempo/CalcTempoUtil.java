package br.com.controledehoras.core.tempo;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import br.com.controledehoras.core.beans.Builder;
import br.com.controledehoras.core.beans.Registrable;
import br.com.controledehoras.core.beans.ITempo;

/**
 * Classe com funções de conversão de tempo
 * @author Cassio Lemos
 *
 */
public final class CalcTempoUtil {

	private static CalcTempoUtil INSTANCE;

	private CalcTempoUtil() {
	}

	public static CalcTempoUtil getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CalcTempoUtil();
		}
		return INSTANCE;
	}

	public ITempo totalizarHoras(List<Registrable> registros) {

		long somaMinutos = 0;

		for (Registrable reg : registros) {
			somaMinutos += reg.getTempo().getMinutos();
		}

		return Builder.buildITempo(somaMinutos);
	}

	/**
	 * A partir de uma lista de {@link RegistroArquivo} agrupa e ordena os
	 * registros por dia
	 * 
	 * @param registros
	 * @return
	 */
	public Map<String, Registrable> agruparRegistrosPorDia(
			List<Registrable> registros) {

		Map<String, Registrable> somaPorDia = new TreeMap<String, Registrable>();

		for (Registrable reg : registros) {
			Long soma = 0l;
			String key = getYYYYMMDD(getCalendar(reg.getData())) + "";
			if (somaPorDia.containsKey(key)) {
				soma = somaPorDia.get(key).getTempo().getMinutos();
			}
			soma += reg.getTempo().getMinutos();

			Registrable newReg = Builder.buildRegistrable();
			newReg.setData(reg.getData());
			ITempo tempo = Builder.buildITempo(soma);
			newReg.setTempo(tempo);

			somaPorDia.put(key, newReg);
		}

		return somaPorDia;
	}

	public ITempo transformarMinutosEmHoras(long minutos) {
		return Builder.buildITempo(minutos);
	}

	public long transformarHorasEmMinutos(long horas, long minutosAdicionais) {
		return (horas * 60) + minutosAdicionais;
	}

	public long transformarHorasEmMinutos(long horas) {
		return transformarHorasEmMinutos(horas, 0);
	}

	/**
	 * Retorna a data no formato utilizado no arquivo CSV a partir de um
	 * Calendar
	 * 
	 * @param date
	 * @return
	 */
	public String getDateFormatDDMMMYY(Calendar date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
		return sdf.format(date.getTime());
	}

	/**
	 * Cria um Calendar a partir de uma String no formato yyyyMMdd
	 * 
	 * @param strDate
	 * @return
	 */
	public Calendar getCalendar(String strDate) {
		Calendar c = null;
		try {
			c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = sdf.parse(strDate);
			c.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * Cria um Calendar a partir de uma data int
	 * 
	 * @param date
	 *            formato esperado yyyy mm dd
	 * @return
	 */
	public Calendar getCalendar(int ano, int mes, int dia) {
		StringBuilder builder = new StringBuilder();
		builder.append(ano).append(mes).append(dia);
		return getCalendar(builder.toString());
	}

	/**
	 * Cria um Calendar a partir de uma data int
	 * 
	 * @param date
	 *            formato esperado yyyy mm dd
	 * @return
	 */
	public Calendar getCalendar(int intData) {
		return getCalendar(intData + "");
	}

	/**
	 * Retorna a data no formato YYYYMMDD em int
	 * 
	 * @param data
	 * @return
	 */
	public int getYYYYMMDD(Calendar data) {
		DecimalFormat format = new DecimalFormat("00");
		String strData = data.get(Calendar.YEAR) + ""
				+ format.format(data.get(Calendar.MONTH) + 1) + ""
				+ format.format(data.get(Calendar.DAY_OF_MONTH)) + "";
		return Integer.parseInt(strData);
	}

	/**
	 * Cria um Calendar a partir de uma data int
	 * 
	 * @param date
	 *            formato esperado DD/MMM/YY
	 * @return
	 */
	public int getIntDate(String strDate) {
		Calendar c = null;
		try {
			c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy", Locale.US);
			Date date = sdf.parse(strDate);
			c.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getYYYYMMDD(c);
	}

	/**
	 * Altera os minutos recebendo uma String no formato HH:MM ex.: 08:26
	 * 
	 * @param hhmm
	 */
	public long getMinutosFromStringHHMM(String hhmm) {
		String ar[] = hhmm.split(":");
		long horas = 0;
		long minutos = 0;
//		horas = Long.parseLong(ar[0]);
//		minutos = Long.parseLong(ar[1]);
		if (hhmm!=null && !"PENDENTE".equals(hhmm) && !"".equals(hhmm)) {
			horas = Long.parseLong(ar[0]);
			minutos = Long.parseLong(ar[1]);
		}
		return (minutos + CalcTempoUtil.getInstance()
				.transformarHorasEmMinutos(horas));
	}

	/**
	 * Retorna um Array de long sendo [0]= horas [1]= minutos
	 * 
	 * @return
	 */
	public long[] getHoras(long minutos) {
		long horas = (int) minutos / 60;
		long m = minutos % 60;
		return new long[] { horas, m };

	}

	/**
	 * Retorna horas em String no formato HH:MM ex.: 08:26
	 * 
	 * @return
	 */
	public String getHorasString(long minutos) {
		DecimalFormat decFormat = new DecimalFormat("#00");
		long x[] = getHoras(minutos);
		return x[0] + ":" + decFormat.format(Math.abs(x[1]));
	}
	
	/**
	 * Retorna a diferença em minutos de dois horarios
	 * @param hhmmInicial
	 * @param hhmmFinal
	 * @return
	 */
	public long getDiferecaHoras(String hhmmInicial, String hhmmFinal){
		long horaInicial = getMinutosFromStringHHMM(hhmmInicial);
		long horarioFinal = getMinutosFromStringHHMM(hhmmFinal);
		if(horarioFinal==0){
			horarioFinal = getHorarioAtual();
		}
		return horarioFinal - horaInicial;
	}
	
	private long getHorarioAtual(){
		SimpleDateFormat sf = new SimpleDateFormat("hh:mm");
		String hhmm = sf.format(new Date());
		return getMinutosFromStringHHMM(hhmm);
	}
	

}
