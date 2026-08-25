<html>

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

</html>
<?php
include('./lib/dbconnect.php');

// Verifica se o formulário foi enviado
if ($_SERVER["REQUEST_METHOD"] == "POST") {


    // Recupera os dados do formulário
    $assunto = $_POST['assunto'];
    $descricao = $_POST['mensagem'];
    $titulo = $_POST['titulo'];
    $email = $_POST['email'];
    $nome = $_POST['nome'];
    $status = null; // Defina o status inicial conforme necessário

    if ($assunto == "outros") {
        $assunto = $_POST['assunto_outro'];
    }
    if (strlen($assunto) <= 50 && strlen($descricao) <= 255 && strlen($titulo) <= 50 && strlen($nome) <= 50 && strlen($email) <= 100) {
        $fccod = "TIC" . uniqid();
        $hratual = date("H:i:s");
        $dtatual = date("Y-m-d");
        // Prepara a instrução SQL
        $sql = "INSERT INTO faleconosco (fccod, assunto, descfc, titulofc, emailfc, nomepes, statusfc, dtfc, hrfc)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        $stmt = $mysqli->prepare($sql);

        // Verifica se a preparação da instrução foi bem-sucedida
        if ($stmt) {
            $stmt->bind_param("ssssssiss", $fccod, $assunto, $descricao, $titulo, $email, $nome, $status, $dtatual, $hratual);

            // Executa a instrução SQL
            if ($stmt->execute()) {
                echo "<script>
            Swal.fire(
            'Ticket criando', 
            'Espere até um administrador entrar em contato com você.',
            'success'
            ).then(() => {
                window.history.back();
            });
            </script>";
            } else {
                echo "Erro: " . mysqli_error($mysqli);
                echo "<script>
            Swal.fire(
            'Erro', 
            'Erro ao criar o ticket.',
            'error'
            ).then(() => {
                window.history.back();
            });
            </script>";
            }

            // Fecha a instrução e a conexão
            $stmt->close();
            $mysqli->close();
        } else {
            echo "Erro na preparação da instrução: " . $mysqli->error;
        }
    } else {
        echo "<script>
        Swal.fire(
        'Excedência de Caráter', 
        'Você ultrapassou o limite de caracteres. Descrição só pode ter 255. Email só pode ter 100. Nome, título e Assunto só pode ter 50. Número só pode ter 5',
        'error'
        ).then(() => {
            window.history.back();
        });
        </script>";
    }
} else {
    echo "Este script deve ser acessado via método POST.";
}
?>