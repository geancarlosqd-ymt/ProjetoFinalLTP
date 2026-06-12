import java.io.*;

public class Estacionamento {

	char ativo;
	String codEst;
	String placa;
	String dataOperacao;
	char tipoOperacao;
	String modeloCor;
	String codMarca;
	String categoriaVeiculo;
	String horaEntrada;
	String horaSaida;
	float valorPago;

	public long pesquisarVeiculo(String codEstPesq) {
		// metodo para localizar um registro no arquivo em disco
		long posicaoCursorArquivo = 0;
		try {
			RandomAccessFile arqEst = new RandomAccessFile("EST.DAT", "rw");
			while (true) {
				posicaoCursorArquivo = arqEst.getFilePointer(); // posicao do inicio do registro no arquivo
				ativo = arqEst.readChar();
				codEst = arqEst.readUTF();
				placa = arqEst.readUTF();
				dataOperacao = arqEst.readUTF();
				tipoOperacao = arqEst.readChar();
				modeloCor = arqEst.readUTF();
				codMarca = arqEst.readUTF();
				categoriaVeiculo = arqEst.readUTF();
				horaEntrada = arqEst.readUTF();
				horaSaida = arqEst.readUTF();
				valorPago = arqEst.readFloat();

				if (codEstPesq.equals(codEst) && ativo == 'S') {
					arqEst.close();
					return posicaoCursorArquivo;
				}
			}
		} catch (EOFException e) {
			return -1; // registro nao foi encontrado
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
			return -1;
		}
	}

	public void salvarRegistroVeiculo() {
		// metodo para incluir um novo registro no final do arquivo em disco
		try {
			RandomAccessFile arqEst = new RandomAccessFile("EST.DAT", "rw");
			arqEst.seek(arqEst.length()); // posiciona o ponteiro no final do arquivo (EOF)
			arqEst.writeChar(ativo);
			arqEst.writeUTF(codEst);
			arqEst.writeUTF(placa);
			arqEst.writeUTF(dataOperacao);
			arqEst.writeChar(tipoOperacao);
			arqEst.writeUTF(modeloCor);
			arqEst.writeUTF(codMarca);
			arqEst.writeUTF(categoriaVeiculo);
			arqEst.writeUTF(horaEntrada);
			arqEst.writeUTF(horaSaida);
			arqEst.writeFloat(valorPago);
			arqEst.close();

			System.out.println("Dados gravados com sucesso !\n");
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	public void desativarRegistroVeiculo(long posicao) {
		// metodo para alterar o valor do campo ATIVO para N, tornando assim o registro
		// excluido
		try {
			RandomAccessFile arqEst = new RandomAccessFile("EST.DAT", "rw");
			arqEst.seek(posicao);
			arqEst.writeChar('N'); // desativar o registro antigo
			arqEst.close();
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	// *********************** INCLUSAO *****************************
	public void registrarEntrada() {

		char confirmacao;
		String ultimoCod = "000000";
		int novoCod;
		RandomAccessFile arqEst = null;

		do {
			Main.leia.nextLine();
			System.out.println("\n ***************  REGISTRO DE ENTRADA DE VEICULOS  ***************** ");

			try {
				arqEst = new RandomAccessFile("EST.DAT", "rw");
				while (true) {
					arqEst.readChar();
					ultimoCod = arqEst.readUTF();
					arqEst.readUTF();
					arqEst.readUTF();
					arqEst.readChar();
					arqEst.readUTF();
					arqEst.readUTF();
					arqEst.readUTF();
					arqEst.readUTF();
					arqEst.readUTF();
					arqEst.readFloat();
				}

			} catch (EOFException e) {
				try {
					arqEst.close();
				} catch (IOException e2) {
					System.out.println("Erro ao fechar o arquivo");
					System.exit(0);
				}
			} catch (IOException e) {
				System.out.println("Erro ao abrir o arquivo");
				System.exit(0);
			}

			novoCod = Integer.parseInt(ultimoCod) + 1;
			ativo = 'S';
			codEst = String.valueOf(novoCod);
			while (codEst.length() < 6) {
				codEst = "0" + codEst;
			}

			System.out.println("Código de Estacionamento....................: " + codEst);
			do {
				System.out.print("Digite a placa..........................: ");
				placa = Main.leia.nextLine();
				if (!placaEhValida(placa)) {
					System.out.println("Placa inválida, digite no formato XXX9999!");
				} else if (veiculoJaEstaNoPatio(placa)) {
					System.out.println("Veículo já está no estacionamento, registre a saída primeiro!");
					placa = "";
				}
			} while (!placaEhValida(placa) || placa.isEmpty());

			do {
				System.out.print("Digite a Data de Entrada(DD/MM/AAAA)..: ");
				dataOperacao = Main.leia.nextLine();
			} while (!dataEhValida(dataOperacao));

			tipoOperacao = 'E';
			System.out.println("Tipo de Operação (E entrada - S saida)......: " + tipoOperacao);

			do {
				System.out.print("Digite o modelo e cor...................: ");
				modeloCor = Main.leia.nextLine();
			} while (!validarModeloCor(modeloCor));

			do {
				System.out.print("Digite o codigo da marca................: ");
				codMarca = Main.leia.nextLine();
				if (pesquisarMarcaVeiculo(codMarca) == -1) {
					System.out.println("Marca inválida!");
				}
			} while (pesquisarMarcaVeiculo(codMarca) == -1);

			do {
				System.out.print("Digite a categoria (GI/PI/GN/PN)........: ");
				categoriaVeiculo = Main.leia.nextLine();
				if (consistirCategoria(categoriaVeiculo).equals("ERRO")) {
					System.out.println("Categoria inválida!");
				}
			} while (consistirCategoria(categoriaVeiculo).equals("ERRO"));
			System.out.println("Categoria: " + consistirCategoria(categoriaVeiculo));

			do {
				System.out.print("Digite a hora de entrada (HH:MM)........: ");
				horaEntrada = Main.leia.nextLine();
			} while (!validarHora(horaEntrada));

			horaSaida = "";
			valorPago = 0;

			do {
				System.out.print("\nConfirma a gravacao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					salvarRegistroVeiculo();
				}
			} while (confirmacao != 'S' && confirmacao != 'N');

			System.out.println("\nDeseja registrar outra entrada? (S/N): ");
			confirmacao = Main.leia.next().charAt(0);

		} while (confirmacao == 'S');
	}

	// ************************ ALTERACAO *****************************
	public void registrarSaidaVeiculo() {
		String CodEst;
		char confirmacao;
		long posicaoRegistro = 0;
		byte opcao;

		do {
			Main.leia.nextLine();
			System.out.println("\n ***************  REGISTRO DE SAIDA DE VEICULOS  ***************** ");
			System.out.print("Digite o Código Para Registrar a saída(FIM para encerrar): ");
			CodEst = Main.leia.nextLine();
			if (CodEst.equalsIgnoreCase("FIM")) {
				break;
			}
			do {

				posicaoRegistro = pesquisarVeiculo(CodEst);
				if (posicaoRegistro == -1) {
					System.out.println("Registro nao cadastrado no arquivo, digite outro valor\n");
				}
			} while (posicaoRegistro == -1);
			if (tipoOperacao == 'S') {
				System.out.println("Este veiculo já saiu do estacionamento");
			}

			System.out.println("Digite a placa..........................: " + placa);
			System.out.println("Data de Entrada.........................: " + dataOperacao);
			tipoOperacao = 'S';
			System.out.println("Tipo de Operação (E entrada - S saida): " + tipoOperacao);
			System.out.println("Digite o modelo e cor...................: " + modeloCor);
			System.out.println("Digite o codigo da marca................: " + codMarca);
			System.out.println("Categoria (GI/PI/GN/PN).................: " + categoriaVeiculo);

			do {
				do {
					System.out.println("Hora de saida(HH:MM)....................: ");
					horaSaida = Main.leia.nextLine();
				} while (!validarHora(horaSaida));
			} while (!validarHoraSaida(horaEntrada, horaSaida));

			valorPago = 5;

			System.out.println("Valor a ser pago.................: " + valorPago);

			do {
				System.out.print("\nConfirma a gravacao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					salvarRegistroVeiculo();
				}
			} while (confirmacao != 'S' && confirmacao != 'N');

		} while (!CodEst.equalsIgnoreCase("Fim"));
	}

	// ************************ EXCLUSAO *****************************
//	public void excluir() {
//		String matriculaChave;
//		char confirmacao;
//		long posicaoRegistro = 0;
//
//		do {
//			do {
//				Main.leia.nextLine();
//				System.out.println(" ***************  EXCLUSAO DE REGISTROS  ***************** ");
//				System.out.print("Digite o Código de Registro a ser excluido( FIM para encerrar ): ");
//				matriculaChave = Main.leia.nextLine();
//				if (matriculaChave.equals("FIM")) {
//					break;
//				}
//
//				posicaoRegistro = pesquisarAluno(matriculaChave);
//				if (posicaoRegistro == -1) {
//					System.out.println("Registro nao cadastrado no arquivo, digite outro valor\n");
//				}
//			} while (posicaoRegistro == -1);
//
//			if (matriculaChave.equals("FIM")) {
//				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
//				break;
//			}
//
//			System.out.println("Placa do carro........: " + placa);
//			System.out.println("Data de entrada.......: " + dataOperacao);
//			System.out.println("Modelo e cor..........: " + modeloCor);
//			System.out.println("Marca.................: " + codMarca);
//			System.out.println("Categoria.............: " + categoriaVeiculo);
//			System.out.println("Hora de entrada.......: " + horaEntrada);
//			System.out.println("Hora de saida.........: " + horaSaida);
//			System.out.println("Valor pago............: " + valorPago);
//			System.out.println();
//
//			do {
//				System.out.print("\nConfirma a exclusao deste aluno (S/N) ? ");
//				confirmacao = Main.leia.next().charAt(0);
//				if (confirmacao == 'S') {
//					desativarAluno(posicaoRegistro);
//				}
//			} while (confirmacao != 'S' && confirmacao != 'N');
//
//		} while (!matricula.equals("FIM"));
//	}

	// ************************ CONSULTA *****************************
//	public void consultar() {
//		RandomAccessFile arqEst;
//		byte opcao;
//		String matriculaChave;
//		char sexoAux;
//		long posicaoRegistro;
//
//		do {
//			do {
//				System.out.println(" ***************  CONSULTA DOS REGISTROS DE ENTRADA E SAIDA  ***************** ");
//				System.out.println(" [1] EXIBIR TODOS OS REGISTROS ");
//				System.out.println(" [2] EXIBIR VEÍCULOS QUE NÃO SAÍRAM DO ESTACIONAMENTO ");
//				System.out.println(" [3] EXIBIR REGISTROS CADASTRADOS POR DATA ");
//				System.out.println(" [0] SAIR");
//				System.out.print("\nDigite a opcao desejada: ");
//				opcao = Main.leia.nextByte();
//				if (opcao < 0 || opcao > 3) {
//					System.out.println("opcao Invalida, digite novamente.\n");
//				}
//			} while (opcao < 0 || opcao > 3);
//
//			switch (opcao) {
//			case 0:
//				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
//				break;
//
//			case 1:
//				// imprime todos os registros, opção [1] !!!
//
//				try {
//					arqEst = new RandomAccessFile("EST.DAT", "rw");
//					imprimirCabecalho();
//					while (true) {
//						ativo = arqEst.readChar();
//						codEst = arqEst.readUTF();
//						placa = arqEst.readUTF();
//						dataOperacao = arqEst.readUTF();
//						tipoOperacao = arqEst.readChar();
//						modeloCor = arqEst.readUTF();
//						codMarca = arqEst.readUTF();
//						categoriaVeiculo = arqEst.readUTF();
//						horaEntrada = arqEst.readUTF();
//						horaSaida = arqEst.readUTF();
//						valorPago = arqEst.readFloat();
//						if (ativo == 'S') {
//							imprimirAluno();
//						}
//					}
//					// arqEst.close();
//				} catch (EOFException e) {
//					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
//					Main.leia.nextLine();
//					matriculaChave = Main.leia.nextLine();
//				} catch (IOException e) {
//					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
//					System.exit(0);
//				}
//				break;

//			case 2:  
//				//	consulta de uma unica matricula
//				
//				Main.leia.nextLine();  // limpa buffer de memoria
//				System.out.print("Digite a Matriocula do Aluno: ");
//				matriculaChave = Main.leia.nextLine();
//
//				posicaoRegistro = pesquisarAluno(matriculaChave);
//				if (posicaoRegistro == -1) {
//					System.out.println("Matricula nao cadastrada no arquivo \n");
//				} else {
//					imprimirCabecalho();
//					imprimirAluno();
//					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
//					Main.leia.nextLine();
//				}
//
//				break;
//
//			case 3:  // imprime alunos do sexo desejado
//				do {
//					System.out.print("Digite o Sexo desejado (M/F): ");
//					sexoAux = Main.leia.next().charAt(0);
//					if (sexoAux != 'F' && sexoAux != 'M') {
//						System.out.println("Sexo Invalido, digite M ou F");
//					}
//				}while (sexoAux != 'F' && sexoAux != 'M');
//
//				try { 
//					arqEst = new RandomAccessFile("EST.DAT", "rw");
//					imprimirCabecalho();
//					while (true) {
//						ativo		= arqEst.readChar();
//						matricula   = arqEst.readUTF();
//						nomeAluno   = arqEst.readUTF();
//						dtNasc      = arqEst.readUTF();
//						mensalidade = arqEst.readFloat();
//						sexo        = arqEst.readChar();
//
//						if ( sexoAux == sexo && ativo == 'S') {
//							imprimirAluno();
//						}
//					}
//				} catch (EOFException e) {
//					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
//					Main.leia.nextLine();
//					matriculaChave = Main.leia.nextLine();
//				} catch (IOException e) { 
//					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
//					System.exit(0);
//				}
//
//			}
//
//		} while (opcao != 0);
//	}

	// ***************************FATURAMENTO***********************************

	public static float calcularValorAPagar() {

	}

	public void exibirRelatorioFaturamento() {
		System.out.println("Julio");
	}

	// ***************************RELATORIO***********************************

	public void imprimirCabecalho() {
		System.out.println(
				"---- PLACA ----  -- OP --  -------- MODELO E COR ----------  -- CATEGORIA --  --- DATA ---  -- HR ENTR --  -- HR SAIDA --  --- VLR PAGO --- ");
	}

	public void imprimirAluno() {

		// FORMATAR TAMANHO DOS ESPACAMENTOS DE ACORDO COM CADA COLUNA

		System.out.println(formatarString(placa, 11) + "  " + formatarString(String.valueOf(tipoOperacao), 30) + "  "
				+ formatarString(modeloCor, 13) + "  " + formatarString(categoriaVeiculo, 13) + "  "
				+ formatarString(dataOperacao, 13) + "  " + formatarString(horaEntrada, 13) + "  "
				+ formatarString(horaSaida, 13) + "  " + formatarString(String.valueOf(valorPago), 6));
	}

	public static String formatarString(String texto, int tamanho) {
		// retorna uma string com o numero de caracteres passado como parametro em
		// TAMANHO
		if (texto.length() > tamanho) {
			texto = texto.substring(0, tamanho);
		} else {
			while (texto.length() < tamanho) {
				texto = texto + " ";
			}
		}
		return texto;
	}

	// ************************ VALIDAÇÕES *****************************

	public boolean validarHora(String horario) {
		byte hora;
		byte minuto;

		if (horario.length() != 5) {
			System.out.println("Horario Inválido, digite 5 caracteres no formato HH:MM");
			return false;
		}
		if (horario.charAt(2) != ':') {
			System.out.println("Horario Inválido, digite 5 caracteres no formato HH:MM");
			return false;
		}

		try {
			hora = Byte.parseByte(horario.substring(0, 2));
			minuto = Byte.parseByte(horario.substring(3));
		} catch (NumberFormatException erro) {
			System.out.println("Horario Inválido, digite hora e minuto numéricos");
			return false;
		}

		if (hora < 0 || hora > 24 || minuto < 0 || minuto > 59) {
			System.out.println("Horario Inválido, " + "digite hora entre 0 e 24, minuto entre 0 e 59");
			return false;
		}

		return true;
	}

	public static boolean dataEhValida(String data) {
		int dia;
		int mes;
		int ano;

		if (data.length() != 10) {
			return false;
		}
		if (data.charAt(2) != '/' || data.charAt(5) != '/') {
			return false;
		}

		try {
			dia = Integer.parseInt(data.substring(0, 2));
			mes = Integer.parseInt(data.substring(3, 5));
			ano = Integer.parseInt(data.substring(6, 10));
		} catch (NumberFormatException e) {
			return false;
		}

		if (mes < 1 || mes > 12) {
			return false;
		}

		if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
			if (dia < 1 || dia > 31) {
				return false;
			}
		} else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
			if (dia < 1 || dia > 30) {
				return false;
			}
		} else {
			if ((ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0)) {
				if (dia < 1 || dia > 29) {
					return false;
				}
			} else {
				if (dia < 1 || dia > 28) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean validarModeloCor(String modeloCor) {

		if (modeloCor == "") {
			System.out.println("A digitação do modelo e a cor é obrigatório");
			return false;
		} else if (modeloCor.length() < 10) {
			System.out.println("Deve conter pelo menos 10 caracteres");
			return false;
		}

		return true;
	}

	public boolean validarHoraSaida(String horaEntrada, String horaSaida) {
		int hEntrada = Integer.parseInt(horaEntrada.substring(0, 2));
		int mEntrada = Integer.parseInt(horaEntrada.substring(3));
		int hSaida = Integer.parseInt(horaSaida.substring(0, 2));
		int mSaida = Integer.parseInt(horaSaida.substring(3));

		if (hEntrada > hSaida || hEntrada == hSaida && mEntrada >= mSaida) {
			System.out.println("Horario Final deve ser maior que o Inicial");
			return false;
		}
		while (hEntrada > hSaida || hEntrada == hSaida && mEntrada >= mSaida)
			;

		return true;
	}

	public static boolean placaEhValida(String placa) {
		if (placa.length() != 7) {
			return false;
		}

		if (!Character.isLetter(placa.charAt(0)))
			return false;
		if (!Character.isLetter(placa.charAt(1)))
			return false;
		if (!Character.isLetter(placa.charAt(2)))
			return false;

		if (Character.digit(placa.charAt(3), 10) == -1)
			return false;
		if (Character.digit(placa.charAt(4), 10) == -1)
			return false;
		if (Character.digit(placa.charAt(5), 10) == -1)
			return false;
		if (Character.digit(placa.charAt(6), 10) == -1)
			return false;

		return true;
	}

	public int pesquisarMarcaVeiculo(String codMarca) {
		for (int i = 0; i < Main.vetCodMarca.length; i++) {
			if (Main.vetCodMarca[i].equals(codMarca)) {
				return i;
			}
		}
		return -1;
	}

	public String consistirCategoria(String categoria) {
		String descricaoCategoria;

		if (categoria.equalsIgnoreCase("GI")) {
			descricaoCategoria = "Grande e Importado";
		} else if (categoria.equalsIgnoreCase("PI")) {
			descricaoCategoria = "Pequeno e Importado";
		} else if (categoria.equalsIgnoreCase("GN")) {
			descricaoCategoria = "Grande e Nacional";
		} else if (categoria.equalsIgnoreCase("PN")) {
			descricaoCategoria = "Pequeno e Nacional";
		} else {
			descricaoCategoria = "ERRO";
		}

		return descricaoCategoria;
	}

	public boolean veiculoJaEstaNoPatio(String placaPesq) {
		try {
			RandomAccessFile arqEst = new RandomAccessFile("EST.DAT", "rw");
			while (true) {
				ativo = arqEst.readChar();
				arqEst.readUTF(); // codEst
				placa = arqEst.readUTF();
				arqEst.readUTF(); // dataOperacao
				tipoOperacao = arqEst.readChar();
				arqEst.readUTF(); // modeloCor
				arqEst.readUTF(); // codMarca
				arqEst.readUTF(); // categoriaVeiculo
				arqEst.readUTF(); // horaEntrada
				arqEst.readUTF(); // horaSaida
				arqEst.readFloat(); // valorPago

				if (placa.equals(placaPesq) && tipoOperacao == 'E' && ativo == 'S') {
					arqEst.close();
					return true;
				}
			}
		} catch (EOFException e) {
			return false;
		} catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo");
			System.exit(0);
			return false;

		}
	}

}
