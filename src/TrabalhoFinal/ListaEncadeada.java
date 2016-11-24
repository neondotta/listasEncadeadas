package TrabalhoFinal;

import java.util.Comparator;

public class ListaEncadeada<T extends Comparable<T>> implements Iterable<T> {
	private int qtdElemento = 0;
	private class Node {
		private T data;
		private Node next;
		private Node previous;
		
		public Node(T value) {
			data = value;
		}
	}
	
	private class ListIterator implements Iterador<T> {
		private Node current = null;
		private Node previous = null;
		private int qtdElemento = 0;
		
		@Override
		public boolean hasNext() {
			if (current == null){
				return head != null;
			}
			return current.next != null;
		}
		@Override
		public T next() {
			if (!hasNext()){
				System.out.println("Fim");
			}
			if (current == null) {
				current = head;
			} else {
				previous = current;
				current = current.next;
			}
			return current.data;
		}
		@Override
		public void remove() {
			if (current == null) {
				throw new IllegalStateException("Use next()!");
			}
			Node next = current.next;
			
			if (previous == null) {
				head = next;
			} else {
				previous.next = next;
			}
			if (next == null) {
				tail = previous;
			}
			qtdElemento--;
		}
		@Override
		public void append(T dado) {
			if (current == null) {
				throw new IllegalStateException("Use next()!");
			}
			
			Node node = new Node(dado);
			Node next = current.next; //node next = null (node next = pr�ximo do atual)
			
			node.next = next; //(proximo do node ser� node next)
			current.next = node;//(proximo do atual recebe o Node node).
/*			current.previous = previous; atual.anterior aponta para o anterior
			current.next = next;
			previous.next = current;
			next.previous = current;*/
			if (current == tail) {
				tail = node;
			}
			qtdElemento++;
		}
		@Override
		public void insert(T dado) {
			if (current == null) {
				throw new IllegalStateException("Use next()!");
			}
			
			Node node = new Node(dado);
			
			node.next = current;
			if (previous != null){
				node.next = current;
				node.previous = previous;				
				previous.next = node;
				current.previous = node;
			}
			else {
				node.next = current;
				node.previous = null;
				current.previous = node;
				head = node;
			}
			qtdElemento++;
		}
		public void viewHead(T dado){
			Node novo = new Node(dado);
			if(head == null){
				current = novo;
				head = novo;
				tail = novo;
			}
		}

		public void orderedInsert(T dado){
			current = head;
			Node novo = new Node(dado);
			if(head == null){
				viewHead(dado);
			}else{
				/*try {
					String s = (String) novo.data;
				} catch (ClassCastException c) {
					System.out.println("Converte direito caralha");
				}*/
				if(hasNext() || head == tail){
					while (hasNext()) {
						if (novo.data instanceof String) {
							if (novo.data.compareTo(current.data) > 0) {
								next();
							} else {
								insert(dado);
								break;
							}
						}
					}
					if(head == tail){
						if (novo.data.compareTo(current.data) > 0) {
							next();
							append(dado);
						} else {
							pushFront(dado);
						}
					}else{					
						next();
					}
				}else{
					append(dado);
				}
			}
			
		}
		//public int tamanho(){return qtdElemento;}
		
	}
	
	private Node head;
	private Node tail;

	void append(T value) {
		Node novo = new Node(value);
		if (tail != null){
			tail.next = novo;
		}else{
			head = novo;
		}
		tail = novo;
		qtdElemento++;
	}

	void pushFront(T value) {
		Node novo = new Node(value);
		novo.next = head;
		if (head == null){
			tail = novo;
		}
		head = novo;
		qtdElemento++;
	}
	
	public Iterador<T> iterator() {
		return new ListIterator();
	}

	public int tamanho(){return qtdElemento;}
}