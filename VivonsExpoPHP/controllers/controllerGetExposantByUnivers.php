<?php

require ('../models/dao/db/DBConnexion.php');
require ('../models/dao/db/Param.php');
require ('../models/dao/ExposantDAO.php');

$universLibelle = $_POST['UniversLibelle'];

print(json_encode(ExposantDAO::getExposantsByUniversLibelle($universLibelle)));