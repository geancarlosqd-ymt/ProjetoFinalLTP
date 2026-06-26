import java.util.*;
public class Main {
	static Scanner leia = new Scanner(System.in);
	
	static String vetCodMarca[] = { "BM", "VW", "FO", "MB", "CV", "FI", "AU", "TO", "HO", "HY" };
	static String vetDescricaoMarca[] = { "BMW", "Volkswagen", "Ford", "Mercedes Benz", "Chevrolet", "Fiat", "Audi", "Toyota", "Honda", "Hyundai" };
	
	public static void main(String[] args) {	
		Estacionamento estacionamento = new Estacionamento();
    	byte opcao = -1;
    	 
    	do {
			do {
    			System.out.println("\n ***************  CADASTRO DE ESTACIONAMENTO  ***************** ");
    			System.out.println(" [1] ENTRADA DE VEICULOS ");
    			System.out.println(" [2] SAIDA DE VEICULOS ");
    			System.out.println(" [3] CONSULTAR VEICULOS ");
    			System.out.println(" [4] EXCLUSÃO DE VEICULOS ");
    			System.out.println(" [5] RELATÓRIO DE FATURAMENTO ");
    			System.out.println(" [0] SAIR");
    			System.out.print("\nDigite a opcao desejada: ");
    			opcao = leia.nextByte();
    			if (opcao < 0 || opcao > 5) {
    				System.out.println("opcao Invalida, digite novamente.\n");
    			}
    		}while (opcao < 0 || opcao > 5);
			
			switch (opcao) {
				case 0:
					System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
					break;
				case 1: 
					estacionamento.registrarEntradaVeiculo(); 
					break;
				case 2:
					estacionamento.registrarSaidaVeiculo();
					break;
				case 3: 
					estacionamento.consultar();
					break;
				case 4: 
					estacionamento.excluir();
					break;
				case 5:
					estacionamento.exibirRelatorioFaturamento();
					break;
			}
    	} while ( opcao != 0 );
    	leia.close();
	}

}




