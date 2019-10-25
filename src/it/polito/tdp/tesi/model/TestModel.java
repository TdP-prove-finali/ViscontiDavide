package it.polito.tdp.tesi.model;

import java.util.*;

public class TestModel {

	public static void main(String[] args) {
		
		Piatto p = new Piatto(0, 2, 4, "a", 4);
		Piatto p2 = new Piatto(1, 2.5, 3, "b", 4.5);
		Piatto p3 = new Piatto(2, 3.5, 3, "c", 6.5);
		Piatto p4 = new Piatto(3, 3, 5, "d", 5, 100);
		Piatto p5 = new Piatto(4, 2, 4, "e", 3.5, 70);
		
		List<Piatto> l = new LinkedList<Piatto>();
		l.add(p);
		l.add(p2);
		l.add(p3);
		l.add(p4);
		l.add(p5);
		
		Manifestazione m = new Manifestazione(0, 1000, "aa", 20, 3000);
		
		Bevanda b = new Bevanda(0, 2, "s", 0.5);
		Bevanda bb = new Bevanda(1, 3.5, "d", 1.5);
		
		List<Bevanda> ll = new LinkedList<Bevanda>();
		ll.add(bb);
		ll.add(b);
		
		Model mm = new Model(m, l, ll);
		mm.creaClienti(40);
		mm.creaDipendenti(1, 3, 6);
		mm.init();
		mm.run();

	}

}
