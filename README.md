# 🟢 Projeto Ecomov – Sistema de Roteamento para Coleta Seletiva

O **Ecomov** é uma aplicação baseada em microsserviços, desenvolvida em **Java com Spring Boot**, com o objetivo de planejar rotas eficientes para a coleta seletiva de resíduos na cidade de *Ecoville*. A aplicação utiliza algoritmos de grafos, comunicação REST entre serviços e persistência com banco de dados PostgreSQL.

---

## ⚙️ Funcionalidades Principais

- Cadastro e gerenciamento de bairros e conexões viárias.
- Cálculo do caminho mais curto entre bairros usando o algoritmo de **Dijkstra**.
- Cadastro de caminhões e suas capacidades.
- Criação e validação de rotas de coleta.
- Agendamento de itinerários mensais e diários.
- Autenticação de usuários.
- Visualização das rotas com distância total.

---

## 🧱 Estrutura de Classes

### 📍 Bairro
```java
- id: Long
- nome: String

###🔗 Conexao
- id: Long
- origem: Bairro
- destino: Bairro
- distanciaKm: double

###🚛 Caminhao
- id: Long
- placa: String
- capacidadeKg: double

###🗺️ Rota
- id: Long
- caminhaoId: String
- bairrosVisitados: List<String>
- distanciaTotal: double
- tiposResiduos: List<String>

###🗓️ Itinerario
- id: Long
- rotaId: Long
- dataColeta: LocalDate


###🔐 Usuario

- id: Long
- nome: String
- email: String
- senhaHash: String
- role: String

###🧠 Serviços Principais
-DijkstraService: Calcula o caminho mais curto entre bairros.

-RotaService: Monta e valida rotas com base nas conexões e caminhões.

-CSVLeitorService: Importa dados iniciais de bairros e conexões.

-AuthService: Garante autenticação e autorização de usuários.


###🛰️ Microsserviços e Camadas
-Cada módulo do sistema possui:

-Controller: expõe as rotas REST.

-Service: lógica de negócio.

-Repository: interface de persistência usando Spring Data JPA.

###📊 Swagger/OpenAPI
-ocalhost:8080/ecomov/swagger-ui.html