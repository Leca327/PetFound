<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="./login/loading.css">
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

<?php

    include('./dbconnect.php');
    // Verifique se o parâmetro 'codnot' foi enviado via GET
    if (isset($_GET['codnot'])) {
        // Conecte-se ao banco de dados 


        // Prepare a consulta SQL para atualizar 'active' para 'false' com base no 'codnot' fornecido
        $codnot = $mysqli->real_escape_string($_GET['codnot']);
        $updateQuery = "UPDATE notificacao SET active = false WHERE notcod = '$codnot'";

        // Execute a consulta de atualização
        if ($mysqli->query($updateQuery)) {
            $query = "SELECT * FROM notificacao WHERE notcod = '" . $codnot . "'";
            $result = mysqli_query($mysqli, $query);
            if ($row = mysqli_fetch_assoc($result)) {
                $apvpet = $row['p_codpet'];
                $apvserv = $row['s_codserv'];
                $contpet = $row['contcodcontatopet'];
                $contserv = $row['contcodcontatoserv'];

                $caminhoArquivo6 = '../anuncio/perfilpet.php';

                if (file_exists($caminhoArquivo6)) {
                    $url7 = '../anuncio/perfilpet.php?petcod=' . $apvpet ;
                    $url8 = '../anuncio/perfilserv.php?servcod=' . $apvserv;
                    $url9 = '../painel.php?opc=op2&petcod=' . $contpet;
                    $url10 = '../painel.php?opc=op5&petcod=' . $contpet;
                    $url11='../painel.php?opc=op4&servcod=' . $contserv;
                    $url12='../painel.php?opc=op6&servcod=' . $contserv;
                } else {
                    $url7 = './anuncio/perfilpet.php?petcod=' . $apvpet ;
                    $url8 = './anuncio/perfilserv.php?servcod=' . $apvserv;
                    $url9 = './painel.php?opc=op2&petcod=' . $contpet;
                    $url10 = './painel.php?opc=op5&petcod=' . $contpet;
                    $url11='./painel.php?opc=op4&servcod=' . $contserv;
                    $url12='./painel.php?opc=op6&servcod=' . $contserv;
                }
 
                if (isset($apvpet)) {
                    echo "<script>window.location = '".$url7."';</script>";
                } else if (isset($apvserv)) {
                    echo "<script>window.location = '".$url8."';</script>";
                } else if (isset($contpet)) {
                    $tipo = substr($codnot, 0, 10);
                    if ($tipo == "NOTCONTPET") {
                        echo "<script>window.location = '".$url9."';</script>";
                    } else {
                        echo "<script>window.location = '".$url10."';</script>";
                    }
                } else if (isset($contserv)) {
                    $tipo = substr($codnot, 0, 10);
                    if ($tipo == "NOTCONTSVC") {
                        echo "<script>window.location = '".$url11."';</script>";
                    } else {
                        echo "<script>window.location = '".$url12."';</script>";
                    }
                } else {
                    echo "<script> window.history.back();</script>";
                }
            }
        } else {
            echo "Erro ao marcar mensagem como lida: " . $mysqli->error;
        }

        // Feche a conexão com o banco de dados
        $mysqli->close();
    } else if (isset($_GET['todos'])) {
        session_start();
        $nickname = $_SESSION['usuario'];
        $selectQuery = "SELECT pcod FROM pessoa WHERE nickname = '$nickname'";

        $result = $mysqli->query($selectQuery);

        if (!$result) {
            echo "Erro ao buscar 'pessoa_codp': " . $mysqli->error;
            exit;
        }

        $row = $result->fetch_assoc();
        $pessoa_codp = $row['pcod'];

        // Prepare a consulta SQL para atualizar 'active' para 'false' com base em 'pessoa_codp'
        $updateQuery = "UPDATE notificacao SET active = false WHERE pessoa_codpessoa = '$pessoa_codp'";
        if ($mysqli->query($updateQuery)) {
            echo "<script> window.history.back();</script>";
        } else {
            echo "Erro ao marcar mensagens como lidas: " . $mysqli->error;
        }

        // Feche a conexão com o banco de dados
        $mysqli->close();
    } else {
        echo "Parâmetro 'codnot' não fornecido.";
    }
