package livraria.core.util.calculo.correios;

public class Principal {

    public static void main(String[] args) throws Exception {
        
        Pacote p = new Pacote("2.2", 20, 20, 20, 20, 1);
        
        DadosFrete df = CorreiosFrete.consultarFretePAC("01130000", "08580615", p);
        
        System.out.println(df.getValor());
        System.out.println(df.getCodigoErro());       
        
        
    }

}
