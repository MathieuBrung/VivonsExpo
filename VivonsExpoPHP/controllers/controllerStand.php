<?php

require ('../models/dao/db/DBConnexion.php');
require ('../models/dao/db/Param.php');
require ('../models/dao/StandDAO.php');

$exposantId = $_POST['ExposantId'];

print(json_encode(StandDAO::getStandByExposantId($exposantId)));