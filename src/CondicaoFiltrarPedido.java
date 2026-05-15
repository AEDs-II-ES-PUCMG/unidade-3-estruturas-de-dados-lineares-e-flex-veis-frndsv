import java.util.function.Predicate;

public class CondicaoFiltrarPedido
    implements Predicate<Pedido> {

    private String descricao;

    public CondicaoFiltrarPedido(String descricao) { 
        this.descricao = descricao;
    }

    @Override
    public boolean test(Pedido pedido) {
        Lista<ItemDePedido> itens = pedido.getItensDoPedido();

        ItemDePedido procurado = new ItemDePedido(new ProdutoNaoPerecivel(descricao, 1, 0.1), 1, 1);

        ItemDePedido encontrado = itens.buscarPor(new CriterioDeBuscaPorDescricao(), procurado);

        return encontrado != null;
    }
}