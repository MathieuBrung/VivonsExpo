<?php

require ('../models/dao/db/DBConnexion.php');
require ('../models/dao/db/Param.php');
require ('../models/dao/SecteurDAO.php');

$universLibelle = $_POST['UniversLibelle'];

print(json_encode(SecteurDAO::getSecteursByUnivers($universLibelle)));