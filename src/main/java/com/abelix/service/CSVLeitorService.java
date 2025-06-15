package com.abelix.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.abelix.model.Bairro;
import com.abelix.model.Conexao;
import com.abelix.model.PontoColeta;
import com.abelix.repository.BairroRepository;
import com.abelix.repository.ConexaoRepository;
import com.abelix.repository.PontoColetaRepository;

@Component
public class CSVLeitorService {

    @Autowired
    private BairroRepository bairroRepository;

    @Autowired
    private ConexaoRepository conexaoRepository;

    @Autowired
    private PontoColetaRepository pontoColetaRepository;

    public CSVLeitorService() {
        System.out.println("CSVLeitorService criado!");
    }

    public void carregarBairros() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource("data/bairros.csv").getInputStream(), StandardCharsets.UTF_8))) {

            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String nome = linha.trim();
                if (nome.isEmpty()) continue;

                Optional<Bairro> existente = bairroRepository.findByNome(nome);
                if (existente.isEmpty()) {
                    Bairro bairro = new Bairro();
                    bairro.setNome(nome);
                    bairroRepository.save(bairro);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregarConexoes() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource("data/ruas_conexoes.csv").getInputStream(), StandardCharsets.UTF_8))) {

            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] partes = linha.split(",");
                if (partes.length < 4) continue;

                Long origemId = Long.parseLong(partes[1].trim());
                Long destinoId = Long.parseLong(partes[2].trim());
                Double distancia = Double.parseDouble(partes[3].trim());

                Optional<Bairro> origemOpt = bairroRepository.findById(origemId);
                Optional<Bairro> destinoOpt = bairroRepository.findById(destinoId);

                if (origemOpt.isPresent() && destinoOpt.isPresent()) {
                    Conexao conexao = new Conexao(origemOpt.get(), destinoOpt.get(), distancia);
                    conexaoRepository.save(conexao);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregarPontosColeta() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource("data/pontos_coleta.csv").getInputStream(), StandardCharsets.UTF_8))) {

            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] partes = parseLinhaCSV(linha);
                if (partes.length < 9) continue;

                Long bairroId = Long.parseLong(partes[1].trim());
                Optional<Bairro> bairroOpt = bairroRepository.findById(bairroId);

                if (bairroOpt.isPresent()) {
                    Bairro bairro = bairroOpt.get();

                    PontoColeta ponto = new PontoColeta();
                    ponto.setBairro(bairro);
                    ponto.setNome(partes[2].trim());
                    ponto.setResponsavel(partes[3].trim());
                    ponto.setTelefoneResponsavel(partes[4].trim());
                    ponto.setEmailResponsavel(partes[5].trim());
                    ponto.setEndereco(partes[6].trim());
                    ponto.setHorarioFuncionamento(partes[7].trim());

                    //  CSV separa resíduos por vírgula 
                    String[] residuos = partes[8].split(",");
                    List<String> tiposResiduo = new ArrayList<>();
                    for (String r : residuos) {
                        tiposResiduo.add(r.trim());
                    }
                    ponto.setTiposResiduoAceitos(tiposResiduo);

                    pontoColetaRepository.save(ponto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //tratar campos CSV com vírgulas entre aspas
    private String[] parseLinhaCSV(String linha) {
        List<String> valores = new ArrayList<>();
        StringBuilder valorAtual = new StringBuilder();
        boolean dentroAspas = false;

        for (char c : linha.toCharArray()) {
            if (c == '"') {
                dentroAspas = !dentroAspas;
            } else if (c == ',' && !dentroAspas) {
                valores.add(valorAtual.toString());
                valorAtual.setLength(0);
            } else {
                valorAtual.append(c);
            }
        }
        valores.add(valorAtual.toString());
        return valores.toArray(new String[0]);
    }
}
