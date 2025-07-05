package com.gestioncafe.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestioncafe.dto.BeneficePeriodeStatDTO;
import com.gestioncafe.dto.VentePeriodeStatDTO;
import com.gestioncafe.dto.VenteProduitStatDTO;
import com.gestioncafe.model.DetailsVente;

public interface DetailsVenteRepository extends JpaRepository<DetailsVente, Integer> {
    @Query("SELECT new com.gestioncafe.dto.VenteProduitStatDTO(d.produit.id, d.produit.nom, SUM(d.quantite), SUM(d.montant)) "
        +
        "FROM DetailsVente d GROUP BY d.produit.id, d.produit.nom")
    List<VenteProduitStatDTO> getVenteStatParProduit();

    @Query("""
            SELECT new com.gestioncafe.dto.VentePeriodeStatDTO(
                CASE WHEN :periode = 'jour' THEN FUNCTION('to_char', v.dateVente, 'YYYY-MM-DD')
                     WHEN :periode = 'semaine' THEN FUNCTION('to_char', v.dateVente, 'IYYY-IW')
                     ELSE FUNCTION('to_char', v.dateVente, 'YYYY-MM') END,
                SUM(d.quantite)
            )
            FROM DetailsVente d JOIN d.vente v
            GROUP BY
                CASE WHEN :periode = 'jour' THEN FUNCTION('to_char', v.dateVente, 'YYYY-MM-DD')
                     WHEN :periode = 'semaine' THEN FUNCTION('to_char', v.dateVente, 'IYYY-IW')
                     ELSE FUNCTION('to_char', v.dateVente, 'YYYY-MM') END
            ORDER BY 1
        """)
    List<VentePeriodeStatDTO> getVenteStatParPeriode(@Param("periode") String periode);

    @Query("""
            SELECT new com.gestioncafe.dto.VentePeriodeStatDTO(
                CASE WHEN :periode = 'jour' THEN FUNCTION('to_char', v.dateVente, 'YYYY-MM-DD')
                     WHEN :periode = 'semaine' THEN FUNCTION('to_char', v.dateVente, 'IYYY-IW')
                     ELSE FUNCTION('to_char', v.dateVente, 'YYYY-MM') END,
                SUM(d.quantite)
            )
            FROM DetailsVente d JOIN d.vente v
            WHERE d.produit.id = :produitId
            GROUP BY
                CASE WHEN :periode = 'jour' THEN FUNCTION('to_char', v.dateVente, 'YYYY-MM-DD')
                     WHEN :periode = 'semaine' THEN FUNCTION('to_char', v.dateVente, 'IYYY-IW')
                     ELSE FUNCTION('to_char', v.dateVente, 'YYYY-MM') END
            ORDER BY 1
        """)
    List<VentePeriodeStatDTO> getVenteStatParPeriodeEtProduit(@Param("periode") String periode,
                                                              @Param("produitId") Integer produitId);

    @Query("""
            SELECT new com.gestioncafe.dto.BeneficePeriodeStatDTO(
                CASE WHEN :periode = 'jour' THEN FUNCTION('to_char', v.dateVente, 'YYYY-MM-DD')
                     WHEN :periode = 'semaine' THEN FUNCTION('to_char', v.dateVente, 'IYYY-IW')
                     ELSE FUNCTION('to_char', v.dateVente, 'YYYY-MM') END,
                AVG(d.quantite),
                SUM(d.montant)
            )
            FROM DetailsVente d JOIN d.vente v
            GROUP BY
                CASE WHEN :periode = 'jour' THEN FUNCTION('to_char', v.dateVente, 'YYYY-MM-DD')
                     WHEN :periode = 'semaine' THEN FUNCTION('to_char', v.dateVente, 'IYYY-IW')
                     ELSE FUNCTION('to_char', v.dateVente, 'YYYY-MM') END
            ORDER BY 1
        """)
    List<BeneficePeriodeStatDTO> getBeneficeStatParPeriode(@Param("periode") String periode);

    @Query("""
            SELECT new com.gestioncafe.dto.VenteProduitStatDTO(d.produit.id, d.produit.nom, SUM(d.quantite), SUM(d.montant))
            FROM DetailsVente d
            WHERE (:dateDebut IS NULL OR d.vente.dateVente >= :dateDebut)
              AND (:dateFin IS NULL OR d.vente.dateVente <= :dateFin)
            GROUP BY d.produit.id, d.produit.nom
        """)
    List<VenteProduitStatDTO> getVenteStatParProduitAvecDate(@Param("dateDebut") LocalDateTime dateDebut,
                                                             @Param("dateFin") LocalDateTime dateFin);

    @Query(value = """
            SELECT dv.id_produit, p.nom, SUM(dv.quantite), SUM(dv.montant)
            FROM details_vente dv
            JOIN produit p ON p.id = dv.id_produit
            JOIN vente v ON v.id = dv.id_vente
            WHERE (COALESCE(:dateExacte, NULL) IS NULL OR DATE(v.date_vente) = :dateExacte)
              AND (COALESCE(:annee, NULL) IS NULL OR EXTRACT(YEAR FROM v.date_vente) = :annee)
              AND (COALESCE(:mois, NULL) IS NULL OR EXTRACT(MONTH FROM v.date_vente) = :mois)
              AND (COALESCE(:jourMois, NULL) IS NULL OR EXTRACT(DAY FROM v.date_vente) = :jourMois)
              AND (COALESCE(:jourSemaine, NULL) IS NULL OR (
                    (:jourSemaine = 7 AND EXTRACT(DOW FROM v.date_vente) = 0) OR
                    (EXTRACT(DOW FROM v.date_vente) = :jourSemaine)
              ))
              AND (COALESCE(:dateDebut, NULL) IS NULL OR v.date_vente >= :dateDebut)
              AND (COALESCE(:dateFin, NULL) IS NULL OR v.date_vente <= :dateFin)
            GROUP BY dv.id_produit, p.nom
        """, nativeQuery = true)
    List<Object[]> getVenteStatParProduitFiltreNative(
        @Param("dateExacte") LocalDate dateExacte,
        @Param("annee") Integer annee,
        @Param("mois") Integer mois,
        @Param("jourMois") Integer jourMois,
        @Param("jourSemaine") Integer jourSemaine,
        @Param("dateDebut") LocalDateTime dateDebut,
        @Param("dateFin") LocalDateTime dateFin);

    @Query(value = """
            SELECT 
                CASE WHEN :periode = 'jour' THEN TO_CHAR(v.date_vente, 'YYYY-MM-DD')
                     WHEN :periode = 'mois' THEN TO_CHAR(v.date_vente, 'YYYY-MM')
                     ELSE TO_CHAR(v.date_vente, 'YYYY') END as periode_label,
                SUM(dv.quantite) as total_quantite
            FROM details_vente dv
            JOIN vente v ON v.id = dv.id_vente
            WHERE (
                (:periode = 'jour' AND v.date_vente >= NOW() - INTERVAL '60 days') OR
                (:periode = 'mois' AND v.date_vente >= date_trunc('month', NOW()) - INTERVAL '11 months') OR
                (:periode = 'annee' AND v.date_vente >= date_trunc('year', NOW()) - INTERVAL '2 years')
            )
            GROUP BY periode_label
            ORDER BY periode_label
        """, nativeQuery = true)
    List<Object[]> getTotalProduitVenduParPeriode(
        @Param("periode") String periode
    );

    // Ajout : bénéfice moyen par période
    @Query(value = """
        SELECT 
            CASE 
                WHEN :periode = 'jour' THEN TO_CHAR(v.date_vente, 'YYYY-MM-DD')
                WHEN :periode = 'mois' THEN TO_CHAR(v.date_vente, 'YYYY-MM')
                WHEN :periode = 'annee' THEN TO_CHAR(v.date_vente, 'YYYY')
                ELSE TO_CHAR(v.date_vente, 'YYYY-MM-DD')
            END as periode,
            AVG(dv.montant) as benefice_moyen
        FROM details_vente dv
        JOIN vente v ON dv.id_vente = v.id
        JOIN produit p ON dv.id_produit = p.id
        -- LEFT JOIN recette r ON r.id_produit = p.id -- plus besoin
        GROUP BY periode
        ORDER BY periode
        """, nativeQuery = true)
    List<Object[]> getBeneficeMoyenParPeriode(@Param("periode") String periode);

    // Bénéfice estimé total pour la période sélectionnée
    @Query(value = """
        SELECT COALESCE(SUM(dv.montant), 0)
        FROM details_vente dv
        JOIN vente v ON dv.id_vente = v.id
        JOIN produit p ON dv.id_produit = p.id
        -- LEFT JOIN recette r ON r.id_produit = p.id -- plus besoin
        WHERE (
            (:periode = 'jour' AND v.date_vente >= NOW() - INTERVAL '60 days') OR
            (:periode = 'mois' AND v.date_vente >= date_trunc('month', NOW()) - INTERVAL '11 months') OR
            (:periode = 'annee' AND v.date_vente >= date_trunc('year', NOW()) - INTERVAL '2 years')
        )
        """, nativeQuery = true)
    BigDecimal getBeneficeEstimeParPeriode(@Param("periode") String periode);

    // Bénéfice total par période (jour/mois/année) AVEC FILTRE sur la période sélectionnée
    @Query(value = """
        SELECT 
            CASE 
                WHEN :periode = 'jour' THEN TO_CHAR(v.date_vente, 'YYYY-MM-DD')
                WHEN :periode = 'mois' THEN TO_CHAR(v.date_vente, 'YYYY-MM')
                WHEN :periode = 'annee' THEN TO_CHAR(v.date_vente, 'YYYY')
                ELSE TO_CHAR(v.date_vente, 'YYYY-MM-DD')
            END as periode,
            COALESCE(SUM(dv.montant), 0) as benefice_total
        FROM details_vente dv
        JOIN vente v ON dv.id_vente = v.id
        JOIN produit p ON dv.id_produit = p.id
        -- LEFT JOIN recette r ON r.id_produit = p.id -- plus besoin
        WHERE (
            (:periode = 'jour' AND v.date_vente >= NOW() - INTERVAL '60 days') OR
            (:periode = 'mois' AND v.date_vente >= date_trunc('month', NOW()) - INTERVAL '11 months') OR
            (:periode = 'annee' AND v.date_vente >= date_trunc('year', NOW()) - INTERVAL '2 years')
        )
        GROUP BY periode
        ORDER BY periode
        """, nativeQuery = true)
    List<Object[]> getBeneficeTotalParPeriode(@Param("periode") String periode);

    // Vérifie s'il existe au moins un détail de vente pour ce produit
    boolean existsByProduitId(Integer produitId);
}