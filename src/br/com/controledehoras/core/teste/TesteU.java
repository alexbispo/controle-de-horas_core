package br.com.controledehoras.core.teste;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TesteU {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<String> lista = new ArrayList<String>();
		lista.add("");
		System.out.println(campoNulo(lista));

	}

	public static boolean campoNulo(Object o) {
		if(o==null){
			return true;
		}
		
		if(o instanceof Collection){
			Collection c = (Collection) o;
			if(c.isEmpty()){
				return true;
			}
		}
		if(o instanceof String){
			String str = (String) o;
			if("".equals(str)){
				return true;
			}
		}
				
		return false;
	}

}
