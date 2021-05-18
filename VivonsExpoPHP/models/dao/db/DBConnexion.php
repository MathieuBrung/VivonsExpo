<?php

    class DBConnexion extends PDO
    {
        private static $connexion;
        
        public static function getConnexion() 
        {

            if (!self::$connexion )
            {
                self::$connexion = new DBConnexion();
            }
            return self::$connexion;
            
        }
        
        function __construct() 
        {
            try {
                parent::__construct(Param::$dsn ,Param::$user, Param::$pass);
            } catch (Exception $e) {

                echo $e->getMessage();
                require('views/errorView.php');
            }
        }
    }