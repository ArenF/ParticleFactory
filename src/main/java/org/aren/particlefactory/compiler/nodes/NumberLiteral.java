package org.aren.particlefactory.compiler.nodes;

import org.aren.particlefactory.compiler.ASTNode;
import org.aren.particlefactory.compiler.SyntaxType;
import org.aren.particlefactory.compiler.Visitor;

public class NumberLiteral extends ASTNode {

    long value;

    public NumberLiteral(long value) {
        super(TokenType.NumberLiteral);
        this.value = value;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
