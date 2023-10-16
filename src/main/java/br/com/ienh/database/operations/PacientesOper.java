package br.com.ienh.database.operations;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;
import br.com.ienh.database.DatabaseConn;

public class PacientesOper {
    public static void consultarPacientes() {
        try{
            Connection conn = DatabaseConn.getDatabaseConnection().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pacientes");

            System.out.println("+-----+--------------+-------+----------------+-------+");
            System.out.println("|  id | nome         | idade | cpf            | sexo  |");
            System.out.println("+-----+--------------+-------+----------------+-------+");

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String cpf = rs.getString("cpf");
                String sexo = rs.getString("sexo");

                System.out.println("|  "+id+"  | "+nome+"        | "+idade+"    | "+cpf+"     |   "+sexo+"   |");
            }
            System.out.println("+-----+-----------+-------+----------------+-------+");
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
                System.out.println("Dados inseridos com sucesso.");
            } else {
                System.out.println("Erro ao inserir os dados.");
            }
        } catch(Exception e){
            System.out.println("------- ERRO: inserção de paciente -------");
            System.out.println(e.getMessage());
        } finally {
            scan.close();
        }
    }

    public static void deletarPaciente(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Informe o id do paciente que deseja deletar: ");
        int id = scan.nextInt();

        try{
            Connection conn = DatabaseConn.getDatabaseConnection().getConnection();
            Statement stmt = conn.createStatement();

            int linhasAfetadas = stmt.executeUpdate("DELETE FROM pacientes WHERE id = "+id);

            if(linhasAfetadas == 1){
                System.out.println("Dados deletados com sucesso.");
            } else {
                System.out.println("Erro ao deletar os dados.");
            }
        } catch(Exception e){
            System.out.println("------- ERRO: deletar paciente -------");
            System.out.println(e.getMessage());
        } finally{
            scan.close();
        }
    }

    public static void modificarPaciente(){
        Scanner scan = new Scanner(System.in);
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
                System.out.println("Dados modificados com sucesso.");
            } else {
                System.out.println("Erro ao modificar os dados.");
            }
            
        } catch (Exception e) {
            System.out.println("------- ERRO: modificar paciente -------");
            System.out.println(e.getMessage());
        }finally{
            scan.close();
        }
    }
}
