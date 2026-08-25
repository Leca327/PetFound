<?php
include('./lib/dbconnect.php');
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
if (isset($_SESSION["usuario"])) {

    $query = "select * from pessoa where nickname = '" . $_SESSION["usuario"] . "'";
    $resultado = mysqli_query($mysqli, $query);

    $dado = mysqli_fetch_array($resultado);

    $sql_imagem2 = "SELECT * FROM pessoa WHERE nickname='" . $_SESSION["usuario"] . "';";
    $result_imagem2 = $mysqli->query($sql_imagem2);
    $nickpes = $_SESSION["usuario"];
    if ($row_imagem = mysqli_fetch_assoc($result_imagem2)) {

        $imageData2 = $row_imagem['banner'];
        $imageType2 = 'image/jpeg';
        if ($imageData2 == null) {
            $imagePath2 = './assets/projetoimg/banner4.png';
            $imageData2 = file_get_contents($imagePath2);
            $imageType2 = mime_content_type($imagePath2);
        }
    } else {
        echo "Nenhum pessoa encontrado com esse código.";
    }
} else if (isset($_SESSION["admin"])) {
    echo "Operação inadequada. Utilize o sistema de Java";
} else {
    echo "<script>
    window.location.href='./login/login.php';

</script>";
}

?>

<!DOCTYPE html>
<html lang="PT-BR">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edição de Pet</title>
    <link rel="stylesheet" href="./lib/padrao.css">


</head>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>

<body>
    <style>
        #picture__input2 {
            display: none;
        }

        .picture2 {
            width: 500px;
            height: 500px;
        }

        .picture2:hover {
            color: #777;
            background: #ccc;
        }

        .picture2:active {
            border-color: turquoise;
            color: turquoise;
            background: #eee;
        }

        .picture2:focus {
            color: #777;
            background: #ccc;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
        }

        .picture__img2 {
            max-width: 100%;
        }

        .imgpet2 {
            width: 500px;
            height: 500px;
            background: #a8a29e;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #aaa;
            border: none;
            border-radius: 20px;
            cursor: pointer;
            font-family: sans-serif;
            transition: color 300ms ease-in-out, background 300ms ease-in-out;
            outline: none;
            overflow: hidden;
        }

        .attimg2 {
            margin-right: 10px;
            margin-top: 3vh;
            border-radius: 10px;
            text-align: center;
            padding: 5px 10px;
            color: white;
            background-color: #662901;
        }

        .rmvimg2 {
            margin-left: 10px;
            margin-top: 3vh;
            border-radius: 10px;
            text-align: center;
            padding: 5px 10px;
            color: white;
            background-color: #662901;
        }

        .fa-x2 {
            color: #2b1100;
            position: absolute;
            font-size: 2vh;
            top: 5px;
            left: 6px;
        }
    </style>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>


    <?php
    echo "<form method='post' enctype='multipart/form-data' onsubmit='prepareAndSubmit(event)'>

    <center>
            <label class='picture2' for='picture__input2' tabIndex='0'>
  <span class='picture__image2'>
    <img class='imgpet2'  id='petImage2' src='data:$imageType2;base64," . base64_encode($imageData2) . "' alt='Pet Image'>
  </span>
</label>
<input type='file' name='picture__input2' id='picture__input2' >
                 
          <div id='buttons-container2'>
          <button class='attimg2' type='submit' id='enviar-btn2' name='enviar-btn2' >Atualizar Imagem</button>
          <button class='rmvimg2' type='submit' id='enviar-sem2' name='enviar-sem2' >Remover Imagem</button></center>
          <a id='closePopupBtn2'><i class='fa-solid2 fa-x2'></i></a>
        </div>
    </form>
    
";
    if (isset($_POST['enviar-btn2'])) {
        // Verifique se o arquivo foi enviado corretamente
        if (isset($_FILES['picture__input2']) && $_FILES['picture__input2']['error'] === UPLOAD_ERR_OK) {

            $imgData2 = file_get_contents($_FILES['picture__input2']['tmp_name']);

            $sql = "UPDATE pessoa SET banner = ? WHERE nickname = ?";
            $stmt = $mysqli->prepare($sql);

            if ($stmt) {
                $stmt->bind_param("ss", $imgData2, $nickpes);

                if ($stmt->execute()) {
                    echo "<script>
                        Swal.fire({
                            title: 'Imagem do Banner Atualizada com Sucesso',
                            text: 'Informações Guardadas',
                            icon: 'success'
                        }).then(() => {
                            <script>window.location.replace('./perfil.php')</script>
                        });
                    </script>";
                } else {
                    echo "Erro ao atualizar imagem: " . $stmt->error;
                }

                // Feche a declaração
                $stmt->close();
            } else {
                // Falha na preparação da declaração
                echo "Erro na preparação da consulta: " . $mysqli->error;
            }
        }
    } else if (isset($_POST['enviar-sem2'])) {
        $sql = "UPDATE pessoa SET banner = NULL WHERE nickname = ?";
        $stmt = $mysqli->prepare($sql);

        if ($stmt) {
            $stmt->bind_param("s", $nickpes);

            if ($stmt->execute()) {
                echo "<script>
                    Swal.fire({
                        title: 'Imagem do Banner Removida com Sucesso',
                        text: 'Informações Atualizadas',
                        icon: 'success'
                    }).then(() => {
                        window.history.back();
                    });
                </script>";
            } else {
                echo "Erro ao remover a imagem: " . $stmt->error;
            }

            // Feche a declaração
            $stmt->close();
        } else {
            // Falha na preparação da declaração
            echo "Erro na preparação da consulta: " . $mysqli->error;
        }
    }

    ?>
    <script>
        // Atualiza a imagem quando um novo arquivo é selecionado
        const pictureInput2 = document.getElementById('picture__input2');
        pictureInput2.addEventListener('change', function() {
            const file = pictureInput2.files[0];
            const reader = new FileReader();

            reader.onloadend = function() {
                document.getElementById('petImage2').src = reader.result;
            };

            if (file) {
                reader.readAsDataURL(file);
            }
        });
    </script>

</body>

</html>