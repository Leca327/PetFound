<!DOCTYPE html>
<html lang="en">

<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
  <link rel="stylesheet" href="./painel.css">
  <link rel="stylesheet" href="./lib/padrao.css">
  <link rel="stylesheet" href="./lib/footer.css">
  <link rel="stylesheet" href="./lib/navbar.css">
  <link rel="stylesheet" href="./lib/responsivonavbar.css">
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>

</head>

<body>
  <br>
  <?php include('./lib/navbar.php'); ?>
  <div class="aaa"></div>
  <div class="container">

    <?php
    include('./lib/dbconnect.php');
    if (isset($_SESSION["usuario"])) {
      $user = $_SESSION["usuario"];
      $query = "SELECT * FROM pessoa p JOIN endereco e on p.endcodend=e.endcod WHERE nickname = '$user';";
      $result = mysqli_query($mysqli, $query);

      if ($result && mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        $cod = $row['pcod'];
        $tipo = substr($cod, 0, 2);

        if ($tipo == "PF") {
          $nome = $row['nomep'];
          $snome = $row['snomep'];
          $email = $row['emailp'];
          $contato = $row['contatop'];
          $senha = $row['senha'];
          $nick = $row['nickname'];
          $cod = $row['pcod'];

          $cep = $row['cep'];
          $uf = $row['uf'];
          $brr = $row['bairro'];
          $end = $row['endereco'];
          $num = $row['numero'];
          $cpt = $row['cmpt'];
          $cid = $row['cidade'];

          $query = "SELECT * FROM fisica WHERE codp = '$cod';";
          $result = mysqli_query($mysqli, $query);

          if ($result && mysqli_num_rows($result) > 0) {
            $row = mysqli_fetch_assoc($result);
            $sexo = $row['sexo'];
            $dt = $row['dt_nascimento'];

            echo "
    <div class='nvr'>
      <br>
      <h3>Painel</h3>
      <br>
      <ul>
        <li><button class='button_op' data-option='op'>Meus Pet</button></li>
        <ul class='sub_itens'>
          <li><button class='sbutton_op' data-option='op1'>Meus Anuncios</button></li>
          <li><button class='sbutton_op' data-option='op2'>Gerenciar Contatos</button></li>
        </ul>
        <hr>
        <li><button class='button_op' data-option='op'>Meus Serviço</button></li>
        <ul>
          <li><button class='sbutton_op' data-option='op3'>Meus Anuncios</button></li>
          <li><button class='sbutton_op' data-option='op4'>Gerenciar Contatos</button></li>
        </ul>
        <hr>
        <li><button class='button_op' data-option='op'>Contatos que Eu criei</button></li>
        <ul>
        <li><button class='sbutton_op' data-option='op5'>Contatos de Pets</button></li>
        <li><button class='sbutton_op' data-option='op6'>Contatos de Serviço</button></li>
        </ul>
      </ul>
    </div>
              
              ";
          }
        } else if ($tipo == "PJ") {
          $nome = $row['nomep'];
          $email = $row['emailp'];
          $contato = $row['contatop'];
          $nick = $row['nickname'];
          $cod = $row['pcod'];

          $cep = $row['cep'];
          $uf = $row['uf'];
          $brr = $row['bairro'];
          $end = $row['endereco'];
          $num = $row['numero'];
          $cpt = $row['cmpt'];
          $cid = $row['cidade'];

          $query = "SELECT * FROM juridica WHERE cod_p = '$cod';";
          $result = mysqli_query($mysqli, $query);

          if ($result && mysqli_num_rows($result) > 0) {
            $row = mysqli_fetch_assoc($result);

            $ra = $row['ramo_ativ'];
            $tip = $row['tipoj'];
            if ($tip == "ONG") {
              echo "
    <div class='nvr'>
      <br>
      <h3>Painel</h3>
      <br>
      <ul>
        <li><button class='button_op' data-option='op'>Meus Pet</button></li>
        <ul class='sub_itens'>
          <li><button class='sbutton_op' data-option='op1'>Meus Anuncios</button></li>
          <li><button class='sbutton_op' data-option='op2'>Gerenciar Contatos</button></li>
        </ul>
        <hr>
        <li><button class='button_op' data-option='op'>Meus Serviço</button></li>
        <ul>
          <li><button class='sbutton_op' data-option='op3'>Meus Anuncios</button></li>
          <li><button class='sbutton_op' data-option='op4'>Gerenciar Contatos</button></li>
        </ul>
        <hr>
        <li><button class='button_op' data-option='op'>Contatos que Eu criei</button></li>
        <ul>
        <li><button class='sbutton_op' data-option='op6'>Contatos de Serviço</button></li>
        </ul>

      </ul>
    </div>
              
              ";
            } else if ($tip == "Empresa") {
              echo "
    <div class='nvr'>
      <br>
      <h3>Painel</h3>
      <br>
      <ul>
        <li><button class='button_op' data-option='op'>Meus Pet</button></li>
        <ul class='sub_itens'>
          <li><button class='sbutton_op' data-option='op1'>Meus Anuncios</button></li>
          <li><button class='sbutton_op' data-option='op2'>Gerenciar Contatos</button></li>
        </ul>
        <hr>
        <li><button class='button_op' data-option='op'>Meus Serviço</button></li>
        <ul>
          <li><button class='sbutton_op' data-option='op3'>Meus Anuncios</button></li>
          <li><button class='sbutton_op' data-option='op4'>Gerenciar Contatos</button></li>
        </ul>
        <hr>
        <li><button class='button_op' data-option='op'>Contatos que Eu criei</button></li>
        <ul>
        <li><button class='sbutton_op' data-option='op6'>Contatos de Serviço</button></li>
        </ul>

      </ul>
    </div>
              
              ";
            }
          }
        }
      }
    } else if (isset($_SESSION["admin"])) {
      $user = $_SESSION["admin"];

      echo "<script>
                        Swal.fire(
                            'Erro',
                            'Acesso Restrito Para Usúarios',
                            'error'
                        ).then(() => {
                            window.location.href='./index.php';
                        });
                    </script>";
    } else {
      echo "<h1>Usuário não autenticado.</h1>";
    }
    ?>
    <?php 

    echo "
    <div class='right'>
      <div id='content_op1' style='display: none;'>
        <p>Meus anuncios de Pets</p>
        <form method='get' action=''>
          <input type='hidden' name='opc' value='op1'> 
          <select id='apvpet' name='apvpet'>
            <option value='aprovado'>Aprovados</option>
            <option value='reprovado' " . (isset($_GET['apvpet']) && $_GET['apvpet'] == 'reprovado' ? 'selected' : '') . ">Reprovados</option>
            <option value='analise' " . (isset($_GET['apvpet']) && $_GET['apvpet'] == 'analise' ? 'selected' : '') . ">Em análise</option>
          </select>
          <select id='dstpet' name='dstpet'>
            <option value='Ativado'>Ativos</option>
            <option value='Desativado' " . (isset($_GET['dstpet']) && $_GET['dstpet'] == 'Desativado' ? 'selected' : '') . ">Desativados</option>
          </select>
                
          <input class='filtrar' type='submit' value='Filtrar'>
        </form>";
    if ($_SERVER['REQUEST_METHOD'] == 'GET') {
      $apv = isset($_GET['apvpet']) ? $_GET['apvpet'] : "aprovado"; // Obtain the filter value from the query string or use the default
      $dst = isset($_GET['dstpet']) ? $_GET['dstpet'] : "Ativado";
    }

    // Consulta SQL com base no valor do filtro
    $sql = "SELECT p.*, i.img 
      FROM pet p
      LEFT JOIN imagem i ON p.petcod = i.petcodpet
      WHERE ";
    if ($apv === "aprovado") {
      $sql .= "p.aprovacaopet = true ";
    } elseif ($apv === "reprovado") {
      $sql .= "p.aprovacaopet = false ";
    } elseif ($apv === "analise") {
      $sql .= "p.aprovacaopet IS NULL ";
    }

    if ($dst === "Ativado") {
      $sql .= "AND (p.bloqueiopet = false or p.bloqueiopet is null)";
    } elseif ($dst === "Desativado") {
      $sql .= "AND p.bloqueiopet = true";
    }

    $result2 = mysqli_query($mysqli, $sql);


    if (!$result2) {
      echo "Nenhum Serviço Cadastrado";
      die("Erro na consulta SQL: " . mysqli_error($mysqli));
    } else {
      $count = 0;
      while ($pet_data = mysqli_fetch_assoc($result2)) {
        $count++;
        $codpet = $pet_data['petcod'];
        $atvo = $pet_data['bloqueiopet'];
        $apdadt = $pet_data['finalidade'];
        $apvpet = $pet_data['aprovacaopet'];
        $mtvrep = $pet_data['motivoreppet'];

        if ($atvo == true) {
          $atvmsg = "Ativar";
        } else if ($atvo == false || $atvo == null) {
          $atvmsg = "Desativar";
        }

        $sql_imagem = "SELECT * FROM imagem WHERE petcodpet='$codpet';";
        $result_imagem = $mysqli->query($sql_imagem);

        if ($row_imagem = mysqli_fetch_assoc($result_imagem)) {
          $imageData = $row_imagem['img'];
          $imageType = 'image/jpeg';
        } else {
          $imagePath = './assets/semimg.png';
          $imageData = file_get_contents($imagePath);
          $imageType = mime_content_type($imagePath);
        }

        $sql2 = "SELECT COUNT(*) as total FROM contatopet WHERE petcodpet='$codpet';";
        $result3 = mysqli_query($mysqli, $sql2);

        if (!$result3) {
          // Trate o erro aqui, se necessário
        } else {
          $row = mysqli_fetch_assoc($result3);
          $cont = $row['total'];
          if ($cont == 1) {
            $cont .= " Contato Deste Pet";
          } else {
            $cont .= " Contatos Deste Pet";
          }
        }

        $cont2 = "Pet não foi adotado";
        $sql3 = "SELECT * FROM contatopet WHERE petcodpet='$codpet' and adotou=true;";
        $result4 = mysqli_query($mysqli, $sql3);

        if ($result4) {
          $row = mysqli_fetch_assoc($result4);

          if ($row) {
            $tutornv = $row['pfcodp'];
            $sql4 = "SELECT nickname FROM pessoa WHERE pcod = '$tutornv'";
            $result5 = mysqli_query($mysqli, $sql4);

            if ($result5) {
              // Verifique se a consulta foi bem-sucedida
              if (mysqli_num_rows($result5) > 0) {
                $row = mysqli_fetch_assoc($result5);
                $nickname = $row['nickname'];
                $cont2 = "Adotado por <a href='./perfil.php?pcod=$tutornv'>" . $nickname . "</a>";
              } else {
                echo "Tutor não encontrado.";
              }
            } else {
              echo "Erro na consulta: " . mysqli_error($mysqli);
            }
          } else {
          }
        }

        $cont3 = "Pet sem Padrinhos";
        $sql5 = "SELECT COUNT(*) as total FROM contatopet WHERE petcodpet='$codpet' and apadrinhou=true;";
        $result4 = mysqli_query($mysqli, $sql5);

        if ($result4) {
          $row = mysqli_fetch_assoc($result4);
          $cont3 = $row['total'];
          if ($cont3 == 1) {
            $cont3 .= " padrinho.";
          } else {
            $cont3 .= " padrinhos.";
          }
        }

        echo '
        <div class="anuserv" onmouseover="onMouseOver(this)" onmouseout="onMouseOut(this)">
          <div class="image-container">
              <img class="imgserv" src="data:' . $imageType . ';base64,' . base64_encode($imageData) . '" alt="Pet Image">
          </div>
          <div class="anuinfo">
              <label>' . $pet_data['nomepet'] . '</label>
              <label>' . $pet_data['tipop'] . '</label>
              <label>' . $pet_data['estadop'] . ', ' . $pet_data['cidadep'] . '</label>
              <label>' . $cont . '</label>
              
            ';
        if ($apdadt == "Padrinho") {
          echo '<label>' . $cont3 . '</label>';
        } else if ($apdadt == "Pad_Ado") {
          echo '<label>' . $cont2 . '</label>
          <label>' . $cont3 . '</label>';
        } else if ($apdadt == "Adocao") {
          echo '<label>' . $cont2 . '</label>';
        }
        if (isset($apvpet) && $apvpet == false) {
          echo "
          <label>Motivo da reprovação:</label>
          <textarea id='auto-resize-textarea' oninput='autoResize(this)' disabled style='border-radius:5px; text-align: left;'>" . $mtvrep . "</textarea>";
        }

        echo '
        
              <div class="buttons">
                <button><a class= "edt" href="editarpet.php?codigo=' . $codpet . '">Editar</a></button>
                <form action="editpet.php" method="post">
                  <input type="hidden" name="petcod" value="' . $codpet . '" required />
                  <input type="hidden" name="atv" value="' . $atvo . '" required />  
                  <input type="hidden" name="nome" value="' . $pet_data['nomepet'] . '" required />  
                  <button type="submit" name="dst-btn">' . $atvmsg . '</button>
                </form>
              </div>

          </div>
        </div>
      ';
      }
    }

    echo "
      </div>
      <div id='content_op2' style='display: none;'>
        <p>Gerenciar contatos dos meus pets</p>
        <form method='get' action=''>
          <input type='hidden' name='opc' value='op2'> 
          <select id='finalcontpet' name='finalcontpet'>
            <option value='aberto'>Não finalizados</option>
            <option value='adt' " . (isset($_GET['finalcontpet']) && $_GET['finalcontpet'] == 'adt' ? 'selected' : '') . ">Finalizados com Adoção</option>
            <option value='apd' " . (isset($_GET['finalcontpet']) && $_GET['finalcontpet'] == 'apd' ? 'selected' : '') . ">Finalizados com Apadrinhamento</option>
            <option value='sadtapd' " . (isset($_GET['finalcontpet']) && $_GET['finalcontpet'] == 'sadtapd' ? 'selected' : '') . ">Finalizados sem adoção ou Apadrinhamento</option>
          </select>
          <select id='arqcontpet' name='arqcontpet'>
            <option value='Ativado'>Em aberto</option>
            <option value='Desativado' " . (isset($_GET['arqcontpet']) && $_GET['arqcontpet'] == 'Desativado' ? 'selected' : '') . ">Arquivados</option>
          </select>
          <input class='filtrar' type='submit' value='Filtrar'>
      </form>";

    if ($_SERVER['REQUEST_METHOD'] == 'GET') {
      $apv = isset($_GET['finalcontpet']) ? $_GET['finalcontpet'] : "aberto";
      $dst = isset($_GET['arqcontpet']) ? $_GET['arqcontpet'] : "Ativado";
    }

    if (isset($_GET['petcod'])) {
      $sql3 = "SELECT * FROM contatopet WHERE  codcontp='" . $_GET['petcod'] . "'";

      $result4 = mysqli_query($mysqli, $sql3);

      if ($result4) {
        while ($row = mysqli_fetch_assoc($result4)) {


          if ($row) {
            $tpcont = $row['tipocont'];
            $tutornv = $row['pfcodp'];
            $cnf1 = $row['confirmpet'];
            $cnf2 = $row['confirmpe'];
            $lig = $row['ligoupet'];
            $codcont = $row['codcontp'];
            $adt = $row['adotou'];
            $apd = $row['apadrinhou'];
            $ativo = $row['arquivarpet'];
            $codpet = $row['petcodpet'];

            $sql4 = "SELECT nickname FROM pessoa WHERE pcod = '$tutornv'";
            $result5 = mysqli_query($mysqli, $sql4);

            echo "
        <div class='GC'>";
            if ($result5) {
              // Verifique se a consulta foi bem-sucedida
              if (mysqli_num_rows($result5) > 0) {
                $row = mysqli_fetch_assoc($result5);
                $nickname = $row['nickname'];

                $sql = "SELECT p.*, i.img 
                        FROM pet p
                        LEFT JOIN imagem i ON p.petcod = i.petcodpet
                        WHERE p.petcod = '$codpet'";

                $result2 = mysqli_query($mysqli, $sql);

                if (!$result2) {
                  echo "Nenhum Pet Cadastrado";
                  die("Erro na consulta SQL: " . mysqli_error($mysqli));
                } else {
                  $count = 0;
                  while ($pet_data = mysqli_fetch_assoc($result2)) {
                    $count++;
                    $codpet = $pet_data['petcod'];
                    $nmpet = $pet_data['nomepet'];
                  }
                }

                echo "
          <div class='NeP'><h5>Contatante: <a href='./perfil.php?pcod=$tutornv'>" . $nickname . "</a>
          <br>qual pet tem interesse: <a href='./anuncio/perfilpet.php?petcod=" . $codpet . "'>  " . $nmpet . "</a></h5></div>";
                echo '
          <form action="attcont.php" method="POST">
            <div class="radioGC">
              <input type="radio" name="confirmacao" value="ligou" ' . ($cnf1 == true ? 'checked' : '') . '> Ligou
              <input type="radio" name="confirmacao" value="nao_ligou" ' . ($cnf1 == false  ? 'checked' : '') . '> Não Ligou
              <input type="radio" name="confirmacao" value="esp" ' . ($cnf1 === null ? 'checked' : '') . '> Em espera
              <input type="hidden" name="codcont" value="' . $codcont . '">
              <input type="submit" name="attligpet" value="Atualizar ligação">
            </div>
          </form>';

                if ($tpcont == "adt" && $lig == true) {
                  echo '
          <form action="attcont.php" method="POST">
            <div class="radioGC">
              <input type="radio" name="confirmacao" value="adt" ' . ($adt == true ? 'checked' : '') . '> Adotou</input>
              <input type="radio" name="confirmacao" value="apd" ' . ($apd == true ? 'checked' : '') . ' disabled> Apadrinhou</input>
              <input type="radio" name="confirmacao" value="nquis" ' . ($apd == false  && $adt == false  ? 'checked' : '') . '> Não quis</input>
              <input type="radio" name="confirmacao" value="esp" ' . ($apd === null && $adt === null ? 'checked' : '') . '> Em espera</input>
              <input type="hidden" name="codcont" value="' . $codcont . '">
              <input type="submit" name="attencpet" value="Atualizar">
            </div>
          </form>';
                } else if ($tpcont == "apd" && $lig == true) {
                  echo '
          <form action="attcont.php" method="POST">
            <div class="radioGC">
              <input type="radio" name="confirmacao" value="adt" ' . ($adt == true ? 'checked' : '') . ' disabled> Adotou</input>
              <input type="radio" name="confirmacao" value="apd" ' . ($apd == true ? 'checked' : '') . ' > Apadrinhou</input>
              <input type="radio" name="confirmacao" value="nquis" ' . ($apd == false  && $adt == false ? 'checked' : '') . '> Não quis</input>
              <input type="radio" name="confirmacao" value="esp" ' . ($apd === null && $adt === null ? 'checked' : '') . '> Em espera</input>
              <input type="hidden" name="codcont" value="' . $codcont . '">
              <input type="submit" name="attencpet" value="Atualizar">
            </div>
          </form>';
                } else if ($tpcont == "adt_apd" && $lig == true) {
                  echo '
          <form action="attcont.php" method="POST">
            <div class="radioGC">
              <input type="radio" name="confirmacao" value="adt" ' . ($adt == true ? 'checked' : '') . ' > Adotou</input>
              <input type="radio" name="confirmacao" value="apd" ' . ($apd == true ? 'checked' : '') . '> Apadrinhou</input>
              <input type="radio" name="confirmacao" value="nquis" ' . ($apd == false && $adt == false  ? 'checked' : '') . '> Não quis</input>
              <input type="radio" name="confirmacao" value="esp" ' . ($apd === null && $adt === null ? 'checked' : '') . '> Em espera</input>
              <input type="hidden" name="codcont" value="' . $codcont . '">
              <input type="submit" name="attencpet" value="Atualizar">
            </div>
          </form>';
                } else {
                  echo '
          <form action="attcont.php" method="POST">
            <div class="radioGC">
              <input type="radio" name="confirmacao" value="adt" ' . ($adt == true ? 'checked' : '') . ' disabled> Adotou</input>
              <input type="radio" name="confirmacao" value="apd" ' . ($apd == true ? 'checked' : '') . ' disabled> Apadrinhou</input>
              <input type="radio" name="confirmacao" value="nquis" ' . ($apd == false  && $adt == false  ? 'checked' : '') . ' disabled> Não quis</input>
              <input type="radio" name="confirmacao" value="esp" ' . ($apd === null && $adt === null ? 'checked' : '') . '> Em espera</input>
              <input type="hidden" name="codcont" value="' . $codcont . '" >
              <input type="submit" name="attencpet" value="Atualizar" disabled>
            </div>
          </form>';
                }

                if ($cnf2 == true) {
                  $incont = "<div class='stts2'>Liguei</div>";
                } else if ($cnf2 === null) {
                  $incont = "<div class='stts3'>Esperando para ligar</div>";
                } else {
                  $incont = "<div class='stts1'>Não vou ligar</div>";
                }

                echo "<div class='sttsalign'>
        <h5 class='stts'>O contatante está como:" . $incont . "</h5>";

                if ($ativo == true) {
                  $ativo2 = "Arquivar Contato";
                } else {
                  $ativo2 = "Desarquivar Contato";
                }


                echo '
            <form action="attcont.php" method="POST">
              <input type="hidden" name="codcont" value="' . $codcont . '" >
              <input type="hidden" name="tpatvpet" value="' . $ativo . '" >
              <input type="submit" name="dstvpet" value="' . $ativo2 . '">
            </form>
          </div>';
              } else {
                echo "Tutor não encontrado.";
              }
            } else {
              echo "Erro na consulta: " . mysqli_error($mysqli);
            }
            echo " 
        </div>";
          } else {
            echo "Nenhum registro encontrado.";
          }
        }
      }
    } else {
    
      $sql = "SELECT p.*, i.img 
                        FROM pet p
                        LEFT JOIN imagem i ON p.petcod = i.petcodpet
                        WHERE p.pessoacodp = '$cod'";

      $result2 = mysqli_query($mysqli, $sql);

      if (!$result2) {
        echo "Nenhum Pet Cadastrado";
        die("Erro na consulta SQL: " . mysqli_error($mysqli));
      } else {
        $count = 0;
        while ($pet_data = mysqli_fetch_assoc($result2)) {
          $count++;
          $codpet = $pet_data['petcod'];
          $nmpet = $pet_data['nomepet'];

          $sql3 = "SELECT * FROM contatopet WHERE ";

          if ($apv === "aberto") {
            $sql3 .= "Apadrinhou IS NULL AND adotou IS NULL ";
          } elseif ($apv === "adt") {
            $sql3 .= "adotou = true ";
          } elseif ($apv === "apd") {
            $sql3 .= "Apadrinhou = true ";
          } elseif ($apv === "sadtapd") {
            $sql3 .= "Apadrinhou = false AND adotou=false ";
          }

          if ($dst === "Ativado") {
            $sql3 .= "AND (arquivarpet = false or arquivarpet is null)";
          } elseif ($dst === "Desativado") {
            $sql3 .= "AND arquivarpet = true";
          }

          $sql3 .= " AND petcodpet='$codpet'";
          $result4 = mysqli_query($mysqli, $sql3);

          if ($result4) {
            while ($row = mysqli_fetch_assoc($result4)) {


              if ($row) {
                $tpcont = $row['tipocont'];
                $tutornv = $row['pfcodp'];
                $cnf1 = $row['confirmpet'];
                $cnf2 = $row['confirmpe'];
                $lig = $row['ligoupet'];
                $codcont = $row['codcontp'];
                $adt = $row['adotou'];
                $apd = $row['apadrinhou'];
                $ativo = $row['arquivarpet'];

                $sql4 = "SELECT nickname FROM pessoa WHERE pcod = '$tutornv'";
                $result5 = mysqli_query($mysqli, $sql4);

                echo "
        <div class='GC'>";
                if ($result5) {
                  // Verifique se a consulta foi bem-sucedida
                  if (mysqli_num_rows($result5) > 0) {
                    $row = mysqli_fetch_assoc($result5);
                    $nickname = $row['nickname'];
                    echo "
          <div class='NeP'><h5>Contatante: <a href='./perfil.php?pcod=$tutornv'>" . $nickname . "</a>
          <br>qual pet tem interesse: <a href='./anuncio/perfilpet.php?petcod=" . $codpet . "'>  " . $nmpet . "</a></h5></div>";
                    echo '
          <form action="attcont.php" method="POST">
            <div class="radioGC">
              <input type="radio" name="confirmacao" value="ligou" ' . ($cnf1 == true ? 'checked' : '') . '> Ligou
              <input type="radio" name="confirmacao" value="nao_ligou" ' . ($cnf1 == false  ? 'checked' : '') . '> Não Ligou
              <input type="radio" name="confirmacao" value="esp" ' . ($cnf1 === null ? 'checked' : '') . '> Em espera
              <input type="hidden" name="codcont" value="' . $codcont . '">
              <input type="submit" name="attligpet" value="Atualizar ligação">
            </div>
          </form>';

                    if ($tpcont == "adt" && $lig == true) {
                      echo '
          <form action="attcont.php" method="POST">
            <div class="radioGC">
              <input type="radio" name="confirmacao" value="adt" ' . ($adt == true ? 'checked' : '') . '> Adotou</input>
              <input type="radio" name="confirmacao" value="apd" ' . ($apd == true ? 'checked' : '') . ' disabled> Apadrinhou</input>
              <input type="radio" name="confirmacao" value="nquis" ' . ($apd == false  && $adt == false  ? 'checked' : '') . '> Não quis</input>
              <input type="radio" name="confirmacao" value="esp" ' . ($apd === null && $adt === null ? 'checked' : '') . '> Em espera</input>
              <input type="hidden" name="codcont" value="' . $codcont . '">
              <input type="submit" name="attencpet" value="Atualizar">
            </div>
          </form>';
                    } else if ($tpcont == "apd" && $lig == true) {
                      echo '
          <form action="attcont.php" method="POST">
            <div class="radioGC">
              <input type="radio" name="confirmacao" value="adt" ' . ($adt == true ? 'checked' : '') . ' disabled> Adotou</input>
              <input type="radio" name="confirmacao" value="apd" ' . ($apd == true ? 'checked' : '') . ' > Apadrinhou</input>
              <input type="radio" name="confirmacao" value="nquis" ' . ($apd == false  && $adt == false ? 'checked' : '') . '> Não quis</input>
              <input type="radio" name="confirmacao" value="esp" ' . ($apd === null && $adt === null ? 'checked' : '') . '> Em espera</input>
              <input type="hidden" name="codcont" value="' . $codcont . '">
              <input type="submit" name="attencpet" value="Atualizar">
            </div>
          </form>';
                    } else if ($tpcont == "adt_apd" && $lig == true) {
                      echo '
          <form action="attcont.php" method="POST">
            <div class="radioGC">
              <input type="radio" name="confirmacao" value="adt" ' . ($adt == true ? 'checked' : '') . ' > Adotou</input>
              <input type="radio" name="confirmacao" value="apd" ' . ($apd == true ? 'checked' : '') . '> Apadrinhou</input>
              <input type="radio" name="confirmacao" value="nquis" ' . ($apd == false && $adt == false  ? 'checked' : '') . '> Não quis</input>
              <input type="radio" name="confirmacao" value="esp" ' . ($apd === null && $adt === null ? 'checked' : '') . '> Em espera</input>
              <input type="hidden" name="codcont" value="' . $codcont . '">
              <input type="submit" name="attencpet" value="Atualizar">
            </div>
          </form>';
                    } else {
                      echo '
          <form action="attcont.php" method="POST">
            <div class="radioGC">
              <input type="radio" name="confirmacao" value="adt" ' . ($adt == true ? 'checked' : '') . ' disabled> Adotou</input>
              <input type="radio" name="confirmacao" value="apd" ' . ($apd == true ? 'checked' : '') . ' disabled> Apadrinhou</input>
              <input type="radio" name="confirmacao" value="nquis" ' . ($apd == false  && $adt == false  ? 'checked' : '') . ' disabled> Não quis</input>
              <input type="radio" name="confirmacao" value="esp" ' . ($apd === null && $adt === null ? 'checked' : '') . '> Em espera</input>
              <input type="hidden" name="codcont" value="' . $codcont . '" >
              <input type="submit" name="attencpet" value="Atualizar" disabled>
            </div>
          </form>';
                    }

                    if ($cnf2 == true) {
                      $incont = "<div class='stts2'>Liguei</div>";
                    } else if ($cnf2 === null) {
                      $incont = "<div class='stts3'>Esperando para ligar</div>";
                    } else {
                      $incont = "<div class='stts1'>Não vou ligar</div>";
                    }

                    echo "<div class='sttsalign'>
        <h5 class='stts'>O contatante está como:" . $incont . "</h5>";

                    if ($ativo == false || $ativo===null) {
                      $ativo2 = "Arquivar Contato";
                    } else {
                      $ativo2 = "Desarquivar Contato";
                    }


                    echo '
            <form action="attcont.php" method="POST">
              <input type="hidden" name="codcont" value="' . $codcont . '" >
              <input type="hidden" name="tpatvpet" value="' . $ativo . '" >
              <input type="submit" name="dstvpet" value="' . $ativo2 . '">
            </form>
          </div>';
                  } else {
                    echo "Tutor não encontrado.";
                  }
                } else {
                  echo "Erro na consulta: " . mysqli_error($mysqli);
                }
                echo " 
        </div>";
              } else {
                echo "Nenhum registro encontrado.";
              }
            }
          }
        }
      }
    }



    echo "
      </div>";
    
    echo "
      <div id='content_op3' style='display: none;'>
        <p>Meus anuncios de Serviço</p>
        <form method='get' action=''>
          <input type='hidden' name='opc' value='op3'> 
          <select id='apvserv' name='apvserv'>
            <option value='aprovado'>Aprovados</option>
            <option value='reprovado' " . (isset($_GET['apvserv']) && $_GET['apvserv'] == 'reprovado' ? 'selected' : '') . ">Reprovados</option>
            <option value='analise' " . (isset($_GET['apvserv']) && $_GET['apvserv'] == 'analise' ? 'selected' : '') . ">Em análise</option>
          </select>
          <select id='dstserv' name='dstserv'>
            <option value='Ativado'>Ativos</option>
            <option value='Desativado' " . (isset($_GET['dstserv']) && $_GET['dstserv'] == 'Desativado' ? 'selected' : '') . ">Desativados</option>
          </select>
          <input class='filtrar' type='submit' value='Filtrar'>
        </form>";

    if ($_SERVER['REQUEST_METHOD'] == 'GET') {
      $apv = isset($_GET['apvserv']) ? $_GET['apvserv'] : "aprovado"; // Obtain the filter value from the query string or use the default
      $dst = isset($_GET['dstserv']) ? $_GET['dstserv'] : "Ativado";
    }


    $sql = "SELECT s.*, i.img 
                        FROM servico s
                        LEFT JOIN imagem i ON s.servcod = i.servicocodserv
                        WHERE ";

    if ($apv === "aprovado") {
      $sql .= "s.aprovacaoserv = true ";
    } elseif ($apv === "reprovado") {
      $sql .= "s.aprovacaoserv = false ";
    } elseif ($apv === "analise") {
      $sql .= "s.aprovacaoserv IS NULL ";
    }
    if ($dst === "Ativado") {
      $sql .= "AND (s.bloqueioserv = false or s.bloqueioserv is null)";
    } elseif ($dst === "Desativado") {
      $sql .= "AND s.bloqueioserv = true";
    }

    $sql .= " AND s.pessoa_codp = '$cod'";

    $result2 = mysqli_query($mysqli, $sql);

    if (!$result2) {
      echo "Nenhum Serviço Cadastrado";
      die("Erro na consulta SQL: " . mysqli_error($mysqli));
    } else {
      $count = 0;
      while ($serv_data = mysqli_fetch_assoc($result2)) {
        $count++;
        $codserv = $serv_data['servcod'];
        $atvo = $serv_data['bloqueioserv'];
        $apvpet = $serv_data['aprovacaoserv'];
        $mtvrep = $serv_data['motivorepserv'];

        if ($atvo == true) {
          $atvmsg = "Ativar";
        } else if ($atvo == false || $atvo == null) {
          $atvmsg = "Desativar";
        }

        $sql_imagem = "SELECT * FROM imagem WHERE servicocodserv='$codserv';";
        $result_imagem = $mysqli->query($sql_imagem);

        if ($row_imagem = mysqli_fetch_assoc($result_imagem)) {
          $imageData = $row_imagem['img'];
          $imageType = 'image/jpeg';
        } else {
          $imagePath = './assets/semimg.png';
          $imageData = file_get_contents($imagePath);
          $imageType = mime_content_type($imagePath);
        }

        $sql2 = "SELECT COUNT(*) as total FROM contatoserv WHERE scodserv='$codserv';";
        $result3 = mysqli_query($mysqli, $sql2);

        if (!$result3) {
          // Trate o erro aqui, se necessário
        } else {
          $row = mysqli_fetch_assoc($result3);
          $cont = $row['total'];
          if ($cont == 1) {
            $cont .= " Contato De Serviço";
          } else {
            $cont .= " Contatos de Serviço";
          }
        }
        $cont3 = "Sem clientes.";
        $sql5 = "SELECT COUNT(*) as total FROM contatoserv WHERE scodserv='$codserv' and contratou=true;";
        $result4 = mysqli_query($mysqli, $sql5);

        if ($result4) {
          $row = mysqli_fetch_assoc($result4);
          $cont3 = $row['total'];
          if ($cont3 == 1) {
            $cont3 .= " cliente.";
          } else {
            $cont3 .= " clientes.";
          }
        }

        echo "
        <div class='anuserv' onmouseover='onMouseOver(this)' onmouseout='onMouseOut(this)'>
          <img class='imgserv' src='data:" . $imageType . ";base64," . base64_encode($imageData) . "' alt='Serviço Image'>
          <div class='anuinfo'>
            <label id='nomeserv-label' >" . $serv_data['nomeserv'] . "</label>
            <label id='preco-label' >R$ " . $serv_data['preco'] . ",00</label>
            <label id='localizacao-label' >" . $serv_data['estados'] . ", " . $serv_data['cidades'] . "</label>
            <label>" . $cont . "</label>
            <label>" . $cont3 . "</label>
         ";
        if (isset($apvpet) && $apvpet == false) {
          echo "
            <label>Motivo da reprovação:</label>
            <textarea id='auto-resize-textarea' oninput='autoResize(this)' disabled style='border-radius:5px; text-align: left;'>" . $mtvrep . "</textarea>
          ";
        }
        echo " 
            <div class='buttons'>
              <button><a class='edt' href='editarserv.php?codigo=" . $serv_data['servcod'] . "'>Editar</a></button>
              <form action='editserv.php' method='post'>
                  <input type='hidden' name='petcod' value='" . $codserv . "' required />
                  <input type='hidden' name='atv' value='" . $atvo . "' required />  
                  <input type='hidden' name='nome' value='" . $serv_data['nomeserv'] . "' required />  
                  <button type='submit' name='dst-btn' class='btndesativar' >  " . $atvmsg . "</button>
              </form>
            </div>
          </div>
        </div>
      ";
      }
    }
    echo "
    </div>
      <div id='content_op4' style='display: none;'>
        <p>Gerenciar contatos dos meus Serviços</p><form method='get' action=''>
        <input type='hidden' name='opc' value='op4'> 
        <select id='finalcontserv' name='finalcontserv'>
          <option value='aberto'>Não finalizados</option>
          <option value='adt' " . (isset($_GET['finalcontserv']) && $_GET['finalcontserv'] == 'adt' ? 'selected' : '') . ">Finalizados com Contrato</option>
          <option value='sctt' " . (isset($_GET['finalcontserv']) && $_GET['finalcontserv'] == 'sctt' ? 'selected' : '') . ">Finalizados sem Contrato</option>
        </select>
        <select id='arqcontserv' name='arqcontserv'>
          <option value='Ativado'>Em aberto</option>
          <option value='Desativado' " . (isset($_GET['arqcontserv']) && $_GET['arqcontserv'] == 'Desativado' ? 'selected' : '') . ">Arquivados</option>
        </select>
        <input class='filtrar' type='submit' value='Filtrar'>
      </form>";

    if (isset($_GET['servcod'])) {
      $sql3 = "SELECT * FROM contatoserv WHERE codconts='" . $_GET['servcod'] . "'";

      $result4 = mysqli_query($mysqli, $sql3);

      if ($result4) {
        if ($row = mysqli_fetch_assoc($result4)) {


          if ($row) {
            $contratante = $row['pcodp'];
            $cnf1 = $row['confirmserv'];
            $cnf2 = $row['confirm_pe'];
            $lig = $row['ligouserv'];
            $codcont = $row['codconts'];
            $cnt = $row['contratou'];
            $ativo = $row['arquivarserv'];
            $codserv = $row['scodserv'];

            $sql4 = "SELECT nickname FROM pessoa WHERE pcod = '$contratante'";
            $result5 = mysqli_query($mysqli, $sql4);

            echo "
        <div class='GC'>";
            if ($result5) {
              // Verifique se a consulta foi bem-sucedida
              if (mysqli_num_rows($result5) > 0) {
                $row = mysqli_fetch_assoc($result5);
                $nickname = $row['nickname'];

                $sql = "SELECT s.*, i.img 
                        FROM servico s
                        LEFT JOIN imagem i ON s.servcod = i.servicocodserv
                        WHERE s.servcod = '$codserv'";

                $result2 = mysqli_query($mysqli, $sql);

                if (!$result2) {
                  echo "Nenhum Serviço Cadastrado";
                  die("Erro na consulta SQL: " . mysqli_error($mysqli));
                } else {
                  $count = 0;

                  while ($serv_data = mysqli_fetch_assoc($result2)) {
                    $count++;
                    $codserv = $serv_data['servcod'];
                    $nmserv = $serv_data['nomeserv'];
                  }
                }

                echo "
          <div class='NeP'><h5>quem criou o contato: <a href='./perfil.php?pcod=$contratante'>" . $nickname . "</a>
          <br>qual serviço tem interesse: <a href='./anuncio/perfilserv.php?servcod=" . $codserv . "'> " . $nmserv . "</a></h5></div>";
                echo '
          <form action="attcont.php" method="POST">
          <div class="radioGC">
            <input type="radio" name="confirmacao" value="ligou" ' . ($cnf1 == true ? 'checked' : '') . '> Ligou</input>
            <input type="radio" name="confirmacao" value="nao_ligou" ' . ($cnf1 == false  ? 'checked' : '') . '> Não Ligou</input>
            <input type="radio" name="confirmacao" value="esp" ' . ($cnf1 === null ? 'checked' : '') . '> Em espera</input>
            <input type="hidden" name="codcont" value="' . $codcont . '">
            <input type="submit" name="attligserv" value="Atualizar ligação">
            </div>
          </form>';

                if ($lig == true) {
                  echo '
          <form action="attcont.php" method="POST">
          <div class="radioGC">
            <input type="radio" name="confirmacao" value="ctt" ' . ($cnt == true ? 'checked' : '') . '> Contratou</input>
            <input type="radio" name="confirmacao" value="nquis" ' . ($cnt == false  && $cnt == false  ? 'checked' : '') . '> Não quis</input>
            <input type="radio" name="confirmacao" value="esp" ' . ($cnt === null && $cnt === null ? 'checked' : '') . '> Em espera</input>
            <input type="hidden" name="codcont" value="' . $codcont . '">
            <input type="submit" name="attencserv" value="Atualizar">
            </div>
          </form>';
                } else {
                  echo '
          <form action="attcont.php" method="POST">
          <div class="radioGC">
            <input type="radio" name="confirmacao" value="ctt" ' . ($cnt == true ? 'checked' : '') . ' disabled> Contratou</input>
            <input type="radio" name="confirmacao" value="nquis" ' . ($cnt == false  && $cnt == false  ? 'checked' : '') . ' disabled> Não quis</input>
            <input type="radio" name="confirmacao" value="esp" ' . ($cnt === null && $cnt === null ? 'checked' : '') . '> Em espera</input>
            <input type="hidden" name="codcont" value="' . $codcont . '" >
            <input type="submit" name="attencserv" value="Atualizar" disabled>
            </div>
          </form>';
                }

                if ($cnf2 == true) {
                  $incont = "<div class='stts2'>Liguei</div>";
                } else if ($cnf2 === null) {
                  $incont = "<div class='stts3'>Esperando para ligar</div>";
                } else {
                  $incont = "<div class='stts1'>Não vou ligar</div>";
                }

                echo "<div class='sttsalign'>
                  <h5 class='stts'>O contatante está como " . $incont . "</h5>";

                if ($ativo == false || $ativo === null) {
                  $ativo2 = "Arquivar Contato";
                } else {
                  $ativo2 = "Desarquivar Contato";
                }

                echo '
          <form action="attcont.php" method="POST">
            <input type="hidden" name="codcont" value="' . $codcont . '" >
            <input type="hidden" name="tpatvpet" value="' . $ativo . '" >
            <input type="submit" name="dstvserv" value="' . $ativo2 . '">
          </form>
          </div>';
              } else {
                echo "contratante não encontrado.";
              }
            } else {
              echo "Erro na consulta: " . mysqli_error($mysqli);
            }
            echo "
        </div>";
          } else {
            echo "Nenhum registro encontrado.";
          }
        }
      }
    } else {
      $sql = "SELECT s.*, i.img 
                        FROM servico s
                        LEFT JOIN imagem i ON s.servcod = i.servicocodserv
                        WHERE s.pessoa_codp = '$cod'";

      $result2 = mysqli_query($mysqli, $sql);

      if (!$result2) {
        echo "Nenhum Serviço Cadastrado";
        die("Erro na consulta SQL: " . mysqli_error($mysqli));
      } else {
        $count = 0;

        while ($serv_data = mysqli_fetch_assoc($result2)) {
          $count++;
          $codserv = $serv_data['servcod'];
          $nmserv = $serv_data['nomeserv'];

          if ($_SERVER['REQUEST_METHOD'] == 'GET') {
            $apv = isset($_GET['finalcontserv']) ? $_GET['finalcontserv'] : "aberto";
            $dst = isset($_GET['arqcontserv']) ? $_GET['arqcontserv'] : "Ativado";
          }
          $sql3 = "SELECT * FROM contatoserv WHERE";

          if ($apv === "aberto") {
            $sql3 .= " contratou IS NULL ";
          } elseif ($apv === "adt") {
            $sql3 .= " contratou = true ";
          } elseif ($apv === "sctt") {
            $sql3 .= " contratou = false ";
          }

          if ($dst === "Ativado") {
            $sql3 .= "AND (arquivarserv = false or arquivarserv is null)";
          } elseif ($dst === "Desativado") {
            $sql3 .= "AND arquivarserv = true";
          }

          $sql3 .= " AND scodserv='$codserv'";

          $result4 = mysqli_query($mysqli, $sql3);

          if ($result4) {
            if ($row = mysqli_fetch_assoc($result4)) {


              if ($row) {
                $contratante = $row['pcodp'];
                $cnf1 = $row['confirmserv'];
                $cnf2 = $row['confirm_pe'];
                $lig = $row['ligouserv'];
                $codcont = $row['codconts'];
                $cnt = $row['contratou'];
                $ativo = $row['arquivarserv'];

                $sql4 = "SELECT nickname FROM pessoa WHERE pcod = '$contratante'";
                $result5 = mysqli_query($mysqli, $sql4);

                echo "
        <div class='GC'>";
                if ($result5) {
                  // Verifique se a consulta foi bem-sucedida
                  if (mysqli_num_rows($result5) > 0) {
                    $row = mysqli_fetch_assoc($result5);
                    $nickname = $row['nickname'];
                    echo "
          <div class='NeP'><h5>quem criou o contato: <a href='./perfil.php?pcod=$contratante'>" . $nickname . "</a>
          <br>qual serviço tem interesse: <a href='./anuncio/perfilserv.php?servcod=" . $codserv . "'> " . $nmserv . "</a></h5></div>";
                    echo '
          <form action="attcont.php" method="POST">
          <div class="radioGC">
            <input type="radio" name="confirmacao" value="ligou" ' . ($cnf1 == true ? 'checked' : '') . '> Ligou</input>
            <input type="radio" name="confirmacao" value="nao_ligou" ' . ($cnf1 == false  ? 'checked' : '') . '> Não Ligou</input>
            <input type="radio" name="confirmacao" value="esp" ' . ($cnf1 === null ? 'checked' : '') . '> Em espera</input>
            <input type="hidden" name="codcont" value="' . $codcont . '">
            <input type="submit" name="attligserv" value="Atualizar ligação">
            </div>
          </form>';

                    if ($lig == true) {
                      echo '
          <form action="attcont.php" method="POST">
          <div class="radioGC">
            <input type="radio" name="confirmacao" value="ctt" ' . ($cnt == true ? 'checked' : '') . '> Contratou</input>
            <input type="radio" name="confirmacao" value="nquis" ' . ($cnt == false  && $cnt == false  ? 'checked' : '') . '> Não quis</input>
            <input type="radio" name="confirmacao" value="esp" ' . ($cnt === null && $cnt === null ? 'checked' : '') . '> Em espera</input>
            <input type="hidden" name="codcont" value="' . $codcont . '">
            <input type="submit" name="attencserv" value="Atualizar">
            </div>
          </form>';
                    } else {
                      echo '
          <form action="attcont.php" method="POST">
          <div class="radioGC">
            <input type="radio" name="confirmacao" value="ctt" ' . ($cnt == true ? 'checked' : '') . ' disabled> Contratou</input>
            <input type="radio" name="confirmacao" value="nquis" ' . ($cnt == false  && $cnt == false  ? 'checked' : '') . ' disabled> Não quis</input>
            <input type="radio" name="confirmacao" value="esp" ' . ($cnt === null && $cnt === null ? 'checked' : '') . '> Em espera</input>
            <input type="hidden" name="codcont" value="' . $codcont . '" >
            <input type="submit" name="attencserv" value="Atualizar" disabled>
            </div>
          </form>';
                    }

                    if ($cnf2 == true) {
                      $incont = "<div class='stts2'>Liguei</div>";
                    } else if ($cnf2 === null) {
                      $incont = "<div class='stts3'>Esperando para ligar</div>";
                    } else {
                      $incont = "<div class='stts1'>Não vou ligar</div>";
                    }

                    echo "<div class='sttsalign'>
                  <h5 class='stts'>O contatante está como " . $incont . "</h5>";

                    if ($ativo == true) {
                      $ativo2 = "Arquivar Contato";
                    } else {
                      $ativo2 = "Desarquivar Contato";
                    }

                    echo '
          <form action="attcont.php" method="POST">
            <input type="hidden" name="codcont" value="' . $codcont . '" >
            <input type="hidden" name="tpatvpet" value="' . $ativo . '" >
            <input type="submit" name="dstvserv" value="' . $ativo2 . '">
          </form>
          </div>';
                  } else {
                    echo "contratante não encontrado.";
                  }
                } else {
                  echo "Erro na consulta: " . mysqli_error($mysqli);
                }
                echo "
        </div>";
              } else {
                echo "Nenhum registro encontrado.";
              }
            }
          }
        }
      }
    }

    echo "
      </div>
      <div id='content_op5' style='display: none;'>
        <p>Meus contatos para Pets</p>
          <form method='get' action=''>
          <input type='hidden' name='opc' value='op5'> 
          <select id='finalcontpet2' name='finalcontpet2'>
            <option value='aberto'>Não finalizados</option>
            <option value='adt' " . (isset($_GET['finalcontpet2']) && $_GET['finalcontpet2'] == 'adt' ? 'selected' : '') . ">Finalizados com Adoção</option>
            <option value='apd' " . (isset($_GET['finalcontpet2']) && $_GET['finalcontpet2'] == 'apd' ? 'selected' : '') . ">Finalizados com Apadrinhamento</option>
            <option value='sadtapd' " . (isset($_GET['finalcontpet2']) && $_GET['finalcontpet2'] == 'sadtapd' ? 'selected' : '') . ">Finalizados sem adoção ou apadrinhamento</option>
          </select>
          <select id='arqcontpet2' name='arqcontpet2'>
            <option value='Ativado'>Em aberto</option>
            <option value='Desativado' " . (isset($_GET['arqcontpet2']) && $_GET['arqcontpet2'] == 'Desativado' ? 'selected' : '') . ">Arquivados</option>
          </select>
          <input class='filtrar' type='submit' value='Filtrar'>
      </form>";

    if ($_SERVER['REQUEST_METHOD'] == 'GET') {
      $apv = isset($_GET['finalcontpet2']) ? $_GET['finalcontpet2'] : "aberto";
      $dst = isset($_GET['arqcontpet2']) ? $_GET['arqcontpet2'] : "Ativado";
    }

    if (isset($_GET['petcod'])) {
      $sql3 = "SELECT * FROM contatopet WHERE ";
      $sql3 .= " codcontp='" . $_GET['petcod'] . "'";
    } else {
      $sql3 = "SELECT * FROM contatopet WHERE ";

      if ($apv === "aberto") {
        $sql3 .= "Apadrinhou IS NULL AND adotou IS NULL ";
      } elseif ($apv === "adt") {
        $sql3 .= "adotou = true ";
      } elseif ($apv === "apd") {
        $sql3 .= "Apadrinhou = true ";
      } elseif ($apv === "sadtapd") {
        $sql3 .= "Apadrinhou = false and adotou=false ";
      }

      if ($dst === "Ativado") {
        $sql3 .= "AND (arquivarpet = false or arquivarpet is null)";
      } elseif ($dst === "Desativado") {
        $sql3 .= "AND arquivarpet = true";
      }

      $sql3 .= " AND pfcodp='$cod'";
    }



    $result4 = mysqli_query($mysqli, $sql3);

    if ($result4) {
      while ($row = mysqli_fetch_assoc($result4)) {
        $codigopet = $row['petcodpet'];

        $sql = "SELECT p.*, i.img,jf.* 
                        FROM pet p
                        LEFT JOIN imagem i ON p.petcod = i.petcodpet
                        JOIN pessoa jf ON jf.pcod=p.pessoacodp WHERE p.petcod = '$codigopet'";

        $result2 = mysqli_query($mysqli, $sql);

        if ($result2) {
          $pet_data = mysqli_fetch_assoc($result2);
          $nmpet = $pet_data['nomepet'];
          $anc = $pet_data['pessoacodp'];
          $nicknameanc = $pet_data['nickname'];

          if ($row) {
            $tpcont = $row['tipocont'];
            $cnf1 = $row['confirmpet'];
            $ativo = $row['arquivarpet'];
            $cnf2 = $row['confirmpe'];
            $codcont = $row['codcontp'];
            $adt = $row['adotou'];
            $apd = $row['apadrinhou'];

            $sql4 = "SELECT nickname FROM pessoa WHERE pcod = '$cod'";
            $result5 = mysqli_query($mysqli, $sql4);
            echo "
        <div class='GC'>";
            if ($result5) {
              // Verifique se a consulta foi bem-sucedida
              if (mysqli_num_rows($result5) > 0) {
                $row = mysqli_fetch_assoc($result5);
                $nickname = $row['nickname'];
                echo "
                <div class='NeP'><h5>Anunciante: <a href='./perfil.php?pcod=$anc'>" . $nicknameanc . "</a>
                <br>pet do contato: <a href='./anuncio/perfilpet.php?petcod=" . $codigopet . "'>  " . $nmpet . "</a></h5>
                ";

                if ($ativo == true) {
                  $ativ = " mas está arquivado.";
                } else {
                  $ativ = " mas está em aberto.";
                }

                if ($adt == true) {
                  $final = " finalizou com sua adoção";
                } else if ($apd == true) {
                  $final = " finalizou com seu apadrinhamento";
                } else if ($apd === null && $adt === null) {
                  $final = " não finalizou ainda";
                } else {
                  $final = " foi encerrado";
                }

                if ($cnf1 == true) {
                  $incont = "<div class='stts2'>Ligou</div>";
                } else if ($cnf1 === null) {
                  $incont = "<div class='stts3'>Esperando você ligar</div>";
                } else {
                  $incont = "<div class='stts1'>Não Ligou</div>";
                }

              
               

                if (($ativo == true) && ($final === " foi encerrado")) {
                  echo "<h5 class='flcn'>Fale conosco caso queira continuar esse contato ou refaze-lo.</h5></div>";
                  echo '
          <form action="attcont.php" method="POST">
          <div class="radioGC">
            <input type="radio" name="confirmacao" value="ligou" ' . ($cnf2 == true ? 'checked' : '') . ' disabled> Liguei</input>
            <input type="radio" name="confirmacao" value="nao_ligou" ' . ($cnf2 == false  ? 'checked' : '') . ' disabled> Não Liguei</input>
            <input type="radio" name="confirmacao" value="esp" ' . ($cnf2 === null ? 'checked' : '') . ' disabled> Em espera</input>
            <input type="hidden" name="codcont" value="' . $codcont . '">
            <input type="submit" name="attligpet2" value="Atualizar ligação" disabled>
            </div>
            </form>';
                } else {
                  echo '</div>
          <form action="attcont.php" method="POST">
          <div class="radioGC1">
            <input type="radio" name="confirmacao" value="ligou" ' . ($cnf2 == true ? 'checked' : '') . '> Liguei</input>
            <input type="radio" name="confirmacao" value="nao_ligou" ' . ($cnf2 == false  ? 'checked' : '') . '> Não Liguei</input>
            <input type="radio" name="confirmacao" value="esp" ' . ($cnf2 === null ? 'checked' : '') . '> Em espera</input>
            <input type="hidden" name="codcont" value="' . $codcont . '">
            <input type="submit" name="attligpet2" value="Atualizar ligação">
            </div>
          </form>';
                }
                    echo "<div class='sttsalignpet'>
          <h5 class='sttspet'>O anunciante está como " . $incont . "</h5>
          <h5 class='NeP1'
          <br>O contato " . $final ."</br>". $ativ . "</h5>";

          echo "</div>";
              } else {
                echo "Tutor não encontrado.";
              }
            } else {
              echo "Erro na consulta: " . mysqli_error($mysqli);
            }
            echo "
        </div>";
          } else {
            echo "Nenhum registro encontrado.";
          }
        }
      }
    }

    echo "
      </div>
      <div id='content_op6' style='display: none;'>
        <p>Meus contatos para serviços</p>
        <form method='get' action=''>
          <input type='hidden' name='opc' value='op6'> 
          <select id='finalcontserv2' name='finalcontserv2'>
            <option value='aberto'>Não finalizados</option>
            <option value='adt' " . (isset($_GET['finalcontserv2']) && $_GET['finalcontserv2'] == 'adt' ? 'selected' : '') . ">Finalizados com Contrato</option>
            <option value='sctt' " . (isset($_GET['finalcontserv2']) && $_GET['finalcontserv2'] == 'sctt' ? 'selected' : '') . ">Finalizados sem Contrato</option>
          </select>
          <select id='arqcontserv2' name='arqcontserv2'>
            <option value='Ativado'>Em aberto</option>
            <option value='Desativado' " . (isset($_GET['arqcontserv2']) && $_GET['arqcontserv2'] == 'Desativado' ? 'selected' : '') . ">Arquivados</option>
          </select>
          <input class='filtrar' type='submit' value='Filtrar'>
        </form>";

    if ($_SERVER['REQUEST_METHOD'] == 'GET') {
      $apv = isset($_GET['finalcontserv2']) ? $_GET['finalcontserv2'] : "aberto";
      $dst = isset($_GET['arqcontserv2']) ? $_GET['arqcontserv2'] : "Ativado";
    }

    $sql3 = "SELECT * FROM contatoserv WHERE";

    if (isset($_GET['servcod'])) {
      $sql3 .= " codconts='" . $_GET['servcod'] . "'";
    } else {
      if ($apv === "aberto") {
        $sql3 .= " contratou IS NULL ";
      } elseif ($apv === "adt") {
        $sql3 .= " contratou = true ";
      } elseif ($apv === "sctt") {
        $sql3 .= " contratou = false ";
      }

      if ($dst === "Ativado") {
        $sql3 .= "AND (arquivarserv = false or arquivarserv is null)";
      } elseif ($dst === "Desativado") {
        $sql3 .= "AND arquivarserv = true";
      }
      $sql3 .= " AND pcodp='$cod'";
    }

    $result4 = mysqli_query($mysqli, $sql3);

    if ($result4) {
      while ($row = mysqli_fetch_assoc($result4)) {
        $codigopet = $row['scodserv'];
        $sql = "SELECT s.*, i.img,jf.* 
                    FROM servico s
                    LEFT JOIN imagem i ON s.servcod = i.servicocodserv JOIN pessoa jf ON jf.pcod=s.pessoa_codp
                    WHERE s.servcod = '$codigopet'";

        $result2 = mysqli_query($mysqli, $sql);

        if ($result2) {
          $pet_data = mysqli_fetch_assoc($result2);
          $nmpet = $pet_data['nomeserv'];
          $codigopet = $pet_data['servcod'];
          $anc = $pet_data['pessoa_codp'];
          $nicknameanc = $pet_data['nickname'];

          if ($row) {
            $cnf1 = $row['confirmserv'];
            $ativo = $row['arquivarserv'];
            $cnf2 = $row['confirm_pe'];
            $codcont = $row['codconts'];
            $ctc = $row['contratou'];
            $avaliou = $row['avaliacao'];

            $sql4 = "SELECT nickname FROM pessoa WHERE pcod = '$cod'";
            $result5 = mysqli_query($mysqli, $sql4);
            echo "
        <div class='GC'>";
            if ($result5) {
              // Verifique se a consulta foi bem-sucedida
              if (mysqli_num_rows($result5) > 0) {
                $row = mysqli_fetch_assoc($result5);
                $nickname = $row['nickname'];
                echo "
                <div class='NeP'><h5>Anunciante: <a href='./perfil.php?pcod=$anc'>" . $nicknameanc . "</a>
                <br>Serviço do contato: <a href='./anuncio/perfilserv.php?servcod=" . $codigopet . "'>" . $nmpet . "</a><br></h5>
                ";

                if ($ativo == true) {
                  $ativ = " mas está arquivado.";
                } else {
                  $ativ = " mas está em aberto.";
                }

                if ($ctc == true) {
                  $final = " finalizou com sua contratação";
                } else if ($ctc === null) {
                  $final = " não finalizou ainda";
                } else {
                  $final = " foi encerrado";
                }

                if ($cnf1 == true) {
                  $incont = "<div class='stts1'>Ligou</div>";
                } else if ($cnf1 === null) {
                  $incont = "<div class='stts3'>Esperando você ligar</div>";
                } else {
                  $incont = "<div class='stts1'>Não ligou</div>";
                }

               
                if ($ativo == true && ($final === " foi encerrado")) {
                  echo "<h5 class='flcn'>Fale conosco caso queira continuar esse contato ou refaze-lo.</h5></div>";
                  echo '
          <form action="attcont.php" method="POST">
          <div class="radioGC1">
            <input type="radio" name="confirmacao" value="ligou" ' . ($cnf2 == true ? 'checked' : '') . ' disabled> Liguei</input>
            <input type="radio" name="confirmacao" value="nao_ligou" ' . ($cnf2 == false  ? 'checked' : '') . ' disabled> Não Liguei</input>
            <input type="radio" name="confirmacao" value="esp" ' . ($cnf2 === null ? 'checked' : '') . ' disabled> Em espera</input>
            <input type="hidden" name="codcont" value="' . $codcont . '">
            <input type="submit" name="attligserv2" value="Atualizar ligação" disabled>
          </div>
            </form>';
                } else {
                  echo '</div>
          <form action="attcont.php" method="POST">
          <div class="radioGC1">
            <input type="radio" name="confirmacao" value="ligou" ' . ($cnf2 == true ? 'checked' : '') . '> Liguei</input>
            <input type="radio" name="confirmacao" value="nao_ligou" ' . ($cnf2 == false  ? 'checked' : '') . '> Não Liguei</input>
            <input type="radio" name="confirmacao" value="esp" ' . ($cnf2 === null ? 'checked' : '') . '> Em espera</input>
            <input type="hidden" name="codcont" value="' . $codcont . '">
            <input type="submit" name="attligserv2" value="Atualizar ligação">
            </div>
          </form>';
                }

                echo "<div class='sttsalignpet'>
                <h5 class='sttspet'>O anunciante está como " . $incont . "</h5>
                <h5 class='NeP1'>
                <br>O contato " . $final ."</br>". $ativ . "</h5></div>";


                if ($ctc == true) {
                  if ($avaliou === null) {
                    echo "
          <button class='avaliar'><a class='avaliar' href='./anuncio/perfilserv.php?servcod=" . $codigopet . "'>Avaliar</a></button>";
                  } else {
                    echo '
          <form action="attcont.php" method="POST">
            <input type="hidden" name="codcont" value="' . $codcont . '">
            <input type="submit" name="rmvaval" value="Remover Avaliação">
          </form>';
                  }
                }
              } else {
                echo "Tutor não encontrado.";
              }
            } else {
              echo "Erro na consulta: " . mysqli_error($mysqli);
            }
            echo "
        </div>";
          } else {
            echo "Nenhum registro encontrado.";
          }
        }
      }
    }

    echo "
      </div>
    </div>
    ";
    ?>
  </div>
  <?php include('./lib/footer.php'); ?>

  <script>
    document.addEventListener('DOMContentLoaded', function() {
      const buttons = document.querySelectorAll('.sbutton_op');
      let selectedOption = 'op1';

      // Verifique se há um parâmetro 'opc' na URL
      const urlParams = new URLSearchParams(window.location.search);
      if (urlParams.has('opc')) {
        const paramOption = urlParams.get('opc');
        if (paramOption === 'op1' || paramOption === 'op2' || paramOption === 'op3' || paramOption === 'op4' || paramOption === 'op5' || paramOption === 'op6') {
          selectedOption = paramOption;
        }
      }

      // Atualize a URL ao clicar em um botão
      buttons.forEach(function(button) {
        button.addEventListener('click', function(event) {
          const option = event.target.getAttribute('data-option');
          selectedOption = option;
          updateURL(selectedOption);
          updateContent(selectedOption);

          // Remova a classe "selected" de todos os botões e adicione àquele que foi clicado
          buttons.forEach(function(btn) {
            btn.classList.remove('selected');
          });
          event.target.classList.add('selected');
        });
      });

      // Função para atualizar a URL
      function updateURL(option) {
        const newURL = window.location.pathname + '?opc=' + option;
        window.history.replaceState(null, null, newURL);
      }

      // Defina a opção selecionada com base na URL
      buttons.forEach(function(button) {
        const option = button.getAttribute('data-option');
        if (option === selectedOption) {
          button.classList.add('selected');
        }
      });

      // Função para atualizar o conteúdo com base na opção selecionada
      function updateContent(option) {
        const contentOp1 = document.getElementById('content_op1');
        const contentOp2 = document.getElementById('content_op2');
        const contentOp3 = document.getElementById('content_op3');
        const contentOp4 = document.getElementById('content_op4');
        const contentOp5 = document.getElementById('content_op5');
        const contentOp6 = document.getElementById('content_op6');

        contentOp1.style.display = (option === 'op1') ? 'block' : 'none';
        contentOp2.style.display = (option === 'op2') ? 'block' : 'none';
        contentOp3.style.display = (option === 'op3') ? 'block' : 'none';
        contentOp4.style.display = (option === 'op4') ? 'block' : 'none';
        contentOp5.style.display = (option === 'op5') ? 'block' : 'none';
        contentOp6.style.display = (option === 'op6') ? 'block' : 'none';
      }

      // Atualize o conteúdo com base na opção selecionada ao carregar a página
      updateContent(selectedOption);
    });
  </script>

  <script>
    function onMouseOver(obj) {
      var buttons = obj.querySelector('.buttons');
      buttons.style.display = 'block';
      obj.style.backgroundColor = 'var(--fundoprincipal1)';
    }

    function onMouseOut(obj) {
      var buttons = obj.querySelector('.buttons');
      buttons.style.display = 'none';
      obj.style.backgroundColor = 'var(--fundosecundario1)';
    }

    document.querySelectorAll('.btnexcluirpet').forEach(function(button) {
      button.addEventListener('click', function() {
        const codpet = this.getAttribute('data-pet-id');
        handleExcluir(codpet);

        function handleExcluir(codpet) {
          Swal.fire({
            title: 'Tem certeza?',
            text: 'Você está prestes a excluir este registro. Essa ação não pode ser desfeita!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sim, excluir!',
            cancelButtonText: 'Cancelar'
          }).then((result) => {
            if (result.isConfirmed) {
              const xhr = new XMLHttpRequest();
              xhr.open('POST', 'excluirpet.php', true);
              xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
              xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                  if (xhr.status === 200) {
                    const response = JSON.parse(xhr.responseText);
                    if (response.success) {
                      Swal.fire({
                        title: 'Excluído!',
                        text: 'O registro foi excluído com sucesso.',
                        icon: 'success'
                      }).then(() => {
                        // Redirecionar para uma página apropriada após a exclusão
                        window.location.href = 'painel.php';
                      });
                    } else {
                      Swal.fire('Erro', 'Ocorreu um erro ao excluir o registro.', 'error');
                    }
                  } else {
                    Swal.fire('Erro', 'Ocorreu um erro ao excluir o registro.', 'error');
                  }
                }
              };
              xhr.send(`codpet=${codpet}`);
            }
          });
        }
      });
    });

    /*Serviço*/

    document.querySelectorAll('.btnexcluirserv').forEach(function(button) {
      button.addEventListener('click', function() {
        const codserv = this.getAttribute('data-servico-id');
        handleExcluirserv(codserv);

        function handleExcluirserv(codserv) {
          Swal.fire({
            title: 'Tem certeza?',
            text: 'Você está prestes a excluir este registro. Essa ação não pode ser desfeita!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sim, excluir!',
            cancelButtonText: 'Cancelar'
          }).then((result) => {
            if (result.isConfirmed) {
              const xhr = new XMLHttpRequest();
              xhr.open('POST', 'excluirserv.php', true);
              xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
              xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                  if (xhr.status === 200) {
                    const response = JSON.parse(xhr.responseText);
                    if (response.success) {
                      Swal.fire({
                        title: 'Excluído!',
                        text: 'O registro foi excluído com sucesso.',
                        icon: 'success'
                      }).then(() => {
                        // Redirecionar para uma página apropriada após a exclusão
                        window.location.href = 'painel.php';
                      });
                    } else {
                      Swal.fire('Erro', `Ocorreu um erro ao excluir o registro. Motivo: ${response.message}`, 'error');
                    }
                  } else {
                    Swal.fire('Erro', 'Ocorreu um erro ao excluir o registro.', 'error');
                  }
                }
              };
              xhr.send(`codserv=${codserv}`);
            }
          });
        }
      });
    });

    function editServiceInfo(codserv) {
      // Obter os elementos das etiquetas e botões relevantes
      const nomeServLabel = document.getElementById('nomeserv-label');
      const precoLabel = document.getElementById('preco-label');
      const localizacaoLabel = document.getElementById('localizacao-label');
      const editButton = event.target;

      // Criar campos de entrada para edição
      const nomeServInput = document.createElement('input');
      nomeServInput.value = nomeServLabel.textContent;
      const precoInput = document.createElement('input');
      precoInput.value = precoLabel.textContent.replace('R$ ', '').replace(',00', '');
      const localizacaoInput = document.createElement('input');
      localizacaoInput.value = localizacaoLabel.textContent;

      // Substituir etiquetas por campos de entrada
      nomeServLabel.replaceWith(nomeServInput);
      precoLabel.replaceWith(precoInput);
      localizacaoLabel.replaceWith(localizacaoInput);

      // Alterar o texto do botão para "Salvar" e adicionar um evento de clique
      editButton.textContent = 'Salvar';
      editButton.onclick = function() {
        // Obter os valores editados
        const novoNomeServ = nomeServInput.value;
        const novoPreco = precoInput.value;
        const novaLocalizacao = localizacaoInput.value;

        // Enviar os novos valores para o servidor (usando AJAX ou outra abordagem)
        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'editarserv.php', true); // Substitua pelo URL apropriado
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function() {
          if (xhr.readyState === 4) {
            if (xhr.status === 200) {
              // Trate a resposta do servidor, se necessário
              const response = JSON.parse(xhr.responseText);
              if (response.success) {
                // Atualizar as etiquetas com os novos valores
                nomeServLabel.textContent = novoNomeServ;
                precoLabel.textContent = 'R$ ' + novoPreco + ',00';
                localizacaoLabel.textContent = novaLocalizacao;

                // Substituir campos de entrada por etiquetas atualizadas
                nomeServInput.replaceWith(nomeServLabel);
                precoInput.replaceWith(precoLabel);
                localizacaoInput.replaceWith(localizacaoLabel);

                // Atualizar o texto do botão de volta para "Editar"
                editButton.textContent = 'Editar';
                editButton.onclick = function() {
                  editServiceInfo(codserv); // Chama a função de edição novamente
                };
              } else {
                // Trate erros ou exiba uma mensagem de erro
                console.error('Erro ao atualizar o serviço:', response.message);
              }
            } else {
              // Trate erros de comunicação com o servidor
              console.error('Erro na solicitação AJAX');
            }
          }
        };

        // Enviar os dados para o servidor
        xhr.send(`codserv=${codserv}&nome=${novoNomeServ}&preco=${novoPreco}&localizacao=${novaLocalizacao}`);
      };
    }

    // Função para ajustar a altura da textarea no carregamento da página
    function adjustTextareaHeight() {
      var textarea = document.getElementById("auto-resize-textarea");
      textarea.style.height = "auto";
      textarea.style.height = (textarea.scrollHeight) + "px";
    }

    // Chamar a função de ajuste de altura no carregamento da página
    window.onload = adjustTextareaHeight;

    // Função para ajustar a altura da textarea conforme o usuário digita
    function autoResize(textarea) {
      textarea.style.height = "auto";
      textarea.style.height = (textarea.scrollHeight) + "px";
    }
  </script>

</body>

</html>