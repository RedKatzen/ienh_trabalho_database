package br.com.ienh;

import java.util.Scanner;
import br.com.ienh.database.operations.PacientesOper;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean ativo = true;
    
        try {
            while(ativo == true){
                System.out.println("+-------------------------------------------------------+");
                System.out.println("|         ACESSO AOS DADOS : OPÇÕES DE CONSULTA         |");
                System.out.println("+-------------------------------------------------------+");
                System.out.println("Opção 1: CONSULTAR PACIENTES");
                System.out.println("Opção 2: INSERIR PACIENTE");
            
                int opcao = scan.nextInt();

                switch(opcao){
                    case 1:
                    PacientesOper.consultaPacientes();;
                        break;
                    case 2:
                        PacientesOper.insercaoPaciente();
                        break;
                    default:
                        System.out.println("Nenhuma opção válida inserida.");
                        break;
                }
                System.out.println("\nDeseja realizar mais operações? ");
                System.out.print("true/false: ");
                ativo = scan.nextBoolean();
            }
            System.out.println("------- FECHANDO PROGRAMA -------");
        } catch(Exception e){
                System.out.println("|         ERRO NA EXECUÇÃO         |");
                System.out.println(e.getMessage());
        } finally {
            scan.close();
        }
    }
}