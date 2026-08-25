

<head>
    <title>Pet Found - Redefina Senha</title>
    <link rel="stylesheet" href="./login/logcad.css">
    <link rel="stylesheet" href="./lib/padrao.css">
</head>

<body>

    <div class="main-login">
        <div class="left-login">
            <a href="./index.php">
                <h4 class="inicio">◄ Início</h4>
            </a>
            <h1 class="frase">Altere sua senha</h1>
            <h1> E mantenha seus dados protegidos</h1>
            <img src="./assets/logo.png" class="left-login-img" alt="doge">
            <br><br>
            <h1 class="pet">PetFound</h1>
        </div>
        <div class="right-login">

            <br>
            <div class="right-login">
                <div class="card-log">
                    <h1 class="titulo">Alteração da Senha</h1>
                    <form action="./login/alterarsenhaesqueci.php" method="post">
                        
                        <div class="textfield-log">
                            <label class="usuario" for="senha_nova"></label>
                            <input type="password" name="senha_nova" placeholder="Senha Nova" required />
                        </div>
                        <div class="textfield-log">
                            <label class="usuario" for="confirme_senha"></label>
                            <input type="password" name="confirme_senha" placeholder="Confirmação da Senha Nova" required />
                        </div>
                        <input type="submit" class="btn-login" name="alterar_bt" value="Alterar" />
                </div>
                </form>
            </div>
        </div>
</body>
