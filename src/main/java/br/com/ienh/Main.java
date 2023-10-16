package br.com.ienh;

import java.util.Scanner;
import br.com.ienh.database.operations.PacientesOper;
import br.com.ienh.database.util.ClearConsole;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean ativo = true;
    
        try {
            while(ativo == true){
                ClearConsole.clearScreen();
                System.out.println("+-------------------------------------------------------+");
                System.out.println("|         ACESSO AOS DADOS : OPÇÕES DE CONSULTA         |");
                System.out.println("+-------------------------------------------------------+");
                System.out.println("    1: CONSULTAR PACIENTES");
                System.out.println("    2: INSERIR PACIENTE");
                System.out.println("    3: DELETAR PACIENTE");
                System.out.println("    4: ATUALIZAR PACIENTE");
                System.out.print(": ");
                int opcao = scan.nextInt();

                switch(opcao){
                    case 1:
                        PacientesOper.consultarPacientes();;
                        break;
                    case 2:
                        PacientesOper.inserirPaciente();
                        break;
                    case 3:
                        PacientesOper.deletarPaciente();
                        break;
                    case 4:
                        PacientesOper.modificarPaciente();
                        break;
                    default:
                        System.out.println("Nenhuma opção válida inserida.");
                        break;
                }
                System.out.println("\nDeseja realizar mais operações? ");
                System.out.print("true/false: ");
                ativo = scan.nextBoolean();
            }
            System.out.println("---------------------------------");
            System.out.println("------- FECHANDO PROGRAMA -------");
            System.out.println("---------------------------------");
        } catch(Exception e){
                System.out.println("|         ERRO NA EXECUÇÃO         |");
                System.out.println(e.getMessage());
        } finally {
            scan.close();
        }
    }
}