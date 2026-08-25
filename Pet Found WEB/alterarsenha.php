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

session_start();
if (isset($_SESSION["usuario"])) {

    $login =  $_SESSION["usuario"];
    $query = "select senha from pessoa where nickname='$login' ";
    $result = mysqli_query($mysqli, $query);
    $row = mysqli_fetch_assoc($result);
    $senha_banco = $row["senha"];

    $senha_nova = $_POST['senha_nova'];
    $confirme_senha = $_POST['confirme_senha'];
    $senha = $_POST['senha_atual'];


    if (($senha_nova == "") && ($confirme_senha == "") && ($senha_banco == "")) {
        echo "<script>
                    Swal.fire(
                    'Erro na Alteração de senha',
                    'Os campos das senhas não podem ser Nulos!',
                    'error'
                    ).then(() => {
                    window.location.href='formredsenha.php';
                    });
                    </script>";
        return false;
    } else {
        if (($senha != $senha_banco)) {
            echo "<script>
                    Swal.fire(
                    'Erro na Alteração de senha',
                    'A senha atual está incorreta',
                    'error'
                    ).then(() => {
                    window.location.href='formredsenha.php';
                    });
                    </script>";
        } else {
            if (($senha_nova != $confirme_senha)) {
                echo "<script>
                    Swal.fire(
                    'Erro na Alteração de senha',
                    'As senhas não coincindem',
                    'error'
                    ).then(() => {
                    window.location.href='formredsenha.php';
                    });
                    </script>";
            } else {
                if (strlen($confirme_senha) <= 25) {
                    $query = "update pessoa set senha='$confirme_senha' where nickname='$login'";
                    $result = mysqli_query($mysqli, $query);

                    if ($result) {
                        echo "<script>
            	Swal.fire(
                	'Alteração de senha bem sucedida',
                	'Senha Alterada com Sucesso!',
                	'success'
            		).then(() => {
                	window.location.href='index.php';
            		});
        		</script>";
                    } else {
                        echo "<script>
                    Swal.fire(
                    'Erro na Alteração de senha',
                    'Senha não alterada!',
                    'error'
                    ).then(() => {
                    window.location.href='formredsenha.php';
                    });
                    </script>";
                    }
                } else {
                    echo "<script>
                    Swal.fire(
                    'Excedência de Caráter', 
                    'Você ultrapassou o limite de caracteres. Senha só pode ter 25.',
                    'error'
                    ).then(() => {
                        window.history.back();
                    });
                    </script>";
                }
            }
        }
    }
} else if (isset($_SESSION["admin"])) {

    $login =  $_SESSION["admin"];
    $query = "select senhaa from admin where usera='$login' ";
    $result = mysqli_query($mysqli, $query);
    $row = mysqli_fetch_assoc($result);
    $senha_banco = $row["senhaa"];

    $senha_nova = $_POST['senha_nova'];
    $confirme_senha = $_POST['confirme_senha'];
    $senha = $_POST['senha_atual'];


    if (($senha_nova == "") && ($confirme_senha == "") && ($senha_banco == "")) {
        echo "<script>
                    Swal.fire(
                    'Erro na Alteração de senha',
                    'Os campos das senhas não podem ser Nulos!',
                    'error'
                    ).then(() => {
                    window.location.href='formredsenha.php';
                    });
                    </script>";
        return false;
    } else {
        if (($senha != $senha_banco)) {
            echo "<script>
                    Swal.fire(
                    'Erro na Alteração de senha',
                    'A senha atual está incorreta',
                    'error'
                    ).then(() => {
                    window.location.href='formredsenha.php';
                    });
                    </script>";
        } else {
            if (($senha_nova != $confirme_senha)) {
                echo "<script>
                    Swal.fire(
                    'Erro na Alteração de senha',
                    'As senhas não coincindem',
                    'error'
                    ).then(() => {
                    window.location.href='formredsenha.php';
                    });
                    </script>";
            } else {
                if (strlen($confirme_senha) <= 25) {
                    $query = "update admin set senhaa='$confirme_senha' where usera='$login'";
                    $result = mysqli_query($mysqli, $query);

                    if ($result) {
                        echo "<script>
            	Swal.fire(
                	'Alteração de senha bem sucedida',
                	'Senha Alterada com Sucesso!',
                	'success'
            		).then(() => {
                	window.location.href='index.php';
            		});
        		</script>";
                    } else {
                        echo "<script>
                    Swal.fire(
                    'Erro na Alteração de senha',
                    'Senha não alterada!',
                    'error'
                    ).then(() => {
                    window.location.href='formredsenha.php';
                    });
                    </script>";
                    }
                } else {
                    echo "<script>
                    Swal.fire(
                    'Excedência de Caráter', 
                    'Você ultrapassou o limite de caracteres. Senha só pode ter 25.',
                    'error'
                    ).then(() => {
                        window.history.back();
                    });
                    </script>";
                }
            }
        }
    }
}
?>