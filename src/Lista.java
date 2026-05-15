import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

public class Lista<E> implements Iterable<E> {

    private Celula<E> primeiro;
    private Celula<E> ultimo;
    private int tamanho;

    public Lista() {
        Celula<E> sentinela = new Celula<>();
        primeiro = ultimo = sentinela;
        tamanho = 0;
    }

    public boolean vazia() {
        return primeiro == ultimo;
    }

    public int tamanho() {
        return tamanho;
    }

    public void inserirFinal(E item) {
        Celula<E> nova = new Celula<>(item);
        ultimo.setProximo(nova);
        ultimo = nova;
        tamanho++;
    }

    public void inserirInicio(E item) {
        Celula<E> nova = new Celula<>(item, primeiro.getProximo());
        if (vazia()) ultimo = nova;
        primeiro.setProximo(nova);
        tamanho++;
    }

    public E removerInicio() {
        if (vazia()) throw new NoSuchElementException("Lista vazia!");
        Celula<E> removido = primeiro.getProximo();
        primeiro.setProximo(removido.getProximo());
        if (removido == ultimo) {
            ultimo = primeiro;
        } 
        removido.setProximo(null);
        tamanho--;
        return removido.getItem();
    }

    public void imprimir() {
        if (vazia()) {
            System.out.println("A lista está vazia!");
        } else {
            Celula<E> aux = primeiro.getProximo();
            while (aux != null) {
                System.out.println(aux.getItem());
                aux = aux.getProximo();
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Celula<E> atual = primeiro.getProximo();

            @Override
            public boolean hasNext() {
                return atual != null;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                E item = atual.getItem();
                atual = atual.getProximo();
                return item;
            }
        };
    }

    // Tarefa 1
    public E buscarPor(Comparator<E> criterioDeBusca, E item) {
        Celula<E> atual = primeiro.getProximo();
        while (atual != null) {
            if (criterioDeBusca.compare(atual.getItem(), item) == 0) {
                return atual.getItem();
            }
            atual = atual.getProximo();
        }
        return null;
    }

    // Tarefa 2
    public double somarMultiplicacoes(Function<E, Double> extratorValor, Function<E, Integer> extratorFator) {
        if (vazia()) {
            throw new IllegalStateException("Lista vazia!");
        }
        double soma = 0;
        Celula<E> atual = primeiro.getProximo();
        while (atual != null) {
            double valor = extratorValor.apply(atual.getItem()); // Recebeu a arrow function do parametro. Agora, a variável valor vai fazer/pedir o seguinte: Aplica a função recebida (extratorValor), mandando como parametro o item atual. A função mandada como parametro laaa na classe Pedido foi item -> item.getPrecoVenda(), ou seja, retornar o preco de venda do item que for passado como parametro. A assinatura é Function<E, Double>, ou seja, recebe uma função e retorna um double. Exatamente o que aconteceu aqui.
            int fator = extratorFator.apply(atual.getItem()); // Recebeu a arrow function do parametro. Agora, a variável fator vai fazer/pedir o seguinte: Aplica a função recebida (extratorFator), mandando como parametro o item atual. A função mandada como parametro laaa na classe Pedido foi item -> item.getQuantidade(), ou seja, retornar a quantidade do item que for passado como parametro. A assinatura é Function<E, Integer>, ou seja, recebe uma função e retorna um int. Exatamente o que aconteceu aqui.
            soma += valor * fator; // Multiplica e soma pra soma total
            atual = atual.getProximo(); 
        }
        return soma;
    }

    // Tarefa 3
    public Lista<E> filtrar(Predicate<E> condicional) {
        if (vazia()) {
            throw new IllegalStateException("Lista vazia!");
        }
        Lista<E> novaLista = new Lista<>();
        Celula<E> atual = primeiro.getProximo();
        while (atual != null) {
            if (condicional.test(atual.getItem())) {
                novaLista.inserirFinal(atual.getItem());
            }
            atual = atual.getProximo();
        }
        return novaLista;
    }

    public Celula<E> getPrimeiro() {
        return primeiro;
    }

    public Lista<E> subLista(int numItens) {
		if (numItens < 0) {
			throw new IllegalArgumentException("Número inválido!");
		}
		Lista<E> aux = new Lista<>();
		Lista<E> resultado = new Lista<>();
		Celula<E> atual = primeiro.getProximo();
		int contador = 0;
		while (atual != null && contador < numItens) {
            aux.inserirFinal(atual.getItem());
			atual = atual.getProximo();
			contador++;
		}
		if (contador < numItens) {
			throw new IllegalArgumentException("A pilha não possui essa quantidade de elementos!");
		}
		while (!aux.vazia()) {
            resultado.inserirFinal(aux.removerInicio());
		}
		return resultado;
	}

    public StringBuilder imprimirLista() {
		Celula<E> aux = new Celula<E>();
		StringBuilder listaBuilder = new StringBuilder();
		aux = primeiro.getProximo();
		for (int i = 0; i < tamanho(); i++) {
			listaBuilder.append(aux.getItem());
            aux = aux.getProximo();
        }

		listaBuilder.append("\n");

		return listaBuilder;
	}

    
	public Lista<E> extrairLote(int numItens) {
		Lista<E> novaLista = new Lista<E>();

		int contador = 0;

		while (!this.vazia() && contador < numItens) {
			E item = this.removerInicio();
			novaLista.inserirFinal(item);
			contador++;
		}

		return novaLista;
	}
}
