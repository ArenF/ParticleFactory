package org.aren.particlefactory.compiler.nodes;

import org.aren.particlefactory.compiler.ASTNode;
import org.aren.particlefactory.compiler.SyntaxType;
import org.aren.particlefactory.compiler.Visitor;

public class CallExpression extends ASTNode {

    String name;
    ASTNode param;

    public CallExpression(String name, ASTNode param) {
        super(TokenType.CallExpression);
        this.name = name;
        this.param = param;
    }

    @Override
    public void accept(Visitor visitor) {
        param.accept(visitor);
        visitor.visit(this);
    }
}
