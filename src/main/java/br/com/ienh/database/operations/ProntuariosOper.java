package br.com.ienh.database.operations;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ProntuariosOper {
    public static void resgatarProntuario(String nome, Statement stmt) {
        try{
            ResultSet rs = stmt.executeQuery("SELECT * FROM prontuarios WHERE nome_paciente = "+nome);
            System.out.println("+-----+----------------+--------------+----------------+-------------------------+");
            System.out.println("| id  | entrada        | saida        | doença         | diagnostico             |");
            System.out.println("+-----+----------------+--------------+----------------+-------------------------+");

            while(rs.next()) {
                int id = rs.getInt("id");
                Date entrada = rs.getDate("entrada_hospital");
                Date saida = rs.getDate("saida_hospital");
                String doenca = rs.getString("doenca");
                String diagnostico = rs.getString("diagnostico");
                System.out.println("| "+id+"  | "+entrada+"        | "+saida+"        | "+doenca+"         | "+diagnostico+"              ");
            }
            System.out.println("+-----+--------------+-------+----------------+-------+");
        } catch(Exception e){
            System.out.println("------- ERRO: resgatar prontuário -------");
            System.out.println(e.getMessage());
        }
    }

    public static void criarProntuario(String nomePaciente, Statement stmt){
        Scanner scan = new Scanner(System.in);
                
        System.out.println("|        PRONATUÁRIO : PACIENTE "+nomePaciente+"      |");
        System.out.print("Entrada do paciente do hospital (dd MM yyyy): ");
        String dataEntrada = scan.nextLine();
        System.out.print("Saída do paciente do hospital (dd/MM/yyyy): ");
        String dataSaida = scan.nextLine();
        System.out.print("Digite o diagnóstico realizado: ");
        String diagnostico = scan.nextLine();
        System.out.print("Digite o nome da doença do paciente (se não: nulo): ");
        String doenca = scan.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
        Date dateEntrada = null;
        Date dateSaida = null;
        try{
            dateEntrada = dateFormat.parse(dataEntrada);
            dateSaida = dateFormat.parse(dataSaida);
        } catch(Exception e){
            
        }

        try{
            int linhasAfetadas = stmt.executeUpdate("INSERT INTO prontuarios (nome_paciente, entrada_hospital, saida_hospital, diagnostico, doenca)"+ 
                                                                    "VALUES ('"+nomePaciente+"', '"+dateEntrada+" 00:00:00', '"+dateSaida+" 00:00:00', '"+diagnostico+"', '"+doenca+"')");
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
}
