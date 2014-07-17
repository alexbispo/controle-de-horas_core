package com.saldo;

import java.util.Calendar;
import com.tempo.CalcTempoUtil;

/**
 * 
 * @author Cassio Lemos
 *
 */
public enum Quadrimestre {
	
	PRIMEIRO(20140201),
	SEGUNDO(20140601),
	TERCEIRO(20141001);
	
	private int dataInicial;
	
	private Quadrimestre(int dataInicial){
		this.dataInicial = dataInicial;
	}

	public int getDataInicial(){
		return this.dataInicial;
	}
	
	public Calendar getCalendarDataInicial(){
		return new CalcTempoUtil().getCalendar(this.dataInicial);
	}
}
