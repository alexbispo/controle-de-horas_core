package br.com.controledehoras.core.teste;

public class Teste001 {

	public static void main(String args[]) {

		int numeros[] = new int[]{2, 1, 7};
		
		insertionSort(numeros, numeros.length);
		
		for (int i : numeros) {
			System.out.println(i);
		}
		
	}

	private static void insertionSort(int numeros[], int tam) {
		int i, j, temp;
		int tempMenorQueAtual = 0;
		for (i = 1; i < tam; i++) {
			temp = numeros[i];
			j = i - 1;
			
			if(temp > numeros[j]){
		      	tempMenorQueAtual = 1;
			  }else{
			  	tempMenorQueAtual = 0;
			  }
			while ((j >= 0) && (tempMenorQueAtual==1)) {
				if(numeros[j] > numeros[j+1]){
			      	tempMenorQueAtual = 1;
				  }else{
				  	tempMenorQueAtual = 0;
				  }
				numeros[j + 1] = numeros[j];
				j--;
			}
			numeros[j + 1] = temp;
		}

	}

}
