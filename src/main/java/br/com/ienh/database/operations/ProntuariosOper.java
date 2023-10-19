package br.com.ienh.database.operations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import br.com.ienh.database.DatabaseConn;

public class ProntuariosOper {
    public static void resgatarProntuario(int idPaciente) {
        try{
            Connection conn = DatabaseConn.getDatabaseConnection().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM prontuarios WHERE id_paciente = "+idPaciente);

            System.out.println("\n|                                 PRONTUÁRIO                                  |");
            System.out.println("+-----+------------+------------+---------------------------+-----------------+");
            System.out.println("|  id | Entrada    | Saída      | Diagnóstico               | Doença          |");
            System.out.println("+-----+------------+------------+---------------------------+-----------------+");

            while(rs.next()) {
                int id = rs.getInt("id");
                String dataEntradaStr = rs.getString("entrada_hospital");
                String dataSaidaStr = rs.getString("saida_hospital");
                String diagnostico = rs.getString("diagnostico");
                String doenca = rs.getString("doenca");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dataEntrada = dateFormat.parse(dataEntradaStr);
                Date dataSaida = dateFormat.parse(dataSaidaStr);

                System.out.format("| %3d | %-10tF | %-10tF | %-25s | %-15s |\n", id, dataEntrada, dataSaida, diagnostico, doenca);
            }
        } catch(Exception e){
            System.out.println("------- ERRO: resgatar prontuário -------");
            System.out.println(e.getMessage());
        }
    }

    public static void criarProntuario(int id_paciente, Statement stmt){
        Scanner scan = new Scanner(System.in);
                
        System.out.println("|          CRIAR PRONATUÁRIO         |");
        System.out.print("Entrada do paciente do hospital (yyyy-mm-dd): ");
        String dataEntrada = scan.nextLine();
        System.out.print("Saída do paciente do hospital (yyyy-mm-dd): ");
        String dataSaida = scan.nextLine();
        System.out.print("Digite o diagnóstico realizado: ");
        String diagnostico = scan.nextLine();
        System.out.print("Digite o nome da doença do paciente (se não: nulo): ");
        String doenca = scan.nextLine();

        try{
            int linhasAfetadas = stmt.executeUpdate("INSERT INTO prontuarios (id_paciente, entrada_hospital, saida_hospital, diagnostico, doenca)"+ 
                                                                    "VALUES ("+id_paciente+", '"+dataEntrada+"', '"+dataSaida+"', '"+diagnostico+"', '"+doenca+"')");
            if(linhasAfetadas == 1) {
                System.out.println("\nDados inseridos com sucesso ao prontuario.\n");
            } else {
                System.out.println("Erro ao inserir os dados.");
            }                                                                    
        } catch(Exception e){
            System.out.println("------- ERRO: inserção de prontuario -------");
            System.out.println(e.getMessage());
        }
    }

    public static void criarProntuario(){
        Scanner scan = new Scanner(System.in);
                
        System.out.println("|          CRIAR PRONATUÁRIO         |");
        System.out.print("Digite o id do paciente existente: ");
        int id_paciente = scan.nextInt();

        System.out.print("Entrada do paciente do hospital (yyyy-mm-dd): ");
        String dataEntrada = scan.next();

        System.out.print("Saída do paciente do hospital (yyyy-mm-dd): ");
        String dataSaida = scan.next();

        System.out.print("Digite o diagnóstico realizado: ");
        String diagnostico = scan.next();

        System.out.print("Digite o nome da doença do paciente (se não: nulo): ");
        String doenca = scan.next();

        try{
            Connection conn = DatabaseConn.getDatabaseConnection().getConnection();
            Statement stmt = conn.createStatement();
            int linhasAfetadas = stmt.executeUpdate("INSERT INTO prontuarios (id_paciente, entrada_hospital, saida_hospital, diagnostico, doenca)"+ 
                                                                    "VALUES ("+id_paciente+", '"+dataEntrada+"', '"+dataSaida+"', '"+diagnostico+"', '"+doenca+"')");
            if(linhasAfetadas == 1) {
                System.out.println("\nDados inseridos com sucesso ao prontuario.\n");
            } else {
                System.out.println("Erro ao inserir os dados.");
            }                                                                    
        } catch(Exception e){
            System.out.println("------- ERRO: inserção de prontuario -------");
            System.out.println(e.getMessage());
        }
    }

    public static void modificarProntuario(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Digite o id do prontuário para modificar: ");
        int id = scan.nextInt();

        try {
            Connection conn = DatabaseConn.getDatabaseConnection().getConnection();
            Statement stmt = conn.createStatement();

            System.out.println("|          MODIFICAR PRONATUÁRIO          |");
            System.out.print("Entrada do paciente do hospital (yyyy-mm-dd): ");
            String dataEntrada = scan.next();

            System.out.print("Saída do paciente do hospital (yyyy-mm-dd): ");
            String dataSaida = scan.next();

            System.out.print("Digite o diagnóstico realizado: ");
            String diagnostico = scan.next();

            System.out.print("Digite o nome da doença do paciente (se não: nulo): ");
            String doenca = scan.next();

            int linhasAfetadas = stmt.executeUpdate("UPDATE prontuarios SET entrada_hospital = '"+dataEntrada+"', saida_hospital = '"+dataSaida+"', diagnostico = '"+diagnostico+"', doenca = '"+doenca+"'"+
                                                                    " WHERE id = "+id+";");
            if(linhasAfetadas == 1){
                System.out.println("\nDados modificados com sucesso.");
            } else {
                System.out.println("\nErro ao modificar os dados.");
            }
            
        } catch (Exception e) {
            System.out.println("------- ERRO: modificar prontuário -------");
            System.out.println(e.getMessage());
        }
    }

    public static void deletarProntuario(int id_paciente, Statement stmt){
        try {
            int linhasAfetadas = stmt.executeUpdate("DELETE FROM prontuarios WHERE id_paciente = "+id_paciente);

            if(linhasAfetadas == 1){
                System.out.println("\nDados do prontuário deletados com sucesso.");
            } else {
                System.out.println("\nErro ao deletar os dados do prontuário.");
            }
        } catch (Exception e) {
            System.out.println("------- ERRO: deletar prontuário -------");
            System.out.println(e.getMessage());
        }
    }

    public static void consultarProntuarios() {
        Scanner scan = new Scanner(System.in);
        try{
            Connection conn = DatabaseConn.getDatabaseConnection().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = null;

            System.out.print("Queres ordernar a tabela? (true/false) : ");
            boolean bool = scan.nextBoolean();

            if(bool == true){
                System.out.println("Digite uma das seguintes opções de ordenamento:");
                System.out.println("    1: BUSCA PELA DATA DE ENTRADA MAIS ANTIGA");
                System.out.println("    2: BUSCA PELA DATA DE SAÍDA MAIS RECENTE");
                System.out.print(": ");
                int opcao = scan.nextInt();

                switch (opcao) {
                    case 1:
                        rs = stmt.executeQuery("SELECT * FROM prontuarios ORDER BY entrada_hospital ASC;");
                        break;
                    case 2:
                        rs = stmt.executeQuery("SELECT * FROM prontuarios ORDER BY saida_hospital DESC;"); 
                    default:
                        break;
                }
            } else {
                rs = stmt.executeQuery("SELECT * FROM prontuarios;");
            }

            System.out.println("+-----+------------+------------+---------------------------+-----------------+");
            System.out.println("|  id | Entrada    | Saída      | Diagnóstico               | Doença          |");
            System.out.println("+-----+------------+------------+---------------------------+-----------------+");

            while(rs.next()) {
                int id = rs.getInt("id");
                String dataEntradaStr = rs.getString("entrada_hospital");
                String dataSaidaStr = rs.getString("saida_hospital");
                String diagnostico = rs.getString("diagnostico");
                String doenca = rs.getString("doenca");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dataEntrada = dateFormat.parse(dataEntradaStr);
                Date dataSaida = dateFormat.parse(dataSaidaStr);

                System.out.format("| %3d | %-10tF | %-10tF | %-25s | %-15s |\n", id, dataEntrada, dataSaida, diagnostico, doenca);
            }
        } catch(Exception e){
            System.out.println("------- ERRO: resgatar os prontuários -------");
            System.out.println(e.getMessage());
        }
    }
}
