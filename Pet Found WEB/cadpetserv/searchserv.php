<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Pet Found - Pesquisar pet</title>
  <link rel="stylesheet" href="../lib/padrao.css">
  <link rel="stylesheet" href="../lib/footer.css">
  <link rel="stylesheet" href="../lib/navbar.css">
  <link rel="stylesheet" href="../lib/responsivonavbar.css">
  <link rel="stylesheet" href="./cardserv.css ">
  <link rel="stylesheet" href="buscaserv.css">
</head>

</head>

<body>
  <?php
  include('../lib/navbar.php');
  include('../lib/dbconnect.php');

  // Contagem total de pets no banco de dados
  $sql_count = "SELECT COUNT(*) as total_pets FROM servico WHERE aprovacaoserv = true and (bloqueioserv=false or bloqueioserv is null)";
  $result_count = $mysqli->query($sql_count);
  $row_count = mysqli_fetch_assoc($result_count);
  $totalPets = $row_count['total_pets'];

  $sqlMaxPrice = "SELECT MAX(preco) as max_price FROM servico";
  $resultMaxPrice = $mysqli->query($sqlMaxPrice);
  $rowMaxPrice = mysqli_fetch_assoc($resultMaxPrice);
  $maxPriceFromDatabase = $rowMaxPrice['max_price'];

  echo '<div class="page-container">
      <div class="banner-container">
        <img src="../assets/servico.png" class="imgbanner">';

  if ($totalPets > 1) {
    echo '<h1 class="msg">Existem ' . $totalPets . ' anuncios para beneficiar seu Pet.<br>
  Aproveite a oportunidade!</h1>';
  } else if ($totalPets == 1) {
    echo '<h1 class="msg">Existe ' . $totalPets . ' anuncio para beneficiar seu Pet.<br>
    Aproveite a oportunidade!</h1>';
  } else {
    echo '<h1 class="msg">Estamos a procura de serviços de Pet!</h1>';
  }

  echo '</div>
      </div>';
  ?>

  <br>

  <h1 class="tittle">Buscar serviços</h1>
  <form method="get" action="">
    <div class="searchline">

      <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


      <script>
        $(document).ready(function() {
          $('#state').change(function() {
            var selectedState = $(this).val();
            $('#citypet').empty();
            $('#citypet').append('<option value="allcities">Todas as cidades</option>');

            if (selectedState !== 'allstates') {
              $.getJSON('https://servicodados.ibge.gov.br/api/v1/localidades/estados/' + selectedState + '/municipios', function(data) {
                $.each(data, function(index, city) {
                  $('#citypet').append('<option value="' + city.nome + '">' + city.nome + '</option>');
                });

                // Pre-select city based on URL parameter if set
                var selectedCity = "<?php echo isset($_GET['citypet']) ? $_GET['citypet'] : ''; ?>";
                if (selectedCity) {
                  $('#citypet').val(selectedCity);
                }
              });
            }
          });

          // Pre-select city based on URL parameter if set
          var selectedCity = "<?php echo isset($_GET['citypet']) ? $_GET['citypet'] : ''; ?>";
          if (selectedCity) {
            $('#citypet').val(selectedCity);
          }
        });
      </script>

      <div class="selectsearch2">

        <select name="state" id="state">
          <option value="allstates">Todos os Estados</option>
          <option value="AC" <?php if (isset($_GET['state']) && $_GET['state'] == "AC") echo 'selected'; ?>>AC</option>
          <option value="AL" <?php if (isset($_GET['state']) && $_GET['state'] == "AL") echo 'selected'; ?>>AL</option>
          <option value="AP" <?php if (isset($_GET['state']) && $_GET['state'] == "AP") echo 'selected'; ?>>AP</option>
          <option value="AM" <?php if (isset($_GET['state']) && $_GET['state'] == "AM") echo 'selected'; ?>>AM</option>
          <option value="BA" <?php if (isset($_GET['state']) && $_GET['state'] == "BA") echo 'selected'; ?>>BA</option>
          <option value="CE" <?php if (isset($_GET['state']) && $_GET['state'] == "CE") echo 'selected'; ?>>CE</option>
          <option value="DF" <?php if (isset($_GET['state']) && $_GET['state'] == "DF") echo 'selected'; ?>>DF</option>
          <option value="ES" <?php if (isset($_GET['state']) && $_GET['state'] == "ES") echo 'selected'; ?>>ES</option>
          <option value="GO" <?php if (isset($_GET['state']) && $_GET['state'] == "GO") echo 'selected'; ?>>GO</option>
          <option value="MA" <?php if (isset($_GET['state']) && $_GET['state'] == "MA") echo 'selected'; ?>>MA</option>
          <option value="MS" <?php if (isset($_GET['state']) && $_GET['state'] == "MS") echo 'selected'; ?>>MS</option>
          <option value="MT" <?php if (isset($_GET['state']) && $_GET['state'] == "MT") echo 'selected'; ?>>MT</option>
          <option value="MG" <?php if (isset($_GET['state']) && $_GET['state'] == "MG") echo 'selected'; ?>>MG</option>
          <option value="PA" <?php if (isset($_GET['state']) && $_GET['state'] == "PA") echo 'selected'; ?>>PA</option>
          <option value="PB" <?php if (isset($_GET['state']) && $_GET['state'] == "PB") echo 'selected'; ?>>PB</option>
          <option value="PR" <?php if (isset($_GET['state']) && $_GET['state'] == "PR") echo 'selected'; ?>>PR</option>
          <option value="PE" <?php if (isset($_GET['state']) && $_GET['state'] == "PE") echo 'selected'; ?>>PE</option>
          <option value="PI" <?php if (isset($_GET['state']) && $_GET['state'] == "PI") echo 'selected'; ?>>PI</option>
          <option value="RJ" <?php if (isset($_GET['state']) && $_GET['state'] == "RJ") echo 'selected'; ?>>RJ</option>
          <option value="RN" <?php if (isset($_GET['state']) && $_GET['state'] == "RN") echo 'selected'; ?>>RN</option>
          <option value="RS" <?php if (isset($_GET['state']) && $_GET['state'] == "RS") echo 'selected'; ?>>RS</option>
          <option value="RO" <?php if (isset($_GET['state']) && $_GET['state'] == "RO") echo 'selected'; ?>>RO</option>
          <option value="RR" <?php if (isset($_GET['state']) && $_GET['state'] == "RR") echo 'selected'; ?>>RR</option>
          <option value="SC" <?php if (isset($_GET['state']) && $_GET['state'] == "SC") echo 'selected'; ?>>SC</option>
          <option value="SP" <?php if (isset($_GET['state']) && $_GET['state'] == "SP") echo 'selected'; ?>>SP</option>
          <option value="SE" <?php if (isset($_GET['state']) && $_GET['state'] == "SE") echo 'selected'; ?>>SE</option>
          <option value="TO" <?php if (isset($_GET['state']) && $_GET['state'] == "TO") echo 'selected'; ?>>TO</option>
        </select>

        <select name="citypet" id="citypet">
          <option value="allcities">Todas as cidades</option>
          <?php
          if (isset($_GET['state']) && $_GET['state'] != "allstates") {
            $selectedState = $_GET['state'];
            $selectedCity = isset($_GET['citypet']) ? $_GET['citypet'] : '';

            $cityData = file_get_contents('https://servicodados.ibge.gov.br/api/v1/localidades/estados/' . $selectedState . '/municipios');
            $cities = json_decode($cityData, true);

            foreach ($cities as $city) {
              $cityName = $city['nome'];
              $selectedAttribute = ($selectedCity === $cityName) ? 'selected' : '';
              echo '<option value="' . $cityName . '" ' . $selectedAttribute . '>' . $cityName . '</option>';
            }
          }
          ?>
        </select>

        <select id="ordenacao" name="ordenacao">
          <option value="mais_novos" <?php if (isset($_GET['ordenacao']) && $_GET['ordenacao'] == "mais_novos") echo 'selected'; ?>>Mais Novos</option>
          <option value="mais_antigos" <?php if (isset($_GET['ordenacao']) && $_GET['ordenacao'] == "mais_antigos") echo 'selected'; ?>>Mais Antigos</option>
        </select>

        <div class="rangeserv" display: flex; flex-direction: column; align-items: center;">
          <label for="priceRange" style="color: white;">Faixa de Preço:</label>
          <div style="display: flex; align-items: center;">
            <span id="priceDisplay" style="color: white;">R$0</span>

            <input type="range" class="custom-range" id="priceRange" name="priceRange" min="0" max="<?= $maxPriceFromDatabase ?>" value="<?= isset($_GET['priceRange']) ? $_GET['priceRange'] : $maxPriceFromDatabase ?>">

            <span id="priceDisplay" style="color: white;">R$</span>

            <output for="priceRange" id="priceValue" style="color: white;"><?= isset($_GET['priceRange']) ? $_GET['priceRange'] : $maxPriceFromDatabase ?></output>

          </div>
        </div>

        <script>
          const priceRange = document.getElementById('priceRange');
          const priceValue = document.getElementById('priceValue');

          priceRange.addEventListener('input', function() {
            priceValue.textContent = this.value;
          });
        </script>

      </div>
      <input type="submit" class="buscapet" id="buscapet" value="Buscar" style="border: none;">
    </div>

  </form>

  <div class="adocaosearch">
    <?php
    include('../lib/dbconnect.php');


    $cardsPerPage = 20;
    $page = isset($_GET['page']) ? $_GET['page'] : 1;

    $startIndex = ($page - 1) * $cardsPerPage;

    $sqlTotal = "SELECT COUNT(*) AS total FROM servico WHERE aprovacaoserv = true and (bloqueioserv=false or bloqueioserv is null);";
    $resultTotal = $mysqli->query($sqlTotal);
    $rowTotal = mysqli_fetch_assoc($resultTotal);
    $totalCards = $rowTotal['total'];

    $stateFilter = "";
    $cityFilter = "";

    if (isset($_GET['citypet']) && $_GET['citypet'] != "allcities") {
      $city = $_GET['citypet'];
      $cityFilter = " AND cidades = '$city'";
    }

    $priceFilter = "";
    if (isset($_GET['priceRange'])) {
      $price = $_GET['priceRange'];
      $priceFilter = " AND preco <= $price";
    }

    if (isset($_GET['state']) && $_GET['state'] != "allstates") {
      $state = $_GET['state'];
      $stateFilter = " AND estados = '$state'";
    }

    $ordenacao = "";
    if (isset($_GET['ordenacao'])) {
      if ($_GET['ordenacao'] == "mais_novos") {
        $ordenacao = " ORDER BY CONCAT(servico.dts, ' ', servico.hrs) DESC";
      } elseif ($_GET['ordenacao'] == "mais_antigos") {
        $ordenacao = " ORDER BY CONCAT(servico.dts, ' ', servico.hrs) ASC";
      }
    }

    $consulta = "SELECT * FROM servico WHERE aprovacaoserv = true and (bloqueioserv=false or bloqueioserv is null) " . $cityFilter . $stateFilter . $priceFilter . $ordenacao;

    $sql = $consulta . " LIMIT $startIndex, $cardsPerPage;";
    $result = $mysqli->query($sql);
    $counter = 0;

    echo '<div class="card-container">';

    while ($pet_data = mysqli_fetch_assoc($result)) {
      $petcodpet = $pet_data['servcod'];
      $sql_imagem = "SELECT * FROM imagem WHERE servicocodserv='$petcodpet';";
      $result_imagem = $mysqli->query($sql_imagem);

      if ($row_imagem = mysqli_fetch_assoc($result_imagem)) {
        $imageData = $row_imagem['img'];
        $imageType = 'image/jpeg';
      } else {
        $imagePath = '../assets/semimg.png';
        $imageData = file_get_contents($imagePath);
        $imageType = mime_content_type($imagePath);
      }

      $precoserv = number_format((float)$pet_data['preco'], 2, ',', '');
      if ($precoserv == "0,00" || $precoserv == "0,0") {
        $precoserv = "Gratuito";
      } else {
        $precoserv = "R$" . $precoserv;
      }

      echo '<div class="cards1">'; // Abre um card
      echo '<h3 style="color: black;">' . $pet_data['nomeserv'] . '</h3>';
      echo '<img class="imgcardpet" src="data:' . $imageType . ';base64,' . base64_encode($imageData) . '" alt="Pet Image">';
      echo '<p class="title"></p>';
      echo '<h3 style="color: black;">' . $pet_data['nomeserv'] . '</h3>';
      echo '<h4 style="color: black;">' . $pet_data['estados'] . ", " . $pet_data['cidades'] . '</h4>';
      echo '<h5 style="color: black;" class="price" data-price="' . $precoserv . '">' . $precoserv . '</h5>';
      echo '<button class="butcardpet"><a href="../anuncio/perfilserv.php?servcod=' . $pet_data['servcod'] . '">Contratar</a></button>';
      echo '</div>'; // Fecha o card

      $counter++;

      if ($counter === 20) {
        break;
      }
    }

    echo '</div>';

    $totalPages = ceil($totalCards / $cardsPerPage);
    echo '<div class="pagination-container" style="display: flex; align-items: center; justify-content: center;">';

    if ($page > 1) {
      echo '<a href="?page=1" style="color: var(--fundoprincipal1); text-decoration: none; font-size: 20px; margin: 0 10px;">&lt;&lt;</a>';
      echo '<a href="?page=' . ($page - 1) . '" style="color: var(--fundoprincipal1); text-decoration: none; font-size: 20px; margin: 0 10px;">&lt;</a>';
    }

    for ($i = 1; $i <= $totalPages; $i++) {
      $style = ($i == $page) ? 'color: var(--fundosecundario2); font-weight: bold;' : 'color: var(--fundoprincipal1);';
      echo '<a href="?page=' . $i . '" style="' . $style . ' text-decoration: none; font-size: 20px; margin: 0 10px;">' . $i . '</a>';
    }

    if ($page < $totalPages) {
      echo '<a href="?page=' . ($page + 1) . '" style="color: var(--fundoprincipal1); text-decoration: none; font-size: 20px; margin: 0 10px;">&gt;</a>';
      echo '<a href="?page=' . $totalPages . '" style="color: var(--fundoprincipal1); text-decoration: none; font-size: 20px; margin: 0 10px;">&gt;&gt;</a>';
    }

    echo '</div>';

    if ($i <= 20) {
      echo "<p class='pag'>Página</p>";
    } else {
      echo "<p class='pag'>Páginas</p>";
    }

    ?>

    <br><br>
  </div>
  <?php include('../lib/footer.php') ?>

  <script>
    document.addEventListener('DOMContentLoaded', function() {
      const buscapet = document.querySelector('.buscapet');

      buscapet.addEventListener('click', function() {
        buscapet.classList.toggle('clicked');
      });
    });
  </script>


</body>

</html>