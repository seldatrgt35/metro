package metrohomework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Vertex<T> {
	private String name;
	private ArrayList<Edge> edges;
	private Vertex parent;
	private boolean visited;  
	private double cost;
	private Object predecessor;  

	public Vertex(String name) {
		this.name = name;
		edges = new ArrayList<Edge>();
		parent = null;
		visited = false;
	}

	public void addEdge(Edge e) {
		edges.add(e);
	}

	public ArrayList<Edge> getEdges() {
		return this.edges;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vertex getParent() {
		return parent;
	}

	public void setParent(Vertex parent) {
		this.parent = parent;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void visit() {
		this.visited = true;
	}

	public void unvisit() {
		this.visited = false;
	}

	public boolean isVisited() {
		return this.visited;
	}

	public Vertex getUnvisitedNeighbor() {
		Vertex result = null;

		Iterator<Vertex> neighbors = getNeighborIterator();
		while (neighbors.hasNext() && (result == null))
		{
			Vertex nextNeighbor = neighbors.next();
			if (!nextNeighbor.isVisited())
				result = nextNeighbor;
		} // end while

			return result;
	}

	public boolean hasEdge(String neighbor) {
		boolean found = false;
		Iterator<Vertex> neighbors = getNeighborIterator();
		while (neighbors.hasNext())
		{
			Vertex nextNeighbor = neighbors.next();
			if (nextNeighbor.getName().equalsIgnoreCase(neighbor))
			{
				found = true;
				break;
			}
		} // end while

		return found;
	}

	public Iterator<Vertex> getNeighborIterator()
	{
		return new NeighborIterator();
	} // end getNeighborIterator

	private class NeighborIterator implements Iterator<Vertex>
	{
		int edgeIndex = 0;  
		private NeighborIterator()
		{
			edgeIndex = 0; 
		} // end default constructor

		public boolean hasNext()
		{
			return edgeIndex < edges.size();
		} // end hasNext

		public Vertex next()
		{
			Vertex nextNeighbor = null;

			if (hasNext())
			{
				nextNeighbor = edges.get(edgeIndex).getDestination();
				edgeIndex++;
			}
			else
				throw new NoSuchElementException();

			return nextNeighbor;
		} // end next

		public void remove()
		{
			throw new UnsupportedOperationException();
		} // end remove
	} // end NeighborIterator

	public <T> T getData() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPredecessor(Vertex<T> predecessor) {
	    this.predecessor = predecessor;
	}
	
}