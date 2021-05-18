<?php

    abstract class UserDAO
    {
        
        static function userExist($login, $password)
        {
            try {
                $db = DBConnexion::getConnexion();
                $req = $db->prepare('SELECT UtilisateurId FROM utilisateur WHERE Login = :login AND Mdp = md5(:password)');
            
                $req->bindValue(":login", $login);
                $req->bindValue(":password" , $password);
                $req->execute();

                $data = $req->fetch();
                
                if ($data != null) 
                {
                    $data = $data['UtilisateurId'];
                } 
                else 
                {
                    $data = false;
                }

            } catch (Exception $e) {
                $data = false;
            }

            return $data;
        }

    }
    

    // static function addDirectory($path)
    // {
    //     // si $path n'existe pas dans la bdd
    //     // alors on le créer
    //     return true;
    // }

    // static function getUserByEmail($userEmail)
    // {
    //     // Paramètre 'Admin' provisoire, il faudra le passer à 'Visitor' lorsque les comptes de la bdd seront créés
    //     $req = DBConnexion::getConnexion('Admin')->prepare('SELECT UserLastName, UserFirstName, UserPhoneNumber, UTName FROM user NATURAL JOIN user_type WHERE UserEmail = :email');
    //     $req->bindValue(':email', $userEmail);
    //     $req->execute();
        
    //     $data = $req->fetch();
        
    //     return new User($data);
    // }


    // Ajouter le type d'utilisateur en paramètre ?
    // static function getUsers()
    // {
        //     $users = [];

        //     // Récupération du type dans les paramètres ?
        //     // Paramètre 'Admin' provisoire, il faudra le passer à 'Visitor' lorsque les comptes de la bdd seront créés
    //     $req = DBConnexion::getConnexion('Admin')->query('SELECT email, lastName, firstName FROM user');
    
    //     while($data = $req->fetch(PDO::FETCH_ASSOC))
    //     {
        //         $users[] = new User($data);
    //     }
    
    //     return $users;
    // }

    // static function getUserTypeByEmail($userEmail)
    // {
        //     // Paramètre 'Admin' provisoire, il faudra le passer à 'Visitor' lorsque les comptes de la bdd seront créés
        //     $req = DBConnexion::getConnexion('Admin')->prepare('SELECT userType FROM user WHERE email = :email');
        //     $req->bindValue(':email', $userEmail);
        //     $req->execute();
        //     $req->fetch();
        
        //     return $req;
        // }