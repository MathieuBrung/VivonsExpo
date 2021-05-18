<?php

require ('../models/dao/db/DBConnexion.php');
require ('../models/dao/db/Param.php');
require ('../models/dao/ExposantDAO.php');

$exposantId = $_POST['ExposantId'];
$raisonSociale = $_POST['RaisonSociale'];
$activite = $_POST['Activite'];
$nom = $_POST['Nom'];
$prenom = $_POST['Prenom'];
$telephone = $_POST['Telephone'];
$mail = $_POST['Mail'];

print(json_encode(ExposantDAO::updateExposant($exposantId, $raisonSociale, $activite, $nom, $prenom, $telephone, $mail)));