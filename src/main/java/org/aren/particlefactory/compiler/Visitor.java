package org.aren.particlefactory.compiler;

import org.aren.particlefactory.compiler.nodes.CallExpression;
import org.aren.particlefactory.compiler.nodes.NumberLiteral;
import org.aren.particlefactory.compiler.nodes.Program;

public interface Visitor {
    void visit(CallExpression callExpression);
    void visit(NumberLiteral numberLiteral);
    void visit(Program program);
}
