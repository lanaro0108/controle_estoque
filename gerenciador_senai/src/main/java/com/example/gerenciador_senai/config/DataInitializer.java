package com.example.gerenciador_senai.config;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.gerenciador_senai.model.AtivoPatrimonial;
import com.example.gerenciador_senai.model.Categoria;
import com.example.gerenciador_senai.model.Funcionario;
import com.example.gerenciador_senai.model.FuncionarioAutenticado;
import com.example.gerenciador_senai.model.Material;
import com.example.gerenciador_senai.model.StatusAtivo;
import com.example.gerenciador_senai.model.TipoMovimentacao;
import com.example.gerenciador_senai.repository.CategoriaRepository;
import com.example.gerenciador_senai.repository.FuncionarioAutenticadoRepository;
import com.example.gerenciador_senai.repository.FuncionarioRepository;
import com.example.gerenciador_senai.repository.MaterialRepository;
import com.example.gerenciador_senai.repository.MovimentacaoEstoqueRepository;
import com.example.gerenciador_senai.service.AtivoPatrimonialService;
import com.example.gerenciador_senai.service.MovimentacaoEstoqueService;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(FuncionarioAutenticadoRepository funcionarioAutenticadoRepository,
            FuncionarioRepository funcionarioRepository,
            CategoriaRepository categoriaRepository,
            MaterialRepository materialRepository,
            MovimentacaoEstoqueRepository movimentacaoEstoqueRepository,
            MovimentacaoEstoqueService movimentacaoEstoqueService,
            AtivoPatrimonialService ativoPatrimonialService) {
        return args -> {

            salvarFuncionarioAutorizado(funcionarioAutenticadoRepository, "Administrador Senai", "1001");
            salvarFuncionarioAutorizado(funcionarioAutenticadoRepository, "Tecnico Laboratorio", "1002");

            if (funcionarioRepository.count() == 0) {
                Funcionario funcionario = new Funcionario();
                funcionario.setNome("Administrador Senai");
                funcionario.setNif("1001");
                funcionario.setSenha("1234");
                funcionario.setAtivo(true);
                funcionarioRepository.save(funcionario);
            }

            if (categoriaRepository.count() == 0) {
                Categoria informatica = new Categoria();
                informatica.setNome("Informatica");
                informatica.setDescricao("Equipamentos e Hardware para uso em sala");
                categoriaRepository.save(informatica);

                Categoria laboratorio = new Categoria();
                laboratorio.setNome("Laboratorio");
                laboratorio.setDescricao("Materiais de uso técnico");
                categoriaRepository.save(laboratorio);

                Categoria limpeza = new Categoria();
                limpeza.setNome("Limpeza");
                limpeza.setDescricao("Materiais de apoio e higiene");
                categoriaRepository.save(limpeza);
            }

            if (materialRepository.count() == 0) {
                Categoria informatica = categoriaRepository.findAllByOrderByNomeAsc().stream()
                        .filter(categoria -> categoria.getNome().equalsIgnoreCase("Informatica"))
                        .findFirst()
                        .orElseThrow();
                Categoria laboratorio = categoriaRepository.findAllByOrderByNomeAsc().stream()
                        .filter(categoria -> categoria.getNome().equalsIgnoreCase("Laboratorio"))
                        .findFirst()
                        .orElseThrow();

                Material notebook = new Material();
                notebook.setNome("Notebook");
                notebook.setDescricao("Notebook para uso em sala");
                notebook.setUnidadeMedida("Unidade");
                notebook.setQuantidadeMinima(3);
                notebook.setQuantidadeEmEstoque(0);
                notebook.setCategoria(informatica);
                materialRepository.save(notebook);

                Material multimetro = new Material();
                multimetro.setNome("Multimetro");
                multimetro.setDescricao("Equipamento de medição");
                multimetro.setUnidadeMedida("Unidade");
                multimetro.setQuantidadeMinima(2);
                multimetro.setQuantidadeEmEstoque(0);
                multimetro.setCategoria(laboratorio);
                materialRepository.save(multimetro);
            }

            if (movimentacaoEstoqueRepository.count() == 0) {
                Material notebook = materialRepository.findAllByOrderByNomeAsc().stream()
                        .filter(material -> material.getNome().equalsIgnoreCase("Notebook"))
                        .findFirst()
                        .orElseThrow();
                Material multimetro = materialRepository.findAllByOrderByNomeAsc().stream()
                        .filter(material -> material.getNome().equalsIgnoreCase("Multimetro"))
                        .findFirst()
                        .orElseThrow();

                movimentacaoEstoqueService.salvar(null, notebook.getId(), TipoMovimentacao.ENTRADA, 10,
                        "Entrada inicial do sistema", "Seed inicial");
                movimentacaoEstoqueService.salvar(null, multimetro.getId(), TipoMovimentacao.ENTRADA, 5,
                        "Entrada inicial do sistema", "Seed inicial");
            }

            if (ativoPatrimonialService.contarAtivos() == 0) {
                AtivoPatrimonial projetor = new AtivoPatrimonial();
                projetor.setCodigoPatrimonio("PAT-001");
                projetor.setNome("Projetor Epson");
                projetor.setDescricao("Projetor da sala multimídia");
                projetor.setLocalizacao("Sala Multimídia");
                projetor.setResponsavel("Coordenação");
                projetor.setStatusAtivo(StatusAtivo.EM_USO);
                projetor.setDataAquisicao(LocalDate.of(2024, 2, 10));
                projetor.setValorAquisicao(new BigDecimal("4200.00"));
                ativoPatrimonialService.salvar(projetor);

                AtivoPatrimonial bancada = new AtivoPatrimonial();
                bancada.setCodigoPatrimonio("PAT-002");
                bancada.setNome("Bancada de Eletrônica");
                bancada.setDescricao("Bancada principal do laboratório");
                bancada.setLocalizacao("Laboratório 2");
                bancada.setResponsavel("Técnico Laboratório");
                bancada.setStatusAtivo(StatusAtivo.DISPONIVEL);
                bancada.setDataAquisicao(LocalDate.of(2023, 8, 15));
                bancada.setValorAquisicao(new BigDecimal("3500.00"));
                ativoPatrimonialService.salvar(bancada);
            }
        };
    }

    private void salvarFuncionarioAutorizado(FuncionarioAutenticadoRepository funcionarioAutenticadoRepository,
            String nome, String nif) {
        if (funcionarioAutenticadoRepository.findByNifAndAtivoTrue(nif).isPresent()) {
            return;
        }

        FuncionarioAutenticado funcionarioAutenticado = new FuncionarioAutenticado();
        funcionarioAutenticado.setNome(nome);
        funcionarioAutenticado.setNif(nif);
        funcionarioAutenticado.setAtivo(true);
        funcionarioAutenticadoRepository.save(funcionarioAutenticado);
    }
}