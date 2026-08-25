<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="petserv.css">
  <link rel="stylesheet" href="../lib/padrao.css">
  <title>Pet Found - Login</title>
</head>

<body>
  <?php session_start(); ?>
  <form method="post" action="./petinsert.php" enctype="multipart/form-data">

    <div class="main-login">

      <div class="left-login">
        <header>Cadastrar pet<svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 512 512">
            <path d="M226.5 92.9c14.3 42.9-.3 86.2-32.6 96.8s-70.1-15.6-84.4-58.5s.3-86.2 32.6-96.8s70.1 15.6 84.4 58.5zM100.4 198.6c18.9 32.4 14.3 70.1-10.2 84.1s-59.7-.9-78.5-33.3S-2.7 179.3 21.8 165.3s59.7 .9 78.5 33.3zM69.2 401.2C121.6 259.9 214.7 224 256 224s134.4 35.9 186.8 177.2c3.6 9.7 5.2 20.1 5.2 30.5v1.6c0 25.8-20.9 46.7-46.7 46.7c-11.5 0-22.9-1.4-34-4.2l-88-22c-15.3-3.8-31.3-3.8-46.6 0l-88 22c-11.1 2.8-22.5 4.2-34 4.2C84.9 480 64 459.1 64 433.3v-1.6c0-10.4 1.6-20.8 5.2-30.5zM421.8 282.7c-24.5-14-29.1-51.7-10.2-84.1s54-47.3 78.5-33.3s29.1 51.7 10.2 84.1s-54 47.3-78.5 33.3zM310.1 189.7c-32.3-10.6-46.9-53.9-32.6-96.8s52.1-69.1 84.4-58.5s46.9 53.9 32.6 96.8s-52.1 69.1-84.4 58.5z" />
          </svg></header>


        <a href="../index.php">
          <h4 class="inicio">◄ Voltar</h4>
        </a>

        <label class="picture" for="picture__input" tabIndex="0">
          <span class="picture__image"></span></label>
        <input type="file" name="picture__input" id="picture__input" accept="image/*">
        <script src="./img.js"></script>

        <div class="textfieldL">
          <label class="usuario" for="nome">Nome</label>
          <input type="text" placeholder="Nome do pet" id="nm" name="nm" required />
        </div>

        <div class="textfieldL">
          <label class="usuario" for="hist">História do pet</label>
          <textarea class="no-resize2" type="text" placeholder="conte-nos um pouco a História do pet" id="hist" name="hist" required></textarea>
        </div>

      </div>

      <button type="submit" class="btn-singup">Cadastrar Pet</button>

      <div class="right-login">

        <div class="textfield">
          <label class="usuario" for="tipet">Tipo de Pet</label>
          <select id="tipet" name="tipet">
            <option value="Cachorro">Cachorro</option>
            <option value="Gato">Gato</option>
            <option value="Passaro">Pássaro</option>
            <option value="Roedor">Roedor</option>
            <option value="Reptil">Réptil</option>
          </select>
        </div>

        <div class="textfield">
          <label class="usuario" for="idade">Faixa etária</label>
          <select id="idade" name="idade">
            <option value="Filhote">Filhote</option>
            <option value="Jovem">Jovem</option>
            <option value="Adulto">Adulto</option>
            <option value="Senior">Senior</option>
            <option value="Idoso">Idoso</option>
          </select>
        </div>

        <div class="textfield">
          <label class="usuario" for="raca">Raça</label>
          <input type="text" placeholder="Raça do pet" id="raca" name="raca" required />
        </div>

        <div class="textfield">
          <label class="usuario" for="Cor">Cor </label>
          <input type="text" placeholder="Cor da pelagem" id="cor" name="cor" required />
        </div>
        <div class="textfield">
          <label class="usuario" for="sexo">Sexo</label>
          <select id="sexo" name="sexo">
            <option value="M">Macho</option>
            <option value="F">Fêmea</option>
          </select>
        </div>

        <div class="textfield">
          <label class="usuario" for="porte">Porte</label>
          <select id="porte" name="porte">
            <option value="Grande">Grande</option>
            <option value="Medio">Médio</option>
            <option value="Pequeno">Pequeno</option>
          </select>
        </div>


        <div class="textfield">
          <label class="usuario" for="end">Estado</label>
          <select name="state" id="state">
            <option value="">Selecione seu estado</option>
            <option value="AC">AC</option>
            <option value="AL">AL</option>
            <option value="AP">AP</option>
            <option value="AM">AM</option>
            <option value="BA">BA</option>
            <option value="CE">CE</option>
            <option value="DF">DF</option>
            <option value="ES">ES</option>
            <option value="GO">GO</option>
            <option value="MA">MA</option>
            <option value="MS">MS</option>
            <option value="MT">MT</option>
            <option value="MG">MG</option>
            <option value="PA">PA</option>
            <option value="PB">PB</option>
            <option value="PR">PR</option>
            <option value="PE">PE</option>
            <option value="PI">PI</option>
            <option value="RJ">RJ</option>
            <option value="RN">RN</option>
            <option value="RS">RS</option>
            <option value="RO">RO</option>
            <option value="RR">RR</option>
            <option value="SC">SC</option>
            <option value="SP">SP</option>
            <option value="SE">SE</option>
            <option value="TO">TO</option>
          </select>
        </div>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
          $(document).ready(function() {
            $('#state').change(function() {
              var selectedState = $(this).val();
              $('#citypet').empty(); // Limpar opções anteriores

              if (selectedState !== 'allstates') {
                $.getJSON('https://servicodados.ibge.gov.br/api/v1/localidades/estados/' + selectedState + '/municipios', function(data) {
                  $.each(data, function(index, city) {
                    $('#citypet').append('<option value="' + city.nome + '">' + city.nome + '</option>');
                  });
                });
              }
            });
          });
        </script>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
          $(document).ready(function() {
            $('form').submit(function(event) {
              var stateValue = $('#state').val();
              var cityValue = $('#citypet').val();

              if (stateValue === '' && cityValue === '') {
                alert('Por favor, selecione um estado e uma cidade.');
                event.preventDefault();
              } else if (stateValue === '') {
                alert('Por favor, selecione um estado.');
                event.preventDefault();
              } else if (cityValue === '') {
                alert('Por favor, selecione uma cidade.');
                event.preventDefault();
              }
            });

            $('#state').change(function() {
              var selectedState = $(this).val();
              $('#citypet').empty();
              $('#citypet').append('<option value="">Selecione sua cidade</option>');

              if (selectedState !== 'allstates') {
                $.getJSON('https://servicodados.ibge.gov.br/api/v1/localidades/estados/' + selectedState + '/municipios', function(data) {
                  $.each(data, function(index, city) {
                    $('#citypet').append('<option value="' + city.nome + '">' + city.nome + '</option>');
                  });
                });
              }
            });
          });
        </script>


        <div class="textfield">
          <label class="usuario" for="end">Cidade</label>
          <select name="citypet" id="citypet">
            <option value="">Selecione sua cidade</option>
          </select>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <?php

        include('../lib/dbconnect.php');

        if (isset($_SESSION["usuario"])) {
          $sqlSelect = "SELECT * FROM pessoa WHERE nickname = '" . $_SESSION["usuario"] . "'";
          $result = mysqli_query($mysqli, $sqlSelect);

          if ($result->num_rows > 0) {
            $row = $result->fetch_assoc();
            $logado = $row['pcod'];
            $ini = substr($logado, 0, 2);

            if ($ini === "PF") {
              echo "<div class='textfield'>
            <label class='usuario' for='fnl'>Finalidade</label>
            <select id='fnl' name='fnl'>
              <option value='Adocao'>Adoção</option>
            </select>
          </div> ";
            } else if ($ini === "PJ") {

              $sqlSelect2 = "SELECT * FROM juridica WHERE cod_p = '" . $logado . "'";
              $result2 = mysqli_query($mysqli, $sqlSelect2);

              if ($result2->num_rows > 0) {
                $row2 = $result2->fetch_assoc();
                $tp = $row2['tipoj'];
                if ($tp === "Empresa") {
                  echo "<div class='textfield'>
            <label class='usuario' for='fnl'>Finalidade</label>
            <select id='fnl' name='fnl'>
              <option value='Adocao'>Adoção</option>
            </select>
          </div>";
                } else if ($tp === "ONG") {
                  echo "<div class='textfield'>
                <label class='usuario' for='fnl'>Finalidade</label>
                <select id='fnl' name='fnl'>
                  <option value='Adocao'>Adoção</option>
                  <option value='Padrinho'>Apadrinhamento</option>
                  <option value='Pad_Ado'>Adoção e Apadrinhamento</option>
                </select>
              </div>";
                }
              } else {
                echo "<script>
          Swal.fire(
          'Erro no Cadastro do pet: 3', 
          'Procure um admin para resolver o problema',
          'error'
          ).then(() => {
          window.location.href='../login/login.php';
          });
          </script>";
                exit;
              }
            } else {
              echo "<script>
            Swal.fire(
            'Erro no Cadastro do pet: 1', 
            'Procure um admin para resolver o problema',
            'error'
            ).then(() => {
            window.location.href='../login/login.php';
            });
            </script>";
              exit;
            }
          } else {

            echo "<script>
        Swal.fire(
        'Erro no Cadastro do pet: 2', 
        'Procure um admin para resolver o problema',
        'error'
        ).then(() => {
        window.location.href='../login/login.php';
        });
        </script>";
            exit;
          }
        } else if (isset($_SESSION["admin"])) {
          echo "<script>
                    Swal.fire(
                    'Erro no Cadastro do pet', 
                    'Plataforma errada para o cadastro de pet',
                    'error'
                    ).then(() => {
                      window.history.back();
                    });
                    </script>";
          exit;
        } else {
          echo "<script>
                    Swal.fire(
                    'Erro no Cadastro do pet', 
                    'Nenhuma conta logada',
                    'error'
                    ).then(() => {
                    window.location.href='../login/login.php';
                    });
                    </script>";
          exit;
        }
        ?>


        <div class="textfield">
          <label class="usuario" for="desc">Descrição do pet</label>
          <textarea class="no-resize" type="text" placeholder="Conte-nos detalhes sobre o pet" id="desc" name="desc" required></textarea>
        </div>


      </div>



  </form>
  <script>
    $(document).ready(function() {
      $('#picture__input').change(function() {
        var fileInput = this;
        if (fileInput.files && fileInput.files[0]) {
          var reader = new FileReader();
          reader.onload = function(e) {
            var image = new Image();
            image.src = e.target.result;

            image.onload = function() {
              var canvas = document.createElement('canvas');
              var maxSide = 183; // Tamanho máximo para ambos os lados

              var width = image.width;
              var height = image.height;

              if (width > height) {
                if (width > maxSide) {
                  height *= maxSide / width;
                  width = maxSide;
                }
              } else {
                if (height > maxSide) {
                  width *= maxSide / height;
                  height = maxSide;
                }
              }

              canvas.width = maxSide;
              canvas.height = maxSide;

              var ctx = canvas.getContext('2d');
              ctx.drawImage(image, 0, 0, width, height, 0, 0, maxSide, maxSide);

              var resizedImage = canvas.toDataURL('image/jpeg');

              // Agora você pode enviar a imagem redimensionada (resizedImage) para o servidor
              // Certifique-se de configurar um campo oculto para enviar os dados ao servidor
            };
          };
          reader.readAsDataURL(fileInput.files[0]);
        }
      });
    });
  </script>

</body>

</html>