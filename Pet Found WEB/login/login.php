<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="logcad.css">
    <link rel="stylesheet" href="../lib/padrao.css">
    <title>Pet Found - Login</title>
</head>



<body>

    <div class="main-login">
        <div class="left-login">
            <a href="../index.php">
                <h4 class="inicio">◄ Página Inicial</h4>
            </a>
            <h1 class="frase">Faça login</h1>
            <h1> E entre para o nosso time</h1>
            <img src="../assets/logo.png" class="left-login-img" alt="doge">
            <BR></BR>
            <h1 class="pet">PetFound</h1>
        </div>

        <div class="right-login">
            <div class="card-log">
                <h1>Login</h1>
                <form method="post" action="./autentica.php">
                    <div class="textfield-log">
                        <label class="usuario" for="usuario">Usuário</label>
                        <input type="text" name="usuario" placeholder="Usuário">
                    </div>

                    <div class="textfield-log">
                        <label class="senha" for="senha">Senha</label>
                        <input type="password" name="senha" id="senha" placeholder="Senha">
                    </div>

                    <img src="../assets/ocultar.png" id="ms" class="mose" onclick="mostrarOcultarSenha(); trocarImagem();">
                    <script type="text/javascript" src="ocultasenha.js"></script>
                    <br>
                    <a href="esqueci.php" class="frase">
                        Esqueceu a senha?
                    </a>

                    <input class="btn-login" type="submit" value="Login" />
                </form>
                <h1 class="frase">Não entrou para o time ainda?</h1>
                <a href="escolha.php" class="frase">
                    Cadastre-se aqui!
                </a>
            </div>
        </div>
    </div>
</body>

</html>

