/* *****************************************************************
 * ILP9 - Implantation d'un langage de programmation.
 * by Christian.Queinnec@paracamplus.com
 * See http://mooc.paracamplus.com/ilp9
 * GPL version 3
 ***************************************************************** */
package com.paracamplus.ilp4.compiler.interfaces;


public interface IASTCprogram 
extends com.paracamplus.ilp3.compiler.interfaces.IASTCprogram {
	IASTCclassDefinition[] getClassDefinitions();
}