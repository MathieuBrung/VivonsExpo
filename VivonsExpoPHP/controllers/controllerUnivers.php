<?php

require ('../models/dao/db/DBConnexion.php');
require ('../models/dao/db/Param.php');
require ('../models/dao/UniversDAO.php');

print(json_encode(UniversDAO::getUnivers()));