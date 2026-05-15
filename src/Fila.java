import java.util.NoSuchElementException;

public class Fila<E> {

	private Celula<E> frente;
	private Celula<E> tras;
	
	Fila() {
		
		Celula<E> sentinela = new Celula<E>();
		frente = tras = sentinela;
	}
	
	public boolean vazia() {
		
		return (frente == tras);
	}
	
	public void enfileirar(E item) {
		
		Celula<E> novaCelula = new Celula<E>(item);
		
		tras.setProximo(novaCelula);
		tras = tras.getProximo();
	}
	
	public E desenfileirar() {
		
		E item = null;
		Celula<E> primeiro;
		
		item = consultarPrimeiro();
		
		primeiro = frente.getProximo();
		frente.setProximo(primeiro.getProximo());
		
		primeiro.setProximo(null);
			
		// Caso o item desenfileirado seja também o último da fila.
		if (primeiro == tras)
			tras = frente;
		
		return item;
	}
	
	public E consultarPrimeiro() {

		if (vazia()) {
			throw new NoSuchElementException("Nao há nenhum item na fila!");
		}

		return frente.getProximo().getItem();

	}

    
	private int consultarTamanho(){
		int contador = 0;
		Celula<E> atual = frente.getProximo();
		while (atual != null) {
			atual = atual.getProximo();
			contador++;
		}
		return contador;
	}


    public int contarCaracter(Character caractere) {
        Celula<E> aux = new Celula<E>();
        aux = frente.getProximo();
        int contadorOcorrencia = 0;
        for (int i = 0; i < consultarTamanho(); i++) {
            if(aux.getItem().equals(caractere)) {
                contadorOcorrencia++;
            }
            aux = aux.getProximo();
        }
        return contadorOcorrencia;
    }
	
	public void imprimir() {
		
		Celula<E> aux;
		
		if (vazia())
			System.out.println("A fila está vazia!");
		else {
			aux = this.frente.getProximo();
			while (aux != null) {
				System.out.println(aux.getItem());
				aux = aux.getProximo();
			}
		} 	
	}

	public Fila<E> extrairLote(int numItens) {

		Fila<E> novaFila = new Fila<E>();

		int contador = 0;

		while (!this.vazia() && contador < numItens) {

			E item = this.desenfileirar();

			novaFila.enfileirar(item);

			contador++;
		}

		return novaFila;
	}
}