package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	/*
	 * AAA:
	 * SELECT h1.City,h2.City, COUNT(DISTINCT(h1.Provider))
	 * FROM nyc wifi hotspot locations h1, nyc wifi hotspot locations h2
	 * WHERE h1.City>h2.City AND h1.Provider=h2.Provider
	 * GROUP BY h1.City,h2.City
	 * VVV:
	 * SELECT DISTINCT h.City
	 * FROM nyc wifi hotspot locations h
	 */

	private NYCDao dao;
	private Graph <String, DefaultWeightedEdge> grafo;

	public Model() {
		dao = new NYCDao();
	}

	public List <String> getAllProvider(){
		return dao.getAllProvider();
	}


	public void creaGrafo(String h) {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		//aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.dao.getAllQuartieri(h));

		//aggiungo archi
		for(Adiacenza a : dao.getArchi(h)) {
			Graphs.addEdge(this.grafo, a.getC1(), a.getC2(), a.getPeso());
		}
		/* for(City c1 : this.cities) {
		for(City c2 : this.cities) {
			if(!c1.equals(c2)) {
			double peso = LatLngTool.distance(c1.getPosizione(), c2.getPosizione(), LengthUnit.KILOMETER);
			Graphs.addEdge(this.grafo, c1, c2, peso);
			}
			}
			}*/

		System.out.println("Grafo Creato");
		System.out.println("#Vertici " +this.grafo.vertexSet().size()+ " \n");
		System.out.println("#Archi " +this.grafo.edgeSet().size()+ " \n");
	}
	public List <String> getAllVertici(){
		return new ArrayList <>(this.grafo.vertexSet());
	}

	public int nVertici() {
		// TODO Auto-generated method stub
		return this.grafo.vertexSet().size();
	}

	public int nArchi() {
		// TODO Auto-generated method stub
		return this.grafo.edgeSet().size();
	}

	public List<Adiacenza> getQuartieriAdiacenti(String c){
		List <Adiacenza> list = new ArrayList<>();
		for(String s: Graphs.neighborListOf(this.grafo, c)) {
			DefaultWeightedEdge e=this.grafo.getEdge(s, c);
			double peso= this.grafo.getEdgeWeight(e);
			Adiacenza a= new Adiacenza(c, s, peso);
			list.add(a);

		}
		Collections.sort(list);
		return list;
	}


}
