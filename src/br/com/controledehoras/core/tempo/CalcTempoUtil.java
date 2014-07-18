package br.com.controledehoras.core.tempo;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.controledehoras.core.beans.RegistroArquivo;
import br.com.controledehoras.core.beans.Tempo;
/**
 * 
 * @author Cassio Lemos
 *
 */
public final class CalcTempoUtil {

	public Tempo totalizarHoras(List<RegistroArquivo> registros) {

		long somaMinutos = 0;

		for (RegistroArquivo reg : registros) {
			somaMinutos += reg.getTempo().getMinutos();
		}

		return new Tempo(somaMinutos);
	}

	/**
	 * A partir de uma lista de {@link RegistroArquivo} agrupa e ordena os registros por dia
	 * @param registros
	 * @return
	 */
	public Map<String, RegistroArquivo> agruparRegistrosPorDia(
			List<RegistroArquivo> registros) {

		Map<String, RegistroArquivo> somaPorDia = new TreeMap<String, RegistroArquivo>();

		for (RegistroArquivo reg : registros) {
			Long soma = 0l;
			String key = getYYYYMMDD(getCalendar(reg.getData()))+"";
			if (somaPorDia.containsKey(key)) {
				soma = somaPorDia.get(key).getTempo().getMinutos();
			}
			soma += reg.getTempo().getMinutos();
			
			RegistroArquivo newReg = new RegistroArquivo();
			newReg.setData(reg.getData());
			Tempo tempo = new Tempo();
			tempo.setMinutos(soma);
			newReg.setTempo(tempo);
			
			
			somaPorDia.put(key, newReg);
		}

		return somaPorDia;
	}

	public Tempo transformarMinutosEmHoras(long minutos) {
		return new Tempo(minutos);
	}

	public long transformarHorasEmMinutos(long horas, long minutosAdicionais) {
		return (horas * 60) + minutosAdicionais;
	}

	public long transformarHorasEmMinutos(long horas) {
		return transformarHorasEmMinutos(horas, 0);
	}

	/**
	 * Retorna a data no formato utilizado no arquivo CSV a partir de um Calendar
	 * @param date
	 * @return
	 */
	public String getDateFormatDDMMMYY(Calendar date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
		return sdf.format(date.getTime());
	}

	/**
	 * Cria um Calendar a partir de uma data int
	 * @param date
	 *            formato esperado yymmdd
	 * @return
	 */
	public Calendar getCalendar(int intDate) {
		Calendar c = null;
		try {
			c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = sdf.parse(intDate+"");
			c.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * Retorna a data no formato YYYYMMDD em int
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
	 * @param date
	 *            formato esperado DD/MMM/YY
	 * @return
	 */
	public int getIntDate(String strDate) {
		Calendar c = null;
		try {
			c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
			Date date = sdf.parse(strDate);
			c.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getYYYYMMDD(c);
	}

}
