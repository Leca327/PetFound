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
include('../../lib/dbconnect.php');

if (isset($_POST['notifications_name']) && isset($_POST['mensagem']) && isset($_POST['user_nick'])) {
    $notifications_name = $_POST["notifications_name"];
    $mensagem = $_POST["mensagem"];
    $user_nick = $_POST["user_nick"];
    $hora_atual = date("Hi");
    $data_atual = date("Ymd");

    if (strlen($mensagem) <= 255 && strlen($notifications_name) <= 50) {

        $comprimento = strlen($notifications_name);
        $primeira_letra = substr($notifications_name, 0, 1);
        $letra_do_meio = substr($notifications_name, round($comprimento / 2) - 1, 1);
        $ultima_letra = substr($notifications_name, -1);
        $imf = $primeira_letra . $letra_do_meio . $ultima_letra;

        $find_pessoa = "SELECT pcod FROM pessoa WHERE nickname = '" . $user_nick . "'";
        $result = mysqli_query($mysqli, $find_pessoa);

        if ($result) {
            $row = mysqli_fetch_assoc($result);
            if ($row) {
                $pcod = $row['pcod'];
                $cortecod = substr($pcod, 0, 5);

                $cod = "NOT" . $cortecod . $hora_atual . $imf . $data_atual;
                $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                $insert_query = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$pcod','$dtatual','$hratual')";

                $result = mysqli_query($mysqli, $insert_query);

                if ($result) {
                    $total = mysqli_affected_rows($mysqli);
                    if ($total > 0) {
                        echo "<script>
            Swal.fire(
                'Notificação',
                    'Notificação enviada',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                    } else {
                        echo "<script>
                    Swal.fire(
                    'Notificação não enviada',
                    '" . mysqli_error($mysqli) . "',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else {
                    echo "Erro na consulta: " . mysqli_error($mysqli);
                }
            } else {
                echo "<script>
                    Swal.fire(
                    'Erro ao enviar Notificação',
                    'Nickname de Pessoa não existe',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
            }
        } else {
            echo "<script>
                    Swal.fire(
                    'Notificação não enviada',
                    '" . mysqli_error($mysqli) . "',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
        }
    } else {
        echo "<script>
                        Swal.fire(
                        'Excedência de Caráter', 
                        'Você ultrapassou o limite de caracteres. Mensagem só pode ter 255 e Título da Notificação só pode ter 50',
                        'error'
                        ).then(() => {
                            window.history.back();
                        });
                        </script>";
    }
} else {
    echo "<script>
                    Swal.fire(
                    'Erro ao enviar Notificação',
                    'Todos os Campos Precisam Ser Preenchidos',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
} ?>