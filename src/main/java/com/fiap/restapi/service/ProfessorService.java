package com.fiap.restapi.service;

import com.fiap.restapi.model.Professor;
import com.fiap.restapi.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private ProfessorRepository repo;

    public ProfessorService(ProfessorRepository repo) {
        this.repo = repo;
    }

    public Professor adicionar(String nome, String dep, String email, String titulacao){
        validar(nome, dep, email, titulacao);
        Professor p = new Professor(null, nome.trim(), dep.trim(), email.trim(), titulacao.trim());
        return repo.adicionar(p);
    }

    public Optional<Professor> buscarPorId(Long id){
        validarID(id);
        return repo.buscarPorId(id);
    }
    public List<Professor> buscarTodos(){
        return repo.buscarTodos();
    }
    public Optional<Professor> atualizar(Long id, String nome, String dep, String email, String titulacao){
        validarID(id);
        validar(nome, dep, email, titulacao);
        Professor p = new Professor (null, nome.trim(), dep.trim(), email.trim(), titulacao.trim());
        return repo.atualizar(id,p);
    }
    public boolean deletar (Long id){
        validarID(id);
        return repo.deletar(id);
    }

    private void validar(String nome, String dep, String email, String titulacao){
        validarNome(nome);
        validarDepartamento(dep);
        validarEmail(email);
        validarTitulacao(titulacao);

    }

    // VALIDACOES INDIVIDUAIS
    private void validarNome(String nome){
        if (nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome deve ser preenchido!");
        }
    }
    private void validarDepartamento(String dep){
        if (dep == null || dep.isBlank()){
            throw new IllegalArgumentException("Departamento deve ser preenchido!");
        }
    }

    //Usando REGEX para validação do formato do email
    private final String EMAIL_REGEX ="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private void validarEmail(String email){
        if (email == null || email.isBlank()){
            throw new IllegalArgumentException("Email deve ser preenchido!");
        }
        if (!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Formato de email inválido: " + email);
        }
    }

    private void validarTitulacao(String titulacao){
        if (titulacao.length() > 80 ){
            throw new IllegalArgumentException("Titulação deve ter até 80 caracteres!");
        }
    }

    private void validarID(Long id){
        if (id == null || id <= 0){
            throw new IllegalArgumentException("ID inválido para a operação!");
        }
    }
}
