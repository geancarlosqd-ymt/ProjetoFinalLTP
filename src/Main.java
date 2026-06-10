import java.util.*;
public class Main {
	static Scanner leia = new Scanner(System.in);
	
	public static void main(String[] args) {	
		Estacionamento estacionamento = new Estacionamento();
    	byte opcao = -1;
    	 
    	do {
			do {
    			System.out.println("\n ***************  CADASTRO DE ALUNOS  ***************** ");
    			System.out.println(" [1] ENTRADA DE VEICULOS ");
    			System.out.println(" [2] SAIDA DE VEICULOS ");
    			System.out.println(" [3] CONSULTAR ALUNOS ");
    			System.out.println(" [4] EXCLUSÃO ");
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
    	//leia.close();
	}

}
