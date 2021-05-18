<?php

require ('../models/dao/db/DBConnexion.php');
require ('../models/dao/db/Param.php');
require ('../models/dao/ExposantDAO.php');

$exposantId = $_POST['ExposantId'];

print(json_encode(ExposantDAO::getExposantByExposantId($exposantId)));