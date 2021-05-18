<?php

require ('../models/dao/db/DBConnexion.php');
require ('../models/dao/db/Param.php');
require ('../models/dao/UserDAO.php');
require ('../models/dao/ExposantDAO.php');

$login = $_POST['login'];
$password = $_POST['password'];

if (UserDAO::userExist($login, $password) != false)
{
    print(json_encode(ExposantDAO::getExposantForConnexionByUserId(UserDAO::userExist($login, $password))));
}