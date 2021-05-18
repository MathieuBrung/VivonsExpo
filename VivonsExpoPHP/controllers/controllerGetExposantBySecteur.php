<?php

require ('../models/dao/db/DBConnexion.php');
require ('../models/dao/db/Param.php');
require ('../models/dao/ExposantDAO.php');

$secteurLibelle = $_POST['SecteurLibelle'];

print(json_encode(ExposantDAO::getExposantsBySecteurLibelle($secteurLibelle)));