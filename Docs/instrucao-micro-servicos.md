# Guia de Migração: Monolito para Microserviços

Este documento serve como um guia e plano de ação para a decomposição da aplicação `@Monolitico` em uma arquitetura de microserviços.

## 1. Objetivo do Projeto

O objetivo principal é refatorar a aplicação monolítica de gerenciamento de usuários, autenticação, empresas e permissões em uma arquitetura distribuída baseada em microserviços. Isso visa melhorar a escalabilidade, manutenibilidade, resiliência e permitir a evolução independente de cada contexto de negócio.

## 2. Arquitetura Alvo

A nova arquitetura será composta pelos seguintes componentes, preferencialmente orquestrados via `docker-compose`:

-   **API Gateway (`Nginx`):** Ponto de entrada único para todas as requisições externas. Responsável por roteamento, SSL, e possivelmente, cache e *rate limiting*.
-   **Service Discovery (`Netflix Eureka`):** Permite que os serviços se registrem e se encontrem dinamicamente na rede.
-   **Config Server (`Spring Cloud Config`):** Centraliza as configurações de todos os microserviços em um repositório Git externo.
-   **Microserviços (`Java Spring Boot`):** Aplicações independentes, cada uma com sua responsabilidade e banco de dados.
-   **Mensageria (`RabbitMQ`):** Para comunicação assíncrona e orientada a eventos entre os serviços.
-   **Banco de Dados (`PostgreSQL`):** Cada microserviço terá seu próprio schema ou instância de banco de dados (padrão *Database per Service*).
-   **Observabilidade:**
    -   **Métricas:** `Prometheus` & `Grafana` para coleta e visualização de métricas de performance.
    -   **Tracing Distribuído:** `Jaeger` para rastrear requisições através de múltiplos serviços.
    -   **Logs Centralizados:** Uma stack como `ELK` (Elasticsearch, Logstash, Kibana) ou `Loki` para agregar logs.

## 2.1. Estratégia de Comunicação e Segurança

A comunicação entre os serviços seguirá dois padrões principais:

-   **Comunicação Assíncrona (Events):**
    -   **Tecnologia:** `RabbitMQ`.
    -   **Uso:** Para notificar outros serviços sobre mudanças de estado (ex: `company.created`, `user.updated`). Isso desacopla os serviços, aumentando a resiliência. Um serviço não precisa estar online para receber a notificação posteriormente.

-   **Comunicação Síncrona (Request/Response):**
    -   **Tecnologia:** `OpenFeign` (alternativa declarativa e mais simples ao `RestTemplate`/`WebClient` do Spring).
    -   **Uso:** Para consultas diretas onde uma resposta imediata é necessária. Exemplo: O API Gateway consultando o `micro-authorization` para verificar uma permissão.
    -   **Segurança Serviço-a-Serviço:** Para garantir que apenas serviços autorizados possam se comunicar, o token JWT do usuário final será propagado entre as chamadas internas. O `OpenFeign` pode ser configurado com um `RequestInterceptor` para adicionar automaticamente o cabeçalho `Authorization` em cada requisição.


## 3. Decomposição em Microserviços

O monolito será dividido nos seguintes serviços, cada um em sua própria pasta no formato `micro-<contexto>`:

| Pasta do Projeto      | Microserviço              | Responsabilidades Principais                               | Entidades Gerenciadas |
| :-------------------- | :------------------------ | :--------------------------------------------------------- | :-------------------- |
| `micro-auth`          | **Serviço de Autenticação**   | Gerenciar identidade (login, senhas), emitir e validar tokens JWT. | `User` (credenciais), `Role` |
| `micro-user`          | **Serviço de Usuário**        | Gerenciar dados de perfil do usuário e sua abrangência de acesso. | `User` (perfil), `Coverage` |
| `micro-company`       | **Serviço de Empresas**       | Gerenciar o cadastro de empresas e suas respectivas plantas. | `Company`, `Plant`    |
| `micro-authorization` | **Serviço de Autorização**    | Gerenciar o controle fino de acesso (recursos e permissões). | `Resource`, `Permission` |

## 4. Plano de Implementação Passo a Passo

### Fase 0: Configuração da Infraestrutura Base

1.  **Criar `docker-compose.yml`:** Na raiz do novo repositório, criar um arquivo para orquestrar todos os serviços de infraestrutura (`PostgreSQL`, `RabbitMQ`, `Eureka Server`, `Config Server`, etc.).
2.  **Criar Repositório de Configuração:** Criar um repositório Git separado para armazenar os arquivos de configuração (`.yml`) de todos os microserviços.

### Fase 1: Desenvolvimento dos Microserviços

O processo para criar cada microserviço é o seguinte:

1.  **Inicializar o Projeto:** Usar o **Spring Initializr** para criar um novo projeto Spring Boot na pasta correspondente (ex: `micro-company`).
2.  **Adicionar Dependências Essenciais:**
    -   `Spring Web`
    -   `Spring Data JPA` & `PostgreSQL Driver`
    -   `Spring for RabbitMQ`
    -   `Spring Cloud Starter Netflix Eureka Client`
    -   `Spring Cloud Starter Config Client`
    -   `Spring Boot Actuator` (para métricas)
    -   `Lombok` e `MapStruct` (utilitários)
3.  **Configurar `bootstrap.yml`:** Apontar para o `Config Server` e `Eureka Server`.
4.  **Migrar o Código do Monolito:**
    -   **Copiar Entidades:** Mover as classes de entidade do domínio (`Company`, `Plant`, etc.) para o novo serviço. **Importante:** Manter apenas os atributos que fazem sentido para o contexto do microserviço.
    -   **Copiar Repositórios, Serviços e DTOs:** Copiar e adaptar as interfaces e implementações das camadas de `repository`, `service`, `dto` e `controller`.
    -   **Refatorar a Lógica:** Ajustar os serviços para remover dependências de outros contextos e adicionar a comunicação via eventos.
5.  **Implementar Comunicação Assíncrona:**
    -   **Publicar Eventos:** Nos serviços que alteram dados (ex: `micro-company`), usar o `RabbitTemplate` para enviar uma mensagem para uma `TopicExchange` após salvar uma entidade (ex: `company.created`).
    -   **Consumir Eventos:** Nos serviços que precisam reagir a essas mudanças (ex: `micro-user`), criar um `@RabbitListener` para consumir a mensagem da fila e executar a lógica necessária (ex: limpar `Coverage` se uma empresa for deletada).

### Ordem de Implementação Sugerida

1.  **`micro-company`:** É um serviço de CRUD mais simples, ideal para validar a configuração da infraestrutura.
2.  **`micro-auth`:** Central para a segurança e identidade.
3.  **`micro-user`:** Depende de eventos do `micro-auth` e `micro-company`.
4.  **`micro-authorization`:** Serviço de consulta, essencial para o API Gateway proteger os endpoints.

Este guia servirá como o "norte" para o desenvolvimento, garantindo que todos os componentes sejam construídos de forma consistente e alinhada com a arquitetura alvo.