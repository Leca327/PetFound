<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./lib/footer.css">
    <link rel="stylesheet" href="./lib/navbar.css">
    <link rel="stylesheet" href="./lib/responsivonavbar.css">
    <link rel="stylesheet" href="./lib/padrao.css">
    <link rel="stylesheet" href="./index.css">
    <link rel="stylesheet" href="./perfil.css">
    <link rel="stylesheet" href="./cards1.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <title>Perfil</title>
</head>

<body>

    <?php include('./lib/navbar.php'); ?>
    <?php include('./lib/dbconnect.php'); ?>
    <br><br><br>

    <?php

    if (!empty($_GET['pcod'])) {
        $user = $_GET['pcod'];
        $query = "SELECT * FROM pessoa p JOIN endereco e on p.endcodend=e.endcod WHERE pcod = '$user';";
        $result = mysqli_query($mysqli, $query);
        $result_imagem = $mysqli->query($query);
        if ($result && mysqli_num_rows($result) > 0) {
            $row = mysqli_fetch_assoc($result);
            $cod = $row['pcod'];
            $tipo = substr($cod, 0, 2);

            if ($tipo == "PF") {
                $nome = $row['nomep'];
                $snome = $row['snomep'];
                $email = $row['emailp'];
                $contato = $row['contatop'];
                $senha = $row['senha'];
                $nick = $row['nickname'];
                $cod = $row['pcod'];

                $cep = $row['cep'];
                $uf = $row['uf'];
                $brr = $row['bairro'];
                $end = $row['endereco'];
                $num = $row['numero'];
                $cpt = $row['cmpt'];
                $cid = $row['cidade'];
                $ddd = substr($contato, 0, 2);
                $parte1 = substr($contato, 2, 5);
                $parte2 = substr($contato, 7);
                $cont = "($ddd) $parte1-$parte2";

                if ($result_imagem && $row_imagem = mysqli_fetch_assoc($result_imagem)) {
                    $imageData = $row_imagem['imgperfil'];
                    $imageType = 'image/jpeg';
                    if ($imageData == null) {
                        // Se não houver imagem, carregue uma imagem padrão.
                        $imagePath = './adm/usuario.jpg';
                        $imageData = file_get_contents($imagePath);
                        $imageType = mime_content_type($imagePath);
                    }
                } else {
                    // Se não houver imagem, carregue uma imagem padrão.
                    $imagePath = './adm/usuario.jpg';
                    $imageData = file_get_contents($imagePath);
                    $imageType = mime_content_type($imagePath);
                }

                $query = "SELECT * FROM fisica WHERE codp = '$cod';";
                $result = mysqli_query($mysqli, $query);

                if ($result && mysqli_num_rows($result) > 0) {
                    $row = mysqli_fetch_assoc($result);
                    $sexo = $row['sexo'];
                    $dt = $row['dt_nascimento'];

                    echo "   <div class='paas'>
        <H1 class='perfil'></H1>
 
        <img src='./assets/projetoimg/banner4.png' alt='' class='fundo'>

            <div class='imgperfil'>    

            <img src='data:" . $imageType . ";base64," . base64_encode($imageData) . "' class='imgP'>
            
            <H1 class='userperfil'>$nick</H1>
            </div>
        </div>

    <div class='dados'>
        <li class='D2'>$nome $snome</li>
        <li class='D5'> $cont</li>
    </div>
";
                }
                echo "<nav>
    <a><button class='button_op' onclick=\"updateSelected('op1')\">Minhas Divulgações de Pets</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op2')\">Minhas Divulgações de Serviços</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op3')\">Adoções feitas por mim</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op4')\">Meus afilhados</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op5')\">Serviços Contratados</button></a>
</nav>";
            } else if ($tipo == "PJ") {
                $nome = $row['nomep'];
                $email = $row['emailp'];
                $contato = $row['contatop'];
                $nick = $row['nickname'];
                $cod = $row['pcod'];
                $ddd = substr($contato, 0, 2);
                $parte1 = substr($contato, 2, 5);
                $parte2 = substr($contato, 7);
                $cont = "($ddd) $parte1-$parte2";
                $cep = $row['cep'];
                $uf = $row['uf'];
                $brr = $row['bairro'];
                $end = $row['endereco'];
                $num = $row['numero'];
                $cpt = $row['cmpt'];
                $cid = $row['cidade'];
                if ($result_imagem && $row_imagem = mysqli_fetch_assoc($result_imagem)) {
                    $imageData = $row_imagem['imgperfil'];
                    $imageType = 'image/jpeg';
                    if ($imageData == null) {
                        // Se não houver imagem, carregue uma imagem padrão.
                        $imagePath = './adm/usuario.jpg';
                        $imageData = file_get_contents($imagePath);
                        $imageType = mime_content_type($imagePath);
                    }
                } else {
                    // Se não houver imagem, carregue uma imagem padrão.
                    $imagePath = './adm/usuario.jpg';
                    $imageData = file_get_contents($imagePath);
                    $imageType = mime_content_type($imagePath);
                }
                $query = "SELECT * FROM juridica WHERE cod_p = '$cod';";
                $result = mysqli_query($mysqli, $query);

                if ($result && mysqli_num_rows($result) > 0) {
                    $row = mysqli_fetch_assoc($result);

                    $ra = $row['ramo_ativ'];
                    $tip = $row['tipoj'];

                    echo "   <div class='paas'>
        <H1 class='perfil'></H1>

        <img src='./assets/projetoimg/banner4.png' alt='' class='fundo'>

            <div class='imgperfil'>    

            <img src='data:" . $imageType . ";base64," . base64_encode($imageData) . "' class='imgP'>

            <H1 class='userperfil'>$nick</H1>
            </div>
        </div>

    <div class='dados'>
        <li class='D2'>$tip: $nome</li>
        <li class='D3'>$ra</li>
        <li class='D5'> $cont</li>
    </div>
";
                    if ($tip === "ONG") {

                        echo "<nav>
    <a><button class='button_op' onclick=\"updateSelected('op1')\">Minhas Divulgações de Pets</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op2')\">Minhas Divulgações de Serviços</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op5')\">Serviços Contratados</button></a>
</nav>";
                    } else {

                        echo "<nav>
    <a><button class='button_op' onclick=\"updateSelected('op1')\">Minhas Divulgações de Pets</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op2')\">Minhas Divulgações de Serviços</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op5')\">Serviços Contratados</button></a>
</nav>";
                    }
                }
            }
        }
    } else if (isset($_SESSION["usuario"]) && empty($_GET['pcod'])) {
        $user = $_SESSION["usuario"];
        $query = "SELECT * FROM pessoa p JOIN endereco e on p.endcodend=e.endcod WHERE nickname = '$user';";
        $result = mysqli_query($mysqli, $query);
        $result_imagem = $mysqli->query($query);
        if ($result && mysqli_num_rows($result) > 0) {
            $row = mysqli_fetch_assoc($result);
            $cod = $row['pcod'];
            $tipo = substr($cod, 0, 2);

            if ($tipo == "PF") {
                $nome = $row['nomep'];
                $snome = $row['snomep'];
                $email = $row['emailp'];
                $contato = $row['contatop'];
                $senha = $row['senha'];
                $nick = $row['nickname'];
                $cod = $row['pcod'];

                $cep = $row['cep'];
                $uf = $row['uf'];
                $brr = $row['bairro'];
                $end = $row['endereco'];
                $num = $row['numero'];
                $cpt = $row['cmpt'];
                $cid = $row['cidade'];
                $ddd = substr($contato, 0, 2);
                $parte1 = substr($contato, 2, 5);
                $parte2 = substr($contato, 7);
                $cont = "($ddd) $parte1-$parte2";

                if ($result_imagem && $row_imagem = mysqli_fetch_assoc($result_imagem)) {
                    $imageData = $row_imagem['imgperfil'];
                    $imageType = 'image/jpeg';
                    if ($imageData == null) {
                        // Se não houver imagem, carregue uma imagem padrão.
                        $imagePath = './adm/usuario.jpg';
                        $imageData = file_get_contents($imagePath);
                        $imageType = mime_content_type($imagePath);
                    }

                    $imageData2 = $row_imagem['banner'];
                    $imageType2 = 'image/jpeg';
                    if ($imageData2 == null) {
                        // Se não houver imagem, carregue uma imagem padrão.
                        $imagePath2 = './assets/projetoimg/banner4.png';
                        $imageData2 = file_get_contents($imagePath2);
                        $imageType2 = mime_content_type($imagePath2);
                    }
                } else {
                    // Se não houver imagem, carregue uma imagem padrão.
                    $imagePath = './adm/usuario.jpg';
                    $imageData = file_get_contents($imagePath);
                    $imageType = mime_content_type($imagePath);
                }

                $query = "SELECT * FROM fisica WHERE codp = '$cod';";
                $result = mysqli_query($mysqli, $query);

                if ($result && mysqli_num_rows($result) > 0) {
                    $row = mysqli_fetch_assoc($result);
                    $sexo = $row['sexo'];
                    $dt = $row['dt_nascimento'];

                    echo "   <div class='paas'>
        <H1 class='perfil'></H1>
 
        <img src='data:" . $imageType2 . ";base64," . base64_encode($imageData2) . "' class='fundo'>
        <a id='openPopupBtn2'><i class='fas fa-pen'></i></a>

            <div class='imgperfil'>    

            <img src='data:" . $imageType . ";base64," . base64_encode($imageData) . "' class='imgP'>
            <a id='openPopupBtn'><i class='fa-solid fa-pen'></i></a>
            <H1 class='userperfil'>$nick</H1>
            </div>
        </div>

    <div class='dados'>
        <li class='D2'>$nome $snome</li>
        <li class='D5'> $cont</li>
    </div>
";
                }
                echo "<nav>
    <a><button class='button_op' onclick=\"updateSelected('op1')\">Minhas Divulgações de Pets</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op2')\">Minhas Divulgações de Serviços</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op3')\">Adoções feitas por mim</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op4')\">Meus afilhados</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op5')\">Serviços Contratados</button></a>
</nav>";
            } else if ($tipo == "PJ") {
                $nome = $row['nomep'];
                $email = $row['emailp'];
                $contato = $row['contatop'];
                $nick = $row['nickname'];
                $cod = $row['pcod'];
                $ddd = substr($contato, 0, 2);
                $parte1 = substr($contato, 2, 5);
                $parte2 = substr($contato, 7);
                $cont = "($ddd) $parte1-$parte2";
                $cep = $row['cep'];
                $uf = $row['uf'];
                $brr = $row['bairro'];
                $end = $row['endereco'];
                $num = $row['numero'];
                $cpt = $row['cmpt'];
                $cid = $row['cidade'];
                if ($result_imagem && $row_imagem = mysqli_fetch_assoc($result_imagem)) {
                    $imageData = $row_imagem['imgperfil'];
                    $imageType = 'image/jpeg';
                    if ($imageData == null) {
                        // Se não houver imagem, carregue uma imagem padrão.
                        $imagePath = './adm/usuario.jpg';
                        $imageData = file_get_contents($imagePath);
                        $imageType = mime_content_type($imagePath);
                    }
                    $imageData2 = $row_imagem['banner'];
                    $imageType2 = 'image/jpeg';
                    if ($imageData2 == null) {
                        // Se não houver imagem, carregue uma imagem padrão.
                        $imagePath2 = './assets/projetoimg/banner4.png';
                        $imageData2 = file_get_contents($imagePath2);
                        $imageType2 = mime_content_type($imagePath2);
                    }
                } else {
                    // Se não houver imagem, carregue uma imagem padrão.
                    $imagePath = './adm/usuario.jpg';
                    $imageData = file_get_contents($imagePath);
                    $imageType = mime_content_type($imagePath);
                }
                $query = "SELECT * FROM juridica WHERE cod_p = '$cod';";
                $result = mysqli_query($mysqli, $query);

                if ($result && mysqli_num_rows($result) > 0) {
                    $row = mysqli_fetch_assoc($result);

                    $ra = $row['ramo_ativ'];
                    $tip = $row['tipoj'];

                    echo "   <div class='paas'>
        <H1 class='perfil'></H1>

        <img src='data:" . $imageType2 . ";base64," . base64_encode($imageData2) . "' class='fundo'>
        <a id='openPopupBtn2'><i class='fas fa-pen'></i></a>

            <div class='imgperfil'>    

            <img src='data:" . $imageType . ";base64," . base64_encode($imageData) . "' class='imgP'>
            <a id='openPopupBtn'><i class='fa-solid fa-pen'></i></a>
            <H1 class='userperfil'>$nick</H1>
            </div>
        </div>

    <div class='dados'>
        <li class='D2'>$tip: $nome</li>
        <li class='D3'>Ramo: $ra</li>
        <li class='D5'> $cont</li>
    </div>
";
                    if ($tip === "ONG") {

                        echo "<nav>
    <a><button class='button_op' onclick=\"updateSelected('op1')\">Minhas Divulgações de Pets</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op2')\">Minhas Divulgações de Serviços</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op5')\">Serviços Contratados</button></a>
</nav>";
                    } else {

                        echo "<nav>
    <a><button class='button_op' onclick=\"updateSelected('op1')\">Minhas Divulgações de Pets</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op2')\">Minhas Divulgações de Serviços</button></a>
    <a><button class='button_op' onclick=\"updateSelected('op5')\">Serviços Contratados</button></a>
</nav>";
                    }
                }
            }
        }
    } else if (isset($_SESSION["admin"])) {
        $user = $_SESSION["admin"];

        // Fetch admin information from the database
        $query = "SELECT * FROM admin WHERE usera = '$user';";
        $result = mysqli_query($mysqli, $query);

        if ($result && mysqli_num_rows($result) > 0) {
            $adminData = mysqli_fetch_assoc($result);
            $nomea = $adminData['nomea'];
            $senhaa = $adminData['senhaa'];
            $admcod = $adminData['admcod'];
            echo "adm";
        } else {
            echo "<h1>Nenhuma informação encontrada para o admin.</h1>";
        }
    } else {
        echo "<script>
            window.location.href='./login/login.php';
      
    </script>";
    }



    echo "
<div id='content_op1' style='display: none;'>";

    // Certifique-se de que a conexão com o banco de dados esteja estabelecida corretamente.
    $cardsPerPage = 14;
    $page = isset($_GET['page']) ? $_GET['page'] : 1;
    $startIndex = ($page - 1) * $cardsPerPage;
    $consulta = "SELECT * FROM pet WHERE aprovacaopet = true and (bloqueiopet= false or bloqueiopet is null) and pessoacodp = '$cod'";

    // Construa uma consulta SQL para contar o número de registros
    $countQuery = "SELECT COUNT(*) as total FROM pet WHERE aprovacaopet = true and (bloqueiopet= false or bloqueiopet is null) and pessoacodp = '$cod'";
    $countResult = $mysqli->query($countQuery);

    if ($countResult) {
        $countData = mysqli_fetch_assoc($countResult);
        $totalCards = $countData['total'];
    } else {
        // Em caso de erro na consulta, defina $totalCards como 0 ou qualquer valor padrão.
        $totalCards = 0;
    }

    $sql = $consulta . " LIMIT $startIndex, $cardsPerPage;";
    $result = $mysqli->query($sql);
    $counter = 0;

    // Verifique se a consulta retornou resultados.
    if ($result) {
        echo '<div class="card-container">';

        while ($pet_data = mysqli_fetch_assoc($result)) {
            $petcodpet = $pet_data['petcod'];
            $sql_imagem = "SELECT * FROM imagem WHERE petcodpet='$petcodpet';";
            $result_imagem = $mysqli->query($sql_imagem);

            if ($result_imagem && $row_imagem = mysqli_fetch_assoc($result_imagem)) {
                $imageData = $row_imagem['img'];
                $imageType = 'image/jpeg';
            } else {
                // Se não houver imagem, carregue uma imagem padrão.
                $imagePath = './assets/semimg.png';
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
            echo '<h3 style="color: black;">1' . $pet_data['nomepet'] . '</h3>';
            echo '<img class="imgcardpet" src="data:' . $imageType . ';base64,' . base64_encode($imageData) . '" alt="Pet Image">';
            echo '<p class="title"></p>';
            echo '<h3 style="color: black;">' . $pet_data['nomepet'] . '</h3>';
            echo '<h4 style="color: black;">' . $pet_data['estadop'] . ", " . $pet_data['cidadep'] . '</h4>';
            echo '<button class="butcardpet"><a href="./anuncio/perfilpet.php?petcod=' . $pet_data['petcod'] . '">' . $anun . '</a></button>';
            echo '</div>'; // Fecha o card

            $counter++;

            // Verifique se atingiu o limite de cards por página
            if ($counter === $cardsPerPage) {
                break;
            }
        }

        // Adicione links de navegação para as páginas seguintes
        $totalPages = ceil($totalCards / $cardsPerPage);

        echo '</div>'; // Fecha a div do container de cards

        echo '<div class="pagination">';
        for ($i = 1; $i <= $totalPages; $i++) {
            echo '<a class="page-link" href="?page=' . $i . '">' . $i . '</a>';
        }
        echo '</div>';
        echo '<br>';

        $total = mysqli_num_rows($result);
        if ($total == 0) {
            echo "<h4 class= 'frasemidle'>Nenhum Pet encontrado.</h4>";
        }
    } else {
        // Trate o caso em que a consulta não retornou resultados.
        echo "<h4 class= 'frasemidle'>Nenhum Pet encontrado.</h4>";
        echo "aa: " . $mysqli->error;
    }

    // Lembre-se de fechar a conexão com o banco de dados quando não for mais necessário.

    echo "</div>";
    echo "<div id='content_op2' style='display: none;'>";

    $cardsPerPage = 10;
    $page = isset($_GET['page']) ? $_GET['page'] : 1;
    $startIndex = ($page - 1) * $cardsPerPage;

    $consulta = "SELECT * FROM servico WHERE aprovacaoserv = true and (bloqueioserv= false or bloqueioserv is null) and pessoa_codp = '$cod'";

    $sql = $consulta . " LIMIT $startIndex, $cardsPerPage;";
    $result = $mysqli->query($sql);
    $counter = 0;
    if ($result) {
        echo '<div class="card-container">';

        while ($pet_data = mysqli_fetch_assoc($result)) {
            $petcodpet = $pet_data['servcod'];
            $sql_imagem = "SELECT * FROM imagem WHERE servicocodserv='$petcodpet';";
            $result_imagem = $mysqli->query($sql_imagem);

            if ($result_imagem && $row_imagem = mysqli_fetch_assoc($result_imagem)) {
                $imageData = $row_imagem['img'];
                $imageType = 'image/jpeg';
            } else {
                // Se não houver imagem, carregue uma imagem padrão.
                $imagePath = './assets/semimg.png';
                $imageData = file_get_contents($imagePath);
                $imageType = mime_content_type($imagePath);
            }

            $precoserv = number_format((float)$pet_data['preco'], 2, ',', '');


            echo '<div class="cards1">'; // Abre um card
            echo '<h3 style="color: black;">' . $pet_data['nomeserv'] . '</h3>';
            echo '<img class="imgcardpet" src="data:' . $imageType . ';base64,' . base64_encode($imageData) . '" alt="Pet Image">';
            echo '<p class="title"></p>';
            echo '<h3 style="color: black;">' . $pet_data['nomeserv'] . '</h3>';
            echo '<h4 style="color: black;">' . $pet_data['estados'] . ", " . $pet_data['cidades'] . '</h4>';
            echo '<h5 style="color: black;" class="price" data-price="' . $precoserv . '">R$' . $precoserv . '</h5>';
            echo '<button class="butcardpet1"><a href="./anuncio/perfilserv.php?servcod=' . $pet_data['servcod'] . '">Contratar</a></button>';
            echo '</div>'; // Fecha o card

            $counter++;

            if ($counter === 20) {
                break;
            }
        }

        echo '</div>';
        $total = mysqli_num_rows($result);
        if ($total == 0) {
            echo "<h4 class= 'frasemidle'>Nenhum Serviço encontrado.</h4>";
        }
    } else {
        // Trate o caso em que a consulta não retornou resultados.
        echo "<h4 class= 'frasemidle'>Nenhum Serviço encontrado.</h4>";
        echo "aa: " . $mysqli->error;
    }

    echo "</div>";
    echo "<div id='content_op3' style='display: none;'>";
    // Certifique-se de que a conexão com o banco de dados esteja estabelecida corretamente.
    $consulta = "SELECT * FROM contatopet WHERE adotou=true and pfcodp = '$cod'";
    $result = $mysqli->query($consulta);

    if ($result) {
        while ($row = mysqli_fetch_assoc($result)) {
            $codpet = $row['petcodpet'];
            $cardsPerPage = 10;
            $page = isset($_GET['page']) ? $_GET['page'] : 1;
            $startIndex = ($page - 1) * $cardsPerPage;
            $consulta = "SELECT * FROM pet WHERE aprovacaopet = true and petcod = '$codpet'";

            $sql = $consulta . " LIMIT $startIndex, $cardsPerPage;";
            $result = $mysqli->query($sql);
            $counter = 0;

            // Verifique se a consulta retornou resultados.
            if ($result) {
                echo '<div class="card-container">';

                while ($pet_data = mysqli_fetch_assoc($result)) {
                    $petcodpet = $pet_data['petcod'];
                    $sql_imagem = "SELECT * FROM imagem WHERE petcodpet='$petcodpet';";
                    $result_imagem = $mysqli->query($sql_imagem);

                    if ($result_imagem && $row_imagem = mysqli_fetch_assoc($result_imagem)) {
                        $imageData = $row_imagem['img'];
                        $imageType = 'image/jpeg';
                    } else {
                        // Se não houver imagem, carregue uma imagem padrão.
                        $imagePath = './assets/semimg.png';
                        $imageData = file_get_contents($imagePath);
                        $imageType = mime_content_type($imagePath);
                    }

                    echo '<div class="cards1">'; // Abre um card
                    echo '<h3 style="color: black;">3' . $pet_data['nomepet'] . '</h3>';
                    echo '<img class="imgcardpet" src="data:' . $imageType . ';base64,' . base64_encode($imageData) . '" alt="Pet Image">';
                    echo '<p class="title"></p>';
                    echo '<h3 style="color: black;">' . $pet_data['nomepet'] . '</h3>';
                    echo '<h4 style="color: black;">' . $pet_data['estadop'] . ", " . $pet_data['cidadep'] . '</h4>';
                    echo '<button class="butcardpet"><a href="./anuncio/perfilpet.php?petcod=' . $pet_data['petcod'] . '">Vizualizar</a></button>';
                    echo '</div>'; // Fecha o card

                    $counter++;

                    if ($counter === 10) {
                        break;
                    }
                }

                echo '</div>'; // Fecha o contêiner de cartões

            } else {
                // Trate o caso em que a consulta não retornou resultados.
                echo "<h4 class= 'frasemidle'>Nenhum Pet encontrado.</h4>";
                echo "aa: " . $mysqli->error;
            }
        }
        $total = mysqli_num_rows($result);
        if ($total == 0) {
            echo "<h4 class= 'frasemidle'>Sem adoções encontradas encontrado.</h4>";
        }
    } else {
        echo 'Nenhuma adoção encontrada.';
        echo "aa: " . $mysqli->error;
    }

    echo "</div>";
    echo "<div id='content_op4' style='display: none;'>";

    // Certifique-se de que a conexão com o banco de dados esteja estabelecida corretamente.

    $consulta = "SELECT * FROM contatopet WHERE apadrinhou=true and (adotou=false or adotou is null) and pfcodp = '$cod'";
    $result = $mysqli->query($consulta);
    if ($result) {
        while ($row = mysqli_fetch_assoc($result)) {
            $codpet = $row['petcodpet'];
            $cardsPerPage = 10;
            $page = isset($_GET['page']) ? $_GET['page'] : 1;
            $startIndex = ($page - 1) * $cardsPerPage;
            $consulta = "SELECT * FROM pet WHERE (aprovacaopet = true OR aprovacaopet IS NULL)
            AND petcod IN (
                SELECT DISTINCT petcodpet
                FROM contatopet
                WHERE apadrinhou = true
            ) and petcod = '$codpet'";
            $sql = $consulta . " LIMIT $startIndex, $cardsPerPage;";
            $result = $mysqli->query($sql);
            $counter = 0;

            // Verifique se a consulta retornou resultados.
            if ($result) {
                echo '<div class="card-container">';

                while ($pet_data = mysqli_fetch_assoc($result)) {
                    $petcodpet = $pet_data['petcod'];
                    $sql_imagem = "SELECT * FROM imagem WHERE petcodpet='$petcodpet';";
                    $result_imagem = $mysqli->query($sql_imagem);
                    if ($result_imagem && $row_imagem = mysqli_fetch_assoc($result_imagem)) {
                        $imageData = $row_imagem['img'];
                        $imageType = 'image/jpeg';
                    } else {
                        // Se não houver imagem, carregue uma imagem padrão.
                        $imagePath = './assets/semimg.png';
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
                    echo '<h3 style="color: black;">5' . $pet_data['nomepet'] . '</h3>';
                    echo '<img class="imgcardpet" src="data:' . $imageType . ';base64,' . base64_encode($imageData) . '" alt="Pet Image">';
                    echo '<p class="title"></p>';
                    echo '<h3 style="color: black;">' . $pet_data['nomepet'] . '</h3>';
                    echo '<h4 style="color: black;">' . $pet_data['estadop'] . ", " . $pet_data['cidadep'] . '</h4>';
                    echo '<button class="butcardpet"><a href="./anuncio/perfilpet.php?petcod=' . $pet_data['petcod'] . '">' . $anun . '</a></button>';
                    echo '</div>'; // Fecha o card

                    $counter++;

                    if ($counter === 10) {
                        break;
                    }
                }


                echo '</div>'; // Fecha o contêiner de cartões

            } else {
                // Trate o caso em que a consulta não retornou resultados.
                echo "<h4 class= 'frasemidle'>Nenhum Pet encontrado.</h4>";
                echo "aa: " . $mysqli->error;
            }
        }
        $total = mysqli_num_rows($result);
        if ($total == 0) {
            $consulta = "SELECT * FROM contatopet WHERE apadrinhou=true and pfcodp = '$cod'";
            $result = $mysqli->query($consulta);
            if ($result) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $codpet = $row['petcodpet'];
                    $cardsPerPage = 10;
                    $page = isset($_GET['page']) ? $_GET['page'] : 1;
                    $startIndex = ($page - 1) * $cardsPerPage;
                    $consulta = "SELECT * FROM pet WHERE (aprovacaopet = true OR aprovacaopet IS NULL)
                AND petcod IN (
                    SELECT DISTINCT petcodpet
                    FROM contatopet
                    WHERE apadrinhou = true
                ) and petcod = '$codpet'";
                    $sql = $consulta . " LIMIT $startIndex, $cardsPerPage;";
                    $result = $mysqli->query($sql);
                    $counter = 0;

                    // Verifique se a consulta retornou resultados.
                    if ($result) {
                        echo '<div class="card-container">';

                        while ($pet_data = mysqli_fetch_assoc($result)) {
                            $petcodpet = $pet_data['petcod'];
                            $sql_imagem = "SELECT * FROM imagem WHERE petcodpet='$petcodpet';";
                            $result_imagem = $mysqli->query($sql_imagem);
                            if ($result_imagem && $row_imagem = mysqli_fetch_assoc($result_imagem)) {
                                $imageData = $row_imagem['img'];
                                $imageType = 'image/jpeg';
                            } else {
                                // Se não houver imagem, carregue uma imagem padrão.
                                $imagePath = './assets/semimg.png';
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
                            echo '<h3 style="color: black;">7' . $pet_data['nomepet'] . '</h3>';
                            echo '<img class="imgcardpet" src="data:' . $imageType . ';base64,' . base64_encode($imageData) . '" alt="Pet Image">';
                            echo '<p class="title"></p>';
                            echo '<h3 style="color: black;">' . $pet_data['nomepet'] . '</h3>';
                            echo '<h4 style="color: black;">' . $pet_data['estadop'] . ", " . $pet_data['cidadep'] . '</h4>';
                            echo '<button class="butcardpet"><a href="./anuncio/perfilpet.php?petcod=' . $pet_data['petcod'] . '"> Vizualizar </a></button>';
                            echo '</div>'; // Fecha o card

                            $counter++;

                            if ($counter === 10) {
                                break;
                            }
                        }

                        echo '</div>'; // Fecha o contêiner de cartões

                    } else {
                        // Trate o caso em que a consulta não retornou resultados.
                        echo "<h4 class= 'frasemidle'>Nenhum Pet encontrado.</h4>";
                        echo "aa: " . $mysqli->error;
                    }
                }
                $total = mysqli_num_rows($result);
                if ($total == 0) {
                    echo "<h4 class='frasemidle'> Nenhum Pet encontrado.</h4>";
                }
            } else {
                echo 'Nenhuma adoção encontrada.a';
                echo "aa: " . $mysqli->error;
            }
        }
    } else {
        echo 'Nenhuma adoção encontrada.';
        echo "aa: " . $mysqli->error;
    }


    echo "</div>";
    echo "<div id='content_op5' style='display: none;'>";

    $consulta = "SELECT * FROM contatoserv WHERE contratou=true and pcodp = '$cod'";
    $result = $mysqli->query($consulta);
    if ($result) {
        while ($row = mysqli_fetch_assoc($result)) {
            $codpet = $row['scodserv'];
            $cardsPerPage = 10;
            $page = isset($_GET['page']) ? $_GET['page'] : 1;
            $startIndex = ($page - 1) * $cardsPerPage;

            $consulta = "SELECT * FROM servico WHERE (aprovacaoserv = true OR aprovacaoserv IS NULL)
            AND servcod IN (
                SELECT DISTINCT scodserv
                FROM contatoserv
                WHERE contratou = true
            ) and servcod = '$codpet'";

            $sql = $consulta . " LIMIT $startIndex, $cardsPerPage;";
            $result = $mysqli->query($sql);
            $counter = 0;
            if ($result) {
                echo '<div class="card-container">';

                while ($pet_data = mysqli_fetch_assoc($result)) {
                    $petcodpet = $pet_data['servcod'];
                    $sql_imagem = "SELECT * FROM imagem WHERE servicocodserv='$petcodpet';";
                    $result_imagem = $mysqli->query($sql_imagem);

                    if ($result_imagem && $row_imagem = mysqli_fetch_assoc($result_imagem)) {
                        $imageData = $row_imagem['img'];
                        $imageType = 'image/jpeg';
                    } else {
                        // Se não houver imagem, carregue uma imagem padrão.
                        $imagePath = './assets/semimg.png';
                        $imageData = file_get_contents($imagePath);
                        $imageType = mime_content_type($imagePath);
                    }

                    $precoserv = number_format((float)$pet_data['preco'], 2, ',', '');


                    echo '<div class="cards1">'; // Abre um card
                    echo '<h3 style="color: black;">' . $pet_data['nomeserv'] . '</h3>';
                    echo '<img class="imgcardpet" src="data:' . $imageType . ';base64,' . base64_encode($imageData) . '" alt="Pet Image">';
                    echo '<p class="title"></p>';
                    echo '<h3 style="color: black;">' . $pet_data['nomeserv'] . '</h3>';
                    echo '<h4 style="color: black;">' . $pet_data['estados'] . ", " . $pet_data['cidades'] . '</h4>';
                    echo '<h4 style="color: black;" class="price" data-price="' . $precoserv . '">R$' . $precoserv . '</h4>';
                    echo '<button class="butcardpet"><a href="./anuncio/perfilserv.php?servcod=' . $pet_data['servcod'] . '">Contratar</a></button>';
                    echo '</div>'; // Fecha o card

                    $counter++;

                    if ($counter === 20) {
                        break;
                    }
                }

                echo "</div>";
            } else {
                // Trate o caso em que a consulta não retornou resultados.
                echo 'Nenhum Serviço encontrado.';
                echo "aa: " . $mysqli->error;
            }
        }
        $total = mysqli_num_rows($result);
        if ($total == 0) {
            echo "<h4 class='frasemidle'>Nenhum Serviço Contratado encontrado.</h4>";
        }
    } else {
        echo 'Nenhuma Serviço Contratado encontrada.';
    }


    echo "</div>";

    ?>

    <div id="popupModal" class="modal">
        <div class="modal-content">
            <?php include('editimgperfil.php'); ?>
        </div>
    </div>
    <div id="popupModal2" class="modal">
        <div class="modal-content">
            <?php include('editimgbanner.php'); ?>
        </div>
    </div>
    <script>
        document.getElementById('openPopupBtn').addEventListener('click', function() {
            document.getElementById('popupModal').style.display = 'block';
        });

        document.getElementById('closePopupBtn').addEventListener('click', function() {
            document.getElementById('popupModal').style.display = 'none';
        });

        window.addEventListener('click', function(event) {
            if (event.target === document.getElementById('popupModal')) {
                document.getElementById('popupModal').style.display = 'none';
            }
        });
    </script>

    <script>
        document.getElementById('openPopupBtn2').addEventListener('click', function() {
            document.getElementById('popupModal2').style.display = 'block';
        });

        document.getElementById('closePopupBtn2').addEventListener('click', function() {
            document.getElementById('popupModal2').style.display = 'none';
        });

        window.addEventListener('click', function(event) {
            if (event.target === document.getElementById('popupModal2')) {
                document.getElementById('popupModal2').style.display = 'none';
            }
        });
    </script>

    <style>
        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.7);
            z-index: 999;
        }

        .modal-content {
            border-radius: 20px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #ff6600;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
        }
    </style>

    <br>


    <script>
        let $bselecionado = 'op1'; // Inicializa a variável com uma opção padrão

        function updateSelected(option) {
            $bselecionado = option;
            updateContent();
        }

        function updateContent() {
            const contentOp1 = document.getElementById('content_op1');
            const contentOp2 = document.getElementById('content_op2');
            const contentOp3 = document.getElementById('content_op3');
            const contentOp4 = document.getElementById('content_op4');
            const contentOp5 = document.getElementById('content_op5');
            const contentOp6 = document.getElementById('content_op6');
            const contentOp7 = document.getElementById('content_op7');
            const contentOp8 = document.getElementById('content_op8');

            contentOp1.style.display = $bselecionado === 'op1' ? 'block' : 'none';
            contentOp2.style.display = $bselecionado === 'op2' ? 'block' : 'none';
            contentOp3.style.display = $bselecionado === 'op3' ? 'block' : 'none';
            contentOp4.style.display = $bselecionado === 'op4' ? 'block' : 'none';
            contentOp5.style.display = $bselecionado === 'op5' ? 'block' : 'none';
            contentOp6.style.display = $bselecionado === 'op6' ? 'block' : 'none';
            contentOp7.style.display = $bselecionado === 'op7' ? 'block' : 'none';
            contentOp8.style.display = $bselecionado === 'op8' ? 'block' : 'none';
        }

        // Chama updateContent inicialmente para exibir o conteúdo padrão com base no valor inicial de $bselecionado
        updateContent();
    </script>

    <?php include('./lib/footer.php'); ?>



</body>

</html>