package be.wimtibackx.experiments.jss_nameexpr_enum_jar_example.symbol_solver_user;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.model.declarations.ValueDeclaration;
import com.github.javaparser.symbolsolver.model.resolution.SymbolReference;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SymbolSolverUser {

    private final CombinedTypeSolver combinedTypeSolver;

    public SymbolSolverUser() {
        combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());
        combinedTypeSolver.add(new JavaParserTypeSolver(new File("sourceundertest/src/main/java")));
        try {
            combinedTypeSolver.add(new JarTypeSolver(new File("enumdefiner/target/enum-definer-1.0-SNAPSHOT.jar").getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        new SymbolSolverUser().start();
    }

    private void start() throws FileNotFoundException {
        final FileInputStream in = new FileInputStream("sourceundertest/src/main/java/be/wimtibackx/experiments/jss_nameexpr_enum_jar_example/source_under_test/SourceClass.java");
        final CompilationUnit cu = JavaParser.parse(in);

        Expression value = cu.getChildNodesByType(NormalAnnotationExpr.class).get(0).getPairs().get(0).getValue();
        System.out.println("Annotation value is " + value);
        SymbolReference<? extends ValueDeclaration> solve = JavaParserFacade.get(combinedTypeSolver).solve(value);
        System.out.println("SymbolReference is " + solve);
    }
}
