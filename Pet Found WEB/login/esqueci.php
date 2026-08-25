<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="logcad.css">
    <link rel="stylesheet" href="../lib/padrao.css">
    <title>Pet Found - Recupere a Senha</title>
</head>


<body>

    <div class="main-login">
        <div class="left-login">
            <a href="../index.php">
                <h4 class="inicio">◄ Início</h4>
            </a>
            <h1 class="frase">Recupere sua senha</h1>
            <h1> Estamos aqui para te ajudar</h1>
            <img src="../assets/logo.png" class="left-login-img" alt="doge">
            <BR></BR>
            <h1 class="pet">PetFound</h1>
        </div>
        
        <div class="right-login">
            <div class="card-login">
                <h1>Recuperar senha</h1>
                <form id="recemail" method="post" action="recupera.php">
                <?php
                /*
                        require_once '../../vendor/autoload.php';

                        use \Firebase\JWT\JWT;
                        
                        $chaveSecreta = 'minha_chave_secreta';
                        
                        function gerarTokenJWT($usuarioId) {
                            global $chaveSecreta;
                            
                            $dadosToken = array(
                                'sub' => $usuarioId,
                                'iat' => time(),
                                'exp' => time() + (60 * 60) // Token válido por 1 hora
                            );
                            
                            $token = JWT::encode($dadosToken, $chaveSecreta);
                            
                            return $token;
                        }
                        
                        function verificarTokenJWT($token) {
                            global $chaveSecreta;
                            
                            try {
                                $_SESSION['dadosToken'] = JWT::decode($token, $chaveSecreta, array('HS256'));
                                return $dadosToken;
                            } catch (Exception $e) {
                                return false;
                            }
                        }
                        */
                        ?>

                    <div class="textfield">
                        <label class="usuario" for="em">E-mail</label>
                        <input type="email" name="em" placeholder="email" required>
                    </div>
                    <input class="btn-login" type="submit" value="Enviar" />
                    <a href="login.php" class="frase">Voltar ao login</a>
                </form>
            </div>
        </div>
    </div>
</body>
<?php
   
    session_start();
    echo "teste sessão:".$_SESSION["error"];
    if(isset($_SESSION["error"])){
        echo "<BR><BR><div class='alert alert-danger' role='alert'>
			<h3>Usuário não cadastrado em nossa base de dados!</h3>
		  </div>";
          $_SESSION["error"]=NULL;
    }
    ?>

