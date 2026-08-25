<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./lib/padrao.css" />



    <style>
        .popup2 {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 9999;
        }

        .cardp>h1 {
            position: absolute;
            top: 9vh;
            left: 12vh;
            color: var(--fundosecundario1);
            font-weight: 600;
            margin: 0;
            font-size: 3vh;
        }

        .cardp>h4 {
            position: absolute;
            top: 6vh;
            left: 10vh;
            color: rgb(255, 102, 0);
            font-weight: 600;
            margin: 0;
            font-size: 3vh;
        }

        .cardp>h3 {
            position: absolute;
            top: 14vh;
            left: 7vh;
            color: var(--textoprincipal);
            font-weight: 600;
            margin: 0;
        }

        .cardp>h5 {
            position: absolute;
            top: 24vh;
            left: 7vh;
            color: var(--textoprincipal);
            font-weight: 600;
            margin: 0;
            font-size: 1.8vh;
        }

        .cancel {
            color: aqua;
        }


        .cardp {
            height: 30vh;
            text-align: center;
            margin-right: 5vh;
            width: 60vh;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            padding: 30px 35px;
            background: rgb(43, 17, 0);
            border-radius: 20px;
            box-shadow: 0px 10px 40px #00000056;

        }

        .bt2 {
            position: absolute;
            top: 25vh;
            left: 44vh;
            width: 15vh;
            padding: 16px 0px;
            margin: 25px;
            border: none;
            border-radius: 8px;
            outline: none;
            text-transform: uppercase;
            font-weight: 800;
            letter-spacing: 3px;
            color: rgb(43, 17, 0);
            background: var(--fundoprincipal1);
            cursor: pointer;

        }

        .bt1 {
            position: absolute;
            top: 25vh;
            left: 27vh;
            width: 15vh;
            padding: 16px 0px;
            margin: 25px;
            border: none;
            border-radius: 8px;
            outline: none;
            text-transform: uppercase;
            font-weight: 800;
            letter-spacing: 3px;
            color: rgb(43, 17, 0);
            background: rgb(255, 102, 0);
            cursor: pointer;

        }

        .bt1:hover {
            transition: 0.5s;
            background-color: rgb(255, 50, 0);
        }

        .bt2:hover {
            transition: 0.5s;
            background-color: rgb(255, 50, 0);
        }


        /* Estilos do pop-up */
        .popup-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 10%;
            height: 10%;
            background-color: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 9999;
        }

        .popuppet {
            padding: 20px;
            border-radius: 5px;
            max-width: 600px;
            text-align: center;
        }
    </style>
    <title>Pet Found - Cadastro</title>
</head>

<body>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
    <?php
    $caminhoArquivo1 = './cadpetserv/cadpet.php';
    $caminhoArquivo2 = '../cadpetserv/cadpet.php';
    if (file_exists($caminhoArquivo1)) {
        $url1 = './cadpetserv/cadpet.php';
        $url2 = './cadpetserv/cadserv.php';
    } else {
        $url1 = '../cadpetserv/cadpet.php';
        $url2 = '../cadpetserv/cadserv.php';
    }
    $ddd = substr($contato, 0, 2);
    $parte1 = substr($contato, 2, 5);
    $parte2 = substr($contato, 7);
    $cont = "($ddd) $parte1-$parte2";
    echo "<div class='popuppet'>
        <div class='cardp'>
            <h4>Número para contato:</h4>
            <h1>" . $cont . "</h1>
            <h3> Ao clicar em 'Criar Contato', você mostra interesse em ligar para o anunciante e permite o registro das ações do contato, podendo ser nomeado como Contratante e dispor de sua avaliação.
            </h3>
            <br>
            <h5> Já em 'Cancelar', você opta por não ligar para o anunciante.
            </h5>
            <form method='post'>
                <button class='bt1' name='btnCriarContato'>Criar contato</button>
            </form>
            <button class='bt2' onclick='closePopuppet()'>Cancelar</button>
        </div>
    </div>";
    if (isset($_POST['btnCriarContato'])) {
        $sqlSelect = "SELECT * FROM pessoa WHERE nickname = '" . $_SESSION['usuario'] . "'";
        $result = mysqli_query($mysqli, $sqlSelect);

        if ($result->num_rows > 0) {
            // Assuming 'nomepet' is a column in the 'pet' table
            $row = $result->fetch_assoc();
            $codpes = $row['pcod'];
            $query = "SELECT * FROM `contatoserv` WHERE pcodp = '" . $codpes . "' AND scodserv= '" . $servcod . "'";
            $result = mysqli_query($mysqli, $query);
            if ($row = mysqli_fetch_assoc($result)) {
                echo "<script> Swal.fire(
                        'Erro no Contato',
                        'Você já criou um contato com o anunciante.',
                        'error'
                    ).then(() => {
                        window. history. back();
                    });
                    </script>";
            } else {

                $query = "SELECT pcod FROM pessoa WHERE nickname = '" . $_SESSION['usuario'] . "'";
                $result = mysqli_query($mysqli, $query);
                if ($row = mysqli_fetch_assoc($result)) {

                    if ($nickp === $_SESSION['usuario']) {
                        echo "<script> Swal.fire(
                    'Erro no Contato',
                    'Você não pode criar um contato com você mesmo.',
                    'error'
                ).then(() => {
                    window. history. back();
                }); 
                </script>";
                    } else {

                        date_default_timezone_set('America/Sao_Paulo');
                        $hora_atual = date("Hi");
                        $data_atual = date("Ymd");
                        // Obtém a primeira letra do nome do pet
                        $inicial_p = substr($codpes, 0, 5);

                        $inicial_pet = substr($servcod, 0, 6);
                        $codcont = "CONT" . $inicial_pet . $hora_atual . $inicial_p . $data_atual;

                        $pf = $row['pcod'];
                        $tip = substr($pf, 0, 2);


                        $logado = $row['pcod'];


                        $hora_atual2 = date("H:i:s");
                        $data_atual2 = date("Y-m-d");

                        $sql = "INSERT INTO contatoserv(pcodp, scodserv, codconts, dtcs, hrcs) 
                                        VALUES('$pf','$servcod','$codcont','$data_atual2','$hora_atual2')";

                        // Executando a instrução SQL para inserir o pet na tabela "pet"
                        if (mysqli_query($mysqli, $sql)) {
                            $query = "SELECT * FROM servico WHERE servcod = '" . $servcod . "'";
                            $result = mysqli_query($mysqli, $query);
                            if ($row = mysqli_fetch_assoc($result)) {
                                $temporario = $row['pessoa_codp'];
                                $nmpet = $row['nomeserv'];
                            }

                            $hora_atual = date("Hi");
                            $data_atual = date("Ymd");

                            $cortecod = substr($temporario, 0, 5);
                            $notifications_name = "Contato para $nmpet";
                            $mensagem = $_SESSION['usuario'] . " criou um contato para receber seus serviços.";
                            
                            $cod = "NOTCONTSVC" . $cortecod . $hora_atual. $data_atual;
                            $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                                $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                            $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatoserv,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$temporario','$codcont','$dtatual','$hratual')";

                            // Executando a instrução SQL para inserir o pet na tabela "pet"
                            if (mysqli_query($mysqli, $sql)) {
                                echo "<script>
                                Swal.fire(
                                    'Criado um contato!',
                                    'O anunciante está esperando por sua ligação',
                                    'success'
                                ).then(() => {
                                    window. history. back();
                                });
                            </script>";
                            }
                        } else {
                            echo "<script> Swal.fire(
                                'Erro no Contato',
                                'Contato não pode ser criado.',
                                'error'
                            ).then(() => {
                                window. history. back();
                            });
                            </script>";
                        }
                    }
                } else {
                    echo "N encontrou pessoa" . $_SESSION['usuario'];
                }
            }
        } else {
            //n encontrou
        }
    }

    ?>
    <div id="popup2" class="popup2">
        <div class="popuppet">

            <script>
                document.close();
            </script>
            <!--<a class="close-button" onclick="closePopup()"> -->
        </div>
    </div>


</body>

</html>