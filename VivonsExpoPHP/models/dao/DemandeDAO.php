<?php

    abstract class DemandeDAO
    {
        static function getDemandesByExposantId($exposantId)
        {
            try {
                $db = DBConnexion::getConnexion();
                $req = $db->prepare('SELECT COUNT(*) AS NbDemandes FROM demande WHERE ExposantId = :exposantId');
            
                $req->bindValue(":exposantId", $exposantId);
                $req->execute();
                
                $data = $req->fetchAll(PDO::FETCH_ASSOC);

            } catch (Exception $e) {
                $data = "";
            }

            return $data;
        }

        static function addDemandeByExposantId($exposantId, $motif)
        {
            $db = DBConnexion::getConnexion();
            $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            $db->beginTransaction();

            $req = $db->prepare('INSERT INTO demande(Motif, ExposantId) VALUES (:motif, :exposantId)');

            $req->bindValue(':motif', $motif);
            $req->bindValue(':exposantId', $exposantId);
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
    }