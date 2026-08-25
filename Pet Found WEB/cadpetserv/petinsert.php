<html>

<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="../login/loading.css">
</head>

<body bgcolor="#ff6600">

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <div class="loading-card">
        <div class="loading-message">Redirecionando</div>
        <div class="loading-spinner-container">
            <div class="loading-spinner"></div>
        </div>
    </div>
</body>

</html>

<?php
session_start();

include('../lib/dbconnect.php');

if ($mysqli) {
    if (
        isset($_POST['nm']) &&
        isset($_POST['idade']) &&
        isset($_POST['porte']) &&
        isset($_POST['fnl']) &&
        isset($_POST['state']) &&
        isset($_POST['hist']) &&
        isset($_POST['desc']) &&
        isset($_POST['tipet']) &&
        isset($_POST['citypet'])
    ) {
        $idade = $_POST['idade'];
        $nome = $_POST['nm'];
        $porte = $_POST['porte'];
        $finalidade = $_POST['fnl'];
        $historia = $_POST['hist'];
        $desc = $_POST['desc'];
        $raca = $_POST['raca'];
        $cor = $_POST['cor'];
        $sexo = $_POST['sexo'];
        $cid = $_POST['citypet'];
        $est = $_POST['state'];
        $tppet = $_POST['tipet'];
 
        if (strlen($nome) <= 25 && strlen($cor) <= 25 && strlen($raca) <= 25 && strlen($desc) <= 255 && strlen($historia) <= 255) {
            // Recebendo os dados do formulário

            // Observação: os dados da imagem não são enviados via $_POST, devem ser tratados separadamente

            // Obtém a hora atual em formato de string
            date_default_timezone_set('America/Sao_Paulo');
            $hora_atual = date("Hi");
            $data_atual = date("Ymd");

            // Obtém a primeira letra do nome do pet
            $comprimento = strlen($nome);
            $primeira_letra = substr($nome, 0, 1);
            $letra_do_meio = substr($nome, round($comprimento / 2) - 1, 1);
            $ultima_letra = substr($nome, -1);
            $inicial_nmp = $primeira_letra . $letra_do_meio . $ultima_letra;

            // Verificando se a sessão 'usuario' está vazia
            if (!empty($_SESSION['usuario'])) {
                $user = $_SESSION['usuario'];
                // O trecho de código abaixo recupera o código do usuário logado no banco de dados e gera o código do pet
                $query = "SELECT pcod FROM pessoa WHERE nickname = '$user'";
                $result = mysqli_query($mysqli, $query);
                if ($row = mysqli_fetch_assoc($result)) {
                    $pessoacod = $row['pcod'];

                    $inicial_p = substr($pessoacod, 0, 5);

                    $codp = "PET" . $inicial_nmp . $hora_atual . $inicial_p . $data_atual;

                    $hora_atual2 = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                    $data_atual2 = date("Y-m-d"); // Formato de data (ano-mês-dia)

                    // Montando a instrução SQL para inserir o pet na tabela "pet"
                    $sql = "INSERT INTO pet(petcod, nomepet, fai_ida, historia, raca, cor_pel, sexo, porte, descpet, estadop,cidadep, finalidade, pessoacodp, dtp, hrp, tipop) 
                    VALUES('$codp','$nome','$idade','$historia','$raca','$cor','$sexo','$porte','$desc','$est','$cid','$finalidade','$pessoacod','$data_atual2','$hora_atual2 ','$tppet')";

                    // Executando a instrução SQL para inserir o pet na tabela "pet"
                    if (mysqli_query($mysqli, $sql)) {
                        if (!empty($_FILES['picture__input']) && $_FILES['picture__input']['error'] === UPLOAD_ERR_OK) {

                            $codi = "IMGpet" . $inicial_nmp . $hora_atual . $inicial_p . $data_atual;

                            $image = $_FILES['picture__input']['tmp_name'];
                            $imgData = file_get_contents($image);

                            $sql2 = "INSERT INTO imagem (codimg, img, petcodpet) VALUES ('$codi', ?, '$codp')";
                            $stmt = mysqli_prepare($mysqli, $sql2);
                            mysqli_stmt_bind_param($stmt, "s", $imgData);
                            if (mysqli_stmt_execute($stmt)) {
                                echo "<script>
                        Swal.fire(
                            'Pet ".$nome." Cadastrado com sucesso',
                            'Bem-vindo ao time',
                            'success'
                        ).then(() => {
                            window.location.href='../index.php'
                        });
                    </script>";
                            } else {
                                $sql = "DELETE FROM pet WHERE petcod = '$codp';";
                                $result = mysqli_query($mysqli, $sql);
                                // Tratar o caso em que a inserção da imagem falha
                                echo "Erro ao cadastrar a imagem no banco de dados: " . mysqli_error($mysqli);
                                echo "Delete: " . $result;
                            }
                        } else {
                            echo "<script>
                        Swal.fire(
                            'Cadastrado com sucesso',
                            'Bem-vindo ao time',
                            'success'
                        ).then(() => {
                            window.location.href='../index.php'
                        });
                    </script>";
                        }
                    } else {

                        echo "Erro: Ao inserir" . "c:" . $codp . " n:" . $nome . " i:" . $idade . " h:" . $historia . " r:" . $raca . " c:" . $cor . " s:" . $sexo . " p:" . $porte . " d:" . $desc . " e:" . $endereco . " f:" . $finalidade . " pc:" . $pessoacod . " d:" . $data_atual . " h:" . $hora_atual . "aa " . $mysqli->error;
                    }
                } else {
                    echo "N encontrou pessoa" . $user;
                }
            } elseif (!empty($_SESSION["admin"])) {
                echo "<script>
                    Swal.fire(
                    'Sistema incorreto',  
                    'Para cadastrar um pet como admin, utilize o sistema JAVA',
                    'error'
                    ).then(() => {
                        window.history.back();
                    });
                    </script>";
            } else {
                // Tratar o caso em que ambas as sessões estão vazias
                echo "<script>
                    Swal.fire(
                    'Erro no Cadastro do pet', 
                    'Nenhuma conta logada',
                    'error'
                    ).then(() => {
                        window.history.back();
                    });
                    </script>";
                exit; // Terminar a execução do script, pois não temos um usuário válido
            }
        } else {
            echo "<script>
                    Swal.fire(
                    'Excedência de Caráter', 
                    'Você ultrapassou o limite de caracteres. Nome,Raça e Cor só podem ter 25. Descrição e História só podem ter 255',
                    'error'
                    ).then(() => {
                        window.history.back();
                    });
                    </script>";
        }
    } else {
        echo "aa" . isset($_POST['nm']) . isset($_POST['idade']) . isset($_POST['porte']) . isset($_POST['fnl']) . isset($_POST['end']) . isset($_POST['hist']) . isset($_POST['desc']);
        echo "<script>
                    Swal.fire(
                    'Campos Vazios', 
                    'Preencha todos os campos',
                    'error'
                    ).then(() => {
                        window.history.back();
                    });
                    </script>";
    }
} else {
    die("Falha ao conectar com o banco de dados: " . mysqli_connect_error());
}
