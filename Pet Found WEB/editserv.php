<html>

<head>

</head>

<body bgcolor="#ff6600">

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>

</html>
<?php
include('./lib/dbconnect.php');


if ($_SERVER["REQUEST_METHOD"] == "POST") {

    if (isset($_POST["enviar-btn"])) {
        if (isset($_POST["petcod"])) {

            $query = "SELECT * FROM servico WHERE servcod = '" . $_POST["petcod"] . "'";
            $resultado = mysqli_query($mysqli, $query);

            $dado = mysqli_fetch_array($resultado);
        } else {
            echo "err: " . $_POST["petcod"];
        }
        session_start();
        // Processar os dados do formulário enviado
        $nm = $_POST['nm'];
        $preco = $_POST['preco'];
        $preco = str_replace(".", "", $preco);
        $preco = str_replace(",", ".", $preco);
        $estpet = $_POST['estpet'];
        $cityp = $_POST['citypet'];
        $desc = $_POST['desc'];
        $codigopet = $_POST["petcod"];

        if (strlen($nm) <= 25 && strlen($desc) <= 255) {
            $updateQuery = "UPDATE servico SET nomeserv='$nm', preco='$preco', estados='$estpet', cidades='$cityp', descserv='$desc', aprovacaoserv=null,admin_codadmn=null 
        WHERE servcod='" . $_POST["petcod"] . "'";

            if (mysqli_query($mysqli, $updateQuery)) {
                $sqlSelect2 = "SELECT * FROM imagem WHERE servicocodserv = '" . $_POST["petcod"] . "'";
                $result2 = mysqli_query($mysqli, $sqlSelect2);

                if ($result2->num_rows > 0) {
                    if (!empty($_FILES['picture__input']) && $_FILES['picture__input']['error'] === UPLOAD_ERR_OK) {
                        $image = $_FILES['picture__input']['tmp_name'];
                        $imgData = file_get_contents($image);

                        $sql = "UPDATE imagem SET img = ? WHERE servicocodserv = ?";
                        $stmt = $mysqli->prepare($sql);
                        $stmt->bind_param("ss", $imgData, $_POST["petcod"]);

                        if ($stmt->execute()) {
                            echo "<script>
    Swal.fire(
        'Pet " . $nm . " Atualizado Com Sucesso',
        'Dados Guardados',
        'success'
    ).then(() => {
        window.history.back();
    });
</script>";
                        } else {
                            echo "Erro ao atualizar imagem: " . $stmt->error;
                        }
                    } else {
                        echo "<script>
                    Swal.fire(
                        'Serviço " . $nm . " Atulizado Com Sucesso',
                        'Dados Guardados',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                    }
                } else {
                    if (!empty($_FILES['picture__input']) && $_FILES['picture__input']['error'] === UPLOAD_ERR_OK) {
                        $inicial_p = substr($codigopet, 0, 6);

                        $comprimento = strlen($nm);
                        $primeira_letra = substr($nm, 0, 1);
                        $letra_do_meio = substr($nm, round($comprimento / 2) - 1, 1);
                        $ultima_letra = substr($nm, -1);
                        $inicial_nmp = $primeira_letra . $letra_do_meio . $ultima_letra;

                        $hora_atual = date("Hi");
                        $data_atual = date("Ymd");

                        $codi = "IMGserv" . $inicial_nmp . $hora_atual . $inicial_p . $data_atual;

                        $image = $_FILES['picture__input']['tmp_name'];
                        $imgData = file_get_contents($image);

                        $sql2 = "INSERT INTO imagem (codimg, img, servicocodserv) VALUES ('$codi', ?, '" . $_POST["petcod"] . "')";
                        $stmt = mysqli_prepare($mysqli, $sql2);
                        mysqli_stmt_bind_param($stmt, "s", $imgData);

                        if (mysqli_stmt_execute($stmt)) {
                            echo "<script>
                    Swal.fire(
                        'Serviço $nm Atulizado Com Sucesso',
                        'Dados Guardados',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                        } else {
                            $sql = "DELETE FROM servico WHERE servcod = '$codp';";
                            $result = mysqli_query($mysqli, $sql);
                            // Tratar o caso em que a inserção da imagem falha
                            echo "Erro ao cadastrar a imagem no banco de dados: " . mysqli_error($mysqli);
                            echo "Delete: " . $result;
                        }
                    } else {
                        echo "<script>
                    Swal.fire(
                        'Serviço " . $nm . " Atulizado Com Sucesso',
                        'Dados Guardados',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                    }
                }
            } else {
                echo "Erro ao atualizar o pet: " . mysqli_error($mysqli);
            }
        } else {
            echo "<script>
            Swal.fire(
            'Excedência de Caráter', 
            'Você ultrapassou o limite de caracteres. Nome só pode ter 25. Descrição só pode ter 255',
            'error'
            ).then(() => {
                window.history.back();
            });
            </script>";
        }
    } else if (isset($_POST["dst-btn"])) {
        if (isset($_POST["petcod"]) && isset($_POST["atv"])) {
            $codigopet = $_POST["petcod"];
            $nm = $_POST["nome"];
            $atv = ($_POST["atv"] == 1) ? 0 : 1; // Inverte o valor de $atv

            // Preparar a consulta SQL com uma declaração preparada
            $updateQuery = "UPDATE servico SET bloqueioserv = ? WHERE servcod = ?";

            // Preparar a declaração
            $stmt = mysqli_prepare($mysqli, $updateQuery);

            // Vincular os parâmetros e seus tipos
            mysqli_stmt_bind_param($stmt, "is", $atv, $codigopet);

            // Executar a consulta
            if (mysqli_stmt_execute($stmt)) {
                echo "<script>
                    Swal.fire(
                        'Serviço " . $nm . " Atualizado Com Sucesso',
                        'Dados Guardados',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
            } else {
                echo mysqli_error($mysqli);
            }

            // Fechar a declaração
            mysqli_stmt_close($stmt);
        } else {
            echo "err: sem codigo pet ou dst";
        }
        /*echo "<script>
                   
                        window.history.back();
                </script>";*/
    } else {
        echo "sem codigo pet";
    }
}
