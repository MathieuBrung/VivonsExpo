<?php

require ('../models/dao/db/DBConnexion.php');
require ('../models/dao/db/Param.php');
require ('../models/dao/DemandeDAO.php');

$exposantId = $_POST['ExposantId'];
$motif = $_POST['Motif'];

print(json_encode(DemandeDAO::addDemandeByExposantId($exposantId, $motif)));