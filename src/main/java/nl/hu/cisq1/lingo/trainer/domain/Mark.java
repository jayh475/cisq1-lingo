package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;


@Entity
@Table(name = "mark")
public enum Mark {
    CORRECT,
    PRESENT,
    ABSENT;


    @GeneratedValue
    @Id
    private int id;



}
