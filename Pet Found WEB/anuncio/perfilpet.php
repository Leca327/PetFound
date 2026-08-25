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
    <link rel="stylesheet" href="perfilpet.css">
    <link rel="stylesheet" href=" ../cards.css">
    <link rel="stylesheet" href=" ../cards1.css">


</head>

<body>
    <?php
    include('../lib/navbar.php');

    if (!empty($_GET['petcod'])) {

        include('../lib/dbconnect.php');
        $petcod = $_GET['petcod'];

        // Use prepared statements to prevent SQL injection
        $sqlSelect = "SELECT * FROM pet WHERE petcod = '" . $petcod . "'";
        $result = mysqli_query($mysqli, $sqlSelect);

        if ($result->num_rows > 0) {
            // Assuming 'nomepet' is a column in the 'pet' table
            $row = $result->fetch_assoc();
            $sexo = $row['sexo'];
            if ($sexo === "F") {
                $sexo = "Fêmea";
            } else if ($sexo === "M") {
                $sexo = "Macho";
            }

            $nome = $row['nomepet'];
            $porte = $row['porte'];
            if ($porte === "Medio") {
                $porte = "Médio";
            }

            $idade = $row['fai_ida'];
            $raca = $row['raca'];
            $data = $row['dtp'];
            $dataObj = date_create($data);
            $dtf = date_format($dataObj, "d/m/Y");

            $historia = $row['historia'];
            $descpet = $row['descpet'];
            $pessoa = $row['pessoacodp'];
            $anunc = $row['finalidade'];
            $anunct = $row['finalidade'];
            $dst = $row['bloqueiopet'];
            $apv = $row['aprovacaopet'];

            $atvo = $row['bloqueiopet'];
            if ($atvo == true) {
                $atvmsg = "Ativar";
            } else if ($atvo == false || $atvo == null) {
                $atvmsg = "Desativar";
            }

            if ($anunc === "Padrinho") {
                $anunc = "Procuro um Padrinho ou madrinha para me ajudar!";
                $anunc2 = "Pet disponível para Apadrinhamento";
            } else if ($anunc === "Adocao") {
                $anunc = "Procuro um lar confortavél e amavél!";
                $anunc2 = "Pet disponível para Adoção";
            } else if ($anunc === "Pad_Ado") {
                $anunc = "Preciso de um lar ou ajuda!";
                $anunc2 = "Pet disponível para Adoção ou Apadrinhamento";
            }

            $sql_imagem = "SELECT * FROM imagem WHERE petcodpet='$petcod';";
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
            echo "Nenhum pet encontrado com esse código.";
        }

        $tipopet = $row['tipop'];
        if ($tipopet === null || $tipopet === "") {
            $tipopet = "PET";
        }
    }
    ?>

    <div class="left-side">
        <a href="../index.php">
            <h6 class="road">Home ></h6>
        </a>
        <a href="../cadpetserv/searchpet.php">
            <h6 class="road2">Quero adotar ></h6>
        </a>
        <?php echo "
        <h6 class='road3'>" . $nome . " </h6> 

        <img class='imgpet' src='data:$imageType;base64," . base64_encode($imageData) . "' alt='Pet Image'>
        <h6 class='anunc2'> " . $anunc2 . "</h6>
     
        "
        ?>
    </div>
    <div class="right-side">
        <?php
        echo "
        <h1 class='nomepet'> " . $nome . "</h1>
        <h6 class='infopet'> " . $tipopet . " | " . $sexo . " | " . $idade . " | Porte " . $porte . " | " . $raca . "</h6>
        <h6 class='infopet2'>" . $msg . "<a href='../perfil.php?pcod=$pessoa'>" . $nickp . "</a> em " . $dtf . "</h6>
        <h6 class='history'> A história do Pet</h6>
        <h6 class='history2'> " . $historia . "</h6>
        <h6 class='descpet'> Mais detalhes sobre o pet</h6>
        <h6 class='descpet2'> " . $descpet . "</h6>";


        if (isset($_SESSION['admin'])) {
            /* echo "<button class='adtbttndono'>Editar Pet</button>
                        <button class='adtbttndono'>Deletar</button>
                <h6 class='anunc'> " . $anunc . "</h6>";*/
        } else if (isset($_SESSION['usuario'])) {
            if ($apv != true && isset($apv)) {
                if ($nickp === $_SESSION['usuario']) {
                    echo "<button class='adtbttncont' >Pet Reprovado.</button>
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
                    echo "<button class='adtbttncont' >Pet desativado.</button>
            <h6 class='anunc'> Volte quando o anuncio for ativado</h6>";
                } else {
                    $query = "SELECT * FROM `contatopet` WHERE petcodpet= '" . $petcod . "' and adotou=true";
                    $result = mysqli_query($mysqli, $query);
                    if ($row = mysqli_fetch_assoc($result)) {
                        echo "<button class='adtbttncont' >Pet foi Adotado.</button>";
                    } else {

                        $sqlSelect = "SELECT * FROM pessoa WHERE nickname = '" . $_SESSION['usuario'] . "'";
                        $result = mysqli_query($mysqli, $sqlSelect);

                        //<button class='adtbttn' onclick='onContatoButtonClick()'>Entrar em contato</button>
                        //<h6 class='anunc'> " . $anunc . "</h6>

                        if ($result->num_rows > 0) {
                            // Assuming 'nomepet' is a column in the 'pet' table
                            $row = $result->fetch_assoc();
                            $codpes = $row['pcod'];
                            $query = "SELECT * FROM `contatopet` WHERE pfcodp = '" . $codpes . "' AND petcodpet= '" . $petcod . "'";
                            $result = mysqli_query($mysqli, $query);
                            if ($row = mysqli_fetch_assoc($result)) {
                                echo "<button class='adtbttncont'>Contato Já foi Criado!</button>
                <h6 class='anunc'> " . $anunc . "</h6>";
                            } else {
                                $query = "SELECT pcod FROM pessoa WHERE nickname = '" . $_SESSION['usuario'] . "'";
                                $result = mysqli_query($mysqli, $query);
                                if ($row = mysqli_fetch_assoc($result)) {

                                    if ($nickp === $_SESSION['usuario']) {
                                        echo "<button class='adtbttndono'><a href='../editarpet.php?codigo=" . $petcod . "'>Editar Pet</a></button> 
                                    <form action='../editpet.php' method='post'>
                                        <input type='hidden' name='petcod' value='" . $petcod . "' required />
                                        <input type='hidden' name='atv' value='" . $atvo . "' required />  
                                        <input type='hidden' name='nome' value='" . $nome . "' required />  
                                        <button type='submit' name='dst-btn' class='adtbttndono' > $atvmsg</button>
                                    </form>
                                    <h6 class='anunc'> " . $anunc . "</h6>";
                                    } else {

                                        echo "<button class='adtbttnoff' onclick='onContatoButtonClick()'>Entrar em contato</button>
                                <h6 class='anunc'> " . $anunc . "</h6>";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            echo "<button class='adtbttn' onclick='paglogin()'>Logar para Criar Contato.</button>
                            <h6 class='anunc'> " . $anunc . "</h6>";
        }
        ?>

    </div>


    <h1 class="hnvpets2"></h1>

    <?php
    $pad = "Padrinhos do Pet";
    $query = "SELECT * FROM `contatopet` WHERE petcodpet= '" . $petcod . "' and adotou=true";
    $result = mysqli_query($mysqli, $query);
    if ($row = mysqli_fetch_assoc($result)) {
        echo "<h1 class='hnvpets'>Tutor</h1>";
        $pad = "Quem ajudou quando estava sem lar.";
        echo "<center>";
        $pfcodp = $row['pfcodp'];
        $sql_imagem = "SELECT * FROM pessoa WHERE pcod='$pfcodp';";
        $result_imagem = $mysqli->query($sql_imagem);

        if ($row_imagem = mysqli_fetch_assoc($result_imagem)) {
            $imageData = $row_imagem['imgperfil'];
            $imageType = 'image/jpeg';
            $nic = $row_imagem['nickname'];
            if ($imageData == null) {
                $imagePath = '../assets/semimg.png';
                $imageData = file_get_contents($imagePath);
                $imageType = mime_content_type($imagePath);
            }
        } else {
            /*  // Defina uma imagem padrão caso nenhuma imagem seja encontrada
        $imagePath = '../assets/semimg.png';
        $imageData = file_get_contents($imagePath);
        $imageType = mime_content_type($imagePath);*/
        }

        echo "<div class='card'>
<img class='cards' src='data:$imageType;base64," . base64_encode($imageData) . "' alt='Pet Image'>
<a class='aa' href='../perfil.php?pcod=$pfcodp'>" . $nic . "</a>
</div>";
        echo "</center>";
    }



    if ($anunct === "Padrinho" || $anunct === "Pad_Ado") {
        echo "<h1 class='hnvpets'>$pad</h1>";
        echo "<center>";

        $query = "SELECT * FROM `contatopet` WHERE petcodpet= '" . $petcod . "' and apadrinhou=true";
        $result = mysqli_query($mysqli, $query);

        while ($row = mysqli_fetch_assoc($result)) {
            $pfcodp = $row['pfcodp'];
            $sql_imagem = "SELECT * FROM pessoa JOIN fisica on pcod=codp WHERE pcod='$pfcodp';";
            $result_imagem = $mysqli->query($sql_imagem);

            if ($row_imagem = mysqli_fetch_assoc($result_imagem)) {
                $imageData = $row_imagem['imgperfil'];
                $imageType = 'image/jpeg';
                $nic = $row_imagem['nickname'];
                $sx = $row_imagem['sexo'];

                if ($sx === "M") {
                    $msg = "Padrinho";
                } else if ($sx === "F") {
                    $msg = "Madrinha";
                } else if ($sx === "O") {
                    $msg = "Padrinho/Madrinha";
                } else {
                    $msg = "Padrinho/Madrinha";
                }

                if ($imageData == null) {
                    $imagePath = '../assets/semimg.png';
                    $imageData = file_get_contents($imagePath);
                    $imageType = mime_content_type($imagePath);
                }

                echo "<div class='card'>
                        <img class='cards' src='data:$imageType;base64," . base64_encode($imageData) . "' alt='Pet Image'>
                        <p class='title'>" . $msg . "</p>
                        <a class='aa' href='../perfil.php?pcod=$pfcodp'>" . $nic . "</a>
                    </div>";
            }
        }

        if (mysqli_num_rows($result) === 0) {
            echo "<button class='petlar2'>Sem Padrinhos por aqui</button><br>";
        }

        echo "</center>";
    }

    ?>


    <div id="popup2" class="popup2">
        <div class="popup-content">
            <?php
            // Caminho do primeiro arquivo
            $caminhoArquivo1 = '../set-up/contatopet.php';

            // Caminho do segundo arquivo
            $caminhoArquivo2 = '../contatopet.php';

            // Caminho do terceiro arquivo
            $caminhoArquivo3 = '../../contatopet.php';


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

    <h1 class="hnvpets">Outros aumigos te esperam aqui!</h1>
    <div class="adocao">
        <?php

        $sql = "SELECT * FROM pet WHERE (aprovacaopet = true OR aprovacaopet IS NULL)
        AND petcod NOT IN (
            SELECT DISTINCT petcodpet
            FROM contatopet
            WHERE adotou = true
        ) AND petcod <> '" . $petcod . "' ORDER BY RAND();";


        $result = $mysqli->query($sql);
        $counter = 0; // Variável para contar as iterações
        if ($result) {
            while ($pet_data = mysqli_fetch_assoc($result)) {
                $petcodpet = $pet_data['petcod'];
                $sql_imagem = "SELECT * FROM imagem WHERE petcodpet='$petcodpet';";
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

                if ($pet_data['finalidade'] == "Adocao") {
                    $anun = "Adote-me";
                } else if ($pet_data['finalidade'] == "Padrinho") {
                    $anun = "Apadrinhe-me";
                } else if ($pet_data['finalidade'] == "Pad_Ado") {
                    $anun = "Ajude-me";
                }

                echo '<div class="cards1">'; // Abre um card
                echo '<h3 style="color: black;">' . $pet_data['nomepet'] . '</h3>';
                echo '<img class="imgcardpet" src="data:' . $imageType . ';base64,' . base64_encode($imageData) . '" alt="Pet Image">';
                echo '<p class="title"></p>';
                echo '<h3 style="color: black;">' . $pet_data['nomepet'] . '</h3>';
                echo '<h4 style="color: black;">' . $pet_data['estadop'] . ", " . $pet_data['cidadep'] . '</h4>';
                echo '<button class="butcardpet"><a href="../anuncio/perfilpet.php?petcod=' . $pet_data['petcod'] . '">' . $anun . '</a></button>';
                echo '</div>'; // Fecha o card

                $counter++; // Incrementa o contador

                if ($counter === 5) {
                    break;
                }
            }
        } else {
            echo "<br><button class='petlar'>Os outros Pets por aqui estão com um lar </button><br><br><br><br><br><br>";
        }

        if ($counter === 0) {
            echo "<br><button class='petlar'>Os outros Pets por aqui estão com um lar </button><br><br><br><br><br><br>";
        }

        ?>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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