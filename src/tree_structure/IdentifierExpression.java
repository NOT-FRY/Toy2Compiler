package tree_structure;

import tree_structure.Expression.Expression;

import java.util.Objects;

public class IdentifierExpression {
    private Identifier identifier;
    private Expression exp;

    public IdentifierExpression(Identifier identifier, Expression exp) {
        this.identifier = identifier;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "IdentifierExpression{" +
                "identifier=" + identifier +
                ", exp=" + exp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentifierExpression that)) return false;
        return Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getExp(), that.getExp());
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public Expression getExp() {
        return exp;
    }

    public void setExp(Expression exp) {
        this.exp = exp;
    }


}
