package com.paracamplus.ilp4.ilp4tme9.compiler;

import com.paracamplus.ilp1.compiler.AssignDestination;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.Inamed;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCmethodDefinition;
import com.paracamplus.ilp4.interfaces.IASTsend;

public class Compiler extends com.paracamplus.ilp4.compiler.Compiler {

    public Compiler(IOperatorEnvironment ioe, IGlobalVariableEnvironment igve) {
        super(ioe, igve);
    }

    @Override
    public Void visit(IASTsend iast, Context context) throws CompilationException {
        emit("{ \n");
        IASTvariable tmpMethod = context.newTemporaryVariable();
        emit("  ILP_general_function " + tmpMethod.getMangledName() + "; \n");
        IASTvariable tmpReceiver = context.newTemporaryVariable();
        emit("  ILP_Object " + tmpReceiver.getMangledName() + "; \n");
        Context c = context.redirect(new AssignDestination(tmpReceiver));

        IASTexpression[] arguments = iast.getArguments();
        IASTvariable[] tmps = new IASTvariable[arguments.length];
        for ( int i=0 ; i<arguments.length ; i++ ) {
            IASTvariable tmp = context.newTemporaryVariable();
            emit("  ILP_Object " + tmp.getMangledName() + "; \n");
            tmps[i] = tmp;
        }

        iast.getReceiver().accept(this, c);
        for ( int i=0 ; i<arguments.length ; i++ ) {
            IASTexpression expression = arguments[i];
            IASTvariable tmp = tmps[i];
            Context c2 = context.redirect(new AssignDestination(tmp));
            expression.accept(this, c2);
        }

        // Cache en ligne
        // emit("{\n");
        // String classCache = tmpMethod.getMangledName() + "ClassCache";
        // String methodCache = tmpMethod.getMangledName() + "MethodCache";
        // String argcCache = tmpMethod.getMangledName() + "ArgcCache";
        // String resultCache = tmpMethod.getMangledName() + "ResultCache";
        // emit("static ILP_Class " + classCache + " = NULL;\n");
        // emit("static ILP_Method " + methodCache + " = NULL;\n");
        // emit("static int " + argcCache + " = -1;\n");
        // emit("static ILP_general_function " + resultCache + " = NULL;\n");

        // emit("if(");
        // emit(tmpReceiver.getMangledName() + "->_class ==" + classCache + " && ");
        // emit("&ILP_object_" + Inamed.computeMangledName(iast.getMethodName()) + "_method == " + methodCache + " && ");
        // emit((1 + arguments.length) + "==" + argcCache);
        // emit(") {\n");
        // emit(tmpMethod.getMangledName());
        // emit(" = " + resultCache + ";\n");
        // emit("} else {\n");

        emit(tmpMethod.getMangledName());

        // Cache global
        // emit(" = ILP_find_method_global_cache(");

        // Cache de ILP_Method
        emit(" = ILP_find_method_method_cache(");

        emit(tmpReceiver.getMangledName());
        emit(", &ILP_object_");
        emit(Inamed.computeMangledName(iast.getMethodName()));
        emit("_method, ");
        emit(1 + arguments.length);
        emit(");\n");
        // emit(classCache + " = " + tmpReceiver.getMangledName() + "->_class; \n");
        // emit(methodCache + " = &ILP_object_" + Inamed.computeMangledName(iast.getMethodName()) + "_method; \n");
        // emit(argcCache + " = " + (arguments.length + 1) + "; \n");
        // emit(resultCache + " = " + tmpMethod.getMangledName() + "; \n");
        // emit("}\n");
        // emit("}\n");

        emit(context.destination.compile());
        emit(tmpMethod.getName());
        emit("(NULL, ");
        emit(tmpReceiver.getMangledName());
        for ( int i = 0 ; i<arguments.length ; i++ ) {
            emit(", ");
            emit(tmps[i].getMangledName());
        }
        emit(");\n}\n");
        return null;
    }

public Void visit(IASTCclassDefinition iast, Context context)
            throws CompilationException {
        String lastFieldName = "NULL";
        int inheritedFieldsCount = 0;
        if ( ! "Object".equals(iast.getSuperClassName()) ) {
            IASTCclassDefinition superClass = iast.getSuperClass();
            String[] fieldNames = superClass.getTotalFieldNames();
            inheritedFieldsCount = fieldNames.length;
            if ( inheritedFieldsCount > 0 ) {
                String fieldName = fieldNames[inheritedFieldsCount - 1];
                String mangledFieldName = Inamed.computeMangledName(fieldName);
                lastFieldName = "&ILP_object_" + mangledFieldName + "_field";
            }
        }
        String[] fieldNames = iast.getProperFieldNames();
        for ( int i=0 ; i<fieldNames.length ; i++ ) {
            String mangledFieldName = Inamed.computeMangledName(fieldNames[i]);
            emit("\nstruct ILP_Field ILP_object_");
            emit(mangledFieldName);
            emit("_field = {\n  &ILP_object_Field_class,\n     { { ");
            emit("(ILP_Class) &ILP_object_");
            emit(iast.getMangledName());
            emit("_class,\n   ");
            emit(lastFieldName);
            emit(",\n    \"");
            emit(mangledFieldName);
            emit("\",\n  ");
            emit(i + inheritedFieldsCount);
            emit(" } }\n};\n");
            lastFieldName = "&ILP_object_" + mangledFieldName + "_field";
        }

        emit("\nstruct ILP_Class");
        emit(iast.getTotalMethodDefinitionsCount());
        emit(" ILP_object_");
        emit(iast.getMangledName());
        emit("_class = {\n  &ILP_object_Class_class,\n  { { ");
        emit("(ILP_Class) &ILP_object_");
        emit(iast.getSuperClass().getMangledName());
        emit("_class,\n         \"");
        emit(iast.getMangledName());
        emit("\",\n         ");
        emit(inheritedFieldsCount + fieldNames.length);
        emit(",\n         ");
        emit(lastFieldName);
        emit(",\n         ");
        emit(iast.getTotalMethodDefinitionsCount());
        emit(",\n { ");
        for ( IASTCmethodDefinition md : iast.getTotalMethodDefinitions() ) {
            emit(md.getCName());
            emit(", \n");
        }
        emit(" } } }\n};\n");

        IASTCmethodDefinition[] methods = iast.getNewProperMethodDefinitions();
        for ( int i = 0 ; i<methods.length ; i++ ) {
            IASTCmethodDefinition method = methods[i];
            if ( ! alreadyGeneratedMethodObject.containsKey(method.getName()) ) {
                emit("\nstruct ILP_Method ILP_object_");
                emit(Inamed.computeMangledName(method.getMethodName()));
                emit("_method = {\n  &ILP_object_Method_class,\n  { { ");
                emit("(struct ILP_Class*) &ILP_object_");
                emit(iast.getMangledName());
                emit("_class,\n  \"");
                emit(method.getMethodName());
                emit("\",\n  ");
                emit(method.getVariables().length);
                emit(",  /* arité */\n  ");
                emit(iast.getMethodOffset(method));
                emit(",  /* offset */ \n  ");
                emit("NULL,  /* cache classe */ \n  ");
                emit("-1,  /* cache arité */ \n  ");
                emit("NULL /* cache resultat */ \n    } }\n};\n");
            }
        }
        return null;
    }

}
