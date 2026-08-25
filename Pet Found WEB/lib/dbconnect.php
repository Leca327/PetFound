<?php
$hostname = "localhost";
$username = "root";
$password = "";
$dbname = "petfound";

$mysqli = new mysqli($hostname, $username, $password, $dbname);


if (mysqli_connect_errno()) {
    exit("Erro ao conectar-se ao banco de dados: " . mysqli_connect_error());
}
?>