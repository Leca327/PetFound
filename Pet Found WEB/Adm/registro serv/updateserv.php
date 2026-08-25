<html>

<head>

</head>

<body bgcolor="#ff6600">

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>

</html>
<?php
include('../../lib/dbconnect.php');
session_start();
if (isset($_POST['selected_pets'])) {
    $selectedPets = $_POST['selected_pets'];
    $petNames = $_POST['pet_names'];
    $mot = $_POST['motivo_reprovacao'];
    $adm = $_SESSION['admin'];


    if (strlen($mot) <= 255) {

        $sql = "SELECT * FROM admin WHERE usera = '$adm'";

        // Executa a consulta
        $result = $mysqli->query($sql);

        // Verifica se a consulta foi executada com sucesso
        if ($result) {
            // Processa os resultados, se houver algum
            if ($result->num_rows > 0) {
                while ($row = $result->fetch_assoc()) {
                    // Acesse o valor do campo desejado
                    $codadm = $row['admcod']; // Substitua 'nome_do_campo' pelo nome do campo desejado

                }
            } else {
                //echo "Nenhum resultado encontrado.";
            }

            // Fecha a conexão com o banco de dados
        } else {
            // echo "Erro na consulta SQL: " . $conn->error;
        }


        if (isset($_POST["approve_button"])) {
            $updateQuery = "UPDATE servico SET aprovacaoserv= 1, motivorepserv=null, admin_codadmn='" . $codadm . "' WHERE servcod='" . $selectedPets . "'";

            if (mysqli_query($mysqli, $updateQuery)) {
                $query = "SELECT * FROM servico WHERE servcod = '" . $selectedPets . "'";
                $result = mysqli_query($mysqli, $query);
                if ($row = mysqli_fetch_assoc($result)) {
                    $temporario = $row['pessoa_codp'];
                    $nmpet = $row['nomeserv'];
                }

                $hora_atual = date("Hi");
                $data_atual = date("Ymd");

                $cortecod = substr($temporario, 0, 5);
                $notifications_name = "$nmpet Aprovado";
                $mensagem = "Seu anuncio de Serviço está no ar e disponivel para todos verem.";

                $cod = "NOTAPVSCV" . $cortecod . $hora_atual . $data_atual;
                $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,s_codserv,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$temporario','$selectedPets','$dtatual','$hratual')";

                // Executando a instrução SQL para inserir o pet na tabela "pet"
                if (mysqli_query($mysqli, $sql)) {
                    echo "<script>
                        Swal.fire(
                            'Serviço " . $petNames . " Aprovado Com Sucesso',
                            'Dados Guardados',
                            'success'
                        ).then(() => {
                            window.history.back();
                        });
                    </script>";
                }
            } else {
                echo "Erro ao atualizar o pet: " . mysqli_error($mysqli);
            }
        } elseif (isset($_POST["enviar_reprovacao"])) {
            if (!empty($mot)) {
                $updateQuery = "UPDATE servico SET aprovacaoserv= false ,  admin_codadmn='" . $codadm . "' ,motivorepserv='$mot'
            WHERE servcod='" . $selectedPets . "'";


                if (mysqli_query($mysqli, $updateQuery)) {
                    $query = "SELECT * FROM servico WHERE servcod = '" . $selectedPets . "'";
                    $result = mysqli_query($mysqli, $query);
                    if ($row = mysqli_fetch_assoc($result)) {
                        $temporario = $row['pessoa_codp'];
                        $nmpet = $row['nomeserv'];
                    }

                    $hora_atual = date("Hi");
                    $data_atual = date("Ymd");

                    $cortecod = substr($temporario, 0, 5);
                    $notifications_name = "$nmpet Reprovado";
                    $mensagem = "Seu anuncio de Serviço precisa mudar informações para ir ao ar.";

                    $cod = "NOTREPSCV" . $cortecod . $hora_atual . $data_atual;
                    $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                    $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                    $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,s_codserv,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$temporario','$selectedPets','$dtatual','$hratual')";

                    // Executando a instrução SQL para inserir o pet na tabela "pet"
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
                        Swal.fire(
                            'Serviço " . $petNames . " Reprovado Com Sucesso',
                            'Dados Guardados',
                            'success'
                        ).then(() => {
                            window.history.back();
                        });
                    </script>";
                    }
                } else {
                    echo "Erro ao atualizar o pet: " . mysqli_error($mysqli);
                }
            } else {
                echo "<script>
                                Swal.fire(
                                    'Erro',
                                    'O motivo de reprovação está vazia',
                                    'error'
                                ).then(() => {
                                    window.history.back();
                                });
                            </script>";
            }
        } elseif (isset($_POST["noavaliar_button"])) {
            $updateQuery = "UPDATE servico SET aprovacaoserv= null , motivorepserv=null,  admin_codadmn=null
            WHERE servcod='" . $selectedPets . "'";

            if (mysqli_query($mysqli, $updateQuery)) {
                echo "<script>
                        Swal.fire(
                            'Serviço " . $petNames . " deixado em espera Com Sucesso',
                            'Dados Guardados',
                            'success'
                        ).then(() => {
                            window.history.back();
                        });
                    </script>";
            } else {
                echo "Erro ao atualizar o pet: " . mysqli_error($mysqli);
            }
        } else {
            echo "<script>
                        Swal.fire(
                            'Campos Vazios',
                            'Erro',
                            'error'
                        ).then(() => {
                            window.history.back();
                        });
                    </script>";
        }
    } else {
        echo "<script>
                    Swal.fire(
                    'Excedência de Caráter', 
                    'Você ultrapassou o limite de caracteres. Motivo de reprovação só pode ter 255',
                    'error'
                    ).then(() => {
                        window.history.back();
                    });
                    </script>";
    }
} else {
    echo "<script>
                    Swal.fire(
                    'Erro',
                    'Você não selecionou o serviço',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
}
