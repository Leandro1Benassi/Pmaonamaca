package Sistema;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public void aumentarSalario(BigDecimal percentual) {
        this.salario = this.salario.add(this.salario.multiply(percentual)).setScale(2, RoundingMode.HALF_UP);
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public int getIdade() {
        return LocalDate.now().getYear() - getDataNascimento().getYear();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("Nome: %s\nNascimento: %s\nSalário: R$ %,.2f\nFunção: %s", 
                getNome(), getDataNascimento().format(formatter), salario, funcao);
    }
}