package br.com.ienh.database.operations;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;
import br.com.ienh.database.DatabaseConn;

public class PacientesOper {
    public static void consultarPacientes() {
        Scanner scan = new Scanner(System.in);
        try{
            Connection conn = DatabaseConn.getDatabaseConnection().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = null;

            System.out.print("Queres ordernar a tabela? (true/false) : ");
            boolean bool = scan.nextBoolean();

            if(bool == true){
                System.out.println("Digite uma das seguintes opções de ordenamento:");
                System.out.println("    1: BUSCA PELA MENOR IDADE");
                System.out.println("    2: BUSCA PELA MAIOR IDADE");
                System.out.print(": ");
                int opcao = scan.nextInt();

                switch (opcao) {
                    case 1:
                        rs = stmt.executeQuery("SELECT * FROM pacientes ORDER BY idade ASC;");
                        break;
                    case 2:
                        rs = stmt.executeQuery("SELECT * FROM pacientes ORDER BY idade DESC;"); 
                    default:
                        break;
                }
            } else {
                rs = stmt.executeQuery("SELECT * FROM pacientes;");
            }
            
            System.out.println("+-----+-----------------+-------+---------------+------+");
            System.out.println("|  id | nome            | idade | cpf           | sexo |");
            System.out.println("+-----+-----------------+-------+---------------+------+");

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String cpf = rs.getString("cpf");
                String sexo = rs.getString("sexo");

                System.out.format("| %3d | %-15s | %-5d | %-13s | %-4s |\n", id, nome, idade, cpf, sexo);
            }   
            System.out.println("+-----+-----------------+-------+---------------+------+");
        } catch(Exception e){
            System.out.println("------- ERRO: consulta de pacientes -------");
            System.out.println(e.getMessage());
        }
    }

    public static void inserirPaciente() {
        Scanner scan = new Scanner(System.in);
        try{
            Connection conn = DatabaseConn.getDatabaseConnection().getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM pacientes");
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("id");
            }

            System.out.println("|          CRIAR PACIENTE         |");
            System.out.println("Infome os seguintes dados do paciente:");
            System.out.print("Nome: ");
            String nome = scan.next();

            System.out.print("Idade (apenas números): ");
            int idade = scan.nextInt();

            System.out.print("CPF (sem hífen): ");
            String cpf = scan.next();

            System.out.print("Sexo (M/F): ");
            String sexo = scan.next();

            int linhasAfetadas = stmt.executeUpdate("INSERT INTO pacientes (nome, idade, cpf, sexo) VALUES ('"+nome+"', "+idade+", '"+cpf+"', '"+sexo+"');");
            if(linhasAfetadas == 1) {
                System.out.println("\nDados inseridos com sucesso.\n");
                ProntuariosOper.criarProntuario(id, stmt);
            } else {
                System.out.println("\nErro ao inserir os dados.");
            }
        } catch(Exception e){
            System.out.println("------- ERRO: inserção de paciente -------");
            System.out.println(e.getMessage());
        } 
    }

    public static void deletarPaciente(){
        Scanner scan = new Scanner(System.in);
        System.out.println("|          DELETAR PACIENTE         |");
        System.out.print("Informe o id do paciente que deseja deletar: ");
        int id = scan.nextInt();

        try{
            Connection conn = DatabaseConn.getDatabaseConnection().getConnection();
            Statement stmt = conn.createStatement();

            int linhasAfetadas = stmt.executeUpdate("DELETE FROM pacientes WHERE id = "+id);

            if(linhasAfetadas == 1){
                System.out.println("\nDados deletados com sucesso.");
                ProntuariosOper.deletarProntuario(id, stmt);
            } else {
                System.out.println("\nErro ao deletar os dados.");
            }
        } catch(Exception e){
            System.out.println("------- ERRO: deletar paciente -------");
            System.out.println(e.getMessage());
        } 
    }

    public static void modificarPaciente(){
        Scanner scan = new Scanner(System.in);
        System.out.println("|          MODIFICAR PACIENTE         |");
        System.out.print("Digite o id do paciente que deseja modificar: ");
        int id = scan.nextInt();

        try {
            Connection conn = DatabaseConn.getDatabaseConnection().getConnection();
            Statement stmt = conn.createStatement();

            System.out.println("Infome os dados novos do paciente:");
            System.out.print("Nome: ");
            String nome = scan.next();

            System.out.print("Idade (apenas números): ");
            int idade = scan.nextInt();

            System.out.print("CPF (sem hífen): ");
            String cpf = scan.next();

            int linhasAfetadas = stmt.executeUpdate("UPDATE pacientes SET nome = '"+nome+"', idade = "+idade+", cpf = '"+cpf+"' WHERE id = "+id+";");
            if(linhasAfetadas == 1){
                System.out.println("\nDados modificados com sucesso.");
            } else {
                System.out.println("\nErro ao modificar os dados.");
            }
            
        } catch (Exception e) {
            System.out.println("------- ERRO: modificar paciente -------");
            System.out.println(e.getMessage());
        }
    }

    public static void consultarPaciente(){
        Scanner scan = new Scanner(System.in);
        System.out.println("|          CONSULTAR PACIENTE         |");
        System.out.print("Digite o id do paciente que deseja consultar: ");
        int id = scan.nextInt();

        try {
            Connection conn = DatabaseConn.getDatabaseConnection().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pacientes WHERE id = "+id);

            System.out.println("+-----+-----------------+-------+---------------+------+");
            System.out.println("|  id | nome            | idade | cpf           | sexo |");
            System.out.println("+-----+-----------------+-------+---------------+------+");

            while (rs.next()) {
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String cpf = rs.getString("cpf");
                String sexo = rs.getString("sexo");
                System.out.format("| %3d | %-15s | %-5d | %-13s | %-4s |\n", id, nome, idade, cpf, sexo);
            }
            ProntuariosOper.resgatarProntuario(id);
        } catch(Exception e){
            System.out.println("------- ERRO: consultar paciente -------");
            System.out.println(e.getMessage());
        } 
    }
}
