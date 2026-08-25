<html>

<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="./loading.css">

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
$user = $_POST["usuario"];
$senha = $_POST["senha"];

include('../lib/dbconnect.php');

if ($mysqli) {


    $query = "SELECT * FROM pessoa WHERE BINARY nickname = '$user' AND BINARY senha = '$senha'";

    $result = mysqli_query($mysqli, $query);
    $total = mysqli_num_rows($result);
    if ($total > 0) {
        $query2 = "SELECT * FROM pessoa WHERE BINARY nickname = '$user' AND BINARY senha = '$senha' AND (bloqueiop IS NULL or bloqueiop =false)";
        $result2 = mysqli_query($mysqli, $query2);
        $total2 = mysqli_num_rows($result2);
        if ($total2 > 0) {

            // Mantém a conexão e executa as demais ações que o sistema precisa
            while ($row = mysqli_fetch_array($result2)) {

                $user = $row["nickname"];
                $_SESSION["usuario"] = $user;
                $_SESSION["error"] = NULL;
                echo "<script>window.location.replace('../index.php')</script>";
            }
                   

        } else {
            echo "<script>
            Swal.fire(
            'Erro ao Logar',
            'Pessoa Desativada',
            'error'
            ).then(() => {
            window.history.back();
            });
            </script>";
        }
    } else {
        $query3 = "SELECT * FROM admin WHERE BINARY usera = '$user' AND BINARY senhaa = '$senha'";
        $result3 = mysqli_query($mysqli, $query3);
        $total3 = mysqli_num_rows($result3);
        if ($total3 > 0) {

            $query4 = "SELECT * FROM admin WHERE BINARY usera = '$user' AND BINARY senhaa = '$senha' AND (bloqueioadm IS NULL or bloqueioadm =false)";
            $result4 = mysqli_query($mysqli, $query4);
            $total4 = mysqli_num_rows($result4);
            if ($total4 > 0) {
                while ($row = mysqli_fetch_array($result4)) {
                    $user = $row["usera"];
                    $_SESSION["admin"] = $user;
                    $_SESSION["error"] = NULL;
                    echo "<script>window.location.replace('../index.php')</script>";
                }
            } else {

                echo "<script>
            Swal.fire(
            'Erro ao Logar',
            'Admin Desativado',
            'error'
            ).then(() => {
            window.history.back();
            });
            </script>";
            }
        } else {
            echo "<script>
                    Swal.fire(
                    'Erro ao Logar',
                    'Login ou senha errada',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
        }
    }
}
//echo "<BR>user: " . $user . "<BR>Senha: " . $senha;
?>