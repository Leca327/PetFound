CREATE DATABASE PetFound;
USE PetFound;

-- Tabela "versao"
CREATE TABLE versao (
codv VARCHAR(6) PRIMARY KEY,
nome VARCHAR(50) NOT NULL,
descv VARCHAR(480) NOT NULL
);

INSERT INTO versao VALUES("v2.8.0","Pequenos Detalhes","-Inserção de Imagem C.R.U.D.;-Mostrar e Ocultar Senha;-Tela de Splash;-Menu item 'Sobre' com Versão;-Copiar item de Busca");
INSERT INTO versao VALUES("v2.9.0","Novo Visual","-Novo Design No Sistema.;-Importação de Audios no Sistema.;-Guardar Versões Antigas.;");
INSERT INTO versao VALUES("v2.9.1","Correções de erro","-Sistema de Alertas novos.;-Disponível sistema de aprovação de pet e serviço.;-Correções de Bugs.;-Possibilidade de Desativar Audio.;");
INSERT INTO versao VALUES("v3.0.0","Upgrade No Sistema","-Design Melhorado.;-Verificação de Tamanho De Campos de Texto.;-Sistema de Busca Melhorado.;-Desativar Entidade.;");
INSERT INTO versao VALUES("v3.1.0","Upgrade No Sistema","-Sistema de ticket para ajuda.;-Melhoria na notificação.;-Registro de login de admin.;-Correções de bugs.;");

-- Tabela "admin"
CREATE TABLE admin(
usera VARCHAR(25) NOT NULL UNIQUE,
senhaa VARCHAR(25) NOT NULL,
nomea VARCHAR(50) NOT NULL,
admcod VARCHAR(25) PRIMARY KEY NOT NULL,
imgadm LONGBLOB NULL,
bloqueioadm BOOLEAN NULL,
chefe BOOLEAN NOT NULL
);

INSERT INTO admin (usera, senhaa, nomea, admcod,chefe) VALUES ('duda', 'admin', 'Maria Eduarda', 'Admdda1214mDA9072023', true); 
INSERT INTO admin (usera, senhaa, nomea, admcod,chefe) VALUES ('leca', 'admin', 'Letícia', 'Admlca1214Lia19072023',true);
INSERT INTO admin (usera, senhaa, nomea, admcod,chefe) VALUES ('bomfim', 'admin', 'João Victor', 'Admbdfm1214Jir19072023',false);
INSERT INTO admin (usera, senhaa, nomea, admcod,chefe) VALUES ('gust', 'admin', 'Gustavo', 'Admgst1214Gto19072023',false);
INSERT INTO admin (usera, senhaa, nomea, admcod,chefe) VALUES ('nulo','adminchefe','CHEFE','10',true);
/*Admin de Segurança*/
INSERT INTO admin (usera, senhaa, nomea, admcod,chefe) VALUES ('null','adminchefe','reserva','1',true);

-- Tabela "endereco"
CREATE TABLE endereco (
endcod VARCHAR(50) PRIMARY KEY NOT NULL,
cep VARCHAR(15) NOT NULL,
uf VARCHAR(2) NOT NULL,
bairro VARCHAR(25) NOT NULL,
endereco VARCHAR(50) NOT NULL,
numero VARCHAR(5) NOT NULL,
cmpt VARCHAR(25),
cidade VARCHAR(25) NOT NULL
);

-- Tabela "pessoa"
CREATE TABLE pessoa (
nomep VARCHAR(50) NOT NULL,
contatop VARCHAR(18) NOT NULL UNIQUE,
emailp VARCHAR(100) NOT NULL UNIQUE,
endcodend VARCHAR(255) NOT NULL,
nickname VARCHAR(50) NOT NULL UNIQUE,
senha VARCHAR(25) NOT NULL,
pcod VARCHAR(50) PRIMARY KEY NOT NULL,
imgperfil LONGBLOB NULL,
dtcriacao DATE NOT NULL,
hrcriacao TIME NOT NULL,
snomep VARCHAR(50) NULL,
FOREIGN KEY (`endcodend`) REFERENCES `endereco` (`endcod`) ON DELETE CASCADE,
bloqueiop BOOLEAN NULL,
banner  LONGBLOB NULL
);

-- Tabela "fisica"
CREATE TABLE fisica (
dt_nascimento DATE NOT NULL,
sexo ENUM('M', 'F', 'O') NOT NULL,
codp VARCHAR(50) NOT NULL UNIQUE,
FOREIGN KEY (`codp`) REFERENCES `pessoa` (`pcod`) ON DELETE CASCADE
);

-- Tabela "juridica"
CREATE TABLE juridica (
cnpj VARCHAR(18) PRIMARY KEY NOT NULL,
cod_p VARCHAR(50) NOT NULL UNIQUE,
ramo_ativ VARCHAR(50) NOT NULL,
tipoj ENUM('ONG', 'Empresa') NOT NULL,
FOREIGN KEY (`cod_p`) REFERENCES `pessoa` (`pcod`) ON DELETE CASCADE
);

-- Tabela "pet"
CREATE TABLE pet (
nomepet VARCHAR(25) NOT NULL,
descpet VARCHAR(255) NULL,
historia VARCHAR(255) NOT NULL,
fai_ida ENUM('Filhote', 'Jovem','Adulto','Senior','Idoso') NOT NULL,
raca VARCHAR(25) NOT NULL,
cor_pel VARCHAR(25) NOT NULL,
sexo ENUM('M', 'F') NOT NULL,
porte ENUM('Grande', 'Medio','Pequeno') NOT NULL,
petcod VARCHAR(25) PRIMARY KEY NOT NULL,
pessoacodp VARCHAR(25) NULL,
admincodadmn VARCHAR(25) NULL,
estadop VARCHAR(2) NULL,
cidadep VARCHAR(50) NULL,
finalidade ENUM('Adocao', 'Padrinho','Pad_Ado') NULL,
FOREIGN KEY (`pessoacodp`) REFERENCES `pessoa` (`pcod`) ON DELETE CASCADE,
FOREIGN KEY (`admincodadmn`) REFERENCES `admin` (`admcod`),
aprovacaopet BOOLEAN NULL,
dtp DATE NOT NULL,
hrp TIME NOT NULL,
tipop VARCHAR(25) NOT NULL,
motivoreppet VARCHAR(255) NULL,
bloqueiopet BOOLEAN NULL
);

-- Tabela "servico"
CREATE TABLE servico (
nomeserv VARCHAR(50) NOT NULL,
descserv VARCHAR(255) NOT NULL,
estados VARCHAR(2) NULL,
cidades VARCHAR(50) NULL,
preco double NOT NULL,
servcod VARCHAR(50) PRIMARY KEY NOT NULL,
pessoa_codp VARCHAR(25) NULL,
admin_codadmn VARCHAR(25) NULL,
FOREIGN KEY (`pessoa_codp`) REFERENCES `pessoa` (`pcod`) ON DELETE CASCADE,
FOREIGN KEY (`admin_codadmn`) REFERENCES `admin` (`admcod`),
aprovacaoserv BOOLEAN NULL,
dts DATE NOT NULL,
hrs TIME NOT NULL,
motivorepserv VARCHAR(255) NULL,
bloqueioserv BOOLEAN NULL
);

-- Tabela "imagem"
CREATE TABLE imagem (
codimg VARCHAR(50),
img LONGBLOB NULL,
petcodpet VARCHAR(25) NULL,
servicocodserv VARCHAR(25) NULL,
FOREIGN KEY (`petcodpet`) REFERENCES `pet` (`petcod`) ON DELETE CASCADE,
FOREIGN KEY (`servicocodserv`) REFERENCES `servico` (`servcod`)
);

-- Tabela "contatopet"
CREATE TABLE contatopet (
pfcodp VARCHAR(50) NOT NULL,
petcodpet VARCHAR(50) NOT NULL,
tipocont ENUM('adt', 'apd','adt_apd') NOT NULL,
codcontp VARCHAR(50) PRIMARY KEY,
FOREIGN KEY (`petcodpet`) REFERENCES `pet` (`petcod`) ON DELETE CASCADE,
FOREIGN KEY (`pfcodp`) REFERENCES `fisica` (`codp`),
apadrinhou BOOLEAN NULL,
adotou BOOLEAN NULL,
dtcp DATE NOT NULL,
hrcp TIME NOT NULL,
ligoupet BOOLEAN NULL,
confirmpet BOOLEAN NULL,
confirmpe BOOLEAN NULL,
arquivarpet BOOLEAN NULL,
dtfinalcp DATE NULL,
hrfinalcp TIME NULL
);

-- Tabela "contatoserv"
CREATE TABLE contatoserv (
pcodp VARCHAR(50) NOT NULL,
scodserv VARCHAR(50) NOT NULL,
codconts VARCHAR(50) PRIMARY KEY,
FOREIGN KEY (`scodserv`) REFERENCES `servico` (`servcod`) ON DELETE CASCADE,
FOREIGN KEY (`pcodp`) REFERENCES `pessoa` (`pcod`)  ON DELETE CASCADE,
contratou BOOLEAN NULL,
avaliacao INTEGER NULL,
comentario VARCHAR(255) NULL,
dtcs DATE NOT NULL,
hrcs TIME NOT NULL,
ligouserv BOOLEAN NULL,
confirmserv BOOLEAN NULL,
confirm_pe BOOLEAN NULL,
arquivarserv BOOLEAN NULL,
dtfinalcs DATE NULL,
hrfinalcs TIME NULL,
dtaval DATE NULL,
hraval TIME NULL
);

CREATE TABLE NOTIFICACAO(
notcod VARCHAR(50)  NOT NULL PRIMARY KEY ,
pessoa_codpessoa VARCHAR(50) NOT NULL,
mensagem VARCHAR(255) NOT NULL,
contcodcontatoserv VARCHAR(50) NULL,
contcodcontatopet VARCHAR(50) NULL,
p_codpet VARCHAR(50) NULL,
s_codserv VARCHAR(50) NULL,
FOREIGN KEY (`p_codpet`) REFERENCES `pet` (`petcod`)  ON DELETE CASCADE,
FOREIGN KEY (`s_codserv`) REFERENCES `servico` (`servcod`) ON DELETE CASCADE,
FOREIGN KEY (`contcodcontatoserv`) REFERENCES `contatoserv` (`codconts`) ON DELETE CASCADE,
FOREIGN KEY (`contcodcontatopet`) REFERENCES `contatopet` (`codcontp`) ON DELETE CASCADE,
FOREIGN KEY (`pessoa_codpessoa`) REFERENCES `pessoa` (`pcod`) ON DELETE CASCADE,
active Boolean NOT NULL,
notifications_name VARCHAR(50) NOT NULL,
dtnot DATE NOT NULL,
hrnot TIME NOT NULL
);

CREATE TABLE faleconosco(
fccod VARCHAR(50)  NOT NULL PRIMARY KEY ,
assunto VARCHAR(50)  NOT NULL,
descfc VARCHAR(255)  NOT NULL,
titulofc VARCHAR(50)  NOT NULL,
emailfc VARCHAR(100)  NOT NULL,
nomepes VARCHAR(50)  NOT NULL,
statusfc  Boolean NULL,
dtfc DATE NULL,
hrfc TIME NULL,
adminfc VARCHAR(25) NULL,
FOREIGN KEY (`adminfc`) REFERENCES `admin` (`admcod`) ,
situfinal VARCHAR(255) NULL,
dtfinal DATE NULL,
hrdinal TIME NULL
);

CREATE TABLE regislogadm(
rlacod VARCHAR(50)  NOT NULL PRIMARY KEY ,
daterla DATE NOT NULL,
hrrla TIME NOT NULL,
codlogadm VARCHAR(25) NOT NULL,
FOREIGN KEY (`codlogadm`) REFERENCES `admin` (`admcod`),
tpsistema VARCHAR(25) NOT NULL
);