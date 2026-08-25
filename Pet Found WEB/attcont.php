<body bgcolor="#ff6600">

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>
<?php
include('./lib/dbconnect.php');
if (isset($_POST['attligpet'])) {
    $codcont = $_POST['codcont'];
    $lig = $_POST['confirmacao'];

    $sql3 = "SELECT * FROM contatopet WHERE codcontp='$codcont'";
    $result4 = mysqli_query($mysqli, $sql3);

    if ($result4) {
        $row = mysqli_fetch_assoc($result4);

        if ($row) {
            $tpcont = $row['tipocont'];
            $tutornv = $row['pfcodp'];
            $cnf2 = $row['confirmpe'];
            $codcont = $row['codcontp'];
            $petcod = $row['petcodpet'];
            if ($lig === "nao_ligou") {

                $lig2 = 0;
            } else if ($lig === "ligou") {

                $lig2 = 1;
            } else if ($lig === "esp") {

                $lig2 = null;
            }

            if ($lig2 == true && $cnf2 == true) {
                $sql = "UPDATE contatopet
                SET ligoupet = true, confirmpet = true, confirmpe = true,arquivarpet=null,adotou=null,apadrinhou=null
                WHERE codcontp = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    $query = "SELECT * FROM pet WHERE petcod = '" . $petcod . "'";
                    $result = mysqli_query($mysqli, $query);
                    if ($row = mysqli_fetch_assoc($result)) {
                        $temporario = $row['pessoacodp'];
                        $nmpet = $row['nomepet'];
                    }

                    $hora_atual = date("Hi");
                    $data_atual = date("Ymd");

                    $cortecod = substr($tutornv, 0, 5);
                    $notifications_name = "Atualização de $nmpet";
                    $mensagem = "O Anunciante indicou que você ligou com sua confirmação. Para mais instruções acesso o painel.";

                    $cod = "NOTCONTQPET" . $cortecod . $hora_atual . $data_atual;
                    $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                    $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                    $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatopet,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$tutornv','$codcont','$dtatual','$hratual')";
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            } else if ($lig2 === null && $cnf2 == true) {
                $sql = "UPDATE contatopet
                SET ligoupet = null, confirmpet = null,arquivarpet=null,adotou=null,apadrinhou=null
                WHERE codcontp = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            } else if ($lig2 == false && $cnf2 == true) {
                $sql = "UPDATE contatopet
                SET ligoupet = false, confirmpet = false , arquivarpet=true,adotou=false,apadrinhou=false
                WHERE codcontp = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    $query = "SELECT * FROM pet WHERE petcod = '" . $petcod . "'";
                    $result = mysqli_query($mysqli, $query);
                    if ($row = mysqli_fetch_assoc($result)) {
                        $temporario = $row['pessoacodp'];
                        $nmpet = $row['nomepet'];
                    }

                    $hora_atual = date("Hi");
                    $data_atual = date("Ymd");

                    $cortecod = substr($tutornv, 0, 5);
                    $notifications_name = "Atualização de $nmpet";
                    $mensagem = "Seu contato foi encerrado. Foi indicado que você não ligou, para mais instruções acesso o painel.";

                    $cod = "NOTCONTNQPET" . $cortecod . $hora_atual . $data_atual;
                    $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                    $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                    $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatopet,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$tutornv','$codcont','$dtatual','$hratual')";
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            } else {
                if ($lig2 === null) {
                    $sql = "UPDATE contatopet
                SET ligoupet = null, confirmpet = null,arquivarpet=null,adotou=null,apadrinhou=null
                WHERE codcontp = '$codcont'";
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else  if ($lig2 == false) {
                    $sql = "UPDATE contatopet
                SET ligoupet = false, confirmpet = $lig2,arquivarpet=true,adotou=false,apadrinhou=false
                WHERE codcontp = '$codcont'";
                    if (mysqli_query($mysqli, $sql)) {
                        $query = "SELECT * FROM pet WHERE petcod = '" . $petcod . "'";
                        $result = mysqli_query($mysqli, $query);
                        if ($row = mysqli_fetch_assoc($result)) {
                            $temporario = $row['pessoacodp'];
                            $nmpet = $row['nomepet'];
                        }

                        $hora_atual = date("Hi");
                        $data_atual = date("Ymd");

                        $cortecod = substr($tutornv, 0, 5);
                        $notifications_name = "Atualização de $nmpet";
                        $mensagem = "Seu contato foi encerrado. Foi indicado que você não ligou, para mais instruções acesso o painel.";

                        $cod = "NOTCONTNQPET" . $cortecod . $hora_atual . $data_atual;
                        $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                        $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                        $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatopet,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$tutornv','$codcont','$dtatual','$hratual')";
                        if (mysqli_query($mysqli, $sql)) {
                            echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                        } else {
                            echo "Erro: " . mysqli_error($mysqli);
                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                        }
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else  if ($lig2 == true) {
                    $sql = "UPDATE contatopet
                SET ligoupet = null, confirmpet = $lig2,arquivarpet=null,adotou=null,apadrinhou=null
                WHERE codcontp = '$codcont'";
                    if (mysqli_query($mysqli, $sql)) {
                        $query = "SELECT * FROM pet WHERE petcod = '" . $petcod . "'";
                        $result = mysqli_query($mysqli, $query);
                        if ($row = mysqli_fetch_assoc($result)) {
                            $temporario = $row['pessoacodp'];
                            $nmpet = $row['nomepet'];
                        }

                        $hora_atual = date("Hi");
                        $data_atual = date("Ymd");

                        $cortecod = substr($tutornv, 0, 5);
                        $notifications_name = "Atualização de $nmpet";
                        $mensagem = "O anunciante indicou que você ligou. Para mais instruções acesso o painel.";

                        $cod = "NOTCONTQPET" . $cortecod . $hora_atual . $data_atual;
                        $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                        $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                        $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatopet,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$tutornv','$codcont','$dtatual','$hratual')";
                        if (mysqli_query($mysqli, $sql)) {
                            echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                        } else {
                            echo "Erro: " . mysqli_error($mysqli);
                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                        }
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                }
            }
        } else {
            echo "Nenhum registro encontrado.";
        }
    }
} elseif (isset($_POST['attligpet2'])) {

    $codcont = $_POST['codcont'];
    $lig = $_POST['confirmacao'];

    $sql3 = "SELECT * FROM contatopet WHERE codcontp='$codcont'";
    $result4 = mysqli_query($mysqli, $sql3);

    if ($result4) {
        $row = mysqli_fetch_assoc($result4);

        if ($row) {
            $tpcont = $row['tipocont'];
            $tutornv = $row['pfcodp'];
            $cnf2 = $row['confirmpet'];
            $codcont = $row['codcontp'];
            $petcod = $row['petcodpet'];
            if ($lig === "nao_ligou") {
                $lig2 = 0;
            } else if ($lig === "ligou") {
                $lig2 = 1;
            } else if ($lig === "esp") {
                $lig2 = null;
            }
            if ($lig2 == true && $cnf2 == true) {
                $sql = "UPDATE contatopet
                SET ligoupet = true, confirmpet = true, confirmpe = true,adotou=null,apadrinhou=null,arquivarpet=null
                WHERE codcontp = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    $query = "SELECT * FROM pet WHERE petcod = '" . $petcod . "'";
                    $result = mysqli_query($mysqli, $query);
                    if ($row = mysqli_fetch_assoc($result)) {
                        $temporario = $row['pessoacodp'];
                        $nmpet = $row['nomepet'];
                    }

                    $hora_atual = date("Hi");
                    $data_atual = date("Ymd");

                    $cortecod = substr($tutornv, 0, 5);
                    $notifications_name = "Atualização de $nmpet";
                    $mensagem = "O contatante indicou que ligou para você. Gerencie o contato pelo painel.";

                    $cod = "NOTCONTPETQ" . $cortecod . $hora_atual . $data_atual;
                    $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                    $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                    $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatopet,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$temporario','$codcont','$dtatual','$hratual')";
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            } else if ($lig2 === null && $cnf2 == true) {
                $sql = "UPDATE contatopet
                SET ligoupet = null, confirmpe = null,adotou=null,apadrinhou=null,arquivarpet=null
                WHERE codcontp = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            } else if ($lig2 == false && $cnf2 == true) {
                $sql = "UPDATE contatopet
                SET ligoupet = false, confirmpe = false,adotou=false,apadrinhou=false,arquivarpet=null
                WHERE codcontp = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    $query = "SELECT * FROM pet WHERE petcod = '" . $petcod . "'";
                    $result = mysqli_query($mysqli, $query);
                    if ($row = mysqli_fetch_assoc($result)) {
                        $temporario = $row['pessoacodp'];
                        $nmpet = $row['nomepet'];
                    }

                    $hora_atual = date("Hi");
                    $data_atual = date("Ymd");

                    $cortecod = substr($tutornv, 0, 5);
                    $notifications_name = "Atualização de $nmpet";
                    $mensagem = "O contatante indicou que não vai ligar para você e o contato foi encerrado. Gerencie o contato pelo painel para arquivar.";

                    $cod = "NOTCONTPETNQ" . $cortecod . $hora_atual . $data_atual;
                    $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                    $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                    $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatopet,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$temporario','$codcont','$dtatual','$hratual')";
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            } else {
               
                if ($lig2 === null) {
                    $sql = "UPDATE contatopet
                SET ligoupet = null, confirmpe = null,adotou=null,apadrinhou=null,arquivarpet=null
                WHERE codcontp = '$codcont'";
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else if ($lig2 == false) {
                    $sql = "UPDATE contatopet
                SET ligoupet = false, confirmpe = $lig2,adotou=false,apadrinhou=false,arquivarpet=null
                WHERE codcontp = '$codcont'";
                    if (mysqli_query($mysqli, $sql)) {
                        $query = "SELECT * FROM pet WHERE petcod = '" . $petcod . "'";
                        $result = mysqli_query($mysqli, $query);
                        if ($row = mysqli_fetch_assoc($result)) {
                            $temporario = $row['pessoacodp'];
                            $nmpet = $row['nomepet'];
                        }

                        $hora_atual = date("Hi");
                        $data_atual = date("Ymd");

                        $cortecod = substr($tutornv, 0, 5);
                        $notifications_name = "Atualização de $nmpet";
                        $mensagem = "O contatante indicou que não vai ligar para você e o contato foi encerrado. Gerencie o contato pelo painel para arquivar.";

                        $cod = "NOTCONTPETNQ" . $cortecod . $hora_atual . $data_atual;
                        $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                        $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                        $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatopet,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$temporario','$codcont','$dtatual','$hratual')";
                        if (mysqli_query($mysqli, $sql)) {
                            echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                        } else {
                            echo "Erro: " . mysqli_error($mysqli);
                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                        }
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else if ($lig2 == true) {
                    $sql = "UPDATE contatopet
                SET ligoupet = null, confirmpe = $lig2,adotou=null,apadrinhou=null,arquivarpet=null
                WHERE codcontp = '$codcont'";
                    if (mysqli_query($mysqli, $sql)) {
                        $query = "SELECT * FROM pet WHERE petcod = '" . $petcod . "'";
                        $result = mysqli_query($mysqli, $query);
                        if ($row = mysqli_fetch_assoc($result)) {
                            $temporario = $row['pessoacodp'];
                            $nmpet = $row['nomepet'];
                        }

                        $hora_atual = date("Hi");
                        $data_atual = date("Ymd");

                        $cortecod = substr($tutornv, 0, 5);
                        $notifications_name = "Atualização de $nmpet";
                        $mensagem = "O contatante indicou que ligou para você. Gerencie o contato pelo painel para arquivar.";

                        $cod = "NOTCONTPETQ" . $cortecod . $hora_atual . $data_atual;
                        $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                        $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                        $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatopet,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$temporario','$codcont','$dtatual','$hratual')";
                        if (mysqli_query($mysqli, $sql)) {
                            echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                        } else {
                            echo "Erro: " . mysqli_error($mysqli);
                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                        }
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                }
            }
        } else {
            echo "Nenhum registro encontrado.";
        }
    }
} else if (isset($_POST['attencpet'])) {
    $codcont = $_POST['codcont'];
    $lig = $_POST['confirmacao'];
    $dataAtual = date('Y-m-d');
    $horaAtual = date('H:i:s');

    $sql3 = "SELECT * FROM contatopet WHERE codcontp='$codcont'";

    $result4 = mysqli_query($mysqli, $sql3);

    if ($result4) {
        $row = mysqli_fetch_assoc($result4);

        if ($row) {
            $petcod = $row['petcodpet'];
        }
    }

    $codcont2 = null;
    $sql4 = "SELECT * FROM contatopet WHERE adotou=true and petcodpet='$petcod'";

    $result = mysqli_query($mysqli, $sql4);

    if ($result) {
        $row = mysqli_fetch_assoc($result);

        if ($row) {
            $codcont2 = $row['codcontp'];
        }
    }

    if ($codcont === $codcont2) {
        $sql3 = "SELECT * FROM contatopet WHERE codcontp<>'$codcont2' and petcodpet='$petcod' and adotou=true";
    } else {
        $sql3 = "SELECT * FROM contatopet WHERE petcodpet='$petcod' and adotou=true";
    }

    $result4 = mysqli_query($mysqli, $sql3);

    if ($result4->num_rows > 0) {
        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Pet não pode ser atualizado pois já possui um dono',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
    } else {

        $sql3 = "SELECT * FROM contatopet WHERE codcontp='$codcont'";
        $result4 = mysqli_query($mysqli, $sql3);

        if ($result4) {
            $row = mysqli_fetch_assoc($result4);

            if ($row) {
                $tpcont = $row['tipocont'];
                $tutornv = $row['pfcodp'];
                $codcont = $row['codcontp'];
                $codpet = $row['petcodpet'];
                if ($lig === "adt") {
                    $sql = "UPDATE contatopet
                SET adotou = true,apadrinhou=false, dtfinalcp='$dataAtual' ,hrfinalcp='$horaAtual',arquivarpet=true
                WHERE codcontp = '$codcont'";

                    if (mysqli_query($mysqli, $sql)) {
                        $sql = "UPDATE pet
                SET bloqueiopet=true
                WHERE petcod = '$codpet'";
                        if (mysqli_query($mysqli, $sql)) {

                            $query = "SELECT * FROM pet WHERE petcod = '" . $petcod . "'";
                            $result = mysqli_query($mysqli, $query);
                            if ($row = mysqli_fetch_assoc($result)) {
                                $temporario = $row['pessoacodp'];
                                $nmpet = $row['nomepet'];
                            }

                            $hora_atual = date("Hi");
                            $data_atual = date("Ymd");

                            $cortecod = substr($tutornv, 0, 5);
                            $notifications_name = "Atualização de $nmpet";
                            $mensagem = "Parabéns! Você adotou um pet, cuide bem dele e dê todo amor do mundo. PetFound estará aqui caso precise.";

                            $cod = "NOTCONTADTPET" . $cortecod . $hora_atual . $data_atual;
                            $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                            $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                            $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatopet,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$tutornv','$codcont','$dtatual','$hratual')";

                            // Executando a instrução SQL para inserir o pet na tabela "pet"
                            if (mysqli_query($mysqli, $sql)) {

                                $sql = "SELECT * FROM pet WHERE petcod = '$codpet'";
                                $result = $mysqli->query($sql);

                                if ($result->num_rows > 0) {

                                    // Loop para percorrer os resultados
                                    while ($row = $result->fetch_assoc()) {

                                        // Exemplo de atualização
                                        $updateSql = "UPDATE contatopet
                                SET arquivarpet=true
                                WHERE petcodpet = '$codpet'";
                                        if ($mysqli->query($updateSql) === TRUE) {
                                            echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                                        } else {
                                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                                        }
                                    }
                                } else {
                                    echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                                }
                            } else {
                                echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                            }
                        } else {
                            echo "Erro: " . mysqli_error($mysqli);
                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                        }
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else if ($lig === "apd") {
                    $sql = "UPDATE contatopet
                SET adotou = false,apadrinhou=true,dtfinalcp='$dataAtual',hrfinalcp='$horaAtual',arquivarpet=null
                WHERE codcontp = '$codcont'";

                    if (mysqli_query($mysqli, $sql)) {
                        $sql = "UPDATE pet
                SET bloqueiopet=null
                WHERE petcod = '$codpet'";
                        if (mysqli_query($mysqli, $sql)) {

                            $query = "SELECT * FROM pet WHERE petcod = '" . $petcod . "'";
                            $result = mysqli_query($mysqli, $query);
                            if ($row = mysqli_fetch_assoc($result)) {
                                $temporario = $row['pessoacodp'];
                                $nmpet = $row['nomepet'];
                            }

                            $hora_atual = date("Hi");
                            $data_atual = date("Ymd");

                            $cortecod = substr($tutornv, 0, 5);
                            $notifications_name = "Atualização de $nmpet";
                            $mensagem = "Parabéns! Você Apadrinhou um pet, toda sua ajuda é muito importante para ele.";

                            $cod = "NOTCONTAPDPET" . $cortecod . $hora_atual . $data_atual;
                            $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                            $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                            $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatopet,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$tutornv','$codcont','$dtatual','$hratual')";

                            // Executando a instrução SQL para inserir o pet na tabela "pet"
                            if (mysqli_query($mysqli, $sql)) {
                                $sql = "SELECT * FROM pet WHERE petcod = '$codpet'";
                                $result = $mysqli->query($sql);

                                if ($result->num_rows > 0) {

                                    // Loop para percorrer os resultados
                                    while ($row = $result->fetch_assoc()) {

                                        // Exemplo de atualização
                                        $updateSql = "UPDATE contatopet
                                SET arquivarpet=null
                                WHERE petcodpet = '$codpet'";
                                        if ($mysqli->query($updateSql) === TRUE) {
                                            echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                                        } else {
                                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                                        }
                                    }
                                } else {
                                    echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                                }
                            } else {
                                echo "Erro: " . mysqli_error($mysqli);
                                echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                            }
                        } else {
                            echo "Erro: " . mysqli_error($mysqli);
                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                        }
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else if ($lig === "nquis") {
                    $sql = "UPDATE contatopet
                SET adotou = false,apadrinhou=false,dtfinalcp=null,hrfinalcp=null,arquivarpet=null
                WHERE codcontp = '$codcont'";

                    if (mysqli_query($mysqli, $sql)) {
                        $sql = "UPDATE pet
                SET bloqueiopet=null
                WHERE petcod = '$codpet'";
                        if (mysqli_query($mysqli, $sql)) {
                            $query = "SELECT * FROM pet WHERE petcod = '" . $petcod . "'";
                            $result = mysqli_query($mysqli, $query);
                            if ($row = mysqli_fetch_assoc($result)) {
                                $temporario = $row['pessoacodp'];
                                $nmpet = $row['nomepet'];
                            }

                            $hora_atual = date("Hi");
                            $data_atual = date("Ymd");

                            $cortecod = substr($tutornv, 0, 5);
                            $notifications_name = "Atualização de $nmpet";
                            $mensagem = "Seu contato foi encerrado. É uma pena você não querer esse pet, esperamos que encontre um que goste.";

                            $cod = "NOTCONTNQPET" . $cortecod . $hora_atual . $data_atual;
                            $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                            $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                            $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatopet,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$tutornv','$codcont','$dtatual','$hratual')";

                            // Executando a instrução SQL para inserir o pet na tabela "pet"
                            if (mysqli_query($mysqli, $sql)) {
                                $sql = "SELECT * FROM pet WHERE petcod = '$codpet'";
                                $result = $mysqli->query($sql);

                                if ($result->num_rows > 0) {

                                    // Loop para percorrer os resultados
                                    while ($row = $result->fetch_assoc()) {

                                        // Exemplo de atualização
                                        $updateSql = "UPDATE contatopet
                                SET arquivarpet=null
                                WHERE petcodpet = '$codpet'";
                                        if ($mysqli->query($updateSql) === TRUE) {
                                            echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                                        } else {
                                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                                        }
                                    }
                                } else {
                                    echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                                }
                            } else {
                                echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                            }
                        } else {
                            echo "Erro: " . mysqli_error($mysqli);
                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                        }
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else if ($lig === "esp") {
                    $sql = "UPDATE contatopet
                SET adotou =null,apadrinhou=null,dtfinalcp=null,hrfinalcp=null,arquivarpet=null
                WHERE codcontp = '$codcont'";

                    if (mysqli_query($mysqli, $sql)) {
                        $sql = "UPDATE pet
                SET bloqueiopet=null
                WHERE petcod = '$codpet'";
                        if (mysqli_query($mysqli, $sql)) {

                            $sql = "SELECT * FROM pet WHERE petcod = '$codpet'";
                            $result = $mysqli->query($sql);

                            if ($result->num_rows > 0) {

                                // Loop para percorrer os resultados
                                while ($row = $result->fetch_assoc()) {

                                    // Exemplo de atualização
                                    $updateSql = "UPDATE contatopet
                                SET arquivarpet=null
                                WHERE petcodpet = '$codpet'";
                                    if ($mysqli->query($updateSql) === TRUE) {
                                        echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                                    } else {
                                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                                    }
                                }
                            } else {
                                echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                            }
                        } else {
                            echo "Erro: " . mysqli_error($mysqli);
                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                        }
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                }
            } else {
                echo "Nenhum registro encontrado.";
            }
        }
    }
} else if (isset($_POST['dstvpet'])) {
    $codcont = $_POST['codcont'];
    $atv = $_POST['tpatvpet'];
    echo "bb: " . $atv;
    if ($atv === null || $atv == false) {

        $atv = 1;
        echo "entrei1: " . $atv;
    } else {

        $atv = 0;
        echo "entrei2: " . $atv;
    }
    echo "aa: " . $atv;
    $updateSql = "UPDATE contatopet
    SET arquivarpet=$atv
    WHERE codcontp = '$codcont'";

    if ($mysqli->query($updateSql) === TRUE) {
        echo "<script>
Swal.fire(
'Atualização bem sucessedida',
'Contato atualizado',
'success'
).then(() => {
window.history.back();
});
</script>";
    } else {
        echo "Erro: " . mysqli_error($mysqli);
        echo "<script>
Swal.fire(
'Erro na Atualização',
'Alguma coisa deu errado ao atualizar',
'error'
).then(() => {
window.history.back();
});
</script>";
    }
} else 
if (isset($_POST['attligserv'])) {
    $codcont = $_POST['codcont'];
    $lig = $_POST['confirmacao'];

    $sql3 = "SELECT * FROM contatoserv WHERE codconts='$codcont'";
    $result4 = mysqli_query($mysqli, $sql3);

    if ($result4) {
        $row = mysqli_fetch_assoc($result4);

        if ($row) {
            $tutornv = $row['pcodp'];
            $cnf2 = $row['confirm_pe'];
            $codcont = $row['codconts'];
            $codpet = $row['scodserv'];

            if ($lig === "nao_ligou") {

                $lig2 = 0;
            } else if ($lig === "ligou") {

                $lig2 = 1;
            } else if ($lig === "esp") {

                $lig2 = null;
            }

            if ($lig2 == true && $cnf2 == true) {
                $sql = "UPDATE contatoserv
                SET ligouserv = true, confirmserv = true, confirm_pe = true,arquivarserv=null,contratou=null,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    $query = "SELECT * FROM servico WHERE servcod = '" . $codpet . "'";
                    $result = mysqli_query($mysqli, $query);
                    if ($row = mysqli_fetch_assoc($result)) {
                        $temporario = $row['pessoa_codp'];
                        $nmpet = $row['nomeserv'];
                    }

                    $hora_atual = date("Hi");
                    $data_atual = date("Ymd");

                    $cortecod = substr($temporario, 0, 5);
                    $notifications_name = "Contato para $nmpet";
                    $mensagem = "O Anunciante indicou que você ligou com sua confirmação. Para mais instruções acesso o painel.";

                    $cod = "NOTCONTQSCV" . $cortecod . $hora_atual . $data_atual;
                    $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                    $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                    $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatoserv,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$tutornv','$codcont','$dtatual','$hratual')";

                    // Executando a instrução SQL para inserir o pet na tabela "pet"
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
                    Swal.fire(
                        'Atualização bem sucessedida',
                        'Contato atualizado',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                    } else {
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            } else if ($lig2 === null && $cnf2 == true) {
                $sql = "UPDATE contatoserv
                SET ligouserv = null, confirmserv = null,arquivarserv=null,contratou=null,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            } else if ($lig2 == false && $cnf2 == true) {
                $sql = "UPDATE contatoserv
                SET ligouserv = false, confirmserv = false,arquivarserv=true,contratou=false,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    $query = "SELECT * FROM servico WHERE servcod = '" . $codpet . "'";
                    $result = mysqli_query($mysqli, $query);
                    if ($row = mysqli_fetch_assoc($result)) {
                        $temporario = $row['pessoa_codp'];
                        $nmpet = $row['nomeserv'];
                    }

                    $hora_atual = date("Hi");
                    $data_atual = date("Ymd");

                    $cortecod = substr($temporario, 0, 5);
                    $notifications_name = "Contato para $nmpet";
                    $mensagem = "Seu contato foi encerrado. Foi indicado que você não ligou. Para mais informações entre no painel.";

                    $cod = "NOTCONTNQSCV" . $cortecod . $hora_atual . $data_atual;
                    $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                    $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                    $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatoserv,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$tutornv','$codcont','$dtatual','$hratual')";

                    // Executando a instrução SQL para inserir o pet na tabela "pet"
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
                    Swal.fire(
                        'Atualização bem sucessedida',
                        'Contato atualizado',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                    } else {
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            } else {
                if ($lig2 === null) {
                    $sql = "UPDATE contatoserv
                SET ligouserv = null, confirmserv = null,arquivarserv=null,contratou=null,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else if ($lig2 == false) {
                    $sql = "UPDATE contatoserv
                SET ligouserv = false, confirmserv = $lig2,arquivarserv=true,contratou=false,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";
                    if (mysqli_query($mysqli, $sql)) {
                        $query = "SELECT * FROM servico WHERE servcod = '" . $codpet . "'";
                        $result = mysqli_query($mysqli, $query);
                        if ($row = mysqli_fetch_assoc($result)) {
                            $temporario = $row['pessoa_codp'];
                            $nmpet = $row['nomeserv'];
                        }

                        $hora_atual = date("Hi");
                        $data_atual = date("Ymd");

                        $cortecod = substr($temporario, 0, 5);
                        $notifications_name = "Contato para $nmpet";
                        $mensagem = "Seu contato foi encerrado. Foi indicado que você não ligou. Para mais informações entre no painel.";

                        $cod = "NOTCONTNQSCV" . $cortecod . $hora_atual . $data_atual;
                        $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                        $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                        $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatoserv,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$tutornv','$codcont','$dtatual','$hratual')";

                        // Executando a instrução SQL para inserir o pet na tabela "pet"
                        if (mysqli_query($mysqli, $sql)) {
                            echo "<script>
                    Swal.fire(
                        'Atualização bem sucessedida',
                        'Contato atualizado',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                        } else {
                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                        }
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else if ($lig2 == true) {
                    $sql = "UPDATE contatoserv
                SET ligouserv = null, confirmserv = $lig2,arquivarserv=null,contratou=null,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";
                    if (mysqli_query($mysqli, $sql)) {
                        $query = "SELECT * FROM servico WHERE servcod = '" . $codpet . "'";
                        $result = mysqli_query($mysqli, $query);
                        if ($row = mysqli_fetch_assoc($result)) {
                            $temporario = $row['pessoa_codp'];
                            $nmpet = $row['nomeserv'];
                        }

                        $hora_atual = date("Hi");
                        $data_atual = date("Ymd");

                        $cortecod = substr($temporario, 0, 5);
                        $notifications_name = "Contato para $nmpet";
                        $mensagem = "O anunciante indicou que você ligou. Para mais instruções acesso o painel.";

                        $cod = "NOTCONTQSCV" . $cortecod . $hora_atual . $data_atual;
                        $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                        $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                        $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatoserv,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$tutornv','$codcont','$dtatual','$hratual')";

                        // Executando a instrução SQL para inserir o pet na tabela "pet"
                        if (mysqli_query($mysqli, $sql)) {
                            echo "<script>
                    Swal.fire(
                        'Atualização bem sucessedida',
                        'Contato atualizado',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                        } else {
                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                        }
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                }
            }
        } else {
            echo "Nenhum registro encontrado.";
        }
    }
} elseif (isset($_POST['attligserv2'])) {
    $codcont = $_POST['codcont'];
    $lig = $_POST['confirmacao'];

    $sql3 = "SELECT * FROM contatoserv WHERE codconts='$codcont'";
    $result4 = mysqli_query($mysqli, $sql3);

    if ($result4) {
        $row = mysqli_fetch_assoc($result4);

        if ($row) {
            $tutornv = $row['pcodp'];
            $cnf2 = $row['confirmserv'];
            $codcont = $row['codconts'];
            $codpet = $row['scodserv'];
            if ($lig === "nao_ligou") {
                $lig2 = 0;
            } else if ($lig === "ligou") {
                $lig2 = 1;
            } else if ($lig === "esp") {
                $lig2 = null;
            }
            if ($lig2 == true && $cnf2 == true) {
                $sql = "UPDATE contatoserv
                SET ligouserv = true, confirmserv = true, confirm_pe = true,arquivarserv=null,contratou=null,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    $query = "SELECT * FROM servico WHERE servcod = '" . $codpet . "'";
                    $result = mysqli_query($mysqli, $query);
                    if ($row = mysqli_fetch_assoc($result)) {
                        $temporario = $row['pessoa_codp'];
                        $nmpet = $row['nomeserv'];
                    }

                    $hora_atual = date("Hi");
                    $data_atual = date("Ymd");

                    $cortecod = substr($temporario, 0, 5);
                    $notifications_name = "Contato para $nmpet";
                    $mensagem = "O contatante indicou que ligou para você. Gerencie o contato pelo painel.";

                    $cod = "NOTCONTSVC" . $cortecod . $hora_atual . $data_atual;
                    $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                    $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                    $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatoserv,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$temporario','$codcont','$dtatual','$hratual')";

                    // Executando a instrução SQL para inserir o pet na tabela "pet"
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
                    Swal.fire(
                        'Atualização bem sucessedida',
                        'Contato atualizado',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                    } else {
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            } else if ($lig2 === null && $cnf2 == true) {
                $sql = "UPDATE contatoserv
                SET ligouserv = null, confirm_pe = null,arquivarserv=null,contratou=null,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            } else if ($lig2 == false && $cnf2 == true) {
                $sql = "UPDATE contatoserv
                SET ligouserv = false, confirm_pe = false,arquivarserv=null,contratou=false,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    $query = "SELECT * FROM servico WHERE servcod = '" . $codpet . "'";
                    $result = mysqli_query($mysqli, $query);
                    if ($row = mysqli_fetch_assoc($result)) {
                        $temporario = $row['pessoa_codp'];
                        $nmpet = $row['nomeserv'];
                    }

                    $hora_atual = date("Hi");
                    $data_atual = date("Ymd");

                    $cortecod = substr($temporario, 0, 5);
                    $notifications_name = "Contato para $nmpet";
                    $mensagem = "O contatante indicou que não vai ligar para você e o contato foi encerrado. Gerencie o contato pelo painel para arquivar.";

                    $cod = "NOTCONTSVC" . $cortecod . $hora_atual . $data_atual;
                    $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                    $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                    $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatoserv,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$temporario','$codcont','$dtatual','$hratual')";

                    // Executando a instrução SQL para inserir o pet na tabela "pet"
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
                    Swal.fire(
                        'Atualização bem sucessedida',
                        'Contato atualizado',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                    } else {
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            } else {
                if ($lig2 === null) {
                    $sql = "UPDATE contatoserv
                SET ligouserv = null, confirm_pe = null,arquivarserv=null,contratou=null,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
            Swal.fire(
                'Atualização bem sucessedida',
                'Contato atualizado',
                'success'
            ).then(() => {
                window.history.back();
            });
        </script>";
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else if ($lig2 == false) {
                    $sql = "UPDATE contatoserv
                SET ligouserv = false, confirm_pe = $lig2,arquivarserv=null,contratou=false,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";
                    if (mysqli_query($mysqli, $sql)) {
                        $query = "SELECT * FROM servico WHERE servcod = '" . $codpet . "'";
                        $result = mysqli_query($mysqli, $query);
                        if ($row = mysqli_fetch_assoc($result)) {
                            $temporario = $row['pessoa_codp'];
                            $nmpet = $row['nomeserv'];
                        }

                        $hora_atual = date("Hi");
                        $data_atual = date("Ymd");

                        $cortecod = substr($temporario, 0, 5);
                        $notifications_name = "Contato para $nmpet";
                        $mensagem = "O contatante indicou que não vai ligar para você e o contato foi encerrado. Gerencie o contato pelo painel para arquivar.";

                        $cod = "NOTCONTSVC" . $cortecod . $hora_atual . $data_atual;
                        $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                        $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                        $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatoserv,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$temporario','$codcont','$dtatual','$hratual')";

                        // Executando a instrução SQL para inserir o pet na tabela "pet"
                        if (mysqli_query($mysqli, $sql)) {
                            echo "<script>
                    Swal.fire(
                        'Atualização bem sucessedida',
                        'Contato atualizado',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                        } else {
                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                        }
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else if ($lig2 == true) {
                    $sql = "UPDATE contatoserv
                SET ligouserv = null, confirm_pe = $lig2,arquivarserv=null,contratou=null,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";
                    if (mysqli_query($mysqli, $sql)) {
                        $query = "SELECT * FROM servico WHERE servcod = '" . $codpet . "'";
                        $result = mysqli_query($mysqli, $query);
                        if ($row = mysqli_fetch_assoc($result)) {
                            $temporario = $row['pessoa_codp'];
                            $nmpet = $row['nomeserv'];
                        }

                        $hora_atual = date("Hi");
                        $data_atual = date("Ymd");

                        $cortecod = substr($temporario, 0, 5);
                        $notifications_name = "Contato para $nmpet";
                        $mensagem = "O contatante indicou que ligou para você. Gerencie o contato pelo painel para arquivar.";

                        $cod = "NOTCONTSVC" . $cortecod . $hora_atual . $data_atual;
                        $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                        $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                        $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatoserv,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$temporario','$codcont','$dtatual','$hratual')";

                        // Executando a instrução SQL para inserir o pet na tabela "pet"
                        if (mysqli_query($mysqli, $sql)) {
                            echo "<script>
                    Swal.fire(
                        'Atualização bem sucessedida',
                        'Contato atualizado',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                        } else {
                            echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                        }
                    } else {
                        echo "Erro: " . mysqli_error($mysqli);
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                }
            }
        } else {
            echo "Nenhum registro encontrado.";
        }
    }
} else if (isset($_POST['attencserv'])) {
    $codcont = $_POST['codcont'];
    $lig = $_POST['confirmacao'];
    $dataAtual = date('Y-m-d');
    $horaAtual = date('H:i:s');

    $sql3 = "SELECT * FROM contatoserv WHERE codconts='$codcont'";
    $result4 = mysqli_query($mysqli, $sql3);

    if ($result4) {
        $row = mysqli_fetch_assoc($result4);

        if ($row) {
            $tutornv = $row['pcodp'];
            $codcont = $row['codconts'];
            $codpet = $row['scodserv'];

            if ($lig === "ctt") {
                $sql = "UPDATE contatoserv
                SET contratou = true,dtfinalcs='$dataAtual',hrfinalcs='$horaAtual',arquivarserv=null,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    $query = "SELECT * FROM servico WHERE servcod = '" . $codpet . "'";
                    $result = mysqli_query($mysqli, $query);
                    if ($row = mysqli_fetch_assoc($result)) {
                        $temporario = $row['pessoa_codp'];
                        $nmpet = $row['nomeserv'];
                    }

                    $hora_atual = date("Hi");
                    $data_atual = date("Ymd");

                    $cortecod = substr($temporario, 0, 5);
                    $notifications_name = "Contato para $nmpet";
                    $mensagem = "Você contratou um serviço. Avalie para que outras pessoas possam saber da sua experiência.";

                    $cod = "NOTCONTCTTSCV" . $cortecod . $hora_atual . $data_atual;
                    $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                    $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                    $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatoserv,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$tutornv','$codcont','$dtatual','$hratual')";

                    // Executando a instrução SQL para inserir o pet na tabela "pet"
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
                    Swal.fire(
                        'Atualização bem sucessedida',
                        'Contato atualizado',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                    } else {
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            } else if ($lig === "nquis") {
                $sql = "UPDATE contatoserv
                SET contratou = false, dtfinalcs='$dataAtual',hrfinalcs='$horaAtual',arquivarserv=true,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    $query = "SELECT * FROM servico WHERE servcod = '" . $codpet . "'";
                    $result = mysqli_query($mysqli, $query);
                    if ($row = mysqli_fetch_assoc($result)) {
                        $temporario = $row['pessoa_codp'];
                        $nmpet = $row['nomeserv'];
                    }

                    $hora_atual = date("Hi");
                    $data_atual = date("Ymd");

                    $cortecod = substr($temporario, 0, 5);
                    $notifications_name = "Contato para $nmpet";
                    $mensagem = "Uma pena que não contratou. Esperamos que você encontre um serviço que encaixe para você.";

                    $cod = "NOTCONTNQSCV" . $cortecod . $hora_atual . $data_atual;
                    $hratual = date("H:i:s"); // Formato de hora (hora:minuto:segundo)
                    $dtatual = date("Y-m-d"); // Formato de data (ano-mês-dia)
                    $sql = "INSERT INTO notificacao (notifications_name, mensagem, active, notcod, pessoa_codpessoa,contcodcontatoserv,dtnot,hrnot) VALUES ('$notifications_name', '$mensagem', '1', '$cod', '$tutornv','$codcont','$dtatual','$hratual')";

                    // Executando a instrução SQL para inserir o pet na tabela "pet"
                    if (mysqli_query($mysqli, $sql)) {
                        echo "<script>
                    Swal.fire(
                        'Atualização bem sucessedida',
                        'Contato atualizado',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                    } else {
                        echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                    }
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            } else if ($lig === "esp") {
                $sql = "UPDATE contatoserv
                SET contratou = null,dtfinalcs='$dataAtual',hrfinalcs='$horaAtual',arquivarserv=null,avaliacao=null,comentario=null,dtaval=null,hraval=null
                WHERE codconts = '$codcont'";

                if (mysqli_query($mysqli, $sql)) {
                    echo "<script>
                    Swal.fire(
                        'Atualização bem sucessedida',
                        'Contato atualizado',
                        'success'
                    ).then(() => {
                        window.history.back();
                    });
                </script>";
                } else {
                    echo "Erro: " . mysqli_error($mysqli);
                    echo "<script>
                    Swal.fire(
                    'Erro na Atualização',
                    'Alguma coisa deu errado ao atualizar',
                    'error'
                    ).then(() => {
                    window.history.back();
                    });
                    </script>";
                }
            }
        } else {
            echo "Nenhum registro encontrado.";
        }
    }
} else if (isset($_POST['dstvserv'])) {
    $codcont = $_POST['codcont'];
    $atv = $_POST['tpatvpet'];
    if ($atv === null || $atv == false) {

        $atv = 1;
        echo "entrei1: " . $atv;
    } else {

        $atv = 0;
        echo "entrei2: " . $atv;
    }
    $updateSql = "UPDATE contatoserv
    SET arquivarserv=$atv
    WHERE codconts = '$codcont'";

    if ($mysqli->query($updateSql) === TRUE) {
        echo "<script>
Swal.fire(
'Atualização bem sucessedida',
'Contato atualizado',
'success'
).then(() => {
window.history.back();
});
</script>";
    } else {
        echo "Erro: " . mysqli_error($mysqli);
        echo "<script>
Swal.fire(
'Erro na Atualização',
'Alguma coisa deu errado ao atualizar',
'error'
).then(() => {
window.history.back();
});
</script>";
    }
} else if (isset($_POST['rmvaval'])) {
    $codcont = $_POST['codcont'];

    $updateSql = "UPDATE contatoserv
    SET avaliacao=null,comentario=null
    WHERE codconts = '$codcont'";

    if ($mysqli->query($updateSql) === TRUE) {
        echo "<script>
Swal.fire(
'Atualização bem sucessedida',
'Contato atualizado',
'success'
).then(() => {
window.history.back();
});
</script>";
    } else {
        echo "Erro: " . mysqli_error($mysqli);
        echo "<script>
Swal.fire(
'Erro na Atualização',
'Alguma coisa deu errado ao atualizar',
'error'
).then(() => {
window.history.back();
});
</script>";
    }
}
