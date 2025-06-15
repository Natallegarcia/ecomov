package com.abelix.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abelix.model.Bairro;
import com.abelix.model.Conexao;
import com.abelix.repository.BairroRepository;
import com.abelix.repository.ConexaoRepository;

@Service
public class DijkstraService {

    @Autowired
    private BairroRepository bairroRepository;

    @Autowired
    private ConexaoRepository conexaoRepository;

    /**
     * Calcula o caminho mínimo entre dois bairros usando o algoritmo de Dijkstra.
     * Retorna uma lista com os bairros no caminho.
     */
    public List<Bairro> calcularCaminho(String nomeOrigem, String nomeDestino) {
        Optional<Bairro> optOrigem = bairroRepository.findByNome(nomeOrigem);
        Optional<Bairro> optDestino = bairroRepository.findByNome(nomeDestino);

        if (optOrigem.isEmpty() || optDestino.isEmpty()) {
            System.out.println("Erro: Bairro origem ou destino não encontrado.");
            return Collections.emptyList();
        }

        Bairro origem = optOrigem.get();
        Bairro destino = optDestino.get();

        Map<Bairro, Double> distancias = new HashMap<>();
        Map<Bairro, Bairro> anteriores = new HashMap<>();
        Set<Bairro> visitados = new HashSet<>();

        List<Bairro> todosBairros = bairroRepository.findAll();
        for (Bairro b : todosBairros) {
            distancias.put(b, Double.MAX_VALUE);
        }

        distancias.put(origem, 0.0);

        PriorityQueue<Bairro> fila = new PriorityQueue<>(Comparator.comparing(distancias::get));
        fila.add(origem);

        while (!fila.isEmpty()) {
            Bairro atual = fila.poll();
            if (!visitados.add(atual)) {
                continue;
            }

            List<Conexao> conexoes = conexaoRepository.findByOrigem(atual);
            for (Conexao conexao : conexoes) {
                Bairro vizinho = conexao.getDestino();
                double novaDist = distancias.get(atual) + conexao.getDistanciaKm();

                if (novaDist < distancias.get(vizinho)) {
                    distancias.put(vizinho, novaDist);
                    anteriores.put(vizinho, atual);
                    fila.add(vizinho);
                }
            }
        }

        // Reconstrução do caminho
        List<Bairro> caminho = new LinkedList<>();
        for (Bairro atual = destino; atual != null; atual = anteriores.get(atual)) {
            caminho.add(0, atual);
        }

        if (!caminho.isEmpty() && caminho.get(0).equals(origem)) {
            return caminho;
        }

        System.out.println("Nenhum caminho encontrado entre " + nomeOrigem + " e " + nomeDestino);
        return Collections.emptyList();
    }

    /**
     * Calcula a distância total em km do caminho mínimo entre dois bairros.
     * Retorna -1 se não houver caminho possível.
     */
    public double calcularDistanciaTotal(String nomeOrigem, String nomeDestino) {
        List<Bairro> caminho = calcularCaminho(nomeOrigem, nomeDestino);
        if (caminho.isEmpty()) {
            return -1;
        }

        double total = 0.0;
        for (int i = 0; i < caminho.size() - 1; i++) {
            Bairro origem = caminho.get(i);
            Bairro destino = caminho.get(i + 1);

            List<Conexao> conexoes = conexaoRepository.findByOrigemAndDestino(origem, destino);

            if (conexoes == null || conexoes.isEmpty()) {
                return -1; // conexão quebrada
            }

            // pega a menor distância dentre as conexões entre esses bairros
            double menorDistancia = conexoes.stream()
                .mapToDouble(Conexao::getDistanciaKm)
                .min()
                .orElse(Double.MAX_VALUE);

            total += menorDistancia;
        }
        return total;
    }

    
}
