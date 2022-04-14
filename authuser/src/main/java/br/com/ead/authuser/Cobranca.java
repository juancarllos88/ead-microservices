package br.com.ead.authuser;

import java.util.Arrays;
import java.util.List;

public class Cobranca {
	
	
	public static void main(String[] args) {
		//Integer valorDoPai = 65;
		//Integer valorDoPai = 133;
		Integer valorDoPai = 7;
		//List<Integer> mensalidades = Arrays.asList(222,89,411,120,246,396,44,21,276,6,402,393,107,282,283,53,182,535);
		List<Integer> mensalidades = Arrays.asList(22,11,33,44) ; 
		//16
		List<Integer> mensalidades2 = Arrays.asList(1,2,5,7,11) ; 
		
		
		Integer indice1 = 0;
		Integer indice2 = mensalidades2.size()-1;
		Integer somatorio=0;
		Integer valor1 = 0;
		Integer valor2 = 0;
		
		do {
			valor1 = mensalidades2.get(indice1);
			valor2 = mensalidades2.get(indice2);
			if(valor1+valor2 < valorDoPai) {
				indice1 = indice1 +1;
			}else if(valor1+valor2 > valorDoPai) {
				indice2 = indice2 -1;
			}else if(valor1+valor2 == valorDoPai) {
				System.out.println("Parcelas " + indice1 + " e " + indice2);
			}
		} while (valor1+valor2!=valorDoPai);
		
//		Integer valor1, valor2 = 0;
//		boolean encontrouValor= false;
//		for (int i = 0; i < mensalidades.size(); i++) {
//			valor1 = mensalidades.get(i);
//			for (int j = 0; j < mensalidades.size(); j++) {
//				valor2= mensalidades.get(j);
//				if((valor1 + valor2 == valorDoPai) && i!=j) {
//					System.out.println("Somatorio das Parcelas: " + i + " e " + j);
//					encontrouValor = true;
//					break;
//				}
//			}
//			if(encontrouValor) break;
//		}
	}

}
