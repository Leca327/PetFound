<?php include('../../lib/dbconnect.php'); ?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Notifications</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css" />
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
  <script src="./assets/js/jquery.min.js"></script>
  <script src="./assets/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="../paginaADM.css">
    <link rel="stylesheet" href="../../lib/padrao.css">
  <style>
    body {
      margin: 0 !important;
      padding: 0 !important;
      box-sizing: border-box;
      font-family: 'Roboto', sans-serif;
      background-color: #ff924b;
    }

    .sairadm{
      position: absolute;
    left: 10vh;
    top: 1vh;
    border-radius: 10px;
    padding: 5px 5px;
    text-decoration: none;
    color: white;
    background-color: #662901;
    }

    .form-horizontal{
      margin-top: 10vh;
    }

    .round {
      width: 20px;
      height: 20px;
      border-radius: 50%;
      position: relative;
      background: red;
      display: inline-block;
      padding: 0.3rem 0.2rem !important;
      margin: 0.3rem 0.2rem !important;
      left: -18px;
      top: 10px;
      z-index: 99 !important;
    }

    .round>span {
      color: white;
      display: block;
      text-align: center;
      font-size: 1rem !important;
      padding: 0 !important;
    }

    #list {
      display: none;
      top: 33px;
      position: absolute;
      right: 2%;
      background: #ffffff;
      z-index: 100 !important;
      width: 25vw;
      margin-left: -37px;
      padding: 0 !important;
      margin: 0 auto !important;
    }

    h3{
      text-align: center;
    }

    .message>span {
      width: 100%;
      display: block;
      color: red;
      text-align: justify;
      margin: 0.2rem 0.3rem !important;
      padding: 0.3rem !important;
      line-height: 1rem !important;
      font-weight: bold;
      border-bottom: 1px solid white;
      font-size: 1.8rem !important;

    }

    .message>.msg {
      width: 90%;
      margin: 0.2rem 0.3rem !important;
      padding: 0.2rem 0.2rem !important;
      text-align: justify;
      font-weight: bold;
      display: block;
      word-wrap: break-word;


    }

  </style>
</head>

<?php
include('../../lib/dbconnect.php');
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
            $imagePath = '../usuario.jpg';
            $imageData = file_get_contents($imagePath);
            $imageType = mime_content_type($imagePath);
        }
    } else {
        echo "Não encontrou o contratante";
    }

    echo "<div class='adm'>
                  <img src='data:$imageType;base64," . base64_encode($imageData) . "' alt='' class='img'>
                  <a href='' class='nome'> $user</a>
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
                    </script></div>";
}
?>
</Div>
<a class="sairadm" href="../paginaADM.php">
    ◄ Voltar Para o Painel de Admin </a>


</Div>

<div class="container">
  

  <form class="form-horizontal" id="frm_data" method="post" action="./insert.php">
  <h3>Sistema de Notificação</h3>
    <div class="form-group row">
      <label class="control-label col-md-4" for="notification">Título</label>
      <div class="col-md-6">
        <input type="text" name="notifications_name" id="notifications_name" class="form-control" placeholder="Notification name" required />
      </div>

    </div>
    <div class="form-group row">
      <label class="control-label col-md-4" for="notification">Mensagem</label>
      <div class="col-md-6">
        <textarea style="resize:none !important;" name="mensagem" id="mensagem" rows="4" cols="10" class='form-control'></textarea>
      </div>
    </div>
    <div class="form-group row">
      <label class="control-label col-md-4" for="user_nick">Nick do usuario</label>
      <div class="col-md-6">
        <input type="text" name="user_nick" id="user_nick" class="form-control" placeholder="User Nick" required />
      </div>
    </div>
    <div class="form-group row">
      <div class="col-md-10 col-offset-2" style="text-align:end;">
        <input type="submit" id="notify" name="submit" style="background:#662901 !important;" class="btn btn-danger" value="Notificar" />
      </div>
    </div>

  </form>

</div>


</body>

</html>