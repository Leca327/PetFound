<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="logcad.css">
    <link rel="stylesheet" href="../lib/padrao.css">
    <title>Pet Found - Cadastro</title>

    <script>
        function voltarPagina() {
            window.history.back();
        }
    </script>

</head>

<body>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <?php
    session_start();
    if (!empty($_SESSION["usuario"])) {
        echo "<script>
                        Swal.fire(
                        'Logado',  
                        'Deslogue para cadastrar uma conta',
                        'error'
                        ).then(() => {
                        window.location.href='../index.php';
                        });
                        </script>";
    } else if (!empty($_SESSION["admin"])) {
        echo "<script>
                        Swal.fire(
                        'Logado como Admin',  
                        'Deslogue para cadastrar uma conta',
                        'error'
                        ).then(() => {
                        window.location.href='../index.php';
                        });
                        </script>";
    } else {
    }
    ?>

    <div class="main-login">
        <div class="left-login">
            <a href="javascript:void(0);" onclick="voltarPagina();">
                <h4 class="inicio">◄ Voltar</h4>
            </a>
            <h1 class="frase">Faça o Cadastro</h1>
            <h1> E entre para o nosso time</h1>
            <img src="../assets/logo.png" class="left-login-img" alt="doge">
            <BR></BR>
            <h1 class="pet">PetFound</h1>
        </div>
        <div class="right-login">
            <div class="card-login">

                <h1>Cadastro para?</h1>
                <a href="singup.php">
                    <button class="btn-fisica">Pessoa Física</button>
                </a>

                <a href="singupong.php">
                    <button class="btn-ong">Pessoa Jurídica</button>
                </a>
                <a class="frase" href="login.php">

                    <h4>Já sou cadastrado</h4>
                </a>
            </div>



        </div>



    </div>
</body>

</html>