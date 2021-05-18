<?php

require ('../models/dao/db/DBConnexion.php');
require ('../models/dao/db/Param.php');
require ('../models/dao/SecteurDAO.php');
require ('../models/dao/ExposantDAO.php');

$login = 'mat';
$password = 'mat';
$secteurLibelle = 'Vivons bois';

print(json_encode(ExposantDAO::addExposant($login, $password, $secteurLibelle)));