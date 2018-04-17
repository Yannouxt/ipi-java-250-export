package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional
public class InitData {

    @Autowired
    private EntityManager em;

    public void insertTestData() {

        Client client  = new Client();
        client.setNom("PETRILLO");
        client.setPrenom("Alexandre");
        em.persist(client);
        
        Client client2  = new Client();
        client2.setNom("LAFOR\"E;ST");
        client2.setPrenom("Yann");
        em.persist(client2);

        Article article = new Article();
        article.setLibelle("Carte mère ASROCK 2345");
        article.setPrix(79.90);
        em.persist(article);
        
        Article article2 = new Article();
        article2.setLibelle("Carte mère AAA 1234");
        article2.setPrix(150.0);
        em.persist(article2);
        
        Facture facture2 = new Facture();
        facture2.setClient(client);
        em.persist(facture2);

        Facture facture = new Facture();
        facture.setClient(client);
        em.persist(facture);

        LigneFacture ligneFacture1 = new LigneFacture();
        ligneFacture1.setFacture(facture);
        ligneFacture1.setArticle(article);
        ligneFacture1.setQuantite(1);
        em.persist(ligneFacture1);
        

        LigneFacture ligneFacture2 = new LigneFacture();
        ligneFacture2.setFacture(facture2);
        ligneFacture2.setArticle(article2);
        ligneFacture2.setQuantite(3);
        em.persist(ligneFacture2);

    }
}
