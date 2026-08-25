<?php
include('./lib/dbconnect.php');
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
if (isset($_SESSION["usuario"])) {

    $query = "select * from pessoa where nickname = '" . $_SESSION["usuario"] . "'";
    $resultado = mysqli_query($mysqli, $query);

    $dado = mysqli_fetch_array($resultado);


    $sql_imagem = "SELECT * FROM pessoa WHERE nickname='" . $_SESSION["usuario"] . "';";
    $result_imagem = $mysqli->query($sql_imagem);
    $nickpes = $_SESSION["usuario"];
    if ($row_imagem = mysqli_fetch_assoc($result_imagem)) {

        $imageData = $row_imagem['imgperfil'];
        $imageType = 'image/jpeg';
        if ($imageData == null) {
            $imagePath = './adm/usuario.jpg';
            $imageData = file_get_contents($imagePath);
            $imageType = mime_content_type($imagePath);
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
        #picture__input {
            display: none;
        }

        .picture {
            width: 500px;
            height: 500px;
        }

        .picture:hover {
            color: #777;
            background: #ccc;
        }

        .picture:active {
            border-color: turquoise;
            color: turquoise;
            background: #eee;
        }

        .picture:focus {
            color: #777;
            background: #ccc;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
        }

        .picture__img {
            max-width: 100%;
        }

        .imgpet {
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

        .attimg {
            margin-right: 10px;
            margin-top: 3vh;
            border-radius: 10px;
            text-align: center;
            padding: 5px 10px;
            color: white;
            background-color: #662901;
        }

        .rmvimg {
            margin-left: 10px;
            margin-top: 3vh;
            border-radius: 10px;
            text-align: center;
            padding: 5px 10px;
            color: white;
            background-color: #662901;
        }

        .fa-x{
            color: #2b1100;
            position: absolute;
            font-size: 2vh;
            top:5px;
            left: 6px;
        }
    </style>


    <?php
    echo "<form method='post' enctype='multipart/form-data' onsubmit='prepareAndSubmit(event)'>

    <center>
            <label class='picture' for='picture__input' tabIndex='0'>
  <span class='picture__image'>
    <img class='imgpet'  id='petImage' src='data:$imageType;base64," . base64_encode($imageData) . "' alt='Pet Image'>
  </span>
</label>
<input type='file' name='picture__input' id='picture__input' >
                 
          <div id='buttons-container'>
          <button class='attimg' type='submit' id='enviar-btn' name='enviar-btn' >Atualizar Imagem</button>
          <button class='rmvimg' type='submit' id='enviar-sem' name='enviar-sem' >Remover Imagem</button></center>
          <a id='closePopupBtn'><i class='fa-solid fa-x'></i></a>
        </div>
    </form>
    
";
    if (isset($_POST['enviar-btn'])) {
        // Verifique se o arquivo foi enviado corretamente
        if (isset($_FILES['picture__input']) && $_FILES['picture__input']['error'] === UPLOAD_ERR_OK) {
            $imgData = file_get_contents($_FILES['picture__input']['tmp_name']);

            $sql = "UPDATE pessoa SET imgperfil = ? WHERE nickname = ?";
            $stmt = $mysqli->prepare($sql);

            if ($stmt) {
                $stmt->bind_param("ss", $imgData, $nickpes);

                if ($stmt->execute()) {
                    echo "<script>
                        Swal.fire({
                            title: 'Imagem de Perfil Atualizada com Sucesso',
                            text: 'Informações Guardadas',
                            icon: 'success'
                        }).then(() => {
                            window.history.back();
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
    } else if (isset($_POST['enviar-sem'])) {
        $sql = "UPDATE pessoa SET imgperfil = NULL WHERE nickname = ?";
        $stmt = $mysqli->prepare($sql);

        if ($stmt) {
            $stmt->bind_param("s", $nickpes);

            if ($stmt->execute()) {
                echo "<script>
                    Swal.fire({
                        title: 'Imagem de Perfil Removida com Sucesso',
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
        const pictureInput = document.getElementById('picture__input');
        pictureInput.addEventListener('change', function() {
            const file = pictureInput.files[0];
            const reader = new FileReader();

            reader.onloadend = function() {
                document.getElementById('petImage').src = reader.result;
            };

            if (file) {
                reader.readAsDataURL(file);
            }
        });
    </script>

</body>

</html>