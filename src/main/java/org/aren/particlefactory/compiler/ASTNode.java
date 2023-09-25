package org.aren.particlefactory.compiler;

import org.aren.particlefactory.compiler.nodes.TokenType;

public abstract class ASTNode {
    private TokenType type;

    protected ASTNode(TokenType type) {
        this.type = type;
    }

    public TokenType getType() {
        return type;
    }

    public abstract void accept(Visitor visitor);
}
