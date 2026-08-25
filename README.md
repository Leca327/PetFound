# Pet Found — Fazendo Aumigos pela Vida

Plataforma digital que reúne ONGs de proteção animal, cuidadores temporários, pessoas físicas e empresas do ramo pet em um único lugar, com o objetivo de aumentar o número de adoções responsáveis e reduzir a quantidade de animais abandonados nas ruas e em abrigos.

Trabalho de Conclusão de Curso — Curso Técnico em Informática, Instituto Superior de Educação do Rio de Janeiro (ISERJ/FAETEC), maio de 2023.

---

## O problema

Animais abandonados dependem de ONGs e cuidadores temporários que trabalham com recursos escassos e pouca visibilidade. A comunicação entre quem quer adotar e quem cuida do animal é fragmentada, espalhada por perfis de rede social e contatos informais, o que alonga o tempo até a adoção.

Some-se a isso os animais com necessidades especiais, que raramente encontram adotante — mas que poderiam ter a vida sustentada por padrinhos e madrinhas, um arranjo que quase nenhuma plataforma contempla.

## A proposta

O Pet Found organiza esse encontro em uma rede colaborativa com três frentes:

- **Adoção** — anúncios de animais disponíveis, buscáveis por raça, porte, faixa etária, sexo e localização
- **Apadrinhamento** — animais que permanecem sob cuidado da ONG mas recebem apoio de padrinhos; um mesmo animal pode estar aberto a adoção, a apadrinhamento ou a ambos
- **Serviços** — pet shops, clínicas veterinárias, banho e tosa, castração e vacinação divulgados por empresas e ONGs, com avaliação por quem contratou

## Sistemas

O projeto é composto por dois clientes sobre o mesmo banco MySQL:

**Site em PHP** — usado pelo público. Cadastro de pessoa física e jurídica, publicação e edição de anúncios de animais e serviços, busca com filtros, página de perfil, contato entre as partes, avaliação de serviços, fale conosco e sistema de tickets.

**Painel administrativo em Java Swing** — usado pela moderação. Aprovação de anúncios, gestão de usuários, acompanhamento de contatos, envio de e-mails e desativação de entidades.

## Atores do sistema

| Ator | Papel |
|---|---|
| Pessoa física | Adota, apadrinha, anuncia animais que não pode manter e divulga serviços |
| Pessoa jurídica (ONG) | Divulga animais resgatados e oferece serviços como castração e vacinação |
| Pessoa jurídica (empresa) | Divulga serviços do ramo pet e pode apoiar animais em busca de lar |
| Administrador | Aprova anúncios, modera conteúdo e mantém o sistema |

## Modelo de dados

O banco `PetFound` parte de uma especialização de `pessoa` em física e jurídica:

| Tabela | Papel |
|---|---|
| `pessoa` | Cadastro comum, com nickname e e-mail únicos |
| `fisica` / `juridica` | Especializações — nascimento e sexo; CNPJ, ramo e tipo (ONG ou empresa) |
| `endereco` | Endereço completo, referenciado por pessoa |
| `pet` | Animal anunciado, com história, raça, porte, faixa etária e finalidade |
| `servico` | Serviço oferecido, com preço e área de atuação |
| `imagem` | Fotos de animais e serviços (LONGBLOB) |
| `contatopet` | Intenção de adoção ou apadrinhamento, com confirmação das duas partes |
| `contatoserv` | Contratação de serviço, com nota e comentário |
| `notificacao` | Alertas gerados pelos eventos do sistema |
| `admin` | Moderadores, com distinção entre comum e chefe |
| `versao` | Changelog exibido dentro do próprio sistema |

## Regras de negócio

- Nenhum anúncio vai ao ar antes da aprovação de um administrador
- O anunciante só pode indicar adotante, padrinho ou contratante se a pessoa tiver feito contato pelo site — o que garante rastreabilidade de todo o processo
- A conclusão de uma adoção exige confirmação de ambos os lados, evitando encerramento unilateral
- Uma pessoa física pode apadrinhar mais de um animal
- Administrador que esquece a senha depende do administrador chefe para redefini-la
- O cadastro exige idade superior a 17 anos
- Entidades são desativadas em vez de excluídas, preservando o histórico

## Estrutura do repositório

```
.
├── TCC_PetFound.pdf         # Monografia completa do projeto
├── Manual Java.pdf          # Manual de uso do painel administrativo
├── Manual Web.pdf           # Manual de uso do site
├── BD_PetFound.sql          # Script de criação do banco
│
├── Pet Found Java/
│   ├── src/
│   │   ├── dao/             # Acesso a dados por entidade
│   │   ├── modelo/          # Classes de modelo
│   │   ├── factory/         # ConnectionFactory — conexão JDBC
│   │   ├── GUI/             # Tela principal
│   │   ├── InJframe/        # Telas internas de busca, notificações e tickets
│   │   ├── Logar/           # Login, recuperação de senha e versão
│   │   ├── starter/         # Splash screen
│   │   ├── alert/           # Componentes de alerta
│   │   └── img/ gif/ audio/ # Recursos da interface
│   └── build.xml            # Projeto Ant/NetBeans
│
├── Pet Found WEB/
│   ├── login/               # Autenticação e recuperação de senha
│   ├── cadastro_pessoa/     # Cadastro de pessoa física e jurídica
│   ├── cadpetserv/          # Cadastro de animais e serviços
│   ├── anuncio/             # Listagem e detalhe dos anúncios
│   ├── Adm/                 # Área administrativa web
│   ├── assets/ lib/         # Recursos estáticos
│   └── bootstrap/           # Framework CSS
│
└── Bibliotecas/             # MySQL Connector/J e JavaMail
```

## Como executar

**Banco de dados** — importe `BD_PetFound.sql` em um servidor MySQL. O script cria o schema, popula a tabela de versões e cadastra os administradores iniciais.

**Painel Java** — abra `Pet Found Java` no NetBeans. Adicione ao classpath o `mysql-connector-j-8.0.31.jar` e os JARs de e-mail em `Bibliotecas/`. As credenciais ficam em `src/factory/ConnectionFactory.java` (por padrão, `localhost:3306`, usuário e senha `root`).

**Site PHP** — copie `Pet Found WEB` para o diretório do servidor (`htdocs` no XAMPP ou `www` no WAMP) e ajuste as credenciais em `config.php`.

O passo a passo detalhado de uso de cada sistema está nos dois manuais em PDF.

## Arquitetura do painel Java

Separação em três camadas:

- **Modelo** (`modelo/`) — classes que representam as entidades do banco
- **DAO** (`dao/`) — encapsula todo o SQL; cada entidade tem seu DAO
- **Interface** (`GUI/`, `InJframe/`) — telas Swing, sem consultas SQL diretas

A conexão é centralizada em uma `ConnectionFactory`, evitando que cada DAO abra a própria.

## Histórico de versões

O sistema exibe o próprio changelog em uma tela "Sobre", alimentada pela tabela `versao`:

| Versão | Nome | Principais mudanças |
|---|---|---|
| v2.8.0 | Pequenos Detalhes | CRUD de imagens, mostrar/ocultar senha, splash screen |
| v2.9.0 | Novo Visual | Novo design, áudio no sistema, guarda de versões anteriores |
| v2.9.1 | Correções de erro | Novo sistema de alertas, aprovação de anúncios, áudio desativável |
| v3.0.0 | Upgrade No Sistema | Design aprimorado, validação de campos, busca melhorada, desativação de entidades |

## Tecnologias

Java · Swing · JDBC (MySQL Connector/J 8.0.31) · JavaMail · PHP · MySQL · Bootstrap · HTML/CSS/JavaScript

**Ambiente de desenvolvimento:** NetBeans (Java), Visual Studio Code (web), XAMPP e WAMP (servidor local)

**Metodologia:** desenvolvimento ágil, em iterações curtas, com divisão de tarefas entre as equipes de Java e web

## Nota sobre segurança

Projeto acadêmico de 2023. As senhas são armazenadas em texto puro no banco (`VARCHAR(25)`) e as credenciais de conexão estão no código-fonte. Em uso real seria necessário aplicar hash com salt (bcrypt ou Argon2), consultas parametrizadas e variáveis de ambiente para as credenciais.

## Equipe

- Gustavo dos Reis Prado
- João Victor Alves Bomfim
- Letícia dos Reis Prado
- Maria Eduarda Alves Cruz
