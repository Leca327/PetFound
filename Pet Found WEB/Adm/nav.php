<?php
   
            session_start();
            if (isset($_SESSION["admin"])) {
                  $user = $_SESSION["admin"];

                  $sql_imagem = "SELECT * FROM admin WHERE usera='$user';";
                  $result_imagem = $mysqli->query($sql_imagem);

                  if ($row_imagem = mysqli_fetch_assoc($result_imagem)) {
                        
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
                        echo "Não encontrou o contratante";
                  }

                  echo "<Div class='adm'>
                  <img src='data:$imageType;base64," . base64_encode($imageData) . "' alt='' class='img'>
                  <a href='' class='nome'> $user</a>
            </Div>";
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
