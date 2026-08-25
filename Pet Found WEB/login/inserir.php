<html>

<head>

</head>

<body bgcolor="#ff6600">

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>

</html>
<?php
include('../lib/dbconnect.php');

// Verificação da conexão
//if (!$conn) {

//}
if ($mysqli) {
    
    if (isset($_POST['nome']) && isset($_POST['cep']) && isset($_POST['brr']) && isset($_POST['cpmt']) && isset($_POST['city']) && !empty($_POST['uf']) && isset($_POST['Snome']) && isset($_POST['email']) && isset($_POST['senha']) && isset($_POST['celular']) && isset($_POST['usuario']) && isset($_POST['endereco']) && isset($_POST['sexo']) && isset($_POST['dt']) && isset($_POST['nm'])) {
        $uf = $_POST['uf'];
        $usuario = $_POST['usuario'];
        $nome = $_POST['nome'];
        $sobrenome = $_POST['Snome'];
        $email = strtolower($_POST['email']);
        $senha = $_POST['senha'];
        $celular = $_POST['celular'];
        $sexo = $_POST['sexo'];
        $dt = $_POST['dt'];
        $endereco = $_POST['endereco'];
        $cep = $_POST['cep'];
        $city = $_POST['city'];
        $brr = $_POST['brr'];
        $nm = $_POST['nm'];
        $cpmt = $_POST['cpmt'];

        $celularf = preg_replace("/[^0-9]/", "", $celular);
        if (strlen($nome) <= 50 && strlen($usuario) <= 50 && strlen($sobrenome) <= 50 && strlen($email) <= 100 && strlen($senha) <= 25 && strlen($senha) <= 25 && strlen($endereco) <= 50 && strlen($brr) <= 25 && strlen($nm) <= 5 && strlen($cpmt) <= 25) {
            // Recebendo os dados do formulário
            if (preg_match('/[a-zA-Z]/', $nm) || strlen($nm) > 5) {
                echo "<script>
                    Swal.fire(
                    'Erro no Cadastro',
                    'Número não pode conter letra ou Possui mais de 5 Digitos',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
            } else {
                $query = "SELECT * FROM pessoa WHERE 
    LOWER(emailp) = LOWER('$email') OR 
    contatop = '$celularf' OR 
    LOWER(nickname) = LOWER('$usuario')";
                $result = mysqli_query($mysqli, $query);
                $total = mysqli_num_rows($result);

                if ($total > 0) {


                    while ($row = $result->fetch_assoc()) {

                        if (strtolower($row['emailp']) == strtolower($email)) {
                            echo "<script>
                Swal.fire(
                'Erro no Cadastro',
                'Email já cadastrado',
                'error'
                ).then(() => {
                    window.history.back();
                });
            </script>";
                        }
                        if ($row['contatop'] == $celularf) {
                            echo "<script>
                Swal.fire(
                'Erro no Cadastro',
                'Celular já Cadastrado',
                'error'
                ).then(() => {
                    window.history.back();
                });
            </script>";
                        }
                        if (strtolower($row['nickname']) == strtolower($usuario)) {
                            echo "<script>
                Swal.fire(
                'Erro no Cadastro',
                'Usuário já existe',
                'error'
                ).then(() => {
                    window.history.back();
                });
            </script>";
                        }
                    }
                } else {

                    $query = "SELECT * FROM admin WHERE usera = '$usuario'";
                    $result = mysqli_query($mysqli, $query);
                    $total = mysqli_num_rows($result);
                    if ($total > 0) {
                        while ($row = $result->fetch_assoc()) {

                            echo "<script>
                        Swal.fire(
                        'Erro no Cadastro',
                        'Usuário já existe',
                        'error'
                        ).then(() => {
                            window.history.back();
                        });
                        </script>";
                        }
                    } else {
                        // Obtém a hora atual em formato de string
                        date_default_timezone_set('America/Sao_Paulo');
                        $hora_atual = date("Hi");
                        $data_atual = date("Ymd");
                        // Obtém a primeira letra do nickname
                        $comprimento = strlen($usuario);
                        $primeira_letra = substr($usuario, 0, 1);
                        $letra_do_meio = substr($usuario, round($comprimento / 2) - 1, 1);
                        $ultima_letra = substr($usuario, -1);
                        $inicial_nick = $primeira_letra . $letra_do_meio . $ultima_letra;

                        $primeira_letran = substr($nome, 0, 1);
                        $letra_do_meion = substr($nome, round($comprimento / 2) - 1, 1);
                        $ultima_letran = substr($nome, -1);
                        $inicial_nome = $primeira_letran . $letra_do_meion . $ultima_letran;
                        // Concatena as informações para gerar o código
                        $cod = "PF" . $inicial_nick . $hora_atual . $inicial_nome . $data_atual;
                        $codsub = substr($cod, 0, 6);
                        $codend = "ENDpf" . $inicial_nome . $hora_atual . $codsub . $data_atual;
                        $hora_atual2 = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                        $data_atual2 = date("Y-m-d"); // Formato de data (ano-mês-dia)



                        // Monta a instrução SQL
                        $sql3 = "INSERT INTO endereco (endcod,cep,uf,bairro,endereco,numero,cmpt,cidade) VALUES ('$codend', '$cep', '$uf','$brr', '$endereco', '$nm','$cpmt', '$city');";
                        $sql = "INSERT INTO pessoa (nickname, emailp, contatop, endcodend, senha,nomep,snomep,pcod,dtcriacao,hrcriacao) VALUES ('$usuario', '$email', '$celularf','$codend',  '$senha','$nome','$sobrenome','$cod','$data_atual2','$hora_atual2')";
                        $sql2 = "INSERT INTO fisica (dt_nascimento, sexo, codp) VALUES ('$dt', '$sexo', '$cod')";
                        // Executa a instrução SQL
                        if (mysqli_query($mysqli, $sql3)) {
                            if (mysqli_query($mysqli, $sql)) {
                                if (mysqli_query($mysqli, $sql2)) {
                                    echo "<script>
            Swal.fire(
                'Cadastrado com sucesso',
                'Bem-vindo ao time',
                'success'
            ).then(() => {
                window.location.href='login.php';
            });
        </script>";
                                } else {
                                    $sql = "DELETE FROM `pessoa` WHERE codp='$cod'";
                                    echo "Erro ao cadastrar no banco de dados: " . mysqli_error($mysqli);
                                }
                            } else {
                                echo "Erro ao cadastrar no banco de dados: " . mysqli_error($mysqli);
                            }
                        } else {
                            echo "Erro ao cadastrar no banco de dados: " . mysqli_error($mysqli);
                        }
                    }
                }
            }
        } else {
            echo "<script>
            Swal.fire(
            'Excedência de Caráter', 
            'Você ultrapassou o limite de caracteres. Email só pode ter 100. Nome, Sobrenome, Endereço, Nickname só pode ter 50. Senha, complemento, cidade, bairro só pode ter 25. Número só pode ter 5',
            'error'
            ).then(() => {
                window.history.back();
            });
            </script>";
        }
    } else {
        echo "<script>
        Swal.fire(
        'Erro no Cadastro',
        'Preencha todos os campos Obrigatórios',
        'error'
        ).then(() => {
            window.history.back();
        });
        </script>";
    }
} else {
    die("Falha ao conectar com o banco de dados: " . mysqli_connect_error());
}
// Fechando a conexão
//mysqli_close($conn);

?>