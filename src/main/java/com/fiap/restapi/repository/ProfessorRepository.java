package com.fiap.restapi.repository;

import com.fiap.restapi.config.ConnectionFactory;
import com.fiap.restapi.model.Professor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProfessorRepository {

    public Professor adicionar(Professor p) {
        final String sql = "INSERT INTO PROFESSOR (NOME,DEPARTAMENTO,EMAIL,TITULACAO) VALUES (?,?,?,?)";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement st = conn.prepareStatement(sql, new String[] { "ID" }))   {

            st.setString(1, p.getNome());
            st.setString(2, p.getDepartamento());
            st.setString(3, p.getNome());
            st.setString(4,p.getTitulacao());
            st.executeUpdate();

            try(ResultSet rs = st.getGeneratedKeys()){
                if(rs.next()){
                    p.setId(rs.getLong(1));
                }else{
                    try(PreparedStatement st2 = conn.prepareStatement(
                            "SELECT MAX(ID) FROM PROFESSOR"
                    )){
                        try(ResultSet rs2 = st2.executeQuery()){
                            if(rs2.next()) p.setId(rs2.getLong(1));
                        }
                    }
                }
            }
            return p;
        }catch(SQLException e){
            throw new RuntimeException("Erro ao inserir o professor" + e.getMessage());
        }
    }

    Optional<Professor> buscarPorId(Long id) {
        final String sql = "SELECT ID,NOME,DEPARTAMENTO,EMAIL,TITULACAO FROM PROFESSOR WHERE ID = ?";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement st = conn.prepareStatement(sql)){

            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if(rs.next()){
                    return Optional.of(new Professor(rs.getLong("ID"),
                            rs.getString("NOME"),
                            rs.getString("DEPARTAMENTO"),
                            rs.getString("EMAIL"),
                            rs.getString("TITULACAO")
                    ));

                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professor por Id "+e.getMessage());
        }
    }

    public List<Professor> buscarTodos(){
        final String sql = "SELECT ID, NOME,DEPARTAMENTO FROM PROFESSOR ORDER BY ID";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement st = conn.prepareStatement(sql)){
            ResultSet rs = st.executeQuery();
            List<Professor> lista = new ArrayList<>();
            while(rs.next()){
                lista.add(new Professor(rs.getLong("ID"),
                        rs.getString("NOME"),
                        rs.getString("DEPARTAMENTO")));
            }
            return lista;

        }catch (SQLException e){
            throw new RuntimeException("Erro ao buscar todos os professores"+e.getMessage());
        }
    }

    Optional<Professor> atualizar(Long id, Professor p) {
        final String sql = "UPDATE PROFESSOR SET NOME = ?, DEPARTAMENTO = ?, EMAIL = ?, TITULACAO = ? WHERE ID = ?";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement st = conn.prepareStatement(sql)){

            st.setString(1, p.getNome());
            st.setString(2, p.getDepartamento());
            st.setString(3,p.getEmail());
            st.setString(4,p.getTitulacao());
            st.setLong(5,id);

            int linhas = st.executeUpdate();
            if(linhas == 0) return Optional.empty();

            return buscarPorId(id);
        }catch (SQLException e){
            throw new RuntimeException("Erro ao atualizar o professor"+e.getMessage());
        }
    }

    boolean deletar(Long id){
        final String sql = "DELETE FROM PROFESSOR WHERE ID = ?";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement st = conn.prepareStatement(sql)){
            st.setLong(1,id);
            return st.executeUpdate() > 0;
        }catch (SQLException e){
            throw new RuntimeException("Erro ao deletar o professor"+e.getMessage());
        }
    }
}
