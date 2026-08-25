<html>

<head>

</head>

<body bgcolor="#ff6600">

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>

<?php
include('../lib/dbconnect.php');

session_start();
$login =  $_SESSION["Recupera"];
echo $login . " " . $_SESSION["Recupera"] . "a";
$senha_nova = $_POST['senha_nova'];
$confirme_senha = $_POST['confirme_senha'];



if (($senha_nova == "") && ($confirme_senha == "")) {
    echo "<script>
                    Swal.fire(
                    'Erro na Alteração de senha',
                    'Os campos das senhas não podem ser Nulos!',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
    return false;
} else {
    if (($senha_nova != $confirme_senha)) {
        echo "<script>
                    Swal.fire(
                    'Erro na Alteração de senha',
                    'As senhas não coincindem',
                    'error'
                    ).then(() => {
                        window.history.back();
                    });
                    </script>";
    } else {
        $query = "update pessoa set senha='$confirme_senha' where emailp='$login'";
        $result = mysqli_query($mysqli, $query);

        if ($result) {
            echo "<script>
            	Swal.fire(
                	'Alteração de senha bem sucedida',
                	'Senha Alterada com Sucesso!',
                	'success'
            		).then(() => {
                	window.location.href='login.php';
            		});
        		</script>";
        } else {
            echo "<script>
                    Swal.fire(
                    'Erro na Alteração de senha',
                    'Senha não alterada!',
                    'error'
                    ).then(() => {
                        window.history.back();
                    });
                    </script>";
        }
    }
}

?>