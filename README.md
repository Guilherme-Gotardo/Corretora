# 🏦 Projeto Corretora - Arquitetura Completa

## 🐳 Containers backend orquestrados via **Amazon EKS**

## 💾 Frontend estático servido via **Amazon S3 + CloudFront**

## 🧠 Regras de negócio (agregações MongoDB) encapsuladas no backend Java

## ⚙️ Funcionalidades específicas executadas por **AWS Lambda**

## 🖥️ EC2 atua como **nó do EKS**

---

## 🏗️ Estrutura Geral do Projeto

### 1. Frontend (Site Estático)
- **Hospedagem:** Amazon S3 + CloudFront
- **Build:** `npm run build`
- **Funções:**
  - Upload de arquivos CNAB
  - Visualização de ações
  - Simulação de saldo e histórico
  - Interface web para o usuário final

---

### 2. Backend (Java + Spring Boot)
- **Hospedagem:** Containers no Amazon EKS
- **Funções principais:**
  - APIs REST:
    - Cadastro de usuários e contas
    - Importação de arquivos CNAB
    - Compra/venda de ações
    - Agregações de saldo, carteira, etc.
  - Integração com MongoDB Atlas
  - Autenticação JWT (opcional)
  - Log em MongoDB ou S3
  - Exposição via API Gateway + ALB

---

### 3. MongoDB Atlas (Banco de Dados)
- **Collections:**
  - `usuarios`
  - `contasBancarias`
  - `acoes`
  - `movimentacoesBancarias`
  - `logs`
  - `arquivosImportados`
- **Agregações acessadas via endpoints Java**, como:
  - `/saldo/{usuarioId}`
  - `/acoes/{usuarioId}`

---

### 4. AWS Lambda
- **Funções:**
  - Trigger para importação CNAB via S3
  - Envio de notificações para Slack
  - Execução agendada com CloudWatch
  - Integração com AWS Secrets Manager
  - Comunicação com backend via HTTP

---

### 5. Infraestrutura
- **Gerenciada por:** Terraform ou AWS CloudFormation
- **Serviços usados:**
  - Amazon EKS (cluster Kubernetes)
  - Amazon EC2 (como worker node do EKS)
  - Amazon S3 (arquivos CNAB e frontend)
  - Amazon CloudFront (distribuição do site)
  - Amazon API Gateway (proxy seguro para backend)
  - Amazon IAM (controle de acesso)

---

## 📁 Estrutura do Repositório

```bash
corretora/
├── frontend/                    # Aplicação web (React, Vue etc.)
├── backend/                     # Spring Boot com APIs e lógica de negócio
│   ├── src/main/java/com/corretora/
│   │   ├── controller/          # REST Controllers
│   │   ├── service/             # Regras de negócio
│   │   ├── domain/              # Entidades e modelos
│   │   ├── repository/          # MongoDB e outras integrações
│   │   └── application/         # Casos de uso e agregações
│   └── Dockerfile
├── lambdas/                     # Funções serverless
│   ├── importarArquivo/
│   ├── notificarSlack/
│   └── ...
├── infra/                       # Código Terraform/CloudFormation
├── scripts/                     # Scripts utilitários (bash, etc)
└── readme.md
```
