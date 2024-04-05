package View;

import java.util.List;
import java.util.ArrayList;

import Model.Expressions.ArithExp;
import Model.Expressions.ReadHeapExp;
import Model.Expressions.RelationExp;
import Model.Expressions.ValueExp;
import Model.Expressions.VarExp;
import Model.Statements.CompStmt;
import Model.Statements.IStmt;
import Model.Statements.ExpresionStmts.PrintStmt;
import Model.Statements.FileStmts.CloseRFileStmt;
import Model.Statements.FileStmts.OpenRFileStmt;
import Model.Statements.FileStmts.ReadFileStmt;
import Model.Statements.HeapStmts.NewHeapStmt;
import Model.Statements.HeapStmts.WriteHeapStmt;
import Model.Statements.LoopStmts.WhileStmt;
import Model.Statements.SelectionStmts.IfStmt;
import Model.Statements.ThreadStmts.ForkStmt;
import Model.Statements.VarStmts.AssignStmt;
import Model.Statements.VarStmts.VarDeclStmt;
import Model.Statements.OtherStmts.NopStmt;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

public class Examples {

        public static List<IStmt> getExamples() {

                List<IStmt> examples = new ArrayList<>();

                IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                new PrintStmt(new VarExp("v"))));
                examples.add(ex1);

                IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                                new CompStmt(new VarDeclStmt("b", new IntType()),
                                                new CompStmt(new AssignStmt("a", new ArithExp('+',
                                                                new ValueExp(new IntValue(2)),
                                                                new ArithExp('*', new ValueExp(new IntValue(3)),
                                                                                new ValueExp(new IntValue(5))))),
                                                                new CompStmt(
                                                                                new AssignStmt("b",
                                                                                                new ArithExp('+',
                                                                                                                new VarExp("a"),
                                                                                                                new ValueExp(new IntValue(
                                                                                                                                1)))),
                                                                                new PrintStmt(new VarExp("b"))))));

                examples.add(ex2);

                IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                                new CompStmt(new VarDeclStmt("v", new IntType()),
                                                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                                                new CompStmt(
                                                                                new IfStmt(new VarExp("a"),
                                                                                                new AssignStmt("v",
                                                                                                                new ValueExp(new IntValue(
                                                                                                                                2))),
                                                                                                new AssignStmt("v",
                                                                                                                new ValueExp(new IntValue(
                                                                                                                                3)))),
                                                                                new PrintStmt(new VarExp("v"))))));

                examples.add(ex3);

                IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                                                new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                                                                new CompStmt(new ReadFileStmt(
                                                                                                new VarExp("varf"),
                                                                                                new VarExp("varc")),
                                                                                                new CompStmt(new PrintStmt(
                                                                                                                new VarExp("varc")),
                                                                                                                new CompStmt(
                                                                                                                                new ReadFileStmt(
                                                                                                                                                new VarExp("varf"),
                                                                                                                                                new VarExp("varc")),
                                                                                                                                new CompStmt(new PrintStmt(
                                                                                                                                                new VarExp("varc")),
                                                                                                                                                new CloseRFileStmt(
                                                                                                                                                                new VarExp("varf"))))))))));

                examples.add(ex4);

                IStmt ex5 = new CompStmt(new VarDeclStmt("a", new IntType()),
                                new CompStmt(new VarDeclStmt("b", new IntType()),
                                                new CompStmt(new VarDeclStmt("v", new IntType()),
                                                                new CompStmt(new AssignStmt("a",
                                                                                new ValueExp(new IntValue(2))),
                                                                                new CompStmt(new AssignStmt("b",
                                                                                                new ValueExp(new IntValue(
                                                                                                                2))),
                                                                                                new CompStmt(
                                                                                                                new IfStmt(
                                                                                                                                new RelationExp("!=",
                                                                                                                                                new VarExp("a"),
                                                                                                                                                new VarExp("b")),
                                                                                                                                new AssignStmt("v",
                                                                                                                                                new ValueExp(new IntValue(
                                                                                                                                                                2))),
                                                                                                                                new AssignStmt("v",
                                                                                                                                                new ValueExp(new IntValue(
                                                                                                                                                                3)))),
                                                                                                                new PrintStmt(new VarExp(
                                                                                                                                "v"))))))));

                examples.add(ex5);

                IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                                new CompStmt(new NewHeapStmt("v", new ValueExp(new IntValue(20))),
                                                new CompStmt(new VarDeclStmt("a",
                                                                new RefType(new RefType(new IntType()))),
                                                                new CompStmt(new NewHeapStmt("a", new VarExp("v")),
                                                                                new CompStmt(new NewHeapStmt("v",
                                                                                                new ValueExp(new IntValue(
                                                                                                                30))),
                                                                                                new CompStmt(new PrintStmt(
                                                                                                                new VarExp("v")),
                                                                                                                new CompStmt(new PrintStmt(
                                                                                                                                new ReadHeapExp(new VarExp(
                                                                                                                                                "v"))),
                                                                                                                                new CompStmt(new PrintStmt(
                                                                                                                                                new VarExp("a")),
                                                                                                                                                new CompStmt(
                                                                                                                                                                new PrintStmt(new ReadHeapExp(
                                                                                                                                                                                new VarExp("a"))),
                                                                                                                                                                new PrintStmt(new ReadHeapExp(
                                                                                                                                                                                new ReadHeapExp(new VarExp(
                                                                                                                                                                                                "a")))))))))))));

                examples.add(ex6);

                IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                                new CompStmt(new NewHeapStmt("v", new ValueExp(new IntValue(0))),
                                                new CompStmt(new VarDeclStmt("a", new IntType()),
                                                                new CompStmt(new AssignStmt("a",
                                                                                new ValueExp(new IntValue(3))),
                                                                                new CompStmt(
                                                                                                new WhileStmt(new RelationExp(
                                                                                                                ">",
                                                                                                                new VarExp("a"),
                                                                                                                new ValueExp(new IntValue(
                                                                                                                                0)))),
                                                                                                new CompStmt(new PrintStmt(
                                                                                                                new ReadHeapExp(new VarExp(
                                                                                                                                "v"))),
                                                                                                                new CompStmt(
                                                                                                                                new AssignStmt("a",
                                                                                                                                                new ArithExp('-',
                                                                                                                                                                new VarExp("a"),
                                                                                                                                                                new ValueExp(new IntValue(
                                                                                                                                                                                1)))),
                                                                                                                                new PrintStmt(new VarExp(
                                                                                                                                                "a")))))))));

                examples.add(ex7);

                IStmt ex8 = new CompStmt(new VarDeclStmt("v", new IntType()),
                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                                                                new CompStmt(new NewHeapStmt("a",
                                                                                new ValueExp(new IntValue(22))),
                                                                                new CompStmt(
                                                                                                new ForkStmt(new CompStmt(
                                                                                                                new WriteHeapStmt(
                                                                                                                                "a",
                                                                                                                                new ValueExp(new IntValue(
                                                                                                                                                30))),
                                                                                                                new CompStmt(
                                                                                                                                new AssignStmt("v",
                                                                                                                                                new ValueExp(new IntValue(
                                                                                                                                                                32))),
                                                                                                                                new CompStmt(new PrintStmt(
                                                                                                                                                new VarExp("v")),
                                                                                                                                                new PrintStmt(
                                                                                                                                                                new ReadHeapExp(new VarExp(
                                                                                                                                                                                "a"))))))),
                                                                                                new CompStmt(new PrintStmt(
                                                                                                                new VarExp("v")),
                                                                                                                new CompStmt(new PrintStmt(
                                                                                                                                new ReadHeapExp(new VarExp(
                                                                                                                                                "a"))),
                                                                                                                                new NopStmt())))))));

                examples.add(ex8);

                IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
                                new CompStmt(new AssignStmt("v", new ValueExp(new BoolValue(0))),
                                                new PrintStmt(new VarExp("v"))));

                examples.add(ex9);

                return examples;
        }

}
