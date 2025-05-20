[Controller] ─▶ [Service] ─▶ [Repository] ─▶ [MongoDB]
Service servirá para colcoar, geralmente, as regras de negócio e não sobrecarregar a minha controller, antes de realizer
a conexão com o banco de dados.

Para estrutura do projeto, vou trabalhar da seguinte forma.

[Frontend Web (S3 + CloudFront)]

| 1. Upload CNAB via SDK JS

[Bucket S3 (uploads)]

| 2. Trigger (evento de upload)

[AWS Lambda (trigger-upload)]

| 3. Envia nome do arquivo para fila

[AWS SQS (fila-processamento)]

| 4. Microserviço Spring Boot lê da fila (em container)

[Container Java (Spring Boot)]

| - Lê CNAB do S3
| - Processa e salva dados no MongoDB
| - Gera relatório em JSON e envia ao S3 (relatorios/)
| - (Opcional) chama Lambda de cache

[MongoDB Atlas]         [S3 (relatórios)]

| 5. Lambda invalida cache (CloudFront)


[API REST (Spring)]     [Usuário vê dados atualizados]

[Frontend consulta por API]

/meu-projeto/
├── api-simulada/                    <-- API simulada para interações e testes
├── backend-core/                    <-- Backend principal, com lógica e processamento
│    ├── src/
│    │    ├── main/
│    │    │    ├── java/
│    │    │    │    ├── com/
│    │    │    │    │    ├── corretora/
│    │    │    │    │    │    ├── controller/             <-- Controladores REST
│    │    │    │    │    │    ├── service/                <-- Regras de negócio
│    │    │    │    │    │    ├── repository/             <-- Repositórios MongoDB
│    │    │    │    │    │    ├── domain/                 <-- Entidades (Usuario, etc.)
│    │    │    │    │    │    ├── lambda/                 <-- Funções Lambda
│    │    │    │    │    │    │    ├── ProcessarArquivoLambda.java  <-- Lambda para processamento de arquivos
│    │    │    │    │    │    │    ├── NotificacaoLambda.java        <-- Lambda para notificações
│    │    │    │    │    │    │    └── InvalidaCacheLambda.java     <-- Lambda para invalidação de cache
│    │    │    │    │    └── application.properties
├── cnab-sample/                     <-- Exemplo de arquivo CNAB para teste
├── exemplo-enumeradores/            <-- Enumerações e utilitários para o projeto
├── frontend/                        <-- Frontend (ex: SPA hospedado no S3)
├── infra/                           <-- Infraestrutura (ex: Terraform, AWS configurations)
├── lambdas/                         <-- Funções Lambda separadas em diferentes diretórios
│    ├── trigger-upload/             <-- Lambda para trigger de upload
│    │    ├── handler.js
│    │    └── package.json
│    ├── processar-arquivo/          <-- Lambda para processamento de CNAB
│    │    ├── handler.js
│    │    └── package.json
│    ├── invalida-cache/             <-- Lambda para invalidar cache
│    │    ├── handler.js
│    │    └── package.json
├── scripts/                         <-- Scripts auxiliares (ex: deploy, build)
├── read.me                          <-- Documentação do projeto
└── .env                              <-- Variáveis de ambiente

