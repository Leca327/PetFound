<html>

<head>
    <!--Roboto font family-->
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400&family=Roboto:wght@100&family=Water+Brush&display=swap" rel="stylesheet">
    <!-- -->
    <link rel="stylesheet" href="menu.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>

</head>

<body bgcolor="#ff6600">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <?php

    /*function verificarTokenJWT($token) {
    global $chaveSecreta;
    
    try {
        $_SESSION['dadosToken'] = JWT::decode($token, $chaveSecreta, array('HS256'));
        
    } catch (Exception $e) {
        return false;
    }
}

// Verificar se o token foi fornecido
if (isset($_SESSION['dadosToken'])) {
    $token =$_SESSION['dadosToken'];
    
    // Verificar a validade do token
    $dadosToken = verificarTokenJWT($token);
    
    if ($dadosToken) {
        // Token válido, redirecionar para formredsenha.php
        header("Location: formredsenha.php?token=" . urlencode($token));
        exit();
    } else {
        // Token inválido, exibir mensagem de erro
        echo "Token inválido!";
        exit();
    }
} else {
    // Token não fornecido, exibir mensagem de erro
    echo "Token não fornecido!";
    echo "Esta assim: ".$_SESSION['dadosToken'];
    exit();
}*/

    use PHPMailer\PHPMailer\PHPMailer;
    use PHPMailer\PHPMailer\SMTP;
    use PHPMailer\PHPMailer\Exception;

    require("./phpm/PHPMailer.php");
    require("./phpm/SMTP.php");
    require("./phpm/Exception.php");

    $mail = new PHPMailer(true);
    $mail->isSMTP();
    $mail->Host = 'smtp.office365.com';
    $mail->Port       = 587;
    $mail->SMTPSecure = 'tls';
    $mail->SMTPAuth   = true;
    $mail->Username = 'petfound302@outlook.com';
    $mail->Password = 'petFound1302#';
    $mail->SetFrom('petfound302@outlook.com', 'PetFound');
    //$mail->SMTPDebug  = 1;
    //$mail->Debugoutput = function($str, $level) {echo "debug level $level; message: $str";}; //$mail->Debugoutput = 'echo';
    $mail->IsHTML(true);
    // Configuração para TLSv1.2
    $mail->SMTPOptions = array(
        'ssl' => array(
            'protocols' => 'TLSv1.2',
        ),
    );
    include('../lib/dbconnect.php');

    $email = $_POST["em"];
    //echo "Conteudo da vari&aacutevel e-mail &eacute " . $email;

    $mysqli = new mysqli($hostname, $username, $password, $dbname);

    if (!$mysqli) {
        echo "Error: Falha ao conectar-se com o banco de dados MySQL." . PHP_EOL;
        echo "Debugging errno: " . mysqli_connect_errno() . PHP_EOL;
        echo "Debugging error: " . mysqli_connect_error() . PHP_EOL;
        exit;
    } else {
        //echo "<BR><BR>Conexão realizada com sucesso!<BR>";
    }

    #Realizando o Select (projeção) no banco de dados
    $query = "SELECT * FROM pessoa where emailp='$email'";
    session_start();
    $result = mysqli_query($mysqli,    $query);
    $total = mysqli_num_rows($result);
    if ($total == 0) {
        echo "<script>
            Swal.fire(
                'Error ao enviar e-mail',
                'O e-mail não está cadastrado no nosso sistema',
                'error'
            ).then(() => {
                window.location.href='esqueci.php';
            });
        </script>";
        exit();
    }
    if ($result) {
        $_SESSION["Recupera"] = $email;

        while ($row = mysqli_fetch_array($result)) {
            $nick = $row['nickname'];
            //verifica se o não houve registros retornados 
            $LINK = "localhost/10-06/Pet Found/formredsenhaesqueci.php";
            //?token=".$_SESSION['dadosToken'];
            $mail->addAddress($email, 'ToEmail');
            $senha = $row['senha'];
            $mail->Subject = 'Recupera&ccedil;&atilde;o de senha';
            $mail->Body    = "<B>Prezado usu&aacute;rio " . $nick . "<br>Abaixo segue o link para redefinir a senha:<BR> <a href='" . $LINK . "'>Clique aqui</a> " .
                 "<BR>Com meus cumprimentos!";
            //$mail->AltBody = 'This is the body in plain text for non-HTML mail clients';
            if (!$mail->send()) {

                echo 'ERRO! Mensagem não pode ser enviada.';
                echo 'Mensagem de ERRO: ' . $mail->ErrorInfo;
            } else {
                echo "<script>
                        Swal.fire(
                            'Olhe seu e-mail',
                            'Um e-mail foi enviado para recuperação de senha',
                            'success'
                        ).then(() => {
                            window.location.href='login.php';
                        });
                    </script>";
            }
        }
    }
    ?>

</body>

</html>