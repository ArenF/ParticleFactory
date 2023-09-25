package org.aren.particlefactory.compiler.nodes;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeType;
import org.aren.particlefactory.compiler.ASTNode;
import org.aren.particlefactory.compiler.SyntaxType;
import org.aren.particlefactory.compiler.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Program extends ASTNode {

    private List<ASTNode> nodeList = new ArrayList<>();

    protected Program() {
        super(TokenType.Program);
    }

    public void add(ASTNode node) {
        nodeList.add(node);
    }

    @Override
    public void accept(Visitor visitor) {
        for (ASTNode node : nodeList) {
            node.accept(visitor);
        }
    }
}
