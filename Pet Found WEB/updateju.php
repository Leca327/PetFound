<html>

<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="./login/loading.css">
</head>

<body bgcolor="#ff6600">

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <div class="loading-card">
        <div class="loading-message">Redirecionando</div>
        <div class="loading-spinner-container">
            <div class="loading-spinner"></div>
        </div>
    </div>
    
    <?php
    include('./lib/dbconnect.php');

    if ($mysqli) {
        if (isset($_POST['ra']) && isset($_POST['nome']) && isset($_POST['email']) && isset($_POST['cont']) && isset($_POST['nik']) && isset($_POST['end']) && isset($_POST['cep']) && isset($_POST['uf']) && isset($_POST['cid']) && isset($_POST['num']) && isset($_POST['end']) && isset($_POST['tij'])) {
            // Recebendo os dados do formulário
            $usuario = $_POST['nik'];
            $nome = $_POST['nome'];
            $email = $_POST['email'];
            $celular = $_POST['cont'];
            $ra = $_POST['ra'];
            $tipoj = $_POST['tij'];
            $end = $_POST['end'];
            $cep = $_POST['cep'];
            $uf = $_POST['uf'];
            $cid = $_POST['cid'];
            $brr = $_POST['brr'];
            $num = $_POST['num'];
            $cpt = $_POST['cpt'];
            session_start();
            $nikanti = $_SESSION['usuario'];
            if (strlen($nome) <= 50 && strlen($usuario) <= 50 && strlen($email) <= 100 && strlen($end) <= 50 && strlen($brr) <= 25 && strlen($num) <= 5 && strlen($cpt) <= 25) {
                // Execute a consulta para verificar se os dados já existem no banco de dados
                $query = "SELECT * FROM pessoa WHERE (emailp = '$email' OR contatop = '$celular' OR nickname = '$usuario') AND nickname != '$nikanti'";

                $result = mysqli_query($mysqli, $query);
                $total = mysqli_num_rows($result);
                if ($total > 0) {
                    while ($row = $result->fetch_assoc()) {
                        if ($row['emailp'] == $email) {
                            echo "<script>
                            Swal.fire(
                                'Erro na Atualização',
                                'Email já cadastrado',
                                'error'
                            ).then(() => {
                                window.history.back();
                            });
                        </script>";
                        }
                        if ($row['contatop'] == $celular) {
                            echo "<script>
                            Swal.fire(
                                'Erro na Atualização',
                                'Celular já está cadastrado',
                                'error'
                            ).then(() => {
                                window.history.back();
                            });
                        </script>";
                        }
                        if ($row['nickname'] == $usuario) {
                            echo "<script>
                            Swal.fire(
                                'Erro na Atualização',
                                'Usuário já existe',
                                'error'
                            ).then(() => {
                                window.history.back();
                            });
                        </script>";
                        }
                    }
                } else {
                    $query = "SELECT * FROM admin WHERE usera = '$usuario'";
                    $result = mysqli_query($mysqli, $query);
                    $total = mysqli_num_rows($result);
                    if ($total > 0) {
                        echo "<script>
                        Swal.fire(
                            'Erro na Atualização',
                            'Usuário já existe',
                            'error'
                        ).then(() => {
                            window.history.back();
                        });
                    </script>";
                    } else {
                        $sqlSelect = "SELECT * FROM pessoa p JOIN endereco e ON p.endcodend = e.endcod JOIN juridica j ON p.pcod = j.cod_p WHERE nickname = '$nikanti';";
                        $result = mysqli_query($mysqli, $sqlSelect);

                        if ($result->num_rows > 0) {
                            $row = $result->fetch_assoc();
                            $codend = $row['endcodend'];
                            $cod = $row['pcod'];

                            date_default_timezone_set('America/Sao_Paulo');
                            $hora_atual = date("Hi");
                            $data_atual = date("Ymd");

                            $sql2 = "UPDATE endereco SET CEP = '$cep', uf = '$uf', cidade = '$cid', bairro = '$brr', cmpt = '$cpt', numero = '$num', endereco = '$end' WHERE endcod = '$codend'";
                            $sql1 = "UPDATE pessoa SET emailp = '$email',contatop = '$celular', nomep = '$nome', nickname='$usuario' WHERE 
        nickname = '$nikanti'";
                            $sql3 = "UPDATE juridica SET ramo_ativ = '$ra', tipoj = '$tipoj' WHERE cod_p = '$cod'";

                            if (mysqli_query($mysqli, $sql1) && mysqli_query($mysqli, $sql2) && mysqli_query($mysqli, $sql3)) {
                                echo "<script>
        Swal.fire(
            'Atualizado com sucesso',
            'Dados guardados',
            'success'
        ).then(() => {
            window.history.back();
        });
    </script>";

                                $_SESSION["usuario"] = $usuario;
                            } else {
                                echo "Erro: " . $mysqli->error;
                                echo "<script>
        Swal.fire(
            'Erro na Atualização',
            'Erro ao cadastrar no banco de dados',
            'error'
        ).then(() => {
            window.history.back();
        });
    </script>";
                            }
                        } else {
                            echo "Erro: " . $mysqli->error;
                            echo "erro " . $usuario;
                        }
                    }
                }
            } else {
                echo "<script>
                Swal.fire(
                'Excedência de Caráter', 
                'Você ultrapassou o limite de caracteres. Email só pode ter 100. Nome, Endereço, Nome fantasia, Ramo/Atividade só pode ter 50. Senha, complemento, cidade, bairro só pode ter 25. Número só pode ter 5',
                'error'
                ).then(() => {
                    window.history.back();
                });
                </script>";
            }
        } else {
            echo "<script>
            window.history.back();
                alert('Preencha todos os campos obrigatórios.');
            </script>";
        }
    } else {
        die("Falha ao conectar com o banco de dados: " . mysqli_connect_error());
    }
    mysqli_close($mysqli);
    ?>
</body>

</html>