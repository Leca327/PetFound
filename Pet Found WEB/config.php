<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
  <link rel="stylesheet" href="./config.css">
  <link rel="stylesheet" href="./lib/padrao.css">
  <link rel="stylesheet" href="./lib/footer.css">
  <link rel="stylesheet" href="./lib/navbar.css">
  <link rel="stylesheet" href="./lib/responsivonavbar.css">
</head>

<body>
  <br>
  <?php include('./lib/navbar.php'); ?>

  <div class="container">
    <div class="nvr">
      <br>
      <h3>Configurações</h3>
      <br>
      <ul class="itens">
        <li><button class="button_op" onclick="updateSelected('op1')">Conta</button></li>
        <li><button class="button_op" onclick="updateSelected('op2')">Op2</button></li>
        <li><button class="button_op" onclick="updateSelected('op3')">Op3</button></li>

      </ul>
    </div>
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
        <div class='right'>
        <div id='content_op1' style='display: none;'>
          <h1 class='wlc'>Olá, $user</h1>
          <fieldset id='dd_psl' >
            <legend>Dados Pessoais</legend>
            <form method='post' action='update.php'>

              <label for='nome'>Nome:</label>
              <input type='text' id='nome' name='nome' value='$nome' disabled>

              <label for='snome'>Sobrenome:</label>
              <input type='text' id='snome' name='snome' value='$snome' disabled>


              <label for='nik'>Nick:</label>
              <input type='text' id='nik' name='nik' value='$nick' disabled>

              <label for='email'>Email:</label>
              <input type='email' id='email' name='email' value='$email' disabled><br>

              <label for='cont'>Contato:</label>
              <input type='text' id='cont' name='cont' value='$contato' disabled>

              <label for='name'>Sexo:</label>
              <select name='sexo'disabled>
                <option value='M' " . ($sexo == 'M' ? 'selected' : '') . ">Masculino</option>
                <option value='F' " . ($sexo == 'F' ? 'selected' : '') . ">Feminino</option>
                <option value='O' " . ($sexo == 'O' ? 'selected' : '') . ">Outro</option>
              </select>

              <label for='dt'>Data de Nascimento</label>
              <input type='date' name='dt' id='dt' placeholder='Data de Nascimento' value='$dt'  disabled required><br>


              <label for='End'>CEP:</label>
              <input type='text' id='cep' name='cep' value='$cep' disabled>
"?>

              <label class='usuario' for='uf'>Estado</label>
              <select placeholder='Estado' id='uf' name='uf' required disabled>
                  <option value='AC' <?php if ($uf == 'AC') echo 'selected'; ?>>AC</option>
                  <option value='AL' <?php if ($uf == 'AL') echo 'selected'; ?>>AL</option>
                  <option value='AP' <?php if ($uf == 'AP') echo 'selected'; ?>>AP</option>
                  <option value='AM' <?php if ($uf == 'AM') echo 'selected'; ?>>AM</option>
                  <option value='BA' <?php if ($uf == 'BA') echo 'selected'; ?>>BA</option>
                  <option value='CE' <?php if ($uf == 'CE') echo 'selected'; ?>>CE</option>
                  <option value='DF' <?php if ($uf == 'DF') echo 'selected'; ?>>DF</option>
                  <option value='ES' <?php if ($uf == 'ES') echo 'selected'; ?>>ES</option>
                  <option value='GO' <?php if ($uf == 'GO') echo 'selected'; ?>>GO</option>
                  <option value='MA' <?php if ($uf == 'MA') echo 'selected'; ?>>MA</option>
                  <option value='MS' <?php if ($uf == 'MS') echo 'selected'; ?>>MS</option>
                  <option value='MT' <?php if ($uf == 'MT') echo 'selected'; ?>>MT</option>
                  <option value='MG' <?php if ($uf == 'MG') echo 'selected'; ?>>MG</option>
                  <option value='PA' <?php if ($uf == 'PA') echo 'selected'; ?>>PA</option>
                  <option value='PB' <?php if ($uf == 'PB') echo 'selected'; ?>>PB</option>
                  <option value='PR' <?php if ($uf == 'PR') echo 'selected'; ?>>PR</option>
                  <option value='PE' <?php if ($uf == 'PE') echo 'selected'; ?>>PE</option>
                  <option value='PI' <?php if ($uf == 'PI') echo 'selected'; ?>>PI</option>
                  <option value='RJ' <?php if ($uf == 'RJ') echo 'selected'; ?>>RJ</option>
                  <option value='RN' <?php if ($uf == 'RN') echo 'selected'; ?>>RN</option>
                  <option value='RS' <?php if ($uf == 'RS') echo 'selected'; ?>>RS</option>
                  <option value='RO' <?php if ($uf == 'RO') echo 'selected'; ?>>RO</option>
                  <option value='RR' <?php if ($uf == 'RR') echo 'selected'; ?>>RR</option>
                  <option value='SC' <?php if ($uf == 'SC') echo 'selected'; ?>>SC</option>
                  <option value='SP' <?php if ($uf == 'SP') echo 'selected'; ?>>SP</option>
                  <option value='SE' <?php if ($uf == 'SE') echo 'selected'; ?>>SE</option>
                  <option value='TO' <?php if ($uf == 'TO') echo 'selected'; ?>>TO</option>
              </select>

              
              

              <label for='End'>Cidade:</label>
            <select placeholder='Cidade' id='cid' name="cid" required disabled>
                <option value=''>Selecione sua cidade</option>
            </select>
              
<?php echo"
              <label for='End'>Bairro:</label>
              <input type='text' id='brr' name='brr' value='$brr' disabled><br>

              <label for='End'>Endereço:</label>
              <input type='text' id='End' name='end' value='$end' disabled>

              <label for='End'>Número:</label>
              <input type='text' id='num' name='num' value='$num' disabled>
              
              <label for='End'>Complemento:</label>
              <input type='text' id='cpt' name='cpt' value='$cpt' disabled>
              

              <div id='buttons-container'>
                <button id='alterar-btn' onclick='enableFields(event)'>Alterar</button>
                <button type='submit' id='enviar-btn' style='display: none;'>Enviar</button>
              </div>
            </form>
          </fieldset>
          <a href='formredsenha.php'>Mudar senha</a>
          </div>
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

            echo "<div class='right'>
            <div id='content_op1' style='display: none;'>
          <h1 class='wlc'>Olá, $user</h1>
          <fieldset id='dd_psl' >
            <legend>Dados Pessoais</legend>
            <form method='post' action='updateju.php'>
              <label for='nome'>Nome:</label>
              <input type='text' id='nome' name='nome' value='$nome' disabled>

              <label for='nik'>Nome Fantasia:</label>
              <input type='text' id='nik' name='nik' value='$nick' disabled><br>

              <label for='email'>Email:</label>
              <input type='email' id='email' name='email' value='$email' disabled>

              <label for='cont'>Contato:</label>
              <input type='text' id='cont' name='cont' value='$contato' disabled><br>

              <label for='tij'>Tipo de Pessoa Juridica:</label>
              <select name='tij'disabled>
                <option value='ONG' " . ($tip == 'ONG' ? 'selected' : '') . ">Ong</option>
                <option value='Empresa' " . ($tip == 'Empresa' ? 'selected' : '') . ">Empresa</option>
              </select>

              <label for='ra'>Ramo/Atividade:</label>
              <input type='text' id='ra' name='ra' value='$ra' disabled><br>

              <label for='End'>CEP:</label>
              <input type='text' id='cep' name='cep' value='$cep' disabled>
              <label for='End'>UF:</label>
              <input type='text' id='uf' name='uf' value='$uf' disabled>
              <label for='End'>Cidade:</label>
              <input type='text' id='cid' name='cid' value='$cid' disabled>
              <label for='End'>Bairro:</label>
              <input type='text' id='brr' name='brr' value='$brr' disabled><br>
              <label for='End'>Endereço:</label>
              <input type='text' id='End' name='end' value='$end' disabled>
              <label for='End'>Número:</label>
              <input type='text' id='num' name='num' value='$num' disabled>
              <label for='End'>Complemento:</label>
              <input type='text' id='cpt' name='cpt' value='$cpt' disabled>
              

              <div id='buttons-container'>
                <button id='alterar-btn' onclick='enableFields(event)'>Alterar</button>
                <button type='submit' id='enviar-btn' style='display: none;'>Enviar</button>
              </div>
            </form>
          </fieldset>
          <a href='formredsenha.php'>Mudar senha</a>
          </div>
        </div>
        
      ";
          }
        }
      }
    } else if (isset($_SESSION["admin"])) {
      $user = $_SESSION["admin"];

      // Fetch admin information from the database
      $query = "SELECT * FROM admin WHERE usera = '$user';";
      $result = mysqli_query($mysqli, $query);

      if ($result && mysqli_num_rows($result) > 0) {
        $adminData = mysqli_fetch_assoc($result);
        $nomea = $adminData['nomea'];
        $senhaa = $adminData['senhaa'];
        $admcod = $adminData['admcod'];

        echo "<div class='right'>
          <div id='content_op1' style='display: none;'>
              <h1 class='wlc'>Olá Admin: $user</h1>
              <fieldset id='dd_psl'>
              <a>Para Atualizar Seus Dados Utilize O Software adequado.
                  <legend>Dados Pessoais:</legend>   
                  <br>       
                  <form method='post' action='updateadmin.php'>
                      <label for='nome'>Nome:</label>
                      <input type='text' id='nome' name='nome' value='$nomea' disabled>
  
                      <label for='usera'>Usuario:</label>
                      <input type='text' id='usera' name='usera' value='$user' disabled>
                      <!-- Add more attributes here as needed -->
                  </form>
              </fieldset>
          </div>
          </div>";
      } else {
        echo "<h1>Nenhuma informação encontrada para o admin.</h1>";
      }
    } else {
      echo "<h1>Usuário não autenticado.</h1>";
    }

    ?>

  </div>
  <?php include('./lib/footer.php'); ?>

  <script>
    document.addEventListener('DOMContentLoaded', function() {
      const firstButton = document.querySelector('.button_op');
      const fieldset = document.getElementById('dd_psl');

      firstButton.classList.add('selected');
      fieldset.classList.remove('hidden');

      const buttons = document.querySelectorAll('.button_op');
      buttons.forEach(function(button) {
        button.addEventListener('click', function(event) {
          const selectedButton = document.querySelector('.button_op.selected');
          selectedButton.classList.remove('selected');
          event.target.classList.add('selected');

          if (event.target.textContent === 'Conta') {
            fieldset.classList.remove('hidden');
          } else {
            fieldset.classList.add('hidden');
          }
        });
      });
    });

    function enableFields(event) {
      event.preventDefault();

      const fields = document.querySelectorAll('#dd_psl input:not([type="submit"]), #dd_psl select');

      const alterarBtn = document.getElementById('alterar-btn');
      const enviarBtn = document.getElementById('enviar-btn');


      fields.forEach(function(field) {
        field.removeAttribute('readonly');
        field.removeAttribute('disabled');
      });

      alterarBtn.style.display = 'none';
      enviarBtn.style.display = 'block';
    }
    let $bselecionado = 'op1'; // Initialize the variable with a default option

    function updateSelected(option) {
      $bselecionado = option;
      updateContent();
    }

    function updateContent() {
      const contentOp1 = document.getElementById('content_op1');
      const contentOp2 = document.getElementById('content_op2');
      const contentOp3 = document.getElementById('content_op3');

      contentOp1.style.display = $bselecionado === 'op1' ? 'block' : 'none';
      contentOp2.style.display = $bselecionado === 'op2' ? 'block' : 'none';
      contentOp3.style.display = $bselecionado === 'op3' ? 'block' : 'none';
    }

    // Call updateContent initially to display default content based on the initial value of $bselecionado
    updateContent();
  </script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            $(document).ready(function() {
                // Função para preencher as cidades
                function fillCities() {
                    var estadoSelecionado = $('#uf').val();
                    $('#cid').empty(); // Limpar opções anteriores

                    if (estadoSelecionado !== 'allstates') {
                        // Faça a solicitação à API para obter as cidades
                        $.getJSON('https://servicodados.ibge.gov.br/api/v1/localidades/estados/' + estadoSelecionado + '/municipios', function(data) {
                            $('#cid').append('<option value="">Selecione sua cidade</option>');
                            $.each(data, function(index, cidade) {
                                $('#cid').append('<option value="' + cidade.nome + '">' + cidade.nome + '</option>');
                            });

                            // Defina a cidade selecionada com base em $cid
                            var selectedCity = "<?php echo $cid; ?>";
                            $('#cid').val(selectedCity);
                        });
                    }
                }

                // Chame a função para preencher as cidades no carregamento da página
                fillCities();

                // Chame a função novamente quando o select de estado é alterado
                $('#uf').change(fillCities);
            });
        </script>

<script>
                    function pesquisacep(cep) {
                        cep = cep.replace(/\D/g, '');

                        if (cep.length === 8) {
                            fetch(`https://viacep.com.br/ws/${cep}/json/`)
                                .then(response => response.json())
                                .then(data => {
                                    if (!data.erro) {
                                        document.getElementById('uf').value = data.uf;
                                        document.getElementById('cidade').value = data.localidade;
                                        document.getElementById('bairro').value = data.bairro;
                                        document.getElementById('endereco').value = data.logradouro;
                                        if (data.complemento) {
                                            document.getElementById('numero').value = data.complemento;
                                        } else {
                                            document.getElementById('numero').readOnly = false;
                                        }
                                    }
                                })
                                .catch(error => {
                                    console.log(error);
                                    alert('Erro ao buscar o CEP. Verifique se o CEP é válido e tente novamente.');
                                });

                            document.getElementById('uf').readOnly = true;
                            document.getElementById('cidade').readOnly = true;
                            document.getElementById('bairro').readOnly = true;
                            document.getElementById('endereco').readOnly = true;
                        }
                    }

                    function validateNumberInput(input) {
                        input.value = input.value.replace(/\D/g, '');
                    }
                </script>

</body>

</html>