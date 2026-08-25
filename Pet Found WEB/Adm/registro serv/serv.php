<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro Serviço</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link href="/your-path-to-fontawesome/css/custom-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="../paginaADM.css">
    <link rel="stylesheet" href="../../lib/padrao.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="serv.css">

</head>

</html>
<a target="_blank" href="https://icons8.com/icon/GRqrcka17h5m/pet-care"></a>
<a target="_blank" href="https://icons8.com/icon/N0rCLUNZo0Qt/paw-print"></a>

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
                    </script></div>";
}
?>

<br><br><br>
<?php
$consulta = "SELECT * FROM servico";
$aprovacaoFilter = "";

if (isset($_GET['aprovados'])) {
    if ($_GET['aprovados'] == "aprovado") {

        $consulta .= " WHERE aprovacaoserv = true";
        $aprovacaoFilter = "aprovado";
    } elseif ($_GET['aprovados'] == "reprovado") {

        $consulta .= " WHERE aprovacaoserv = false";
        $aprovacaoFilter = "reprovado";
    } elseif ($_GET['aprovados'] == "nao_analisado") {

        $consulta .= " WHERE aprovacaoserv IS NULL";
        $aprovacaoFilter = "nao_analisado";
    } elseif ($_GET['aprovados'] == "todos") {

        $consulta .= "";
        $aprovacaoFilter = "todos";
    }
}

$ordenacao = "";
if (isset($_GET['ordenacao'])) {
    if ($_GET['ordenacao'] == "mais_novos") {
        $ordenacao = " ORDER BY dts DESC, hrs DESC";
    } elseif ($_GET['ordenacao'] == "mais_antigos") {
        $ordenacao = " ORDER BY dts ASC, hrs ASC";
    }
}
if ($aprovacaoFilter == "todos") {
    $dsta = "";
    if (isset($_GET['dsta'])) {
        if ($_GET['dsta'] == "atv") {
            $dsta = " WHERE (bloqueioserv = false OR bloqueioserv IS NULL)";
        } elseif ($_GET['dsta'] == "dst") {
            $dsta = " WHERE bloqueioserv = true";
        }
    }
} else {
    $dsta = "";
    if (isset($_GET['dsta'])) {
        if ($_GET['dsta'] == "atv") {
            $dsta = " AND (bloqueioserv = false OR bloqueioserv IS NULL)";
        } elseif ($_GET['dsta'] == "dst") {
            $dsta = " AND bloqueioserv = true";
        } elseif ($_GET['dsta'] == "td") {
            $dsta = "";
        }
    }
}

$consulta .= ' ' . $dsta . $ordenacao;

$con = $mysqli->query($consulta) or die($mysqli->error);
if (isset($_GET['aprovados'])) {
    // Consulta para contar o número de registros
    $countConsulta = "SELECT COUNT(*) AS qtd FROM servico";
    if ($_GET['aprovados'] == "aprovado") {
        $countConsulta .= " WHERE aprovacaoserv = true";
    } else if ($_GET['aprovados'] == "nao_analisado") {
        $countConsulta .= " WHERE aprovacaoserv IS NULL";
    } else if ($_GET['aprovados'] == "reprovado") {
        $countConsulta .= " WHERE aprovacaoserv = false";
    }
} else {
    $countConsulta = "SELECT COUNT(*) AS qtd FROM servico";
}


$qtdResult = $mysqli->query($countConsulta);
$qtdRow = $qtdResult->fetch_assoc();
$qtdpet = $qtdRow['qtd'];
?>
<html>

<head>
    <meta charset="utf8">
</head>


<html>

<head>
    <meta charset="utf8">
</head>

<body>
    <a class="sairadm" href="../paginaADM.php">
        ◄ Voltar Para o Painel de Admin </a>

       

    <form method="get" action="">
        <div class="filtro">
            <label for="aprovados">Aprovação:</label>
            <select id="aprovados" name="aprovados">
                <option value="todos" <?php if (!isset($_GET['aprovados']) || $_GET['aprovados'] == "todos") echo 'selected'; ?>>Todos</option>
                <option value="aprovado" <?php if (isset($_GET['aprovados']) && $_GET['aprovados'] == "aprovado") echo 'selected'; ?>>Aprovados</option>
                <option value="reprovado" <?php if (isset($_GET['aprovados']) && $_GET['aprovados'] == "reprovado") echo 'selected'; ?>>Reprovados</option>
                <option value="nao_analisado" <?php if (isset($_GET['aprovados']) && $_GET['aprovados'] == "nao_analisado") echo 'selected'; ?>>Não Analisado</option>
            </select>

            <label for="dsta">Desativados:</label>
            <select id="dsta" name="dsta">
                <option value="td" <?php if (isset($_GET['dsta']) && $_GET['dsta'] == "td") echo 'selected'; ?>>Todos</option>
                <option value="atv" <?php if (isset($_GET['dsta']) && $_GET['dsta'] == "atv") echo 'selected'; ?>>Ativos</option>
                <option value="dst" <?php if (isset($_GET['dsta']) && $_GET['dsta'] == "dst") echo 'selected'; ?>>Desativados</option>
            </select>

            <label for="ordenacao">Ordenação:</label>
            <select id="ordenacao" name="ordenacao">
                <option value="mais_novos" <?php if (isset($_GET['ordenacao']) && $_GET['ordenacao'] == "mais_novos") echo 'selected'; ?>>Mais Novos</option>
                <option value="mais_antigos" <?php if (isset($_GET['ordenacao']) && $_GET['ordenacao'] == "mais_antigos") echo 'selected'; ?>>Mais Antigos</option>
            </select>



            <input class="btnS6" type="submit" value="Filtrar">
        </div>
    </form>
    <form method="POST" action="updateserv.php">

        <table class="tbladm" border="1">

            <h5 class="poss"><?php echo $qtdpet; ?> Serviços Encontrados</h5>
            <tr>
                <td></td>
                <td class="bold-text">Codigo do serviço</td>
                <td>Nome do serviço</td>
                <td>Descrição do serviço</td>
                <td>Preço do serviço</td>
                <td>UF</td>
                <td>Cidade</td>
                <td>Codigo da pessoa</td>
                <td>Aprovado pelo ADM</td>
                <td>Aprovado do Serviço</td>
                <td>Motivo de Reprovar</td>
                <td>Data do Registro</td>
                <td>Hora do Registro</td>
            </tr>


            <?php while ($dado = $con->fetch_array()) {
                $bloq = isset($dado["bloqueioserv"]) ? $dado["bloqueioserv"] : ''; // Provide a default value if the key doesn't exist
                switch ($dado["bloqueioserv"]) {
                    case 1:
                        $bloq = "Ativar";
                        break;
                    case null:
                        $bloq = "Desativar";
                        break;
                    default:
                        $bloq = "Desativar";
                        break;
                }

                $apv = $dado["aprovacaoserv"];
                switch ($dado["aprovacaoserv"]) {
                    case 1:
                        $apv = "Aprovado";
                        $approveDisabled = ''; // Permitir aprovar
                        $rejectDisabled = 'disabled'; // Desabilitar reprovar
                        $noavaliarDisabled = 'disabled'; // Desabilitar não avaliar
                        break;
                    case null:
                        $apv = "Não Analisado";
                        $approveDisabled = 'disabled'; // Desabilitar aprovar
                        $rejectDisabled = ''; // Permitir reprovar
                        $noavaliarDisabled = ''; // Permitir não avaliar
                        break;
                    default:
                        $apv = "Reprovado";
                        $approveDisabled = 'disabled'; // Desabilitar aprovar
                        $rejectDisabled = 'disabled'; // Desabilitar reprovar
                        $noavaliarDisabled = ''; // Permitir não avaliar
                        break;
                }

            ?>


                <tr>
                    <td><input type="checkbox" name="selected_pets" value="<?php echo $dado["servcod"]; ?>" id="pet_<?php echo $dado["servcod"]; ?>" onclick="checkButtonStatus()"><input type="hidden" name="pet_names" value="<?php echo $dado["nomeserv"]; ?>"><input type="hidden" name="pet_aprovacao_<?php echo $dado["servcod"]; ?>" value="<?php echo $apv; ?>"></td>
                    <td><?php echo $dado["servcod"]; ?></td>
                    <td><?php echo $dado["nomeserv"]; ?></td>
                    <td><?php echo $dado["descserv"]; ?></td>
                    <td><?php echo "R$" . number_format($dado["preco"], 2, ',', '.'); ?></td>
                    <td><?php echo $dado["estados"]; ?></td>
                    <td><?php echo $dado["cidades"]; ?></td>
                    <td><?php echo $dado["pessoa_codp"]; ?></td>
                    <td><?php echo $dado["admin_codadmn"]; ?></td>
                    <td><?php echo $apv; ?></td>
                    <td><?php echo $dado["motivorepserv"]; ?></td>
                    <td><?php echo date("d/m/y", strtotime($dado["dts"])); ?></td>
                    <td><?php echo $dado["hrs"]; ?></td>


                    <td>
                        <a class="editsadm" href="../../anuncio/perfilserv.php?servcod=<?php echo $dado["servcod"]; ?>">Vizualizar</a>
                        <a class="delsadm" href="excluir.php?codigo=<?php echo $dado["servcod"]; ?>&apv=<?php echo $bloq; ?>"><?php echo $bloq; ?></a>
                    </td>
                </tr>
            <?php } ?>

        </table>

        <div class="btns">
    <button class="btnS1" type='submit' name='approve_button' disabled="disabled">Aprovar Serviço</button>
    <button class="btnS2" type='button' id='reject_button' name='reje_button' disabled="disabled" onclick='toggleReprovarTextarea()'>Reprovar Serviço</button>
    <button class="btnS3" type='submit' name='noavaliar_button' disabled="disabled">Não Avaliar</button>
        </div>
        <div class="btns2">
    <textarea id='motivo_reprovacao' name='motivo_reprovacao' style='display: none; width:30vh; height:10vh; resize:none;' placeholder='Digite o motivo da reprovação'></textarea>
    <button class="btnS4" type='submit' id='enviar_reprovacao' name='enviar_reprovacao' style='display: none;' onclick='enviarMotivoReprovacao()'>Enviar</button>
        </div>

    </form>

</body>

</html>
<script>
    function checkButtonStatus() {
        // Obtém todos os checkboxes com o nome "selected_pets"
        var checkboxes = document.querySelectorAll('input[name="selected_pets"]');
        var approveButton = document.querySelector('button[name="approve_button"]');
        var rejectButton = document.querySelector('button[name="reje_button"]');
        var noavaliarButton = document.querySelector('button[name="noavaliar_button"]');

        // Variável para rastrear o valor de $apv da linha correspondente à checkbox selecionada
        var selectedApv = null;

        // Verifica quantas checkboxes estão selecionadas
        var selectedCount = 0;

        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                selectedCount++;
                // Obtém o valor de $apv do campo oculto associado à checkbox selecionada
                var petId = checkboxes[i].value;
                var hiddenInput = document.querySelector('input[name="pet_aprovacao_' + petId + '"]');
                if (hiddenInput) {
                    selectedApv = hiddenInput.value;
                }
            }
        }
        var motivoTextarea = document.getElementById('motivo_reprovacao');
        var enviarBotao = document.getElementById('enviar_reprovacao');
        motivoTextarea.style.display = 'none';
        enviarBotao.style.display = 'none';
        // Habilita ou desabilita os botões com base no valor de $apv
        if (selectedCount === 1) {
            if (selectedApv === "Aprovado") {
                approveButton.setAttribute('disabled', 'disabled');
                rejectButton.removeAttribute('disabled');
                noavaliarButton.removeAttribute('disabled');

            } else if (selectedApv === "Reprovado") {
                approveButton.removeAttribute('disabled');
                rejectButton.setAttribute('disabled', 'disabled');
                noavaliarButton.removeAttribute('disabled');

            } else if (selectedApv === "Não Analisado") {
                approveButton.removeAttribute('disabled');
                rejectButton.removeAttribute('disabled');
                noavaliarButton.setAttribute('disabled', 'disabled');

            } else {
                // Se o valor de $apv for diferente dessas opções, desabilita todos os botões
                approveButton.setAttribute('disabled', 'disabled');
                rejectButton.setAttribute('disabled', 'disabled');
                noavaliarButton.setAttribute('disabled', 'disabled');

            }
        } else {
            // Se mais de uma checkbox estiver selecionada, desabilita todos os botões
            approveButton.setAttribute('disabled', 'disabled');
            rejectButton.setAttribute('disabled', 'disabled');
            noavaliarButton.setAttribute('disabled', 'disabled');
        }
    }

    function toggleReprovarTextarea() {
        var motivoTextarea = document.getElementById('motivo_reprovacao');
        var enviarBotao = document.getElementById('enviar_reprovacao');

        // Verifica se há alguma checkbox marcada com o nome "selected_pets"
        var checkboxes = document.querySelectorAll('input[name="selected_pets"]');
        var peloMenosUmaSelecionada = Array.from(checkboxes).some(function(checkbox) {
            return checkbox.checked;
        });

        if (peloMenosUmaSelecionada) {
            motivoTextarea.style.display = motivoTextarea.style.display === 'none' ? '' : 'none';
            enviarBotao.style.display = motivoTextarea.style.display === 'none' ? 'none' : '';

        } else {
            alert('Por favor, selecione pelo menos uma opção.');
        }
    }
</script>