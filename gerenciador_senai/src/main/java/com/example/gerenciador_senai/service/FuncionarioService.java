package com.example.gerenciador_senai.service;

import org.springframework.stereotype.Service;

import com.example.gerenciador_senai.model.Funcionario;
import com.example.gerenciador_senai.repository.FuncionarioAutenticadoRepository;
import com.example.gerenciador_senai.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	private final FuncionarioRepository funcionarioRepository;
	private final FuncionarioAutenticadoRepository funcionarioAutenticadoRepository;

	public FuncionarioService(FuncionarioRepository funcionarioRepository,
			FuncionarioAutenticadoRepository funcionarioAutenticadoRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.funcionarioAutenticadoRepository = funcionarioAutenticadoRepository;
	}

	public Funcionario cadastrar(String nome, String nif, String senha) {
		nome = limpar(nome);
		nif = limpar(nif);
		senha = limpar(senha);

		if (nome.isBlank() || nif.isBlank() || senha.isBlank()) {
			throw new IllegalArgumentException("Preencha nome, nif e senha");
		}

		if (!funcionarioAutenticadoRepository.existsByNifAndNomeIgnoreCaseAndAtivoTrue(nif, nome)) {
			throw new IllegalArgumentException("Nif e nome não estão autorizados para cadastro");
		}

		if (funcionarioRepository.existsByNif(nif)) {
			throw new IllegalArgumentException("Já existe um funcionário cadastrado com esse nif");
		}

		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setNif(nif);
		funcionario.setSenha(senha);
		funcionario.setAtivo(true);
		return funcionarioRepository.save(funcionario);
	}

	public Funcionario autenticar(String nif, String senha) {
		nif = limpar(nif);
		senha = limpar(senha);

		Funcionario funcionario = funcionarioRepository.findByNifAndAtivoTrue(nif)
				.orElseThrow(() -> new IllegalArgumentException("Nif ou senha inválidos"));

		if (!funcionario.getSenha().equals(senha)) {
			throw new IllegalArgumentException("Nif ou senha inválidos");
		}

		return funcionario;
	}

	public long contarFuncionariosAtivos() {
		return funcionarioRepository.countByAtivoTrue();
	}

	private String limpar(String valor) {
		return valor == null ? "" : valor.trim();
	}
}
