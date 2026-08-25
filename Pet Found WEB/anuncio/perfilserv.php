<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Perfil do Pet</title>
    <link rel="stylesheet" href="../lib/padrao.css">
    <link rel="stylesheet" href="../lib/footer.css">
    <link rel="stylesheet" href="../lib/navbar.css">
    <link rel="stylesheet" href="../lib/responsivonavbar.css">
    <link rel="stylesheet" href="perfilserv.css">
    <link rel="stylesheet" href=" ../cards.css">
    <link rel="stylesheet" href=" ../cards1.css">
    <link rel="stylesheet" href="coment.css">
    <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
    <link rel="stylesheet" href="estrela.css">

</head>

<body>
    <?php
    include('../lib/navbar.php');

    if (!empty($_GET['servcod'])) {

        include('../lib/dbconnect.php');
        $servcod = $_GET['servcod'];

        // Use prepared statements to prevent SQL injection
        $sqlSelect = "SELECT * FROM servico WHERE servcod = '" . $servcod . "'";
        $result = mysqli_query($mysqli, $sqlSelect);

        if ($result->num_rows > 0) {
            // Assuming 'nomepet' is a column in the 'pet' table
            $row = $result->fetch_assoc();

            $nome = $row['nomeserv'];
            $data = $row['dts'];
            $dataObj = date_create($data);
            $dtf = date_format($dataObj, "d/m/Y");
            $dst = $row['bloqueioserv'];
            $apv = $row['aprovacaoserv'];
            $descpet = $row['descserv'];
            $pessoa = $row['pessoa_codp'];
            $precoserv = number_format((float)$row['preco'], 2, ',', '');
            $atvo = $row['bloqueioserv'];

            if ($atvo == true) {
                $atvmsg = "Ativar";
            } else if ($atvo == false || $atvo == null) {
                $atvmsg = "Desativar";
            }

            if ($precoserv == "0,00" || $precoserv == "0,0") {
                $precoserv = "Gratuito";
              } else {
                $precoserv = "R$" . $precoserv;
              }

            $sql_imagem = "SELECT * FROM imagem WHERE servicocodserv='$servcod';";
            $result_imagem = $mysqli->query($sql_imagem);

            if ($row_imagem = mysqli_fetch_assoc($result_imagem)) {
                $imageData = $row_imagem['img'];
                $imageType = 'image/jpeg';
            } else {
                // Defina uma imagem padrão caso nenhuma imagem seja encontrada
                $imagePath = '../assets/semimg.png';
                $imageData = file_get_contents($imagePath);
                $imageType = mime_content_type($imagePath);
            }

            $sqlSelect2 = "SELECT * FROM pessoa WHERE pcod = '" . $pessoa . "'";
            $result2 = mysqli_query($mysqli, $sqlSelect2);

            if ($result2->num_rows > 0) {
                $row2 = $result2->fetch_assoc();
                $nickp = $row2['nickname'];
                $contato = $row2['contatop'];
                $primeirosCaracteres = substr($pessoa, 0, 2);

                // Verificar se os 2 primeiros caracteres são iguais a "pf"
                if ($primeirosCaracteres === "PF") {
                    $sqlSelect3 = "SELECT * FROM fisica WHERE codp = '" . $pessoa . "'";
                    $result3 = mysqli_query($mysqli, $sqlSelect3);

                    if ($result3->num_rows > 0) {
                        $row3 = $result3->fetch_assoc();
                        $sx = $row3['sexo'];
                        if ($sx === "M") {
                            $msg = "Publicado por ";
                        } else if ($sx === "F") {
                            $msg = "Publicado pela ";
                        } else if ($sx === "O") {
                            $msg = "Publicado pela pessoa ";
                        } else {
                            $msg = "Publicado pela pessoa XXXX";
                        }
                    }
                } elseif ($primeirosCaracteres === "PJ") {
                    $sqlSelect4 = "SELECT * FROM juridica WHERE cod_p = '" . $pessoa . "'";
                    $result4 = mysqli_query($mysqli, $sqlSelect4);

                    if ($result4->num_rows > 0) {
                        $row4 = $result4->fetch_assoc();
                        $tp = $row4['tipoj'];
                        if ($tp === "Empresa") {
                            $msg = "Publicado pela Empresa ";
                        } else if ($tp === "ONG") {
                            $msg = "Publicado pela ONG ";
                        }
                    } else {
                        // Caso não encontre na tabela juridica, você pode definir uma mensagem padrão
                        $msg = "Publicado pela empresa/ong ";
                    }
                }
            } else {
                echo "Nenhum pessoa encontrado com esse código.";
            }
        } else {
            echo "Nenhum Serviço encontrado com esse código.";
        }
    }
    ?>

    <div class="left-side">
        <a href="../index.php">
            <h6 class="road">Home ></h6>
        </a>
        <a href="../cadpetserv/searchserv.php">
            <h6 class="road2">Quero Contratar ></h6>
        </a>
        <?php echo "
        <h6 class='road3'>" . $nome . " </h6> 

        <img class='imgpet' src='data:$imageType;base64," . base64_encode($imageData) . "' alt='Serviço Image'>
        <h6 class='anunc2'> Não perca a Oportunidade </h6>
     <br><br>
        "
        ?>
    </div>
    <div class="right-side">
        <?php
        $sql = "SELECT AVG(avaliacao) AS media_avaliacao FROM contatoserv WHERE contratou = true AND avaliacao IS NOT NULL AND comentario IS NOT NULL  AND scodserv = '" . $servcod . "';";

        $result = $mysqli->query($sql);
        $counter = 0; // Variável para contar as iterações
        if ($result) {
            $pet_data = mysqli_fetch_assoc($result);
            $avali = $pet_data['media_avaliacao'];

            // Verifique se a parte decimal é igual a 0.5
            $decimal_part = $avali - floor($avali);
            if ($decimal_part == 0.5) {
                // Arredonde para cima, adicionando 1
                $avali = floor($avali);
            }

            echo "<ul id='comments-list' class='comments-list'>
                <li>
                    <div class='rating2'>
                        <input type='number' name='rating' value='$avali' hidden>";
            for ($i = 0; $i < 5; $i++) {
                if ($avali >= 2) {
                    // Exibe um osso inteiro
                    echo "<i class='bx bxs-bone bone' style='--i: " . $i . ";'></i>";
                    $avali -= 2; // Reduz a avaliação em 2 para contar o osso inteiro
                } elseif ($avali == 1) {
                    // Exibe um meio osso
                    echo "<i class='bx bx-bone bone half' style='--i: " . $i . ";'></i>";
                    $avali--; // Reduz a avaliação para que o próximo seja vazio
                } else {
                    // Output um osso vazio
                    echo "<i class='bx bx-bone bone empty' style='--i: " . $i . ";'></i>";
                }
            }

            echo "</div>"; // Feche o contêiner dos comentários fora do loop
            echo "</ul>";
        }


        echo "
        <h1 class='nomepet'> " . $nome . "</h1>
        <h6 class='infopet'>" . $precoserv .  "</h6>
        <h6 class='infopet2'>" . $msg . "<a href='../perfil.php?pcod=$pessoa'>" . $nickp . "</a> em " . $dtf . "</h6>
        <h6 class='descpet'> Detalhes sobre o Serviço</h6>
        <h6 class='descpet2'> " . $descpet . "</h6>";


        if (isset($_SESSION['admin'])) {
            /*  echo "<button class='adtbttndono'>Editar Pet</button>
                        <button class='adtbttndono'>Deletar</button>
                <h6 class='anunc'> Se interessou? Crie um Contato</h6>";*/
        } else if (isset($_SESSION['usuario'])) {
            if ($apv != true && isset($apv)) {
                if ($nickp === $_SESSION['usuario']) {
                    echo "<button class='adtbttncont' >Serviço Reprovado.</button>
            <h6 class='anunc'> Edite as informações para reavaliar-mos. O motivo está no seu painel.</h6>";
                } else {
                    echo "<script>window.history.back();</script>";
                }
            } else if (empty($apv)) {
                if ($nickp === $_SESSION['usuario']) {
                    echo "<button class='adtbttncont' >Pet em Análise.</button>
            <h6 class='anunc'> Espere até analisarmos seu anuncio</h6>";
                } else {
                    echo "<script>window.history.back();</script>";
                }
            } else {
                if ($dst == true) {
                    if ($pessoa === $_SESSION['usuario']) {
                        echo "<button class='adtbttncont' >Serviço desativado.</button>
                <h6 class='anunc'> Volte quando o anuncio for ativado</h6>";
                    } else {
                        echo "<script>window.history.back();</script>";
                    }
                } else {


                    $sqlSelect = "SELECT * FROM pessoa WHERE nickname = '" . $_SESSION['usuario'] . "'";
                    $result = mysqli_query($mysqli, $sqlSelect);

                    //<button class='adtbttn' onclick='onContatoButtonClick()'>Entrar em contato</button>
                    //<h6 class='anunc'> " . $anunc . "</h6>

                    if ($result->num_rows > 0) {
                        // Assuming 'nomepet' is a column in the 'pet' table
                        $row = $result->fetch_assoc();
                        $codpes = $row['pcod'];
                        $query = "SELECT * FROM `contatoserv` WHERE pcodp = '" . $codpes . "' AND scodserv= '" . $servcod . "'";
                        $result = mysqli_query($mysqli, $query);
                        if ($row = mysqli_fetch_assoc($result)) {
                            $query = "SELECT * FROM contatoserv WHERE pcodp = '$codpes' AND scodserv = '$servcod' AND contratou = true AND (avaliacao = false OR avaliacao IS NULL)";
                            $result = mysqli_query($mysqli, $query);
                            if ($row = mysqli_fetch_assoc($result)) {
                                echo "<button class='adtbttncont'>Contato Já foi Criado!</button>                    
                            <button class='adtbttnoff' onclick='openAvaliarPopup()'>Avaliar</button>     
                    ";
                            } else {
                                echo "<button class='adtbttncont'>Contato Já foi Criado!</button>  
                                    
                        ";
                            }
                        } else {
                            $query = "SELECT pcod FROM pessoa WHERE nickname = '" . $_SESSION['usuario'] . "'";
                            $result = mysqli_query($mysqli, $query);
                            if ($row = mysqli_fetch_assoc($result)) {

                                if ($nickp === $_SESSION['usuario']) {
                                    echo "<button class='adtbttndono'><a href='../editarserv.php?codigo=" . $servcod . "'>Editar Serviço</a></button>
                                    <form action='../3editserv.php' method='post'>
                                        <input type='hidden' name='petcod' value='" . $servcod . "' required />
                                        <input type='hidden' name='atv' value='" . $atvo . "' required />  
                                        <input type='hidden' name='nome' value='" . $nome . "' required />  
                                        <button type='submit' name='dst-btn' class='adtbttndono' >  " . $atvmsg . "</button>
                                    </form>
                    <h6 class='anunc'> Se interessou? Crie um Contato</h6>";
                                } else {

                                    echo "<button class='adtbttnoff' onclick='onContatoButtonClick()'>Entrar em contato</button>
                                <h6 class='anunc'> Se interessou? Crie um Contato</h6>";
                                }
                            }
                        }
                    }
                }
            }
        } else {
            echo "<button class='adtbttn' onclick='paglogin()'>Logar para Criar Contato.</button>
                            <h6 class='anunc'> Se interessou? Crie um Contato</h6>";
        }
        ?>

    </div>

    <div id="popup2" class="popup2">
        <div class="popup-content">
            <?php
            // Caminho do primeiro arquivo
            $caminhoArquivo1 = '../set-up/contatoserv.php';

            // Caminho do segundo arquivo
            $caminhoArquivo2 = '../contatoserv.php';

            // Caminho do terceiro arquivo
            $caminhoArquivo3 = '../../contatoserv.php';


            // Verifica se o primeiro arquivo existe
            if (file_exists($caminhoArquivo1)) {
                include($caminhoArquivo1);
            } else if (file_exists($caminhoArquivo2)) {
                // Se o primeiro arquivo não existir, inclui o segundo arquivo
                include($caminhoArquivo2);
            } else {
                // Se o segundo arquivo não existir, inclui o terceiro arquivo
                include($caminhoArquivo3);
            }
            ?>

        </div>
    </div>

    <div id="avaliar-popup" style="display: none;">
        <div class="popup-content">
            <?php
            // Caminho do primeiro arquivo
            $caminhoArquivo4 = '../set-up/avaliar.php';

            // Caminho do segundo arquivo
            $caminhoArquivo5 = '../avaliar.php';

            // Caminho do terceiro arquivo
            $caminhoArquivo6 = '../../avaliar.php';


            // Verifica se o primeiro arquivo existe
            if (file_exists($caminhoArquivo4)) {
                include($caminhoArquivo4);
            } else if (file_exists($caminhoArquivo5)) {
                // Se o primeiro arquivo não existir, inclui o segundo arquivo
                include($caminhoArquivo5);
            } else {
                // Se o segundo arquivo não existir, inclui o terceiro arquivo
                include($caminhoArquivo6);
            }
            ?>

        </div>
    </div>

    <br><br>

    <h1 class="hnvpets">Avaliações do Serviço</h1>
    <div class="adocao">
        <?php
        $sql = "SELECT * FROM contatoserv WHERE contratou = true AND avaliacao IS NOT NULL AND comentario IS NOT NULL AND scodserv = '" . $servcod . "';";

        $result = $mysqli->query($sql);
        $counter = 0; // Variável para contar as iterações
        if ($result) {
            echo "<div class='comments-container'>"; // Adicione a margem inferior para criar a distância
            while ($pet_data = mysqli_fetch_assoc($result)) {
                $petcodpet = isset($pet_data['petcod']) ? $pet_data['petcod'] : ''; // Verifique se a chave 'petcod' existe
                $codpessoa = $pet_data['pcodp'];
                $sql_imagem = "SELECT * FROM pessoa WHERE pcod='$codpessoa';";
                $result_imagem = $mysqli->query($sql_imagem);

                if ($row_imagem = mysqli_fetch_assoc($result_imagem)) {
                    $namepess = $row_imagem['nickname'];
                    if ($row_imagem['imgperfil'] != null) {
                        $imageData = $row_imagem['imgperfil'];
                        $imageType = 'image/jpeg';
                    } else {
                        // Defina uma imagem padrão caso nenhuma imagem seja encontrada
                        $imagePath = '../assets/semimg.png';
                        $imageData = file_get_contents($imagePath);
                        $imageType = mime_content_type($imagePath);
                    }
                } else {
                    echo "Não encontrou o contratante";
                }

                $avali = $pet_data['avaliacao'];
                $coment = $pet_data['comentario'];
                $dttemp = $pet_data['dtaval'];
                $hrtemp = $pet_data['hraval'];

                // Converter a data e hora armazenadas em timestamps
                $timestamp_dttemp = strtotime($dttemp . ' ' . $hrtemp);
                $timestamp_atual = time();

                // Calcular a diferença em segundos
                $diferenca_segundos = $timestamp_atual - $timestamp_dttemp;

                if ($diferenca_segundos < 60) {
                    $tempo = "Há " . $diferenca_segundos . " segundo(s) atrás";
                } elseif ($diferenca_segundos < 3600) {
                    $minutos = floor($diferenca_segundos / 60);
                    $tempo = "Há " . $minutos . " minuto(s) atrás";
                } elseif ($diferenca_segundos < 86400) {
                    $horas = floor($diferenca_segundos / 3600);
                    $tempo = "Há " . $horas . " hora(s) atrás";
                } elseif ($diferenca_segundos < 604800) { // Menos de 7 dias (1 semana)
                    $dias = floor($diferenca_segundos / 86400);
                    $tempo = "Há " . $dias . " dia(s) atrás";
                } else {
                    // Se passar de 7 dias, formate a data e hora completa
                    $tempo = date('d/m/Y H:i', $timestamp_dttemp);
                }

                $dtcontr = $pet_data['dtfinalcs'];
                $hrcontr = $pet_data['hrfinalcs'];

                $timestamp_dttemp = strtotime($dtcontr . ' ' . $hrcontr);
                $timestamp_atual = time();

                // Calcular a diferença em segundos
                $diferenca_segundos = $timestamp_atual - $timestamp_dttemp;

                if ($diferenca_segundos < 60) {
                    $tempo2 = " Contratou há " . $diferenca_segundos . " segundo(s) atrás";
                } elseif ($diferenca_segundos < 3600) {
                    $minutos = floor($diferenca_segundos / 60);
                    $tempo2 = " Contratou há " . $minutos . " minuto(s) atrás";
                } elseif ($diferenca_segundos < 86400) {
                    $horas = floor($diferenca_segundos / 3600);
                    $tempo2 = " Contratou há " . $horas . " hora(s) atrás";
                } elseif ($diferenca_segundos < 604800) { // Menos de 7 dias (1 semana)
                    $dias = floor($diferenca_segundos / 86400);
                    $tempo2 = " Contratou há " . $dias . " dia(s) atrás";
                } else {
                    // Se passar de 7 dias, formate a data e hora completa
                    $tempo2 = " Contratou desde " . date('d/m/Y H:i', $timestamp_dttemp);
                }

                echo "<ul id='comments-list' class='comments-list'>
              <li>
                  <div class='rating1'>
                      <input type='number' name='rating' value='$avali' hidden>";
                for ($i = 0; $i < 5; $i++) {
                    if ($avali >= 2) {
                        // Exibe um osso inteiro
                        echo "<i class='bx bxs-bone bone'  style='--i: " . $i . ";'></i>";
                        $avali -= 2; // Reduz a avaliação em 2 para contar o osso inteiro
                    } elseif ($avali == 1) {
                        // Exibe um meio osso
                        echo "<i class='bx bx-bone bone half' style='--i: " . $i . ";'></i>";
                        $avali--; // Reduz a avaliação para que o próximo seja vazio
                    } else {
                        // Output um osso vazio
                        echo "<i class='bx bx-bone bone empty' style='--i: " . $i . ";'></i>";
                    }
                }
                echo "</div>
                <!-- Avatar -->
              <div class='comment-avatar'><img src='data:$imageType;base64," . base64_encode($imageData) . "' alt=''></div>
              <!-- Contêiner de Comentário -->
              <div class='comment-box'>
                  <div class='comment-head'>
                      <h6 class='comment-name'><a href='../perfil.php?pcod=$codpessoa'>" . $namepess . "</a></h6>
                      <span>" . $tempo . "</span>                    
                  </div>
                  <div class='comment-content'>
                      " . $coment . "
                  </div>
              </div>
          </li>
      </ul>";

                $counter++; // Incrementa o contador

                if ($counter === 5) {
                    break;
                }
                echo "<br>";
            }

            echo "</div></div>"; // Feche o contêiner dos comentários fora do loop
        }

        if ($counter === 0) {
            echo "<button class='petlar2'>Sem Avaliações por aqui</button><br>";
        }
        ?>




        <h1 class="hnvpets2">Outros Serviços te esperam aqui!</h1>
        <div class="adocao">
            <?php

            $sql = "SELECT * FROM servico WHERE aprovacaoserv = true AND servcod <> '" . $servcod . "' ORDER BY RAND();";


            $result = $mysqli->query($sql);
            $counter = 0; // Variável para contar as iterações
            if ($result) {
                while ($pet_data = mysqli_fetch_assoc($result)) {
                    $petcodpet = $pet_data['servcod'];
                    $sql_imagem = "SELECT * FROM imagem WHERE servicocodserv='$petcodpet';";
                    $result_imagem = $mysqli->query($sql_imagem);

                    if ($row_imagem = mysqli_fetch_assoc($result_imagem)) {
                        $imageData = $row_imagem['img'];
                        $imageType = 'image/jpeg';
                    } else {
                        // Defina uma imagem padrão caso nenhuma imagem seja encontrada
                        $imagePath = '../assets/semimg.png';
                        $imageData = file_get_contents($imagePath);
                        $imageType = mime_content_type($imagePath);
                    }

                    echo '<div class="cards1">'; // Abre um card
                    echo '<h3 style="color: black;">' . $pet_data['nomeserv'] . '</h3>';
                    echo '<img class="imgcardpet" src="data:' . $imageType . ';base64,' . base64_encode($imageData) . '" alt="Pet Image">';
                    echo '<p class="title"></p>';
                    echo '<h3 style="color: black;">' . $pet_data['nomeserv'] . '</h3>';
                    echo '<h4 style="color: black;">' . $pet_data['estados'] . ", " . $pet_data['cidades'] . '</h4>';
                    echo '<h5 style="color: black;" class="price" data-price="' . $precoserv . '">R$' . $precoserv . '</h5>';
                    echo '<button class="butcardpet"><a href="../anuncio/perfilserv.php?servcod=' . $pet_data['servcod'] . '">Contratar</a></button>';
                    echo '</div>';

                    $counter++; // Incrementa o contador

                    if ($counter === 5) {
                        break;
                    }
                }
            }

            if ($counter === 0) {
                echo "<br><button class='petlar'>Sem mais Serviços por aqui</button><br><br><br><br><br><br>";
            }

            ?>
            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

            <script>
                function openAvaliarPopup() {
                    document.getElementById('avaliar-popup').style.display = 'block';
                    document.addEventListener('keydown', handleEscapeKeyAvaliar);
                }

                function closeAvaliarPopup() {
                    document.getElementById('avaliar-popup').style.display = 'none';
                    document.removeEventListener('keydown', handleEscapeKeyAvaliar);
                }

                function handleEscapeKeyAvaliar(event) {
                    if (event.key === 'Escape') {
                        closeAvaliarPopup();
                    }
                }
            </script>

            <script>
                function paglogin() {
                    window.location.href = "../login/login.php";
                }


                function openPopuppet() {
                    document.getElementById('popup2').style.display = 'block';
                    document.addEventListener('keydown', handleEscapeKey);

                }

                function closePopuppet() {
                    document.getElementById('popup2').style.display = 'none';
                    document.removeEventListener('keydown', handleEscapeKey);
                }

                function handleEscapeKey(event) {
                    if (event.key === 'Escape') {
                        closePopup();
                    }
                }


                function verificarLogin() {
                    // Verificar se a variável de sessão "usuario" possui um valor
                    var loggedIn = <?php echo isset($_SESSION["usuario"]) ? 'true' : 'false'; ?>;
                    // Verificar se a variável de sessão "admin" possui valor e é igual a true (administrador logado)
                    var isAdmin = <?php echo isset($_SESSION["admin"]) ? 'true' : 'false'; ?>;

                    return {
                        loggedIn,
                        isAdmin
                    };
                }

                function onContatoButtonClick() {
                    var {
                        loggedIn,
                        isAdmin
                    } = verificarLogin(); // Obter o status de login e se é administrador
                    if (loggedIn) {
                        openPopuppet();

                    } else if (isAdmin) {
                        Swal.fire(
                            'Erro no Contato',
                            'Você precisa ser um usuário, não um administrador, para entrar em contato.',
                            'error'
                        ).then(() => {
                            window.history.back();
                        });
                    } else {
                        Swal.fire(
                            'Erro no Contato',
                            'Nenhuma conta logada',
                            'error'
                        ).then(() => {
                            window.location.href = '../login/login.php';
                        });
                    }
                }

                // Adicionar um evento de clique ao botão "adtbttn" que chama a função onContatoButtonClick
                document.querySelector('.adtbttn').addEventListener('click', onContatoButtonClick);
            </script>

        </div>
        <?php include('../lib/footer.php');
        ?>

</body>

</html>