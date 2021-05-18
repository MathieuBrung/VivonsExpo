<?php

    abstract class ExposantDAO 
    {

        
        static function getExposantsByUniversLibelle($universLibelle)
        {
            try {
                $db = DBConnexion::getConnexion();
                $req = $db->prepare('SELECT exposant.ExposantId, RaisonSociale, Activite, StandId, AlleeId, TraveeId, HallId_ALLEE
                                    FROM exposant, secteur, univers, stand
                                    WHERE UniversLibelle = :universLibelle
                                    AND exposant.SecteurId = secteur.SecteurId
                                    AND secteur.UniversId = univers.UniversId
                                    AND stand.ExposantId = exposant.ExposantId
                                    ORDER BY RaisonSociale');

                $req->bindValue(":universLibelle", $universLibelle);
                $req->execute();

                $data = $req->fetchAll(PDO::FETCH_ASSOC);

            } catch (Exception $e) {
                $data = "";
            }

            return $data;
        }

        static function getExposantsBySecteurLibelle($secteurLibelle)
        {
            try {
                $db = DBConnexion::getConnexion();
                $req = $db->prepare('SELECT exposant.ExposantId, RaisonSociale, Activite, StandId, AlleeId, TraveeId, HallId_ALLEE
                                    FROM exposant, secteur, univers, stand
                                    WHERE SecteurLibelle = :secteurLibelle
                                    AND exposant.SecteurId = secteur.SecteurId
                                    AND secteur.UniversId = univers.UniversId
                                    AND stand.ExposantId = exposant.ExposantId
                                    ORDER BY RaisonSociale');

                $req->bindValue(":secteurLibelle", $secteurLibelle);
                $req->execute();
                
                $data = $req->fetchAll(PDO::FETCH_ASSOC);

            } catch (Exception $e) {
                $data = "";
            }

            return $data;
        }

        static function getExposantByExposantId($exposantId)
        {
            try {
                $db = DBConnexion::getConnexion();
                $req = $db->prepare('SELECT * FROM exposant WHERE ExposantId = :exposantId');
            
                $req->bindValue(":exposantId", $exposantId);
                $req->execute();
                
                $data = $req->fetchAll(PDO::FETCH_ASSOC);

            } catch (Exception $e) {
                $data = "";
            }

            return $data;
        }

        static function getExposantByUserId($userId)
        {
            try {
                $db = DBConnexion::getConnexion();
                $req = $db->prepare('SELECT * FROM exposant WHERE UtilisateurId = :userId');
            
                $req->bindValue(":userId", $userId);
                $req->execute();
                
                $data = $req->fetchAll(PDO::FETCH_ASSOC);

            } catch (Exception $e) {
                $data = "";
            }

            return $data;
        }

        public static function getSecteurIdExposantId($exposantId)
        {
            try {
                $db = DBConnexion::getConnexion();
                $req = $db->prepare('SELECT SecteurId FROM Exposant WHERE ExposantId = :exposantId');
                $req->bindValue(':exposantId', $exposantId);
                $req->execute();

                $data = $req->fetch();
                $data = $data['SecteurId'];
            } catch (Exception $e){
                $data = -1;
            }
            return $data;
        }

        static function getExposantForConnexionByUserId($userId)
        {
            try {
                $db = DBConnexion::getConnexion();
                $req = $db->prepare('SELECT ExposantId, RaisonSociale FROM exposant WHERE UtilisateurId = :userId');
            
                $req->bindValue(":userId", $userId);
                $req->execute();
                
                $data = $req->fetchAll(PDO::FETCH_ASSOC);

            } catch (Exception $e) {
                $data = "";
            }

            return $data;
        }

        static function updateExposant($exposantId, $raisonSociale, $activite, $nom, $prenom, $telephone, $mail)
        {
            $db = DBConnexion::getConnexion();
            $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            $db->beginTransaction();
            $req = $db->prepare('UPDATE exposant SET RaisonSociale = :raisonSociale, Activite = :activite, Nom = :nom, Prenom = :prenom, Telephone = :telephone, Mail = :mail
                                WHERE ExposantId = :exposantId');
            
            $req->bindValue(':exposantId', $exposantId);
            $req->bindValue(':raisonSociale', $raisonSociale);
            $req->bindValue(':activite', $activite);
            $req->bindValue(':nom', $nom);
            $req->bindValue(':prenom', $prenom);
            $req->bindValue(':telephone', $telephone);
            $req->bindValue(':mail', $mail);

            $req->execute();

            if (!$db->commit())
            {
                $db->rollBack();
                return false;
            }
            else
            {
                return true;
            }
        }

        static function addExposant($login, $password, $secteurLibelle)
        {

            $secteurId = SecteurDAO::getSecteurIdByName($secteurLibelle);

            $db = DBConnexion::getConnexion();
            $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            $db->beginTransaction();

            $req = $db->prepare('INSERT INTO utilisateur(Login, Mdp) 
                                VALUES (:login, :password)');

            $req2 = $db->prepare('INSERT INTO exposant(UtilisateurId, SecteurId) 
                                VALUES (:userId, :secteurId)');

            $req->bindValue(':login', $login);
            $req->bindValue(':password', md5($password));
            $req->execute();

            $userId = $db->lastInsertId();

            $req2->bindValue(':userId', $userId);
            $req2->bindValue(':secteurId', $secteurId);
            $req2->execute();

            $exposantId = $db->lastInsertId();

            if (!$db->commit())
            {
                $db->rollBack();
                return false;
            }
            else
            {
                return $exposantId;
            }

        }
    }