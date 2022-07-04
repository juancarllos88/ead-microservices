package br.com.ead.authuser;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Cobranca {
	
	
//	public static void main(String[] args) {
//		//Integer valorDoPai = 65;
//		//Integer valorDoPai = 133;
//		Integer valorDoPai = 7;
//		//List<Integer> mensalidades = Arrays.asList(222,89,411,120,246,396,44,21,276,6,402,393,107,282,283,53,182,535);
//		List<Integer> mensalidades = Arrays.asList(22,11,33,44) ; 
//		//16
//		List<Integer> mensalidades2 = Arrays.asList(1,2,5,7,11) ; 
//		
//		
//		Integer indice1 = 0;
//		Integer indice2 = mensalidades2.size()-1;
//		Integer somatorio=0;
//		Integer valor1 = 0;
//		Integer valor2 = 0;
//		
//		do {
//			valor1 = mensalidades2.get(indice1);
//			valor2 = mensalidades2.get(indice2);
//			if(valor1+valor2 < valorDoPai) {
//				indice1 = indice1 +1;
//			}else if(valor1+valor2 > valorDoPai) {
//				indice2 = indice2 -1;
//			}else if(valor1+valor2 == valorDoPai) {
//				System.out.println("Parcelas " + indice1 + " e " + indice2);
//			}
//		} while (valor1+valor2!=valorDoPai);
//		
////		Integer valor1, valor2 = 0;
////		boolean encontrouValor= false;
////		for (int i = 0; i < mensalidades.size(); i++) {
////			valor1 = mensalidades.get(i);
////			for (int j = 0; j < mensalidades.size(); j++) {
////				valor2= mensalidades.get(j);
////				if((valor1 + valor2 == valorDoPai) && i!=j) {
////					System.out.println("Somatorio das Parcelas: " + i + " e " + j);
////					encontrouValor = true;
////					break;
////				}
////			}
////			if(encontrouValor) break;
////		}
//	}
	
	public static Set<LocalDate> getFixedHolidays(int year) {
		Set<LocalDate> dates = new HashSet<>();
		dates.add(LocalDate.of(year, 9, 7));
		dates.add(LocalDate.of(year, 12, 25));

		return dates;
	}

	public static LocalDate getNextUsefulDay(int day) {
		LocalDate data = LocalDate.of(2022, 9, 6);
		Set<LocalDate> feriados = getFixedHolidays(2022);
		for (int i = 0; i < day; i++) {
			data = data.plusDays(1);
			if (data.getDayOfWeek() == DayOfWeek.SATURDAY 
					|| data.getDayOfWeek() == DayOfWeek.SUNDAY
					|| feriados.contains(data)) {

				day++;
			}

		}
		return data;
	}

	public static void main(String[] args) {
		int minDay = 3;
		int maxDay = 7;
		System.out.println(getNextUsefulDay(minDay).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		System.out.println(getNextUsefulDay(maxDay).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	}

}
