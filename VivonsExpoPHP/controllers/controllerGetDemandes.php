<?php

require ('../models/dao/db/DBConnexion.php');
require ('../models/dao/db/Param.php');
require ('../models/dao/DemandeDAO.php');

$exposantId = $_POST['ExposantId'];

print(json_encode(DemandeDAO::getDemandesByExposantId($exposantId)));