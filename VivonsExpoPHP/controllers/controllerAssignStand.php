<?php

require ('../models/dao/db/DBConnexion.php');
require ('../models/dao/db/Param.php');
require ('../models/dao/ExposantDAO.php');
require ('../models/dao/StandDAO.php');

// $exposantId = $_POST['ExposantId'];
$exposantId = "7";
$secteurId = ExposantDAO::getSecteurIdExposantId($exposantId);

print(json_encode(StandDAO::assignStand($exposantId, $secteurId)));