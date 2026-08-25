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

            $query = "SELECT * FROM pet WHERE petcod = '" . $_POST["petcod"] . "'";
            $resultado = mysqli_query($mysqli, $query);

            $dado = mysqli_fetch_array($resultado);
        } else {
            echo "err: " . $_POST["petcod"];
        }
        session_start();
        // Processar os dados do formulário enviado
        $nm = $_POST['nm'];
        $tipet = $_POST['tipet'];
        $idade = $_POST['idade'];
        $raca = $_POST['raca'];
        $cor = $_POST['cor'];
        $sexo = $_POST['sexo'];
        $porte = $_POST['porte'];
        $estpet = $_POST['estpet'];
        $cityp = $_POST['citypet'];
        //$fnl = $_POST['fnl'];
        $hist = $_POST['hist'];
        $desc = $_POST['desc'];
        // $dono = $_POST['dono'];
        $codigopet = $_POST["petcod"];
        if (strlen($nm) <= 25 && strlen($cor) <= 25 && strlen($raca) <= 25 && strlen($desc) <= 255 && strlen($hist) <= 255) {
            if ($sexo === "Fêmea") {
                $sexo = "F";
            } else if ($sexo === "Macho") {
                $sexo = "M";
            }
            $updateQuery = "UPDATE pet SET nomepet='$nm', tipop='$tipet', fai_ida='$idade', raca='$raca', cor_pel='$cor', 
        sexo='$sexo', porte='$porte', estadop='$estpet', cidadep='$cityp', historia='$hist', descpet='$desc', aprovacaopet=null,admincodadmn=null 
        WHERE petcod='" . $_POST["petcod"] . "'";

            if (mysqli_query($mysqli, $updateQuery)) {
                $sqlSelect2 = "SELECT * FROM imagem WHERE petcodpet = '" . $_POST["petcod"] . "'";
                $result2 = mysqli_query($mysqli, $sqlSelect2);

                if ($result2->num_rows > 0) {
                    if (!empty($_FILES['picture__input']) && $_FILES['picture__input']['error'] === UPLOAD_ERR_OK) {
                        $image = $_FILES['picture__input']['tmp_name'];
                        $imgData = file_get_contents($image);

                        $sql = "UPDATE imagem SET img = ? WHERE petcodpet = ?";
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
                        'Pet " . $nm . " Atulizado Com Sucesso',
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

                        $codi = "IMGpet" . $inicial_nmp . $hora_atual . $inicial_p . $data_atual;

                        $image = $_FILES['picture__input']['tmp_name'];
                        $imgData = file_get_contents($image);

                        $sql2 = "INSERT INTO imagem (codimg, img, petcodpet) VALUES ('$codi', ?, '" . $_POST["petcod"] . "')";
                        $stmt = mysqli_prepare($mysqli, $sql2);
                        mysqli_stmt_bind_param($stmt, "s", $imgData);

                        if (mysqli_stmt_execute($stmt)) {
                            echo "<script>
                    Swal.fire(
                        'Pet $nm Atulizado Com Sucesso',
                        'Dados Guardados',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                        } else {
                            $sql = "DELETE FROM pet WHERE petcod = '$codp';";
                            $result = mysqli_query($mysqli, $sql);
                            // Tratar o caso em que a inserção da imagem falha
                            echo "Erro ao cadastrar a imagem no banco de dados: " . mysqli_error($mysqli);
                            echo "Delete: " . $result;
                        }
                    } else {
                        echo "<script>
                    Swal.fire(
                        'Pet " . $nm . " Atulizado Com Sucesso',
                        'Dados Guardados',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                    }
                }
            } else {
                echo "<script>
                Swal.fire(
                'Erro ao Atualizar',
                'Alguma coisa deu errado',
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
                    'Você ultrapassou o limite de caracteres. Nome,Raça e Cor só podem ter 25. Descrição e História só podem ter 255',
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
            $updateQuery = "UPDATE pet SET bloqueiopet = ? WHERE petcod = ?";

            // Preparar a declaração
            $stmt = mysqli_prepare($mysqli, $updateQuery);

            // Vincular os parâmetros e seus tipos
            mysqli_stmt_bind_param($stmt, "is", $atv, $codigopet);

            // Executar a consulta
            if (mysqli_stmt_execute($stmt)) {
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
                echo mysqli_error($mysqli);
            }

            // Fechar a declaração
            mysqli_stmt_close($stmt);
        } else {
            echo "err: sem codigo pet ou dst";
        }


        // $query = "SELECT * FROM pet WHERE petcod = '" . $_POST["petcod"] . "'";
        // $resultado = mysqli_query($mysqli, $query);

        //$dado = mysqli_fetch_array($resultado);

        /*echo "<script>
                   
                        window.history.back();
                </script>";*/
    } else {
        echo "sem escolha";
    }
}
