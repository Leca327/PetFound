<!DOCTYPE html>
<html lang="en">

<head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>Document</title>
      <link rel="stylesheet" href="./paginaADM.css">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
      <link href="/your-path-to-fontawesome/css/custom-icons.css" rel="stylesheet">
      <link rel="stylesheet" href="paginaADM.css">
      <link rel="stylesheet" href="../lib/padrao.css">
      <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>

<a target="_blank" href="https://icons8.com/icon/GRqrcka17h5m/pet-care"></a>
<a target="_blank" href="https://icons8.com/icon/N0rCLUNZo0Qt/paw-print"></a>

<body>
      <Div class="topo">
            <a href="../index.php" class="volta">Pet Found </a>
            <?php
            include('../lib/dbconnect.php');
            session_start();
            if (isset($_SESSION["admin"])) {
                  $user = $_SESSION["admin"];
                  
                  $sql_imagem = "SELECT * FROM admin WHERE usera='$user';";
                  $result_imagem = $mysqli->query($sql_imagem);

                  if ($row_imagem = mysqli_fetch_assoc($result_imagem)) {
                        $perm = $row_imagem["chefe"];
                        if ($row_imagem['imgadm'] != null) {
                              $imageData = $row_imagem['imgadm'];
                              $imageType = 'image/jpeg';
                        } else {
                              // Defina uma imagem padrão caso nenhuma imagem seja encontrada
                              $imagePath = './usuario.jpg';
                              $imageData = file_get_contents($imagePath);
                              $imageType = mime_content_type($imagePath);
                        }
                  } else {
                        echo "Não encontrou o adm";
                  }

                  echo "<div class='adm'>
                  <a href='' class='nome'>
                  <img src='data:$imageType;base64," . base64_encode($imageData) . "' alt='' class='img'>
                   $user</a>
            </div>";
            } else {
                  echo "<script>
                    Swal.fire(
                    'Admin não Logado',  
                    'Faça o Login',
                    'error'
                    ).then(() => {
                    window.location.href='../index.php';
                    });
                    </script>";
            }

            ?>



      </Div>


      <div class="con">
            <a href="./registro/regis.php" class="petR">
                  <img src="buspet.png" alt="RP">
                  Registro de pet
            </a>
            <?php
            if ($perm == true) {
                  echo '<a href="./notifications/not.php" class="not">
                  <img src="notify.png" alt="EN">
                  Enviar Notificação
            </a>';
            } else {
            }

            ?>

            <a href="./registro serv/serv.php" class="servico">
                  <img src="busserv.png" alt="SR">
                  Registro de serviço
            </a>

      </div>


</body>

</html>