<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pet Found</title>
    <link rel="stylesheet" href="./padrao.css">
    <link rel="stylesheet" href="./navbar.css">
    <!--<link rel="stylesheet" href="responsivonavbar.css">-->
    <link href="https://cdn.jsdelivr.net/npm/remixicon@3.5.0/fonts/remixicon.css" rel="stylesheet">
    <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/js-cookie/3.0.1/js.cookie.min.js"></script>

</head>
<style>
    .imgperfil {
        text-align: center !important;
        height: 50px !important;
        width: 50px !important;
        border-radius: 100% !important;

    }

    .round {
        width: 20px;
        height: 20px;
        border-radius: 50%;
        position: relative;
        background: #ff0000;
        display: inline-block;
        padding: 0.3rem 0.2rem !important;
        margin: 0.3rem 0.2rem !important;
        left: -18px;
        top: 10px;
        z-index: 99 !important;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .round>span {
        /* ... */
        color: white;
        display: block;
        text-align: center;
        font-size: 1rem !important;
        padding: 0 !important;
        margin: 0;
        /* Adicione esta linha para remover o espaço extra */
    }

    .triangulo {
        position: absolute;
        top: -15px;
        left: 125px;
        width: 10px;
        height: 0;
        border-left: 15px solid transparent;
        border-right: 15px solid transparent;
        border-bottom: 17.7px solid var(--fundosecundario2);
        /* Altere a cor como desejado */
    }

    #list {
        /*lista notification*/
        display: none;
        top: 70px;
        position: absolute;
        left: 1vh;
        background: var(--fundosecundario2);
        z-index: 100 !important;
        width: 30vh;
        margin-left: -37px;
        padding: 0 !important;
        margin: 0 auto !important;
        border-radius: 10px;


    }

    .message>span {
        /*notification name*/
        width: 100%;
        display: block;
        color: black;
        text-align: justify;
        padding: 0.3rem !important;
        line-height: 0.5rem !important;
        font-weight: bold;
        font-size: 1.2rem !important;

    }

    .message {
        /*retangulo notifications*/
        margin: -0.1rem 0.8rem !important;
        padding: 0.5rem 0 !important;
        text-align: center;
        display: block;
        border-radius: 10px;

    }

    .message>.msg {
        /*mensagem notifications*/
        color: white;
        padding: 0.2rem 0.2rem !important;
        font-weight: bold;
        display: block;
        font-size: 0.8rem !important;
        word-wrap: break-word;
    }

    .fa-bell {
        position: relative;
        /* Adicione esta linha */
        font-size: 32px;
        color: white;
    }
</style>

<body>

    <?php
  session_start();
   
    //echo "<script>alert('aaa: ".$_SESSION["admin"]."');</script>";
    include('dbconnect.php');

  

    $caminhoArquivo1 = './index.php';
    $caminhoArquivo2 = '../index.php';
    if (file_exists($caminhoArquivo1)) {
        $url = './index.php';
    } else {
        $url = '../index.php';
    }

    $currentURL = $_SERVER['REQUEST_URI'];

    // Verifica se a URL corresponde à página searchpet com tipanunc=Adocao
    $isSearchPetAdocao = strpos($currentURL, 'searchpet.php') !== false && strpos($currentURL, 'tipanunc=Adocao') !== false;
    $isSearchPetPadrinho = strpos($currentURL, 'searchpet.php') !== false && strpos($currentURL, 'tipanunc=Padrinho') !== false;
    // Verifica se a URL corresponde à página searchserv.php
    $isSearchServ = strpos($currentURL, 'searchserv.php') !== false;


    $caminhoArquivo5 = './cadpetserv/searchpet.php';
    $caminhoArquivo6 = '../cadpetserv/searchpet.php';
    $caminhoArquivo7 = './searchpet.php';
    if (file_exists($caminhoArquivo6)) {
        $url7 = '../cadpetserv/searchpet.php?typepet=allespe&genderpet=allgender&Porte=allporte&idade=allidade&tipanunc=Adocao&state=allstates&citypet=allcities&ordenacao=mais_novos';
        $url8 = '../cadpetserv/searchpet.php?typepet=allespe&genderpet=allgender&Porte=allporte&idade=allidade&tipanunc=Padrinho&state=allstates&citypet=allcities&ordenacao=mais_novos';
        $url9 = '../cadpetserv/searchserv.php';
    } else {
        $url7 = './cadpetserv/searchpet.php?typepet=allespe&genderpet=allgender&Porte=allporte&idade=allidade&tipanunc=Adocao&state=allstates&citypet=allcities&ordenacao=mais_novos';
        $url8 = './cadpetserv/searchpet.php?typepet=allespe&genderpet=allgender&Porte=allporte&idade=allidade&tipanunc=Padrinho&state=allstates&citypet=allcities&ordenacao=mais_novos';
        $url9 = './cadpetserv/searchserv.php';
    }

    echo "<header>
            
    <a href=" . $url . " class='logo'><span>Petfound</span></a>
    
    <ul class='navbar'>
        <li><a href='$url7' " . ($isSearchPetAdocao ? "class='active'" : "") . ">Quero adotar</a></li>
         <li><a href='$url8' " . ($isSearchPetPadrinho ? "class='active'" : "") . ">Quero apadrinhar</a></li>
        <li><a href='$url9' " . ($isSearchServ ? "class='active'" : "") . ">Quero Servi&ccedil;os</a></li>
        <li><a href='#' onclick='openPopup()'>Quero divulgar</a></li>
        
    </ul>";


    if (!isset($_SESSION["usuario"]) & !isset($_SESSION["admin"])) {
        $caminhoArquivo1 = 'login/login.php';
        $caminhoArquivo2 = '../login/login.php';
        if (file_exists($caminhoArquivo1)) {
            $url1 = 'login/login.php';
            $url2 = 'login/escolha.php';
        } else {
            $url1 = '../login/login.php';
            $url2 = '../login/escolha.php';
        }
        echo "
                        <div class='main'>
                            <a href='" . $url1 . "' class='user'>Logar</a>
                            <a href='" . $url2 . "'> Cadastrar</a>
                            <div class='bx bx-menu' id='menu-icon'></div>
                        </div>
                    
                                        ";
    } else {
        if (isset($_SESSION["usuario"]) & !isset($_SESSION["admin"])) {
            $user = $_SESSION["usuario"];
            $insert_query = "SELECT * from pessoa where nickname='" . $user . "';";

            $result = mysqli_query($mysqli, $insert_query);

            $row = mysqli_fetch_assoc($result);
            if ($row) {
                $pcod = $row['pcod'];
                $find_notifications = "SELECT * FROM notificacao WHERE pessoa_codpessoa = '$pcod'  AND active=true";
                $result = mysqli_query($mysqli, $find_notifications);
                $visu = mysqli_num_rows($result);


                $query2 = "SELECT * FROM notificacao WHERE pessoa_codpessoa = '$pcod' ORDER BY notcod DESC";
                $result2 = mysqli_query($mysqli, $query2);
                $total = mysqli_num_rows($result2);
                while ($rows = mysqli_fetch_assoc($result2)) {

                    $notifications_data[] = array(
                        "notifications_name" => $rows['notifications_name'],
                        "mensagem" => $rows['mensagem'],
                        "notcod" => $rows['notcod'],
                        "visto" => $rows['active'],
                    );
                }

                if (isset($user))
                    $caminhoArquivo1 = './config.php';
                $caminhoArquivo2 = '../config.php';
                $caminhoArquivo3 = '../../../logout.php';
                if (file_exists($caminhoArquivo1)) {
                    $url1 = './perfil.php';
                    $url2 = './logout.php';
                    $url3 = './painel.php';
                    $url4 = './config.php';
                } else if (file_exists($caminhoArquivo3)) {
                    $url1 = '../../../perfil.php';
                    $url2 = '../../../logout.php';
                    $url3 = '../../../painel.php';
                    $url4 = '../../../config.php';
                } else if (file_exists($caminhoArquivo2)) {
                    $url1 = '../perfil.php';
                    $url2 = '../logout.php';
                    $url3 = '../painel.php';
                    $url4 = '../config.php';
                }
                $sql_imagem = "SELECT * FROM pessoa WHERE nickname='$user';";
                $result_imagem = $mysqli->query($sql_imagem);

                if ($row_imagem = mysqli_fetch_assoc($result_imagem)) {

                    if ($row_imagem['imgperfil'] != null) {
                        $imageData = $row_imagem['imgperfil'];
                        $imageType = 'image/jpeg';
                    } else {
                        // Defina uma imagem padrão caso nenhuma imagem seja encontrada
                        //$imagePath = './adm/usuario.jpg';
                        // $imageData = file_get_contents($imagePath);
                        //$imageType = mime_content_type($imagePath);
                    }
                } else {
                    echo "Não encontrou o adm";
                }

                echo "<div class='main'>";
                if ($row_imagem['imgperfil'] != null) {
                    // Exibe a imagem
                    echo "<div class='imgperfil2'><img src='data:$imageType;base64," . base64_encode($imageData) . "' alt=''></div>";
                } else {
                    // Exibe o ícone de usuário
                    echo "<i class='bx bx-user-circle'></i>";
                }

                echo "
                <a href='" . $url1 . "' class='user'>" . $user . "</a>
                <div class='bx bx-menu' id='menu-icon'></div>
                <ul class='dropdown'>
                    <li><a href='" . $url1 . "'>Perfil</a></li>
                    <li><a href='" . $url3 . "'>Painel</a></li>
                    <li><a href='" . $url4 . "'>Configuração</a></li>
                    <li><a href='" . $url2 . "'>Deslogar</a></li>
                </ul>
                <i class='fa fa-bell' id='over' data-value='" . $visu . "' style='z-index:99 !important;font-size:32px;color:black;margin:0.5rem 0.4rem !important;'></i>
                            ";

                if (!empty($visu)) {
                    echo "<div class='round' id='bell-count' data-value='" . $visu . "'><span>" . $visu . "</span>
                    </div>";
                }
                
                // if ($total > 0) {
                if ($total > 0) {
                    echo " <div id='list'><div class='triangulo'></div>";
                    foreach ($notifications_data as $list_rows) {
                        $url7 = '../lib/deactive.php';
                        if (file_exists($caminhoArquivo6)) {
                            $url7 = '../lib/deactive.php?codnot=' . $list_rows['notcod'];
                        } else {
                            $url7 = './lib/deactive.php?codnot=' . $list_rows['notcod'];
                        }
                        if ($list_rows['visto'] == true) {
                            
                            echo " <a class='hvmsg' href='" . $url7 . "'>
                                    <li id='message_items'>
                                        <div class='message alert alert-warning' style='background: #ff924b;' data-id='" . $list_rows['notcod'] . "'>
                                    " . $list_rows['notifications_name'] . "
                                        <div class='msg'>
                                            <p>" . $list_rows['mensagem'] . "</p>
                                        </div>
                                        </div>
                                    </li></a>";
                        } else {
                            
                            echo " <a class='hvmsg' href='" . $url7 . "'>
                                    <li id='message_items'>
                                    <div class='message alert alert-warning' style='background: #ff6600;' data-id='" . $list_rows['notcod'] . "'>
                                    " . $list_rows['notifications_name'] . "
                                        <div class='msg'>
                                            <p>" . $list_rows['mensagem'] . "</p>
                                        </div>
                                        </div>
                                    </li></a>";
                        }
                    }
                    //}
                    echo "<a href='./lib/deactive.php?todos=true'><p id='vsltd'>Vizualizar todos</></a>
                </div>";
                }


                echo " </div>";
            } else {
                // Nickname não encontrado na tabela pessoa
                echo "<a href='./logout.php'>Erro: Nickname não encontrado.</a>";

                exit; // ou redirecione o usuário para a página anterior
            }
        } else if (!isset($_SESSION["usuario"]) & isset($_SESSION["admin"])) {
            $user = $_SESSION["admin"];
            if (isset($user))
                $caminhoArquivo1 = './config.php';
            $caminhoArquivo2 = '../config.php';
            $caminhoArquivo3 = '../../../logout.php';
            if (file_exists($caminhoArquivo1)) {
                $url1 = './config.php';
                $url2 = './logout.php';
                $url3 = './Adm/paginaADM.php';
            } else if (file_exists($caminhoArquivo3)) {
                $url1 = '../../../config.php';
                $url2 = '../../../logout.php';
                $url3 = '../../../Adm/paginaADM.php';
            } else if (file_exists($caminhoArquivo2)) {
                $url1 = '../config.php';
                $url2 = '../logout.php';
                $url3 = '../Adm/paginaADM.php';
            }
            $sql_imagem = "SELECT * FROM admin WHERE usera='$user';";
            $result_imagem = $mysqli->query($sql_imagem);

            if ($row_imagem = mysqli_fetch_assoc($result_imagem)) {

                if ($row_imagem['imgadm'] != null) {
                    $imageData = $row_imagem['imgadm'];
                    $imageType = 'image/jpeg';
                } else {
                    // Defina uma imagem padrão caso nenhuma imagem seja encontrada
                    //$imagePath = './adm/usuario.jpg';
                    // $imageData = file_get_contents($imagePath);
                    // $imageType = mime_content_type($imagePath);
                }
            } else {
                echo "Não encontrou o adm";
            }

            echo "<div class='main'>";
            if ($row_imagem['imgadm'] != null) {
                // Exibe a imagem
                echo "<div class='imgperfil2'><img src='data:$imageType;base64," . base64_encode($imageData) . "' alt='' ></div>";
            } else {
                // Exibe o ícone de usuário
                echo "<i class='bx bx-user-circle'></i>";
            }
            echo "<a href='" . $url3 . "' class='user'>Admin: " . $user . "</a>
                <div class='bx bx-menu' id='menu-icon'></div>
                <ul class='dropdown'>
                    <li><a href='" . $url3 . "'>Painel</a></li>
                    <li><a href='" . $url1 . "'>Configuração</a></li>
                    <li><a href='" . $url2 . "'>Deslogar</a></li>
                </ul>";
        }
    }
    ?>
    </header>
    <div id="popup" class="popup">
        <div class="popup-content">
            <?php
            // Caminho do primeiro arquivo
            $caminhoArquivo1 = './escolha2.php';

            // Caminho do segundo arquivo
            $caminhoArquivo2 = '../escolha2.php';

            // Caminho do terceiro arquivo
            $caminhoArquivo3 = '../../escolha2.php';


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

            <a class="close-button" onclick="closePopup()">
                <svg class="x" xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 384 512">
                    <path d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z" />
                </svg></a>
        </div>
    </div>

    <script>
        let menu, navbar, dropdown, userLink, dropdownContent, timeoutId;

        document.addEventListener("DOMContentLoaded", function() {
            menu = document.querySelector('#menu-icon');
            navbar = document.querySelector('.navbar');
            dropdown = document.querySelector('.dropdown');
            userLink = document.querySelector('.user');
            dropdownContent = document.querySelector('.dropdown');

            menu.onclick = () => {
                menu.classList.toggle('bx-x');
                navbar.classList.toggle('open');
                dropdown.classList.toggle('open');
            };


            function openPopup() {
                document.getElementById('popup').style.display = 'block';
                document.addEventListener('keydown', handleEscapeKey);
            }

            function closePopup() {
                document.getElementById('popup').style.display = 'none';
                document.removeEventListener('keydown', handleEscapeKey);
            }

            function handleEscapeKey(event) {
                if (event.key === 'Escape') {
                    closePopup();
                }
            }

            userLink.addEventListener("mouseover", function() {
                clearTimeout(timeoutId); // Limpa o timeout anterior
                dropdownContent.style.display = 'block'; // Exibe o dropdown ao passar o mouse sobre o link de usuário
            });

            userLink.addEventListener("mouseout", function() {
                // Configura um timeout para ocultar o dropdown após 300ms
                timeoutId = setTimeout(function() {
                    dropdownContent.style.display = 'none';
                }, 300);
            });

            dropdownContent.addEventListener("mouseover", function() {
                clearTimeout(timeoutId); // Limpa o timeout para manter o dropdown visível
            });

            dropdownContent.addEventListener("mouseout", function() {
                // Configura um timeout para ocultar o dropdown após 300ms
                timeoutId = setTimeout(function() {
                    dropdownContent.style.display = 'none';
                }, 300);

                let bellIcon = document.getElementById('over');
                bellIcon.addEventListener('click', function(e) {
                    e.preventDefault();
                    toggleNotifications();
                });

                // Função para abrir/fechar as notificações
                function toggleNotifications() {
                    notificationsList.style.display = notificationsList.style.display === 'block' ? 'none' : 'block';
                }
            });
        });
    </script>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            // Lidar com o clique no ícone de sino
            $("#over").on('click', function(e) {
                e.preventDefault();
                toggleNotifications();
            });

            // Função para abrir/fechar as notificações
            function toggleNotifications() {
                $('#list').toggle();
            }
        });




        /*        function toggleNotifications() {
                    var list = document.getElementById('list');
                    if (list.style.display === 'block' || list.style.display === '') {
                        list.style.display = 'none';
                    } else {
                        list.style.display = 'block';
                    }
                }*/

        // Lidar com o clique no ícone de sino
        var bellIcon = document.getElementById('over');
        bellIcon.addEventListener('click', function(e) {
            //   e.preventDefault();
            toggleNotifications();

        });
    </script>
    <br><br><br><br>
</body>

</html>