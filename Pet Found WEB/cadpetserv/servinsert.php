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
        isset($_POST['preco']) &&
        isset($_POST['desc']) &&
        isset($_POST['citys']) &&
        isset($_POST['ests'])
    ) {
        // Recebendo os dados do formulário
        $nome = $_POST['nm'];
        $preco = $_POST['preco'];
        $desc = $_POST['desc'];
        $cid = $_POST['citys']; 
        $est = $_POST['ests'];
        // Observação: os dados da imagem não são enviados via $_POST, devem ser tratados separadamente

        if (strlen($nome) <= 25 && strlen($desc) <= 255 ) {

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

                    $cods = "SVC" . $inicial_nmp . $hora_atual . $inicial_p . $data_atual;

                    $hora_atual2 = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                    $data_atual2 = date("Y-m-d"); // Formato de data (ano-mês-dia)

                    // Montando a instrução SQL para inserir o pet na tabela "pet"
                    $preco = str_replace(".", "", $preco);
                    $preco = str_replace(",", ".", $preco);

                    $sql = "INSERT INTO servico(servcod, nomeserv, cidades,estados, preco, descserv, pessoa_codp, dts, hrs) 
                                 VALUES('$cods','$nome','$cid','$est','$preco','$desc','$pessoacod','$data_atual2','$hora_atual2 ')";

                    // Executando a instrução SQL para inserir o pet na tabela "pet"
                    if (mysqli_query($mysqli, $sql)) {
                        if (!empty($_FILES['picture__input']) && $_FILES['picture__input']['error'] === UPLOAD_ERR_OK) {

                            $codi = "IMGserv" . $inicial_nmp . $hora_atual . $inicial_p . $data_atual;

                            $image = $_FILES['picture__input']['tmp_name'];
                            $imgData = file_get_contents($image);

                            $sql2 = "INSERT INTO imagem (codimg, img, servicocodserv) VALUES ('$codi', ?, '$cods')";
                            $stmt = mysqli_prepare($mysqli, $sql2);
                            mysqli_stmt_bind_param($stmt, "s", $imgData);
                            if (mysqli_stmt_execute($stmt)) {
                                echo "<script>
                        Swal.fire(
                            'Cadastrado com sucesso',
                            'Bem-vindo ao time',
                            'success'
                        ).then(() => {
                            window.location.href='../index.php';
                        });
                    </script>";
                            } else {
                                $sql = "DELETE FROM servico WHERE servcod ='$cods';";
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
                            window.location.href='../index.php';
                        });
                    </script>";
                        }
                    } else {

                        echo "Erro: Ao inserir" . " n:" . $nome . " p:" . $preco . " d:" . $desc . " c:" . $cods . " pc:" . $pessoacod . " dt:" . $data_atual2 . " h:" . $hora_atual2;
                    }
                } else {
                    echo "N encontrou pessoa" . $user;
                }
            } elseif (!empty($_SESSION['admin'])) {
                echo "<script>
                    Swal.fire(
                    'Sistema incorreto',  
                    'Para cadastrar Serviço como admin, utilize o sistema JAVA',
                    'error'
                    ).then(() => {
                    window.location.href='../index.php';
                    });
                    </script>";
            } else {
                echo "n log";
                // Tratar o caso em que ambas as sessões estão vazias
                echo "Erro: Nenhum usuário logado.";
                echo "<script>
                    Swal.fire(
                    'Erro no Cadastro de Serviço',
                    'Nenhuma conta logada',
                    'error'
                    ).then(() => {
                    window.location.href='../login/login.php';
                    });
                    </script>";
                exit; // Terminar a execução do script, pois não temos um usuário válido
            }
        }else{
            echo "<script>
            Swal.fire(
            'Excedência de Caráter', 
            'Você ultrapassou o limite de caracteres. Nome só pode ter 25. Descrição só pode ter 255',
            'error'
            ).then(() => {
                window.history.back();
            });
            </script>";
        }
    } else {
        echo "aa" . isset($_POST['nm']) . isset($_POST['idade']) . isset($_POST['porte']) . isset($_POST['fnl']) . isset($_POST['end']) . isset($_POST['hist']) . isset($_POST['desc']);
        echo "<script>window.location.href = 'cadpet.php'; alert('Preencha todos os campos obrigatórios.');</script>";
    }
} else {
    die("Falha ao conectar com o banco de dados: " . mysqli_connect_error());
}

?>