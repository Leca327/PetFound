<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="servpet.css">
  <link rel="stylesheet" href="../lib/padrao.css">
  <title>Pet Found - Login</title>
</head>

<body>
  <?php session_start();
  ?>
  <form method="post" action="./servinsert.php" enctype="multipart/form-data">

    <div class="main-login">

      <div class="left-login">
        <header>Cadastrar Serviço<svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 512 512">
            <path d="M226.5 92.9c14.3 42.9-.3 86.2-32.6 96.8s-70.1-15.6-84.4-58.5s.3-86.2 32.6-96.8s70.1 15.6 84.4 58.5zM100.4 198.6c18.9 32.4 14.3 70.1-10.2 84.1s-59.7-.9-78.5-33.3S-2.7 179.3 21.8 165.3s59.7 .9 78.5 33.3zM69.2 401.2C121.6 259.9 214.7 224 256 224s134.4 35.9 186.8 177.2c3.6 9.7 5.2 20.1 5.2 30.5v1.6c0 25.8-20.9 46.7-46.7 46.7c-11.5 0-22.9-1.4-34-4.2l-88-22c-15.3-3.8-31.3-3.8-46.6 0l-88 22c-11.1 2.8-22.5 4.2-34 4.2C84.9 480 64 459.1 64 433.3v-1.6c0-10.4 1.6-20.8 5.2-30.5zM421.8 282.7c-24.5-14-29.1-51.7-10.2-84.1s54-47.3 78.5-33.3s29.1 51.7 10.2 84.1s-54 47.3-78.5 33.3zM310.1 189.7c-32.3-10.6-46.9-53.9-32.6-96.8s52.1-69.1 84.4-58.5s46.9 53.9 32.6 96.8s-52.1 69.1-84.4 58.5z" />
          </svg></header>


        <a href="../index.php">
          <h4 class="inicio">◄ Voltar</h4>
        </a>

        <label class="picture" for="picture__input" tabIndex="0">
          <span class="picture__image"></span></label>
        <input type="file" name="picture__input" id="picture__input">
        <script src="./img.js"></script>


      </div>

      <button type="submit" class="btn-singup">Cadastrar Serviço</button>

      <div class="right-login">

        <div class="textfield">
          <label class="usuario" for="nome">Título</label>
          <input type="text" placeholder="Título do serviço" id="nm" name="nm" required />
        </div>

        <div class="textfield">
          <label class="usuario" for="preco">Preço</label>
          <input type="text" id="preco" value="R$" name="preco" required />
        </div>

        <script>
          const precoInput = document.getElementById('preco');

          precoInput.addEventListener('input', (event) => {
            let value = event.target.value.replace(/\D/g, '');
            value = value.replace(/(\d)(\d{2})$/, '$1,$2');
            value = value.replace(/(?=(\d{3})+(\D))\B/g, '.');

            precoInput.value = `${value}`;
          });
        </script>
 
 
        <div class="textfield">
          <label class="usuario" for="ests">Estado</label>
          <select name="ests" id="ests">
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
            $('#ests').change(function() {
              var selectedState = $(this).val();
              $('#citys').empty(); // Limpar opções anteriores

              if (selectedState !== 'allstates') {
                $.getJSON('https://servicodados.ibge.gov.br/api/v1/localidades/estados/' + selectedState + '/municipios', function(data) {
                  $.each(data, function(index, city) {
                    $('#citys').append('<option value="' + city.nome + '">' + city.nome + '</option>');
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
              var stateValue = $('#ests').val();
              var cityValue = $('#citys').val();

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
              $('#citys').empty();
              $('#citys').append('<option value="">Selecione sua cidade</option>');

              if (selectedState !== 'allstates') {
                $.getJSON('https://servicodados.ibge.gov.br/api/v1/localidades/estados/' + selectedState + '/municipios', function(data) {
                  $.each(data, function(index, city) {
                    $('#citys').append('<option value="' + city.nome + '">' + city.nome + '</option>');
                  });
                });
              }
            });
          });
        </script>


        <div class="textfield">
          <label class="usuario" for="end">Cidade</label>
          <select name="citys" id="citys">
            <option value="">Selecione sua cidade</option>
          </select>
        </div>

        
        <div class="textfield">
          <label class="usuario" for="desc">Descrição do pet</label>
          <textarea class="no-resize" type="text" placeholder="Conte-nos detalhes sobre o pet" id="desc" name="desc" required></textarea>
        </div>
        
      </div>

      



  </form>

  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
  <?php
  include('../lib/dbconnect.php');


  if (isset($_SESSION["usuario"])) {
  } else {
    if (isset($_SESSION["admin"])) {
      echo "<script>
                    Swal.fire(
                    'Erro Para Cadastro do pet', 
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
                    'Erro Para Cadastro do pet', 
                    'Nenhuma conta logada',
                    'error'
                    ).then(() => {
                      window.location.href='../login/login.php';
                    });
                    </script>";
      exit;
    }
  }
  ?>




</body>

</html>