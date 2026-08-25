<?php
if (isset($_GET["codigo"]) && !empty($_GET["codigo"])) {
    include('../../lib/dbconnect.php');

    $codigo = $_GET["codigo"]; // Obtenha o código do pet do parâmetro da URL
    $apv = $_GET["apv"]; // Obtenha o valor "apv" do parâmetro da URL

    if ($apv === "Ativar") {
        $bloqueio = 0;
    } else {
        $bloqueio = 1;
    }

    // Use prepared statements to update the field
    $query = "UPDATE pet SET bloqueiopet = ? WHERE petcod = ?";

    $stmt = mysqli_prepare($mysqli, $query);

    if ($stmt) {
        mysqli_stmt_bind_param($stmt, "ss", $bloqueio, $codigo);
        $result = mysqli_stmt_execute($stmt);

        if ($result) {
            header("Location: regis.php?mensagem=Atualizado com sucesso".$bloqueio);
            exit();
        } else {
            header("Location: regis.php?mensagem=Ocorreu algum erro na atualização");
            exit();
        }
    } else {
        header("Location: regis.php?mensagem=Ocorreu algum erro na preparação da declaração");
        exit();
    }
} else {
    header("Location: regis.php?mensagem=Selecione um pet para atualizar.");
    exit();
}
?>