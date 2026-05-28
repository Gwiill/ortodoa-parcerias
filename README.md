# ORTODOA Parcerias

Projeto Spring Boot minimalista para simular um fluxo de pedido de parceria entre hospitais/instituições e o ORTODOA.

## Objetivo
Dar ênfase a:
- Java e orientação a objetos
- Spring Boot
- Spring Security com login por perfil
- Spring Data JPA
- H2 Database
- Thymeleaf

## Fluxo do sistema
1. A instituição preenche o formulário público.
2. O sistema cria automaticamente um login usando o e-mail.
3. A senha inicial é o CNPJ informado.
4. O login da instituição fica desabilitado enquanto o pedido estiver pendente.
5. O administrador entra no sistema, analisa o cadastro e aprova ou reprova.
6. Após aprovação, a instituição consegue entrar no painel e visualizar seu status.

## Onde analisar o cadastro de cada parceiro
Depois de logar como administrador, acesse:
- `/admin/parceiros`

Nessa tela você vê:
- dados da instituição
- status do pedido
- login gerado
- senha inicial de acesso

## Usuários de teste
### Administrador
- login: `admin@ortodoa.com`
- senha: `admin123`

### Instituição aprovada
- login: `hospital.aprovado@ortodoa.com`
- senha: `12345678000199`

## Como rodar
```bash
./mvnw spring-boot:run
```

Depois abra:
- `http://localhost:8080/`
- `http://localhost:8080/login`
- `http://localhost:8080/h2-console`
- Rodar o Mailpit executar no console: "docker start mailpit"
- Para acessar Mailpit: http://localhost:8025

## H2 Console
Use estas configurações:
- JDBC URL: `jdbc:h2:mem:ortodoa_db`
- User Name: `sa`
- Password: deixe em branco

## Estrutura principal
- `entity/` -> entidades do domínio
- `repository/` -> acesso aos dados
- `service/` -> regras de negócio
- `controller/` -> fluxo web
- `config/` -> segurança e carga inicial
- `templates/` -> páginas Thymeleaf
