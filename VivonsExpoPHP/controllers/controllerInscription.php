<?php

require ('../models/dao/db/DBConnexion.php');
require ('../models/dao/db/Param.php');
require ('../models/dao/SecteurDAO.php');
require ('../models/dao/ExposantDAO.php');
require ('../models/dao/StandDAO.php');

$login = $_POST['login'];
$password = $_POST['password'];
$secteurLibelle = $_POST['secteurLibelle'];

$exposantId = ExposantDAO::addExposant($login, $password, $secteurLibelle);
$secteurId = ExposantDAO::getSecteurIdExposantId($exposantId);

print(json_encode(StandDAO::assignStand($exposantId, $secteurId)));