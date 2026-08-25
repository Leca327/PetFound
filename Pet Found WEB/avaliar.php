<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>

    <link rel="stylesheet" href="avaliar.css">
    <title>Form Reviews</title>
</head>

<style>
    :root {
        --yellow: #ff924b;
        --blue: #ff924b;
        --blue-d-1: #ff924b;
        --light: #F5F5F5;
        --grey: #AAA;
        --white: #662901;
        --shadow: 8px 8px 30px rgba(0, 0, 0, .05);
    }

    .wrapper {
        background: var(--white);
        padding: 2rem;
        max-width: 576px;
        width: 100%;
        border-radius: .75rem;
        box-shadow: var(--shadow);
        text-align: center;
    }

    .wrapper h3 {
        color: white;
        font-size: 1.5rem;
        font-weight: 600;
        margin-bottom: 1rem;
    }

    .rating {
        display: flex;
        justify-content: center;
        align-items: center;
        grid-gap: .5rem;
        font-size: 2rem;
        color: var(--yellow);
        margin-bottom: 2rem;
    }

    .rating .star {
        cursor: pointer;
    }

    .rating .star.active {
        opacity: 0;
        animation: animate .5s calc(var(--i) * .1s) ease-in-out forwards;
    }

    @keyframes animate {
        0% {
            opacity: 0;
            transform: scale(1);
        }

        50% {
            opacity: 1;
            transform: scale(1.2);
        }

        100% {
            opacity: 1;
            transform: scale(1);
        }
    }


    .rating .star:hover {
        transform: scale(1.1);
    }

    textarea {
        width: 100%;
        background: var(--light);
        padding: 1rem;
        border-radius: .5rem;
        border: none;
        outline: none;
        resize: none;
        margin-bottom: .5rem;
    }

    .btn-group {
        display: flex;
        grid-gap: .5rem;
        align-items: center;
    }

    .btn-group .btn {
        padding: .75rem 1rem;
        border-radius: .5rem;
        border: none;
        outline: none;
        cursor: pointer;
        font-size: .875rem;
        font-weight: 500;
    }

    .btn-group .btn.submit {
        background: var(--blue);
        color: white;
    }

    .btn-group .btn.submit:hover {
        background: #ff6600;
    }

    .btn-group .btn.cancel {
        background: var(--white);
        color: white;
    }

    .btn-group .btn.cancel:hover {
        background: #ff6600;
    }
</style>

<body>
    <form method="POST">
        <div class="wrapper">
            <h3>Classifique o serviço</h3>
            <div class="rating">
                <input type="number" name="rating" hidden>
                <i class='bx bx-bone bone' style="--i: 0;"></i>
                <i class='bx bx-bone bone' style="--i: 1;"></i>
                <i class='bx bx-bone bone' style="--i: 2;"></i>
                <i class='bx bx-bone bone' style="--i: 3;"></i>
                <i class='bx bx-bone bone' style="--i: 4;"></i>
                <i class='bx bx-bone bone' style="--i: 5;"></i>
                <i class='bx bx-bone bone' style="--i: 6;"></i>
                <i class='bx bx-bone bone' style="--i: 7;"></i>
                <i class='bx bx-bone bone' style="--i: 8;"></i>
                <i class='bx bx-bone bone' style="--i: 9;"></i>
            </div>
            <textarea name="opinion" cols="30" rows="5" placeholder="Nos diga a sua opinião"></textarea>
            <div class="btn-group">
                <button type="submit" name="avaliar" class="btn submit">Postar</button>
                <button class="btn cancel" onclick='closeAvaliarPopup()'>Cancelar</button>
            </div>
        </div>
    </form>
    <script>
        const allStar = document.querySelectorAll('.rating .bone')
        const ratingValue = document.querySelector('.rating input')

        allStar.forEach((item, idx) => {
            item.addEventListener('click', function() {
                let click = 0
                ratingValue.value = idx + 1

                allStar.forEach(i => {
                    i.classList.replace('bxs-bone', 'bx-bone')
                    i.classList.remove('active')
                })
                for (let i = 0; i < allStar.length; i++) {
                    if (i <= idx) {
                        allStar[i].classList.replace('bx-bone', 'bxs-bone')
                        allStar[i].classList.add('active')
                    } else {
                        allStar[i].style.setProperty('--i', click)
                        click++
                    }
                }
            })
        })
    </script>
</body>

<?php


echo $_SESSION['usuario'] . " " . $servcod;
// Check if the form was submitted
if (isset($_POST['avaliar'])) {
    $rating = $_POST['rating'];
    $opinion = $_POST['opinion'];

    $sqlSelect = "SELECT * FROM pessoa WHERE nickname = '" . $_SESSION['usuario'] . "'";
    $result = mysqli_query($mysqli, $sqlSelect);

    if ($result->num_rows > 0) {

        $row = $result->fetch_assoc();
        $codpessoa = $row['pcod'];
        $dataAtual = date('Y-m-d');
        $horaAtual = date('H:i:s');
        // Get the rating and opinion from the form

        // Update the existing record in the database
        $query = "UPDATE contatoserv SET avaliacao = '$rating', comentario = '$opinion',dtaval= '$dataAtual' ,hraval='$horaAtual' WHERE pcodp = '$codpessoa' AND scodserv = '$servcod'";
        $result = mysqli_query($mysqli, $query);

        if ($result) {
            echo "<script>
        Swal.fire(
            'Avaliação feita!',
            'Obrigada pelo seu FeedBack',
            'success'
        ).then(() => {
            window.history.back();
        });
    </script>";
        } else {
            echo "<script> Swal.fire(
        'Erro em Avaliar',
        'Avaliação não foi feita.',
        'error'
    ).then(() => {
        window.history.back();
    });
    </script>";
        }


        // Close the database connection
        $stmt->close();
        $mysqli->close();
    } else {
        echo "<script> Swal.fire(
            'Erro em Avaliar',
            'Pessoa Não encontrada.',
            'error'
        ).then(() => {
            window.history.back();
        });
        </script>";
        echo $stmt->error;
    }
}
?>

</html>